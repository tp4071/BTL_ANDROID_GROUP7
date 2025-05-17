package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.repository.PhieuMuonRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PhieuMuonViewModel extends ViewModel {
    private final PhieuMuonRepository repository = new PhieuMuonRepository();
    private final MutableLiveData<List<PhieuMuon>> listPhieuMuon = new MutableLiveData<>();
    private MutableLiveData<List<PhieuMuon>> top5PhieuMuon;
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<List<PhieuMuon>> getListPhieuMuon() {
        return listPhieuMuon;
    }

    public LiveData<List<PhieuMuon>> getLatestPhieuMuon() {

        if (top5PhieuMuon == null) {
            top5PhieuMuon = repository.getLatestPhieuMuon();
        }
        return top5PhieuMuon;
    }

    public void loadPhieuMuon() {
        repository.getAllPhieuMuon(listPhieuMuon); // Gọi từ Repository
    }

    public void createPhieuMuon(String maSach, String sl, String msv) {
        Map<String, Object> body = new HashMap<>();
        body.put("p_so_luong ", Integer.valueOf(sl));
        body.put("p_ma_sinh_vien", msv);
        body.put("p_ma_sach", maSach);
        String maPM = "PM" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        Date ngayMuon = new Date();
        String trangThai = "Chưa trả";
        PhieuMuon pm = new PhieuMuon(maPM, ngayMuon, trangThai, Integer.parseInt(sl), msv, maSach);
        repository.checkLegit(body, result -> {
            if ("OK".equals(result)) {
                repository.createPhieuMuon(pm);
                statusMessage.postValue("Tạo phiếu mượn thành công");
            } else statusMessage.postValue(result.replaceAll(",","\n"));
        });
    }

    public void updatePhieuMuon(String maPM, PhieuMuon pm) {
        repository.updateTrangThai(maPM, pm);
    }
}