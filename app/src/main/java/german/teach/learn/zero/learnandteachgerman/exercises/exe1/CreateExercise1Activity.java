package german.teach.learn.zero.learnandteachgerman.exercises.exe1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import german.teach.learn.zero.learnandteachgerman.R;

public class CreateExercise1Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int GALERY_REQUEST = 1;
    private String mArticle = null;

    private EditText mWord;
    private ImageButton mSelectImage;
    private Button mBtSubmit;
    private Spinner articleSpinner;
    private Uri mImageUri = null;
    private ProgressDialog mProgressDialog;

    private StorageReference mStorage;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercise1);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Exercise1");
        mStorage = FirebaseStorage.getInstance().getReference();

        mProgressDialog = new ProgressDialog(this);

        mSelectImage = (ImageButton) findViewById(R.id.image_button);
        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galeryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galeryIntent.setType("image/*");
                startActivityForResult(galeryIntent, GALERY_REQUEST);
            }
        });

        mWord = (EditText) findViewById(R.id.word_edit_text);

        mBtSubmit = (Button) findViewById(R.id.submit_button);
        mBtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPosting();
            }
        });

        articleSpinner = (Spinner) findViewById(R.id.spinner_article);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.articles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        articleSpinner.setAdapter(adapter);
        articleSpinner.setOnItemSelectedListener(this);

    }


    private void startPosting() {
        final String word = mWord.getText().toString().trim();
        if (!TextUtils.isEmpty(word) && mArticle != null && mImageUri != null) {
            mProgressDialog.setMessage("Uploading to database");
            mProgressDialog.show();
            StorageReference filepath = mStorage.child("Exercise_Image_" + UUID.randomUUID()).child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabaseReference.push();
                    newPost.child("word").setValue(word);
                    newPost.child("article").setValue(mArticle);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mProgressDialog.dismiss();
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mArticle = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
