package com.kasa.zulakasasimulator.view.zulaGameUserList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.adapter.UserListAdapter;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserListActivity extends AppCompatActivity {
    private LinearLayoutManager layoutManager;
    public static final String BASE_URL = "https://www.oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    TextView userCountter;
    String userMail;
    UserListAdapter userListAdapter;
    String textChanged;

    //Banner Recyli setup
    public static final int ITEMS_PER_AD = 4;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private RecyclerView recyclerView;
    private List<ZulaGameData> userList = new ArrayList<>();
    public final static int spaceBetweenAds = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        MobileAds.initialize(this, AD_UNIT_ID);
        Bundle extras = getIntent().getExtras();
        userMail = extras.getString("userMail");

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        userCountter = (TextView) findViewById(R.id.userCountter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        initAdMobAdsSDK();
        //addAdMobBannerAds();
        initViews();
    }

    @Override
    public void onBackPressed() {
        Intent gcKraliceKasasi = new Intent(UserListActivity.this, kasaOyunuZula.class);
        gcKraliceKasasi.putExtra("kasaGeriKontrol",1);
        startActivity(gcKraliceKasasi);
    }

    private void initAdMobAdsSDK() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.userList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ZulaGameDataPostInterface request = retrofit.create(ZulaGameDataPostInterface.class);
        Call<List<ZulaGameData>> call = request.getJSON();
        call.enqueue(new Callback<List<ZulaGameData>>() {
            @Override
            public void onResponse(Call<List<ZulaGameData>> call, Response<List<ZulaGameData>> response) {
                userList = response.body();
                //addNativeExpressAds();

                userCountter.setText("Rekabetci sayısı " + userList.size());
                recyclerView = (RecyclerView) findViewById(R.id.userList);
                layoutManager = new LinearLayoutManager(UserListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                UserListAdapter recyclerViewAdapter = new UserListAdapter(getApplicationContext(), userList, userMail);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<ZulaGameData>> call, Throwable t) {

            }
        });
    }


  /*  private void addNativeExpressAds() {
        for (int i = spaceBetweenAds; i <= userList.size(); i += (spaceBetweenAds + 1)) {
            NativeExpressAdView adView = new NativeExpressAdView(this);
            adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
            userList.add(i,adView);
            Log.e("liste",userList.toString());
        }

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                float scale = UserListActivity.this.getResources().getDisplayMetrics().density;
                int adWidth = (int) (recyclerView.getWidth() - (2 * UserListActivity.this.getResources().getDimension(R.dimen.activity_horizontal_margin)));
                AdSize adSize = new AdSize((int) (adWidth / scale), 150);
                for (int i = spaceBetweenAds; i <= userList.size(); i += (spaceBetweenAds + 1)) {
                    NativeExpressAdView adViewToSize = (NativeExpressAdView) userList.get(i);
                    adViewToSize.setAdSize(adSize);
                }

                loadNativeExpressAd(spaceBetweenAds);
            }
        });

    }
    private void loadNativeExpressAd(final int index) {

        if (index >= userList.size()) {
            return;
        }

        Object item = userList.get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // The previous Native Express ad failed to load. Call this method again to load
                // the next ad in the items list.
                Log.e("AdmobMainActivity", "The previous Native Express ad failed to load. Attempting to"
                        + " load the next Native Express ad in the items list.");
                loadNativeExpressAd(index + spaceBetweenAds + 1);
            }
        });
        adView.loadAd(new AdRequest.Builder().build());
    }*/




}