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


    public SachViewModel() {
        this.sachRepository = new SachRepository();
    }
    public LiveData<List<Sach>> getTop5MostBorrowedBooks() {

       // if (top5Books == null) {
            top5Books = sachRepository.getTop5MostBorrowedBooks();
        //}
        return top5Books;
    }

}
