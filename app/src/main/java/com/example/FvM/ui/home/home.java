package com.example.FvM.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.HomeFragmentBinding;
import com.example.FvM.ui.game.GameActivity;
import com.example.FvM.ui.players.query;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment {

    private HomeFragmentBinding binding;
    private List<String> kerdesek_f;
    private List<String> kerdesek_m;
    private List<String> players;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_scene(view);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void change_scene(View view){
        beolvas beolvas = new beolvas(view.getContext());
        kerdesek_f = beolvas.readline("F");
        kerdesek_m = beolvas.readline("M");
        players = query.player_down(view.getContext());

        Intent intent = new Intent(getContext(), GameActivity.class);

        intent.putStringArrayListExtra("kerdesek_f", (ArrayList<String>) kerdesek_f);
        intent.putStringArrayListExtra("kerdesek_m", (ArrayList<String>) kerdesek_m);
        intent.putStringArrayListExtra("players", (ArrayList<String>) players);

        startActivity(intent);

    }
}