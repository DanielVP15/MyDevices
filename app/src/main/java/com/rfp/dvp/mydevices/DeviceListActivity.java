package com.rfp.dvp.mydevices;
        writeDevices(device);
        recyclerView.setAdapter(new DeviceAdapter(device, this));
    public void fireBaseCheck() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        writeNewUser(user.getUid(),user.getDisplayName(),user.getEmail());
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void writeDevices(List<Device> devices){
        for(Device device : devices){
            String deviceId = device.getId();
            device.setId(null);
            mDatabase.child("devices").child(deviceId).setValue(device);
        }

    }

        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.UserInfo;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.rfp.dvp.mydevices.devices.Device;
        import com.rfp.dvp.mydevices.devices.User;

        import java.util.ArrayList;
        import java.util.List;

public class DeviceListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layout;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        fireBaseCheck();

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        init();

    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        Device a5 = new Device("Galaxy A5","1",true,"Rodrigo Felippo");
        Device k4 = new Device("LG K4","2",true,"Rafael Brito");
        Device g1 = new Device("Motorola G1","3",false,"Daniel Pires");
        Device xa = new Device("Sony Xperia XA","4",true,"Gabriel Calazans");
        List<Device> device = new ArrayList<>();
        device.add(a5);
        device.add(k4);
        device.add(g1);
        device.add(xa);
        recyclerView.setAdapter(new DeviceAdapter(device,this));

        recyclerView.setHasFixedSize(true);
        layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
    }


}
