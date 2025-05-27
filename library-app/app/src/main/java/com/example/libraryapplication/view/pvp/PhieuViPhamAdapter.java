package com.example.libraryapplication.view.pvp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhieuViPhamAdapter extends BaseAdapter {
    private List<PhieuViPham> list;
    private final Context context;
    private final PhieuViPhamViewModel viewModel;

    public PhieuViPhamAdapter(Context context, PhieuViPhamViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setData(List<PhieuViPham> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        TextView tvMaPhieu, tvMaPM, tvSoTienPhat, tvSoNgayQuaHan, tvKieuVP, tvTrangThai, createDate;
        View layoutItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_violation, parent, false);
            holder = new ViewHolder();
            holder.tvMaPhieu = convertView.findViewById(R.id.tvMaPhieu);
            holder.tvMaPM = convertView.findViewById(R.id.tvMaPM);
            holder.tvSoTienPhat = convertView.findViewById(R.id.tvSoTienPhat);
            holder.tvSoNgayQuaHan = convertView.findViewById(R.id.tvSoNgayQuaHan);
            holder.tvKieuVP = convertView.findViewById(R.id.tvKieuVP);
            holder.tvTrangThai = convertView.findViewById(R.id.tvTrangThai);
            holder.createDate = convertView.findViewById(R.id.createDate);
            holder.layoutItem = convertView.findViewById(R.id.layoutItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PhieuViPham item = list.get(position);
        if (item == null) return convertView;

        Date date = item.getNgayLap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        holder.tvMaPhieu.setText("Mã phiếu: " + item.getMaPhieuVP());
        holder.tvMaPM.setText("Mã PM: " + item.getMaPM());
        holder.tvSoTienPhat.setText("Tiền phạt: " + item.getSoTienPhat() + " VND");
        holder.tvSoNgayQuaHan.setText("Ngày quá hạn: " + item.getSoNgayQuaHan());
        holder.tvKieuVP.setText("Kiểu VP: " + item.getKieuVP());
        holder.tvTrangThai.setText("Trạng thái: " + item.getTrangThai());
        holder.createDate.setText("Ngày tạo: " + sdf.format(date));

        holder.layoutItem.setOnLongClickListener(v -> {
            if ("Đã thanh toán".equalsIgnoreCase(item.getTrangThai())) {
                Toast.makeText(context, "Trạng thái đã được xử lý, không thể thay đổi.", Toast.LENGTH_SHORT).show();
                return true;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn chuyển trạng thái thành 'Đã thanh toán'?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        item.setTrangThai("Đã thanh toán");
                        holder.tvTrangThai.setText("Trạng thái: Đã thanh toán");
                        viewModel.updateTrangThai(item.getMaPhieuVP(), item, () -> {
                            Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                            viewModel.loadPhieuViPham();
                        });
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();

            return true;
        });

        return convertView;
    }
}
