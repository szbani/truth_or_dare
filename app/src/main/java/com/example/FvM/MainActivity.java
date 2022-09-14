package com.example.FvM;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.FvM.databinding.ActivityMainBinding;
import com.example.FvM.ui.game.GameActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final int Swipe_min = 120;
    private static final int Swipe_max_off_path = 250;
    private static final int Swipe_threshold = 200;
    private GestureDetector mDetector;
    View.OnTouchListener gestureListener;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ConstraintLayout constraintLayout = findViewById(R.id.Main_layout);

        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(8000);
        animationDrawable.start();

        mDetector = new GestureDetector(this, new gestureLis());
        gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return mDetector.onTouchEvent(event);

            }
        };

        //ugyan az
        // setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav);
        //megkeresi a 'nav'-ot
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_player, R.id.nav_settings).build();

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host) ;
        navController = navHostFragment.getNavController();

        //navController = Navigation.findNavController(this, R.id.nav_host);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.nav, navController);



    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class gestureLis extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";
        @Override
        public boolean onFling(MotionEvent event1,MotionEvent  event2,
                               float velocityX, float velocityY){
            try {
                if (Math.abs(event1.getY()-event2.getY()) > Swipe_max_off_path)
                    return false;
                if (event1.getX() - event2.getX() > Swipe_min && Math.abs(velocityX) > Swipe_threshold){
                    Log.d(DEBUG_TAG,"Left Swipe: ");
                    if (navController.getCurrentDestination().getId() == R.id.nav_home){
                        navController.navigate(R.id.action_nav_home_to_nav_player);
                    }else if (navController.getCurrentDestination().getId() == R.id.nav_player){
                        navController.navigate(R.id.action_nav_player_to_nav_settings);
                    }

                }else if (event2.getX()-event1.getX() > Swipe_min && Math.abs(velocityX) > Swipe_threshold){
                    Log.d(DEBUG_TAG,"Right Swipe: ");
                    if (navController.getCurrentDestination().getId() == R.id.nav_player){
                        navController.navigate(R.id.action_nav_player_to_nav_home);
                    }else if (navController.getCurrentDestination().getId() == R.id.nav_settings){
                        navController.navigate(R.id.action_nav_settings_to_nav_player);
                    }

                }
            }catch (Exception e){

            }
            return false;
        }
        @Override
        public boolean onDown(MotionEvent event){
            Log.d(DEBUG_TAG,"onDown: "+ event.toString());
            return true;
        }

    }
}