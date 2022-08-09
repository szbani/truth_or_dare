package com.example.FvM.ui.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.GameFragmentBinding;

import java.util.List;
import java.util.Random;

public class game_events extends Fragment {

    private GameFragmentBinding binding;
    private Random random;
    private String player;
    private String kerdes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        random = new Random();
        binding = GameFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


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
            if (!player.equals("")) {
                player = player.replace(player.substring(player.length()-1), "").trim();
                binding.playerNameText.setText(player);
            }else{
                binding.playerNameText.setText("");
            }
            //kérdés személy cseréléses
            if (kerdes.contains("@person")){
                String person;

                if (GameActivity.players.size() > 1){

                    person = player_valaszt();
                    while(person.equals(player)){
                        person = player_valaszt();
                    }
                    person = person.replace(person.substring(person.length()-1), "").trim();
                }else {
                    int ran = random.nextInt(2);
                        if (ran == 0) person = getString(R.string.Def_Person_0);
                        else person = getString(R.string.Def_Person_1);
                }
                kerdes = kerdes.replace("@person", person);
            }
            //kérdés jobbra/balra ülő
            if(kerdes.contains("@sit")){
                if (GameActivity.players.size() > 2){
                    String way = "jobbra";
                    if ((int) random.nextInt(2)==0){
                        way = "balra";
                    }
                    int ran = random.nextInt(GameActivity.players.size()-1);
                    if (ran==0){
                        kerdes = kerdes.replace("@sit",way+" ulonek");
                    }else{
                        ran = ran+1;
                        kerdes = kerdes.replace("@sit",way +" "+ ran +".-dik szemely");
                    }
                }else{
                    kerdes = kerdes.replace("@sit","tudja a faszom");
                }
            }
        }
        catch (Exception e){
            Log.e( "Exception" ,"ez itt a hiba" + e);
        }
    }
    //játkos kiválasztása
    public String player_valaszt(){
        if (GameActivity.players.size() > 1){
            return GameActivity.players.get(random.nextInt(GameActivity.players.size()));
        }
        else {
            return "";
        }
    }
    //kérdés kiválasztása
    public String kivalaszt(int type){
        String kerdes = "";
        try {
            if (type == 0 ){
                kerdes = GameActivity.K_f.get(random.nextInt(GameActivity.K_f.size()));
            }
            else if (type == 1){
                kerdes = GameActivity.K_m.get(random.nextInt(GameActivity.K_m.size()));
            }

        }catch (Exception e){
            kerdes = e.toString();
        }
        return kerdes;
    }

}