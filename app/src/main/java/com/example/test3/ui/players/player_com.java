package com.example.test3.ui.players;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.test3.R;

public class player_com {

    public View add_player(LayoutInflater inflater,Fragment fragment, Context context, String name, int gender, int index){
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
                new ePlayerDialog(name, gender,line,index).show(fragment.getChildFragmentManager(), PlayerDialog.TAG);

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

    public void edit_player(LinearLayout layout, String name, int gender){

       TextView textView = (TextView)layout.getChildAt(0);
       textView.setText(name);

    }

    public void delete_player(LinearLayout layout){
        layout.removeAllViews();
    }
}
