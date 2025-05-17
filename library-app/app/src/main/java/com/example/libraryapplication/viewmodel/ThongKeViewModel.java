package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.ThongKeRepository;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThongKeViewModel extends ViewModel {
    private ThongKeRepository thongKeRepository;
    private MutableLiveData<List<Sach>> top5Books;
    private MutableLiveData<List<Map<String, Object>>> pmTK;
    private MutableLiveData<List<Map<String, Object>>> pvpTK;

    public ThongKeViewModel(){this.thongKeRepository=new ThongKeRepository();}
    public void getTK(String start,String end){
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> body = new HashMap<>();
        body.put("p_ngay_bat_dau", start);
        body.put("p_ngay_ket_thuc", end);
        top5Books=thongKeRepository.getTopSach(body);
        pmTK=thongKeRepository.thongKePM(body);
        pvpTK=thongKeRepository.thongKePhieuViPhamTheoNgay(body);
    }
    public LiveData<List<Sach>> getTop5Books(){
        return top5Books;
    }
    public LiveData<List<Map<String, Object>>> getPmTK(){
        return pmTK;
    }
    public LiveData<List<Map<String, Object>>> getPvpTK(){
        return pvpTK;
    }

}
