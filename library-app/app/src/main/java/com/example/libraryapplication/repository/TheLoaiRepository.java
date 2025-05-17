package com.example.libraryapplication.repository;

import android.util.Log;

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
    public MutableLiveData<TheLoai> getTLByID(String maTL) {
        MutableLiveData<TheLoai> data=new MutableLiveData<>();
        api.getTLByID("eq."+maTL).enqueue(new Callback<>() {
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> res) {
                if (res.isSuccessful()) {
                    data.setValue(res.body().get(0));
                }
            }
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                Log.d("Error while call API",t.getMessage());
                data.postValue(null);
            }
        });
        return data;
    }

    public MutableLiveData<List<TheLoai>> getTheLoai() {
        MutableLiveData<List<TheLoai>> data=new MutableLiveData<>();
        api.getTheLoai().enqueue(new Callback<>() {
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> res) {

                if (res.isSuccessful()) data.setValue(res.body());
                else data.setValue(null);
            }

            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                data.postValue(null);
            }
        });
        return data;
    }
}
