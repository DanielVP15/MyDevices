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
    private static Uso mUsage;

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

    public static void startUse(Device device) {
        Date data = new Date();
        Uso usage = new Uso(mUser.getEmail(), device.getModel(), device.getId(), data.toString(), false);
        mDatabase.child(DeviceExtras.TAG_USAGES).child(data.toString()).setValue(usage);
    }

    public static void endUse(Device device) {
        mDatabase.child(DeviceExtras.TAG_USAGES).push();
        loadUsages(device);
    }

    private static void finishUse(Uso usage) {
        Date data = new Date();
        mUsage.setFim(data.toString());
        mUsage.isReturned();
        Map<String, Object> usageValue = mUsage.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/usos/" + mUsage.getInicio() + "/", usageValue);

        mDatabase.updateChildren(childUpdates);
        mUsage = null;
    }

    public static void loadUsages(Device device) {
        final Device mDevice = device;
        mDatabase.child(DeviceExtras.TAG_USAGES).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Uso usage = dataSnapshot.getValue(Uso.class);
                    if (!usage.getReturned() && usage.getDeviceId().equals(mDevice.getId())) {
                        mUsage = usage;
                        finishUse(usage);
                        Log.e("teste", "addU");
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.exists()) {
                    Uso usage = dataSnapshot.getValue(Uso.class);
                    if (!usage.getReturned() && usage.getDeviceId().equals(mDevice.getId())) {
                        mUsage = usage;
                        finishUse(usage);
                        Log.e("teste", "changedU");
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
