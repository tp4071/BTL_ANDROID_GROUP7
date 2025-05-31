package com.example.libraryapplication.view.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;

import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.view.pm.PMForm;
import com.example.libraryapplication.view.components.MenuBarHandler;
import com.example.libraryapplication.view.components.StatusBarHandler;
import com.example.libraryapplication.viewmodel.SachViewModel;
import com.example.libraryapplication.viewmodel.TheLoaiViewModel;

import java.text.SimpleDateFormat;

public class BookDetails extends AppCompatActivity {
    TextView tvMaSach,tvTenSach,tvTacGia,tvNXB,tvNPH,tvSoTrang,tvSoLuong, tvGiaTien,tvMaTL;
    Button borrowBook, btnEdit, btnDelete;
    ImageView backIcon;
    private SachViewModel sachViewModel;
    Sach s;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private TheLoaiViewModel theLoaiViewModel;

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

        // Navigate & mapping
        View menuInclude = findViewById(R.id.menu_bar);
        View status = findViewById(R.id.status_bar);
        StatusBarHandler.backToHomePage(status, this);
        StatusBarHandler.comeToQLTKTT(status , this);
        StatusBarHandler.updateNameTK(status , this);
        MenuBarHandler.setupMenu(menuInclude, this);
        mapping();

        s = (Sach)getIntent().getSerializableExtra("sach");
        if (s != null) {
            loadBookDetails(s);
        }

        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(BookDetails.this, AddAndEditBookActivity.class);
            intent.putExtra("sach", s);
            startActivityForResult(intent,1);
        });
        backIcon.setOnClickListener(v->{
            setResult(RESULT_OK);
            finish();
        });
        btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá sách này không (Xóa sách sẽ đồng thời xóa các phiếu mượn liên quan)?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        sachViewModel.deleteSach(s.getMaSach());
                        setResult(RESULT_OK);
                        finish();
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Sach  s = (Sach) data.getSerializableExtra("sach");
            assert s != null;
            loadBookDetails(s);
        }
    }
    private void loadBookDetails(Sach s) {
        theLoaiViewModel.getTLById(s.getMaTL()).observe(this, theLoai -> {
            tvMaSach.setText(String.format("ID:%s", s.getMaSach()));
            tvTenSach.setText(s.getTenSach());
            tvTacGia.setText(String.format("Tác giả: %s", s.getTacGia()));
            tvNXB.setText(String.format("Nhà xuất bản: %s", s.getNxb()));
            tvNPH.setText(String.format("Ngay phát hành: %s", dateFormat.format(s.getNph())));
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
        sachViewModel = new ViewModelProvider(this).get(SachViewModel.class);
        borrowBook=findViewById(R.id.borrowBook);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        backIcon=findViewById(R.id.backIcon);
    }
}
