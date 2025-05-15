package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.network.SupabaseApi;
import com.example.libraryapplication.network.SupabaseClient;
import com.example.libraryapplication.repository.PhieuMuonRepository;
import com.example.libraryapplication.viewmodel.PhieuMuonViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PMManagedment extends AppCompatActivity {
    private SupabaseApi api = SupabaseClient.getApi();
    TextView txt_HienThiMaPM ;
    EditText edt_CapNhatMaPM ;
    Button btn_CapNhatTrangThaiPM , btn_HuyCapNhatTrangThaiPM ;
    List<PhieuMuon> listPM ;
    ArrayAdapter arrayAdapter ;
    ListView lv_PhieuMuon ;
    Intent intent ;
    ActivityResultLauncher activityResultLauncher ;
    private PhieuMuonViewModel viewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pmmanagedment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listPM = new ArrayList<PhieuMuon>() ;
        myMapping();
        // Get toàn bộ phiếu mượn
        viewModel = new ViewModelProvider(this).get(PhieuMuonViewModel.class);

        viewModel.getListPhieuMuon().observe(this, data -> {
            listPM.clear();
            listPM.addAll(data);
            arrayAdapter.notifyDataSetChanged(); // Cập nhật UI
        });

        viewModel.loadPhieuMuon(); // Gọi API khi giao diện được tạo

    }

    private void myMapping() {
        txt_HienThiMaPM = findViewById(R.id.txt_HienThiMaPM);
        edt_CapNhatMaPM = findViewById(R.id.edt_CapNhatMaPM);
        btn_CapNhatTrangThaiPM = findViewById(R.id.btn_CapNhatTrangThaiPM);
        btn_HuyCapNhatTrangThaiPM = findViewById(R.id.btn_HuyCapNhatTrangThaiPM);

        lv_PhieuMuon = findViewById(R.id.lv_DanhSachPhieuMuon);
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , listPM) ;
        lv_PhieuMuon.setAdapter(arrayAdapter);
    }

}