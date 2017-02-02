package german.teach.learn.zero.learnandteachgerman;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.exercises.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.Exercise1;

/**
 * Created by zero on 02.02.17.
 */

public class ExercisePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<Exercise1> mExercise1s;

    private static final String EXTRA_EXERCISE_ID =
            "com.zero.nully.exercise_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) {
        Intent intent = new Intent(packageContext, ExercisePagerActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID, crimeId);
        return intent;
    }

    public static Intent newIntent(Context packageContext, int crimeId) {
        Intent intent = new Intent(packageContext, ExercisePagerActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_pager);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_EXERCISE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
        mExercise1s = ExerciseLab.get(this).getExercise1s();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Exercise1 exercise1 = mExercise1s.get(position);
                return ExerciseFragment.newInstance(exercise1.getId());
            }

            @Override
            public int getCount() {
                return mExercise1s.size();
            }
        });

        for (int i = 0; i < mExercise1s.size(); i++) {
            if (mExercise1s.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

}