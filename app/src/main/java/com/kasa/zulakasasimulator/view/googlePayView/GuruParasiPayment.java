package com.kasa.zulakasasimulator.view.googlePayView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.OnItemTouchListener;
import com.kasa.zulakasasimulator.adapter.GuruMarketAdapter;
import com.kasa.zulakasasimulator.model.guruMarket.GuruMarket;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGamerDataResponse;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.loginView.RegisterFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;


public class GuruParasiPayment extends AppCompatActivity implements PurchasesUpdatedListener {

    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    Activity activity = this;
    String titleMoneyCard;
    private RecyclerView recyclerView;
    private GuruMarketAdapter adapter;
    private List<GuruMarket> marketItemList = null;
    OnItemTouchListener onItemTouchListener;
    private LinearLayoutManager layoutManager;
    public static final String BASE_URL = "https://www.oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    private BillingClient mBillingClient;
    // @BindView(R.id.pay)
    Button pay;
    Boolean openClickPay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru_parasi_payment);
        TextView infoPayment = (TextView) findViewById(R.id.info);
        TextView openSatinAl = (TextView) findViewById(R.id.openSatinAl);

        openSatinAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(openClickPay){
                    loadJSON(true);
                    openClickPay = false;
                }
                else {
                    loadJSON(false);
                    openClickPay = true;
                }

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, RegisterFragment.class));
        }
        user = firebaseAuth.getCurrentUser();

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshGuruMarket);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        initViews();

        onItemTouchListener = new OnItemTouchListener() {
            @Override
            public void onItemClick(View view, int position, String title) {
                titleMoneyCard = title;
                buySubscription(titleMoneyCard, activity);
                //Toast.makeText(GuruParasiPayment.this, "Tapped " + position + " " + title, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClickDuyuru(View view, int position) {

            }
        };

        infoPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        ButterKnife.bind(this);
        mBillingClient = BillingClient.newBuilder(this).enablePendingPurchases().setListener(this).build();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.guruMarketItem);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON(false);
    }

    private void loadJSON(Boolean openControll) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ZulaGameDataPostInterface request = retrofit.create(ZulaGameDataPostInterface.class);
        Call<List<GuruMarket>> call = request.getGuruMarketJSON();
        call.enqueue(new Callback<List<GuruMarket>>() {
            @Override
            public void onResponse(Call<List<GuruMarket>> call, Response<List<GuruMarket>> response) {
                marketItemList = response.body();
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.guruMarketItem);
                layoutManager = new LinearLayoutManager(GuruParasiPayment.this);
                recyclerView.setLayoutManager(layoutManager);
                GuruMarketAdapter recyclerViewAdapter = new GuruMarketAdapter(getApplicationContext(), marketItemList, onItemTouchListener,openControll);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<GuruMarket>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Veriler çekilemiyor.", Toast.LENGTH_LONG);
            }
        });
    }

    private void openDialog() {
        View view = getLayoutInflater().inflate(R.layout.fragment_bottom_shet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }


    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> purchases) {
        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {

                //satın alma başarılı bir şekilde tamamlandığı durumda yapacağınız işlemler burada yer alacak
                Toast.makeText(getApplicationContext(), purchase.toString(), Toast.LENGTH_LONG).show();
                insertDataAPI(0L,0L,10000L,user.getEmail(),0L,false);
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Kullanıcı iptal ettiği durumlarda yapacağınız işlemler burada yer alacak.
            Toast.makeText(getApplicationContext(), "Ödeme başarısız", Toast.LENGTH_LONG);
        }
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED){

            Toast.makeText(getApplicationContext(), "Bu ürün zaten sizde var", Toast.LENGTH_LONG).show();
        }
        else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_UNAVAILABLE ){
            Toast.makeText(getApplicationContext(), " Satın alınmak istenen ürün mevcut", Toast.LENGTH_LONG).show();
        }
    }

    //@OnClick(R.id.pay)
   /* void f_haftalikAbonelik(View view) {
        void satinAl(String title){

        }
        buySubscription(title, this);
    }*/

    private void buySubscription(final String productID, Activity activity) {
        //Toast.makeText(getApplicationContext(), "Tıklandı", Toast.LENGTH_LONG).show();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == OK && !productID.equals("")) {
                    List<String> skuList = new ArrayList<>();
                    skuList.add(productID);
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                    //Ödeme detaylarını almak için bu bölümü kullanılır
                    mBillingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
                        @Override
                        public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                            Log.e("TAG", "querySkuDetailsAsync " + billingResult.getResponseCode());
                            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();

                                    //Ekrana ödeme işleminin çıkması ve ödemeyi tamamlamak için kullanılır
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetails)
                                            .build();
                                    mBillingClient.launchBillingFlow(activity, flowParams);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Hatalı işlem" + billingResult.getDebugMessage(), Toast.LENGTH_LONG).show();
                                Log.e("TAG", " error: " + billingResult.getDebugMessage());
                            }

                        }
                    });
                } else
                    Log.e("TAG", "onBillingSetupFinished() error code: " + billingResult.getDebugMessage());
            }

            @Override
            public void onBillingServiceDisconnected() {
                Log.w("Hata", "onBillingServiceDisconnected()");
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
        progressDoalog = new ProgressDialog(GuruParasiPayment.this);

        progressDoalog.setMax(100);
        progressDoalog.setMessage("Para ekleniyor");
        progressDoalog.setTitle("CÜZDANIM");
        Drawable draw=getResources().getDrawable(R.drawable.custom_progressbar);
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
                    Toast.makeText(getApplicationContext(), zulaGamerDataResponse.getMessage(), Toast.LENGTH_SHORT).show();

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
                                    progressDoalog.dismiss();
                                    //bottomSheetDialogOpenKasa.cancel();
                                    //  Toast.makeText(getApplicationContext(), "Kasa GURU Ücreti alındı.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Kasa GURU Ücreti eklenemedi.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        progressDoalog.setProgress(60);
                        progressDoalog.setProgress(80);
                        ZulaGameData zulaGameData = new ZulaGameData(caseWinXP, caseWinZP, caseWinGURU, userMail);
                        ZulaGameDataPostInterface zulaUserGameData = retrofit().create(ZulaGameDataPostInterface.class);
                        Call<ZulaGamerDataResponse> callKasaDegeriOdeme = zulaUserGameData.zulaGameDataInsert(zulaGameData);
                        callKasaDegeriOdeme.enqueue(new Callback<ZulaGamerDataResponse>() {
                            @Override
                            public void onResponse(Call<ZulaGamerDataResponse> call, Response<ZulaGamerDataResponse> response) {
                                ZulaGamerDataResponse zulaGamerDataResponse = response.body();
                                if (zulaGamerDataResponse != null && zulaGamerDataResponse.getStatus()) {
                                    progressDoalog.setProgress(100);
                                    // Toast.makeText(getApplicationContext(), "Kasa ZP Ücreti alındı.", Toast.LENGTH_SHORT).show();
                                    progressDoalog.dismiss();
                                }

                            }

                            @Override
                            public void onFailure(Call<ZulaGamerDataResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Kasa ZP Ücreti alınamadı.", Toast.LENGTH_SHORT).show();
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
    public Retrofit retrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

}