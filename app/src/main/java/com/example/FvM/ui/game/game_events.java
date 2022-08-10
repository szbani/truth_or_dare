package com.example.FvM.ui.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.GameFragmentBinding;

import java.util.Random;

public class game_events extends Fragment {

    private GameFragmentBinding binding;
    public static Random random;
    private static String player;
    private  String kerdes;
    NavController navController;

    public game_events(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        navController = Navigation.findNavController(getParentFragment().getView());

        binding.FBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_game_to_nav2_truth);
            }
        });
        binding.MBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_game_to_nav2_dare);
            }
        });

        random = new Random();

        player = player_valaszt();
        if (!player.equals("")) {
            player = player.replace(player.substring(player.length()-1), "").trim();
            binding.playerNameText.setText(player);
        }else{
            binding.playerNameText.setText("");
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getKerdes(int type){
        try {

            kerdes = kivalaszt(type).trim();

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
            return kerdes;
        }
        catch (Exception e){
            Log.e( "Exception" ,"ez itt a hiba" + e);
            return e.toString();
        }
    }
    //játkos kiválasztása
    public static String player_valaszt(){
        if (GameActivity.players.size() > 1){
            return GameActivity.players.get(random.nextInt(GameActivity.players.size()));
        }
        else {
            return "";
        }
    }
    //kérdés kiválasztása
    public static String kivalaszt(int type){
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