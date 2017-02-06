package german.teach.learn.zero.learnandteachgerman.exercises.exe1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
    private Uri tmpImageUri = null;
    private Bitmap defaultBitmap;
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

        defaultBitmap = ((BitmapDrawable)mSelectImage.getDrawable()).getBitmap();

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

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void startPosting() {
        final String word = mWord.getText().toString().trim();
        if (!TextUtils.isEmpty(word) && mArticle != null && mImageUri != null) {
            mProgressDialog.setMessage("Uploading to database");
            mProgressDialog.show();


            StorageReference filepath = mStorage.child("Exercise_Image_" + UUID.randomUUID()).child(tmpImageUri.getLastPathSegment());
            filepath.putFile(tmpImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDatabaseReference.push();
                    newPost.child("word").setValue(word);
                    newPost.child("article").setValue(mArticle);
                    newPost.child("image").setValue(downloadUrl.toString());

                    mWord.setText("");
                    mSelectImage.setImageBitmap(defaultBitmap);
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

            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(mImageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImage = getResizedBitmap(selectedImage, 400);// 400 is for example, replace with desired size
                tmpImageUri = getImageUri(getApplicationContext(),selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mArticle = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
