package com.example.libraryapplication.repository;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.ThuThu;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;
import com.example.libraryapplication.sharedPreferences.SessionManager;
import com.example.libraryapplication.view.Login;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThuThuRepository {
    private SupabaseApi api = SupabaseClient.getApi();

    public void login(ThuThu input, MutableLiveData<List<ThuThu>> result) {
        api.login(input).enqueue(new Callback<List<ThuThu>>() {
            public void onResponse(Call<List<ThuThu>> call, Response<List<ThuThu>> res) {
                if (res.isSuccessful()) result.setValue(res.body());
            }
            public void onFailure(Call<List<ThuThu>> call, Throwable t) {}
        });
    }

    public void updateThongTin(String id, ThuThu tt) {
        api.updateThuThu("eq." + id, tt).enqueue(new Callback<ThuThu>() {
            @Override
            public void onResponse(Call<ThuThu> call, Response<ThuThu> response) {
                if (response.isSuccessful()) {
                    Log.d("UpdateThuThu", "Dữ liệu gửi đi: " + tt.toString());
                    Log.d("UpdateThuThu", "CẬP NHẬT THÀNH CÔNG THÔNG TIN THỦ THƯ!");
                } else {
                    Log.d("UpdateThuThu", "Dữ liệu gửi đi: " + tt.toString());
                    Log.e("UpdateThuThu", "LỖI CẬP NHẬT : " + response.code() + " - " + response.message());
                }
            }
            @Override
            public void onFailure(Call<ThuThu> call, Throwable t) {
                Log.e("UpdateThuThu", "LỖI KẾT NỐI : " + t.getMessage());
            }
        });
    }

    public void dangNhapThuThu(String tenTK , String matKhau , Context context , MutableLiveData<Boolean> result) {
        api.dangNhap("eq." + tenTK , "eq." + matKhau).enqueue(new Callback<List<ThuThu>>() {
            @Override
            public void onResponse(Call<List<ThuThu>> call, Response<List<ThuThu>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    // Đăng nhập thành công
                    ThuThu thuThu = response.body().get(0);
                    Log.d("LOGIN", "Đăng nhập thành công: " + thuThu.getTenTK());
                    // Lưu thông tin người dùng vào SharedPreferences hoặc ViewModel
                    SessionManager sessionManager = new SessionManager(context);
                    sessionManager.saveThuThu(thuThu);
                    Log.d("Login" , "Tên tài khoản admin : " + sessionManager.getTenTK());
                    Log.d("Login" , "Mật khẩu tài khoản admin : " + sessionManager.getMatKhau());
                    result.postValue(true);
                } else {
                    // Sai tài khoản hoặc mật khẩu
                    Log.e("Login", "Tài khoản hoặc mật khẩu không đúng!");
                    result.postValue(false);
                }
            }
            @Override
            public void onFailure(Call<List<ThuThu>> call, Throwable t) {
                // Lỗi kết nối
                Log.e("Login", "Lỗi kết nối: " + t.getMessage());
                result.postValue(false);
            }
        });
    }
}
