package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeRepository {
    private SupabaseApi api = SupabaseClient.getApi();

    public void getSachMuonNhieu(MutableLiveData<List<Sach>> data) {
        api.getSachMuonNhieuNhat().enqueue(new Callback<List<Sach>>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {}
        });
    }

    public void getSachMuonIt(MutableLiveData<List<Sach>> data) {
        api.getSachMuonItNhat().enqueue(new Callback<List<Sach>>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {}
        });
    }

    public void getSoLuongQuaHan(MutableLiveData<Integer> data) {
        api.getSoLuongPhieuMuonQuaHan().enqueue(new Callback<Integer>() {
            public void onResponse(Call<Integer> call, Response<Integer> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<Integer> call, Throwable t) {}
        });
    }

    public void getViPhamChuaThanhToan(MutableLiveData<List<PhieuViPham>> data) {
        api.getViPhamChuaThanhToan().enqueue(new Callback<List<PhieuViPham>>() {
            public void onResponse(Call<List<PhieuViPham>> call, Response<List<PhieuViPham>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<PhieuViPham>> call, Throwable t) {}
        });
    }

}
