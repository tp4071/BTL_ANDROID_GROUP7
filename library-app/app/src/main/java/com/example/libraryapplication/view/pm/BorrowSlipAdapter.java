package com.example.libraryapplication.view.pm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuMuon;

import java.text.SimpleDateFormat;
import java.util.List;

public class BorrowSlipAdapter extends BaseAdapter {
    private final Context context;
    private List<PhieuMuon> list;

    public BorrowSlipAdapter(Context context, List<PhieuMuon> list) {
        this.context = context;
        this.list = list;
    }

    public void updateData(List<PhieuMuon> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvMaPM, tvNgayMuon, tvMaSV, tvMaSach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_borrow, parent, false);
            h = new ViewHolder();
            h.tvMaPM = convertView.findViewById(R.id.tvMaPM);
            h.tvNgayMuon = convertView.findViewById(R.id.tvNgayMuon);
            h.tvMaSV = convertView.findViewById(R.id.tvMaSV);
            h.tvMaSach = convertView.findViewById(R.id.tvMaSach);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        PhieuMuon pm = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        h.tvMaPM.setText("Mã phiếu mượn: " + pm.getMaPM());
        h.tvNgayMuon.setText("Ngày mượn: " + sdf.format(pm.getNgayMuon()));
        h.tvMaSV.setText("Mã sinh viên: " + pm.getMaSinhVien());
        h.tvMaSach.setText("Mã sách: " + pm.getMaSach());

        return convertView;
    }
}

