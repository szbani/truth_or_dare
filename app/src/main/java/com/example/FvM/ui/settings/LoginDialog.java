package com.example.FvM.ui.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;

import org.json.JSONObject;


public class LoginDialog extends DialogFragment {

    private String userName;
    private String password;

    public LoginDialog() {
        this.userName = "";
        this.password = "";
    }

    public LoginDialog(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        FragmentManager fm = getParentFragmentManager();

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        EditText user = view.findViewById(R.id.username);
        EditText pw1 = view.findViewById(R.id.password);

        user.setText(userName);
        pw1.setText(password);

        view.findViewById(R.id.Register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireDialog().dismiss();
                new RegisterDialog().show(getParentFragmentManager(), "Register");
            }
        });

        builder.setView(view).setTitle(R.string.dialog_login);

        Dialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button customPositiveBtn = view.findViewById(R.id.loginConfirm);
                customPositiveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userName = String.valueOf(user.getText());
                        password = String.valueOf(pw1.getText());

                        if (userName.isEmpty()) {
                            Toast.makeText(getContext(), "Nincs megadva Email", Toast.LENGTH_SHORT).show();
                        } else if (password.length() < 6) {
                            Toast.makeText(getContext(), "Leagább 5 karakter hosszú kell legyen a jelszó", Toast.LENGTH_SHORT).show();
                        } else {
                            View loggedIn = getLayoutInflater().inflate(R.layout.logged_in, null);
                            FrameLayout userContainer = getActivity().findViewById(R.id.user_view);
                            loggedIn.findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    settings.logout(activity,fm);
                                }
                            });
                            try {
                                RealmHelper.login(userName, password);
                                if (RealmHelper.isLogged()){
                                    userContainer.removeAllViews();
                                    userContainer.addView(loggedIn);
                                    dismiss();
                                }else{
                                    Toast.makeText(getContext(), "Sikertelen Bejelentkezés", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
//                                    Log.e("Register Hiba", String.valueOf(e));;
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        return dialog;
    }
}
