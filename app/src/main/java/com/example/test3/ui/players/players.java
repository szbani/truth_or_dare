package com.example.test3.ui.players;

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
import android.widget.LinearLayout;

import com.example.test3.R;
import com.example.test3.databinding.PlayersFragmentBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class players extends Fragment {

    private PlayersViewModel mViewModel;
    private PlayersFragmentBinding binding;
    private List<String> packs;
    private Context context;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PlayersFragmentBinding.inflate(getLayoutInflater());
        //View inf = inflater.inflate(R.layout.players_fragment, container, false);
        View view = binding.getRoot();

        PlayerDialog dialog = new PlayerDialog();

        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlayerDialog().show(getChildFragmentManager(), PlayerDialog.TAG);
            }
        });

        SharedPreferences sh = getActivity().getSharedPreferences("packok", Context.MODE_PRIVATE);

        Set<String> packsset = new HashSet<>();
        packsset = sh.getStringSet("packs",packsset);
        packs = new ArrayList<>(packsset);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlayersViewModel.class);
        // TODO: Use the ViewModel
    }

    public void add_player(){
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        LinearLayout layout = (LinearLayout) binding.players;

        View child = inflater.inflate(R.layout.player_temp, binding.getRoot());
        layout.addView(child);
    }
}

