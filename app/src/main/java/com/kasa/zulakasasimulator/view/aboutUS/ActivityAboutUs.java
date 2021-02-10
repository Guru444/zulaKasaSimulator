package com.kasa.zulakasasimulator.view.aboutUS;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Element;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.MainActivity;
import com.kasa.zulakasasimulator.view.SplashActivity;
import com.kasa.zulakasasimulator.view.zulaGameUserList.kasaOyunuZula;

import java.util.Calendar;
import java.util.List;

import mehdi.sakout.aboutpage.AboutPage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityAboutUs extends AppCompatActivity {
    Boolean appClick = true;
    Boolean gameClick = true;
    Boolean gamePlayClick = true;
    private ProgressDialog progressDialog;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView versionName = (TextView) findViewById(R.id.versionName);
        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            versionName.setText("V"+version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView discord = (TextView) findViewById(R.id.discord);
        TextView instagram = (TextView) findViewById(R.id.instagram);

        discord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog = new ProgressDialog(ActivityAboutUs.this);
                progressDialog.setMessage("Discord adresi açılıyor...");
                progressDialog.show();

                ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("discord");
                call.enqueue(new Callback<List<ZulaSettings>>() {
                    @Override
                    public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                        List<ZulaSettings> zulaSettings = response.body();
                        if (zulaSettings != null) {
                            Intent viewIntent =
                                    new Intent("android.intent.action.VIEW",
                                            Uri.parse(zulaSettings.get(0).getOption_value()));
                            startActivity(viewIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Sosyal Medya Sunucu Hatası", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                    }
                });
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://www.instagram.com/guru.ajans/"));
                startActivity(viewIntent);
            }
        });

        TextView app = (TextView) findViewById(R.id.app);
        TextView game = (TextView) findViewById(R.id.game);
        TextView gamePlay = (TextView) findViewById(R.id.gamePlay);

        TextView appDesc = (TextView) findViewById(R.id.appDesc);
        TextView gameDesc = (TextView) findViewById(R.id.gameDesc);
        TextView gamePlayDesc = (TextView) findViewById(R.id.gamePlayDesc);

        app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appClick){
                    appDesc.setVisibility(View.VISIBLE);
                    app.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.uparrow,0);
                    appClick = false;
                }
                else {
                    appDesc.setVisibility(View.GONE);
                    app.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.downarrow,0);
                    appClick = true;
                }
            }
        });
        game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameClick){
                    gameDesc.setVisibility(View.VISIBLE);
                    game.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.uparrow,0);
                    gameClick = false;
                }
                else {
                    gameDesc.setVisibility(View.GONE);
                    game.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.downarrow,0);
                    gameClick = true;
                }
            }
        });
        gamePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamePlayClick){
                    gamePlayDesc.setVisibility(View.VISIBLE);
                    gamePlay.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.uparrow,0);
                    gamePlayClick = false;
                }
                else {
                    gamePlayDesc.setVisibility(View.GONE);
                    gamePlay.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.downarrow,0);
                    gamePlayClick = true;
                }
            }
        });


    }
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}