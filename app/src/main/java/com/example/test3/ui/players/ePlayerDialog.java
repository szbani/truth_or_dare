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
    private Context mcontext;
    public ePlayerDialog(Context context){ this.mcontext = context;}

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
                    p_layout.addView(player_com.add_player(inflater,mcontext,name,gender,p_layout.getChildCount()));
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

    public static String TAG = "EditPlayer";
}