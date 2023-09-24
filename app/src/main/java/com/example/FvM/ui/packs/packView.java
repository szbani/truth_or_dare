package com.example.FvM.ui.packs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.FvM.R;
import com.example.FvM.databinding.PackNavFragmentBinding;

public class packView extends Fragment {

    private PackNavFragmentBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PackNavFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        final NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_packs);
        NavController navController = navHostFragment.getNavController();

        return view;
    }
}
