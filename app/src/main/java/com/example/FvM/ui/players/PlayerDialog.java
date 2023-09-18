package com.example.FvM.ui.players;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;

public class PlayerDialog extends DialogFragment {

    private final player_com pc;
    public PlayerDialog(player_com pc){
        this.pc = pc;
    }
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
                EditText editText = view.findViewById(R.id.dialog_name);
                String name = editText.getText().toString();
                name = name.trim();

                Spinner spinner = view.findViewById(R.id.dialog_gender);
                int gender = 1;
                if (spinner.getSelectedItem().toString().equals("Fiú")){
                    gender = 0;
                }
                if (!name.isEmpty()){
                    query.player_up(getActivity(),name,gender);
                    pc.refresh();
                }
            }
        })
        .setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {})
        .setTitle(R.string.dialog_title);

        Spinner spinner = view.findViewById(R.id.dialog_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.dialog_gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

    return builder.create();
    }

    public static String TAG = "AddPlayer";
}