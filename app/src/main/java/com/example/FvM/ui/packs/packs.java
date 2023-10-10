package com.example.FvM.ui.packs;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
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
import android.widget.Toast;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.PacksFragmentBinding;
import com.example.FvM.models.Packs;
import com.example.FvM.ui.packs.packsDirections.ActionPacksToPackEdit;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class packs extends Fragment {

    private PacksFragmentBinding binding;
    public SharedPreferences prefs;
    public Set<String> packs;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PacksFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
        packs = new HashSet<>();

        navController = Navigation.findNavController(getParentFragment().getView());

        binding.newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RealmHelper.getLoggedUser())
                    new packAddDialog().show(getParentFragmentManager(), "packAdd");
                else
                    Toast.makeText(getContext(), "Jelentkezz be előbb", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void onResume() {
        super.onResume();

        List<Packs> packsList = RealmHelper.getPacks();
        if (packsList == null) {
            packsList = new ArrayList<>();
        }
        Log.i("packlist", String.valueOf(packsList));
        LinearLayout defaultPacks = binding.defPacks;
        LinearLayout userPacks = binding.userPacks;
        Boolean logged = RealmHelper.getLoggedUser();
        packs = prefs.getStringSet("packs", packs);
        for (Packs pack : packsList) {
            LinearLayout userPack = (LinearLayout) getLayoutInflater().inflate(R.layout.pack_temp, null);
            CheckBox nameField = (CheckBox) userPack.findViewById(R.id.name);
            if (packs.contains(String.valueOf(pack.get_id()))) {
                nameField.setChecked(true);
            }

            ImageButton editBtn = (ImageButton) userPack.findViewById(R.id.edit);
            ImageButton deleteBtn = (ImageButton) userPack.findViewById(R.id.delete);

            nameField.setText(pack.getName());
            nameField.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    packs = prefs.getStringSet("packs", null);
                    if (nameField.isChecked())
                        packs.add(String.valueOf(pack.get_id()));
                    else {
                        packs.remove(String.valueOf(pack.get_id()));
                    }
                    prefs.edit().remove("packs").commit();
                    prefs.edit().putStringSet("packs", packs).commit();
                }
            });

            if (pack.getOwner_id().equals("default")) {
                userPack.removeView(editBtn);
                userPack.removeView(deleteBtn);
                defaultPacks.addView(userPack);
            } else if (logged) {

                editBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionPacksToPackEdit action = packsDirections.actionPacksToPackEdit(pack.getName(), String.valueOf(pack.get_id()));
                        navController.navigate(action);
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
    }

    public void onPause() {
        super.onPause();
        LinearLayout defaultPacks = binding.defPacks;
        LinearLayout userPacks = binding.userPacks;
        userPacks.removeAllViews();
        defaultPacks.removeAllViews();
    }
}

