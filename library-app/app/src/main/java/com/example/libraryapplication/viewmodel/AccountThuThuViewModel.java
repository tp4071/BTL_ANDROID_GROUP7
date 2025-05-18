package com.example.libraryapplication.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.ThuThu;
import com.example.libraryapplication.repository.ThuThuRepository;

public class AccountThuThuViewModel extends ViewModel {
    public AccountThuThuViewModel() {
    }
    private final ThuThuRepository thuThuRepository = new ThuThuRepository();
    private final MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    public LiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void dangNhapThuThu(String maTK , String matKhau , Context context) {
        thuThuRepository.dangNhapThuThu(maTK , matKhau , context , loginResult);
    }

    public void updateThongTin(String maTK , ThuThu tt) {
        thuThuRepository.updateThongTin(maTK , tt );
    }
}
