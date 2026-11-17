# Musteri URL'i Uzerinde Basit Test

URL, "site_url" degerinden okunur:
- Ortam degiskeni olarak verilirse (orn. site_url=https://musteri.com) o kullanilir,
- verilmezse env/default/default.properties icindeki site_url kullanilir.

Adimlar sayfaya ozel locator kullanmaz; her sitede calisir.
Ekran goruntuleri testinium-selenium4-driver kutuphanesi tarafindan
her Selenium komutundan sonra otomatik alinir (takeScreenshot = YES).

## Musteri URL'ine git, gez, rastgele tikla
tags:simpleTest
* Konfigürasyondaki "site_url" adresine git
* Sayfanın yüklendiğini kontrol et
* "2" saniye bekle
* Sayfanın en altına kaydır
* "1" saniye bekle
* Sayfanın en üstüne kaydır
* Sayfadaki tıklanabilir elementlerden birine otomatik olarak rastgele tıkla
* "2" saniye bekle
* Sayfanın yüklendiğini kontrol et
* Önceki sayfaya geri dön
* Sayfayı yenile
* Sayfanın yüklendiğini kontrol et
