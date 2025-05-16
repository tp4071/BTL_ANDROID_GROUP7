package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        RecyclerView rvList = findViewById(R.id.rvViolationList);

        viewModel = new ViewModelProvider(this).get(PhieuViPhamViewModel.class);
        adapter = new PhieuViPhamAdapter(this, viewModel);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(adapter);

        viewModel.getPhieuViPhamList().observe(this, list -> adapter.setData(list));
        viewModel.loadPhieuViPham();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString().trim();
                if (!keyword.isEmpty()) {
                    viewModel.search(keyword);
                } else {
                    viewModel.loadPhieuViPham();
                }
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, CreatePhieuViPhamActivity.class)));
    }
}
