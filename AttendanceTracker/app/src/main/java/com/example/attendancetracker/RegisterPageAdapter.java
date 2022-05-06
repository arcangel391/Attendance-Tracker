package com.example.attendancetracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class RegisterPageAdapter extends PagerAdapter {
    Context context;

    public RegisterPageAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater;
        View view;

        if(position == 0){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(R.layout.register_page, container, false);

            container.addView(view);
            return view;

        }else if(position == 1){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(R.layout.register_page1, container, false);

            container.addView(view);
            return view;
        }else if(position==2){
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(R.layout.register_page2, container, false);

            container.addView(view);
            return view;
        }else{
            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(R.layout.register_page3, container, false);

            container.addView(view);
            return view;
        }

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
