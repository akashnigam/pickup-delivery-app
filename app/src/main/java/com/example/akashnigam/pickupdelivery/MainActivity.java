package com.example.akashnigam.pickupdelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter adapter;

    private ArrayList<String> arrayList;

    private ArrayList<ArrayList<String>> completeJobList;

    static final int GET_LOCATIONS = 1;

    static final String PICKUP_LOC = "pickupLoc";

    static final String DELIVERY_LOC = "deliveryLoc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = findViewById(R.id.listView);
        //ScrollView list = findViewById(R.id.listView);
        completeJobList = new ArrayList<ArrayList<String>>();
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayList);
        list.setAdapter(adapter);
    }

    public void startPickupDeliveryActivity(View view) {
        Intent intent = new Intent(this, AddPickupDeliveryActivity.class);
        startActivityForResult(intent,GET_LOCATIONS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(this,"Already Logged In"+Integer.toString(requestCode),Toast.LENGTH_SHORT).show();
        // Check which request we're responding to
        if (requestCode == GET_LOCATIONS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String pickupLoc = data.getStringExtra("pickupLoc");
                String deliveryLoc = data.getStringExtra("deliveryLoc");
                ArrayList<String> locArr = new ArrayList<String>();
                locArr.add(pickupLoc);
                locArr.add(deliveryLoc);
                completeJobList.add(locArr);
                String concatLocations = "Pickup Location:" + pickupLoc + "\nDelivery Location:" + deliveryLoc;
                arrayList.add(concatLocations);
                Toast.makeText(this,"Already Logged In",Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        }
    }

    public void addInList(View view) {
        EditText editTxt = (EditText) findViewById(R.id.editText3);
        arrayList.add(editTxt.getText().toString());
        adapter.notifyDataSetChanged();
    }

}
