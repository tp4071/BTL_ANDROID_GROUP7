package com.example.libraryapplication.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.libraryapplication.model.ThuThu;

public class SessionManager {
    private static final String PREF_NAME = "ThuThuSession";
    private static final String maTK = "maTK";
    private static final String tenTK = "tenTK";
    private static final String matKhau = "matKhau";

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveThuThu(ThuThu thuThu) {
        editor.putString(maTK , thuThu.getMaTK());
        editor.putString(tenTK, thuThu.getTenTK());
        editor.putString(matKhau, thuThu.getMatKhau());
        editor.apply();
    }

    public String getTenTK() {
        return prefs.getString(tenTK, null);
    }

    public String getMatKhau() {
        return prefs.getString(matKhau, null);
    }

    public String getMaTK(){
        return prefs.getString(maTK , null);
    }

    // Log out
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
