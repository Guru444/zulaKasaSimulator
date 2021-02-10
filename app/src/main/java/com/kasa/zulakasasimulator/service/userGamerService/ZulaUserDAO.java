package com.kasa.zulakasasimulator.service.userGamerService;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUsersKuponKod;
import com.kasa.zulakasasimulator.model.zulaVideoControl.ZulaVideoControl;

import java.util.List;

@Dao
public interface ZulaUserDAO {

    //Video DB işlemleri
    @Insert
    public void addUserVideo(ZulaVideoControl zulaVideoControl);

    @Query("Select * from userVideoControl where zulaUserMail IN (:userMail)")
    public List<ZulaVideoControl> getZulaUsers(String userMail);

    @Query("Update userVideoControl set videoOneHour = :newTime where zulaUserMail =:email")
    public void updaterUserVideoTime(String newTime,String email);

    //Game DB işlemleri
    @Insert
    public void addZulaUserGameData(ZulaGameData zulaGameData);

    @Query("SELECT id,SUM(caseWinXP) as caseWinXP,SUM(caseWinZP) as caseWinZP, SUM(caseWinGURU) as caseWinGURU FROM zulaUsers where zulaUserMail=:userMail")
    public List<ZulaGameData> getZulaUsersData(String userMail);

    @Query("DELETE FROM zulaUsers")
    public void deleteZulaUserData();

    //Ödül Kodu işlemleri
    @Insert
    public void addZulaUserKuponKodu(ZulaUsersKuponKod zulaUsersKuponKod);

    @Query("Select * from zulaCouponUser")
    public List<ZulaUsersKuponKod> getCountCouponList();
}
