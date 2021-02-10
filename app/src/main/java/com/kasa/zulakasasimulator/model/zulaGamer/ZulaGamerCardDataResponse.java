package com.kasa.zulakasasimulator.model.zulaGamer;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZulaGamerCardDataResponse implements Serializable {
    @Nullable
    @SerializedName("caseWinXP")
    private String caseWinXP;
    @Nullable
    @SerializedName("caseWinZP")
    private String caseWinZP;
    @Nullable
    @SerializedName("caseWinGURU")
    private String caseWinGURU;

    public String getCaseWinXP() { return caseWinXP; }
    public String getCaseWinZP() { return caseWinZP; }
    public String getCaseWinGURU() { return caseWinGURU; }

    public void setCaseWinZP(@Nullable String caseWinZP) {
        this.caseWinZP = caseWinZP;
    }

    public void setCaseWinXP(@Nullable String caseWinXP) {
        this.caseWinXP = caseWinXP;
    }

    public void setCaseWinGURU(@Nullable String caseWinGURU) {
        this.caseWinGURU = caseWinGURU;
    }
}
