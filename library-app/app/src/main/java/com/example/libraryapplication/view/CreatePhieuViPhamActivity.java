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

            PhieuViPham vp = new PhieuViPham(null, tienPhat, 0, trangThai, kieuVP, maPM);
            viewModel.createPhieuViPham(vp);

            Toast.makeText(this, "Đã lưu phiếu vi phạm", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnCancel.setOnClickListener(v -> finish());
    }
}
