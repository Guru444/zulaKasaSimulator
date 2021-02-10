package com.kasa.zulakasasimulator.model;

public class cekilisKullanicilar {
    public String getKullaniciNickName() {
        return kullaniciNickName;
    }

    public void setKullaniciNickName(String kullaniciNickName) {
        this.kullaniciNickName = kullaniciNickName;
    }

    public String getKullaniciKazanilanItem() {
        return kullaniciKazanilanItem;
    }

    public void setKullaniciKazanilanItem(String kullaniciKazanilanItem) {
        this.kullaniciKazanilanItem = kullaniciKazanilanItem;
    }

    public String getKullaniciCekilisKatilimTarih() {
        return kullaniciCekilisKatilimTarih;
    }

    public void setKullaniciCekilisKatilimTarih(String kullaniciCekilisKatilimTarih) {
        this.kullaniciCekilisKatilimTarih = kullaniciCekilisKatilimTarih;
    }

    public  cekilisKullanicilar(){

    }

    public cekilisKullanicilar(String key,String kullaniciNickName, String kullaniciKazanilanItem, String kullaniciCekilisKatilimTarih,String deviceID) {
        this.kullaniciNickName = kullaniciNickName;
        this.kullaniciKazanilanItem = kullaniciKazanilanItem;
        this.kullaniciCekilisKatilimTarih = kullaniciCekilisKatilimTarih;
        this.key = key;
        this.deviceID = deviceID;
    }

    String kullaniciNickName;
    String kullaniciKazanilanItem;
    String kullaniciCekilisKatilimTarih;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    String deviceID;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    String key;
}
