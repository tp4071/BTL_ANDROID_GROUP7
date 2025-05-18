package com.example.libraryapplication.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.ThuThu;
import com.example.libraryapplication.sharedPreferences.SessionManager;
import com.example.libraryapplication.viewmodel.AccountThuThuViewModel;

public class AccountManagedment extends AppCompatActivity {
    private final AccountThuThuViewModel viewModel = new AccountThuThuViewModel() ;
    EditText edt_NhapMKcu , edt_CapNhatMK , edt_XacNhanMKMoi ;
    Button btn_CapNhatMK , btn_HuyCapNhatMK ;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_managedment);

        View menuInclude = findViewById(R.id.menuBar);
        View status = findViewById(R.id.status_bar);
        StatusBarHandler.backToHomePage(status, this);
        StatusBarHandler.comeToQLTKTT(status , this);
        StatusBarHandler.updateNameTK(status , this);
        MenuBarHandler.setupMenu(menuInclude, this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myMapping() ;

        btn_CapNhatMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager sessionManager = new SessionManager(AccountManagedment.this);
                String maTK = sessionManager.getMaTK().trim() ;
                String tenTK = sessionManager.getTenTK().trim() ;
                String matKhau = sessionManager.getMatKhau().trim();
                Log.d("Mã tài khoản" , "maTK : " + maTK);
                Log.d("Tên tài khoản log ra ","Tên tài khoản : " + tenTK) ;
                Log.d("Mật khẩu cũ " , "Mật khẩu cũ : " + matKhau) ;
                if (checkInput()) {
                    ThuThu tt = new ThuThu(maTK , tenTK , edt_XacNhanMKMoi.getText().toString().trim()) ;
                    sessionManager.saveThuThu(tt);
                    viewModel.updateThongTin(maTK , tt);
                    Toast.makeText(AccountManagedment.this, "BẠN ĐÃ ĐỔI MẬT KHẨU THÀNH CÔNG !!! ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void myMapping() {
        edt_NhapMKcu = findViewById(R.id.edt_NhapMKcu);
        edt_CapNhatMK = findViewById(R.id.edt_CapNhatMK);
        edt_XacNhanMKMoi = findViewById(R.id.edt_XacNhanMKMoi);
        btn_CapNhatMK = findViewById(R.id.btn_CapNhatMK);
        btn_HuyCapNhatMK = findViewById(R.id.btn_HuyCapNhatMK);
    }

    private boolean checkInput(){
        String mkCu = edt_NhapMKcu.getText().toString().trim();
        Log.d("mkCu" , "Hàm checkInput mkCu : " + mkCu);
        String mkMoi = edt_CapNhatMK.getText().toString().trim();
        Log.d("mkMoi" , "Hàm checkInput mkMoi : " + mkMoi);
        String xacNhanMK = edt_XacNhanMKMoi.getText().toString().trim();
        Log.d("xacNhanMK" , "Hàm checkInput xacNhanMK : " + xacNhanMK);
        SessionManager sessionManager = new SessionManager(AccountManagedment.this);
        String mkSession = sessionManager.getMatKhau().trim();
        Log.d("MK_CHECK", "MK Nhập: [" + mkCu + "] - MK session: [" + mkSession + "]");

        if (mkCu.isEmpty() || mkMoi.isEmpty() || xacNhanMK.isEmpty()) {
            Toast.makeText(this, "VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN !!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mkCu.equals(sessionManager.getMatKhau().trim())) {
            Toast.makeText(this, "MẬT KHẨU CŨ MÀ BẠN NHẬP KHÔNG ĐÚNG !!! ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!mkMoi.equals(xacNhanMK)) {
            Toast.makeText(this, "MẬT KHẨU MỚI VÀ XÁC NHẬN KHÔNG KHỚP !!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}