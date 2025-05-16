package com.example.libraryapplication.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.libraryapplication.R;
public class StatusBarHandler {
    public static void backToHomePage(View rootView, Activity activity){
        TextView home = rootView.findViewById(R.id.home);
        home.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, HomePage.class));
        });
    }
}
