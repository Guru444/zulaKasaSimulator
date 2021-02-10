package com.kasa.zulakasasimulator.model.zulaRegister;

public class ZulaGameUsersInfo {
    public String zulaUserName;
    public String zulaUserMail;
    public String zulaUserPassword;

    public ZulaGameUsersInfo(String zulaName, String zulaMail, String zulaPassword) {
        this.zulaUserName = zulaName;
        this.zulaUserMail = zulaMail;
        this.zulaUserPassword = zulaPassword;
    }
}
