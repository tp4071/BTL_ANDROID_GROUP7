package com.example.libraryapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhieuViPhamAdapter extends RecyclerView.Adapter<PhieuViPhamAdapter.ViewHolder> {
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_violation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhieuViPham item = list.get(position);
        Date date = item.getNgayLap();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (item == null) return;

        holder.tvMaPhieu.setText("Mã phiếu: " + item.getMaPhieuVP());
        holder.tvMaPM.setText("Mã PM: " + item.getMaPM());
        holder.tvSoTienPhat.setText("Tiền phạt: " + item.getSoTienPhat() + " VND");
        holder.tvSoNgayQuaHan.setText("Ngày quá hạn: " + item.getSoNgayQuaHan());
        holder.tvKieuVP.setText("Kiểu VP: " + item.getKieuVP());
        holder.tvTrangThai.setText("Trạng thái: " + item.getTrangThai());
        holder.createDate.setText("Ngày tạo: " + sdf.format(date));

        // Long click để đổi trạng thái nếu chưa xử lý
        holder.layoutItem.setOnLongClickListener(v -> {
            if ("Đã thanh toán".equalsIgnoreCase(item.getTrangThai())) {
                Toast.makeText(context, "Trạng thái đã được xử lý, không thể thay đổi.", Toast.LENGTH_SHORT).show();
                return true;
            }

            new android.app.AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc muốn chuyển trạng thái thành 'Đã thanh toán'?")
                    .setPositiveButton("Đồng ý", (dialog, which) -> {
                        item.setTrangThai("Đã thanh toán");
                        holder.tvTrangThai.setText("Trạng thái: Đã thanh toán");
                        viewModel.updateTrangThai(item.getMaPhieuVP(), item, () -> {
                            Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                            viewModel.loadPhieuViPham();
                        });

                        notifyItemChanged(position);
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        });
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvMaPM, tvSoTienPhat, tvSoNgayQuaHan, tvKieuVP, tvTrangThai, createDate;
        View layoutItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvSoTienPhat = itemView.findViewById(R.id.tvSoTienPhat);
            tvSoNgayQuaHan = itemView.findViewById(R.id.tvSoNgayQuaHan);
            tvKieuVP = itemView.findViewById(R.id.tvKieuVP);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            createDate = itemView.findViewById(R.id.createDate);
            layoutItem = itemView.findViewById(R.id.layoutItem);
        }
    }

}