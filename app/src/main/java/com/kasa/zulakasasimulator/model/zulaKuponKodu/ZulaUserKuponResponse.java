package com.kasa.zulakasasimulator.model.zulaKuponKodu;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ZulaUserKuponResponse implements Serializable {

    @Nullable
    @SerializedName("zulaCouponName")
    private String zulaCouponName;
    @Nullable
    @SerializedName("couponZp")
    private String couponZp;
    @Nullable
    @SerializedName("couponXp")
    private String couponXp;
    @Nullable
    @SerializedName("couponGuru")
    private String couponGuru;
    @Nullable
    @SerializedName("couponPiece")
    private String couponPiece;

    /*@SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;

    public Boolean getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
*/

    @Nullable
    public String getZulaCouponName() { return zulaCouponName; }

    public void setZulaCouponName(@Nullable String zulaCouponName) { this.zulaCouponName = zulaCouponName; }

    @Nullable
    public String getCouponZp() { return couponZp; }

    public void setCouponZp(@Nullable String couponZp) { this.couponZp = couponZp; }

    @Nullable
    public String getCouponXp() { return couponXp; }

    public void setCouponXp(@Nullable String couponXp) { this.couponXp = couponXp; }

    @Nullable
    public String getCouponGuru() { return couponGuru; }

    public void setCouponGuru(@Nullable String couponGuru) { this.couponGuru = couponGuru; }

    @Nullable
    public String getCouponPiece() { return couponPiece; }

    public void setCouponPiece(@Nullable String couponPiece) { this.couponPiece = couponPiece; }
}
