package com.example.FvM.ui.packs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.ui.home.ePlayerDialog;

import org.bson.types.ObjectId;
import org.w3c.dom.Text;

public class packAddDialog extends DialogFragment {

    private String id;
    public packAddDialog(String id){
        this.id = id;
    }
    public packAddDialog(){

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = requireActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.add_pack_dialog, null);

        TextView textField = activity.findViewById(R.id.packNameTextView);
        EditText nameField = view.findViewById(R.id.packName);
        if (id != null){
            nameField.setText(textField.getText());
        }
        NavController navController = Navigation.findNavController(getParentFragment().getView());

        builder.setView(view).setTitle("Pack Felvétele");
        builder.setPositiveButton("Kész", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                View pack = inflater.inflate(R.layout.pack_temp,null);
                LinearLayout userPacks = activity.findViewById(R.id.userPacks);

                String name = nameField.getText().toString();
                ObjectId packId;
                if (id == null) {
                    packId = RealmHelper.addPack(name);
                    userPacks.addView(pack);

                    CheckBox editText = pack.findViewById(R.id.name);
                    editText.setText(name);
                    //edit
                    ImageButton edit = pack.findViewById(R.id.edit);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            packsDirections.ActionPacksToPackEdit action = packsDirections.actionPacksToPackEdit(name,String.valueOf(packId));
                            navController.navigate(action);
                        }
                    });
                    ImageButton delete = pack.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RealmHelper.deletePack(packId);
                            LinearLayout parenttt = (LinearLayout) v.getParent().getParent();
                            parenttt.removeView((LinearLayout)v.getParent());

                        }
                    });
                }
                else{
                    ObjectId objid = new ObjectId(id);
                    RealmHelper.updateName(objid,name);
                    textField.setText(name);
                }

            }
        });
        builder.setNegativeButton(R.string.dialog_cancel,(dialogInterface, i) -> {});

        return builder.create();
    }
}
