package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.NhanVien;

import java.util.ArrayList;

public class AdapterDisplayStaff extends BaseAdapter {

    private ArrayList<NhanVien> listNhanvien;
    int layout;
    Context context;
    ViewHolder viewHolder;

    public AdapterDisplayStaff(ArrayList<NhanVien> listNhanvien, int layout, Context context) {
        this.listNhanvien = listNhanvien;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listNhanvien.size();
    }

    @Override
    public Object getItem(int position) {
        return listNhanvien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listNhanvien.get(position).getMaNV();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customstaff_HinhNV = (ImageView)view.findViewById(R.id.img_customstaff_HinhNV);
            viewHolder.txt_customstaff_TenNV = (TextView)view.findViewById(R.id.txt_customstaff_TenNV);
            viewHolder.txt_customstaff_TenQuyen = (TextView)view.findViewById(R.id.txt_customstaff_TenQuyen);
            viewHolder.txt_customstaff_SDT = (TextView)view.findViewById(R.id.txt_customstaff_SDT);
            viewHolder.txt_customstaff_Email = (TextView)view.findViewById(R.id.txt_customstaff_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        NhanVien nhanVienDTO = listNhanvien.get(position);

        viewHolder.txt_customstaff_TenNV.setText(nhanVienDTO.getHoTenNV());
        viewHolder.txt_customstaff_TenQuyen.setText(nhanVienDTO.getQuyen().getTenQuyen());
        viewHolder.txt_customstaff_SDT.setText(nhanVienDTO.getSdt());
        viewHolder.txt_customstaff_Email.setText(nhanVienDTO.getEmail());

        return view;
    }

    public class ViewHolder{
        ImageView img_customstaff_HinhNV;
        TextView txt_customstaff_TenNV, txt_customstaff_TenQuyen,txt_customstaff_SDT, txt_customstaff_Email;
    }
}
