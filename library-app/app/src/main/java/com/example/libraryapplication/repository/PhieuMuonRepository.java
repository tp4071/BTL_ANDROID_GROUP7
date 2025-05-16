package com.example.libraryapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuMuonRepository {
    private SupabaseApi api = SupabaseClient.getApi();
    public PhieuMuonRepository(){

    }
    public void createPhieuMuon(PhieuMuon pm) {
        api.createPhieuMuon(pm).enqueue(new Callback<PhieuMuon>() {
            public void onResponse(Call<PhieuMuon> call, Response<PhieuMuon> res) {}
            public void onFailure(Call<PhieuMuon> call, Throwable t) {}
        });
    }

    public void getAllPhieuMuon(MutableLiveData<List<PhieuMuon>> data) {
        api.getAllPhieuMuon().enqueue(new Callback<List<PhieuMuon>>() {
            public void onResponse(Call<List<PhieuMuon>> call, Response<List<PhieuMuon>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<PhieuMuon>> call, Throwable t) {}
        });
    }

    public void updateTrangThai(String id, PhieuMuon pm) {
        api.updateTrangThaiPM(id, pm).enqueue(new Callback<PhieuMuon>() {
            public void onResponse(Call<PhieuMuon> call, Response<PhieuMuon> res) {}
            public void onFailure(Call<PhieuMuon> call, Throwable t) {}
        });
    }

    public MutableLiveData<List<PhieuMuon>> getLatestPhieuMuon() {
        MutableLiveData<List<PhieuMuon>> data = new MutableLiveData<>();
        api.getLatestPhieuMuon("ngayMuon.desc",5).enqueue(new Callback<List<PhieuMuon>>() {
            @Override
            public void onResponse(Call<List<PhieuMuon>> call, Response<List<PhieuMuon>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<PhieuMuon>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi gọi API: " + t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }
}
