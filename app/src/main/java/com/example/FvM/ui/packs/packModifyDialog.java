package com.example.FvM.ui.packs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;

import com.example.FvM.R;

public class packModifyDialog extends DialogFragment {

    public packModifyDialog(){

    }
    public packModifyDialog(String edit){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.pack_edit_dialog,null);
        return builder.create();
    }
}
