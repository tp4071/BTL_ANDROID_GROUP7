package com.example.libraryapplication.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.repository.PhieuViPhamRepository;

import java.util.List;

public class PhieuViPhamViewModel extends ViewModel {
    private final MutableLiveData<List<PhieuViPham>> list = new MutableLiveData<>();
    private final PhieuViPhamRepository repo = new PhieuViPhamRepository();

    public MutableLiveData<List<PhieuViPham>> getPhieuViPhamList() {
        return list;
    }

    public void loadPhieuViPham() {
        repo.getAll(list);
    }

    public void createPhieuViPham(PhieuViPham vp) {
        repo.create(vp, this::loadPhieuViPham);
    }

    public void updateTrangThai(String id, PhieuViPham vp, Runnable onSuccess) {
        repo.updateTrangThai(id, vp, onSuccess);
    }

    public void search(String keyword) {
        repo.searchByMaPM(keyword, list);
    }
}

