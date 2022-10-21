package com.example.FvM.ui.players;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.FvM.R;

import java.util.List;

public class player_com {
    private final Activity activity;
    private final LayoutInflater inflater;
    private static List<String> players;
    private final  View view;
    private final FragmentManager fa;

    public player_com(Activity activity, View view, FragmentManager fa){
        this.activity = activity;
        this.inflater = activity.getLayoutInflater();
        this.fa = fa;
        this.view = view;
    }
    public void playerlist(){
        players = query.player_down(inflater.getContext());
        for (int i = 0; i < players.size();i++){
            try {
                String name = players.get(i);
                int gender = Integer.parseInt(name.substring(name.length()-1));
                name = name.replace(" "+gender,"");
                View child = add_player(name,gender,i);
                LinearLayout players = view.findViewById(R.id.players);
                players.addView(child);
            }catch (Exception e){
                Log.e("Exception","(players)ez itt a hiba" + e);
            }

        }
    }

    public void refresh(){
        LinearLayout players = view.findViewById(R.id.players);
        players.removeAllViews();
        playerlist();
    }

    public View add_player(String name, int gender, int index){
        View child = inflater.inflate(R.layout.player_temp,null);
        //name
        TextView editText = child.findViewById(R.id.name);
        editText.setText(name);
        //edit
        ImageButton edit = child.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ePlayerDialog(name, gender,index,new player_com(activity,view,fa)).show(fa, ePlayerDialog.TAG);
            }
        });
        //delete
        ImageButton delete = child.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.player_delete(inflater.getContext(), index);
                refresh();
            }
        });
        return child;
    }
}
