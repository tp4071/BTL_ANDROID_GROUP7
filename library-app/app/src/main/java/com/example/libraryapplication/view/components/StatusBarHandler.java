package com.example.libraryapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.libraryapplication.R;
import com.example.libraryapplication.sharedPreferences.SessionManager;
import com.example.libraryapplication.view.thuthu.AccountManagedment;
import com.example.libraryapplication.view.homePage.HomePage;

public class StatusBarHandler {
    public static void backToHomePage(View rootView, Activity activity){
        TextView home = rootView.findViewById(R.id.home);
        home.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, HomePage.class));
        });
    }

    public static void comeToQLTKTT(View rootView, Activity activity){
        ImageView admin = rootView.findViewById(R.id.user_icon);
        admin.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, AccountManagedment.class));
        });
    }

    public static void updateNameTK(View rootView, Activity activity){
        TextView userNameTextView = rootView.findViewById(R.id.user_name);
        SessionManager sessionManager = new SessionManager(activity);
        String tenTaiKhoan = sessionManager.getTenTK();
        userNameTextView.setText(tenTaiKhoan);
    }
}
