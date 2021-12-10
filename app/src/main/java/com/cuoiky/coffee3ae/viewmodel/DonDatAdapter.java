package com.cuoiky.coffee3ae.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;

import java.util.ArrayList;

public class DonDatAdapter extends RecyclerView.Adapter<DonDatAdapter.ViewHoder> {

    private ArrayList<ChiTietDonDat> listDonDat;

    public DonDatAdapter(ArrayList<ChiTietDonDat> listDonDat) {
        this.listDonDat = listDonDat;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_row,parent,  false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {

        if(listDonDat.get(position).getDonDat().getTinhTrang().equals("true")){
            holder.tvTinhTrang.setText("Đã Thanh Toán");
        }
        else{
            holder.tvTinhTrang.setText("Chưa Thanh Toán");
        }
        holder.tvTenNhanVien.setText(listDonDat.get(position).getDonDat().getNhanVien().getHoTenNV());
        holder.tvNgay.setText(listDonDat.get(position).getDonDat().getNgayDat());
        holder.tvMaDonDat.setText("Mã đơn: " + listDonDat.get(position).getDonDat().getMaDonDat());
        holder.tvMaBan.setText(listDonDat.get(position).getDonDat().getBan().getTenBan());
    }

    @Override
    public int getItemCount() {
        return listDonDat.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        public TextView tvMaBan, tvNgay, tvTenNhanVien, tvTinhTrang, tvMaDonDat ;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaBan = itemView.findViewById(R.id.tv_ten_ban_home);
            tvMaDonDat = itemView.findViewById(R.id.tv_ma_don_home);
            tvNgay = itemView.findViewById(R.id.tv_date_home);
            tvTenNhanVien = itemView.findViewById(R.id.tv_namenv_home);
            tvTinhTrang = itemView.findViewById(R.id.tv_trang_thai_home);
        }
    }
}
