package com.kasa.zulakasasimulator.model.zulaNoticeCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ZulaNoticeCountter {
    @SerializedName("noticeCount")
    @Expose
    private String noticeCount;

    public String getNoticeCount() {
        return noticeCount;
    }

    public void setNoticeCount(String noticeCount) {
        this.noticeCount = noticeCount;
    }
}
