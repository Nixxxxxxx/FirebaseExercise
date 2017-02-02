package german.teach.learn.zero.learnandteachgerman.exercises;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import german.teach.learn.zero.learnandteachgerman.R;
import german.teach.learn.zero.learnandteachgerman.models.Exercise1;

public class Exercise1Activity extends AppCompatActivity {

    private RecyclerView mExercise;

    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise1");

        mExercise = (RecyclerView) findViewById(R.id.exercise_list);
        mExercise.setHasFixedSize(true);
        mExercise.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Exercise1,ExerciseViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Exercise1, ExerciseViewHolder>(
                Exercise1.class,
                R.layout.exercise1_row,
                ExerciseViewHolder.class,
                mDatabaseReference

        ) {
            @Override
            protected void populateViewHolder(ExerciseViewHolder viewHolder, Exercise1 model, int position) {
                viewHolder.setTitle(model.getQuestion());
                viewHolder.setMessage(model.getAnswer());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };

        mExercise.setAdapter(firebaseRecyclerAdapter);


    }



    public  static  class ExerciseViewHolder extends  RecyclerView.ViewHolder{
        View mView;

        public ExerciseViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public  void setTitle(String title){
            TextView row_title = (TextView) mView.findViewById(R.id.row_question);
            row_title.setText(title);
        }

        public void setMessage(String message){
            TextView row_message = (TextView) mView.findViewById(R.id.row_answer);
            row_message.setText(message);
        }

        public  void setImage(Context ctx, String image ){
            ImageView row_image = (ImageView) mView.findViewById(R.id.row_image);
            Picasso.with(ctx).load(image).into(row_image);
        }


    }


}
