package com.rfp.dvp.mydevices;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PutNameActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mPutName;
    private Button mContinue;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_name);
        mContext = this;
        init();
    }

    public void init() {
        mPutName = (EditText) findViewById(R.id.put_name_edit_text);
        mContinue = (Button) findViewById(R.id.put_name_button);
        mContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.put_name_button:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (mPutName.getText() == null) {
                    Toast.makeText(PutNameActivity.this, R.string.nothing_digited, Toast.LENGTH_SHORT).show();
                } else {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mPutName.getText().toString())
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                    }
                                }
                            });
                    returnResult();

                }
        }
    }

    public void returnResult() {
        Intent it = new Intent(mContext, LoginActivity.class);
        setResult(RESULT_OK, it);
        finish();
    }
}
