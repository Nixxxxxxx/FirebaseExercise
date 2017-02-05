package german.teach.learn.zero.learnandteachgerman.exercises.exe1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.R;
import german.teach.learn.zero.learnandteachgerman.models.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.ExercisePagerActivity;

/**
 * Created by zero on 02.02.17.
 */

public class ExerciseOneFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_EXERCISE_ID_1 = "exercise_id1";
    private Exercise1 mExercise1;
    private TextView mWordText;
    private Button mDerButton;
    private Button mDieButton;
    private Button mDasButton;
    private String mArticle;
    private ImageView mImageView;
    private ImageView mTickView;

    private Activity mActivity;

    public static ExerciseOneFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE_ID_1, uuid);
        ExerciseOneFragment fragment = new ExerciseOneFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID uuid = (UUID) getArguments().getSerializable(ARG_EXERCISE_ID_1);
        mExercise1 = ExerciseLab.get(getActivity()).getExercise(uuid);
        mArticle = mExercise1.getArticle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_exercise1, container, false);
        mWordText = (TextView) v.findViewById(R.id.word_label);
        mWordText.setText(mExercise1.getWord());

        mDerButton = (Button) v.findViewById(R.id.der_button);
        mDerButton.setOnClickListener(this);
        mDieButton = (Button) v.findViewById(R.id.die_button);
        mDieButton.setOnClickListener(this);
        mDasButton = (Button) v.findViewById(R.id.das_button);
        mDasButton.setOnClickListener(this);

        mImageView = (ImageView) v.findViewById(R.id.word_view);
        Picasso.with(getActivity().getApplicationContext()).load(mExercise1.getImage()).into(mImageView);

        mTickView = (ImageView) v.findViewById(R.id.tick_view);
        mTickView.setVisibility(View.GONE);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mActivity = (Activity)context;
        }
    }

    @Override
    public void onClick(View view) {
        if (((Button) view.findViewById(view.getId())).getText().equals(mArticle)) {
            mTickView.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((ExercisePagerActivity)mActivity).nextExercise();
                }
            },1000);
        } else {
            Toast.makeText(getContext(), "Falsch :(", Toast.LENGTH_SHORT).show();
        }
    }
}