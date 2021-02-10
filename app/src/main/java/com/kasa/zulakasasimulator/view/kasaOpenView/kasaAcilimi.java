package com.kasa.zulakasasimulator.view.kasaOpenView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.kasa.zulakasasimulator.R;

import java.util.Random;

public class kasaAcilimi extends AppCompatActivity implements RewardedVideoAdListener {

    private static final String AD_UNIT_ID = "ca-app-pub-6370444038396282/9120109952";
    private static final String APP_ID ="ca-app-pub-6370444038396282~3250983402";
    private RewardedVideoAd mRewardedVideoAd;
    ImageView kasa;
    Animation animation;
    TextView acilanKasaAdet,kazanılanZP;
    String[] array1,array2,array3;
    public MediaPlayer muzikcalar;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    TextView kutuAcmaHakki;
    int kalanHak,hak=1 ;
    int kasaNumber;
    int Videoizledimi=0;
    private AdView adView;
    public boolean internetVarmi(final Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_acilimi);
        MobileAds.initialize(this, "ca-app-pub-6370444038396282/1613472258");
        adView = (AdView)findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        if(internetVarmi(this)){

        muzikcalar = MediaPlayer.create(kasaAcilimi.this, R.raw.ses);

        //Video Reklam Ekleme
        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //Shared
        sharedPref = this.getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        kalanHak = sharedPref.getInt("kutuAcilisHakki", 0);

        Button kasaAc = (Button) findViewById(R.id.kasaAc);
        Bundle extras = getIntent().getExtras();
        String kasaAdi = extras.getString("KasaName");
        kasaNumber = extras.getInt("kasaNumber");
        //Log.i("kasaNumber",kasaNumber);
        if (kasaNumber == 1) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.kasayirmi));
        } else if (kasaNumber == 2) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.kralice));
        } else if (kasaNumber == 3) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sanslikasa2));
        } else if (kasaNumber == 4) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.lokum));
        } else if (kasaNumber == 5) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.kral));
        } else if (kasaNumber == 6) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ozelmeteryal));
        } else if (kasaNumber == 7) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.supermeteryal));
        } else if (kasaNumber == 8) {
            kasa = (ImageView) findViewById(R.id.imageKasa);
            kasa.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.platinzula));
        }
        kasaAc.setText(kasaAdi + " AÇ");
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        kasaAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("KalanHak", Integer.toString(sharedPref.getInt("kutuAcilisHakki", 0)) + Integer.toString(hak * 5));
                if (sharedPref.getInt("kutuAcilisHakki", 0) != 100) {
                    muzikcalar.start();
                    kasa.startAnimation(animation);
                    Bundle extras = getIntent().getExtras();
                    String kasaAdiAlert = extras.getString("KasaName");
                    array1 = extras.getStringArray("kasaKotu");
                    array2 = extras.getStringArray("kasaOrta");
                    array3 = extras.getStringArray("kasaGood");
                    rastageleZulaArac(kasaAdiAlert, array1.length, array2.length, array3.length);
                    kalanHak = sharedPref.getInt("kutuAcilisHakki", 0);
                    int toplamKasaAcilis = 1;
                    kalanHak += toplamKasaAcilis;
                    editor.putInt("kutuAcilisHakki", (kalanHak));
                    editor.commit();
                    loadRewardedVideoAd();
                } else {
                    if (mRewardedVideoAd.isLoaded()) {
                        mRewardedVideoAd.show();
                        editor.putInt("kutuAcilisHakki", 0);
                        kalanHak = sharedPref.getInt("kutuAcilisHakki", 0);

                        editor.commit();
                    } else {
                        loadRewardedVideoAd();
                    }
                }
            }
        });
    }
        else{
            AlertDialog alert = new AlertDialog.Builder(this).create();
            alert.setTitle("İnternet Bağlantı Hatası");
            alert.setMessage("\t \t Lütfen internet bağlantınızı kontrol ediniz");
            alert.setIcon(R.drawable.kasa512x512);
            alert.setButton(DialogInterface.BUTTON_NEUTRAL, "Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent gcKraliceKasasi = new Intent(kasaAcilimi.this, kasaAcilimiMagaza.class);
        gcKraliceKasasi.putExtra("kasaGeriKontrol",1);
        startActivity(gcKraliceKasasi);
    }
    public void rastageleZulaArac(String kasaAdiAlert, int ArrayLength1, int ArrayLength2, int ArrayLength3){

        AlertDialog.Builder builder = new AlertDialog.Builder(kasaAcilimi.this);
        builder.setTitle(kasaAdiAlert.toUpperCase());
        int sayi=0;
        int number;
        Random random = new Random();
        while(sayi<100)
        {
            sayi++;
            number =  random.nextInt(101);
            Log.i("rand",Integer.toString(number));
            if(number <=100 && number >=20)
            {
                int rastbad = random.nextInt(ArrayLength1);
                builder.setMessage(array1[rastbad]);
            }
            else if(number <=20 && number >=2)
            {
                int rastortalar = random.nextInt(ArrayLength2);
                builder.setMessage(array2[rastortalar]);
            }
            else if(number <=2 && number >=1)
            {
                int rasteniyiler = random.nextInt(ArrayLength3);
                builder.setMessage(array3[rasteniyiler]);
            }
        }
        builder.setNeutralButton("SÜPER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setCancelable(false);
        if(kasaNumber==1){
            builder.setIcon(R.drawable.kasayirmi);
        }
        else if(kasaNumber==2){
            builder.setIcon(R.drawable.kralice);
        }
        else if(kasaNumber==3){
            builder.setIcon(R.drawable.sanslikasa2);
        }
        else if(kasaNumber==4){
            builder.setIcon(R.drawable.lokum);
        }
        else if(kasaNumber==5){
            builder.setIcon(R.drawable.kral);
        }
        else if(kasaNumber==6){
            builder.setIcon(R.drawable.ozelmeteryal);

        }else if(kasaNumber==7){
            builder.setIcon(R.drawable.supermeteryal);
        }

        builder.show();
    }
    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }
    @Override
    public void onRewardedVideoAdLoaded() {
    }
    @Override
    public void onRewardedVideoAdOpened() {
    }
    @Override
    public void onRewardedVideoStarted() {
    }
    @Override
    public void onRewardedVideoAdClosed() {
    }
    @Override
    public void onRewarded(RewardItem rewardItem) {

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
