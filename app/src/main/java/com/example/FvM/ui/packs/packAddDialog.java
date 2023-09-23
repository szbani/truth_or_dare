package com.example.FvM.ui.packs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;
import com.example.FvM.ui.home.ePlayerDialog;

public class packAddDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_pack_dialog, null);

        EditText nameField = view.findViewById(R.id.packName);

        builder.setView(view).setTitle("Pack Felvétele");
        builder.setPositiveButton("Kész", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                View pack = inflater.inflate(R.layout.pack_temp,null);
                LinearLayout userPacks = activity.findViewById(R.id.userPacks);

                userPacks.addView(pack);

                TextView editText = pack.findViewById(R.id.name);
                editText.setText(nameField.getText().toString());
                //edit
                ImageButton edit = pack.findViewById(R.id.edit);
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                ImageButton delete = pack.findViewById(R.id.delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinearLayout parenttt = (LinearLayout) v.getParent().getParent();
                        parenttt.removeView((LinearLayout)v.getParent());

                    }
                });
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {});

        return builder.create();
    }
}
