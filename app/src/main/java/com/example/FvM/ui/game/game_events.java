package com.example.FvM.ui.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.GameFragmentBinding;


public class game_events extends Fragment {

    private GameFragmentBinding binding;
    protected static String player;
    NavController navController;

    public game_events(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = GameFragmentBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getParentFragment().getView());

        binding.truthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_game_to_nav2_truth);
            }
        });
        binding.dareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_game_to_nav2_dare);
            }
        });
        binding.gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        String p_temp = question_com.player_valaszt();
        if (!p_temp.equals("")) {
            while(p_temp.equals(player)){
               p_temp = question_com.player_valaszt();
            }
            player = p_temp;
            Log.e("Player","ez a player:"+player);
        }else{
            player = "Játékos ";
        }
        binding.playerNameText.setText(player.replace(player.substring(player.length()-1), "").trim());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        navController = null;
    }

}