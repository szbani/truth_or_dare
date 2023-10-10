package com.example.FvM.ui.game;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.DareFragmentBinding;

public class DareFragment extends Fragment {

    private DareFragmentBinding binding;
    private question_com qcom;
    private NavController navController;
    public static String d_kerdes;
    public String d_kerdes_last;

    public DareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DareFragmentBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getParentFragment().getView());
        qcom = new question_com(getActivity());
        String player = game_events.player;

        binding.dareName.setText(player.replace(player.substring(player.length()-1), "").trim());
        String temp = qcom.getKerdes(1);
        while(temp.equals(d_kerdes)){
            temp = qcom.getKerdes(1);
        }
        d_kerdes_last = d_kerdes;
        d_kerdes = temp;
        binding.dareQuestionText.setText(d_kerdes);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_dare_to_nav2_game);
            }
        });
        binding.newQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = qcom.getKerdes(1);
                while(temp.equals(d_kerdes) || temp.equals(d_kerdes_last)){
                    temp = qcom.getKerdes(1);
                }
                d_kerdes = temp;
                binding.dareQuestionText.setText( d_kerdes);
                binding.newQBtn.setEnabled(false);
            }
        });

        return binding.getRoot();
    }
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
        navController = null;
        qcom = null;
        d_kerdes_last = null;
    }
}
