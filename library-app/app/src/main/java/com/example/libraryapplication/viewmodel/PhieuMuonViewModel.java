package com.example.libraryapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.PhieuMuonRepository;
import com.example.libraryapplication.repository.SachRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PhieuMuonViewModel extends ViewModel {
    private final PhieuMuonRepository repository = new PhieuMuonRepository();
    private final SachRepository sachRepository=new SachRepository();
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

    public void createPhieuMuon(Sach s, String sl, String msv) {
        Map<String, Object> body = new HashMap<>();
        body.put("p_so_luong", Integer.valueOf(sl));
        body.put("p_ma_sinh_vien", msv);
        body.put("p_ma_sach", s.getMaSach());
        String maPM = "PM" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        Date ngayMuon = new Date();
        String trangThai = "Chưa trả";
        PhieuMuon pm = new PhieuMuon(maPM, ngayMuon, trangThai, Integer.parseInt(sl), msv, s.getMaSach());
        repository.checkLegit(body, result -> {
            if ("OK".equals(result)) {
                s.setSoLuong(s.getSoLuong()-Integer.valueOf(sl));
                sachRepository.updateSach("eq."+s.getMaSach(),s);
                repository.createPhieuMuon(pm);
                statusMessage.postValue("Tạo phiếu mượn thành công");
            } else statusMessage.postValue(result.replaceAll(",","\n"));
        });

    }

    public void updatePhieuMuon(String maPM, PhieuMuon pm) {
        Sach s=sachRepository.getSachById("eq."+pm.getMaSach()).getValue().get(0);
        s.setSoLuong(s.getSoLuong()+Integer.valueOf(pm.getSoLuongMuon()));
        sachRepository.updateSach("eq."+s.getMaSach(),s);
        repository.updateTrangThai(maPM, pm);
    }
}