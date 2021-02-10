package com.kasa.zulakasasimulator.service.userGamerService;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUsersKuponKod;
import com.kasa.zulakasasimulator.model.zulaVideoControl.ZulaVideoControl;

@Database(entities = {ZulaVideoControl.class,ZulaGameData.class, ZulaUsersKuponKod.class}, version = 3)
public abstract class ZulaGameDatabase extends RoomDatabase {

    public abstract ZulaUserDAO zulaUserDAO();
}
