package com.example.libraryapplication.view.book;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.view.components.MenuBarHandler;
import com.example.libraryapplication.view.components.StatusBarHandler;
import com.example.libraryapplication.viewmodel.SachViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookManagedment extends AppCompatActivity {
    List<Sach> listBook;
    SachViewModel viewmodel;
    ArrayAdapter adapter;
    Button btnAddBook;
    ImageView backIcon;
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
        lvBooks.setOnItemLongClickListener((parent, view, position, id) -> {
            String ms = listBook.get(position).getMaSach();

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Xác nhận xoá")
                    .setMessage("Bạn có chắc chắn muốn xoá sách này không?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        viewmodel.deleteSach(ms);

                        // Sau khi xoá thì gọi lại loadBookLists() để cập nhật danh sách
                        viewmodel.loadBookLists();
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();

            return true;
        });

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(BookManagedment.this, AddAndEditBookActivity.class);
            startActivity(intent);
        });
        backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v->{finish();});
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            displayListBook();
        }
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