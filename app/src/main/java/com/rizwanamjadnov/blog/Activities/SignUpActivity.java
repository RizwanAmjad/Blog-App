package com.rizwanamjadnov.blog.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rizwanamjadnov.blog.R;

public class SignUpActivity extends AppCompatActivity {
    private Button closeButton;


    private void initializeItems(){
        closeButton = (Button) findViewById(R.id.closeButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initializeItems();
    }
}