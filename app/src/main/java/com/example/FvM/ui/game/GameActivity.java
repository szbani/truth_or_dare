package com.example.FvM.ui.game;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.FvM.databinding.ActivityGameBinding;

import com.example.FvM.R;

import java.util.List;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    protected static List<String> K_f;
    protected static List<String> K_m;
    protected static List<String> players;
    public static int player_num;

    private ActivityGameBinding binding;

    private SensorManager sensorManager;

    private boolean isTilted = false;

    private static final int Swipe_min = 120;
    private static final int Swipe_max_off_path = 250;
    private static final int Swipe_threshold = 200;
    private GestureDetector mDetector;
    View.OnTouchListener gestureListener;

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        setContentView(binding.getRoot());

        K_f = intent.getStringArrayListExtra("kerdesek_f");
        K_m = intent.getStringArrayListExtra("kerdesek_m");
        players = intent.getStringArrayListExtra("players");
        player_num = -1;

        //sensor
        sensorManager = getSystemService(SensorManager.class);

        //swipe listener
        mDetector = new GestureDetector(this, new gestureLis());
        gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return mDetector.onTouchEvent(event);

            }
        };

        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_game);
        navController = navHostFragment.getNavController();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && event.values[2] > 1.5f && !isTilted) {
            Log.i("tilt", "tilt left");
            isTilted = true;
            if (navController.getCurrentDestination().getId() == R.id.nav2_dare) {
                navController.navigate(R.id.action_nav2_dare_to_nav2_game);
            } else if (navController.getCurrentDestination().getId() == R.id.nav2_truth) {
                navController.navigate(R.id.action_nav2_truth_to_nav2_game);
            } else {
                navController.navigate(R.id.action_nav2_game_to_nav2_truth);
            }
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && event.values[2] < -1.5f && !isTilted) {
            Log.i("tilt", "tilt right");
            isTilted = true;
            if (navController.getCurrentDestination().getId() == R.id.nav2_truth) {
                navController.navigate(R.id.action_nav2_truth_to_nav2_game);
            } else if (navController.getCurrentDestination().getId() == R.id.nav2_dare) {
                navController.navigate(R.id.action_nav2_dare_to_nav2_game);
            } else {
                navController.navigate(R.id.action_nav2_game_to_nav2_dare);
            }
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE && event.values[2] > -0.2f && event.values[2] < 0.2f && isTilted) {
            Log.i("tilt", "tilt reset");
            isTilted = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor Gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (Gyro != null) {
            sensorManager.registerListener(this, Gyro,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
//        handler.post(sensorRead);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
//        handler.removeCallbacks(sensorRead);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example.
    }


    class gestureLis extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            try {
                if (Math.abs(event1.getY() - event2.getY()) > Swipe_max_off_path)
                    return false;
                if (event1.getX() - event2.getX() > Swipe_min && Math.abs(velocityX) > Swipe_threshold) {
                    Log.d(DEBUG_TAG, "Left Swipe: ");
                    if (navController.getCurrentDestination().getId() == R.id.nav2_truth) {
                        navController.navigate(R.id.action_nav2_truth_to_nav2_game);
                    } else if (navController.getCurrentDestination().getId() == R.id.nav2_dare) {
                        navController.navigate(R.id.action_nav2_dare_to_nav2_game);
                    } else {
                        navController.navigate(R.id.action_nav2_game_to_nav2_dare);
                    }

                } else if (event2.getX() - event1.getX() > Swipe_min && Math.abs(velocityX) > Swipe_threshold) {
                    Log.d(DEBUG_TAG, "Right Swipe: ");
                    if (navController.getCurrentDestination().getId() == R.id.nav2_dare) {
                        navController.navigate(R.id.action_nav2_dare_to_nav2_game);
                    } else if (navController.getCurrentDestination().getId() == R.id.nav2_truth) {
                        navController.navigate(R.id.action_nav2_truth_to_nav2_game);
                    } else {
                        navController.navigate(R.id.action_nav2_game_to_nav2_truth);
                    }

                }
            } catch (Exception e) {

            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

    }
}