package com.kasa.zulakasasimulator.view.zulaGameUserList;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGamerCardDataResponse;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGamerDataResponse;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUserKuponResponse;
import com.kasa.zulakasasimulator.model.zulaKuponKodu.ZulaUsersKuponKod;
import com.kasa.zulakasasimulator.model.zulaNoticeCount.ZulaNoticeCountter;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaTotalOpenCase;
import com.kasa.zulakasasimulator.model.zulaVideoControl.ZulaVideoControl;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDatabase;
import com.kasa.zulakasasimulator.view.MainActivity;
import com.kasa.zulakasasimulator.view.googlePayView.GuruParasiPayment;
import com.kasa.zulakasasimulator.view.kasaOpenView.kasaAcilimiMagaza;
import com.kasa.zulakasasimulator.view.loginView.RegisterFragment;
import com.kasa.zulakasasimulator.view.zulaNoticeActivity.ZulaNoticeListActivity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class kasaOyunuZula extends AppCompatActivity implements RewardedVideoAdListener {

    private FirebaseAuth firebaseAuth;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    private static final String AD_UNIT_ID = "ca-app-pub-6370444038396282/1391448078";
    private static final String APP_ID = "ca-app-pub-6370444038396282~3250983402";
    private RewardedVideoAd mRewardedVideoAd;
    private ProgressDialog progressDialog;

    public Long kazanılanGuruParasi = 0L;
    public Long kazanilanXP = 0L;
    public Long kazanilanZP = 100L;

    public Long kazanGuru;
    public Long kazanZP;
    public Long kazanXP;

    public Long userXPTotal;
    public Long userZPTotal;
    public Long userGuruTotal;
    TextView xp, zp, guru;

    String typeKontrol;
    Animation animation;
    Animation animationOpenKasa;
    FirebaseUser user;
    String userUuid = "Merhaba";
    public MediaPlayer muzikcalar;
    public MediaPlayer gameSound;

    String dayDifference, dayDiffenceDay, dayMinute;

    //Room Database
    public static ZulaGameDatabase zulaGameDatabase;


    String[] kotuCikanlarCekilis =  {"15000 XP", "20000 XP", "25000 XP", "2500 GURU", "25000 XP", "30000 XP", "25000 XP", "25000 XP", "25000 XP", "15000 XP", "15000 ZP", "10000 ZP", "7500 ZP", "1000 ZP", "5000 XP", "7500 XP", "7500 XP", "10000 XP", "2500 XP", "20000 XP", "10000 XP", "7500 XP", "10000 XP", "10000 ZP", "10000 ZP", "7500 XP", "7500 XP", "10000 XP", "25000 XP", "10000 XP"};
    String[] ortaCikanlarCekilis = {"25000 XP", "10000 XP", "15000 XP", "15000 XP", "25000 XP", "15000 ZP", "15000 ZP", "15000 XP", "10000 ZP", "25000 XP", "25000 XP", "25000 XP", "25000 ZP", "25000 ZP", "25000 ZP", "25000 XP", "25000 XP", "25000 XP"};
    String[] iyiCikanlarCekilis ={"100000 XP", "50000 XP", "100000 XP", "50000 ZP", "75000 XP", "50000 XP", "25000 ZP", "100000 XP", "50000 ZP", "10000 ZP", "10000 XP", "25000 XP", "250000 XP","30000 ZP","50000 XP","25000 XP","25000 XP","50000 XP","50000 XP","25000 XP","25000 XP","100000 XP","150000 XP"};

    String[] kotuCikanlarAgirKasa = {"100 ZP", "150 ZP", "100 ZP", "250 XP", "250 ZP", "150 ZP", "250 ZP", "100 ZP", "500 ZP", "100 ZP", "250 ZP", "150 ZP", "150 ZP", "100 ZP", "750 ZP", "250 ZP", "250 ZP", "100 ZP", "100 ZP", "250 ZP", "100 ZP", "100 ZP", "250 ZP", "150 ZP", "100 ZP", "150 ZP", "250 ZP", "100 ZP", "250 ZP", "250 ZP"};
    String[] ortaCikanlarAgirKasa = {"100 ZP", "150 ZP", "250 ZP", "250 ZP", "500 ZP", "500 ZP", "500 ZP", "500 ZP", "250 ZP","150 ZP", "250 ZP", "100 ZP", "250 ZP","750 ZP", "250 ZP", "250 ZP","250 ZP", "500 XP", "100 ZP", "250 ZP", "150 ZP"};
    String[] iyiCikanlarAgirKasa =  {"500 ZP", "750 ZP", "750 ZP", "500 ZP", "250 ZP", "750 ZP", "750 ZP", "1000 ZP", "250 ZP", "250 ZP", "500 ZP", "250 ZP"};

    String[] kotuCikanlarAntikaKasa = {"250 ZP", "100 ZP", "250 XP", "250 ZP", "250 XP", "500 XP", "750 XP", "100 XP", "500 XP", "500 XP", "750 ZP", "500 ZP", "250 ZP", "100 ZP", "150 XP", "150 ZP","250 ZP","250 XP","100 ZP","100 ZP", "250 ZP", "500 XP", "500 XP", "500 ZP", "500 XP", "500 XP", "250 XP", "100 XP", "250 ZP", "250 XP", "100 ZP", "250 ZP", "250 XP", "250 ZP"};
    String[] ortaCikanlarAntikaKasa = {"250 ZP", "1000 ZP", "750 XP", "750 ZP", "250 ZP", "500 XP", "250 ZP", "500 XP", "500 ZP", "750 XP","250 ZP","500 XP", "1000 XP", "100 XP", "250 ZP", "500 XP", "250 ZP", "500 XP", "250 XP", "250 ZP"};
    String[] iyiCikanlarAntikaKasa = {"1000 ZP", "1000 XP", "1000 ZP", "1000 ZP", "1000 ZP", "1000 ZP", "1000 ZP", "1000 ZP", "1000 XP", "2500 XP", "1000 XP", "1000 XP"};

    String[] kotuCikanlarCuruKasa = {"500 ZP", "250 ZP", "1000 XP", "500 ZP", "750 ZP", "500 XP", "750 XP", "1000 XP", "500 XP", "500 ZP", "500 ZP", "750 ZP", "750 ZP", "100 ZP", "500 XP", "750 ZP", "250 ZP", "750 XP", "100 XP", "250 XP", "500 ZP", "750 XP", "750 XP", "100 ZP", "250 ZP", "100 ZP", "750 ZP", "250 ZP", "750 XP", "150 ZP"};
    String[] ortaCikanlarCuruKasa = {"750 XP", "1000 XP", "1000 ZP", "500 XP", "750 ZP", "500 XP", "750 XP", "750 XP", "750 ZP", "500 ZP", "2500 XP", "2500 ZP", "2500 XP", "500 XP", "500 ZP", "500 ZP", "750 XP", "750 ZP"};
    String[] iyiCikanlarCuruKasa = {"2500 XP", "2500 XP", "5000 XP", "1000 ZP", "2500 ZP", "1000 XP", "750 XP", "1500 XP", "2500 XP", "1000 XP", "2500 XP", "1000 ZP"};

    String[] kotuCikanlarDemirKasa = {"1000 ZP", "1500 ZP", "1500 XP", "2500 XP", "1500 ZP", "1000 ZP", "2000 ZP", "2500 XP", "2000 ZP", "1000 ZP", "2500 ZP", "1500 XP", "2500 ZP", "750 ZP", "1500 XP", "2500 ZP", "1500 ZP", "1000 XP", "1500 XP", "2500 XP", "10000 XP", "7500 XP", "1000 XP", "1000 XP", "1000 ZP", "750 ZP", "1000 ZP", "1000 ZP", "2500 XP", "2000 ZP"};
    String[] ortaCikanlarDemirKasa =  {"2500 XP", "5000 XP", "5000 ZP", "2500 ZP", "2500 XP", "2500 XP", "2500 ZP", "1000 XP", "1500 XP", "1000 ZP", "5000 XP", "2500 XP", "2500 XP", "500 XP", "5000 ZP", "2500 ZP", "2500 XP", "5000 ZP"};
    String[] iyiCikanlarDemirKasa = {"10000 XP", "10000 XP", "10000 ZP", "7500 XP", "10000 XP", "15000 XP", "10000 XP", "10000 XP", "10000 XP", "10000 ZP", "10000 XP", "10000 ZP","10000 XP","10000 XP", "25000 XP", "10000 XP","50000 XP"};

    LinearLayout appBarLayout;
    String kontrolWatch;
    String FinalDate;
    String kuponText;
    Long sayac = 0L;
    ImageView guruKasasi, agirKasa, antikKasa, curukKasa, demirKasa;
    long lastTimeGuru = 0;
    Boolean openKontrolLinearLayout = true;
    String kasadanCikan, message;
    BottomSheetDialog bottomSheetDialogOpenKasa;
    FrameLayout goNotiList;
    TextView cart_badge, infoOpenCase;
    int kasaGeriKontrol = 0;
    private ProgressBar progreso;
    InterstitialAd mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_oyunu_zula);

        try {
            Bundle extras = getIntent().getExtras();
            kasaGeriKontrol = extras.getInt("kasaGeriKontrol");
            Log.i("kontrol", Integer.toString(kasaGeriKontrol));
        } catch (Exception e) {
        }

        ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
        Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("reward_status");
        call.enqueue(new Callback<List<ZulaSettings>>() {
            @Override
            public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                List<ZulaSettings> zulaSettings = response.body();
                if (zulaSettings != null) {
                    if (Integer.parseInt(zulaSettings.get(0).getOption_value()) == 1) {
                        if (kasaGeriKontrol == 1) {
                            inrequestadd();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Oyun Bakım hatası", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

            }
        });

        //Room DataBase builder
        zulaGameDatabase = Room.databaseBuilder(getApplicationContext(), ZulaGameDatabase.class, "zulaGame").allowMainThreadQueries().build();

        //Ödüllü reklam
        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //Kullanıcı login yapmamışsa
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, RegisterFragment.class));
        }

        //Kullanici mail adress -> primary Key
        user = firebaseAuth.getCurrentUser();
        userUuid = user.getEmail();
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animationOpenKasa = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);

        muzikcalar = MediaPlayer.create(kasaOyunuZula.this, R.raw.ses);
        gameSound = MediaPlayer.create(kasaOyunuZula.this, R.raw.gamesound);

        //Oyun Kasalari
        guruKasasi = findViewById(R.id.guruKasasi);
        agirKasa = findViewById(R.id.agirKasa);
        antikKasa = findViewById(R.id.antikKasa);
        curukKasa = (ImageView) findViewById(R.id.curukKasa);
        demirKasa = (ImageView) findViewById(R.id.demirKasa);
        appBarLayout = (LinearLayout) findViewById(R.id.userDataDetail);
        appBarLayout.setVisibility(View.VISIBLE);

        goNotiList = (FrameLayout) findViewById(R.id.goNotifiList);
        cart_badge = (TextView) findViewById(R.id.cart_badge);
        cart_badge.startAnimation(animationOpenKasa);


        getNotificationCount();

        goNotiList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(kasaOyunuZula.this, ZulaNoticeListActivity.class);
                startActivity(intent);
            }
        });

        TextView menuGame = (TextView) findViewById(R.id.menuGame);

        TextView openCard = (TextView) findViewById(R.id.info);
        zp = (TextView) findViewById(R.id.zp);
        xp = (TextView) findViewById(R.id.xp);
        guru = (TextView) findViewById(R.id.guru);
        infoOpenCase = (TextView) findViewById(R.id.infoOpenCase);
        infoOpenCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogTotalCase(message);
            }
        });

        //Menu BottomSheet
        menuGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuDialog();
            }
        });

        getZulaUserDetail();
        getTotolOpenCase("case");

        guruKasasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableOrDisableButtons(false);
                if (userGuruTotal >= 2500) {
                    if (System.currentTimeMillis() > lastTimeGuru + 800) {
                        lastTimeGuru = System.currentTimeMillis();
                        guruKasasi.startAnimation(animation);
                        rastageleZulaArac("GURU KASASI", kotuCikanlarCekilis, ortaCikanlarCekilis, iyiCikanlarCekilis, kotuCikanlarCekilis.length, ortaCikanlarCekilis.length, iyiCikanlarCekilis.length, -2500L, true);
                    } else {
                        Toast.makeText(getBaseContext(), "Çok hızlısın Lütfen biraz yavaşla..",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openErrorBottomSheet("Yeterince GURU parası bulunmamaktadır.");
                    enableOrDisableButtons(true);
                }

                //alertDialog("GURU KASASI", "Yeterince GURU parası bulunmamaktadır.");
            }
        });
        agirKasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableOrDisableButtons(false);
                if (userZPTotal >= 100L) {
                    if (System.currentTimeMillis() > lastTimeGuru + 800) {
                        lastTimeGuru = System.currentTimeMillis();
                        agirKasa.startAnimation(animation);
                        rastageleZulaArac("AĞIR KASA", kotuCikanlarAgirKasa, ortaCikanlarAgirKasa, iyiCikanlarAgirKasa, kotuCikanlarAgirKasa.length, ortaCikanlarAgirKasa.length, iyiCikanlarAgirKasa.length, -100L, false);
                    } else {
                        Toast.makeText(getBaseContext(), "Çok hızlısın Lütfen biraz yavaşla...",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openErrorBottomSheet("Yeterince ZP bulunmamaktadır.");
                    enableOrDisableButtons(true);
                }
                // alertDialog("AĞIR KASA", "Yeterince ZP bulunmamaktadır.");
            }
        });
        antikKasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableOrDisableButtons(false);
                if (userZPTotal >= 250L) {
                    if (System.currentTimeMillis() > lastTimeGuru + 800) {
                        lastTimeGuru = System.currentTimeMillis();
                        antikKasa.startAnimation(animation);
                        rastageleZulaArac("ANTİKA KASA", kotuCikanlarAntikaKasa, ortaCikanlarAntikaKasa, iyiCikanlarAntikaKasa, kotuCikanlarAntikaKasa.length, ortaCikanlarAntikaKasa.length, iyiCikanlarAntikaKasa.length, -250L, false);
                    } else {
                        Toast.makeText(getBaseContext(), "Çok hızlısın Lütfen biraz yavaşla...",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openErrorBottomSheet("Yeterince ZP bulunmamaktadır.");
                    enableOrDisableButtons(true);
                }
                //alertDialog("ANTİK KASA", "Yeterince ZP bulunmamaktadır.");
            }
        });
        curukKasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableOrDisableButtons(false);
                if (userZPTotal >= 500L) {
                    if (System.currentTimeMillis() > lastTimeGuru + 800) {
                        lastTimeGuru = System.currentTimeMillis();
                        curukKasa.startAnimation(animation);
                        rastageleZulaArac("ÇÜRÜK KASA", kotuCikanlarCuruKasa, ortaCikanlarCuruKasa, iyiCikanlarCuruKasa, kotuCikanlarCuruKasa.length, ortaCikanlarCuruKasa.length, iyiCikanlarCuruKasa.length, -500L, false);
                    } else {
                        Toast.makeText(getBaseContext(), "Çok hızlısın Lütfen biraz yavaşla...",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openErrorBottomSheet("Yeterince ZP bulunmamaktadır.");
                    enableOrDisableButtons(true);
                }
                //alertDialog("ÇÜRÜK KASA", "Yeterince ZP bulunmamaktadır.");
            }
        });
        demirKasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableOrDisableButtons(false);
                if (userZPTotal >= 1000L) {
                    if (System.currentTimeMillis() > lastTimeGuru + 800) {
                        lastTimeGuru = System.currentTimeMillis();
                        demirKasa.startAnimation(animation);
                        rastageleZulaArac("DEMİR KASA", kotuCikanlarDemirKasa, ortaCikanlarDemirKasa, iyiCikanlarDemirKasa, kotuCikanlarDemirKasa.length, ortaCikanlarDemirKasa.length, iyiCikanlarDemirKasa.length, -1000L, false);
                    } else {
                        Toast.makeText(getBaseContext(), "Çok hızlısın Lütfen biraz yavaşla...",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    openErrorBottomSheet("Yeterince ZP bulunmamaktadır.");
                    enableOrDisableButtons(true);
                }
                //alertDialog("DEMİR KASA", "Yeterince ZP bulunmamaktadır.");
            }
        });

        //Kullanıcının istatiksel bilgilerinin çekildiği kısım
        openCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                loadRewardedVideoAd();

                List<ZulaVideoControl> zulaVideoControls = kasaOyunuZula.zulaGameDatabase.zulaUserDAO().getZulaUsers(user.getEmail());

                Date tarih = new Date();
                SimpleDateFormat bugun = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

                ZulaVideoControl zulaVideoControl = new ZulaVideoControl(bugun.format(tarih), bugun.format(tarih), bugun.format(tarih), user.getEmail());
                AlertDialog.Builder builder = new AlertDialog.Builder(kasaOyunuZula.this);

                builder.setTitle("VİDEO İZLEME");
                try {
                    for (ZulaVideoControl zulaVideo : zulaVideoControls) {
                        kontrolWatch = zulaVideo.getVideoOneHour();
                    }
                    String CurrentDate = kontrolWatch; //Son video izleme süresini aldık.
                    FinalDate = bugun.format(tarih); // Bugünün tarihi
                    Date date1;
                    Date date2;
                    SimpleDateFormat dates = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

                    date1 = dates.parse(CurrentDate);
                    date2 = dates.parse(FinalDate);

                    long difference = Math.abs(date1.getTime() - date2.getTime());
                    Log.e("HERE", "HERE: " + difference);
                    //long differenceDatesMouth = difference / (30 *24 * 60 *60 * 1000);
                    long differenceDatesMin = difference / (60 * 1000);//Dakikası
                    long differenceDatesMinute = difference / (60 * 60 * 1000);//Saat
                    long differenceDates = difference / (24 * 60 * 60 * 1000);//gün

                    // dayMouth =  Long.toString(differenceDatesMouth);
                    dayDifference = Long.toString(differenceDatesMinute);
                    dayDiffenceDay = Long.toString(differenceDates);

                    if (differenceDatesMin < 60) {
                        dayMinute = Long.toString((differenceDatesMin));
                    } else if (differenceDatesMin > 60) {
                        dayMinute = Long.toString((differenceDatesMin) % 60);
                    }

                    Log.e("Gün", "şimdi: " + dayDifference);

                } catch (Exception exception) {
                    Log.e("Hata", "çevirme " + exception);
                }

                if (dayDiffenceDay == "0" && dayDifference == "0") {
                    builder.setMessage("Kalan süre : " + (60 - Integer.parseInt(dayMinute)) + " Dakika");
                } else if (dayDiffenceDay == "0") {
                    builder.setMessage("En son video izleme " + dayDifference + " Saat " + dayMinute + " Dakika geçti");
                } else {
                    for (ZulaVideoControl zulaVideo : zulaVideoControls) {
                        kontrolWatch = zulaVideo.getVideoOneHour();
                    }
                    if (kontrolWatch == null) {
                        builder.setMessage("Video izlemeye hazırsın");
                    }
                }
                //builder.setMessage(dayDiffenceDay + " gün " + dayDifference + " Saat " + dayMinute + " Dakika geçti");

                ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("video_reward");
                call.enqueue(new Callback<List<ZulaSettings>>() {
                    @Override
                    public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                        List<ZulaSettings> zulaSettings = response.body();
                        if (zulaSettings != null) {
                            if (Integer.parseInt(zulaSettings.get(2).getOption_value()) == 1){
                                builder.setPositiveButton("ZP KAZAN", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        loadRewardedVideoAd();
                                        for (ZulaVideoControl zulaVideo : zulaVideoControls) {
                                            kontrolWatch = zulaVideo.getVideoOneHour();
                                        }
                                        if (kontrolWatch != null) {
                                            if (Integer.parseInt(dayMinute) >= 1 && Integer.parseInt(dayDifference) >= 1 || Integer.parseInt(dayDiffenceDay) >= 1) {
                                                if (mRewardedVideoAd.isLoaded()) {
                                                    mRewardedVideoAd.show();
                                                }
                                                kasaOyunuZula.zulaGameDatabase.zulaUserDAO().updaterUserVideoTime(bugun.format(tarih), user.getEmail());
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Video izlemen için süreni beklemelisin. Kalan süre :" + (60 - Integer.parseInt(dayMinute)) + " Dakika", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            kasaOyunuZula.zulaGameDatabase.zulaUserDAO().addUserVideo(zulaVideoControl);
                                            //Toast.makeText(getApplicationContext(), "Room DB Kaydetme işlemi başarılı.", Toast.LENGTH_SHORT).show();

                                            if (mRewardedVideoAd.isLoaded()) {
                                                mRewardedVideoAd.show();
                                            }
                                        }
                                    }
                                });
                                builder.show();
                            }
                            else
                                openDialogBakım(zulaSettings.get(2).getOption_message());

                        }
                    }

                    @Override
                    public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hata ödül eklenemedi." + t.toString(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });
    }
    private void openDialogBakım(String message) {
        View view = getLayoutInflater().inflate(R.layout.game_maintenance_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.messsage);
        messsage.setText(message);
        // TextView gallery_sel = (TextView) view.findViewById(R.id.gallery);
        bottomSheetDialog.show();
    }

    public void inrequestadd() {
        mInterstitial = new InterstitialAd(kasaOyunuZula.this);
        mInterstitial.setAdUnitId("ca-app-pub-6370444038396282/8812277754");
        mInterstitial.loadAd(new AdRequest.Builder().build());
        mInterstitial.setAdListener(new AdListener() {

            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitial.isLoaded()) {
                    mInterstitial.show();
                }
            }

        });
    }

    private void openErrorBottomSheet(String mesaj) {
        View view = getLayoutInflater().inflate(R.layout.not_have_guru_money, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView guruTotalMoney = (TextView) view.findViewById(R.id.guruTotalMoney);
        guruTotalMoney.setText(mesaj);
        //bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }

    private void openGameSettings() {
        View view = getLayoutInflater().inflate(R.layout.game_page_settings, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView panelKapat = (TextView) view.findViewById(R.id.panelKapat);
        TextView volumeKapat = (TextView) view.findViewById(R.id.volumeKapat);
        panelKapat.setText("Panel Gizle");
        volumeKapat.setText("Sesi Aç");
        volumeKapat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.volumeoff, 0, 0);
        panelKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openKontrolLinearLayout) {
                    panelKapat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.hide, 0, 0);
                    panelKapat.setText("Panel Görüntüle");
                    appBarLayout.setVisibility(View.GONE);
                    openKontrolLinearLayout = false;
                } else {
                    panelKapat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.thumbup, 0, 0);
                    panelKapat.setText("Panel Gizle");
                    appBarLayout.setVisibility(View.VISIBLE);
                    openKontrolLinearLayout = true;
                }
            }
        });
        volumeKapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameSound.isPlaying()) {
                    gameSound.start();
                    gameSound.setLooping(true);
                    volumeKapat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.volume, 0, 0);
                    volumeKapat.setText("Sesi Kapat");
                } else {
                    gameSound.pause();
                    volumeKapat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.volumeoff, 0, 0);
                    volumeKapat.setText("Sesi Aç");
                    //gameSound.reset();
                }
            }
        });
        bottomSheetDialog.show();
    }

    private void openMenuDialog() {
        View view = getLayoutInflater().inflate(R.layout.zula_game_menu, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView kuponKodu = (TextView) view.findViewById(R.id.kodInsert);
        TextView payment = (TextView) view.findViewById(R.id.payment);
        TextView openUserList = (TextView) view.findViewById(R.id.openUserList);
        TextView panelGizle = (TextView) view.findViewById(R.id.panelGizle);

        panelGizle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameSettings();
            }
        });

        kuponKodu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(kasaOyunuZula.this);
                progressDialog.setMessage("Market Hazırlanıyor..");
                progressDialog.show();

                ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("gurumarket");
                call.enqueue(new Callback<List<ZulaSettings>>() {
                    @Override
                    public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                        List<ZulaSettings> zulaSettings = response.body();
                        if (zulaSettings != null) {
                            if (Integer.parseInt(zulaSettings.get(0).getOption_value()) == 1) {
                                Intent intent = new Intent(kasaOyunuZula.this, GuruParasiPayment.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade, R.anim.slide);
                                progressDialog.dismiss();
                            } else {
                                openMarketStatus(zulaSettings.get(0).getOption_message());
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Market Bakım hatası", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                    }
                });


            }
        });
        openUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(kasaOyunuZula.this, UserListActivity.class);
                intent.putExtra("userMail", user.getEmail());
                startActivity(intent);
            }
        });
        bottomSheetDialog.show();
    }

    private void openMarketStatus(String mesaj) {
        View view = getLayoutInflater().inflate(R.layout.not_have_guru_money, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView guruTotalMoney = (TextView) view.findViewById(R.id.guruTotalMoney);
        guruTotalMoney.setText(mesaj);
        //bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
    }

    private void openDialog() {
        View view = getLayoutInflater().inflate(R.layout.kupon_kodu_form, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        EditText textKupon = (EditText) view.findViewById(R.id.kuponKodu);
        Button btn_kod = (Button) view.findViewById(R.id.btn_kod);
        btn_kod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(kasaOyunuZula.this);
                progressDialog.setMessage("Kod kontrol ediliyor..");
                progressDialog.show();
                kuponText = textKupon.getText().toString();
                ZulaUsersKuponKod zulaUsersKuponKod = new ZulaUsersKuponKod(user.getEmail(), kuponText);
                ZulaGameDataPostInterface zulaUserKuponDataKontrol = retrofit().create(ZulaGameDataPostInterface.class);
                Call<ZulaUserKuponResponse> call = zulaUserKuponDataKontrol.getKuponDetail(kuponText);
                call.enqueue(new Callback<ZulaUserKuponResponse>() {
                    @Override
                    public void onResponse(Call<ZulaUserKuponResponse> call, Response<ZulaUserKuponResponse> response) {
                        ZulaUserKuponResponse zulaUserKuponResponse = response.body();
                        if (zulaUserKuponResponse != null) {
                            ZulaGameDataPostInterface zulaUserKuponKontrol = retrofit().create(ZulaGameDataPostInterface.class);
                            Call<ZulaUserKuponResponse> callkupon = zulaUserKuponKontrol.getUserKuponData(user.getEmail(), kuponText);
                            callkupon.enqueue(new Callback<ZulaUserKuponResponse>() {
                                @Override
                                public void onResponse(Call<ZulaUserKuponResponse> call, Response<ZulaUserKuponResponse> response) {
                                    ZulaUserKuponResponse zulaUserKuponResponse1 = response.body();
                                    if (zulaUserKuponResponse1 != null ) {
                                        if (zulaUserKuponResponse1.getZulaCouponName() != null) {
                                            Toast.makeText(getApplicationContext(), "Bu kodu daha önceden kullanmıştın.", Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                        else {
                                            if(zulaUserKuponResponse != null && zulaUserKuponResponse.getCouponPiece() !=null){
                                                List<ZulaUsersKuponKod> zulaUsersKuponKods = kasaOyunuZula.zulaGameDatabase.zulaUserDAO().getCountCouponList();
                                                for (ZulaUsersKuponKod zulaUsersKupon : zulaUsersKuponKods) {
                                                    sayac++;
                                                }
                                                if (sayac < Long.parseLong(zulaUserKuponResponse.getCouponPiece())) {
                                                    progressDialog.dismiss();
                                                    insertKuponKodu(user.getEmail(), kuponText, zulaUsersKuponKod, Long.parseLong(zulaUserKuponResponse.getCouponXp()), Long.parseLong(zulaUserKuponResponse.getCouponZp()), Long.parseLong(zulaUserKuponResponse.getCouponGuru()));
                                                } else{
                                                    Toast.makeText(getApplicationContext(), "Bu kuponun kullanım sayısı dolmuştur.", Toast.LENGTH_LONG).show();
                                                    progressDialog.dismiss();
                                                }
                                                progressDialog.dismiss();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "Hatalı ödül kodu girdiniz.", Toast.LENGTH_LONG).show();
                                                progressDialog.dismiss();
                                            }


                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Hatalı ödül kodu girdiniz.", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }


                                }
                                @Override
                                public void onFailure(Call<ZulaUserKuponResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Sunucu Hatası, Tekrar deneyin", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            });
                        } else{
                            Toast.makeText(getApplicationContext(), "Hatalı ödül kodu girdiniz.", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ZulaUserKuponResponse> call, Throwable t) {

                    }
                });
            }
        });

        bottomSheetDialog.show();
    }

    public void insertKuponKodu(String userMail, String kuponText, ZulaUsersKuponKod zulaUsersKuponKod, Long caseWinXP, Long caseWinZP, Long caseWinGURU) {
        progressDialog = new ProgressDialog(kasaOyunuZula.this);
        progressDialog.setMessage("Kontrol ediliyor..");
        progressDialog.show();
        ZulaGameDataPostInterface zulaUserKuponDataInsert = retrofit().create(ZulaGameDataPostInterface.class);
        Call<ZulaGamerDataResponse> call = zulaUserKuponDataInsert.zulaUserKuponKodInsert(zulaUsersKuponKod);
        call.enqueue(new Callback<ZulaGamerDataResponse>() {
            @Override
            public void onResponse(Call<ZulaGamerDataResponse> call, Response<ZulaGamerDataResponse> response) {
                ZulaGamerDataResponse zulaGamerDataResponse = response.body();
                if (zulaGamerDataResponse != null && zulaGamerDataResponse.getStatus()) {

                    //Toast.makeText(getApplicationContext(), "Kod eklendi", Toast.LENGTH_SHORT).show();

                    insertDataAPI(caseWinXP, caseWinZP, caseWinGURU, userMail, 0L, false);
                    kasaOyunuZula.zulaGameDatabase.zulaUserDAO().addZulaUserKuponKodu(zulaUsersKuponKod);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Servis hatalı kupon kodu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertDataAPI(Long caseWinXP, Long caseWinZP, Long caseWinGURU, String userMail, Long kasDegeri, Boolean kasaGuruMu) {
        ZulaGameData zulaGameData = new ZulaGameData(caseWinXP, caseWinZP, caseWinGURU, userMail);
        //Room Database ekledik.
        //kasaOyunuZula.zulaGameDatabase.zulaUserDAO().deleteZulaUserData();
        //Toast.makeText(getApplicationContext(), "Room table silindi.", Toast.LENGTH_SHORT).show();

        //Url e interface üzerinden EXT e istek yolladık.
        ZulaGameDataPostInterface zulaUserGameData = retrofit().create(ZulaGameDataPostInterface.class);
        Call<ZulaGamerDataResponse> call = zulaUserGameData.zulaGameDataInsert(zulaGameData);
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(kasaOyunuZula.this);

        progressDoalog.setMax(100);
        progressDoalog.setMessage("Para ekleniyor");
        progressDoalog.setTitle("CÜZDANIM");
        Drawable draw = getResources().getDrawable(R.drawable.custom_progressbar);
        progressDoalog.setProgressDrawable(draw);
        progressDoalog.setCancelable(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // show it
        progressDoalog.show();
        progressDoalog.setProgress(20);
        progressDoalog.setProgress(40);
        call.enqueue(new Callback<ZulaGamerDataResponse>() {
            @Override
            public void onResponse(Call<ZulaGamerDataResponse> call, Response<ZulaGamerDataResponse> response) {
                ZulaGamerDataResponse zulaGamerDataResponse = response.body();

                if (zulaGamerDataResponse != null && zulaGamerDataResponse.getStatus()) {
                    //Toast.makeText(getApplicationContext(), zulaGamerDataResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    //Odeme durumu
                    if (kasaGuruMu) {
                        ZulaGameData zulaGameData = new ZulaGameData(0L, 0L, kasDegeri, userMail);
                        ZulaGameDataPostInterface zulaUserGameData = retrofit().create(ZulaGameDataPostInterface.class);
                        Call<ZulaGamerDataResponse> callKasaDegeriOdeme = zulaUserGameData.zulaGameDataInsert(zulaGameData);
                        callKasaDegeriOdeme.enqueue(new Callback<ZulaGamerDataResponse>() {
                            @Override
                            public void onResponse(Call<ZulaGamerDataResponse> call, Response<ZulaGamerDataResponse> response) {
                                ZulaGamerDataResponse zulaGamerDataResponse = response.body();
                                progressDoalog.setProgress(80);

                                if (zulaGamerDataResponse != null && zulaGamerDataResponse.getStatus()) {
                                    progressDoalog.setProgress(100);
                                    getZulaUserDetail();
                                    muzikcalar.start();
                                    progressDoalog.dismiss();
                                    //bottomSheetDialogOpenKasa.cancel();
                                    //  Toast.makeText(getApplicationContext(), "Kasa GURU Ücreti alındı.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Kasa GURU Ücreti alınamadı.", Toast.LENGTH_SHORT).show();
                                bottomSheetDialogOpenKasa.setCancelable(false);
                            }
                        });
                    } else {
                        progressDoalog.setProgress(60);
                        progressDoalog.setProgress(80);
                        ZulaGameData zulaGameData = new ZulaGameData(0L, kasDegeri, 0L, userMail);
                        ZulaGameDataPostInterface zulaUserGameData = retrofit().create(ZulaGameDataPostInterface.class);
                        Call<ZulaGamerDataResponse> callKasaDegeriOdeme = zulaUserGameData.zulaGameDataInsert(zulaGameData);
                        callKasaDegeriOdeme.enqueue(new Callback<ZulaGamerDataResponse>() {
                            @Override
                            public void onResponse(Call<ZulaGamerDataResponse> call, Response<ZulaGamerDataResponse> response) {
                                ZulaGamerDataResponse zulaGamerDataResponse = response.body();
                                if (zulaGamerDataResponse != null && zulaGamerDataResponse.getStatus()) {
                                    progressDoalog.setProgress(100);
                                    // Toast.makeText(getApplicationContext(), "Kasa ZP Ücreti alındı.", Toast.LENGTH_SHORT).show();
                                    getZulaUserDetail();
                                    muzikcalar.start();
                                    progressDoalog.dismiss();
                                }

                            }

                            @Override
                            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Kasa ZP Ücreti alınamadı.", Toast.LENGTH_SHORT).show();
                                bottomSheetDialogOpenKasa.setCancelable(false);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hatalı oyun bilgileri ekleme", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent gcKraliceKasasi = new Intent(kasaOyunuZula.this, MainActivity.class);
        gcKraliceKasasi.putExtra("kasaGeriKontrol",1);
        startActivity(gcKraliceKasasi);
    }

    public void getNotificationCount() {
        ZulaGameDataPostInterface request = retrofit().create(ZulaGameDataPostInterface.class);
        Call<List<ZulaNoticeCountter>> call = request.getZulaNoticeCountterJSON();
        call.enqueue(new Callback<List<ZulaNoticeCountter>>() {
            @Override
            public void onResponse(Call<List<ZulaNoticeCountter>> call, Response<List<ZulaNoticeCountter>> response) {
                cart_badge.setText("" + response.body().get(0).getNoticeCount());
            }

            @Override
            public void onFailure(Call<List<ZulaNoticeCountter>> call, Throwable t) {

            }
        });
    }

    public void getTotolOpenCase(String optionGroup) {
        ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
        Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings(optionGroup);
        call.enqueue(new Callback<List<ZulaSettings>>() {
            @Override
            public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                List<ZulaSettings> zulaSettings = response.body();
                if (zulaSettings != null) {
                    ZulaGameDataPostInterface zulaGameDataTotalCase = retrofit().create(ZulaGameDataPostInterface.class);
                    Call<List<ZulaTotalOpenCase>> callTotalCase = zulaGameDataTotalCase.getTotalCase();
                    callTotalCase.enqueue(new Callback<List<ZulaTotalOpenCase>>() {
                        @Override
                        public void onResponse(Call<List<ZulaTotalOpenCase>> call, Response<List<ZulaTotalOpenCase>> response) {
                            List<ZulaTotalOpenCase> zulaTotalOpenCases = response.body();
                            if (zulaTotalOpenCases != null) {
                                progressBarCheck(Integer.parseInt(zulaTotalOpenCases.get(0).getAcilanKasa()), Integer.parseInt(zulaSettings.get(0).getOption_value()));
                                message = zulaSettings.get(0).getOption_message();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ZulaTotalOpenCase>> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Toplam Kasa boş geldi", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

            }
        });
    }

    public void progressBarCheck(int start, int end) {
        progreso = (ProgressBar) findViewById(R.id.determinateBar);
        TextView MaxValue = (TextView) findViewById(R.id.MaxValue);
        MaxValue.setText(Integer.toString(start) + " / " + Integer.toString(end));

        progreso.setMax(end);
        if (start > 200)
            progreso.setProgress(100);
        if (start > 400)
            progreso.setProgress(300);
        if (start > 600)
            progreso.setProgress(400);
        if (start > 800)
            progreso.setProgress(600);
        if (start > 900)
            progreso.setProgress(800);

        progreso.setProgress(start);
    }

    private void openDialogTotalCase(String message) {
        View view = getLayoutInflater().inflate(R.layout.total_case_info, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.guruTotalMoney);
        messsage.setText(message + "");

        bottomSheetDialog.show();
    }

    public void getZulaUserDetail() {
        ZulaGameDataPostInterface zulaUserGameCardData = retrofit().create(ZulaGameDataPostInterface.class);
        Call<ZulaGamerCardDataResponse> call = zulaUserGameCardData.getDataUser(user.getEmail());
        call.enqueue(new Callback<ZulaGamerCardDataResponse>() {
            @Override
            public void onResponse(Call<ZulaGamerCardDataResponse> call, Response<ZulaGamerCardDataResponse> response) {
                ZulaGamerCardDataResponse zulaGamerResponse = response.body();
                if (zulaGamerResponse != null) {
                    if (zulaGamerResponse.getCaseWinXP() != null && zulaGamerResponse.getCaseWinZP() != null && zulaGamerResponse.getCaseWinGURU() != null) {
                        userXPTotal = Long.parseLong(zulaGamerResponse.getCaseWinXP());
                        userZPTotal = Long.parseLong(zulaGamerResponse.getCaseWinZP());
                        userGuruTotal = Long.parseLong(zulaGamerResponse.getCaseWinGURU());
                        userDetailResponse(userXPTotal, userZPTotal, userGuruTotal);
                    } else {
                        userZPTotal = 1000L;
                        zulaGamerResponse.setCaseWinZP(userZPTotal.toString());
                        userXPTotal = 0L;
                        zulaGamerResponse.setCaseWinXP(userXPTotal.toString());
                        userGuruTotal = 0L;
                        zulaGamerResponse.setCaseWinGURU(userGuruTotal.toString());
                        insertDataAPI(userXPTotal, userZPTotal, userGuruTotal, user.getEmail(), 0L, false);

                        //Room
                        userDetailResponse(Long.parseLong(zulaGamerResponse.getCaseWinXP()), Long.parseLong(zulaGamerResponse.getCaseWinZP()), Long.parseLong(zulaGamerResponse.getCaseWinGURU()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ZulaGamerCardDataResponse> call, Throwable t) {
                Log.i("Hata", t.toString());
                Toast.makeText(getApplicationContext(), "Oyuncu bilgileri çekilememektedir.." + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void enableOrDisableButtons(boolean isEnabled) {
        guruKasasi.setEnabled(isEnabled);
        agirKasa.setEnabled(isEnabled);
        antikKasa.setEnabled(isEnabled);
        curukKasa.setEnabled(isEnabled);
        demirKasa.setEnabled(isEnabled);
    }

    public void userDetailResponse(Long addXP, Long addZP, Long addGURU) {
        xp.setText(addXP.toString());
        zp.setText(addZP.toString());
        guru.setText(addGURU.toString());
        getTotolOpenCase("case");
        enableOrDisableButtons(true);
    }

    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    public void alertDialog(@NotNull String title, String mesaj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(kasaOyunuZula.this);
        builder.setTitle(title.toUpperCase());
        builder.setMessage(mesaj);
        builder.show();
    }

    //Random kasa içeriği
    public void rastageleZulaArac(String kasaAdiAlert, String[] kotuCikanlarCekilis, String[] ortaCikanlarCekilis, String[] iyiCikanlarCekilis, int ArrayLength1, int ArrayLength2, int ArrayLength3, Long kasaDegeri, Boolean kasaGuruMu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(kasaOyunuZula.this);
        builder.setTitle(kasaAdiAlert.toUpperCase());
        int sayi = 0;
        int number;
        Random random = new Random();
        while (sayi < 100) {
            sayi++;
            number = random.nextInt(101);
            Log.i("rand", Integer.toString(number));
            if (number <= 100 && number >= 20) {
                int rastbad = random.nextInt(ArrayLength1);
                typeKontrol = kotuCikanlarCekilis[rastbad].substring(kotuCikanlarCekilis[rastbad].indexOf(" "));
                Log.i("type", typeKontrol);

                if (typeKontrol.contains("GURU")) {
                    kazanGuru = Long.parseLong(kotuCikanlarCekilis[rastbad].substring(0, kotuCikanlarCekilis[rastbad].indexOf(" ")));
                    builder.setMessage(kotuCikanlarCekilis[rastbad]);
                    kasadanCikan = kotuCikanlarCekilis[rastbad];

                } else if (typeKontrol.contains("ZP")) {
                    kazanZP = Long.parseLong(kotuCikanlarCekilis[rastbad].substring(0, kotuCikanlarCekilis[rastbad].indexOf(" ")));
                    builder.setMessage(kotuCikanlarCekilis[rastbad]);
                    kasadanCikan = kotuCikanlarCekilis[rastbad];
                } else {
                    kazanXP = Long.parseLong(kotuCikanlarCekilis[rastbad].substring(0, kotuCikanlarCekilis[rastbad].indexOf(" ")));
                    builder.setMessage(kotuCikanlarCekilis[rastbad]);
                    kasadanCikan = kotuCikanlarCekilis[rastbad];
                }
            } else if (number <= 20 && number >= 2) {
                int rastortalar = random.nextInt(ArrayLength2);
                typeKontrol = ortaCikanlarCekilis[rastortalar].substring(ortaCikanlarCekilis[rastortalar].indexOf(" "));
                Log.i("type", typeKontrol);
                if (typeKontrol.contains("GURU")) {
                    kazanGuru = Long.parseLong(ortaCikanlarCekilis[rastortalar].substring(0, ortaCikanlarCekilis[rastortalar].indexOf(" ")));
                    builder.setMessage(ortaCikanlarCekilis[rastortalar]);
                    kasadanCikan = ortaCikanlarCekilis[rastortalar];
                } else if (typeKontrol.contains("ZP")) {
                    kazanZP = Long.parseLong(ortaCikanlarCekilis[rastortalar].substring(0, ortaCikanlarCekilis[rastortalar].indexOf(" ")));
                    builder.setMessage(ortaCikanlarCekilis[rastortalar]);
                    kasadanCikan = ortaCikanlarCekilis[rastortalar];
                } else {
                    kazanXP = Long.parseLong(ortaCikanlarCekilis[rastortalar].substring(0, ortaCikanlarCekilis[rastortalar].indexOf(" ")));
                    builder.setMessage(ortaCikanlarCekilis[rastortalar]);
                    kasadanCikan = ortaCikanlarCekilis[rastortalar];
                }
            } else if (number <= 2 && number >= 1) {
                int rasteniyiler = random.nextInt(ArrayLength3);
                typeKontrol = iyiCikanlarCekilis[rasteniyiler].substring(iyiCikanlarCekilis[rasteniyiler].indexOf(" "));
                Log.i("type", typeKontrol);
                if (typeKontrol.contains("GURU")) {
                    kazanGuru = Long.parseLong(iyiCikanlarCekilis[rasteniyiler].substring(0, iyiCikanlarCekilis[rasteniyiler].indexOf(" ")));
                    builder.setMessage(iyiCikanlarCekilis[rasteniyiler]);
                    kasadanCikan = iyiCikanlarCekilis[rasteniyiler];
                } else if (typeKontrol.contains("ZP")) {
                    kazanZP = Long.parseLong(iyiCikanlarCekilis[rasteniyiler].substring(0, iyiCikanlarCekilis[rasteniyiler].indexOf(" ")));
                    builder.setMessage(iyiCikanlarCekilis[rasteniyiler]);
                    kasadanCikan = iyiCikanlarCekilis[rasteniyiler];
                } else {
                    kazanXP = Long.parseLong(iyiCikanlarCekilis[rasteniyiler].substring(0, iyiCikanlarCekilis[rasteniyiler].indexOf(" ")));
                    builder.setMessage(iyiCikanlarCekilis[rasteniyiler]);
                    kasadanCikan = iyiCikanlarCekilis[rasteniyiler];
                }
            }
        }
        builder.setPositiveButton("ÇIKANI AL", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (typeKontrol.contains("GURU")) {
                    kazanilanZP = 0L;
                    kazanilanXP = 0L;
                    kazanılanGuruParasi = kazanGuru;

                } else if (typeKontrol.contains("ZP")) {
                    kazanilanXP = 0L;
                    kazanılanGuruParasi = 0L;
                    kazanilanZP = kazanZP;

                } else {
                    kazanilanZP = 0L;
                    kazanılanGuruParasi = 0L;
                    kazanilanXP = kazanXP;

                }
                insertDataAPI(kazanilanXP, kazanilanZP, kazanılanGuruParasi, user.getEmail(), kasaDegeri, kasaGuruMu);
            }
        });
        builder.setIcon(R.drawable.kasa512x512);
        builder.setCancelable(false);
        //builder.show();
        openCaseDetail(kasaAdiAlert.toUpperCase(), kasadanCikan, typeKontrol, kazanGuru, kazanXP, kazanZP, kasaDegeri, kasaGuruMu);
    }

    private void openCaseDetail(String kasaAdi, String message, String typeCase, Long guruShow, Long xpShow, Long zpShow, Long kasaMoney, Boolean kasaKontrol) {
        View view = getLayoutInflater().inflate(R.layout.open_case_detail_show_page, null);
        bottomSheetDialogOpenKasa = new BottomSheetDialog(this);
        bottomSheetDialogOpenKasa.setContentView(view);
        TextView kasadanCikan = (TextView) view.findViewById(R.id.kasadanCikan);
        TextView kasaName = (TextView) view.findViewById(R.id.kasaName);
        Button btnKasadanCikaniEkle = (Button) view.findViewById(R.id.btn_add);
        kasadanCikan.startAnimation(animationOpenKasa);
        kasaName.setText(kasaAdi);
        kasadanCikan.setText(message);
        btnKasadanCikaniEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kasadanCikan.startAnimation(animationOpenKasa);
                if (typeCase.contains("GURU")) {
                    kazanilanZP = 0L;
                    kazanilanXP = 0L;
                    kazanılanGuruParasi = guruShow;

                } else if (typeCase.contains("ZP")) {
                    kazanilanXP = 0L;
                    kazanılanGuruParasi = 0L;
                    kazanilanZP = zpShow;

                } else {
                    kazanilanZP = 0L;
                    kazanılanGuruParasi = 0L;
                    kazanilanXP = xpShow;

                }
                insertDataAPI(kazanilanXP, kazanilanZP, kazanılanGuruParasi, user.getEmail(), kasaMoney, kasaKontrol);
                bottomSheetDialogOpenKasa.cancel();
            }
        });
        bottomSheetDialogOpenKasa.setCancelable(false);
        bottomSheetDialogOpenKasa.show();

    }

    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Toast.makeText(getApplicationContext(),"Video yükleniyor",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
    }

    @Override
    public void onRewardedVideoStarted() {
    }

    @Override
    public void onRewardedVideoAdClosed() {
       /* AlertDialog.Builder builder = new AlertDialog.Builder(kasaOyunuZula.this, R.style.ShowAlertDialogTheme);
        builder.setTitle("BAŞARISIZ");
        builder.setMessage("Videoyu kapattığınız için ödülü kazanamadınız..");
        builder.show();
        loadRewardedVideoAd();*/
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        int puan = rewardItem.getAmount();
        Log.e("odul", Integer.toString(puan));
        if (puan == 10) {
            ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
            Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("video_reward");
            call.enqueue(new Callback<List<ZulaSettings>>() {
                @Override
                public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                    List<ZulaSettings> zulaSettings = response.body();
                    if (zulaSettings != null) {
                        insertDataAPI(0L, Long.parseLong(zulaSettings.get(0).getOption_value()), Long.parseLong(zulaSettings.get(1).getOption_value()), user.getEmail(), 0L, false);
                        //Toast.makeText(getApplicationContext(), "Tebrikler", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Hata ödül eklenemedi." + t.toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
        /*else {
            AlertDialog.Builder builder = new AlertDialog.Builder(kasaOyunuZula.this, R.style.ShowAlertDialogTheme);
            builder.setTitle("BAŞARISIZ");
            builder.setMessage("Videoyu kapattığınız için ödülü kazanamadınız..");
            builder.show();
            loadRewardedVideoAd();
        }*/
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
    }

    @Override
    public void onRewardedVideoCompleted() {
    }
}
