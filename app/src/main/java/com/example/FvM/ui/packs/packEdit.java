package com.example.FvM.ui.packs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.databinding.PackEditFragmentBinding;
import com.example.FvM.models.Category;
import com.example.FvM.models.Packs;
import com.example.FvM.models.Questions;

import org.bson.types.ObjectId;
import org.w3c.dom.Text;

import java.util.List;

public class packEdit extends Fragment {
    private PackEditFragmentBinding binding;
    private String name;
    private String id;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PackEditFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        name = packEditArgs.fromBundle(getArguments()).getPackName();
        id = packEditArgs.fromBundle(getArguments()).getPackId();
        TextView pageName = view.findViewById(R.id.packNameTextView);
        pageName.setText(name);

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
                new packAddDialog(id).show(getChildFragmentManager(), "editPackName");
            }
        });
        view.findViewById(R.id.new_Question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new packModifyDialog(id).show(getChildFragmentManager(), "addQuestion");
            }
        });

        List<Questions> questions = RealmHelper.getPack(new ObjectId(id)).getQuestions();
        LinearLayout dareField = binding.dareLayout;
        LinearLayout truthField = binding.truthLayout;
        for (Questions q : questions) {
            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.question_temp, null);

            TextView nameField = (TextView) layout.findViewById(R.id.name);
            ImageButton editBtn = (ImageButton) layout.findViewById(R.id.edit);
            ImageButton DelBtn = (ImageButton) layout.findViewById(R.id.delete);
            nameField.setText(q.getQuestion());
            if (q.getCategory().equals(Category.Dare.name())) {
                dareField.addView(layout);
            } else {
                truthField.addView(layout);
            }

            DelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RealmHelper.deleteQuestion(new ObjectId(id),q.get_id());
                    LinearLayout parenttt = (LinearLayout) v.getParent().getParent();
                    parenttt.removeView((LinearLayout) v.getParent());
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    new packModifyDialog(id,q,(TextView)parent.findViewById(R.id.name)).show(getChildFragmentManager(), "editQuestion");
                }
            });

        }


        return view;

    }
}
