package com.example.libraryapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SachRepository {
    private SupabaseApi api = SupabaseClient.getApi();
    public SachRepository(){

    }
    public void getAllSach(MutableLiveData<List<Sach>> data) {
        api.getAllSach().enqueue(new Callback<List<Sach>>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {}
        });
    }


    public MutableLiveData<List<Sach>> searchSach(String keyword) {
        MutableLiveData<List<Sach>> data = new MutableLiveData<>();
        api.searchSach(keyword).enqueue(new Callback<List<Sach>>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());

            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {data.postValue(null);}
        });
        return data;
    }

    public void getSachById(String id, MutableLiveData<List<Sach>> data) {
        api.getSachById(id).enqueue(new Callback<List<Sach>>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {}
        });
    }

    public void createSach(Sach sach) {
        api.createSach(sach).enqueue(new Callback<Sach>() {
            public void onResponse(Call<Sach> call, Response<Sach> res) {}
            public void onFailure(Call<Sach> call, Throwable t) {}
        });
    }

    public void updateSach(String id, Sach sach) {
        api.updateSach(id, sach).enqueue(new Callback<Sach>() {
            public void onResponse(Call<Sach> call, Response<Sach> res) {}
            public void onFailure(Call<Sach> call, Throwable t) {}
        });
    }

    public void deleteSach(String id) {
        api.deleteSach(id).enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> res) {}
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }

    public MutableLiveData<List<Sach>> getLatestBook() {
        MutableLiveData<List<Sach>> data = new MutableLiveData<>();
        api.getLatestBook("nph.desc",5).enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.postValue(response.body());
                } else {
                    data.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi gọi API: " + t.getMessage());
                data.postValue(null);
            }
        });

        return data;
    }



}
