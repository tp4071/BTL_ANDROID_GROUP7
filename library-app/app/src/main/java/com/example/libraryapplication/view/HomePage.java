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
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {
    ListView topBook;
    ArrayAdapter<Sach> topBookAdt;
    ArrayList<Sach> topBookArr;
    private SachViewModel sachViewModel;

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
        sachViewModel.getTop5MostBorrowedBooks().observe(this, new Observer<List<Sach>>() {
            @Override
            public void onChanged(List<Sach> saches) {
                if (saches != null) {
                    topBookArr.clear();
                    topBookArr.addAll(saches);
                    topBookAdt.notifyDataSetChanged();
                }
            }
        });
        topBook.setOnItemClickListener((parent, view, position, id) -> {
            Sach sach = topBookArr.get(position);
            Intent intent = new Intent(HomePage.this, BookDetails.class);
            intent.putExtra("sach", sach); // cần implements Serializable
            startActivity(intent);
        });
    }
    private void mapping(){
        topBook=findViewById(R.id.topBook);
        topBookArr=new ArrayList<>();
        topBookAdt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, topBookArr);
        sachViewModel = new ViewModelProvider(this).get(SachViewModel.class);
        topBook.setAdapter(topBookAdt);
    }

}