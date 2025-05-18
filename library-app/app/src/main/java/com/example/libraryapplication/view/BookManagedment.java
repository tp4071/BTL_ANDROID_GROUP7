package com.example.libraryapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookManagedment extends AppCompatActivity {
    List<Sach> listBook;
    SachViewModel viewmodel;
    ArrayAdapter adapter;
    Button btnAddBook;
    ListView lvBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_managedment);

        View menuInclude = findViewById(R.id.menuBar);
        View status = findViewById(R.id.status_bar);
        StatusBarHandler.backToHomePage(status, this);
        StatusBarHandler.comeToQLTKTT(status , this);
        StatusBarHandler.updateNameTK(status , this);
        MenuBarHandler.setupMenu(menuInclude, this);

        listBook = new ArrayList<>();
        myMapping();
        displayListBook();
        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(BookManagedment.this, BookDetails.class);
            Sach sach = listBook.get(position);
            intent.putExtra("sach", sach);
            startActivity(intent);
        });
        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookManagedment.this, AddAndEditBookActivity.class);
            startActivity(intent);
        });

    }

    private void displayListBook() {
         viewmodel = new ViewModelProvider(this).get(SachViewModel.class);

        viewmodel.getAll().observe(this, data ->{
            listBook.clear();
            listBook.addAll(data);
            adapter.notifyDataSetChanged();
        });
        viewmodel.loadBookLists();
    }

    private void myMapping() {
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , listBook);
        lvBooks.setAdapter(adapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        viewmodel.loadBookLists();
    }

}