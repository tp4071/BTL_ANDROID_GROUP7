package com.example.libraryapplication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.Sach;
import com.example.libraryapplication.viewmodel.ThongKeViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThongKe extends AppCompatActivity {
    ListView topSach;
    ArrayList<Sach> topSachArr;
    ArrayAdapter<Sach> topSachAdt;
    EditText start,end;
    Button tk,tx;
    ThongKeViewModel thongKeViewModel;
    LineChart lineChartPM;
    LineChart lineChartPVP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_thong_ke), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        start.setOnClickListener(v->{
            DatePickerDialog datePickerDialog=createDatePicker(start);
            datePickerDialog.show();
        });
        end.setOnClickListener(v->{
            DatePickerDialog datePickerDialog=createDatePicker(end);
            datePickerDialog.show();
        });
        tk.setOnClickListener(v -> {
            SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                Date startDate=sdf.parse(start.getText().toString());
                Date endDate=sdf.parse(end.getText().toString());
                if(startDate.after(endDate)){
                    Toast.makeText(ThongKe.this,"Thời gian thống kê không hợp lệ",Toast.LENGTH_LONG).show();
                    return;
                }
                if(start.getText().toString().isEmpty()||end.getText().toString().isEmpty()){
                    Toast.makeText(ThongKe.this,"Hãy nhập khoảng thời gian cần thống kê",Toast.LENGTH_LONG).show();
                    return;
                }
                thongKeViewModel.getTK(start.getText().toString(),end.getText().toString());
                thongKeViewModel.getTop5Books().observe(this,saches->{
                    if (saches != null) {
                        topSachArr.clear();
                        topSachArr.addAll(saches);
                        topSachAdt.notifyDataSetChanged();
                    }
                });
                thongKeViewModel.getPmTK().observe(this,data -> {
                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, Object> item = data.get(i);
                        float value = Float.parseFloat(item.get("sophieumuon").toString());
                        entries.add(new Entry(i, value));
                    }

                    LineDataSet dataSet = new LineDataSet(entries, "Phiếu Mượn");
                    lineChartPM.setData(new LineData(dataSet));
                    lineChartPM.invalidate();
                });
                thongKeViewModel.getPvpTK().observe(this,data->{
                    List<Entry> entries = new ArrayList<>();
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, Object> item = data.get(i);
                        float value = Float.parseFloat(item.get("sophieuvipham").toString());
                        entries.add(new Entry(i, value));
                    }

                    LineDataSet dataSet = new LineDataSet(entries, "Vi Phạm");
                    lineChartPVP.setData(new LineData(dataSet));
                    lineChartPVP.invalidate();
                });
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void mapping(){
        topSach=findViewById(R.id.topBooksList);
        topSachArr=new ArrayList<>();
        topSachAdt=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,topSachArr);
        topSach.setAdapter(topSachAdt);
        start=findViewById(R.id.startDateInput);
        end=findViewById(R.id.endDateInput);
        tk=findViewById(R.id.tk);
        tx=findViewById(R.id.tx);
        thongKeViewModel=new ViewModelProvider(this).get(ThongKeViewModel.class);
        lineChartPM = findViewById(R.id.borrowChart);
        lineChartPVP = findViewById(R.id.violationChart);
    }
    private DatePickerDialog createDatePicker(EditText dateInp){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ThongKe.this,
                (DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) -> {
                    // Format ngày (tháng + 1 vì tháng bắt đầu từ 0)
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    dateInp.setText(date);
                },
                year, month, day
        );
        return datePickerDialog;
    }
}