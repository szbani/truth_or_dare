package com.example.test3.ui.players;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test3.R;

public class player_com{

    public static View add_player(LayoutInflater inflater, Context context, String name, int gender, int index){
        //LinearLayout p_layout = (LinearLayout) getActivity().findViewById(R.id.players);

        View child = inflater.inflate(R.layout.player_temp,null);
        //line
        LinearLayout line = child.findViewById(R.id.line);
        //name
        TextView editText = child.findViewById(R.id.name);
        editText.setText(name);
        //edit
        ImageButton edit = child.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.player_edit(context,name,gender,index);
                edit_player(line);
            }
        });
        //delete
        ImageButton delete = child.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.player_delete(context,index);
                delete_player(line);
            }
        });
        return child;
    }

    public static void edit_player(LinearLayout layout){

    }

    public static void delete_player(LinearLayout layout){
        layout.removeAllViews();
    }
}
