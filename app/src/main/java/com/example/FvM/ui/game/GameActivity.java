package com.example.FvM.ui.game;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.FvM.databinding.ActivityGameBinding;

import com.example.FvM.R;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    protected static List<String> K_f;
    protected static List<String> K_m;
    protected static List<String> players;

    private ActivityGameBinding binding;

    private static final int Swipe_min = 120;
    private static final int Swipe_max_off_path = 250;
    private static final int Swipe_threshold = 200;
    private GestureDetector mDetector;
    View.OnTouchListener gestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());

        Intent intent = getIntent();
        setContentView(binding.getRoot());

        K_f = intent.getStringArrayListExtra("kerdesek_f");
        K_m = intent.getStringArrayListExtra("kerdesek_m");
        players = intent.getStringArrayListExtra("players");

        mDetector = new GestureDetector(this, new gestureLis());
        gestureListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return mDetector.onTouchEvent(event);

            }
        };
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
                }else if (event2.getX()-event1.getX() > Swipe_min && Math.abs(velocityX) > Swipe_threshold){
                    Log.d(DEBUG_TAG,"Right Swipe: ");
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