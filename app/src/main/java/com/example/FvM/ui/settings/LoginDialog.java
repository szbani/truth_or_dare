package com.example.FvM.ui.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;

import org.json.JSONObject;



public class LoginDialog extends DialogFragment {

    private String userName;
    private String password;

    public LoginDialog(){
        this.userName = "";
        this.password = "";
    }

    public LoginDialog(String userName,String password){
        this.userName = userName;
        this.password = password;
    }

    private JSONObject user = new JSONObject();
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        EditText username = view.findViewById(R.id.username);
        EditText pw1 = view.findViewById(R.id.password);

        username.setText(userName);
        pw1.setText(password);

        view.findViewById(R.id.Register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireDialog().dismiss();
                new RegisterDialog().show(getParentFragmentManager(),"Register");
            }
        });

        builder.setView(view)
                .setPositiveButton(R.string.dialog_login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            user.put("username",username.getText());
                            user.put("pw1",pw1.getText());
                        }catch (Exception e){
                            Log.e("JSON", "onClick: "+ e);
                        }
                        //backend
                    }
                })
                .setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {})
                .setTitle(R.string.dialog_login);

        return builder.create();
    }
}
