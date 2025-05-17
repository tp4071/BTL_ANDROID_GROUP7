package com.example.libraryapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.TheLoai;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheLoaiRepository {
    private final SupabaseApi api = SupabaseClient.getApi();
    public TheLoaiRepository(){}
    public MutableLiveData<TheLoai> getTLByID(String id) {
        MutableLiveData<TheLoai> data=new MutableLiveData<>();
        api.getTLByID(id).enqueue(new Callback<>() {
            public void onResponse(Call<TheLoai> call, Response<TheLoai> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<TheLoai> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<TheLoai>> getTheLoai() {
        MutableLiveData<List<TheLoai>> data=new MutableLiveData<>();
        api.getTheLoai().enqueue(new Callback<List<TheLoai>>() {
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {data.postValue(null);}
        });
        return data;
    }
}
