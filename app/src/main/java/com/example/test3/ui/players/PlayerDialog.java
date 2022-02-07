package com.example.test3.ui.players;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.test3.R;
import com.example.test3.ui.query.query;

public class PlayerDialog extends DialogFragment {
    query query = new query();
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.getContext(), android.R.style.Theme_Material_Light_Dialog_Alert));
        builder.setView(view);
        builder.setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) view.findViewById(R.id.dialog_name);
                String name = editText.getText().toString();
                while (name.startsWith(" ")){
                    name = name.replaceFirst(" ","");
                }

                Spinner spinner = (Spinner) view.findViewById(R.id.dialog_gender);
                int gender = 1;
                if (spinner.getSelectedItem().toString().equals("Fiú")){
                    gender = 0;
                }
                if (!name.isEmpty()){
                    LinearLayout p_layout = (LinearLayout) getActivity().findViewById(R.id.players);
                    p_layout.addView(add_player(inflater,name,gender));
                    query.player_up(getActivity(),name,gender);
                }

            }
        })
        .setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {})
        .setTitle(R.string.dialog_title);

        Spinner spinner = (Spinner) view.findViewById(R.id.dialog_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.dialog_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    return builder.create();
    }

    public View add_player(LayoutInflater inflater,String name,int gender){
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
                edit_player(line);
            }
        });
        //delete
        ImageButton delete = child.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_player(line);
            }
        });
        return child;
    }

    public void edit_player(LinearLayout layout){

    }

    public void delete_player(LinearLayout layout){
        layout.removeAllViews();
    }

    public static String TAG = "NewPlayerDialog";
}