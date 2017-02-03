package german.teach.learn.zero.learnandteachgerman.exercises.exe2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.GoogleSignInActivity;
import german.teach.learn.zero.learnandteachgerman.R;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.Exercise1;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.ExerciseOneFragment;
import german.teach.learn.zero.learnandteachgerman.models.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.ExercisePagerActivity;

public class ExerciseTwoFragment extends Fragment {
    private static final String ARG_EXERCISE_ID_2 = "exercise_id1";

    private  GridView mGridView;
    private  String mWord;
    private  String mAnswer;
    private Exercise1 mExercise1;
    private  TextView mTextView;
    private ImageView mImageView;
    private Activity mActivity;


    public static ExerciseTwoFragment newInstance(UUID uuid) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXERCISE_ID_2, uuid);
        ExerciseTwoFragment fragment = new ExerciseTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID) getArguments().getSerializable(ARG_EXERCISE_ID_2);
        mExercise1 = ExerciseLab.get(getActivity()).getExercise(crimeId);
        mWord = mExercise1.getWord();
        mAnswer = "";

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mActivity = (Activity)context;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_exercise2, container, false);
        mTextView = (TextView) v.findViewById(R.id.word_text_view_auf2);
        mTextView.setText(mAnswer);

        mImageView = (ImageView) v.findViewById(R.id.image_view_auf2);
        Picasso.with(getActivity().getApplicationContext()).load(mExercise1.getImage()).into(mImageView);

        mGridView = (GridView) v.findViewById(R.id.gridview);
        mGridView.setAdapter(new MyCustomadapter(getContext(),mWord));


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
                // Send intent to SingleViewActivity
                Log.d("WWWWW", v.toString());
                Log.d("WWWWW", ((TextView) v).getText().toString());
                v.setVisibility(View.INVISIBLE);
                mAnswer += ((TextView) v).getText().toString();
                mTextView.setText(mAnswer);
                if(mAnswer.length() == mWord.length()) {
                    if (mAnswer.equals(mWord)) {
                        ((ExercisePagerActivity) mActivity).nextExercise();
                    }else{
                        Toast.makeText(getContext(), "Just WRONG",
                                Toast.LENGTH_SHORT).show();
                        mGridView.setAdapter(new MyCustomadapter(getContext(),mWord));
                        mAnswer = "";
                    }
                }


            }
        });

        return  v;
    }

}
