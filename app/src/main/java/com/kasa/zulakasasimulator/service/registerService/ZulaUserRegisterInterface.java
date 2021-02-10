package com.kasa.zulakasasimulator.service.registerService;

import com.kasa.zulakasasimulator.model.zulaRegister.ZulaGameUsersInfo;
import com.kasa.zulakasasimulator.model.zulaRegister.ZulaUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ZulaUserRegisterInterface {

    @POST("newUser")
    Call<ZulaUserResponse> zulaUserRegisterMethod(@Body  ZulaGameUsersInfo zulaGameUsersInfo);
}
