package com.rizwanamjadnov.blog.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rizwanamjadnov.blog.R;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private EditText userEmail;
    private EditText userPassword;
    private Button loginButton;
    private Button signUpButton;

    private ProgressDialog progressDialog;

    private void initializeItems(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking your Login Status!");
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();


        userEmail = (EditText) findViewById(R.id.editTextUserEmail);
        userPassword = (EditText) findViewById(R.id.editTextUserPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this, PostActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Please Sign In", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPassword.getText().toString().trim();
                if(!(email.equals("") || password.equals(""))){
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Logging You In");
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this, "Unable to Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                    dialog.setTitle("Wait!");
                    dialog.setMessage("Fill All Fields");
                    dialog.show();
                }
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuth!=null)
            mAuth.removeAuthStateListener(mAuthStateListener);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        progressDialog.dismiss();
    }
}