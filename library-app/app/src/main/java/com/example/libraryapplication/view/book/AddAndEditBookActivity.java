package com.example.libraryapplication.view.book;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.model.TheLoai;
import com.example.libraryapplication.repository.SachRepository;
import com.example.libraryapplication.viewmodel.SachViewModel;
import com.example.libraryapplication.viewmodel.TheLoaiViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AddAndEditBookActivity extends AppCompatActivity {
    private EditText etTenSach, etTacGia, etNXB, etNPH, etSoTrang, etSoLuong, etGiaTien;
    private Spinner spinnerTheLoai;
    private Button btnSaveBook, btnCancel;
    private TheLoaiViewModel theLoaiViewModel;
    private SachRepository sachRepository = new SachRepository();
    private List<TheLoai> listTheLoai;
    private SachViewModel sachViewModel;
    Sach sachEditing = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_and_edit_book);

        mappingViews();
        sachViewModel = new ViewModelProvider(this).get(SachViewModel.class);
        setupTheLoaiSpinner();
        setupDatePicker();
        setupButtons();
        
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("sach")) {
            sachEditing = (Sach) intent.getSerializableExtra("sach");
        }

    }

    private void fillBookData(Sach sach) {
        etTenSach.setText(sach.getTenSach());
        etTacGia.setText(sach.getTacGia());
        etNXB.setText(sach.getNxb());
        etSoTrang.setText(String.valueOf(sach.getSoTrang()));
        etSoLuong.setText(String.valueOf(sach.getSoLuong()));
        etGiaTien.setText(String.valueOf(sach.getGiaTien()));

        // Gán ngày vào EditText etNPH (giả sử bạn dùng EditText + DatePickerDialog)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        etNPH.setText(sdf.format(sach.getNph()));

        if (listTheLoai != null) {
            for (int i = 0; i < listTheLoai.size(); i++) {
                if (listTheLoai.get(i).getMaTL().equals(sach.getMaTL())) {
                    spinnerTheLoai.setSelection(i);
                    break;
                }
            }
        }
    }


    private void mappingViews() {
        etTenSach = findViewById(R.id.etTenSach);
        etTacGia = findViewById(R.id.etTacGia);
        etNXB = findViewById(R.id.etNXB);
        etNPH = findViewById(R.id.etNPH);
        etSoTrang = findViewById(R.id.etSoTrang);
        etSoLuong = findViewById(R.id.etSoLuong);
        etGiaTien = findViewById(R.id.etGiaTien);
        spinnerTheLoai = findViewById(R.id.spinnerTheLoai);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void setupDatePicker() {
        etNPH.setFocusable(false);
        etNPH.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddAndEditBookActivity.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                etNPH.setText(date);
            }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void setupTheLoaiSpinner() {
        theLoaiViewModel = new ViewModelProvider(this).get(TheLoaiViewModel.class);
        theLoaiViewModel.getTheLoai().observe(this, theLoais -> {
            if (theLoais != null) {
                listTheLoai = theLoais;
                List<String> tenTLs = new ArrayList<>();
                for (TheLoai tl : listTheLoai) {
                    tenTLs.add(tl.getTenTL());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tenTLs);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTheLoai.setAdapter(adapter);
                if (sachEditing != null) {
                    fillBookData(sachEditing);
                }
            }
        });

    }

    private void setupButtons() {
        btnSaveBook.setOnClickListener(v -> {
            try {
                saveBook();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveBook() throws ParseException {
        if (!validateInput()) return;

        // Lấy dữ liệu đầu vào
        String tenSach = etTenSach.getText().toString().trim();
        String tacGia = etTacGia.getText().toString().trim();
        String nxb = etNXB.getText().toString().trim();
        String nphStr = etNPH.getText().toString().trim();
        int soTrang = Integer.parseInt(etSoTrang.getText().toString().trim());
        int soLuong = Integer.parseInt(etSoLuong.getText().toString().trim());
        float giaTien = Float.parseFloat(etGiaTien.getText().toString().trim());
        String maTL = listTheLoai.get(spinnerTheLoai.getSelectedItemPosition()).getMaTL();
        Date ngayPhatHanh = new SimpleDateFormat("dd/MM/yyyy").parse(nphStr);

        // Tạo sách
        if (sachEditing != null) {
            // Cập nhật
            sachEditing.setTenSach(tenSach);
            sachEditing.setTacGia(tacGia);
            sachEditing.setNxb(nxb);
            sachEditing.setNph(ngayPhatHanh);
            sachEditing.setSoLuong(soLuong);
            sachEditing.setSoTrang(soTrang);
            sachEditing.setGiaTien(giaTien);
            sachEditing.setMaTL(maTL);

            sachViewModel.updateSach(sachEditing.getMaSach(), sachEditing);
            Intent intent = new Intent(AddAndEditBookActivity.this, BookDetails.class);
            intent.putExtra("sach", sachEditing);
            //startActivity(intent);
            Toast.makeText(this, "Cập nhật sách thành công!", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();
        } else {
            // Thêm mới
            String maSach = generateMaSach();
            Sach newSach = new Sach(maSach, tenSach, nxb, ngayPhatHanh, soLuong, soTrang, tacGia, giaTien, maTL);
            sachViewModel.createSach(newSach);
            Toast.makeText(this, "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddAndEditBookActivity.this, BookManagedment.class);
            setResult(RESULT_OK, intent);
        }

        finish();
    }

    private boolean validateInput() {
        boolean hasError = false;

        String tenSach = etTenSach.getText().toString().trim();
        String tacGia = etTacGia.getText().toString().trim();
        String nxb = etNXB.getText().toString().trim();
        String nph = etNPH.getText().toString().trim();
        String soTrangStr = etSoTrang.getText().toString().trim();
        String soLuongStr = etSoLuong.getText().toString().trim();
        String giaTienStr = etGiaTien.getText().toString().trim();

        if (tenSach.length() < 2) {
            etTenSach.setError("Tên sách phải từ 2 ký tự trở lên");
            hasError = true;
        }
        if (tacGia.length() < 2) {
            etTacGia.setError("Tác giả phải từ 2 ký tự trở lên");
            hasError = true;
        }
        if (nxb.length() < 2) {
            etNXB.setError("Nhà xuất bản phải từ 2 ký tự trở lên");
            hasError = true;
        }
        if (nph.isEmpty()) {
            etNPH.setError("Vui lòng chọn ngày phát hành");
            hasError = true;
        }

        try {
            int soTrang = Integer.parseInt(soTrangStr);
            if (soTrang <= 0) throw new Exception();
        } catch (Exception e) {
            etSoTrang.setError("Số trang phải là số nguyên dương");
            hasError = true;
        }

        try {
            int soLuong = Integer.parseInt(soLuongStr);
            if (soLuong <= 0) throw new Exception();
        } catch (Exception e) {
            etSoLuong.setError("Số lượng phải là số nguyên dương");
            hasError = true;
        }

        try {
            float giaTien = Float.parseFloat(giaTienStr);
            if (giaTien < 0) throw new Exception();
        } catch (Exception e) {
            etGiaTien.setError("Giá tiền phải là số hợp lệ");
            hasError = true;
        }

        if (listTheLoai == null || listTheLoai.isEmpty()) {
            Toast.makeText(this, "Chưa có thể loại sách để chọn", Toast.LENGTH_SHORT).show();
            hasError = true;
        }

        return !hasError;
    }



    private String generateMaSach() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder("MS");
        Random rnd = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}