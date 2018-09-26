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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailidEdit, passwordEdit;
    private Button loginBtn;
    private ProgressBar loginprogressBar;
    private FirebaseAuth firebaseAuth;
    private TextView userregisterView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailidEdit = findViewById(R.id.emailidEdit);
        userregisterView = findViewById(R.id.userregisterView);
        passwordEdit = findViewById(R.id.passwordEdit);
        loginBtn = findViewById(R.id.loginBtn);
        loginprogressBar = findViewById(R.id.loginprogressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(this);
        userregisterView.setOnClickListener(this);

        // if user already logged-in then do this
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() !=null){
            // instance profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }


    }

    private void userLogin(){
        String email = emailidEdit.getText().toString().trim();
        final String password = passwordEdit.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            emailidEdit.setError("Email is empty...");
            return;
        }

        if(TextUtils.isEmpty(password)){
            passwordEdit.setError("Password is empty...");
            return;
        }
        loginBtn.requestFocus();
        loginBtn.setVisibility(View.INVISIBLE);
        loginprogressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if( task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login Successfull...", Toast.LENGTH_SHORT).show();
                            loginprogressBar.setVisibility(View.INVISIBLE);
                            loginBtn.setVisibility(View.VISIBLE);
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Unsuccessfull...", Toast.LENGTH_SHORT).show();
                            loginprogressBar.setVisibility(View.INVISIBLE);
                            loginBtn.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if( view == loginBtn){
            userLogin();
        }

        if( view == userregisterView ){
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }
}
