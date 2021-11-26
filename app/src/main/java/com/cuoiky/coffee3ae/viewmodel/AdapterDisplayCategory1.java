package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.LoaiMon;

import java.util.ArrayList;

public class AdapterDisplayCategory1 extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<LoaiMon> loaiMonList ;
    ViewHolder viewHolder;

    public AdapterDisplayCategory1(Context context, int layout, ArrayList<LoaiMon> loaiMonList) {
        this.context = context;
        this.layout = layout;
        this.loaiMonList = loaiMonList;
    }

    @Override
    public int getCount() {
        return loaiMonList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonList.get(position).getMaLoai();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        //nếu lần đầu gọi view
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            //truyền component vào viewholder để ko gọi findview ở những lần hiển thị khác
            viewHolder.img_customcategory_HinhLoai = (ImageView)view.findViewById(R.id.img_customcategory_HinhLoai);
            viewHolder.txt_customcategory_TenLoai = (TextView)view.findViewById(R.id.txt_customcategory_TenLoai);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final LoaiMon loaiMon = loaiMonList.get(position);

        viewHolder.txt_customcategory_TenLoai.setText(loaiMon.getTenLoai());


        new DownloadImageTask(viewHolder.img_customcategory_HinhLoai).execute(loaiMonList.get(position).getHinhAnh());


        return view;
    }

    //tạo viewholer lưu trữ component
    public class ViewHolder{
        TextView txt_customcategory_TenLoai;
        ImageView img_customcategory_HinhLoai;
    }
}
