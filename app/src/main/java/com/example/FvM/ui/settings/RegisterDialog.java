package com.example.FvM.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;


public class RegisterDialog extends DialogFragment {
    private String userName;
    private String password;
    private String password2;

    public RegisterDialog() {

        this.userName = "";
        this.password = "";
        this.password2 = "";
    }

    public RegisterDialog(String userName, String password, String password2) {
        this.userName = userName;
        this.password = password;
        this.password2 = password2;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.register_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        view.findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireDialog().dismiss();
                new LoginDialog().show(getParentFragmentManager(), "Login");
            }
        });
        EditText user = view.findViewById(R.id.username);
        EditText pw1 = view.findViewById(R.id.password);
        EditText pw2 = view.findViewById(R.id.password2);

        user.setText(userName);
        pw1.setText(password);
        pw2.setText(password2);


        builder.setView(view);
        builder.setTitle("Regisztráció");

        Dialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button customPositiveBtn = view.findViewById(R.id.RegisterConfirm);
                customPositiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userName = String.valueOf(user.getText());
                        password = String.valueOf(pw1.getText());
                        password2 = String.valueOf(pw2.getText());

                        if (userName.isEmpty()) {
                            Toast.makeText(getContext(), "Nincs megadva Email", Toast.LENGTH_SHORT).show();
                        } else if (password.equals(password2)) {
                            if (password.length() < 6) {
                                Toast.makeText(getContext(), "Leagább 5 karakter hosszú kell legyen a jelszó", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    RealmHelper.register(userName, password);
                                    requireDialog().dismiss();
                                    new LoginDialog().show(getParentFragmentManager(), "Login");
                                } catch (Exception e) {
//                                    Log.e("Register Hiba", String.valueOf(e));;
                                    e.printStackTrace();
                                }
                            }
                        } else {
//                            new RegisterDialog(userName, password, password2).show(getParentFragmentManager(), "Register");
                            Toast.makeText(getContext(), "A két jelszó nem eggyezik meg", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return dialog;

    }
}