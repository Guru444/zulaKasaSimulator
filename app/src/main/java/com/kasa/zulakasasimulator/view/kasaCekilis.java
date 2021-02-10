package com.kasa.zulakasasimulator.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.cekilisKullanicilar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.provider.Settings.System;
public class kasaCekilis extends AppCompatActivity implements RewardedVideoAdListener {

    public boolean internetVarmi(final Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    //FireBase Databese
    FirebaseDatabase database;
    DatabaseReference myRef;
    String kazanilanItem;
    ImageView cekilisKasasi;
    EditText zulaNickName;
    Animation animation;
    private static final String AD_UNIT_ID = "ca-app-pub-6370444038396282/9120109952";
    private static final String APP_ID ="ca-app-pub-6370444038396282~3250983402";
    private RewardedVideoAd mRewardedVideoAd;

    String[] kotuCikanlarCekilis = {"3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","5.850 ZULA ALTINI","3.000 ZULA ALTINI","5.850 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","3.000 ZULA ALTINI","5.850 ZULA ALTINI"};
    String[] ortaCikanlarCekilis = {"16.250 ZULA ALTINI","5.850 ZULA ALTINI","3.000 ZULA ALTINI","5.850 ZULA ALTINI","5.850 ZULA ALTINI","16.250 ZULA ALTINI","5.850 ZULA ALTINI","5.850 ZULA ALTINI"};
    String[] iyiCikanlarCekilis = {"34.000 ZULA ALTINI","5.850 ZULA ALTINI","16.250 ZULA ALTINI","34.000 ZULA ALTINI"};

    ArrayList<String> arrayList  = new ArrayList<>();
    ArrayList<String> deviceList  = new ArrayList<>();
    int izlediMi=0;
    String androidID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_cekilis);
        if(internetVarmi(this)){
        //Telefon IMEİ number Get
        androidID = System.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("cekiliseKatilanlar");

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hotelRef = rootRef.child("cekiliseKatilanlar");

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String address = ds.child("kullaniciNickName").getValue(String.class);
                    String devices = ds.child("deviceID").getValue(String.class);
                    arrayList.add(address);
                    deviceList.add(devices);
                    Log.d("TAG", address + "**------ " + devices);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.i("Failed to read value.", String.valueOf(error.toException()));
            }
        };
        hotelRef.addListenerForSingleValueEvent(eventListener);
        //Reklam
        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        zulaNickName = (EditText) findViewById(R.id.zulaKullaniciAdi);
        Button kasaAc = (Button) findViewById(R.id.kasaAc);
        /*Button magazaKasa = (Button) findViewById(R.id.magaza);
        Button button = (Button) findViewById(R.id.button);*/
        cekilisKasasi = (ImageView) findViewById(R.id.imageKasa);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        kasaAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadRewardedVideoAd();
                String kullaniciAdi = zulaNickName.getText().toString();
                if (!TextUtils.isEmpty(kullaniciAdi)) {
                    if (arrayList.contains(kullaniciAdi) == false && deviceList.contains(androidID) == false) {
                        if (mRewardedVideoAd.isLoaded()) {
                            mRewardedVideoAd.show();
                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
                        builder.setTitle("KATILIM HAKKINIZ DOLDU");
                        builder.setMessage("Çekilişe katılım sağlamış bulunmaktasınız");
                        builder.show();
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
                    builder.setTitle("BOŞ BIRAKMAYIN");
                    builder.setMessage("lütfen kullanıcı adınızı boş bırakmayınız");
                    builder.show();
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
            alert.setCancelable(false);
            alert.show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent gcKraliceKasasi = new Intent(kasaCekilis.this, MainActivity.class);
        startActivity(gcKraliceKasasi);
    }
    public void rastageleZulaArac(String kasaAdiAlert, int ArrayLength1, int ArrayLength2, int ArrayLength3){
        AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
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
                kazanilanItem = kotuCikanlarCekilis[rastbad];
                builder.setMessage(kotuCikanlarCekilis[rastbad]);
            }
            else if(number <=20 && number >=2)
            {
                int rastortalar = random.nextInt(ArrayLength2);
                kazanilanItem = ortaCikanlarCekilis[rastortalar];
                builder.setMessage(ortaCikanlarCekilis[rastortalar]);
            }
            else if(number <=2 && number >=1)
            {
                int rasteniyiler = random.nextInt(ArrayLength3);
                kazanilanItem = iyiCikanlarCekilis[rasteniyiler];
                builder.setMessage(iyiCikanlarCekilis[rasteniyiler]);
            }
        }
        builder.setIcon(R.drawable.supermeteryal);
        builder.show();
    }
    private void insertData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = simpleDateFormat.format(new Date());
        final String nickName = zulaNickName.getText().toString();
        final String kasadanCikan = kazanilanItem;
        final String katilimTarihi = format;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cekiliseKatilanlar");
        String key = database.getReference("cekiliseKatilanlar").push().getKey();

        cekilisKullanicilar zulaGamer = new cekilisKullanicilar(key,nickName,kasadanCikan,katilimTarihi,androidID);
        myRef.child(key).setValue(zulaGamer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
                builder.setTitle("BAŞARILI");
                builder.setMessage("Çekilişe Katılma işlemi Başarılı.. \n\nÇekilişte Kazanacağınız Ödül \n\n"+kasadanCikan);
                builder.setPositiveButton("TEŞEKKÜRLER", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gcKraliceKasasi = new Intent(kasaCekilis.this, MainActivity.class);
                        startActivity(gcKraliceKasasi);
                    }
                });
                builder.setCancelable(false);
                builder.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
                builder.setTitle("BAŞARILI");
                builder.setMessage("Çekilişe Katılma işlemi hatalı..");
                builder.show();
            }
        });
    }
    private void loadRewardedVideoAd() {
        if (!mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.loadAd(AD_UNIT_ID, new AdRequest.Builder().build());
        }
    }
    @Override
    public void onRewardedVideoAdLoaded() { }
    @Override
    public void onRewardedVideoAdOpened() { }
    @Override
    public void onRewardedVideoStarted() { }
    @Override
    public void onRewardedVideoAdClosed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(kasaCekilis.this, R.style.ShowAlertDialogTheme);
        builder.setTitle("BAŞARISIZ");
        builder.setMessage("Çekilişe Katılma Hakkını Kazanamadınız.");
        builder.show();
        loadRewardedVideoAd();
    }
    @Override
    public void onRewarded(RewardItem rewardItem) {
        int puan = rewardItem.getAmount();
        if(puan == 10){
            izlediMi++;
            cekilisKasasi.startAnimation(animation);
            rastageleZulaArac("ÇEKİLİŞ KASASI",kotuCikanlarCekilis.length,ortaCikanlarCekilis.length,iyiCikanlarCekilis.length);
            insertData();
        }

    }
    @Override
    public void onRewardedVideoAdLeftApplication() { }
    @Override
    public void onRewardedVideoAdFailedToLoad(int i) { }
    @Override
    public void onRewardedVideoCompleted() { }
}
