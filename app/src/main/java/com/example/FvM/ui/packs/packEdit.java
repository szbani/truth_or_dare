package com.example.FvM.ui.packs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.FvM.R;
import com.example.FvM.databinding.PackEditFragmentBinding;

public class packEdit extends Fragment {
    private PackEditFragmentBinding binding ;
    private String name;
    private String id;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        binding = PackEditFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

//        name= args.name;

        NavController navController = Navigation.findNavController(getParentFragment().getView());

        view.findViewById(R.id.back_pack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_packEdit_to_packs);
            }
        });
        view.findViewById(R.id.editPackName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.findViewById(R.id.)
                new packAddDialog().show(getChildFragmentManager(),"editPackName");
            }
        });

        return view;

    }
}
