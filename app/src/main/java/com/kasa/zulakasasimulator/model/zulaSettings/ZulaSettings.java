package com.kasa.zulakasasimulator.model.zulaSettings;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZulaSettings implements Serializable {

    @SerializedName("option_name")
    @Expose
    private String option_name;
    @SerializedName("option_value")
    @Expose
    private String option_value;
    @SerializedName("option_group")
    @Expose
    private String option_group;
    @SerializedName("auto_load")
    @Expose
    private String auto_load;
    @SerializedName("option_message")
    @Expose
    private String option_message;

    public String getOption_message() {
        return option_message;
    }

    public void setOption_message(String option_message) {
        this.option_message = option_message;
    }

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_value() {
        return option_value;
    }

    public void setOption_value(String option_value) {
        this.option_value = option_value;
    }

    public String getOption_group() {
        return option_group;
    }

    public void setOption_group(String option_group) {
        this.option_group = option_group;
    }

    public String getAuto_load() {
        return auto_load;
    }

    public void setAuto_load(String auto_load) {
        this.auto_load = auto_load;
    }
}
