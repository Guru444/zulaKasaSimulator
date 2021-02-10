package com.kasa.zulakasasimulator.service.userGamerService;

import com.kasa.zulakasasimulator.model.JSONResponse;
import com.kasa.zulakasasimulator.model.guruMarket.GuruMarket;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGamerCardDataResponse;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGamerDataResponse;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUserKuponResponse;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUsersKuponKod;
import com.kasa.zulakasasimulator.model.zulaNotice.ZulaNoticeList;
import com.kasa.zulakasasimulator.model.zulaNoticeCount.ZulaNoticeCountter;
import com.kasa.zulakasasimulator.model.zulaRegister.ZulaUserResponse;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaTotalOpenCase;
import com.kasa.zulakasasimulator.model.zulaVideoControl.ZulaVideoControl;
import com.kasa.zulakasasimulator.model.zulaVideoControl.ZulaVideoControlResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ZulaGameDataPostInterface {

    //Oyuncunun açtığı kasalardaki veriler insert ediliyor.
    @POST("zulaUserGameDetail")
    Call<ZulaGamerDataResponse> zulaGameDataInsert(@Body ZulaGameData zulaGameData);

    //Kullanıcıların xp, zp, guru paraların toplamını döndürmektedir.
    @Headers("Content-Type: application/json")
    @GET("zulaUsersCard/{zulaUserMail}")
    Call<ZulaGamerCardDataResponse> getDataUser(@Path("zulaUserMail") String zulaUserMail);

    //Kullanıcıların bilgileri ile beraber listelenmektedir.
    @GET("zulaGameUsers")
    Call<List<ZulaGameData>> getJSON();

    //Settings case limit
    @GET("settings/{option_group}")
    Call<List<ZulaSettings>> getSettings(@Path("option_group") String option_group);

    @GET("zulaOpenedCase")
    Call<List<ZulaTotalOpenCase>> getTotalCase();

    //Market ürünlerinin çekildiği call
    @GET("zulaGuruMarket")
    Call<List<GuruMarket>> getGuruMarketJSON();

    //Duyuruların çekildiği
    @GET("zulaNotice")
    Call<List<ZulaNoticeList>> getZulaNoticeJSON();

    @GET("zulaNoticeCounter")
    Call<List<ZulaNoticeCountter>> getZulaNoticeCountterJSON();

    //Kupon kodu insert
    @POST("zulaCouponUse")
    Call<ZulaGamerDataResponse> zulaUserKuponKodInsert(@Body ZulaUsersKuponKod zulaUsersKuponKod);

    //Kullanıcının kupon kodunu kullanmış mı kontrolü
    @Headers("Content-Type: application/json")
    @GET("zulaCouponControl/{zulaUserMail}/{zulaCouponName}")
    Call<ZulaUserKuponResponse> getUserKuponData(@Path("zulaUserMail") String zulaUserMail, @Path("zulaCouponName") String zulaCouponName);

    @Headers("Content-Type: application/json")
    @GET("zulaCouponDetail/{zulaUserCoupon}")
    Call<ZulaUserKuponResponse> getKuponDetail(@Path("zulaUserCoupon") String zulaUserCoupon);
}
