package com.kasa.zulakasasimulator.view.kasaOpenView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.view.MainActivity;

public class kasaAcilimiMagaza extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {

    ImageView kasa1, kasa2, kasa3, kasa4, kasa5, kasa6, kasa7, kasa8;
    private static final String AD_UNIT_ID = "ca-app-pub-6370444038396282/9120109952";
    private static final String APP_ID ="ca-app-pub-6370444038396282~3250983402";
    private RewardedVideoAd mRewardedVideoAd;
    InterstitialAd mInterstitial;
    int kasaGeriKontrol=0;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kasa_acilimi_magaza);


        try {
            Bundle extras = getIntent().getExtras();
            kasaGeriKontrol = extras.getInt("kasaGeriKontrol");
            Log.i("kontrol", Integer.toString(kasaGeriKontrol));
        } catch (Exception e) {
        }

        //Video Reklam Ekleme
        MobileAds.initialize(this, APP_ID);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        kasa1 = (ImageView) findViewById(R.id.kasaresim1);
        kasa2 = (ImageView) findViewById(R.id.kasaresim2);
        kasa3 = (ImageView) findViewById(R.id.kasaresim3);
        kasa4 = (ImageView) findViewById(R.id.kasaresim4);
        kasa5 = (ImageView) findViewById(R.id.kasaresim5);
        kasa6 = (ImageView) findViewById(R.id.kasaresim6);
        kasa7 = (ImageView) findViewById(R.id.kasaresim7);
        kasa8 = (ImageView) findViewById(R.id.kasaresim8);

        kasa1.setOnClickListener(this);
        kasa2.setOnClickListener(this);
        kasa3.setOnClickListener(this);
        kasa4.setOnClickListener(this);
        kasa5.setOnClickListener(this);
        kasa6.setOnClickListener(this);
        kasa7.setOnClickListener(this);
        kasa8.setOnClickListener(this);

        if (kasaGeriKontrol == 1) {
            inrequestadd();
            /*if (mRewardedVideoAd.isLoaded()) {
                mRewardedVideoAd.show();
            }*/
        }

    }
    public void inrequestadd() {
        mInterstitial = new InterstitialAd(kasaAcilimiMagaza.this);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kasaresim1:
                String[] kotuCikanlar2020Kasasi = {"500 MATERYAL","6.000ZP","12.500ZP","6.000ZP","1 ADET XP KASASI","5.000XP","1 ADET ZULA KASASI","6.000ZP","1 ADET YILDIZ KASASI","12.500ZP","1000 MATERYAL","6.000ZP","20.000XP","1 ADET SÜPER XP KASASI","6.000ZP","1 ADET XP KASASI","12.500ZP","12.500ZP","1 ADET SÜPER XP KASASI","500 MATERYAL","1 ADET YILDIZ KASASI","1 ADET SÜPER XP KASASI","500 MATERYAL","1 ADET SÜPER XP KASASI","1 ADET YILDIZ KASASI","1 ADET XP KASASI","1 ADET SÜPER XP KASASI","1 ADET XP KASASI","6.000ZP","1 ADET SÜPER XP KASASI","6.000ZP","7.500XP","6.000ZP","1 ADET SÜPER XP KASASI","6.000ZP","500 MATERYAL","1000 MATERYAL"};
                String[] ortaCikanlar2020Kasasi = {"5000 MATERYAL","2500 MATERYAL","35.000ZP","75.000ZP","50.000XP","35.000ZP","75.000ZP","35.000ZP","50.000XP","75.000ZP","35.000ZP","35.000ZP","50.000XP","35.000ZP","5000 MATERYAL","2500 MATERYAL","50.000XP","35.000ZP","50.000XP","100.000XP","75.000ZP","35.000ZP","100.000XP","50.000XP"};
                String[] iyiCikanlar2020Kasasi = {"150.000ZP","250.000ZP","150.000ZP","250.000XP","500.000XP","150.000ZP","1.000.000ZP","150.000ZP","250.000XP","500.000ZP","1.000.000XP","150.000ZP","150.000ZP","150.000ZP","250.000ZP","250.000XP","500.000XP"};

                Intent gc = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gc.putExtra("kasaNumber",1);
                gc.putExtra("KasaName","2020 KASASI");
                gc.putExtra("kasaKotu",kotuCikanlar2020Kasasi);
                gc.putExtra("kasaOrta",ortaCikanlar2020Kasasi);
                gc.putExtra("kasaGood",iyiCikanlar2020Kasasi);
                startActivity(gc);
                break;
            case R.id.kasaresim2:
                String[] kotuCikanlarKralicekasasi = {"6000 ZP","METERYAL KASASI", "6000ZP", "DESTEK KASASI" , "25.000ZP", "PLATİN DESTE",
                        "6000ZP","PLATİN TAARUZ DESTESİ","GLADYO KASASI","FESTİVAL KASASI","KOMANDO KASASI","5.000XP","10.000XP","20.000XP","10.000XP","5.000XP","FESTİVAL KASASI","KOMBO KASASI","6000ZP","PLATİN KASA","EKSTRA KASASI","20.000XP","ALTIN DESTE"};
                String[] ortaCikanlarKralicekasasi = {"50.000XP","75.000ZP","100.000XP","5 ADET GLADYO KASASI","150.000XP",
                        "50.000XP","5 ADET GLADYO KASASI","75.000ZP","100.000XP","75.000ZP","150.000ZP","75.000ZP","50.000XP","50.000XP","150.000XP","50.000XP","5 ADET GLADYO KASASI","75.000ZP","100.000XP","75.000ZP","50.000XP","150.000XP","75.000ZP","250.000ZP"};
                String[] iyiCikanlarKralicekasasi = {"1 ADET DESTANSI QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","3 ADET QUANTUM GARANTİLİ DESTE"};

                Intent gcKraliceKasasi = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcKraliceKasasi.putExtra("kasaNumber",2);
                gcKraliceKasasi.putExtra("KasaName","KRALİÇE KASASI");
                gcKraliceKasasi.putExtra("kasaKotu",kotuCikanlarKralicekasasi);
                gcKraliceKasasi.putExtra("kasaOrta",ortaCikanlarKralicekasasi);
                gcKraliceKasasi.putExtra("kasaGood",iyiCikanlarKralicekasasi);
                startActivity(gcKraliceKasasi);
                break;
            case R.id.kasaresim7:
                String[] kotuCikanlarSans2 = {"1 ADET EKSTRA KASASI","3 ADET EKSTRA KASASI","1 ADET KOMBO KASASI","RASTGELE 1 GÜNLÜK SİLAH","35.000ZP","25.000ZP","6.000ZP","RASTGELE 1 ADET SÜNGÜ","RASTGELE 1 ADET STANDART DESTE","RASTGELE 3 ADET STANDART DESTE","RASTGELE 5 ADET STANDART DESTE","1 ADET SÜPER DESTE KASASI","1 ADET SÜPER MATERYAL KASASI","3 ADET EKSTRA KASASI","1 ADET KOMBO KASASI","RASTGELE 1 ADET SÜNGÜ","1 ADET EKSTRA KASASI","1 ADET XP KASASI","1 ADET EKSTRA KASASI","RASTGELE 1 ADET EFSANE DESTE","1 ADET SINIRSIZ MAKİNALI KASA","1 ADET 2019 KASASI","1 ADET SÜPER XP KASASI","1 ADET  XP KASASI","1 ADET  GLADYO KASASI","1 ADET MATAERYAL KASASI","1 ADET SÜPER DESEN KASASI","1 ADET EKSTRA KASASI","3 ADET EKSTRA KASASI","RASTGELE 1 ADET SÜNGÜ","1 ADET EKSTRA KASASI","1 ADET KOMANDO KASASI","1 ADET EKSTRA KASASI","1 ADET EKSTRA KASASI","3 ADET EKSTRA KASASI","1 ADET SÜNGÜ KASASI","1 ADET EKSTRA KASASI"};
                String[] ortaCikanlarSans2 = {"1 ADET 30 GÜNLÜK GARANTİLİ KASA","150.000ZP","7 GÜNLÜK RASTGELE SİLAH","1 ADET 90 GÜNLÜK GARANTİLİ KASA 2","1 ADET 7 GÜNLÜK GARANTİLİ KASA","7 GÜNLÜK RASTGELE SİLAH","7 GÜNLÜK RASTGELE SİLAH","1 ADET SINIRSIZ MAKİNALI KASASI","1 ADET 30 GÜNLÜK GARANTİLİ KASA","75.000ZP","1 ADET 7 GÜNLÜK GARANTİLİ KASA","7 GÜNLÜK RASTGELE SİLAH","1 ADET 7 GÜNLÜK GARANTİLİ KASA","7 GÜNLÜK RASTGELE SİLAH","7 GÜNLÜK RASTGELE SİLAH","7 GÜNLÜK RASTGELE SİLAH","1 ADET SINIRSIZ BIÇAK KASASI","1 ADET 30 GÜNLÜK GARANTİLİ KASA","1 ADET SINIRSIZ NİŞANCI KASASI","7 GÜNLÜK RASTGELE SİLAH","1 ADET 30 GÜNLÜK GARANTİLİ KASA","1 ADET 60 GÜNLÜK GARANTİLİ KASA","7 GÜNLÜK RASTGELE SİLAH","75.000ZP","7 GÜNLÜK RASTGELE SİLAH","7 GÜNLÜK RASTGELE SİLAH"};
                String[] iyiCikanlarSans2 = {"500.000XP","250.000ZP","SINIRSIZ AK-47","SINIRSIZ MPT-76","250.000ZP","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","3 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","500.000XP","SINIRSIZ KRİSSVECTOR","SINIRSIZ P90","SINIRSIZ CHEYTAC M200","SINIRSIZ KAR98","250.000XP","SINIRSIZ KARAMBİT","250.000XP","SINIRSIZ DESERT EAGLE","SINIRSIZ TEC9","3 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","SINIRSIZ DSR-1","SINIRSIZ M93R","250.000ZP","SINIRSIZ AUG A3","250.000ZP","250.000XP","250.000ZP","SINIRSIZ M468","250.000ZP","250.000XP","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","SINIRSIZ ZİGANA","SINIRSIZ GLOCK18","250.000XP","250.000XP","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","SINIRSIZ ZİGANA","250.000ZP","SINIRSIZ KRİSSVECTOR","SINIRSIZ KARAMBİT","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","SINIRSIZ AK-47","SINIRSIZ DSR-1","250.000ZP","SINIRSIZ KARAMBİT","SINIRSIZ KRİSSVECTOR","3 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","SINIRSIZ ZİGANA","SINIRSIZ AK-47","250.000XP","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","250.000ZP","SINIRSIZ DSR-1","250.000ZP","1 ADET DESTANSI QUANTUM SUSTURUCU GARANTİLİ DESTE","250.000XP","250.000XP"};

                Intent gcSans2 = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcSans2.putExtra("kasaNumber",3);
                gcSans2.putExtra("KasaName","ŞANS KASASI 2");
                gcSans2.putExtra("kasaKotu",kotuCikanlarSans2);
                gcSans2.putExtra("kasaOrta",ortaCikanlarSans2);
                gcSans2.putExtra("kasaGood",iyiCikanlarSans2);
                startActivity(gcSans2);
                break;
            case R.id.kasaresim4:
                String[] kotuCikanlarLokum = {"2  ADET DESTEK KASASI","3  ADET EKSTRA KASASI","10.000XP","12.500ZP","2  ADET KOMBO KASASI","PLATİN KASA","35.000ZP","40.000ZP","25.000ZP","3  ADET DESTEK KASASI","2  ADET DESTEK KASASI","2  ADET DESTEK KASASI","EKSTRA KASASI","10.000XP","10.000XP","3  ADET DESTEK KASASI","5 ADET KOMBO KASASI","DESTEK KASASI","2  ADET DESTEK KASASI","EKSTRA KASASI","3  ADET EKSTRA KASASI","5  ADET KOMBO KASASI","10.000XP","DESTEK KASASI","10.000XP","2  ADET DESTEK KASASI","2  ADET DESTEK KASASI","10.000XP","1  ADET KOMBO KASASI","2  ADET KOMBO KASASI","2  ADET KOMBO KASASI","DESTEK KASASI","DESTEK KASASI","10.000XP","PLATİN KASA","PLATİN KASA","5 ADET KOMBO KASASI","3 ADET EKSTRA KASASI","2  ADET DESTEK KASASI","5 ADET KOMBO KASASI"};
                String[] ortaCikanlarLokum = {"20.000XP","20.000XP","50.000XP","20.000XP","50.000XP","50.000XP","50.000XP","50.000XP","75.000ZP","20.000XP","20.000XP","50.000ZP","100.000XP","150.000XP","150.000ZP","50.000XP","20.000XP","50.000XP","75.000ZP","50.000ZP","20.000XP","50.000ZP"};
                String[] iyiCikanlarLokum = {"1.000.000XP","2.000.000XP","250.000ZP","250.000XP","500.000ZP","250.000ZP","250.000ZP","250.000XP","250.000XP","500.000ZP","250.000XP","5 ADET QUANTUM GARANTİLİ DESTE","1 ADET QUANTUM GARANTİLİ DESTE","3 ADET QUANTUM GARANTİLİ DESTE"};

                Intent gcLokum = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcLokum.putExtra("kasaNumber",4);
                gcLokum.putExtra("KasaName","LOKUM KASASI");
                gcLokum.putExtra("kasaKotu",kotuCikanlarLokum);
                gcLokum.putExtra("kasaOrta",ortaCikanlarLokum);
                gcLokum.putExtra("kasaGood",iyiCikanlarLokum);
                startActivity(gcLokum);
                break;
            case R.id.kasaresim3:
                String[] kotuCikanlarKral = {"1 ADET SÜPER ZP KASASI","2 ADET SÜPER ZP KASASI","3 ADET SÜPER ZP KASASI","1 ADET ZULA KASASI","2 ADET ZULA KASASI","1 ADET GLADYO KASASI","1 ADET GLADYO KASASI","2 ADET GLADYO KASASI","2 ADET SÜPER MATERYAL KASASI","2 ADET  MATERYAL KASASI","1 ADET  ELMAS DESTE","2 ADET  ELMAS DESTE","2 ADET SÜPER ZP KASASI","3 ADET  KOMANDO KASASI","2 ADET SÜPER ZP KASASI","3 ADET SÜPER ZP KASASI","2 ADET SÜPER ZP KASASI","2 ADET SÜPER ZP KASASI","2 ADET ZULA KASASI","2 ADET ZULA KASASI","3 ADET  ELMAS DESTE","2 ADET  ELMAS DESTE","2 ADET GLADYO KASASI","3 ADET SÜPER ZP KASASI","2 ADET SÜPER ZP KASASI","3 ADET  ELMAS DESTE","2 ADET SÜPER ZP KASASI","1 ADET SÜPER ZP KASASI","2 ADET SÜPER ZP KASASI"};
                String[] ortaCikanlarKral = {"2 ADET KOMBO KASASI","2 ADET MEGA ZP KASASI","500 MATERYAL","1000 MATERYAL","1 ADET KOMBO KASASI","2 ADET KOMBO KASASI","50.000XP","2 ADET MEGA ZP KASASI","50.000XP","2 ADET MEGA ZP KASASI","500 MATERYAL","1 ADET MEGA ZP KASASI","500 MATERYAL","2 ADET KOMBO KASASI","2 ADET KOMBO KASASI","1 ADET MEGA ZP KASASI","2 ADET KOMBO KASASI","2 ADET MEGA ZP KASASI","5.000 MATERYAL","50.000XP","2 ADET MEGA ZP KASASI","500 MATERYAL","1 ADET MEGA ZP KASASI","50.000XP","50.000XP"};
                String[] iyiCikanlarKral = {"1.000 MATERYAL","100.000XP","250.000XP","10.000 MATERYAL","100.000XP","100.000XP","100.000XP","100.000XP","250.000XP","100.000XP","100.000XP","100.000XP","250.000XP","100.000XP","250.000XP","100.000XP","500.000XP","100.000XP","100.000XP","100.000XP"};

                Intent gcKral = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcKral.putExtra("kasaNumber",5);
                gcKral.putExtra("KasaName","KRAL KASASI");
                gcKral.putExtra("kasaKotu",kotuCikanlarKral);
                gcKral.putExtra("kasaOrta",ortaCikanlarKral);
                gcKral.putExtra("kasaGood",iyiCikanlarKral);
                startActivity(gcKral);
                break;
            case R.id.kasaresim5:
                String[] kotuCikanlarOzelMetaryal = {"250 DEMİR","500 DEMİR","750 DEMİR","50 TİTANYUM","50 KROM","250 DEMİR","250 DEMİR","250 DEMİR","50 KROM","250 DEMİR","250 DEMİR","50 KROM","250 DEMİR","50 KROM","750 DEMİR","500 DEMİR","50 TİTANYUM","750 DEMİR","50 KROM","50 KROM","250 DEMİR","50 KROM","250 DEMİR","500 DEMİR","50 TİTANYUM","500 DEMİR","50 TİTANYUM","50 KROM","750 DEMİR","250 DEMİR","50 KROM","250 DEMİR","50 KROM"};
                String[] ortaCikanlarOzelMetaryal = {"1.000 DEMİR","100 KROM","250 KROM","100 TİTANYUM","100 KROM","100 KROM","100 KROM","100 KROM","100 KROM","100 KROM","100 KROM","1.000 DEMİR","100 KROM","250 KROM","100 KROM","100 KROM","100 KROM","100 TİTANYUM","100 KROM","250 KROM","250 KROM","1.000 DEMİR","100 KROM","100 KROM"};
                String[] iyiCikanlarOzelMetaryal = {"500 KROM","500 TİTANYUM","250 TİTANYUM","1.500 DEMİR","2.500 DEMİR","500 KROM","500 KROM","1.500 DEMİR","500 KROM","500 KROM","500 KROM","1.500 DEMİR","500 KROM","500 KROM","500 TİTANYUM","1.500 DEMİR","500 KROM","1.500 DEMİR","500 KROM","1.500 DEMİR","2.500 DEMİR","2.500 DEMİR","500 KROM","500 KROM","1.500 DEMİR","500 KROM","500 KROM"};

                Intent gcOzelMetaryal = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcOzelMetaryal.putExtra("kasaNumber",6);
                gcOzelMetaryal.putExtra("KasaName","ÖZEL MATERYAL KASASI");
                gcOzelMetaryal.putExtra("kasaKotu",kotuCikanlarOzelMetaryal);
                gcOzelMetaryal.putExtra("kasaOrta",ortaCikanlarOzelMetaryal);
                gcOzelMetaryal.putExtra("kasaGood",iyiCikanlarOzelMetaryal);
                startActivity(gcOzelMetaryal);
                break;
            case R.id.kasaresim8:
                String[] kotuCikanlarSuperMateryal = {"5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","10 KROMLU 100 MATERYAL","25 KROMLU 200 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","100 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","10 KROMLU 100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","10 KROMLU 100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL","10 KROMLU 100 MATERYAL","5 TİTANYUMLU 50 MATERYAL","100 MATERYAL"};
                String[] ortaCikanlarSuperMateryal = {"50 KROMLU 500 MATERYAL","500 MATERYAL","500 MATERYAL","25 TİTANYUMLU 200 MATERYAL","500 MATERYAL","500 MATERYAL","25 TİTANYUMLU 200 MATERYAL"};
                String[] iyiCikanlarSuperMateryal = {"100 TİTANYUMLU 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","200 KROMLU 2.500 MATERYAL","1500 TİTANYUMLU 10.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 KOBALTLI 1.000 MATERYAL","200 KROMLU 2.500 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 KOBALTLI 1.000 MATERYAL","1500 TİTANYUMLU 10.000 MATERYAL","100 KOBALTLI 1.000 MATERYAL","200 KROMLU 2.500 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","200 KROMLU 2.500 MATERYAL","100 KOBALTLI 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","100 KOBALTLI 1.000 MATERYAL","100 TİTANYUMLU 1.000 MATERYAL","1500 TİTANYUMLU 10.000 MATERYAL"};

                Intent gcSuperMateryal = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcSuperMateryal.putExtra("kasaNumber",7);
                gcSuperMateryal.putExtra("KasaName","SÜPER MATERYAL KASASI");
                gcSuperMateryal.putExtra("kasaKotu",kotuCikanlarSuperMateryal);
                gcSuperMateryal.putExtra("kasaOrta",ortaCikanlarSuperMateryal);
                gcSuperMateryal.putExtra("kasaGood",iyiCikanlarSuperMateryal);
                startActivity(gcSuperMateryal);
                break;
            case R.id.kasaresim6:
                String[] kotuCikanlarPlatinKasa = {" 1 ADET DESTE KASASI"," 3 ADET DESTE KASASI"," 1 ADET ELMAS DESTE"," 5.000XP"," 7.500XP"," 500 MATERYAL"," 6.000ZP"," 12.500ZP"," 1 ADET PLATİN KASA"," 1 ADET LOKUM KASASI"," 25.000ZP"," 50.000ZP"," 5.000XP"," 5.000XP"," 7.500XP"," 500 MATERYAL"," 1 ADET ELMAS DESTE"," 7.500XP"," 1 ADET DESTE KASASI"," 1 ADET LOKUM KASASI"," 1 ADET PLATİN KASA"," 1 ADET DESTE KASASI"," 1 ADET DESTE KASASI"," 1 ADET LOKUM KASASI"," 6.000ZP"," 1 ADET LOKUM KASASI"," 1 ADET DESTE KASASI"," 6.000ZP"," 1 ADET DESTE KASASI"," 1 ADET ÖZEL MATERYAL KASASI"," 20.000XP"," 25.000XP"};
                String[] ortaCikanlarPlatinKasa = {" ŞANS KASASI 2"," 75.000ZP"," 1000 MATERYAL"," 2500 MATERYAL"," 5 ADET LOKUM KASASI"," 5 ADET ŞANS KASASI 2"," 100.000ZP"," 100.000XP"," 100.000XP"," 1000 MATERYAL"," 1000 MATERYAL"," ŞANS KASASI 2"," 5 ADET ŞANS KASASI 2"," 5 ADET ŞANS KASASI 2"," 75.000ZP"," 1000 MATERYAL"," 5 ADET ŞANS KASASI 2"," 5 ADET ŞANS KASASI 2"," 1000 MATERYAL"," 100.000ZP"};
                String[] iyiCikanlarPlatinKasa = {"250.000ZP","1 ADET DESTANSI GARANTİLİ DESTE","250.000XP","5000 MATERYAL","500.000ZP","2.000.000ZP","15.000.000ZP","1 ADET DESTANSI GARANTİLİ DESTE","250.000ZP","250.000ZP","1 ADET DESTANSI GARANTİLİ DESTE","250.000ZP","250.000ZP","5000 MATERYAL","250.000ZP","5000 MATERYAL","500.000ZP","250.000ZP","5000 MATERYAL","250.000ZP"};

                Intent gcPlatinKasa = new Intent(kasaAcilimiMagaza.this, kasaAcilimi.class);
                gcPlatinKasa.putExtra("kasaNumber",8);
                gcPlatinKasa.putExtra("KasaName","PLATİN GLADYO KASASI");
                gcPlatinKasa.putExtra("kasaKotu",kotuCikanlarPlatinKasa);
                gcPlatinKasa.putExtra("kasaOrta",ortaCikanlarPlatinKasa);
                gcPlatinKasa.putExtra("kasaGood",iyiCikanlarPlatinKasa);
                startActivity(gcPlatinKasa);
                break;
        }
    }
    @Override
    public void onBackPressed() {
        Intent gcKraliceKasasi = new Intent(kasaAcilimiMagaza.this, MainActivity.class);
        startActivity(gcKraliceKasasi);
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
    public void onRewardedVideoAdClosed() { }
    @Override
    public void onRewarded(RewardItem rewardItem) { }
    @Override
    public void onRewardedVideoAdLeftApplication() { }
    @Override
    public void onRewardedVideoAdFailedToLoad(int i) { }
    @Override
    public void onRewardedVideoCompleted() { }
}