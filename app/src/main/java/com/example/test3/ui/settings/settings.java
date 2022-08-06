package com.example.test3.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.test3.databinding.SettingsFragmentBinding;

import java.util.HashSet;
import java.util.Set;

public class settings extends Fragment {

    private SettingsFragmentBinding binding;
    public  Set<String> packs ;
    private SettingsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = SettingsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        SharedPreferences sh = getActivity().getSharedPreferences("packok",Context.MODE_PRIVATE);
        packs = new HashSet<>();
        packs = sh.getStringSet("packs",packs);

        for (int i = 0; i < binding.Packs.getChildCount(); i++){
            CheckBox ch = (CheckBox) binding.Packs.getChildAt(i);
            if (!packs.isEmpty()){
                ch.setChecked(packs.contains(ch.getText()));
            }
            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkbox_Click();
                }
            });
        }



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }
    // packok elmentesi sorrendjenek fixelese
    public void checkbox_Click(){
        packs = new HashSet<>();
        for ( int i = 0; i < binding.Packs.getChildCount();i++){
            CheckBox checkBox = (CheckBox) binding.Packs.getChildAt(i);
            if (checkBox.isChecked()){
                String name = checkBox.getText().toString();
                packs.add(name);
            }

        }
        SharedPreferences settings = getActivity().getSharedPreferences("packok", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        editor.putStringSet("packs", packs);

        editor.apply();
    }

}