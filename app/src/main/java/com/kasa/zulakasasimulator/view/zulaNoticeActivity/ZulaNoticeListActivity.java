package com.kasa.zulakasasimulator.view.zulaNoticeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.kasa.zulakasasimulator.OnItemTouchListener;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.adapter.UserListAdapter;
import com.kasa.zulakasasimulator.adapter.ZulaNoticeListAdapter;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaNotice.ZulaNoticeList;
import com.kasa.zulakasasimulator.service.userGamerService.ZulaGameDataPostInterface;
import com.kasa.zulakasasimulator.view.googlePayView.GuruParasiPayment;
import com.kasa.zulakasasimulator.view.zulaGameUserList.UserListActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ZulaNoticeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ZulaNoticeList> noticeList =null;
    private LinearLayoutManager layoutManager;
    OnItemTouchListener onItemTouchListener;
    Boolean openKontrol = true;
    TextView cart_badge;
    String dayDifference, dayDiffenceDay, dayMinute, FinalDate;
    public static final String BASE_URL = "https://www.oyunpuanla.com/zulaKasaSimulator/public/index.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zula_notice_list);
        SwipeRefreshLayout swipeRefreshNotice = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshNotice);
        cart_badge = (TextView)findViewById(R.id.cart_badge);



        swipeRefreshNotice.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                if (swipeRefreshNotice.isRefreshing()) {
                    swipeRefreshNotice.setRefreshing(false);
                }
            }
        });



        onItemTouchListener = new OnItemTouchListener() {
            @Override
            public void onItemClick(View view, int position, String title) {


            }

            @Override
            public void onItemClickDuyuru(View view, int position) {
                Date tarih = new Date();
                SimpleDateFormat bugun = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String CurrentDate = noticeList.get(position).getZulaNoticePlanTime(); //Son video izleme süresini aldık.
                    FinalDate = bugun.format(tarih); // Bugünün tarihi
                    Date date1;
                    Date date2;
                    SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
                } catch (Exception exception) {
                    Log.e("Hata", "çevirme " + exception);
                }

                Log.e("Gün", "şimdi: " + dayDifference);

                if (dayDiffenceDay == "0" && dayDifference == "0")
                    openNotificationDesc(noticeList.get(position).getZulaNoticeHref(),noticeList.get(position).getZulaNoticeTitle(),noticeList.get(position).getZulaNoticeDescription(),dayMinute,"0","0");
                   // holder.zulaNoticeAgo.setText(dayMinute +" Dakika önce");
                    //builder.setMessage("Kalan süre : " + (60 - Integer.parseInt(dayMinute)) + " Dakika");
                else if (dayDifference == "0")
                    openNotificationDesc(noticeList.get(position).getZulaNoticeHref(),noticeList.get(position).getZulaNoticeTitle(),noticeList.get(position).getZulaNoticeDescription(),dayMinute,dayDifference,"0");
                    //holder.zulaNoticeAgo.setText(dayDifference + "Saat" + dayMinute + "Dakika önce");
                    //builder.setMessage("En son video izleme " + dayDifference + " Saat " + dayMinute + " Dakika geçti");
                else
                    openNotificationDesc(noticeList.get(position).getZulaNoticeHref(),noticeList.get(position).getZulaNoticeTitle(),noticeList.get(position).getZulaNoticeDescription(),dayMinute,dayDifference,dayDiffenceDay);
                    //holder.zulaNoticeAgo.setText("Çok önce");
                //Toast.makeText(ZulaNoticeListActivity.this, "Tapped " + position, Toast.LENGTH_SHORT).show();
            }
        };

        initViews();
    }
    private void openNotificationDesc(String notiURL,String notiTitle,String notiDesc,String kalanMinute,String kalanHour, String kalanDay) {
        View view = getLayoutInflater().inflate(R.layout.notification_detail_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        TextView notificationDetail = (TextView) view.findViewById(R.id.notificationDetail);
        TextView notificationTitle = (TextView) view.findViewById(R.id.notificationTitle);
        TextView notificationTime = (TextView) view.findViewById(R.id.notificationTime);
        Button openNoti = (Button) view.findViewById(R.id.openNoti);
        openNoti.setVisibility(View.VISIBLE);
        notificationDetail.setText(notiDesc);
        notificationTitle.setText(notiTitle);
        if(notiURL.equals("")){
            openNoti.setVisibility(View.INVISIBLE);
        }
        if(kalanHour !="0" && kalanDay != "0"){
            notificationTime.setText(kalanDay+" Gün ");
        }
        else if(kalanHour != "0"){
            notificationTime.setText(kalanHour+" Saat "+ kalanMinute+" Dakika");
        }
        else{
            notificationTime.setText(kalanMinute+" Dakika");
        }
        openNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse(notiURL));
                startActivity(viewIntent);
            }
        });

        bottomSheetDialog.show();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.noticeList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }
    private void loadJSON(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ZulaGameDataPostInterface request = retrofit.create(ZulaGameDataPostInterface.class);
        Call<List<ZulaNoticeList>> call = request.getZulaNoticeJSON();
        call.enqueue(new Callback<List<ZulaNoticeList>>() {
            @Override
            public void onResponse(Call<List<ZulaNoticeList>> call, Response<List<ZulaNoticeList>> response) {
                noticeList = response.body();
                cart_badge.setText(""+noticeList.size());
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.noticeList);
                layoutManager = new LinearLayoutManager(ZulaNoticeListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                ZulaNoticeListAdapter recyclerViewAdapter =new ZulaNoticeListAdapter(getApplicationContext(), noticeList,onItemTouchListener);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<ZulaNoticeList>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Hatalı duyuru çekme",Toast.LENGTH_LONG);
            }
        });
    }
}