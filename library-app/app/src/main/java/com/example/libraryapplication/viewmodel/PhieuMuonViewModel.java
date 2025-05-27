package com.example.libraryapplication.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.PhieuMuonRepository;
import com.example.libraryapplication.repository.SachRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class PhieuMuonViewModel extends ViewModel {
    private final PhieuMuonRepository repository = new PhieuMuonRepository();
    private final SachRepository sachRepository=new SachRepository();
    private final MutableLiveData<List<PhieuMuon>> listPhieuMuon = new MutableLiveData<>();
    private MutableLiveData<List<PhieuMuon>> top5PhieuMuon;
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private static final int NGAY_TOI_DA = 14; // 2 tuần

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public LiveData<List<PhieuMuon>> getLatestPhieuMuon() {

        if (top5PhieuMuon == null) {
            top5PhieuMuon = repository.getLatestPhieuMuon();
        }
        return top5PhieuMuon;
    }

    private List<PhieuMuon> filterPhieuMuonConHan(List<PhieuMuon> danhSach) {
        List<PhieuMuon> ketQua = new java.util.ArrayList<>();
        Date today = new Date();

        for (PhieuMuon pm : danhSach) {
            Date ngayMuon = pm.getNgayMuon();
            if (ngayMuon == null) continue;

            if (pm.getTrangThai().equals("Chưa trả")) {
                ketQua.add(pm);
            } else {
                Log.d("View ID phiếu mượn quá hạn : " , pm.getMaPM());
            }
        }

        return ketQua;
    }

    public void loadPhieuMuon() {
        repository.getAllPhieuMuon(new MutableLiveData<List<PhieuMuon>>() {
            @Override
            public void setValue(List<PhieuMuon> danhSach) {
                // Lọc danh sách trước khi đẩy vào LiveData thật sự
                List<PhieuMuon> ketQua = filterPhieuMuonConHan(danhSach);
                listPhieuMuon.postValue(ketQua);
            }
        });
    }

    public LiveData<List<PhieuMuon>> getListPhieuMuon() {
        return listPhieuMuon;
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
        repository.updateTrangThai(maPM, pm);
    }

    private String generateMaPhieuVP() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("VP");
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}