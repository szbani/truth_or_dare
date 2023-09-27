package com.example.FvM.ui.packs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.FvM.R;
import com.example.FvM.RealmHelper;
import com.example.FvM.models.Category;
import com.example.FvM.models.Questions;

import org.bson.types.ObjectId;

public class packModifyDialog extends DialogFragment {

    private ObjectId id;
    private Questions question;
    private TextView nameTextView;

    public packModifyDialog(String id) {
        this.id = new ObjectId(id);
    }

    public packModifyDialog(String id, Questions question, TextView nameTextView) {
        this.id = new ObjectId(id);
        this.question = question;
        this.nameTextView = nameTextView;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = requireActivity();
        FragmentManager fm = getParentFragmentManager();
        ObjectId qId = null;
        if (question != null) {
            qId = question.get_id();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.pack_edit_dialog, null);

        EditText questionField = view.findViewById(R.id.questionTextField);
        Spinner spinner = view.findViewById(R.id.questionSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.question_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (qId != null) {
            LinearLayout l = (LinearLayout) spinner.getParent();
            l.removeView(spinner);
            l.removeView(view.findViewById(R.id.categoryTextView));
            questionField.setText(question.getQuestion());
        }

        builder.setView(view);
        ObjectId finalQId = qId;
        builder.setPositiveButton("kész", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                LinearLayout layout;
                if (finalQId == null) {
                    String category;
                    if (spinner.getSelectedItem().toString().equals("Felelsz")) {
                        category = Category.Truth.name();
                        layout = activity.findViewById(R.id.truthLayout);
                    } else {
                        category = Category.Dare.name();
                        layout = activity.findViewById(R.id.dareLayout);
                    }
                    Questions questions = new Questions(questionField.getText().toString(), category);

                    RealmHelper.addQuestion(id, questions);

                    LinearLayout temp = (LinearLayout) inflater.inflate(R.layout.question_temp, null);
                    TextView nameField = (TextView) temp.findViewById(R.id.name);
                    ImageButton editBtn = (ImageButton) temp.findViewById(R.id.edit);
                    ImageButton DelBtn = (ImageButton) temp.findViewById(R.id.delete);

                    editBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            LinearLayout parent = (LinearLayout) v.getParent();
                            new packModifyDialog(id.toString(),questions,(TextView)parent.findViewById(R.id.name)).show(fm, "editQuestion");
                        }
                    });

                    DelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RealmHelper.deleteQuestion(id, questions.get_id());
                            LinearLayout parenttt = (LinearLayout) v.getParent().getParent();
                            parenttt.removeView((LinearLayout) v.getParent());
                        }
                    });

                    nameField.setText(questions.getQuestion());
                    layout.addView(temp);
                } else {
                    RealmHelper.updateQuestion(id, finalQId, questionField.getText().toString());
                    nameTextView.setText(questionField.getText().toString());
                }
            }
        });

        return builder.create();
    }
}
