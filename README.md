# SigaraPlugin (Paper 1.21.8)

`/sigara al [miktar]` komutuyla, resource pack'teki `sigara` texture'ina
(custom_model_data floats:[1.0]) sahip, adi "Sigara" olan ve mor renkli
"Kullanim hakki: X" acklamasi tasiyan bir goat_horn itemi verir.

- `/sigara al`      -> 50 kullanim hakli sigara verir
- `/sigara al 10`   -> 10 kullanim hakli sigara verir

## Bu jar nasil derlenir?

Bu sandbox ortaminin internet erisimi PaperMC/Maven depolarina kapali
oldugu icin jar burada derlenemedi. Iki secenegin var:

### 1) Kendi bilgisayarinda (JDK 21 + Maven gerekli)
```
mvn clean package
```
Cikti: `target/SigaraPlugin.jar`

### 2) GitHub Actions ile (hicbir sey kurmadan)
1. Bu klasoru yeni bir GitHub reposuna push'la (`.github/workflows/build.yml` zaten hazir)
2. Repo sayfasinda "Actions" sekmesine git, "Build SigaraPlugin" workflow'unun
   calismasini bekle (birkac dakika surer)
3. Workflow bitince "Artifacts" bolumunden `SigaraPlugin.jar` dosyasini indir

## Kurulum
1. Derlenen `SigaraPlugin.jar` dosyasini sunucunun `plugins/` klasorune koy
2. Sunucuyu yeniden baslat (veya `/reload confirm`)
3. `/sigara al` komutunu dene (varsayilan olarak sadece OP kullanabilir,
   `plugin.yml` icindeki `sigara.al` iznini degistirerek bunu ayarlayabilirsin)

## Not
Bu plugin sadece itemi vermekle ilgilenir; her kullanimda "kullanim hakki"
sayisinin otomatik azalmasi icin ayrica bir PlayerInteractEvent listener'i
eklenmesi gerekir. Istersen bunu da plugin'e ekleyebilirim.
