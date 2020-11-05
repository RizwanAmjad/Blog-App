package com.rizwanamjadnov.blog.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rizwanamjadnov.blog.Data.BlogRecyclerAdapter;
import com.rizwanamjadnov.blog.Model.Blog;
import com.rizwanamjadnov.blog.R;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    private List<Blog> blogList;
    private RecyclerView blogRecycler;
    private BlogRecyclerAdapter blogRecyclerAdapter;

    private void initializeItems(){
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("Blog");
        databaseReference.keepSynced(true);

        blogList = new ArrayList<>();
        blogRecycler = (RecyclerView) findViewById(R.id.blogRecycler);
        blogRecycler.setLayoutManager(new LinearLayoutManager(this));
        blogRecycler.hasFixedSize();

        blogRecyclerAdapter = new BlogRecyclerAdapter(PostActivity.this, blogList);
        blogRecycler.setAdapter(blogRecyclerAdapter);
        progressDialog = new ProgressDialog(PostActivity.this);
        progressDialog.setMessage("Loading Posts");
        progressDialog.show();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Blog blog = dataSnapshot.getValue(Blog.class);
                blogList.add(blog);
                blogRecyclerAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null){
                    finish();
                }
            }
        };
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initializeItems();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_logout:
                progressDialog = new ProgressDialog(PostActivity.this);
                progressDialog.setMessage("Loggin You Out!");
                progressDialog.show();
                mAuth.signOut();
                break;
            case R.id.action_add:
                startActivity(new Intent(PostActivity.this, AddPostActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth!=null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}