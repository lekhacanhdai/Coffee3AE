package com.cuoiky.coffee3ae.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.model.BanAn;
import com.cuoiky.coffee3ae.model.DonDat;
import com.cuoiky.coffee3ae.view.Activities.HomeActivity;
import com.cuoiky.coffee3ae.view.Activities.PaymentActivity;
import com.cuoiky.coffee3ae.view.Fragments.DisplayCategoryFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayTable extends BaseAdapter implements View.OnClickListener {


    Context context;
    int layout;
    List<BanAn> listBanAn;
    ViewHolder viewHolder;
    private int manv;


    FragmentManager fragmentManager;

    public AdapterDisplayTable(Context context, int layout, List<BanAn> listBanAn) {
        this.context = context;
        this.layout = layout;
        this.listBanAn = listBanAn;
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }


    @Override
    public int getCount() {
        return listBanAn.size();
    }

    @Override
    public Object getItem(int position) {
        return listBanAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listBanAn.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgBanAn = (ImageView) view.findViewById(R.id.img_customtable_BanAn);
            viewHolder.imgGoiMon = (ImageView) view.findViewById(R.id.img_customtable_GoiMon);
            viewHolder.imgThanhToan = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txtTenBanAn = (TextView)view.findViewById(R.id.txt_customtable_TenBanAn);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(listBanAn.get(position).isDuocChon()){
            HienThiButton();
        }else {
            AnButton();
        }


        boolean kttinhtrang = listBanAn.get(position).isDuocChon();
        //?????i h??nh theo t??nh tr???ng
        if(kttinhtrang == true ){
            viewHolder.imgBanAn.setImageResource(R.drawable.ban_done_2);
        }else {
            viewHolder.imgBanAn.setImageResource(R.drawable.ban_dai_hello);
        }

        viewHolder.txtTenBanAn.setText(listBanAn.get(position).getTenBan());
        viewHolder.imgBanAn.setTag(position);

        //s??? ki???n click
        viewHolder.imgBanAn.setOnClickListener(this);
        viewHolder.imgGoiMon.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();
        int vitri1 = (int) viewHolder.imgBanAn.getTag();

        int maban = listBanAn.get(vitri1).getMaBan();
        String tenban = listBanAn.get(vitri1).getTenBan();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        switch (id) {
            case R.id.img_customtable_BanAn:
                int vitri = (int)v.getTag();

                HienThiButton();
                break;
            case R.id.img_customtable_AnNut:
                AnButton();
                break;
            case R.id.img_customtable_GoiMon:

                Intent getIHome = ((HomeActivity)context).getIntent();
                manv = getIHome.getIntExtra("manv",0);

                Log.d("manv_adapter", ""+manv);
                boolean tinhtrang = true;

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("maban",maban);
                bDataCategory.putInt("manv",manv);
                bDataCategory.putBoolean("tinhtrang",tinhtrang);
                displayCategoryFragment.setArguments(bDataCategory);
                transaction.replace(R.id.home_view,displayCategoryFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;


            case R.id.img_customtable_ThanhToan:
                //chuy???n d??? li???u qua trang thanh to??n
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("maban",maban);
                iThanhToan.putExtra("tenban",tenban);
                iThanhToan.putExtra("ngaydat",ngaydat);
                context.startActivity(iThanhToan);
                break;
            default:
                break;
        }


    }


    private void HienThiButton(){
        viewHolder.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnNut;
        TextView txtTenBanAn;
    }
}
