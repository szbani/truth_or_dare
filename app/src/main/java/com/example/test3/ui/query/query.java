package com.example.test3.ui.query;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.test3.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class query {

    private Set<String> set;

    public void player_up(Activity activity, String name, int gender){
        set = new HashSet<>();
        String player = name + " " + Integer.toString(gender);
        set.add(player);
        SharedPreferences players = activity.getSharedPreferences("players", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = players.edit();

        editor.putStringSet("players",set);

        editor.apply();
    }

    public List<String> player_down(Activity activity){
        SharedPreferences sh = activity.getSharedPreferences("players", Context.MODE_PRIVATE);
        set = sh.getStringSet("players",set);
        List<String> players = new ArrayList<>(set);
        if (players.isEmpty()){
            players.add(R.string.player + " 0");
        }
        return players;
    }

    public void player_edit(){
        set = new HashSet<>();
    }

}
