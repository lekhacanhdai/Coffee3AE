package com.cuoiky.coffee3ae.view.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.cuoiky.coffee3ae.R;
import com.cuoiky.coffee3ae.databinding.ActivityHomeBinding;
import com.cuoiky.coffee3ae.databinding.ActivityMainBinding;
import com.cuoiky.coffee3ae.view.Fragments.DisplayCategoryFragment;
import com.cuoiky.coffee3ae.view.Fragments.HomeFragment;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private ActivityHomeBinding binding;

    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        View view = binding.navHome.getHeaderView(0);
        setSupportActionBar(binding.tbarHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        DisplayCategoryFragment homeFragment = new DisplayCategoryFragment();
        tranHomeF.replace(R.id.home_view, homeFragment);
        tranHomeF.commit();

        binding.navHome.setCheckedItem(R.id.nav_home);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}