package com.example.FvM.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.HomeFragmentBinding;
import com.example.FvM.models.Category;
import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;
import com.example.FvM.ui.game.GameActivity;
import com.example.FvM.ui.settings.settings;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class home extends Fragment {

    private HomeFragmentBinding binding;
    private static List<String> players;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        binding.startBtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {


//                ObjectId asd = RealmHelper.addPack("Pack1");
//                Log.i("PACK_ID", asd.toString());
//
//                Packs pack = RealmHelper.getPack(new ObjectId("650f1ad58e03896796e541c5"));
//                Log.i("PACK_ID", pack.toString());
//                Questions questions = new Questions();
//                questions.setQuestion("asd");
//                questions.setCategory(Category.Dare.name());
//
//                RealmHelper.addQuestion(pack, questions);


                change_scene(view);

            }
        });

        view.findViewById(R.id.new_Question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PlayerDialog().show(getChildFragmentManager(), PlayerDialog.TAG);

            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        playerlist(getParentFragmentManager(), getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            LinearLayout players = getActivity().findViewById(R.id.players);
            players.removeAllViews();
        } catch (Exception e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void change_scene(View view) {
        // TODO - fix this shit

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> set = new HashSet<>();
        set = sh.getStringSet("packs", set);
        Log.d("PACKS", set.toString());
        String[] pack_ids_string = set.toArray(new String[0]);
        List<ObjectId> pack_ids = new ArrayList<>();
        for (String id : pack_ids_string) {
            pack_ids.add(new ObjectId(id));
        }
        Log.w("PACKSQuery", pack_ids.toString());
        List<Packs> packsList = RealmHelper.getPacks("_id", pack_ids);
        if (packsList == null) {
            packsList = new ArrayList<>();
        }
        ArrayList<String> dareQuestions = new ArrayList<>();
        ArrayList<String> truthQuestions = new ArrayList<>();
        for (Packs pack : packsList) {
            for (Questions question : pack.getQuestions()) {
                if (question.getCategory().equals(Category.Dare.name())) {
                    dareQuestions.add(question.getQuestion());
                } else {
                    truthQuestions.add(question.getQuestion());
                }
            }
        }
        Log.w("DARE", dareQuestions.toString());
        Log.w("TRUTH", truthQuestions.toString());
        List<String> players = query.player_down(view.getContext());
        boolean setting = settings.get_r_player(getActivity());
        Bundle set_bundle = new Bundle();
        set_bundle.putBoolean("p-order", setting);

        Intent intent = new Intent(getContext(), GameActivity.class);

        intent.putStringArrayListExtra("kerdesek_f", truthQuestions);
        intent.putStringArrayListExtra("kerdesek_m", dareQuestions);
        intent.putStringArrayListExtra("players", (ArrayList<String>) players);
        intent.putExtra("settings", set_bundle);
        startActivity(intent);
    }

    public static void playerlist(FragmentManager fm, Activity activity) {
        players = query.player_down(activity.getLayoutInflater().getContext());
        for (int i = 0; i < players.size(); i++) {
            try {
                String name = players.get(i);
                int gender = Integer.parseInt(name.substring(name.length() - 1));
                name = name.replace(" " + gender, "");
                View child = add_player(name, gender, i, fm, activity);
                LinearLayout players = activity.findViewById(R.id.players);
                players.addView(child);
            } catch (Exception e) {
                Log.e("Exception", "(players)ez itt a hiba" + e);
            }

        }
    }

    public static View add_player(String name, int gender, int index, FragmentManager fm, Activity activity) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View child = inflater.inflate(R.layout.player_temp, null);
        //name
        TextView editText = child.findViewById(R.id.name);
        editText.setText(name);
        //edit
        ImageButton edit = child.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ePlayerDialog(name, gender, index).show(fm, ePlayerDialog.TAG);
            }
        });
        //delete
        ImageButton delete = child.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                query.player_delete(inflater.getContext(), index);
                refresh(fm, activity);
            }
        });
        return child;
    }

    public static void refresh(FragmentManager fm, Activity activity) {
        LinearLayout players = activity.findViewById(R.id.players);
        players.removeAllViews();
        home.playerlist(fm, activity);
    }
}