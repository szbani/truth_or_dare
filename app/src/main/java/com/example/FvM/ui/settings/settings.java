package com.example.FvM.ui.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.FvM.databinding.SettingsFragmentBinding;

import java.util.HashSet;
import java.util.Set;

public class settings extends Fragment {

    private SettingsFragmentBinding binding;
    public  Set<String> packs ;
    public SharedPreferences prefs;
    private Activity activity;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = SettingsFragmentBinding.inflate(getLayoutInflater());
        activity=getActivity();
        View view = binding.getRoot();
        prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        packs = new HashSet<>();

        packs = prefs.getStringSet("packs",packs);

        for (int i = 0; i < binding.Packs.getChildCount(); i++){
            CheckBox ch = (CheckBox) binding.Packs.getChildAt(i);
            if (!packs.isEmpty()){
                ch.setChecked(packs.contains(ch.getText()));
            }
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    packs_Click();
                }
            });
        }
        SwitchCompat sw = (SwitchCompat) binding.random;
        sw.setChecked(get_r_player(activity));
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                set_r_player(b);
            }
        });
        return view;
    }

    public void packs_Click(){
        packs = new HashSet<>();
        for ( int i = 0; i < binding.Packs.getChildCount();i++){
            CheckBox checkBox = (CheckBox) binding.Packs.getChildAt(i);
            if (checkBox.isChecked()){
                String name = checkBox.getText().toString();
                packs.add(name);
            }

        }
        prefs.edit().putStringSet("packs", packs).apply();
    }

    public  void  set_r_player(boolean b){
        prefs.edit().putBoolean("r_player", b).apply();
    }

    public static boolean  get_r_player(Activity activity){
        boolean bool;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        bool = prefs.getBoolean("r_player",false);
        return bool;
    }
}