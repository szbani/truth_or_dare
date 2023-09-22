package com.example.FvM.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.HomeFragmentBinding;
import com.example.FvM.models.Category;
import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;
import com.example.FvM.models.Task;
import com.example.FvM.ui.game.GameActivity;
import com.example.FvM.ui.players.query;
import com.example.FvM.ui.settings.settings;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment {

    private HomeFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                change_scene(view);

//                Packs pack = new Packs();
//                pack.setName("pack 1");
//                ObjectId pack_id = RealmHelper.addPack(pack);
//                Log.i("PACK_ID", pack_id.toString());

//                Questions questions = new Questions();
//                questions.setQuestion("asd");
//                questions.setCategory(Category.Dare.name());
//                questions.setPack_id(pack_id);
//                RealmHelper.addQuestion(questions);

                List<Questions> asd = RealmHelper.getQuestions();
                List<Packs> packs = RealmHelper.getPacks();
                Log.v("PACKS", String.valueOf(packs.size()));
//                Log.v("TASKS", asd.toString());
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void change_scene(View view){
        beolvas beolvas = new beolvas(view.getContext());
        List<String> kerdesek_f = beolvas.readline("F");
        List<String> kerdesek_m = beolvas.readline("M");
        List<String> players = query.player_down(view.getContext());

        boolean setting = settings.get_r_player(getActivity());
        Bundle set_bundle = new Bundle();
        set_bundle.putBoolean("p-order",setting);

        Intent intent = new Intent(getContext(), GameActivity.class);

        intent.putStringArrayListExtra("kerdesek_f", (ArrayList<String>) kerdesek_f);
        intent.putStringArrayListExtra("kerdesek_m", (ArrayList<String>) kerdesek_m);
        intent.putStringArrayListExtra("players", (ArrayList<String>) players);
        intent.putExtra("settings",set_bundle);
        startActivity(intent);

    }
}