package com.example.FvM.ui.packs;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.PacksFragmentBinding;
import com.example.FvM.models.Packs;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class packs extends Fragment {

    private PacksFragmentBinding binding;
    public SharedPreferences prefs;
    public Set<String> packs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PacksFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        NavController navController = Navigation.findNavController(getParentFragment().getView());

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        packs = new HashSet<>();

        packs = prefs.getStringSet("packs", packs);

        binding.newPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new packAddDialog().show(getParentFragmentManager(), "packAdd");
            }
        });

        for (int i = 1; i < binding.defPacks.getChildCount(); i++) {
            CheckBox ch = (CheckBox) binding.defPacks.getChildAt(i);
            if (!packs.isEmpty()) {
                ch.setChecked(packs.contains(ch.getText()));
            }
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    packs_Click();
                }
            });
        }

        List<Packs> packsList = RealmHelper.getPacks();
        Log.i("packlist", String.valueOf(packsList));
        LinearLayout defaultPacks = binding.defPacks;
        LinearLayout userPacks = binding.userPacks;
        for (Packs pack : packsList) {
            LinearLayout userPack = (LinearLayout) inflater.inflate(R.layout.pack_temp, null);
            CheckBox nameField = (CheckBox) userPack.findViewById(R.id.name);

            ImageButton editBtn = (ImageButton) userPack.findViewById(R.id.edit);
            ImageButton deleteBtn = (ImageButton) userPack.findViewById(R.id.delete);

            nameField.setText(pack.getName());

            if (pack.getOwner_id().equals("default")) {
                userPack.removeView(editBtn);
                userPack.removeView(deleteBtn);
                defaultPacks.addView(userPack);
            } else {
                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Confirmation action =
                        navController.navigate(R.id.action_packs_to_packEdit);
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RealmHelper.deletePack(pack.get_id());
                        LinearLayout parenttt = (LinearLayout) v.getParent().getParent();
                        parenttt.removeView((LinearLayout) v.getParent());
                    }
                });

                userPacks.addView(userPack);
            }
        }

        return view;
    }

    public void packs_Click() {
        packs = new HashSet<>();
        for (int i = 0; i < binding.defPacks.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) binding.defPacks.getChildAt(i);
            if (checkBox.isChecked()) {
                String name = checkBox.getText().toString();
                packs.add(name);
            }

        }
        prefs.edit().putStringSet("packs", packs).apply();
    }

}

