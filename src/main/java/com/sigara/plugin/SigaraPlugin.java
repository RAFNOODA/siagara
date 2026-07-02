package com.sigara.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SigaraPlugin extends JavaPlugin implements CommandExecutor {

    private static final int DEFAULT_KULLANIM_HAKKI = 50;

    @Override
    public void onEnable() {
        getCommand("sigara").setExecutor(this);
        getLogger().info("SigaraPlugin aktif edildi.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("Bu komut sadece oyuncular tarafindan kullanilabilir.")
                    .color(NamedTextColor.RED));
            return true;
        }

        if (args.length == 0 || !args[0].equalsIgnoreCase("al")) {
            player.sendMessage(Component.text("Kullanim: /sigara al [miktar]").color(NamedTextColor.YELLOW));
            return true;
        }

        int kullanimHakki = DEFAULT_KULLANIM_HAKKI;

        if (args.length >= 2) {
            try {
                kullanimHakki = Integer.parseInt(args[1]);
                if (kullanimHakki <= 0) {
                    player.sendMessage(Component.text("Miktar 0'dan buyuk olmali.").color(NamedTextColor.RED));
                    return true;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(Component.text("Gecersiz miktar: " + args[1]).color(NamedTextColor.RED));
                return true;
            }
        }

        ItemStack sigara = createSigara(kullanimHakki);

        // Envanterde yer yoksa yere dusur, varsa envantere ekle
        var kalan = player.getInventory().addItem(sigara);
        if (!kalan.isEmpty()) {
            kalan.values().forEach(item ->
                    player.getWorld().dropItemNaturally(player.getLocation(), item));
            player.sendMessage(Component.text("Envanterin dolu oldugu icin sigara yere dusuruldu.")
                    .color(NamedTextColor.GOLD));
        }

        player.sendMessage(Component.text("Sana " + kullanimHakki + " kullanim hakli bir sigara verildi.")
                .color(NamedTextColor.LIGHT_PURPLE));

        return true;
    }

    /**
     * custom_model_data floats:[1.0] olan, adi "Sigara" olan ve mor renkli
     * "Kullanim hakki: X" aciklamasina sahip bir goat_horn itemi olusturur.
     */
    private ItemStack createSigara(int kullanimHakki) {
        ItemStack item = new ItemStack(Material.GOAT_HORN);
        ItemMeta meta = item.getItemMeta();

        // Isim: "Sigara" (italik degil)
        meta.displayName(Component.text("Sigara")
                .color(NamedTextColor.WHITE)
                .decoration(TextDecoration.ITALIC, false));

        // custom_model_data -> floats:[1.0]  (resource pack'teki sigara.json ile eslesir)
        CustomModelDataComponent cmd = meta.getCustomModelDataComponent();
        cmd.setFloats(List.of(1.0f));
        meta.setCustomModelDataComponent(cmd);

        // Aciklama: mor renkli "Kullanim hakki: X"
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Kullanim hakki: " + kullanimHakki)
                .color(NamedTextColor.LIGHT_PURPLE)
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);

        item.setItemMeta(meta);
        return item;
    }
}
