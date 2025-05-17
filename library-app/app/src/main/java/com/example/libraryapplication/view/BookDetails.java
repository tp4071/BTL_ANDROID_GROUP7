package com.example.libraryapplication.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.TheLoaiViewModel;

import java.text.SimpleDateFormat;

public class BookDetails extends AppCompatActivity {
    TextView tvMaSach,tvTenSach,tvTacGia,tvNXB,tvNPH,tvSoTrang,tvSoLuong, tvGiaTien,tvMaTL;
    Button borrowBook;
    TheLoaiViewModel theLoaiViewModel;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_book_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        Sach s=(Sach)getIntent().getSerializableExtra("sach");
        if (s != null) {
            SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
            theLoaiViewModel.getTLById(s.getMaTL()).observe(this, theLoai -> {
                tvMaSach.setText(String.format("ID:%s", s.getMaSach()));
                tvTenSach.setText(s.getTenSach());
                tvTacGia.setText(String.format("Tác giả: %s", s.getTacGia()));
                tvNXB.setText(String.format("Nhà xuất bản: %s", s.getNxb()));
                tvNPH.setText(String.format("Ngay phát hành: %s",dateFormat.format(s.getNph())));
                tvSoTrang.setText(String.format("Số trang: %d", s.getSoTrang()));
                tvSoLuong.setText(String.format("Số lượng: %d", s.getSoLuong()));
                tvGiaTien.setText(String.format("Giá tiền: %s VNĐ", s.getGiaTien()));
                tvMaTL.setText(String.format("Thể loại: %s", theLoai.getTenTL()));

                borrowBook.setOnClickListener(v -> {
                    Intent intent = new Intent(BookDetails.this, PMForm.class);
                    intent.putExtra("sach", s);
                    intent.putExtra("theLoai", theLoai);
                    startActivity(intent);
                });
            });
        }

    }
    private void mapping(){
        tvMaSach=findViewById(R.id.tvMaSach);
        tvTenSach=findViewById(R.id.tvTenSach);
        tvTacGia=findViewById(R.id.tvTacGia);
        tvNXB=findViewById(R.id.tvNXB);
        tvNPH=findViewById(R.id.tvNPH);
        tvSoTrang=findViewById(R.id.tvSoTrang);
        tvSoLuong=findViewById(R.id.tvSoLuong);
        tvGiaTien=findViewById(R.id.tvGiaTien);
        tvMaTL=findViewById(R.id.tvMaTL);
        theLoaiViewModel=new ViewModelProvider(this).get(TheLoaiViewModel.class);
        borrowBook=findViewById(R.id.borrowBook);
    }
}