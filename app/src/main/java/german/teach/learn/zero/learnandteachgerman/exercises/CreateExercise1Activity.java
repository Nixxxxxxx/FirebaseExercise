package german.teach.learn.zero.learnandteachgerman.exercises;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import german.teach.learn.zero.learnandteachgerman.R;

public class CreateExercise1Activity extends AppCompatActivity {

    private ImageButton mSelectImage;
    private EditText mQuestion;
    private EditText mAnswer;
    private Button mBtSubmit;
    private Uri mImageUri = null;
    private ProgressDialog mProgressDialog;

    private static final int GALERY_REQUEST = 1;
    private StorageReference mStorage;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise1);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise1");
        mStorage = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog(this);


        mSelectImage = (ImageButton) findViewById(R.id.imageButton);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galeryIntent.setType("image/*");
                startActivityForResult(galeryIntent, GALERY_REQUEST);
            }
        });

        mQuestion = (EditText) findViewById(R.id.idEditTitle);
        mAnswer = (EditText) findViewById(R.id.idEditMesage);

        mBtSubmit = (Button) findViewById(R.id.idBtSubmit);
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

    }


    private void startPosting() {

        final String question = mQuestion.getText().toString().trim();
        final String answer = mAnswer.getText().toString().trim();
        if (!TextUtils.isEmpty(question) && !TextUtils.isEmpty(answer) && mImageUri != null) {
            mProgressDialog.setMessage("Uploading to database");
            mProgressDialog.show();
            StorageReference filepath = mStorage.child("Exercise_Image").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabaseReference.push();
                    newPost.child("question").setValue(question);
                    newPost.child("answer").setValue(answer);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgressDialog.dismiss();

                    //  startActivity(new Intent(PostActivity.this,MainActivity.class));
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            mSelectImage.setImageURI(mImageUri);
        }
    }
}
