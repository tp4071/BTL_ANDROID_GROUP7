package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.helper.BookResult;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.SachViewModel;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ListView searchResult;
    EditText searchBar;
    ShapeableImageView search;
    SachViewModel sachViewModel;
    ArrayAdapter<BookResult> resultAdt;
    ArrayList<BookResult> resultArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_search), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        View menuInclude = findViewById(R.id.menu_bar);
        View status = findViewById(R.id.status_bar);
        StatusBarHandler.backToHomePage(status, this);
        StatusBarHandler.comeToQLTKTT(status , this);
        StatusBarHandler.updateNameTK(status , this);
        MenuBarHandler.setupMenu(menuInclude, this);
        mapping();
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sachViewModel.getSearchResult(searchBar.getText().toString()).observe(Search.this,saches -> {
                    if(saches!=null){
                        resultArr.clear();
                        if(saches.isEmpty())resultArr.add(new BookResult(null,"Hãy thử nhập lại"));
                        for(Sach onSach :saches){
                            resultArr.add(new BookResult(onSach,null));
                        }
                        resultAdt.notifyDataSetChanged();
                    }
                });

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search.setOnClickListener(v -> {
            if(searchBar.getText().toString().isEmpty()){
                Toast.makeText(Search.this,"Hãy nhập từ khóa cần tìm",Toast.LENGTH_SHORT).show();
                return;
            }
            sachViewModel.getSearchResult(searchBar.getText().toString()).observe(this,saches -> {
                if(saches!=null){
                    resultArr.clear();
                    if(saches.isEmpty())resultArr.add(new BookResult(null,"Hãy thử nhập lại"));
                    for(Sach s :saches){
                        resultArr.add(new BookResult(s,null));
                    }
                    resultAdt.notifyDataSetChanged();
                }
            });

        });
        searchResult.setOnItemClickListener((parent, view, position, id) -> {
            BookResult br = resultArr.get(position);
            Intent intent = new Intent(Search.this, BookDetails.class);
            intent.putExtra("sach", br.getSach());
            startActivity(intent);
        });
    }
    private void mapping(){
        searchBar=findViewById(R.id.edit_search);
        searchResult=findViewById(R.id.result);
        search=findViewById(R.id.btn_search);
        resultArr=new ArrayList<>();
        resultAdt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,resultArr);
        searchResult.setAdapter(resultAdt);
        sachViewModel= new ViewModelProvider(this).get(SachViewModel.class);
    }
}