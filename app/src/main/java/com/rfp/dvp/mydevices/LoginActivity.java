package com.rfp.dvp.mydevices;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "loginTest";

    public EditText mEmail;
    public EditText mPassword;
    public Button mLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initFirebase();
        init();
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void init() {
        mEmail = (EditText) findViewById(R.id.login_email_edit_text);
        mPassword = (EditText) findViewById(R.id.login_password_edit_text);
        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(this);
        callListDevicesActivity();
    }

    private void signIn() {
        if (!TextUtils.isEmpty(mEmail.getText().toString().trim())
                && !TextUtils.isEmpty(mPassword.getText().toString().trim())) {


            mAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(), mPassword.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            } else {
                                callListDevicesActivity();
                            }
                        }
                    });
        }
    }

    private void callListDevicesActivity() {
        Intent it = new Intent(this, DeviceListActivity.class);
        startActivity(it);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                signIn();
        }
    }
}
