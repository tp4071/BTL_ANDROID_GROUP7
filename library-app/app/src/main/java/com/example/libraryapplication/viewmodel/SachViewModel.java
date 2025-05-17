package com.example.libraryapplication.viewmodel;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.SachRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SachViewModel extends ViewModel {
    private SachRepository sachRepository;
    private MutableLiveData<List<Sach>> top5Books;
    private MutableLiveData<List<Sach>> searchResult;

    public SachViewModel() {this.sachRepository = new SachRepository();}
    public LiveData<List<Sach>> getLatestBook() {

        if (top5Books == null) {
            top5Books = sachRepository.getLatestBook();
        }
        return top5Books;
    }
    public LiveData<List<Sach>> getSearchResult(String keyword){
        Map<String, String> body = new HashMap<>();
        body.put("p_tu_khoa", keyword);
        searchResult = sachRepository.searchSach(body);
        return searchResult;
    }

}
