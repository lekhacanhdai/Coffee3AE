package com.cuoiky.coffee3ae.view.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.ActivityHomeBinding;
import com.cuoiky.coffee3ae.databinding.ActivityMainBinding;
import com.cuoiky.coffee3ae.view.Fragments.BlankFragment;
import com.cuoiky.coffee3ae.view.Fragments.DisplayCategoryFragment;
import com.cuoiky.coffee3ae.view.Fragments.DisplayStaffFrament;
import com.cuoiky.coffee3ae.view.Fragments.DisplayStatisticFragment;
import com.cuoiky.coffee3ae.view.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private ActivityHomeBinding binding;

    private FragmentManager fragmentManager;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        View view = binding.navHome.getHeaderView(0);
        setSupportActionBar(binding.tbarHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.navHome.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,binding.homeLayout, binding.tbarHome
                ,R.string.opentoggle,R.string.closetoggle){
            @Override
            public void onDrawerOpened(View drawerView) {    super.onDrawerOpened(drawerView); }

            @Override
            public void onDrawerClosed(View drawerView) {    super.onDrawerClosed(drawerView); }
        };

        binding.homeLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction tranHomeF = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        tranHomeF.replace(R.id.home_view, homeFragment);
        tranHomeF.commit();

        binding.navHome.setCheckedItem(R.id.nav_home1);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.nav_home1:
                FragmentTransaction trancHome = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                trancHome.replace(R.id.home_view, homeFragment);
                trancHome.commit();
                binding.navHome.setCheckedItem(itemId);
                binding.homeLayout.closeDrawers();
                break;
            case R.id.nav_statistic:
                FragmentTransaction trancThongKe = fragmentManager.beginTransaction();
                DisplayStatisticFragment statisticFragment = new DisplayStatisticFragment();
                trancThongKe.replace(R.id.home_view, statisticFragment).addToBackStack(null);
                trancThongKe.commit();
                binding.navHome.setCheckedItem(itemId);
                binding.homeLayout.closeDrawers();
                break;
            case R.id.nav_table:
                break;
            case R.id.nav_category:
                FragmentTransaction trancLoai = fragmentManager.beginTransaction();
                DisplayCategoryFragment categoryFragment = new DisplayCategoryFragment();
                trancLoai.replace(R.id.home_view, categoryFragment).addToBackStack(null);
                trancLoai.commit();
                binding.navHome.setCheckedItem(itemId);
                binding.homeLayout.closeDrawers();
                break;
            case R.id.nav_staff:
                FragmentTransaction trancNhanVien = fragmentManager.beginTransaction();
                DisplayStaffFrament staffFrament = new DisplayStaffFrament();
                trancNhanVien.replace(R.id.home_view, staffFrament).addToBackStack(null);
                trancNhanVien.commit();
                binding.navHome.setCheckedItem(itemId);
                binding.homeLayout.closeDrawers();
                break;
            case R.id.nav_logout:
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return false;
    }





}