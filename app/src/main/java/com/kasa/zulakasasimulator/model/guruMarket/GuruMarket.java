package com.kasa.zulakasasimulator.model.guruMarket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GuruMarket {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("GuruMarketName")
    @Expose
    private String GuruMarketName;
    @SerializedName("GuruMarketDescription")
    @Expose
    private String GuruMarketDescription;
    @SerializedName("GuruMarketPrice")
    @Expose
    private String GuruMarketPrice;
    @SerializedName("GuruMarketUnit")
    @Expose
    private String GuruMarketUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuruMarketName() {
        return GuruMarketName;
    }

    public void setGuruMarketName(String guruMarketName) {
        GuruMarketName = guruMarketName;
    }

    public String getGuruMarketDescription() {
        return GuruMarketDescription;
    }

    public void setGuruMarketDescription(String guruMarketDescription) {
        GuruMarketDescription = guruMarketDescription;
    }

    public String getGuruMarketPrice() {
        return GuruMarketPrice;
    }

    public void setGuruMarketPrice(String guruMarketPrice) {
        GuruMarketPrice = guruMarketPrice;
    }

    public String getGuruMarketUnit() {
        return GuruMarketUnit;
    }

    public void setGuruMarketUnit(String guruMarketUnit) {
        GuruMarketUnit = guruMarketUnit;
    }
}
