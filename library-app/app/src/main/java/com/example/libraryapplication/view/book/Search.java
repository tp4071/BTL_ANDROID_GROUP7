package com.example.libraryapplication.view.book;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.helper.BookResult;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.view.components.MenuBarHandler;
import com.example.libraryapplication.view.components.StatusBarHandler;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    ListView searchResult;
    EditText searchBar;
    ImageView backIcon;
    SachViewModel sachViewModel;
    ArrayAdapter<BookResult> resultAdt;
    ArrayList<BookResult> resultArr;
    private ActivityResultLauncher<Intent> bookDetailsLauncher;
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

        bookDetailsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        getSearchResult();
                    }
                }
        );

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
                getSearchResult();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        backIcon.setOnClickListener(v->{
            setResult(RESULT_OK);
            finish();
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
        resultArr=new ArrayList<>();
        resultAdt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,resultArr);
        searchResult.setAdapter(resultAdt);
        sachViewModel= new ViewModelProvider(this).get(SachViewModel.class);
        backIcon=findViewById(R.id.backIcon);
    }
    private void getSearchResult(){
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
}
