package com.example.libraryapplication.view.homePage;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.view.book.BookDetails;
import com.example.libraryapplication.view.components.MenuBarHandler;
import com.example.libraryapplication.view.components.StatusBarHandler;
import com.example.libraryapplication.viewmodel.PhieuMuonViewModel;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    ListView newBook,newPM;
    LinearLayout bookBtn,pmBtn,searchBtn,pvpBtn,tkBtn;
    ArrayAdapter<Sach> newBookAdt;
    ArrayAdapter<PhieuMuon> newPMAdt;
    ArrayList<Sach> newBookArr;
    ArrayList<PhieuMuon> newPMArr;
    private SachViewModel sachViewModel;
    private PhieuMuonViewModel pmViewModel;
    private ActivityResultLauncher<Intent> bookDetailsLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

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
        mapping();
        sachViewModel.getLatestBook().observe(this, saches -> {
            if (saches != null) {
                newBookArr.clear();
                newBookArr.addAll(saches);
                newBookAdt.notifyDataSetChanged();
            }
        });

        bookDetailsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        sachViewModel.getLatestBook().observe(this, saches -> {
                            if (saches != null) {
                                newBookArr.clear();
                                newBookArr.addAll(saches);
                                newBookAdt.notifyDataSetChanged();
                            }
                        });
                        pmViewModel.getLatestPhieuMuon().observe(this, phieuMuons -> {
                            if(phieuMuons!=null){
                                newPMArr.clear();
                                newPMArr.addAll(phieuMuons);
                                newPMAdt.notifyDataSetChanged();
                            }
                        });
                    }
                }
        );

        newBook.setOnItemClickListener((parent, view, position, id) -> {
            Sach sach = newBookArr.get(position);
            Intent intent = new Intent(HomePage.this, BookDetails.class);
            intent.putExtra("sach", sach);
            bookDetailsLauncher.launch(intent);
        });
        pmViewModel.getLatestPhieuMuon().observe(this, phieuMuons -> {
            if(phieuMuons!=null){
                newPMArr.clear();
                newPMArr.addAll(phieuMuons);
                newPMAdt.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sachViewModel.getLatestBook().observe(this, saches -> {
            if (saches != null) {
                newBookArr.clear();
                newBookArr.addAll(saches);
                newBookAdt.notifyDataSetChanged();
            }
        });
    }

    private void mapping(){
        newBook=findViewById(R.id.newBook);
        newBookArr=new ArrayList<>();
        newBookAdt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,newBookArr);
        sachViewModel = new ViewModelProvider(this).get(SachViewModel.class);
        newBook.setAdapter(newBookAdt);
        newPM=findViewById(R.id.newPM);
        newPMArr=new ArrayList<>();
        newPMAdt=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,newPMArr);
        newPM.setAdapter(newPMAdt);
        pmViewModel=new ViewModelProvider(this).get(PhieuMuonViewModel.class);
        bookBtn=findViewById(R.id.bookBtn);
        pmBtn=findViewById(R.id.pmBtn);
        searchBtn=findViewById(R.id.searchBtn);
        pvpBtn=findViewById(R.id.pvpBtn);
        tkBtn=findViewById(R.id.tkBtn);
    }

}