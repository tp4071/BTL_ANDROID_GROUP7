package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.repository.PhieuMuonRepository;

import java.util.List;

public class PMViewModel extends ViewModel {
    private PhieuMuonRepository phieuMuonRepository;
    private MutableLiveData<List<PhieuMuon>> top5PhieuMuon;
    public PMViewModel(){
        this.phieuMuonRepository=new PhieuMuonRepository();
    }
    public LiveData<List<PhieuMuon>> getLatestPhieuMuon() {

        if (top5PhieuMuon == null) {
            top5PhieuMuon = phieuMuonRepository.getLatestPhieuMuon();
        }
        return top5PhieuMuon;
    }


}
