package com.kasa.zulakasasimulator.model.zulaKuponKodu;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "zulaCouponUser")
public class ZulaUsersKuponKod {

    public static final String ID = "id";


    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "zulaUserMail")
    public String zulaUserMail;
    @ColumnInfo(name = "zulaUserCoupon")
    public String zulaUserCoupon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZulaUserMail() {
        return zulaUserMail;
    }

    public void setZulaUserMail(String zulaUserMail) {
        this.zulaUserMail = zulaUserMail;
    }

    public String getZulaUserCoupon() {
        return zulaUserCoupon;
    }

    public void setZulaUserCoupon(String zulaUserCoupon) {
        this.zulaUserCoupon = zulaUserCoupon;
    }

    public ZulaUsersKuponKod(String zulaUserMail, String zulaUserCoupon) {
        this.zulaUserMail = zulaUserMail;
        this.zulaUserCoupon = zulaUserCoupon;
    }
}
