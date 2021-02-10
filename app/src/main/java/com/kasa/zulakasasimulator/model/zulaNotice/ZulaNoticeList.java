package com.kasa.zulakasasimulator.model.zulaNotice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZulaNoticeList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("zulaNoticeTitle")
    @Expose
    private String zulaNoticeTitle;
    @SerializedName("zulaNoticeDescription")
    @Expose
    private String zulaNoticeDescription;
    @SerializedName("zulaNoticeHref")
    @Expose
    private String zulaNoticeHref;
    @SerializedName("zulaNoticePlanTime")
    @Expose
    private String zulaNoticePlanTime;
    @SerializedName("zulaNoticeDate")
    @Expose
    private String zulaNoticeDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getZulaNoticeTitle() {
        return zulaNoticeTitle;
    }

    public void setZulaNoticeTitle(String zulaNoticeTitle) {
        this.zulaNoticeTitle = zulaNoticeTitle;
    }

    public String getZulaNoticeDescription() {
        return zulaNoticeDescription;
    }

    public void setZulaNoticeDescription(String zulaNoticeDescription) {
        this.zulaNoticeDescription = zulaNoticeDescription;
    }

    public String getZulaNoticeHref() {
        return zulaNoticeHref;
    }

    public void setZulaNoticeHref(String zulaNoticeHref) {
        this.zulaNoticeHref = zulaNoticeHref;
    }

    public String getZulaNoticePlanTime() {
        return zulaNoticePlanTime;
    }

    public void setZulaNoticePlanTime(String zulaNoticePlanTime) {
        this.zulaNoticePlanTime = zulaNoticePlanTime;
    }

    public String getZulaNoticeDate() {
        return zulaNoticeDate;
    }

    public void setZulaNoticeDate(String zulaNoticeDate) {
        this.zulaNoticeDate = zulaNoticeDate;
    }
}
