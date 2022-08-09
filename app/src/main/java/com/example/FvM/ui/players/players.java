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

import java.util.List;


public class players extends Fragment {

    private PlayersViewModel mViewModel;
    private PlayersFragmentBinding binding;
    private List<String> players;
    public Context context;
    player_com com = new player_com();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        mViewModel = new ViewModelProvider(this).get(PlayersViewModel.class);
//
//        mViewModel.getplayers().observe(getActivity(), players ->{
//
//        });

        binding = PlayersFragmentBinding.inflate(getLayoutInflater());
        //View inf = inflater.inflate(R.layout.players_fragment, container, false);
        View view = binding.getRoot();
        context = view.getContext();

        view.findViewById(R.id.floating_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlayerDialog().show(getChildFragmentManager(), PlayerDialog.TAG);
            }
        });

        players = query.player_down(view.getContext());
        for (int i = 0; i < players.size();i++){
            try {
                String name = players.get(i);
                int gender = Integer.parseInt(name.substring(name.length()-1));
                name = name.replace(" "+gender,"");
                View child = com.add_player(inflater,this,context,name,gender,i);
                binding.players.addView(child);
            }catch (Exception e){
                Log.e("Exception","ez itt a hiba" + e);
            }

        }
        return view;
    }



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(PlayersViewModel.class);
//        // TODO: Use the ViewModel
//    }


}

