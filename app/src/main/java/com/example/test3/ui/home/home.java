package com.example.test3.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test3.databinding.HomeFragmentBinding;

import java.util.List;
import java.util.Random;

import com.example.test3.ui.players.query;

public class home extends Fragment {

    private HomeFragmentBinding binding;
    private List<String> kerdesek_f;
    private List<String> kerdesek_m;
    private List<String> players;

    private String player;
    String kerdes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        beolvas beolvas = new beolvas(view.getContext());
        kerdesek_f = beolvas.readline("F");
        kerdesek_m = beolvas.readline("M");

        players = query.player_down(view.getContext());

        binding.FBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_press(0);
                binding.gameText.setText(kerdes);
            }
        });
        binding.MBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_press(1);
                binding.gameText.setText(kerdes);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void btn_press(int type){
        player = player_valaszt();
        kerdes =  (player.replace(player.substring(player.length()-1), "") + kivalaszt(type)).trim();
    }

    public String player_valaszt(){
        if (players.size() > 0){
            int random = new Random().nextInt(players.size());
            return players.get(random);
        }
        else {
            return "";
        }
    }

    public String kivalaszt(int type){
        String kerdes = "";
        try {
            if (type == 0 ){
                int random = new Random().nextInt(kerdesek_f.size());
                kerdes = kerdesek_f.get(random);
            }
            else if (type == 1){
                int random = new Random().nextInt(kerdesek_m.size());
                kerdes = kerdesek_m.get(random);
            }

        }catch (Exception e){
            kerdes = e.toString();
        }
        return kerdes;
    }

}