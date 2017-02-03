package german.teach.learn.zero.learnandteachgerman.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.exercises.exe1.Exercise1;

/**
 * Created by zero on 02.02.17.
 */

public class ExerciseLab {

    private static ExerciseLab sExerciseLab;
    private List<Exercise1> mExercise1s;
    private List<String> words;


    public static ExerciseLab get(Context context) {
        if (sExerciseLab == null) {
            sExerciseLab = new ExerciseLab(context);
        }
        return sExerciseLab;
    }

    private ExerciseLab(Context context) {
        mExercise1s = new ArrayList<>();
        words = new ArrayList<>();
    }

    public List<Exercise1> getExercise1s() {
        return mExercise1s;
    }

    public Exercise1 getExercise(UUID id) {
        for (Exercise1 exercise1 : mExercise1s) {
            if (exercise1.getId().equals(id)) {
                return exercise1;
            }
        }
        return null;
    }

    public  List<String> getWords(){
        return words;
    }

}
