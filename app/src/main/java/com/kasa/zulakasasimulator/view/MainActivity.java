package com.kasa.zulakasasimulator.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaSettings.ZulaSettings;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.Circle.CircleFragment;
import com.kasa.zulakasasimulator.view.aboutUS.ActivityAboutUs;
import com.kasa.zulakasasimulator.view.kasaOpenView.kasaAcilimiMagaza;
import com.kasa.zulakasasimulator.view.zulaGameUserList.kasaOyunuZula;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public MediaPlayer muzikcalar;
    private ProgressDialog progressDialog;
    public static final String BASE_URL = "https://oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    Animation animation;
    ViewPager mPager;
    CirclePagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        muzikcalar = MediaPlayer.create(MainActivity.this, R.raw.ses);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);

        /*mPager = (ViewPager) findViewById(R.id.pager);
        mPager.startAnimation(animation);
        List<CircleFragment> fragments = new ArrayList<>();
        fragments.add(CircleFragment.newInstance("DÜELLO"));
        fragments.add(CircleFragment.newInstance("KASA MAĞAZA"));
        fragments.add(CircleFragment.newInstance("KASA OYUNU"));
        fragments.add(CircleFragment.newInstance("HAKKIMIZDA"));
        mAdapter = new CirclePagerAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(mAdapter);

        mPager.setPageTransformer(true, new RotationCircleTransformer());*/


        Button kasaAc = (Button) findViewById(R.id.button);
        Button magazaKasa = (Button) findViewById(R.id.magaza);
        Button buttonoyun = (Button) findViewById(R.id.kasaOyun);
        Button hakkimizda = (Button) findViewById(R.id.hakkimizda);

        buttonoyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Oyun yükleniyor...");
                progressDialog.show();

                ZulaGameDataPostInterface zulaGameDataPostInterface = retrofit().create(ZulaGameDataPostInterface.class);
                Call<List<ZulaSettings>> call = zulaGameDataPostInterface.getSettings("gaming");
                call.enqueue(new Callback<List<ZulaSettings>>() {
                    @Override
                    public void onResponse(Call<List<ZulaSettings>> call, Response<List<ZulaSettings>> response) {
                        List<ZulaSettings> zulaSettings = response.body();
                        if (zulaSettings != null) {
                            if (Integer.parseInt(zulaSettings.get(0).getOption_value()) == 1) {

                                Intent intent = new Intent(MainActivity.this, kasaOyunuZula.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade, R.anim.slide);
                                progressDialog.dismiss();
                            } else {
                                openDialog(zulaSettings.get(0).getOption_message());
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oyun Bakım hatası", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ZulaSettings>> call, Throwable t) {

                    }
                });

            }
        });
        magazaKasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, kasaAcilimiMagaza.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.slide);
            }
        });
        kasaAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("ÇOK YAKINDA KASA DÜELLOSU SİZLERLE..");
            }
        });
        hakkimizda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityAboutUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade, R.anim.slide);
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

    private void openDialog(String message) {
        View view = getLayoutInflater().inflate(R.layout.cok_yakinda_duello, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView messsage = (TextView) view.findViewById(R.id.messsage);
        messsage.setText(message);
        bottomSheetDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Çıkmak istediğinizden emin misiniz ?");
        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    static class CirclePagerAdapter extends FragmentStatePagerAdapter {

        List<CircleFragment> mFrags = new ArrayList<>();

        public CirclePagerAdapter(FragmentManager fm, List<CircleFragment> frags) {
            super(fm);
            mFrags = frags;
        }

        @Override
        public androidx.fragment.app.Fragment getItem(int position) {
            int index = position % mFrags.size();
            return mFrags.get(index);
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

    }
}
