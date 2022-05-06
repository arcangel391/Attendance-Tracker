package com.example.attendancetracker;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterPage2 extends AppCompatActivity {
    View footer;
    TextView previous, next;
    ImageView dot2, dot3, dot1;
    LinearLayout header;
    LinearLayout dotLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page2);
        getWindow().setEnterTransition(null);

        footer = findViewById(R.id.footer);
        previous = footer.findViewById(R.id.btnPrevious);
        next = footer.findViewById(R.id.btnNext);
        previous.setVisibility(View.VISIBLE);

        header = findViewById(R.id.header);
        dotLayout = footer.findViewById(R.id.dotLayout);
        dot1 = footer.findViewById(R.id.dot1);
        dot1.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot2 = footer.findViewById(R.id.dot2);
        dot3 = footer.findViewById(R.id.dot3);

        dot2.setBackgroundTintList(getResources().getColorStateList(R.color.green));
        dot3.setBackgroundTintList(getResources().getColorStateList(R.color.sky));


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage3.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage2.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage1.class);
                Pair[] pairs = new Pair[4];

                pairs[0] = new Pair<View, String>(previous, "transition-previous-btn");
                pairs[1] = new Pair<View, String>(next, "transition-next-btn");
                pairs[2] = new Pair<View, String>(header, "transition-header");
                pairs[3] = new Pair<View, String>(dotLayout, "transition-dot");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterPage2.this,pairs);
                getWindow().setExitTransition(null);
                startActivity(intent, options.toBundle());

            }
        });
    }
}
