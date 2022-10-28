package com.example.FvM.ui.game;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.TruthFagmentBinding;

public class TruthFragment extends Fragment {

    private TruthFagmentBinding binding;
    private question_com qcom;
    private NavController navController;
    public static String t_kerdes;
    public String t_kerdes_last;

    public TruthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TruthFagmentBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getParentFragment().getView());
        qcom = new question_com(getActivity());
        String player = game_events.player;

        binding.truthName.setText(player.replace(player.substring(player.length()-1), "").trim());
        String temp = qcom.getKerdes(0);
        while(temp.equals(t_kerdes)){
            temp = qcom.getKerdes(0);
        }
        t_kerdes_last = t_kerdes;
        t_kerdes = temp;
        binding.truthQuestionText.setText( t_kerdes);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_truth_to_nav2_game);
            }
        });
        binding.newQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = qcom.getKerdes(0);
                while(temp.equals(t_kerdes) || temp.equals(t_kerdes_last)){
                    temp = qcom.getKerdes(0);
                }
                t_kerdes = temp;
                binding.truthQuestionText.setText( t_kerdes);
                binding.newQBtn.setEnabled(false);
            }
        });

        return binding.getRoot();
    }
}