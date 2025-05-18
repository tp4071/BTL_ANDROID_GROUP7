package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;

public class PhieuViPhamActivity extends AppCompatActivity {
    private PhieuViPhamViewModel viewModel;
    private PhieuViPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pvpmanagedment);

        EditText edtSearch = findViewById(R.id.edtSearch);
        ImageButton btnAdd = findViewById(R.id.btnAdd);
        ListView lvList = findViewById(R.id.lvViolationList); // Đã đổi từ RecyclerView sang ListView
        View menuInclude = findViewById(R.id.menuBar);
        View statusBar = findViewById(R.id.statusBar);
        MenuBarHandler.setupMenu(menuInclude, this);
        StatusBarHandler.backToHomePage(statusBar, this);

        viewModel = new ViewModelProvider(this).get(PhieuViPhamViewModel.class);
        adapter = new PhieuViPhamAdapter(this, viewModel);
        lvList.setAdapter(adapter);

        viewModel.getPhieuViPhamList().observe(this, list -> adapter.setData(list));
        viewModel.loadPhieuViPham();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (viewModel == null) return;
                String keyword = s.toString().trim();
                if (keyword.length() >= 2) {
                    viewModel.search(keyword);
                } else if (keyword.isEmpty()) {
                    viewModel.loadPhieuViPham();
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, CreatePhieuViPhamActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadPhieuViPham();
    }
}
