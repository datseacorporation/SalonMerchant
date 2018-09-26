package com.datseacorporation.salonmerchant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText remailidEdit, rpasswordEdit;
    private Button nextBtn;
    private ProgressBar registerprogressBar;
    private FirebaseAuth firebaseAuth;
    private TextView userloginView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        remailidEdit = findViewById(R.id.remailidEdit);
        rpasswordEdit = findViewById(R.id.rpasswordEdit);
        nextBtn = findViewById(R.id.nextBtn);
        userloginView = findViewById(R.id.userloginView);
        registerprogressBar = findViewById(R.id.registerprogressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        nextBtn.setOnClickListener(this);
        userloginView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if( view == nextBtn ){
            userRegister();
        }

        if( view == userloginView ){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }


    private void userRegister(){
        String email = remailidEdit.getText().toString().trim();
        String password = rpasswordEdit.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            remailidEdit.setError("Email is empty...");
            return;
        }

        if(TextUtils.isEmpty(password)){
            rpasswordEdit.setError("Password is empty...");
            return;
        }
        nextBtn.requestFocus();
        nextBtn.setVisibility(View.INVISIBLE);
        registerprogressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){

                            Toast.makeText(RegisterActivity.this, "Registration Successfull...", Toast.LENGTH_SHORT).show();
                            registerprogressBar.setVisibility(View.INVISIBLE);
                            nextBtn.setVisibility(View.VISIBLE);
                            startActivity(new Intent(getApplicationContext(), BusinessActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Unsuccessfull...", Toast.LENGTH_SHORT).show();
                            registerprogressBar.setVisibility(View.VISIBLE);
                            nextBtn.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

}
