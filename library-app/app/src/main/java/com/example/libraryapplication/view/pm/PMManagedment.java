package com.example.libraryapplication.view.pm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;
import com.example.libraryapplication.view.components.MenuBarHandler;
import com.example.libraryapplication.view.components.StatusBarHandler;
import com.example.libraryapplication.viewmodel.PhieuMuonViewModel;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PMManagedment extends AppCompatActivity {
    private SupabaseApi api = SupabaseClient.getApi();
    TextView txt_HienThiMaPM ;
    EditText edt_CapNhatTrangThaiPM ;
    Spinner spinnerTrangThaiPM ;
    Button btn_CapNhatTrangThaiPM , btn_HuyCapNhatTrangThaiPM ;
    List<PhieuMuon> listPM ;
    List<PhieuMuon> listPMKoCoPVP ;
    ArrayAdapter arrayAdapter ;
    ListView lv_DanhSachPhieuMuon ;
    Intent intent ;
    ImageView backIconPMManagedment;
    ActivityResultLauncher activityResultLauncher ;
    private PhieuMuonViewModel viewModel ;
//    private PhieuViPhamViewModel phieuViPhamViewModel ;
    public int currentPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pmmanagedment);

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

        listPM = new ArrayList<>() ;

        // Tham chiếu giao diện
        myMapping();

        // Get toàn bộ phiếu mượn
        hienThiDanhSachPhieuMuon();

        // Event for clicking one item on listView PhieuMuon
        lv_DanhSachPhieuMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition=position;
                myDisplay(listPM.get(position));
            }
        });

        // Button for updating status PhieuMuon
        btn_CapNhatTrangThaiPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( txt_HienThiMaPM.getText().toString().trim().isEmpty() || spinnerTrangThaiPM.getSelectedItem().toString().isEmpty() ) {
                    Toast.makeText(PMManagedment.this, "VUI LÒNG CHỌN 1 PHIẾU MƯỢN ĐỂ THỰC HIỆN THAO TÁC", Toast.LENGTH_SHORT).show();
                } else {
                    PhieuMuon pmUpdate = listPM.get(currentPosition);
//                String trangThai = edt_CapNhatTrangThaiPM.getText().toString();
                    String trangThai = spinnerTrangThaiPM.getSelectedItem().toString();
                    if (pmUpdate.getTrangThai().equals(trangThai)) {
                        Toast.makeText(PMManagedment.this, "BẠN ĐÃ THỰC HIỆN CẬP NHẬT TRẠNG THÁI PHIẾU MƯỢN CHƯA ?", Toast.LENGTH_SHORT).show();
                    } else {
                        pmUpdate.setTrangThai(trangThai);
                        Log.d("CapNhatPM", "maPM = " + pmUpdate.getMaPM() + ", trangThai = " + trangThai);
                        if ( trangThai.equals("Đã trả")) {

                            Date today = new Date(); // ngày hôm nay
                            Date ngayMuon = pmUpdate.getNgayMuon(); // ngày mượn sách

                            // Tính số mili-giây giữa hai ngày
                            long diffMillis = today.getTime() - ngayMuon.getTime();

                            // Tính số ngày, ép kiểu về int nếu cần
                            int diffDays = (int) (diffMillis / (1000 * 60 * 60 * 24));

                            int soNgayToiDa = 14 ;

                            // Đảm bảo diffDays >= 14
                            if (diffDays >= soNgayToiDa) {
//                                diffDays = 0;
                                int soTienPhat = 5000 * diffDays;
                                Log.d("SỐ TIỀN PHẠT", String.valueOf(soTienPhat));
                                Log.d("SỐ NGÀY QUÁ HẠN", String.valueOf(diffDays));

                                PhieuViPham vp = new PhieuViPham(generateMaPhieuVP(), soTienPhat, diffDays, "Chưa xử lý", "Quá hạn", pmUpdate.getMaPM(), today);
                                PhieuViPhamViewModel phieuViPhamViewModel = new PhieuViPhamViewModel() ;
                                phieuViPhamViewModel.createPhieuViPham(vp);
                            }
                        }
                        viewModel.updatePhieuMuon(pmUpdate.getMaPM() , pmUpdate);
                        viewModel.loadPhieuMuon();
                        Toast.makeText(PMManagedment.this, "CẬP NHẬT TRẠNG THÁI PHIẾU MƯỢN THÀNH CÔNG !!! ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        backIconPMManagedment.setOnClickListener(v->{
            setResult(RESULT_OK);
            finish();
        });

        btn_HuyCapNhatTrangThaiPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearViewInfor();
            }
        });
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

    // Function for displaying PhieuMuon be chosen
    private void myDisplay(PhieuMuon pm) {
        txt_HienThiMaPM.setText(pm.getMaPM());
//        edt_CapNhatTrangThaiPM.setText(pm.getTrangThai());
        setSpinnerText(spinnerTrangThaiPM , pm.getTrangThai());
    }

    // Function for choosing item
    private void setSpinnerText(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        int pos = adapter.getPosition(value);
        spinner.setSelection(pos);
    }

    private void myMapping() {
        txt_HienThiMaPM = findViewById(R.id.txt_HienThiMaPM);
//        edt_CapNhatTrangThaiPM = findViewById(R.id.edt_CapNhatTrangThaiPM);
        spinnerTrangThaiPM = findViewById(R.id.spinnerTrangThai);
        btn_CapNhatTrangThaiPM = findViewById(R.id.btn_CapNhatTrangThaiPM);
        btn_HuyCapNhatTrangThaiPM = findViewById(R.id.btn_HuyCapNhatTrangThaiPM);
        backIconPMManagedment = findViewById(R.id.backIconPMManagedment) ;
        lv_DanhSachPhieuMuon = findViewById(R.id.lv_DanhSachPhieuMuon);

        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , listPM) ;
        lv_DanhSachPhieuMuon.setAdapter(arrayAdapter);
    }

    private void hienThiDanhSachPhieuMuon(){
        viewModel = new ViewModelProvider(this).get(PhieuMuonViewModel.class);

        viewModel.getListPhieuMuon().observe(this, data -> {
            listPM.clear();
            listPM.addAll(data);
            arrayAdapter.notifyDataSetChanged(); // Cập nhật UI
        });

        viewModel.loadPhieuMuon(); // Gọi API khi giao diện được tạo
    }

    private void clearViewInfor(){
        txt_HienThiMaPM.setText("");
        spinnerTrangThaiPM.setSelection(0);
    }

}