package com.example.weatherapp;


import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class RotatePageTransformer implements PageTransformer {
	private static final float MIN_SCALE = 0.75f;

    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.setAlpha(1);
            
            view.setRotationY(position*180);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            view.setTranslationX(-position*250);
            view.setRotationY(position*235);

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }
	
}