package com.example.attendancetracker;

import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class RegisterPage extends AppCompatActivity {
    private ViewPager viewPager;
    private TextView previous, next;
    private LinearLayout dotLayout;
    private RegisterPageAdapter registerPageAdapter;
    private TextView[] dots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page_main);


        viewPager = findViewById(R.id.viewPager);
        previous = findViewById(R.id.btnPrevious);
        next = findViewById(R.id.btnNext);
        dotLayout = findViewById(R.id.dotLayout);

        registerPageAdapter = new RegisterPageAdapter(RegisterPage.this);
        viewPager.setAdapter(registerPageAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addDots(position);
                if(position == 0){
                    next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.INVISIBLE);
                    previous.setEnabled(false);
                    next.setEnabled(true);

                }else if(position == 1 || position == 2){
                    next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.VISIBLE);
                    previous.setEnabled(true);
                    next.setEnabled(true);
                }else{
                    next.setText("Sign-up");
                    next.setVisibility(View.VISIBLE);
                    previous.setVisibility(View.INVISIBLE);
                    previous.setEnabled(false);
                    next.setEnabled(true);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    public void addDots(int position){
        dots = new TextView[4];
        dotLayout.removeAllViews();
        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(RegisterPage.this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.gray));

            dotLayout.addView(dots[i]);

        }

        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.green));
        }

    }

}
