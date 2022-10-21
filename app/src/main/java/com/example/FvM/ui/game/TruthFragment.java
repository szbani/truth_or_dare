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
    public TruthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TruthFagmentBinding.inflate(getLayoutInflater());
        navController = Navigation.findNavController(getParentFragment().getView());
        qcom = new question_com(getActivity());

        binding.truthName.setText(game_events.player);
        binding.truthQuestionText.setText( qcom.getKerdes(0));

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_nav2_truth_to_nav2_game);
            }
        });
        binding.newQBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.truthQuestionText.setText( qcom.getKerdes(1));
                binding.newQBtn.setEnabled(false);
            }
        });

        return binding.getRoot();
    }
}