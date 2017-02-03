package german.teach.learn.zero.learnandteachgerman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import german.teach.learn.zero.learnandteachgerman.exercises.exe1.CreateExercise1Activity;
import german.teach.learn.zero.learnandteachgerman.models.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.ExercisePagerActivity;
import german.teach.learn.zero.learnandteachgerman.exercises.exe1.Exercise1;

public class ExerciseSelection extends AppCompatActivity implements View.OnClickListener {
    private Button createExercise_1_Bt;
    private Button exercise_1_Bt;
    private Button exercise_2_Bt;
    List<Exercise1> mExercise;
    List<String> mWords;
    private DatabaseReference mDatabaseReference;
    ValueEventListener mValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        createExercise_1_Bt = (Button) findViewById(R.id.create_exercise_1_bt);
        createExercise_1_Bt.setOnClickListener(this);

        exercise_1_Bt = (Button) findViewById(R.id.exercise_1_bt);
        exercise_1_Bt.setOnClickListener(this);

        exercise_2_Bt = (Button) findViewById(R.id.exercise_2_bt);
        exercise_2_Bt.setOnClickListener(this);

        mExercise = ExerciseLab.get(this).getExercise1s();
        mWords = ExerciseLab.get(this).getWords();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise1");
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                for (com.google.firebase.database.DataSnapshot data : dataSnapshot.getChildren()) {
                    if (!mWords.contains(data.child("word").getValue().toString())) {
                        Exercise1 exercise = new Exercise1();
                        exercise.setWord(data.child("word").getValue().toString());
                        exercise.setArticle(data.child("article").getValue().toString());
                        exercise.setImage(data.child("image").getValue().toString());
                        mExercise.add(exercise);
                        mWords.add(data.child("word").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("TAG", "getUser:onCancelled", databaseError.toException());
            }
        };

    }


    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseReference.addValueEventListener(mValueEventListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabaseReference.removeEventListener(mValueEventListener);
    }

    @Override
    public void onClick(View view) {
        Bundle data = new Bundle();
        switch (view.getId()) {
            case R.id.create_exercise_1_bt:
                startActivity(new Intent(ExerciseSelection.this, CreateExercise1Activity.class));
                break;
            case R.id.exercise_1_bt:
                data.putSerializable("Aufgabe", "Aufgabe1");
                Intent intent = ExercisePagerActivity.newIntent(ExerciseSelection.this, mExercise.get(0).getId());
                intent.putExtras(data);
                startActivity(intent);
                break;
            case  R.id.exercise_2_bt:
                Log.d("HHHHH","WWWWWWWW");
                data.putSerializable("Aufgabe", "Aufgabe2");
                Intent intent1 = ExercisePagerActivity.newIntent(ExerciseSelection.this, mExercise.get(0).getId());
                intent1.putExtras(data);
                startActivity(intent1);
                break;
        }
    }
}
