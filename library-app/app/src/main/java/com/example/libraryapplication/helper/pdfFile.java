package com.example.libraryapplication.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class pdfFile {
    public Bitmap getBitmapFromListView(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        int itemCount = adapter.getCount();
        int totalHeight = 0;
        List<Bitmap> bitmaps = new ArrayList<>();

        // Vẽ từng item của listView ra bitmap
        for (int i = 0; i < itemCount; i++) {
            View itemView = adapter.getView(i, null, listView);
            itemView.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            itemView.layout(0, 0, itemView.getMeasuredWidth(), itemView.getMeasuredHeight());

            Bitmap itemBitmap = Bitmap.createBitmap(itemView.getMeasuredWidth(), itemView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(itemBitmap);
            itemView.draw(canvas);

            bitmaps.add(itemBitmap);
            totalHeight += itemView.getMeasuredHeight();
        }

        // Tạo bitmap tổng hợp với chiều cao đủ chứa tất cả item
        Bitmap bigBitmap = Bitmap.createBitmap(listView.getWidth(), totalHeight, Bitmap.Config.ARGB_8888);
        Canvas bigCanvas = new Canvas(bigBitmap);
        bigCanvas.drawColor(Color.WHITE);

        int currentHeight = 0;
        for (Bitmap bmp : bitmaps) {
            bigCanvas.drawBitmap(bmp, 0, currentHeight, null);
            currentHeight += bmp.getHeight();
            bmp.recycle();
        }
        return bigBitmap;
    }

    public static File createPDF(String date,Bitmap topBook, Bitmap chart1, Bitmap chart2,){
        PdfDocument pdfDocument=new PdfDocument();

    }
}
