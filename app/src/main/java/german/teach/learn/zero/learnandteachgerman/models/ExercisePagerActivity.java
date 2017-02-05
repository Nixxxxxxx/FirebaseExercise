package german.teach.learn.zero.learnandteachgerman.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.R;

/**
 * Created by zero on 02.02.17.
 */

public class ExercisePagerActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private List<Integer> solvedQuestion;
    private ModFragmentStatePagerAdapter myExerciseFragmentAdapter;

    private static final String EXTRA_EXERCISE_ID = "com.zero.nully.exercise_id";

    public static Intent newIntent(Context packageContext, UUID uuid) {
        Intent intent = new Intent(packageContext, ExercisePagerActivity.class);
        intent.putExtra(EXTRA_EXERCISE_ID, uuid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_pager);

        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_EXERCISE_ID);

        solvedQuestion = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);

        myExerciseFragmentAdapter = new ModFragmentStatePagerAdapter(getSupportFragmentManager(), getApplicationContext(), getIntent());
        mViewPager.setAdapter(myExerciseFragmentAdapter);
    }


    public void nextExercise() {
        myExerciseFragmentAdapter.deletePage(mViewPager.getCurrentItem());
    }

}