package com.example.FvM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.FvM.databinding.ActivityMainBinding;
import com.example.FvM.ui.home.home;
import com.example.FvM.ui.packs.packView;
import com.example.FvM.ui.packs.packs;
import com.example.FvM.ui.settings.settings;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static  final int NUM_PAGES =3;
    private static ViewPager2 viewPager;
    private static FragmentStateAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        //animation
        LinearLayout layout = findViewById(R.id.Main_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(8000);
        animationDrawable.start();
        //animation end

        int[] icons = {R.drawable.ic_play,R.drawable.ic_player,R.drawable.ic_settings};
        int[] i_texts = {R.string.menu_home,R.string.menu_packs,R.string.menu_settings};
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setIcon(icons[position]).setText(i_texts[position])
        ).attach();

        RealmHelper.init(binding.getRoot().getContext());


    }

    @Override
    public void onBackPressed(){
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }

    }
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position==1)return new packView();
            if (position==2)return new settings();

            return new home();
        }
        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}