package com.example.libraryapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.libraryapplication.R;
import com.example.libraryapplication.model.PhieuViPham;
import com.example.libraryapplication.viewmodel.PhieuViPhamViewModel;
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
        if (item == null) return;

        holder.tvMaPhieu.setText("Mã phiếu: " + item.getMaPhieuVP());
        holder.tvMaPM.setText("Mã PM: " + item.getMaPM());
        holder.tvSoTienPhat.setText("Tiền phạt: " + item.getSoTienPhat() + " VND");
        holder.tvSoNgayQuaHan.setText("Ngày quá hạn: " + item.getSoNgayQuaHan());
        holder.tvKieuVP.setText("Kiểu VP: " + item.getKieuVP());
        holder.tvTrangThai.setText("Trạng thái: " + item.getTrangThai());

        boolean isDaXuLy = "Đã xử lý".equalsIgnoreCase(item.getTrangThai());
        holder.switchTrangThai.setChecked(isDaXuLy);

        holder.switchTrangThai.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newTrangThai = isChecked ? "Đã xử lý" : "Chưa xử lý";
            item.setTrangThai(newTrangThai);
            holder.tvTrangThai.setText("Trạng thái: " + newTrangThai);
            viewModel.updateTrangThai(item.getMaPhieuVP(), item);
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaPhieu, tvMaPM, tvSoTienPhat, tvSoNgayQuaHan, tvKieuVP, tvTrangThai;
        Switch switchTrangThai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieu);
            tvMaPM = itemView.findViewById(R.id.tvMaPM);
            tvSoTienPhat = itemView.findViewById(R.id.tvSoTienPhat);
            tvSoNgayQuaHan = itemView.findViewById(R.id.tvSoNgayQuaHan);
            tvKieuVP = itemView.findViewById(R.id.tvKieuVP);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
            switchTrangThai = itemView.findViewById(R.id.switchTrangThai);
        }
    }
}