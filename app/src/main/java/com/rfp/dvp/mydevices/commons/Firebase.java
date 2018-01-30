package com.rfp.dvp.mydevices.commons;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rfp.dvp.mydevices.objects.Device;
import com.rfp.dvp.mydevices.objects.User;
import com.rfp.dvp.mydevices.objects.Uso;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dvpires on 29/01/2018.
 */

public class Firebase {

    private static DatabaseReference mDatabase;
    private static User mUser;


    public static void initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());

    }

    public static DatabaseReference getDatabase() {
        return mDatabase;
    }

    private static void writeNewUser(String userId, String name, String email) {
        mUser = new User(email, name);
        mDatabase.child(DeviceExtras.TAG_USERS).child(userId).setValue(mUser);
    }


    public static User getUser() {
        return mUser;
    }
}
