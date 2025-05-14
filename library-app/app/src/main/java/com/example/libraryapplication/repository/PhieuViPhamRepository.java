package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuViPhamRepository {
    private SupabaseApi api = SupabaseClient.getApi();

    public void getAll(MutableLiveData<List<PhieuViPham>> data) {
        api.getAllPhieuViPham().enqueue(new Callback<List<PhieuViPham>>() {
            public void onResponse(Call<List<PhieuViPham>> call, Response<List<PhieuViPham>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<PhieuViPham>> call, Throwable t) {}
        });
    }

    public void updateTrangThai(String id, PhieuViPham vp) {
        api.updateTrangThaiViPham(id, vp).enqueue(new Callback<PhieuViPham>() {
            public void onResponse(Call<PhieuViPham> call, Response<PhieuViPham> res) {}
            public void onFailure(Call<PhieuViPham> call, Throwable t) {}
        });
    }
}
