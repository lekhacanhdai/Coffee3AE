package com.cuoiky.coffee3ae.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.Mon;

import java.util.ArrayList;

public class AdapterDisplayMenu extends RecyclerView.Adapter<AdapterDisplayMenu.ViewHolder>{

    private ArrayList<Mon> listMon;

    public AdapterDisplayMenu(ArrayList<Mon> listMon) {
        this.listMon = listMon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_layout_displaymenu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tenMon.setText(listMon.get(position).getTenMon());
        holder.giaTien.setText(listMon.get(position).getGiaTien()+"VNĐ");
        new DownloadImageTask(holder.img_monan).execute(listMon.get(position).getHinhAnh());

     /*   if(listMon.get(position).getTinhTrang().equals("true")){
            holder.tinhTrang.setText("Còn món");
        }else{
            holder.tinhTrang.setText("Hết món");
        }*/
    }

    @Override
    public int getItemCount() {
        return listMon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tenMon,giaTien, tinhTrang;
        ImageView img_monan;

        public ViewHolder(@NonNull View view) {
            super(view);
            tenMon = view.findViewById(R.id.txt_custommenu_TenMon);
            giaTien = view.findViewById(R.id.txt_custommenu_GiaTien);
            tinhTrang = view.findViewById(R.id.txt_addmenu_TinhTrang);
            img_monan = (ImageView) view.findViewById(R.id.img_custommenu_HinhMon);

        }
    }
}
