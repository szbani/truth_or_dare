package com.example.FvM.ui.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.databinding.DareFragmentBinding;

public class DareFragment extends Fragment {

    private DareFragmentBinding binding;

    public DareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DareFragmentBinding.inflate(getLayoutInflater());

        binding.dareName.setText(game_events.player);
        question_com qcom = new question_com(getActivity(),1);

        binding.dareQuestionText.setText(qcom.getKerdes());

        return binding.getRoot();
    }
}

//    erdekes lehet
//
//    public static DareFragment newInstance(String param1, String param2) {
//        DareFragment fragment = new DareFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//      }
//    }