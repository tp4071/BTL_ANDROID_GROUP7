package com.example.libraryapplication.viewmodel;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.PhieuMuonRepository;
import com.example.libraryapplication.repository.SachRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SachViewModel extends ViewModel {
    private final SachRepository sachRepository;
    private final PhieuMuonRepository phieuMuonRepository;
    private MutableLiveData<List<Sach>> top5Books;
    private MutableLiveData<List<Sach>> searchResult;
    private final MutableLiveData<List<Sach>> listBook = new MutableLiveData<>();

    public SachViewModel() {
        this.sachRepository = new SachRepository();
        this.phieuMuonRepository = new PhieuMuonRepository() ;
    }


    public LiveData<List<Sach>> getLatestBook() {

        top5Books = sachRepository.getLatestBook();
        return top5Books;
    }

    public MutableLiveData<List<Sach>> getAll(){return listBook;}
    public void loadBookLists(){
        sachRepository.getAllSach(listBook);
    }
    public LiveData<List<Sach>> getSearchResult(String keyword){
        Map<String, String> body = new HashMap<>();
        body.put("p_tu_khoa", keyword);
        searchResult = sachRepository.searchSach(body);
        return searchResult;
    }

    public void createSach(Sach sach) {
        sachRepository.createSach(sach);
    }

    public void updateSach(String id, Sach sach) {
        sachRepository.updateSach(id, sach);
    }

    public void deleteSach(String id) {
        sachRepository.deleteSach(id);
    }

    public LiveData<List<Sach>> getSachById(String id) {
        return sachRepository.getSachById(id);
    }

}
