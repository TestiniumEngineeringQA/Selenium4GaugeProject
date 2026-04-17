Login sayfası spec dosyası
==========================

Geçerli Login
-------------
Tags: GecerliLogin

* Geçerli login yapılır ve hesaba giriş yapılır

Geçersiz Login-Boş kullanıcı adı boş şifre
------------------------------------------
Tags:GecersizLoginBosKullaniciAdiBosSifre

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmı boş bırakılır
* Şifre kısmı boş bırakılır
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "Please fill out this field." hata mesajı alınır


Geçersiz Login-Boş kullanıcı adı ve geçerli şifre
-------------------------------------------------
Tags:GecersizLoginBosKullaniciAdiGecerliSifre

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmı boş bırakılır
* Şifre kısmına "Q6XpZ?6GsMbA" şifresi girilir
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "Please fill out this field." hata mesajı alınır

Geçersiz Login-Geçerli kullanıcı adı ve boş şifre
-------------------------------------------------
Tags: GecersizLoginGecerliKullaniciAdiBosSifre

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "qa@testinium.io" kullanıcı adı girilir
* Şifre kısmı boş bırakılır
* Giriş yap butonuna tıklanır
* Şifre kısmında "Please fill out this field." hata mesajı alınır

Geçersiz Login-Yanlış kullanıcı adı ve yanlış şifre
---------------------------------------------------
Tags:GecersizLoginYanlisKullaniciAdiYanlisSifre

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "testsenaryo@gmail.com" kullanıcı adı girilir
* Şifre kısmına "Aa1234567" şifresi girilir
* Giriş yap butonuna tıklanır
* "EMAIL OR PASSWORD IS INCORRECT!" hata mesajı alınır

Geçersiz Login-Kayıtlı ama doğrulanmamış mail
---------------------------------------------
Tags: GecersizMailKayitliAmaDogrulanmamisMail

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "testinium@testinium.com" kullanıcı adı girilir
* Şifre kısmına "Qwe123+" şifresi girilir
* Giriş yap butonuna tıklanır
* UserNotEnabledException sayfasına yönlendiği görülür
* User Not Enabled sayfasında "I've sent a link to testinium@testinium.com click it and start using Testinium" mesajının alındığı görülür

Geçersiz Login-İnput Kontrolleri-@siz mail
------------------------------------------
Tags: GecersizLoginInputKontrolleriAtsizMail

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "testgmail.com" kullanıcı adı girilir
* Şifre kısmına "Aa123456" şifresi girilir
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "Please include an '@' in the email address." hata mesajı alınır

Geçersiz Login-Input Kontrolleri-Mailde geçersiz karakter
---------------------------------------------------------
Tags: GecersizLoginInputKontrolleriMaildeGecersizKarakter

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "test<@gmail.com" kullanıcı adı girilir
* Şifre kısmına "Aa123456" şifresi girilir
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "A part followed by '@' should not contain the symbol '<'." hata mesajı alınır

Geçersiz Login-İnput Kontrolleri-@işaretinden sonrası eksik Mail
----------------------------------------------------------------- 
Tags: GecersizLoginInputKontrolleriAtIsaretindenSonrasiEksikMail

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "test@" kullanıcı adı girilir
* Şifre kısmına "Aa123456" şifresi girilir
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "Please enter a part following '@'. 'test@' is incomplete." hata mesajı alınır

Geçersiz Login-İnput Kontrolleri-@işaretinden sonra sadece com
-----------------------------------------------------------------
Tags: GecersizLoginInputKontrolleriAtIsaretindenSonraSadeceCom

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kullanıcı adı kısmına "test@.com" kullanıcı adı girilir
* Şifre kısmına "Aa123456" şifresi girilir
* Giriş yap butonuna tıklanır
* Kullanıcı adı kısmında "'.' is used at a wrong position in '.com'." hata mesajı alınır

Login Sayfasından Şifremi Unuttum sayfasına geçiş
-------------------------------------------------
Tags: LoginSayfasindanSifremiUnuttumSayfasinaGecis

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Şifremi unuttum butonuna tıklanır
* Şifremi unuttum paneli görülür

Login sayfasından Kayıt ol sayfasına geçiş
------------------------------------------
Tags: LoginSayfasindanKayitOlSayfasinaGecis

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Kayıt ol butonuna tıklanır
* Kayıt ol paneli görülür

Linkedin ile giriş kontrolü
---------------------------
Tags:LinkedinIleGirisKontrolu

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Linkedin butonuna tıklanır
* Linkedin ile giriş sayfasının ekrana geldiği görülür

Google ile giriş kontrolü
--------------------------
Tags: GoogleIleGirisKontrolu

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Google butonuna tıklanır
* Google ile giriş sayfasının ekrana geldiği görülür

Github ile giriş kontrolü
-------------------------
Tags: GithubIleGirisKontrolu

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* Github butonuna tıklanır
* Github ile giriş sayfasının ekrana geldiği görülür

Wait kontrolü
-------------------------
Tags: WaitKontrolu

* "https://account.testinium.com/uaa/login" sayfasına gelinir
* "1020" saniye bekle
* Github butonuna tıklanır
* Github ile giriş sayfasının ekrana geldiği görülür



