package com.cuoiky.coffee3ae.view.Activities;

import static com.cuoiky.coffee3ae.R.anim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.cuoiky.coffee3ae.databinding.SplashLayoutBinding;

public class SplashActivity extends AppCompatActivity {
    private SplashLayoutBinding binding;

    private static int SPLASH_TIMER = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashLayoutBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot();
        setContentView(rootView);

        //Lấy đối tượng
        Animation sideAnim = AnimationUtils.loadAnimation(this, anim.side_anim);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, anim.bottom_anim);

        //Thiết lập animation cho component
        binding.imgLogo.setAnimation(sideAnim);
        binding.txtCoffeeshop.setAnimation(sideAnim);
        binding.txtPowered.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(intent);
                overridePendingTransition(anim.fade_in, anim.fade_out);
                finish();
            }
        }, SPLASH_TIMER);
    }
}