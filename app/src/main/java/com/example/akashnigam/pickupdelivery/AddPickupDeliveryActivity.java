package com.example.akashnigam.pickupdelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPickupDeliveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pickup_delivery);
    }

    public void submitLocations(View view) {
        EditText pickupText = (EditText) findViewById(R.id.pickupLocation);
        EditText deliveryText = (EditText) findViewById(R.id.deliveryLocation);
        String pickupLoc = pickupText.getText().toString();
        String deliveryLoc = deliveryText.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("pickupLoc",pickupLoc);
        returnIntent.putExtra("deliveryLoc",pickupLoc);
        setResult(RESULT_OK,returnIntent);
        finish();
    }
}
