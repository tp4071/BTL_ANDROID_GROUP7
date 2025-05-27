package com.example.libraryapplication.view.thuthu;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.libraryapplication.R;
import com.example.libraryapplication.repository.ThuThuRepository;
import com.example.libraryapplication.view.homePage.HomePage;
import com.example.libraryapplication.viewmodel.AccountThuThuViewModel;

public class Login extends AppCompatActivity {
    private final ThuThuRepository thuThuRepository = new ThuThuRepository() ;
    private final AccountThuThuViewModel accountThuThuViewModel = new AccountThuThuViewModel();
    EditText edt_TenTaiKhoan , edt_MatKhau ;
    Button btn_DangNhap , btn_HuyDangNhap;
    Intent intent ;
    ActivityResultLauncher activityResultLauncher ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Thiết lập observer CHỈ MỘT LẦN
        accountThuThuViewModel.getLoginResult().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                Toast.makeText(getApplicationContext(), "ĐĂNG NHẬP THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, HomePage.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "ĐĂNG NHẬP THẤT BẠI", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myMapping() ;

        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTK = edt_TenTaiKhoan.getText().toString().trim() ;
                String matKhau = edt_MatKhau.getText().toString().trim() ;
                accountThuThuViewModel.dangNhapThuThu(tenTK , matKhau , Login.this);
                // Quan sát kết quả login
//                accountThuThuViewModel.getLoginResult().observe(Login.this, isSuccess -> {
//                    if (isSuccess != null && isSuccess) {
//                        Toast.makeText(getApplicationContext(), "ĐĂNG NHẬP THÀNH CÔNG", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Login.this, HomePage.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "ĐĂNG NHẬP THẤT BẠI", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

        btn_HuyDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    private void myMapping() {
        edt_TenTaiKhoan = findViewById(R.id.edt_TenTaiKhoan) ;
        edt_MatKhau = findViewById(R.id.edt_MatKhau) ;
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        btn_HuyDangNhap = findViewById(R.id.btn_HuyDangNhap) ;
    }

}