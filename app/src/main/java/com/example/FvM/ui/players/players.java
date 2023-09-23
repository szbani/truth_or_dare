package com.example.FvM.ui.players;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.PlayersFragmentBinding;
import com.example.FvM.ui.home.PlayerDialog;


public class players extends Fragment {

    private PlayersFragmentBinding binding;
    private View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PlayersFragmentBinding.inflate(getLayoutInflater());
        //View inf = inflater.inflate(R.layout.players_fragment, container, false);
        view = binding.getRoot();

//        player_com pc =new player_com(getActivity(),view,getChildFragmentManager());

//        view.findViewById(R.id.new_player).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new PlayerDialog(pc).show(getChildFragmentManager(), PlayerDialog.TAG);
//            }
//        });
//
//
//        pc.playerlist();

        return view;
    }
}

