package com.example.giaodienchinh_doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.nav_bottom);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0 :
                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;
                    case 1 :
                        bottomNavigationView.getMenu().findItem(R.id.shop).setChecked(true);
                        break;
                    case 2 :
                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home){
                    mViewPager.setCurrentItem(0);
                }
                if (item.getItemId() == R.id.shop){
                    mViewPager.setCurrentItem((1));
                }
                if (item.getItemId() == R.id.profile){
                    mViewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }
}