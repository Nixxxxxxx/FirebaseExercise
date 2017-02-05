package german.teach.learn.zero.learnandteachgerman.models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.ExerciseSelection;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.Exercise1;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.ExerciseOneFragment;
import german.teach.learn.zero.learnandteachgerman.exercises.exe2.ExerciseTwoFragment;

/**
 * Created by zero on 04.02.17.
 */

public class ModFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Integer> page_indexes;
    private List<Exercise1> mExercise1s;
    private Intent mIntent;
    private String exerciseName;

    public ModFragmentStatePagerAdapter(FragmentManager fm, Context ctx, Intent intent) {
        super(fm);
        mExercise1s = new ArrayList(ExerciseLab.get(ctx).getExercise1s());
        mIntent = intent;
        page_indexes = new ArrayList<>();
        for (int i = 0; i < mExercise1s.size(); i++) {
            page_indexes.add(new Integer(i));
        }
    }

    @Override
    public int getCount() {
        return page_indexes.size();
    }

    @Override
    public Fragment getItem(int position) {
        Integer index = page_indexes.get(position);
        Exercise1 exercise1 = mExercise1s.get(position);
        Bundle data = mIntent.getExtras();
        exerciseName = data.getSerializable(ExerciseSelection.AUFGABEN).toString();
        if (exerciseName.equals(ExerciseSelection.AUFGABE1)) {
            return ExerciseOneFragment.newInstance(exercise1.getId());
        } else {
            return ExerciseTwoFragment.newInstance(exercise1.getId());
        }
    }

    public void deletePage(int position) {
        page_indexes.remove(position);
        mExercise1s.remove(position);
        notifyDataSetChanged();
    }

    // This is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
