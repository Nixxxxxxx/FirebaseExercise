package german.teach.learn.zero.learnandteachgerman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.exercises.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.Exercise1;

/**
 * Created by zero on 02.02.17.
 */

public class ExerciseFragment extends Fragment {
    private static final String ARG_CRIME_ID = "crime_id";
    private Exercise1 mExercise1;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static ExerciseFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);
        ExerciseFragment fragment = new ExerciseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mExercise1 = ExerciseLab.get(getActivity()).getExercise(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise1, container, false);
        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mExercise1.getQuestion());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mExercise1.setQuestion(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });
        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setText("XXXX");
        mDateButton.setEnabled(false);

        return v;
    }

}