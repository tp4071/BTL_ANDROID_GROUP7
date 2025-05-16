package com.example.libraryapplication.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;

import java.util.Date;
import java.util.Random;

public class CreatePhieuViPhamActivity extends AppCompatActivity {
    private PhieuViPhamViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_violation);

        viewModel = new ViewModelProvider(this).get(PhieuViPhamViewModel.class);

        EditText edtMaPM = findViewById(R.id.edtBorrowId);
        EditText edtFine = findViewById(R.id.edtFine);
        EditText edtTrangThai = findViewById(R.id.edtStatus);
        EditText edtKieuVP = findViewById(R.id.edtType);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(v -> {
            String maPM = edtMaPM.getText().toString();
            double tienPhat = Double.parseDouble(edtFine.getText().toString());
            String trangThai = edtTrangThai.getText().toString();
            String kieuVP = edtKieuVP.getText().toString();

            String maPhieuVP = generateMaPhieuVP();
            Date createDate = new Date();

            PhieuViPham vp = new PhieuViPham(maPhieuVP, tienPhat, 0, trangThai, kieuVP, maPM, createDate);
            viewModel.createPhieuViPham(vp);

            Toast.makeText(this, "Đã lưu phiếu vi phạm với mã: " + maPhieuVP, Toast.LENGTH_SHORT).show();
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
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

