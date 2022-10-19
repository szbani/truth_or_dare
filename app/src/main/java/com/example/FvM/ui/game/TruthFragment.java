package com.example.FvM.ui.game;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.databinding.TruthFagmentBinding;

public class TruthFragment extends Fragment {

    private TruthFagmentBinding binding;
    private question_com qcom;
    public TruthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TruthFagmentBinding.inflate(getLayoutInflater());

        binding.truthName.setText(game_events.player);

        qcom = new question_com(getActivity());

        binding.truthQuestionText.setText( qcom.getKerdes(1));

        return binding.getRoot();
    }
}