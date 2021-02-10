package com.kasa.zulakasasimulator.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kasa.zulakasasimulator.OnItemTouchListener;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import com.kasa.zulakasasimulator.model.zulaNotice.ZulaNoticeList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ZulaNoticeListAdapter extends RecyclerView.Adapter<ZulaNoticeListAdapter.ViewHolder> {
    private List<ZulaNoticeList> item;
    private Context context;
    Boolean openKontrol = true;
    private OnItemTouchListener onItemTouchListener;
    String dayDifference, dayDiffenceDay, dayMinute, FinalDate;

    public ZulaNoticeListAdapter(Context context, List<ZulaNoticeList> item, OnItemTouchListener onItemTouchListener) {
        this.context = context;
        this.item = item;
        this.onItemTouchListener = onItemTouchListener;
    }

    @NonNull
    @Override
    public ZulaNoticeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZulaNoticeListAdapter.ViewHolder holder, int position) {
        holder.zulaNoticeTitle.setText(item.get(position).getZulaNoticeTitle());
        holder.zulaNoticeOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemTouchListener.onItemClickDuyuru(v, position);
            }
        });
        Date tarih = new Date();
        SimpleDateFormat bugun = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String CurrentDate = item.get(position).getZulaNoticeDate(); //Son video izleme süresini aldık.
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
            holder.zulaNoticeAgo.setText(dayMinute +" dakika önce");
            //builder.setMessage("Kalan süre : " + (60 - Integer.parseInt(dayMinute)) + " Dakika");
        else if (dayDiffenceDay == "0")
            holder.zulaNoticeAgo.setText(dayDifference + " saat önce");
            //builder.setMessage("En son video izleme " + dayDifference + " Saat " + dayMinute + " Dakika geçti");
        else
            holder.zulaNoticeAgo.setText("Çok önce");
            //builder.setMessage("Video izlemeyeli uzun zaman oldu.");

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView zulaNoticeTitle, zulaNoticeAgo;
        private LinearLayout zulaNoticeOpen;
        private LinearLayout detail;

        public ViewHolder(View view) {
            super(view);

            zulaNoticeTitle = (TextView) view.findViewById(R.id.zulaNoticeTitle);
            zulaNoticeAgo = (TextView) view.findViewById(R.id.zulaNoticeAgo);

            zulaNoticeOpen = (LinearLayout) view.findViewById(R.id.zulaNoticeOpen);
        }
    }
}
