package com.example.giaodienchinh_doan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.giaodienchinh_doan.AdapterView.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.facebook.AccessToken;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {
    ViewPager mViewPager;
    MeowBottomNavigation bottomNavigation;
//    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = findViewById(R.id.viewpager);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_foreground));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_shop_foreground));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_person_foreground));
        bottomNavigation.show(1,true);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);
        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                mViewPager.setCurrentItem(model.getId() - 1);
                return null;
            }
        });
        bottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case 2:
                        mViewPager.setCurrentItem(1);
                        break;
                    case 3:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return null;
            }
        });
        mViewPager.setCurrentItem(0);

    }
}
//package com.example.giaodienchinh_doan;
//
//        import androidx.annotation.NonNull;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.fragment.app.FragmentStatePagerAdapter;
//        import androidx.viewpager.widget.ViewPager;
//
//        import android.os.Bundle;
//        import android.view.MenuItem;
//
//        import com.example.giaodienchinh_doan.AdapterView.ViewPagerAdapter;
//        import com.google.android.material.bottomnavigation.BottomNavigationView;
//        import com.facebook.AccessToken;
//
//public class MainActivity extends AppCompatActivity {
//    ViewPager mViewPager;
//    BottomNavigationView bottomNavigationView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mViewPager = findViewById(R.id.viewpager);
//        bottomNavigationView = findViewById(R.id.nav_bottom);
//
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        mViewPager.setAdapter(adapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0 :
//                        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
//                        break;
//                    case 1 :
//                        bottomNavigationView.getMenu().findItem(R.id.shop).setChecked(true);
//                        break;
//                    case 2 :
//                        bottomNavigationView.getMenu().findItem(R.id.profile).setChecked(true);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.home){
//                    mViewPager.setCurrentItem(0);
//                }
//                if (item.getItemId() == R.id.shop){
//                    mViewPager.setCurrentItem((1));
//                }
//                if (item.getItemId() == R.id.profile){
//                    mViewPager.setCurrentItem(2);
//                }
//                return true;
//            }
//        });
//    }
//}