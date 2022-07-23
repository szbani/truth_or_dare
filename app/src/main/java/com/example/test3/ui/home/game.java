package com.example.test3.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test3.R;
import com.example.test3.databinding.GameFragmentBinding;

import java.util.List;
import java.util.Random;

import com.example.test3.ui.players.query;

public class game extends Fragment {

    private GameFragmentBinding binding;
    private List<String> kerdesek_f;
    private List<String> kerdesek_m;
    private List<String> players;

    private String player;
    String kerdes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(getLayoutInflater());
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
        try {
            player = player_valaszt();
            kerdes = kivalaszt(type).trim();
            if (player != "") {
                kerdes =  (player.replace(player.substring(player.length()-1), "") + kerdes);
            }
            if (kerdes.contains("@person")){
                String person;

                if (players.size() > 1){

                    person = player_valaszt();
                    while(person.equals(player)){
                        person = player_valaszt();
                    }
                    person = person.replace(person.substring(person.length()-1), "").trim();
                }else {
                    int random = new Random().nextInt(2);
                        if (random == 0) person = getString(R.string.Def_Person_0);
                        else person = getString(R.string.Def_Person_1);
                }
                kerdes = kerdes.replace("@person", person);
            }
        }
        catch (Exception e){
            Log.e( "Exception" ,"ez itt a hiba" + e);
        }
    }

    public String player_valaszt(){
        if (players.size() > 1){
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