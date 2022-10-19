package com.example.FvM.ui.players;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.PlayersFragmentBinding;


public class players extends Fragment {

    private  PlayersFragmentBinding binding;
    public Context context;
    private View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = PlayersFragmentBinding.inflate(getLayoutInflater());
        //View inf = inflater.inflate(R.layout.players_fragment, container, false);
        view = binding.getRoot();
        context = view.getContext();

        player_com pc =new player_com(getActivity(),view,getChildFragmentManager());

        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlayerDialog(pc).show(getChildFragmentManager(), PlayerDialog.TAG);
            }
        });


        pc.playerlist();

        return view;
    }
}

