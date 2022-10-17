package com.example.FvM.ui.game;

import android.content.Context;
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
    protected static String player;
    NavController navController;

    public game_events(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getParentFragment().getView());
        random = new Random();
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
        String p_temp = player_valaszt();
        if (!p_temp.equals("")) {
            while(p_temp.equals(player)){
               p_temp =player_valaszt();
            }
            player = p_temp;
            Log.e("Player","ez a player:"+player);
            binding.playerNameText.setText(player.replace(player.substring(player.length()-1), "").trim());
        }else{
            binding.playerNameText.setText("");
        }

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    //@person
    public static String kerdes_Person(String kerdes, Context context) {
        try {
            //kérdés személy cseréléses
            if (kerdes.contains("@person")) {
                String person;

                if (GameActivity.players.size() > 1) {

                    person = player_valaszt();
                    while (person.equals(player)) {
                        person = player_valaszt();
                    }
                    person = person.replace(person.substring(person.length() - 1), "").trim();
                } else {
                    int ran = random.nextInt(2);
                    if (ran == 0) person =  context.getString(R.string.Def_Person_0);
                    else person = context.getString(R.string.Def_Person_1);
                }
                kerdes = kerdes.replace("@person", person);
            }
        } catch (Exception e) {
            Log.e("Exception", "(kerdes_person)ez itt a hiba" + e);
            return e.toString();
        }
        return kerdes;
    }
    //@sit
    public static String kerdes_Sit(String kerdes, Context context){
        try {
            //kérdés jobbra/balra ülő
            if(kerdes.contains("@sit")){
                if (GameActivity.players.size() > 2){
                    String way = "jobbra";
                    if ( random.nextInt(2)==0){
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
                    kerdes = kerdes.replace("@sit","valamelyik személy");
                }
            }
        }
        catch (Exception e){
            Log.e( "Exception" ,"(kerdes_sit)ez itt a hiba" + e);
            return e.toString();
        }
        return kerdes;
    }

    //@gender
    public static String kerdes_Gender(String kerdes, Context context) {
        try {
            //kérdés személy cseréléses
            if (kerdes.contains("@gender")) {
                String person;

                if (GameActivity.players.size() > 1) {

                    person = player_valaszt();
                    while (person.equals(player) || person.substring(person.length() - 1).equals(player.substring(player.length() -1))) {
                        person = player_valaszt();
                    }
                    person = person.replace(person.substring(person.length() - 1), "").trim();
                } else {
                    int ran = random.nextInt(2);
                    if (ran == 0) person =  context.getString(R.string.Def_Person_0);
                    else person = context.getString(R.string.Def_Person_1);
                }
                kerdes = kerdes.replace("@person", person);
            }
        } catch (Exception e) {
            Log.e("Exception", "(kerdes_gender)ez itt a hiba" + e);
            return e.toString();
        }
        return kerdes;
    }
    //játkos kiválasztása
    public static String player_valaszt(){
        try {
            if (GameActivity.players.size() > 1){
                return GameActivity.players.get(random.nextInt(GameActivity.players.size()));
            }
            else {
                return "";
            }
        }catch (Exception e){
            Log.e("Exception","(player_valaszt)hiba: ");
        }
        return "";
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
        return kerdes.trim();
    }

}