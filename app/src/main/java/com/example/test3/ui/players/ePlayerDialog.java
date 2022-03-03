package com.example.test3.ui.players;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.util.Log;
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

public class ePlayerDialog extends DialogFragment {
    private String name;
    private int gender;
    private LinearLayout line;
    private int id;

    public ePlayerDialog(String name, int gender,LinearLayout linearLayout,int id){
        this.name = name;
        this.gender = gender;
        this.line = linearLayout;
        this.id = id;
    }
    player_com com = new player_com();

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
                name = name.trim();

                Spinner spinner = (Spinner) view.findViewById(R.id.dialog_gender);
                int gender = 1;
                if (spinner.getSelectedItem().toString().equals("Fiú")){
                    gender = 0;
                }
                if (!name.isEmpty()){
                    LinearLayout p_layout = (LinearLayout) getActivity().findViewById(R.id.players);
                    com.edit_player((LinearLayout)p_layout.getChildAt(id),name,gender);
                    query.player_edit(getContext(),name,gender,id);
                }

            }
        })
                .setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {})
                .setTitle(R.string.dialog_title);

        TextView textname =  view.findViewById(R.id.dialog_name);
        textname.setText(name);
        Spinner spinner = (Spinner) view.findViewById(R.id.dialog_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.dialog_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(gender);

        return builder.create();
    }

    public static String TAG = "EditPlayer";
}