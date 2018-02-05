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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.rfp.dvp.mydevices.utils.ConectionUtils;

public class UserRegistryActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private EditText password;
    private EditText userFirstName;
    private EditText userSecondName;

    private TextView login;

    private Button registryButton;

    private FirebaseAuth mAuth;

    private AlertDialog.Builder alert;
    private AlertDialog alt;
    private boolean isAlertCreate;

    private Context mContext;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registry);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        mContext = this;
        activity = (Activity) mContext;


        mAuth = FirebaseAuth.getInstance();

        init();

    }

    private void init(){

        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        userFirstName = (EditText) findViewById(R.id.input_first_name);
        userSecondName = (EditText) findViewById(R.id.input_second_name);

        login = (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(this);


        registryButton = (Button) findViewById(R.id.btn_signup);
        registryButton.setOnClickListener(this);

    }

    private void initFirebase() {
        createAlertDialog();

        if (!TextUtils.isEmpty(email.getText().toString().trim())
             && !TextUtils.isEmpty(password.getText().toString().trim())
             && !TextUtils.isEmpty(userFirstName.getText().toString().trim())
             && !TextUtils.isEmpty(userSecondName.getText().toString().trim())) {

            mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("create", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI();
                                dismissProgressDialog();
                                Toast.makeText(UserRegistryActivity.this, "Usuário cadastrado com sucesso!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("create2", "createUserWithEmail:failure", task.getException());
                                dismissProgressDialog();
                                Toast.makeText(UserRegistryActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }


                    });

        }else{
            dismissProgressDialog();
            Toast.makeText(UserRegistryActivity.this, "Todos os campos devem ser preenchidos!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(userFirstName.getText().toString().trim()+" "+userSecondName.getText().toString().trim())
                .build();

        mAuth.getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("name", "User profile updated.");
                        }
                    }
                });
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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_signup:
                ConectionUtils.hideSoftKeyboard(activity);
                initFirebase();
                break;
            case R.id.link_login:
                callLoginActivity();
                break;
        }

    }

    public void callLoginActivity(){
        Intent it = new Intent(this, LoginActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }

}
