package com.example.libraryapplication.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.model.TheLoai;
import com.example.libraryapplication.viewmodel.PhieuMuonViewModel;

public class PMForm extends AppCompatActivity {
    TextView bookId,bookTitle,tl,bookQuantity,bookPrice;
    EditText studentIdInput, quantityInput;
    Button addPM;
    ImageView backIcon;
    PhieuMuonViewModel phieuMuonViewModel;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pmform);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_pmform), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //navigate
        View menuInclude = findViewById(R.id.menu_bar);
        View status = findViewById(R.id.status_bar);
        StatusBarHandler.backToHomePage(status, this);
        StatusBarHandler.comeToQLTKTT(status , this);
        StatusBarHandler.updateNameTK(status , this);
        MenuBarHandler.setupMenu(menuInclude, this);
        mapping();
        Sach s=(Sach)getIntent().getSerializableExtra("sach");
        TheLoai theLoai=(TheLoai)getIntent().getSerializableExtra("tl");
        if (s != null) {
            bookId.setText(String.format("ID: %s", s.getMaSach()));
            bookTitle.setText(String.format("%s - %s", s.getTenSach(), s.getTacGia()));
            if (theLoai != null) {
                tl.setText(String.format("Thể loại: %s", theLoai.getTenTL()));
            }
            bookQuantity.setText(String.format("Số lượng còn: %d", s.getSoLuong()));
            bookPrice.setText(String.format("Giá tiền: %s", s.getGiaTien()));
            addPM.setOnClickListener(v->{
                if (bookQuantity.getText().toString().isEmpty()|| studentIdInput.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(studentIdInput.getText().toString().length()<10){
                    Toast.makeText(this, "Mã sinh viên bao gồm 10 số, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có chắc chắn muốn tạo phiếu mượn ?")
                        .setPositiveButton("Đồng ý", (dialog, which) -> {
                            phieuMuonViewModel.createPhieuMuon(s,quantityInput.getText().toString(),studentIdInput.getText().toString());
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
            });
        }
        phieuMuonViewModel.getStatusMessage().observe(this,message->{
            if(message==null)return;

            if(!message.equals("OK")){
                Toast.makeText(this,message,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this,"Tạo phiếu mượn thành công",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PMForm.this,HomePage.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        backIcon.setOnClickListener(v->{
            setResult(RESULT_OK);
            finish();
        });
    }
    private void mapping(){
        bookId=findViewById(R.id.bookId);
        bookTitle=findViewById(R.id.bookTitle);
        tl=findViewById(R.id.tl);
        bookQuantity=findViewById(R.id.bookQuantity);
        bookPrice=findViewById(R.id.bookPrice);
        studentIdInput=findViewById(R.id.studentIdInput);
        quantityInput=findViewById(R.id.quantityInput);
        addPM=findViewById(R.id.addPM);
        backIcon=findViewById(R.id.backIcon);
        phieuMuonViewModel=new ViewModelProvider(this).get(PhieuMuonViewModel.class);
    }
}