package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.ChiTietDonDat;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.model.NhanVien;

import java.util.ArrayList;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<ChiTietDonDat> listDonDat;
    ViewHolder viewHolder;
    NhanVien nhanVien;
    BanAn banAn;

    public AdapterDisplayStatistic(Context context, int layout, ArrayList<ChiTietDonDat> listDonDat) {
        this.context = context;
        this.layout = layout;
        this.listDonDat = listDonDat;

    }

    @Override
    public int getCount() {
        return listDonDat.size();
    }

    @Override
    public Object getItem(int position) {
        return listDonDat.get(position);

    }

    @Override
    public long getItemId(int position) {
        return listDonDat.get(position).getDonDat().getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = view.findViewById(R.id.tv_ma_don_home);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.tv_date_home);
            viewHolder.txt_customstatistic_TenNV = (TextView)view.findViewById(R.id.tv_namenv_home);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.tv_trang_thai_home);
            viewHolder.txt_customstatistic_BanDat = (TextView)view.findViewById(R.id.tv_ten_ban_home);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDat donDatDTO = listDonDat.get(position).getDonDat();

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTO.getTongTien()+" VNĐ");
        if (donDatDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
       // NhanVienDTO nhanVienDTO = nhanVienDAO.LayNVTheoMa(donDatDTO.getMaNV());
        nhanVien = donDatDTO.getNhanVien();
        banAn = donDatDTO.getBan();
        viewHolder.txt_customstatistic_TenNV.setText(nhanVien.getHoTenNV());
        viewHolder.txt_customstatistic_BanDat.setText(banAn.getTenBan());

        return view;
    }

    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenNV
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_BanDat;

    }
}
