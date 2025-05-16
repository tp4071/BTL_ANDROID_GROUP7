package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.repository.PhieuMuonRepository;

import java.util.List;

public class PhieuMuonViewModel extends ViewModel {
    private final PhieuMuonRepository repository = new PhieuMuonRepository();
    private final MutableLiveData<List<PhieuMuon>> listPhieuMuon = new MutableLiveData<>();
//    private final MutableLiveData<PhieuMuon> PhieuMuonUpdate = new MutableLiveData<>() ;
    public LiveData<List<PhieuMuon>> getListPhieuMuon() {
        return listPhieuMuon;
    }

    public void loadPhieuMuon() {
        repository.getAllPhieuMuon(listPhieuMuon); // Gọi từ Repository
    }

    public void updatePhieuMuon(String maPM , PhieuMuon pm) {
        repository.updateTrangThai(maPM , pm);
    }

//    public MutableLiveData<PhieuMuon> getPhieuMuonUpdate() {
//        return PhieuMuonUpdate;
//    }
}