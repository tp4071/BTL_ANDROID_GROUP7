package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuMuonRepository {
    private SupabaseApi api = SupabaseClient.getApi();

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
}
