package com.cynoteck.petofyparents.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyparents.R;
import com.cynoteck.petofyparents.onClicks.OnItemClickListener;
import com.cynoteck.petofyparents.onClicks.OnSliderClickListener;

import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<Integer> image_arraylist;
    OnSliderClickListener onItemClickListener;

    public SliderPagerAdapter(Activity activity, ArrayList<Integer> image_arraylist,OnSliderClickListener onItemClickListener) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = view.findViewById(R.id.im_slider);
        Glide.with(activity.getApplicationContext()).load(image_arraylist.get(position)).into(im_slider);
        container.addView(view);

        im_slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSliderClickListener(position);
                }
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
    @Override
    public float getPageWidth(int position) {
        return(0.8f);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
