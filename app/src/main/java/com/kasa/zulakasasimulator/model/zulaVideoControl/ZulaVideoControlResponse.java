package com.kasa.zulakasasimulator.model.zulaVideoControl;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ZulaVideoControlResponse implements Serializable {

    @SerializedName("videoOneHour")
    private LocalDateTime videoOneHour;
    @SerializedName("videoMinute")
    private LocalDateTime videoMinute;
    @SerializedName("dateProcess")
    private LocalDateTime dateProcess;

    public LocalDateTime getVideoOneHour(){
        return videoOneHour;
    }
    public LocalDateTime getVideoMinute(){
        return videoMinute;
    }
    public LocalDateTime getDateProcess(){
        return dateProcess;
    }
}
