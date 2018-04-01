package com.levegra.anthonius_1202150034_modul6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.levegra.anthonius_1202150034_modul6.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AuthActivity extends AppCompatActivity {

    private static final String TAG = AuthActivity.class.getSimpleName();

    TextInputLayout mEmail, mPassword;
    Button mSignIn, mSignUp;
    String email, password;

    FirebaseAuth mAuth;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(AuthActivity.this, MainActivity.class));
            finish();
        }

        mEmail = findViewById(R.id.email_login);
        mPassword = findViewById(R.id.pass_login);
        mSignIn = findViewById(R.id.btn_login);
        mSignUp = findViewById(R.id.btn_signup);

        databaseUser = FirebaseDatabase.getInstance().getReference(MainActivity.table_3);
        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getTextField();
                if (validateForm()){
                    login(email, password);
                }else {
                    toastMessage("Mohon isi email/password");
                }
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextField();
                if (validateForm()){
                    signUp(email, password);
                }else {
                    toastMessage("Mohon isi email/password");
                }
            }
        });
    }

    private void signUp(final String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        toastMessage("createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            toastMessage("Authentication failed." + task.getException());
                        } else {
                            String id = mAuth.getCurrentUser().getUid();
                            String[] username = email.split("@");
                            User user = new User(id, username[0], email);
                            databaseUser.child(id).setValue(user);
                            startActivity(new Intent(AuthActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    private void login(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(AuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            if (password.length() < 6) {
                                mPassword.setError(getString(R.string.minimum_password));
                            } else {
                                toastMessage(getString(R.string.auth_failed));
                            }
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private boolean validateForm(){
        if (email.isEmpty() || password.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void getTextField(){
        email = mEmail.getEditText().getText().toString();
        password = mPassword.getEditText().getText().toString();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
