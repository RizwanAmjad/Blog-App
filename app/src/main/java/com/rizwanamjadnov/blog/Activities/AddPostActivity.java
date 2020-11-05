package com.rizwanamjadnov.blog.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rizwanamjadnov.blog.Model.Blog;
import com.rizwanamjadnov.blog.R;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;

    private Uri mURI;
    private ImageView postImage;
    private EditText postTitle;
    private EditText postDescription;
    private Button submitButton;
    private ProgressDialog progressDialog;

    private static final int GALLERY_CODE = 1;

    private void initializeItems(){
        database = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        databaseReference = database.getReference().child("Blog");
        progressDialog = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        postImage = (ImageView) findViewById(R.id.postImageView);
        postTitle = (EditText) findViewById(R.id.editTextPostTitle);
        postDescription = (EditText) findViewById(R.id.editTextPostDescription);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        initializeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_CODE && resultCode==RESULT_OK){
            mURI = data.getData();
            postImage.setImageURI(mURI);
        }
    }

    private void startPosting() {

        final String title = postTitle.getText().toString().trim();
        final String description = postDescription.getText().toString().trim();
        if(!(title.equals("") || description.equals("") || mURI==null)){
            progressDialog.setMessage("Posting...");
            progressDialog.show();
            // Start Uploading
            final StorageReference filePath = storageReference.child("Post Images").child(mURI.getLastPathSegment());

            filePath.putFile(mURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String UriString = uri.toString();
                            DatabaseReference newPost = databaseReference.push();

                            Map<String, String> blogMap = new HashMap<>();
                            blogMap.put("title", title);
                            blogMap.put("description", description);
                            blogMap.put("image", UriString);
                            blogMap.put("timeStamp", String.valueOf(java.lang.System.currentTimeMillis()));
                            blogMap.put("userId", auth.getUid());

                            newPost.setValue(blogMap);

                            progressDialog.dismiss();
                            finish();
                        }
                    });

                }
            });


        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(AddPostActivity.this);
            dialog.setTitle("Wait!");
            dialog.setMessage("Fill All Fields");
            dialog.show();

        }

    }

}