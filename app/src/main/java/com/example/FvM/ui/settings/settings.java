package com.example.FvM.ui.settings;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.SettingsFragmentBinding;

import java.util.HashSet;
import java.util.Set;

public class settings extends Fragment {

    private SettingsFragmentBinding binding;
    public SharedPreferences prefs;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SwitchCompat swRandom = (SwitchCompat) binding.random;
        swRandom.setChecked(get_r_player(getActivity()));
        swRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                set_r_player(b);
            }
        });
        FrameLayout userContainer = view.findViewById(R.id.user_view);
        View userField;
        if (RealmHelper.getLoggedUser()){
            userField = getLayoutInflater().inflate(R.layout.logged_in,null);

            TextView userNameTextView = (TextView) userField.findViewById(R.id.UserNameField);
            userNameTextView.setText("Felhasználó: "+RealmHelper.getUsername());

            userField.findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout(requireActivity(),getParentFragmentManager());
                }
            });
            userContainer.addView(userField);

        }else{
            userField = getLayoutInflater().inflate(R.layout.logged_out,null);

            userField.findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new LoginDialog().show(getChildFragmentManager(),"Login");
                }
            });
            userContainer.addView(userField);
        }

        return view;
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

    public static void logout(Activity activity, FragmentManager fm){

        FrameLayout userContainer = activity.findViewById(R.id.user_view);
        View loggedOut = activity.getLayoutInflater().inflate(R.layout.logged_out,null);
        loggedOut.findViewById(R.id.logOut_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoginDialog().show(fm,"Login");
            }
        });

        RealmHelper.logout();

        userContainer.removeAllViews();
        userContainer.addView(loggedOut);

    }

    private static String TAG = "Debug";
}