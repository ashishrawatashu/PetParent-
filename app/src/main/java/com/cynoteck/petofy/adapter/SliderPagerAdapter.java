package com.cynoteck.petofy.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.cynoteck.petofy.R;
import com.cynoteck.petofy.onClicks.OnSliderClickListener;

import java.util.ArrayList;

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater  layoutInflater;
    Activity                activity;
    ArrayList<Integer>      image_arraylist;
    OnSliderClickListener       onItemClickListener;
    ArrayList<String>       service_type_images;
    String                  type ="";

    public SliderPagerAdapter(Activity activity, ArrayList<Integer> image_arraylist,OnSliderClickListener onItemClickListener) {
        this.activity               = activity;
        this.image_arraylist        = image_arraylist;
        this.onItemClickListener    = onItemClickListener;
    }

    public SliderPagerAdapter(Activity activity, ArrayList<String> service_type_images,OnSliderClickListener onItemClickListener,String type) {
        this.activity                   = activity;
        this.service_type_images        = service_type_images;
        this.onItemClickListener        = onItemClickListener;
        this.type                       = type;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater      = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view           = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = view.findViewById(R.id.im_slider);
        if (type.equals("serviceImages")){
            Log.d("serviceImages","serviceImagesADAPETR");
            Glide.with(activity.getApplicationContext()).load(service_type_images.get(position)).into(im_slider);
            container.addView(view);
        }else {
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
        }




        return view;
    }

    @Override
    public int getCount() {
        if (type.equals("serviceImages")){
            return service_type_images.size();
        }else {
            return image_arraylist.size();
        }
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }
    @Override
    public float getPageWidth(int position) {
        if (type.equals("serviceImages")){
            return(1.0f);
        }else {
            return(0.8f);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
