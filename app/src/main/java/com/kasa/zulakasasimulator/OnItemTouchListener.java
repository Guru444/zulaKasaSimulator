package com.kasa.zulakasasimulator;

import android.view.View;

public interface OnItemTouchListener {
    void onItemClick(View view, int position,String title);
    void onItemClickDuyuru(View view,int position);
}
