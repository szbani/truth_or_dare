package com.example.test3.ui.players;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;

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


import java.util.List;


public class players extends Fragment {

    private PlayersViewModel mViewModel;
    private PlayersFragmentBinding binding;
    private List<String> players;
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

        players = dialog.query.player_down(view.getContext());
        for (int i = 0; i < players.size();i++){
            String name = players.get(i);
            int gender = Integer.parseInt(name.substring(name.length()-1));
            name = name.replace(" "+gender,"");
            View child = dialog.add_player(inflater,name,gender);
            binding.players.addView(child);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PlayersViewModel.class);
        // TODO: Use the ViewModel
    }

}

