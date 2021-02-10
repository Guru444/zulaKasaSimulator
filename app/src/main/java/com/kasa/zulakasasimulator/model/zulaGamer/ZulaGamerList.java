package com.kasa.zulakasasimulator.model.zulaGamer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZulaGamerList {
    @SerializedName("zulaUserMail")
    @Expose
    private String zulaUserMail;
    @SerializedName("caseWinXP")
    @Expose
    private String caseWinXP;
    @SerializedName("caseWinZP")
    @Expose
    private String caseWinZP;
    @SerializedName("caseWinGURU")
    @Expose
    private String caseWinGURU;

    public String getZulaUserMail() {
        return zulaUserMail;
    }
    public void setZulaUserMail(String zulaUserMail) {
        this.zulaUserMail = zulaUserMail;
    }
    public String getCaseWinXP() {
        return caseWinXP;
    }
    public void setCaseWinXP(String caseWinXP) {
        this.caseWinXP = caseWinXP;
    }
    public String getCaseWinZP() {
        return caseWinZP;
    }
    public void setStationName(String caseWinZP) {
        this.caseWinZP = caseWinZP;
    }
    public String setCaseWinGURU () {
        return caseWinGURU;
    }
    public void setFullSlot(String caseWinGURU) {
        this.caseWinGURU = caseWinGURU;
    }
}
