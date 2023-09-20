package com.example.FvM.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;

import org.json.JSONObject;


public class RegisterDialog extends DialogFragment {

    private JSONObject user = new JSONObject();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.register_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireDialog().dismiss();
                new LoginDialog().show(getParentFragmentManager(), "Login");
            }
        });

        builder.setView(view)
                .setPositiveButton(R.string.dialog_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText username = view.findViewById(R.id.username);
                        EditText pw1 = view.findViewById(R.id.password);
                        EditText pw2 = view.findViewById(R.id.password2);
                        try {
                            user.put("username", username.getText());
                            user.put("pw1", pw1.getText());
                            user.put("pw2", pw2.getText());
                        } catch (Exception e) {
                            Log.e("JSON", "onClick: " + e);
                        }
                        if ((pw1.getText()).equals(pw2.getText())) {
                            dismiss();
                        } else {
                            Toast.makeText(getContext(), "A két jelszó nem eggyezik meg", Toast.LENGTH_SHORT).show();
                        }
                        //backend
                    }
                });
        builder.setNegativeButton(R.string.dialog_cancel, (dialogInterface, i) -> {
                })
                .setTitle(R.string.dialog_register);

        return builder.create();
    }
}