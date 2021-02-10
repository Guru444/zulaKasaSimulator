package com.kasa.zulakasasimulator.model.zulaGamer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "zulaUsers")
public class ZulaGameData {
    public static final String ID = "id";


    @ColumnInfo(name = ID)
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "caseWinXP")
    public Long caseWinXP;
    @ColumnInfo(name = "caseWinZP")
    public Long caseWinZP;
    @ColumnInfo(name = "caseWinGURU")
    public Long caseWinGURU;
    @ColumnInfo(name = "zulaUserMail")
    public String zulaUserMail;
    @ColumnInfo(name = "zulaUserName")
    public String zulaUserName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCaseWinXP() {
        return caseWinXP;
    }

    public void setCaseWinXP(Long caseWinXP) {
        this.caseWinXP = caseWinXP;
    }

    public Long getCaseWinZP() {
        return caseWinZP;
    }

    public void setCaseWinZP(Long caseWinZP) {
        this.caseWinZP = caseWinZP;
    }

    public Long getCaseWinGURU() {
        return caseWinGURU;
    }

    public void setCaseWinGURU(Long caseWinGURU) {
        this.caseWinGURU = caseWinGURU;
    }

    public String getZulaUserMail() {
        return zulaUserMail;
    }

    public void setZulaUserMail(String zulaUserMail) {
        this.zulaUserMail = zulaUserMail;
    }

    public String getZulaUserName() {
        return zulaUserName;
    }

    public void setZulaUserName(String zulaUserName) {
        this.zulaUserName = zulaUserName;
    }

    public ZulaGameData(Long caseWinXP, Long caseWinZP, Long caseWinGURU, String zulaUserMail) {
        this.caseWinXP = caseWinXP;
        this.caseWinZP = caseWinZP;
        this.caseWinGURU = caseWinGURU;
        this.zulaUserMail = zulaUserMail;
    }
}
