package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.ThanhToan;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterDisplayPayment  extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<ChiTietDonDat> listChitietdondat;
    ViewHolder viewHolder;

    public AdapterDisplayPayment(Context context, int layout, ArrayList<ChiTietDonDat> listChitietdondat) {
        this.context = context;
        this.layout = layout;
        this.listChitietdondat = listChitietdondat;
    }

    @Override
    public int getCount() {
        return listChitietdondat.size();
    }

    @Override
    public Object getItem(int position) {
        return listChitietdondat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_custompayment_HinhMon = (CircleImageView)view.findViewById(R.id.img_custompayment_HinhMon);
            viewHolder.txt_custompayment_TenMon = (TextView)view.findViewById(R.id.txt_custompayment_TenMon);
            viewHolder.txt_custompayment_SoLuong = (TextView)view.findViewById(R.id.txt_custompayment_SoLuong);
            viewHolder.txt_custompayment_GiaTien = (TextView)view.findViewById(R.id.txt_custompayment_GiaTien);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)view.getTag();
        }
        ChiTietDonDat thanhToanDTO = listChitietdondat.get(position);

        viewHolder.txt_custompayment_TenMon.setText(thanhToanDTO.getMon().getTenMon());
        viewHolder.txt_custompayment_SoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolder.txt_custompayment_GiaTien.setText(String.valueOf(thanhToanDTO.getMon().getGiaTien())+" đ");

        new DownloadImageTask(viewHolder.img_custompayment_HinhMon).execute(listChitietdondat.get(position).getMon().getHinhAnh());

        return view;
    }

    public class ViewHolder{
        CircleImageView img_custompayment_HinhMon;
        TextView txt_custompayment_TenMon, txt_custompayment_SoLuong, txt_custompayment_GiaTien;
    }
}
