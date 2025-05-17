package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.TheLoai;
import com.example.libraryapplication.repository.TheLoaiRepository;

import java.util.List;

public class TheLoaiViewModel extends  ViewModel{
    private TheLoaiRepository theLoaiRepository;
    private MutableLiveData<TheLoai> theLoai;
    private MutableLiveData<List<TheLoai>> theLoais;
    public TheLoaiViewModel(){this.theLoaiRepository=new TheLoaiRepository();}
    public LiveData<TheLoai> getTLById(String id){
        if(theLoai==null)theLoai=theLoaiRepository.getTLByID(id);
        return theLoai;
    }
    public LiveData<List<TheLoai>> getTheLoai(){
        if(theLoais==null)theLoais=theLoaiRepository.getTheLoai();
        return theLoais;
    }
}
