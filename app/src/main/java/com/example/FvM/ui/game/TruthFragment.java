package com.example.FvM.ui.game;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.R;
import com.example.FvM.databinding.TruthFagmentBinding;

public class TruthFragment extends Fragment {

    private TruthFagmentBinding binding;

    public TruthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = TruthFagmentBinding.inflate(getLayoutInflater());

        binding.truthName.setText(game_events.player);

//        String question = "";
//        do {
//            question = game_events.kivalaszt(0);
//        }while(question.equals(q_temp));
        question_com qcom = new question_com(getActivity(),1);


        binding.truthQuestionText.setText(qcom.getKerdes());

        return binding.getRoot();
    }
}