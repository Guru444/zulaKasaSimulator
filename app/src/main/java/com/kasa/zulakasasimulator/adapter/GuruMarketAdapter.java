package com.kasa.zulakasasimulator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.daimajia.swipe.SwipeLayout;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.OnItemTouchListener;
import com.kasa.zulakasasimulator.model.guruMarket.GuruMarket;

import java.util.List;

public class GuruMarketAdapter extends RecyclerView.Adapter<GuruMarketAdapter.ViewHolder> {
    private List<GuruMarket> item;
    private Context context ;
    private OnItemTouchListener onItemTouchListener;
    Boolean openControll = null;


    public GuruMarketAdapter(Context context, List<GuruMarket> item,OnItemTouchListener onItemTouchListener, Boolean openControll) {
        this.context = context;
        this.item = item;
        this.onItemTouchListener = onItemTouchListener;
        this.openControll = openControll;
    }

    @Override
    public GuruMarketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guru_market_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuruMarketAdapter.ViewHolder holder, int position) {
        if (openControll)
            holder.swipelayout.open(true);
        else
            holder.swipelayout.close(true);


            holder.guruMoneyTitle.setText(item.get(position).getGuruMarketDescription());
            holder.price.setText(item.get(position).getGuruMarketPrice());
            holder.priceUnit.setText(item.get(position).getGuruMarketUnit());
            holder.totalPrice.setText(item.get(position).getGuruMarketName());
            holder.pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onItemTouchListener.onItemClick(v,position,holder.totalPrice.getText().toString());
                }
            });
    }

    @Override
    public int getItemCount() {
        return item.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView guruMoneyTitle,price,priceUnit,totalPrice;
        private Button pay;
        SwipeRevealLayout swipelayout;
        public ViewHolder(View view) {
            super(view);
            guruMoneyTitle = (TextView)view.findViewById(R.id.guruMoneyTitle);
            totalPrice = (TextView)view.findViewById(R.id.totalPrice);
            priceUnit = (TextView)view.findViewById(R.id.priceUnit);
            price = (TextView)view.findViewById(R.id.price);
            pay = (Button) view.findViewById(R.id.pay);
            swipelayout = (SwipeRevealLayout) view.findViewById(R.id.swipelayout);

        }

    }
}
