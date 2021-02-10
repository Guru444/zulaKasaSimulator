package com.kasa.zulakasasimulator.view.Circle;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class RotationCircleTransformer implements ViewPager.PageTransformer {


    public RotationCircleTransformer() {
    }

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();
        view.setPivotX((float) pageWidth);
        view.setPivotY((float) pageHeight);

        if (position < -1) { //[-infinity,1)
            //off to the left by a lot
            view.setRotation(0);
            view.setAlpha(0);
        } else if (position <= 1) { //[-1,1]
            view.setTranslationX((-position) * pageWidth); //shift the view over
            view.setRotation(position * (90)); //rotate it
            // Fade the page relative to its distance from the center
            view.setAlpha(Math.max(1, 1 - Math.abs(position) / 3));
        } else { //(1, +infinity]
            //off to the right by a lot
            view.setRotation(0);
            view.setAlpha(0);
        }
    }
}
