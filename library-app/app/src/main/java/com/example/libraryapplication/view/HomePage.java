package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuMuon;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.PMViewModel;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    ListView newBook,newPM;
    ArrayAdapter<Sach> newBookAdt;
    ArrayAdapter<PhieuMuon> newPMAdt;
    ArrayList<Sach> newBookArr;
    ArrayList<PhieuMuon> newPMArr;
    private SachViewModel sachViewModel;
    private PMViewModel pmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        sachViewModel.getLatestBook().observe(this, new Observer<List<Sach>>() {
            @Override
            public void onChanged(List<Sach> saches) {
                if (saches != null) {
                    newBookArr.clear();
                    newBookArr.addAll(saches);
                    newBookAdt.notifyDataSetChanged();
                }
            }
        });
        newBook.setOnItemClickListener((parent, view, position, id) -> {
            Sach sach = newBookArr.get(position);
            Intent intent = new Intent(HomePage.this, BookDetails.class);
            intent.putExtra("sach", sach); // cần implements Serializable
            startActivity(intent);
        });
        pmViewModel.getLatestPhieuMuon().observe(this, new Observer<List<PhieuMuon>>() {
            @Override
            public void onChanged(List<PhieuMuon> phieuMuons) {
                if(phieuMuons!=null){
                    newPMArr.clear();
                    newPMArr.addAll(phieuMuons);
                    newPMAdt.notifyDataSetChanged();
                }
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
        pmViewModel=new ViewModelProvider(this).get(PMViewModel.class);
    }

}