package german.teach.learn.zero.learnandteachgerman.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.R;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.Exercise1;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.ExerciseOneFragment;
import german.teach.learn.zero.learnandteachgerman.exercises.exe2.ExerciseTwoFragment;

/**
 * Created by zero on 02.02.17.
 */

public class ExercisePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<Exercise1> mExercise1s;
    private  int lastExercise;
    private  String exerciseName;
    private  List<Integer> solvedQuestion;

    private static final String EXTRA_EXERCISE_ID =
            "com.zero.nully.exercise_id";

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, ExercisePagerActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID, uuid);
        return intent;
    }

/*    public static Intent newIntent(Context packageContext, int crimeId) {
        Intent intent = new Intent(packageContext, ExercisePagerActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID, crimeId);
        return intent;
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_pager);

        UUID uuid = (UUID) getIntent()
                .getSerializableExtra(EXTRA_EXERCISE_ID);

        solvedQuestion = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mExercise1s = ExerciseLab.get(this).getExercise1s();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                lastExercise = position;
                Exercise1 exercise1 = mExercise1s.get(position);

                Intent intent = getIntent();
                Bundle data = intent.getExtras();
                exerciseName =  data.getSerializable("Aufgabe").toString();
                if(exerciseName.equals("Aufgabe2")){
                    return ExerciseTwoFragment.newInstance(exercise1.getId());
                }else {
                    return ExerciseOneFragment.newInstance(exercise1.getId());
                }
            }

            @Override
            public int getCount() {
                return mExercise1s.size();
            }
        });

    /*    for (int i = 0; i < mExercise1s.size(); i++) {
            if (mExercise1s.get(i).getId().equals(uuid)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        */
    }


    public  void nextExercise(){
        solvedQuestion.add(lastExercise);
        if(lastExercise < mExercise1s.size()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(lastExercise);
                }
            },1000);
        }
    }

}