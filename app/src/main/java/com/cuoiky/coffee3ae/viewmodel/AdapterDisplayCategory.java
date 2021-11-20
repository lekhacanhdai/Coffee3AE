package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;
import java.util.ArrayList;

public class AdapterDisplayCategory extends RecyclerView.Adapter<AdapterDisplayCategory.ViewHolder>{

    private ArrayList<LoaiMon> LoaiMonList;

    public AdapterDisplayCategory(ArrayList<LoaiMon> loaiMonList) {
        this.LoaiMonList = loaiMonList;
    }

    @NonNull
    @Override
    public AdapterDisplayCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout_displaycategory, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDisplayCategory.ViewHolder holder, int position) {
            holder.tenloai.setText(LoaiMonList.get(position).getTenLoai());
            holder.img_monan.setImageURI(LoaiMonList.get(position).getHinhAnh());
    }

    @Override
    public int getItemCount() {
        return LoaiMonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenloai;
        ImageView img_monan;

        public ViewHolder(@NonNull View view) {
            super(view);
            tenloai = view.findViewById(R.id.txt_customcategory_TenLoai);
            img_monan = view.findViewById(R.id.img_customcategory_HinhLoai);

        }
    }
}
