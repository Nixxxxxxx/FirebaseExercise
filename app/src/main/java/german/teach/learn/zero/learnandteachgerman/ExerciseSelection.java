package german.teach.learn.zero.learnandteachgerman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import german.teach.learn.zero.learnandteachgerman.exercises.CreateExercise1Activity;
import german.teach.learn.zero.learnandteachgerman.exercises.ExerciseLab;
import german.teach.learn.zero.learnandteachgerman.models.Exercise1;

public class ExerciseSelection extends AppCompatActivity implements View.OnClickListener {
    private Button createExercise_1_Bt;
    private Button exercise_1_Bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        createExercise_1_Bt = (Button)findViewById(R.id.create_exercise_1_bt);
        createExercise_1_Bt.setOnClickListener(this);

        exercise_1_Bt = (Button)findViewById(R.id.exercise_1_bt);
        exercise_1_Bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_exercise_1_bt:
                startActivity(new Intent(ExerciseSelection.this, CreateExercise1Activity.class));

                break;
            case R.id.exercise_1_bt:
                List<Exercise1> mExercise = ExerciseLab.get(this).getExercise1s();

              //  startActivity(new Intent(ExerciseSelection.this, Exercise1Activity.class));
                Intent intent = ExercisePagerActivity.newIntent(ExerciseSelection.this,mExercise.get(0).getId());
                startActivity(intent);
                break;
        }
    }
}
