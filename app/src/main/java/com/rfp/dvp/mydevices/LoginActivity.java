package com.rfp.dvp.mydevices;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rfp.dvp.mydevices.commons.Firebase;
import com.rfp.dvp.mydevices.utils.ConectionUtils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    private static final String TAG = "loginTest";

    public EditText mEmail;
    public EditText mPassword;
    public Button mLogin;
    public TextView mRegistry;

    private AlertDialog.Builder alert;
    private AlertDialog alt;
    private boolean isAlertCreate;

    private Context mContext;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mContext = this;
        activity = (Activity) mContext;

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
        mEmail = (EditText) findViewById(R.id.input_email);
        mPassword = (EditText) findViewById(R.id.input_password);
        mLogin = (Button) findViewById(R.id.btn_login);
        mRegistry = (TextView) findViewById(R.id.link_signup);
        mLogin.setOnClickListener(this);
        mRegistry.setOnClickListener(this);

    }

    private void signIn() {
        createAlertDialog();
        if (!TextUtils.isEmpty(mEmail.getText().toString().trim())
                && !TextUtils.isEmpty(mPassword.getText().toString().trim())) {

            mAuth.signInWithEmailAndPassword(mEmail.getText().toString().trim(), mPassword.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                dismissProgressDialog();
                                Log.w(TAG, "signInWithEmail:failed", task.getException());
                                Toast.makeText(LoginActivity.this, R.string.auth_failed, Toast.LENGTH_SHORT).show();
                            } else {
                                checkUsername();
                            }
                            dismissProgressDialog();
                        }

                    });
        }else{
            dismissProgressDialog();
        }
    }

    private void callListDevicesActivity() {
        dismissProgressDialog();
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("teste");

        myRef.setValue("kiokjhj");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/


        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("Rodrigo Felippo").build();

        mAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");
                        }
                    }
                });*/


        Firebase.initFirebase();
        Intent it = new Intent(this, DeviceListActivity.class);
        startActivity(it);
    }

    private void callUserRegistryActivity() {
        Intent it = new Intent(this, UserRegistryActivity.class);
        startActivity(it);
    }

    public void checkUsername() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url

            callListDevicesActivity();

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                ConectionUtils.hideSoftKeyboard(activity);
                signIn();
                break;
            case R.id.link_signup:
                ConectionUtils.hideSoftKeyboard(activity);
                callUserRegistryActivity();
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        dismissProgressDialog();
    }

    private void createAlertDialog() {
        if (!isAlertCreate) {
            isAlertCreate = true;
            alert = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.custom_alertdialog_layout, null);
            alert.setView(dialogView);


            alert.setCancelable(false);
            alt = alert.create();
            alt.show();
        }
    }

    private void dismissProgressDialog() {
        if (alt != null && alt.isShowing()) {
            alt.dismiss();
            isAlertCreate = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case 1:
                    callListDevicesActivity();
                    break;
            }
        }


    }
}
