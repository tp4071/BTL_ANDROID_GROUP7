package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
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
                if (checkInput()) {
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
        if ( edt_NhapMKcu.getText().toString().trim().equals("") ||
                edt_CapNhatMK.getText().toString().trim().equals("") ||
                edt_XacNhanMKMoi.getText().toString().trim().equals("")  ) {
            Toast.makeText(this, "VUI LÒNG NHẬP ĐẦY ĐỦ THÔNG TIN !!!", Toast.LENGTH_SHORT).show();
            return false ;
        } else if (edt_CapNhatMK.getText().toString().trim().equals(edt_XacNhanMKMoi.getText().toString().trim())) {
            return true ;
        } else {
            Toast.makeText(this, "VUI LÒNG XÁC NHẬN THẬT KỸ MẬT KHẨU MỚI CỦA BẠN !!! ", Toast.LENGTH_SHORT).show();
            return false ;
        }
    }
}