package com.example.libraryapplication.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.libraryapplication.R;

import com.example.libraryapplication.model.Sach;

public class BookDetails extends AppCompatActivity {
    TextView tvMaSach,tvTenSach,tvTacGia,tvNXB,tvNPH,tvSoTrang,tvSoLuong, tvGiaTien,tvMaTL;
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
        tvMaSach.setText("ID:"+s.getMaSach());
        tvTenSach.setText(s.getTenSach());
        tvTacGia.setText("Tác giả: "+s.getTacGia());
        tvNXB.setText("Nhà xuất bản: "+s.getNxb());
        tvNPH.setText("Nhà phát hành: "+s.getNph());
        tvSoTrang.setText("Số trang: "+s.getSoTrang());
        tvSoLuong.setText("Số lượng: "+s.getSoLuong());
        tvGiaTien.setText("Giá tiền: "+s.getGiaTien());
        tvMaTL.setText("Mã thể loại: "+s.getMaTL());
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
    }
}