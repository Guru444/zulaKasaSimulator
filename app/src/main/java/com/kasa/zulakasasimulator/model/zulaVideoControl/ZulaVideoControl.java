package com.kasa.zulakasasimulator.model.zulaVideoControl;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userVideoControl")
public class ZulaVideoControl {
    public static final String ID = "id";

    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "videoOneHour")
    private String videoOneHour;

    @ColumnInfo(name = "videoMinute")
    private String videoMinute;

    @ColumnInfo(name = "dateProcess")
    private String dateProcess;

    @ColumnInfo(name = "zulaUserMail")
    private String zulaUserMail;


    public Long getFie_id() {
        return id;
    }

    public void setFie_id(Long id) {
        this.id = id;
    }

    public String getVideoOneHour() {
        return videoOneHour;
    }

    public void setVideoOneHour(String videoOneHour) {
        this.videoOneHour = videoOneHour;
    }

    public String getVideoMinute() {
        return videoMinute;
    }

    public void setVideoMinute(String videoMinute) {
        this.videoMinute = videoMinute;
    }

    public String getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(String dateProcess) {
        this.dateProcess = dateProcess;
    }

    public String getZulaUserMail() {
        return zulaUserMail;
    }

    public void setZulaUserMail(String zulaUserMail) {
        this.zulaUserMail = zulaUserMail;
    }


    public ZulaVideoControl(String videoOneHour,String videoMinute,String dateProcess,String zulaUserMail){
        this.videoOneHour = videoOneHour;
        this.videoMinute = videoMinute;
        this.dateProcess = dateProcess;
        this.zulaUserMail = zulaUserMail;
    }
}
