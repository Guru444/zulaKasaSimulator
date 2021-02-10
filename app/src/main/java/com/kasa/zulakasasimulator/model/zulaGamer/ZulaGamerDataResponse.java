package com.kasa.zulakasasimulator.model.zulaGamer;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZulaGamerDataResponse implements Serializable {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;

    public Boolean getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
}
