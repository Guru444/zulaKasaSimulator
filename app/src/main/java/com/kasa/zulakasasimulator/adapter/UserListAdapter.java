package com.kasa.zulakasasimulator.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.kasa.zulakasasimulator.R;
import com.kasa.zulakasasimulator.model.zulaGamer.ZulaGameData;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private List<ZulaGameData> item;
    private Context context;
    private String userMail;
    private List<ZulaGameData> contactListFiltered;
    private static final int ITEM_TYPE_USER = 978;
    private static final int ITEM_TYPE_BANNER_AD = 978;


    public UserListAdapter(Context context, List<ZulaGameData> item, String userMail) {
        this.context = context;
        this.item = item;
        this.contactListFiltered = item;
        this.userMail = userMail;
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(UserListAdapter.ViewHolder viewHolder, int i) {

        viewHolder.userName.setText(item.get(i).getZulaUserName());
        viewHolder.userXP.setText(item.get(i).getCaseWinXP().toString() + " XP");
        viewHolder.userZP.setText(item.get(i).getCaseWinZP().toString() + " ZP");
        viewHolder.userGURU.setText(item.get(i).getCaseWinGURU().toString() + " GURU");

        if(i==0){
            viewHolder.userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.first,0,0,0);
            viewHolder.frameLayout.setVisibility(View.GONE);
        }
        else if(i==1){
            viewHolder.userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.second,0,0,0);
            viewHolder.frameLayout.setVisibility(View.GONE);
        }
        else if(i==2){
            viewHolder.userName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.third,0,0,0);
            viewHolder.frameLayout.setVisibility(View.GONE);
        }
        else if(i>2){
            viewHolder.userName.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            viewHolder.frameLayout.setVisibility(View.VISIBLE);
            viewHolder.cartBadge.setText(Integer.toString(i+1));
        }

        if(item.get(i).getZulaUserMail().equals(userMail)){
            viewHolder.userLayout.setBackgroundResource(R.drawable.shadowfile_yellow);
        }
        else
            viewHolder.userLayout.setBackgroundResource(R.drawable.shadowfile);



        viewHolder.openModelSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Çok yakında...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userXP, userZP, userGURU, cartBadge;
        LinearLayout userLayout;
        FrameLayout frameLayout;
        Button openModelSheet;

        public ViewHolder(View view) {
            super(view);

            userName = view.findViewById(R.id.userName);
            userXP = (TextView) view.findViewById(R.id.userXP);
            userZP = (TextView) view.findViewById(R.id.userZP);
            userGURU = (TextView) view.findViewById(R.id.userGURU);
            cartBadge = (TextView) view.findViewById(R.id.cart_badge);
            userLayout = (LinearLayout) view.findViewById(R.id.layoutUser);
            frameLayout = (FrameLayout) view.findViewById(R.id.ranked);
            openModelSheet = (Button) view.findViewById(R.id.openModelSheet);

        }
    }
    class MyAdViewHolder extends RecyclerView.ViewHolder{

        public MyAdViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}

