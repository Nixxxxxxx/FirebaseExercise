package german.teach.learn.zero.learnandteachgerman.exercises;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.models.Exercise1;

/**
 * Created by zero on 02.02.17.
 */

public class ExerciseLab {

    private static ExerciseLab sExerciseLab;
    private List<Exercise1> mExercise1s;

    private DatabaseReference mDatabaseReference;


    public static ExerciseLab get(Context context) {
        if (sExerciseLab == null) {
            sExerciseLab = new ExerciseLab(context);

        }
        return sExerciseLab;
    }

    private ExerciseLab(Context context) {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise1");




        mExercise1s = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Exercise1 exercise1 = new Exercise1();
            exercise1.setQuestion("Crime #" + i);
            exercise1.setAnswer("XX"); // Every other one
            mExercise1s.add(exercise1);
        }
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
}
