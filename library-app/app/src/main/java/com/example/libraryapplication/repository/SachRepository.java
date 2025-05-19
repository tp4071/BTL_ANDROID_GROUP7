package com.example.libraryapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


    public MutableLiveData<List<Sach>> searchSach(Map<String,String> body) {
        MutableLiveData<List<Sach>> data = new MutableLiveData<>();
        api.searchSach(body).enqueue(new Callback<>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                if (res.isSuccessful()) data.setValue(res.body());
            }

            public void onFailure(Call<List<Sach>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<Sach>> getSachById(String id) {
        MutableLiveData<List<Sach>> data=new MutableLiveData<>();

        api.getSachById(id).enqueue(new Callback<>() {
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> res) {
                Log.d("Body", String.valueOf(res.isSuccessful()));
                if (res.isSuccessful()) {
                    Log.d("Data",res.body().get(0).toString());
                    data.setValue(res.body());
                }

            }
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                Log.d("Error while call",t.getMessage());
                data.postValue(null);
            }
        });
        return data;
    }

    public void createSach(Sach sach) {
        api.createSach(sach).enqueue(new Callback<Sach>() {
            public void onResponse(Call<Sach> call, Response<Sach> res) {

                if (res.isSuccessful()) {
                    Log.d("API_SUCCESS", "Tạo sách thành công: " + res.body());
                } else {
                    Log.e("API_ERROR", "Mã lỗi: " + res.code() + ", message: " + res.message());
                    try {
                        Log.e("API_ERROR_BODY", "Chi tiết: " + res.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            public void onFailure(Call<Sach> call, Throwable t) {}
        });
    }

    public void updateSach(String id, Sach sach) {
        String filter = "eq." + id;

        api.updateSach(filter, sach).enqueue(new Callback<List<Sach>>() {
            @Override
            public void onResponse(Call<List<Sach>> call, Response<List<Sach>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Log.d("API_SUCCESS", "Sách đã được cập nhật: " + response.body().get(0));
                } else {
                    Log.e("API_FAIL", "Cập nhật thất bại. Mã lỗi: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Sach>> call, Throwable t) {
                Log.e("API_ERROR", "Lỗi kết nối: " + t.getMessage());
            }
        });
    }



    public void deleteSach(String id) {
        api.deleteSach("eq." + id).enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> res) {

            }
            public void onFailure(Call<Void> call, Throwable t) {
                // xử lý thất bại
            }
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
