package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.ThuThu;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

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
        api.updateThuThu(id, tt).enqueue(new Callback<ThuThu>() {
            public void onResponse(Call<ThuThu> call, Response<ThuThu> res) {}
            public void onFailure(Call<ThuThu> call, Throwable t) {}
        });
    }
}
