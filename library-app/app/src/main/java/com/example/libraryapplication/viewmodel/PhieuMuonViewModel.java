package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.repository.PhieuMuonRepository;
import com.example.libraryapplication.repository.SachRepository;
import com.example.libraryapplication.repository.PhieuViPhamRepository;

public class PhieuMuonViewModel extends ViewModel{
    public void taoPhieuMuon(Sach sach, String maSinhVien, String ngayMuon, String ngayTra, int soLuongMuon) {
        isLoading.setValue(true);

        // Bước 1: Kiểm tra số lượng mượn
        if (soLuongMuon > sach.getSoLuong()) {
            resultMessage.setValue("Số lượng sách không đủ.");
            isLoading.setValue(false);
            return;
        }

        // Bước 2: Kiểm tra sinh viên có vi phạm chưa thanh toán không
        viPhamRepository.coViPhamChuaThanhToan(maSinhVien, isViPham -> {
            if (isViPham) {
                resultMessage.setValue("Sinh viên đang có vi phạm chưa thanh toán, không thể mượn sách.");
                isLoading.setValue(false);
            } else {
                // Bước 3: Tạo phiếu mượn
                PhieuMuon pm = new PhieuMuon();
                pm.setMaSach(sach.getMaSach());
                pm.setMaSinhVien(maSinhVien);
                pm.setNgayMuon(ngayMuon);
                pm.setNgayTra(ngayTra);
                pm.setTrangThai(false); // chưa trả
                pm.setSoLuong(soLuongMuon);

                phieuMuonRepository.taoPhieuMuon(pm, success -> {
                    if (success) {
                        // Trừ số lượng sách
                        sachRepository.giamSoLuongSach(sach.getMaSach(), soLuongMuon);
                        resultMessage.setValue("Mượn sách thành công.");
                    } else {
                        resultMessage.setValue("Tạo phiếu mượn thất bại.");
                    }
                    isLoading.setValue(false);
                });
            }
        });
    }

}
