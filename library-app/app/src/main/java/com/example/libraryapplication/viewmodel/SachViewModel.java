package com.example.libraryapplication.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.SachRepository;

import java.util.List;

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
        if (searchResult == null) {
            searchResult = sachRepository.searchSach(keyword);
        }
        return searchResult;
    }

}
