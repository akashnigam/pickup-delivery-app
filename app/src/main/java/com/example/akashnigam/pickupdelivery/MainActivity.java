package com.example.akashnigam.pickupdelivery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private ArrayAdapter adapter;

    private ArrayList<String> arrayList;

    private List<ArrayList<String>> completeJobList;

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
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String completeJobListStr = prefs.getString("completeJobList", null);
        if(completeJobListStr != null) {
            completeJobList = decodeJsonString(completeJobListStr);
            updateArrayList(completeJobList);
        }
        adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, arrayList);
        list.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        String str = encodeJsonString(completeJobList);
        editor.putString("completeJobList",str);
        editor.commit();
    }

    private String encodeJsonString(List<ArrayList<String>> list) {
        JSONArray jsonarray = new JSONArray();
        ArrayList<String> arr;
        String str;
        for (int i = 0; i < list.size(); i++) {
            arr = list.get(i);
            jsonarray.put(arr);
        }
        return jsonarray.toString();
    }

    private void updateArrayList(List<ArrayList<String>> list) {
        arrayList = new ArrayList<String>();
        ArrayList<String> arr;
        String pickupLoc,deliveryLoc;
        for (int i = 0; i < list.size(); i++) {
            arr = list.get(i);
            pickupLoc = arr.get(0);
            deliveryLoc = arr.get(1);
            String concatLocations = "Pickup Location:" + pickupLoc + "\nDelivery Location:" + deliveryLoc;
            arrayList.add(concatLocations);
        }
    }

    private List<ArrayList<String>> decodeJsonString(String str) {
        List<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        try {
            JSONArray jsonarray = new JSONArray(str);
            ArrayList<String> arr;
            String str2;
            JSONArray jsonarray2;
            for (int i = 0; i < jsonarray.length(); i++) {
                str2 = (String) jsonarray.get(i);
                jsonarray2 = new JSONArray(str2);
                arr = new ArrayList<String>();
                arr.add(jsonarray2.getString(0));
                arr.add(jsonarray2.getString(1));
                list.add(arr);
            }
        } catch (JSONException e) {
        }
        return list;
    }

    public void startPickupDeliveryActivity(View view) {
        Intent intent = new Intent(this, AddPickupDeliveryActivity.class);
        startActivityForResult(intent,GET_LOCATIONS);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_LOCATIONS) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String pickupLoc = data.getStringExtra(PICKUP_LOC);
                String deliveryLoc = data.getStringExtra(DELIVERY_LOC);
                ArrayList<String> locArr = new ArrayList<String>();
                locArr.add(pickupLoc);
                locArr.add(deliveryLoc);
                completeJobList.add(locArr);
                String concatLocations = "Pickup Location:" + pickupLoc + "\nDelivery Location:" + deliveryLoc;
                arrayList.add(concatLocations);
                adapter.notifyDataSetChanged();
                Toast.makeText(this,"Submitted Pickup and Delivery location successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
