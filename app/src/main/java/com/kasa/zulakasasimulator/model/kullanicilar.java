package com.kasa.zulakasasimulator.model;

public class kullanicilar {

    String kullaniciAdi;
    String mailAdresi;
    String sifre;
    String uniqKey;
    String loginDate;
    Long kazanılanGuruParasi;
    Long kazanılanZP;
    Long kazanılanXP;

    public Long getKazanılanGuruParasi() {
        return kazanılanGuruParasi;
    }

    public void setKazanılanGuruParasi(Long kazanılanGuruParasi) {
        this.kazanılanGuruParasi = kazanılanGuruParasi;
    }

    public Long getKazanılanZP() {
        return kazanılanZP;
    }

    public void setKazanılanZP(Long kazanılanZP) {
        this.kazanılanZP = kazanılanZP;
    }

    public Long getKazanılanXP() {
        return kazanılanXP;
    }

    public void setKazanılanXP(Long kazanılanXP) {
        this.kazanılanXP = kazanılanXP;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getMailAdresi() {
        return mailAdresi;
    }

    public void setMailAdresi(String mailAdresi) {
        this.mailAdresi = mailAdresi;
    }

    public String sifre() {
        return sifre;
    }

    public void setDeviceID(String sifre) {
        this.sifre = sifre;
    }

    public String getUniqKey() {
        return uniqKey;
    }

    public void setUniqKey(String uniqKey) {
        this.uniqKey = uniqKey;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public kullanicilar(String kullaniciAdi, String mailAdresi, String sifre, String uniqKey, String loginDate, Long kazanılanGuruParasi, Long kazanılanZP, Long kazanılanXP) {
        this.kullaniciAdi = kullaniciAdi;
        this.mailAdresi = mailAdresi;
        this.sifre = sifre;
        this.uniqKey = uniqKey;
        this.loginDate = loginDate;
        this.kazanılanGuruParasi = kazanılanGuruParasi;
        this.kazanılanZP = kazanılanZP;
        this.kazanılanXP = kazanılanXP;
    }
}
