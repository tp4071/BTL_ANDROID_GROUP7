package com.example.libraryapplication.view.components;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.libraryapplication.R;
import com.example.libraryapplication.view.book.BookManagedment;
import com.example.libraryapplication.view.pm.PMManagedment;
import com.example.libraryapplication.view.pvp.PhieuViPhamActivity;
import com.example.libraryapplication.view.book.Search;
import com.example.libraryapplication.view.thongke.ThongKe;

public class MenuBarHandler {

    public static void setupMenu(View rootView, Activity activity) {
        LinearLayout bookBtn = rootView.findViewById(R.id.bookBtn);
        LinearLayout pmBtn = rootView.findViewById(R.id.pmBtn);
        LinearLayout searchBtn = rootView.findViewById(R.id.searchBtn);
        LinearLayout pvpBtn = rootView.findViewById(R.id.pvpBtn);
        LinearLayout tkBtn = rootView.findViewById(R.id.tkBtn);

        bookBtn.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, BookManagedment.class));
        });

        pmBtn.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, PMManagedment.class));
        });

        searchBtn.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, Search.class));
        });

        pvpBtn.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, PhieuViPhamActivity.class));
        });

        tkBtn.setOnClickListener(v -> {
            activity.startActivity(new Intent(activity, ThongKe.class));
        });
    }
}
