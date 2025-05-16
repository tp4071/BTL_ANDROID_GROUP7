package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeRepository {
    private SupabaseApi api = SupabaseClient.getApi();

    public MutableLiveData<List<Sach>> getTopSach(Map<String, String> body) {
        MutableLiveData<List<Sach>> data= new MutableLiveData<>();
        api.getTopSach(body).enqueue(new Callback<>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }

            public void onFailure(Call<List<Sach>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<Map<String, Object>>> thongKePhieuViPhamTheoNgay(Map<String, String> body) {
        MutableLiveData<List<Map<String, Object>>> data=new MutableLiveData<>();
        api.thongKePhieuViPhamTheoNgay(body).enqueue(new Callback<>() {
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }

            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<Map<String, Object>>> thongKePM(Map<String, String> body) {
        MutableLiveData<List<Map<String, Object>>> data=new MutableLiveData<>();
        api.thongKePM(body).enqueue(new Callback<>() {
            public void onResponse(Call<List<Map<String, Object>>> call, Response<List<Map<String, Object>>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<Map<String, Object>>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

}
