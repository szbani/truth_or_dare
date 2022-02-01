package com.example.test3.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test3.databinding.HomeFragmentBinding;

import java.util.List;
import java.util.Random;

public class home extends Fragment {

    private HomeFragmentBinding binding;
    private List<String> kerdesek_f;
    private List<String> kerdesek_m;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        beolvas beolvas = new beolvas(view.getContext());
        kerdesek_f = beolvas.readline("F");
        kerdesek_m = beolvas.readline("M");

        binding.FBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.gameText.setText(kivalaszt(0));
            }
        });
        binding.MBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.gameText.setText(kivalaszt(1));
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public String kivalaszt(int type){
        String kerdes = "";
        try {
            if (type == 0 ){
                int random = new Random().nextInt(kerdesek_f.size());
                kerdes = kerdesek_f.get(random);
            }
            else if (type == 1){
                int random = new Random().nextInt(kerdesek_m.size());
                kerdes = kerdesek_m.get(random);
            }

        }catch (Exception e){
            kerdes = e.toString();
        }
        return kerdes;
    }

}