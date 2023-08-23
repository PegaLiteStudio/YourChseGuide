package com.yourapps.your_chse_guide.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.ads.MobileAds;
import com.yourapps.your_chse_guide.R;
import com.yourapps.your_chse_guide.adapters.MainFragmentAdapter;
import com.yourapps.your_chse_guide.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    //Stream Subject book chapter
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {
        });

        Objects.requireNonNull(getSupportActionBar()).hide();


        binding.bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.youtube_ic));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.home));
        binding.bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.info));
        binding.bottomNavigation.show(2, true);

        MainFragmentAdapter mainFragmentadapter = new MainFragmentAdapter(getSupportFragmentManager(), 3);
        binding.viewPager.setAdapter(mainFragmentadapter);
        binding.viewPager.setCurrentItem(1);
        mainFragmentadapter.notifyDataSetChanged();
        binding.viewPager.setOffscreenPageLimit(3);

        Window window = getWindow();
        window.setNavigationBarColor(Color.WHITE);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_theme));
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));

                    binding.bottomNavigation.show(1, true);
                } else if (position == 1) {
                    binding.bottomNavigation.show(2, true);
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.blue_theme));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                } else if (position == 2) {
                    binding.bottomNavigation.show(3, true);
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.bottomNavigation.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    binding.viewPager.setCurrentItem(0);
                    mainFragmentadapter.notifyDataSetChanged();
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.white));

                    break;
                case 2:
                    binding.viewPager.setCurrentItem(1);
                    mainFragmentadapter.notifyDataSetChanged();
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.blue_theme));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    break;
                case 3:
                    binding.viewPager.setCurrentItem(2);
                    mainFragmentadapter.notifyDataSetChanged();
                    window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    break;

            }
            return null;
        });

    }
}