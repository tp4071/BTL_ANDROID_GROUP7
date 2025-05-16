package com.example.libraryapplication.repository;

import android.util.Log;

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

            public void onFailure(Call<List<PhieuViPham>> call, Throwable t) {
            }
        });
    }

    public void updateTrangThai(String id, PhieuViPham vp, Runnable onSuccess) {
        String filter = "eq." + id;
        api.updateTrangThaiViPham(filter, vp).enqueue(new Callback<List<PhieuViPham>>() {
            @Override
            public void onResponse(Call<List<PhieuViPham>> call, Response<List<PhieuViPham>> res) {
                if (res.isSuccessful() && res.body() != null && !res.body().isEmpty()) {
                    onSuccess.run();
                } else {
                    Log.e("UpdateTrangThai", "Update thất bại hoặc không có dữ liệu trả về");
                }
            }
            @Override
            public void onFailure(Call<List<PhieuViPham>> call, Throwable t) {
                Log.e("UpdateTrangThai", "Lỗi mạng khi cập nhật", t);
            }
        });
    }




    public void create(PhieuViPham vp, Runnable onSuccess) {
        api.createPhieuViPham(vp).enqueue(new Callback<PhieuViPham>() {
            public void onResponse(Call<PhieuViPham> call, Response<PhieuViPham> res) {
                if (res.isSuccessful() && onSuccess != null) onSuccess.run();
            }

            public void onFailure(Call<PhieuViPham> call, Throwable t) {
            }
        });
    }

    public void searchByMaPM(String keyword, MutableLiveData<List<PhieuViPham>> data) {
        // Chuẩn hóa keyword với định dạng Supabase ILIKE
        String formatted = "ilike.*" + keyword + "*";

        api.searchPhieuViPham(formatted).enqueue(new Callback<List<PhieuViPham>>() {
            @Override
            public void onResponse(Call<List<PhieuViPham>> call, Response<List<PhieuViPham>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<PhieuViPham>> call, Throwable t) {
                data.postValue(null);
            }
        });
    }


}
