package com.example.FvM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.example.FvM.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ConstraintLayout constraintLayout = findViewById(R.id.Main_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(6000);
        animationDrawable.start();

        //ugyan az
        // setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav);
        //megkeresi a 'nav'-ot
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_player, R.id.nav_settings).build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.nav, navController);

    }

}