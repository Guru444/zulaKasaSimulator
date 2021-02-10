package com.kasa.zulakasasimulator.model.zulaSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZulaTotalOpenCase implements Serializable {
    @SerializedName("acilanKasa")
    @Expose
    private String acilanKasa;

    public String getAcilanKasa() {
        return acilanKasa;
    }

    public void setAcilanKasa(String acilanKasa) {
        this.acilanKasa = acilanKasa;
    }
}
