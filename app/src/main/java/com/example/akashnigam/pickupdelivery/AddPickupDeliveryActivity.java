package com.example.akashnigam.pickupdelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
//import com.google.android.gms.location.places.

public class AddPickupDeliveryActivity extends AppCompatActivity {

    static final int PICKUP_PLACE_PICKER_REQUEST = 1;

    static final int DELIVERY_PLACE_PICKER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pickup_delivery);
    }

    public void submitLocations(View view) {
        TextView pickupText = (TextView) findViewById(R.id.pickupLocation);
        TextView deliveryText = (TextView) findViewById(R.id.deliveryLocation);
        String pickupLoc = pickupText.getText().toString();
        String deliveryLoc = deliveryText.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.PICKUP_LOC,pickupLoc);
        returnIntent.putExtra(MainActivity.DELIVERY_LOC,deliveryLoc);
        setResult(RESULT_OK,returnIntent);
        finish();
    }

    public void startPickupSelectLocationActivity(View view) {
        PlacePicker.IntentBuilder builder =  new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PICKUP_PLACE_PICKER_REQUEST);
        } catch (Exception e) {
        }
    }

    public void startDeliverySelectLocationActivity(View view) {
        PlacePicker.IntentBuilder builder =  new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), DELIVERY_PLACE_PICKER_REQUEST);
        } catch (Exception e) {
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKUP_PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                TextView pickupText = (TextView) findViewById(R.id.pickupLocation);
                pickupText.setText(toastMsg);
            }
        } else if (requestCode == DELIVERY_PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                TextView deliveryText = (TextView) findViewById(R.id.deliveryLocation);
                deliveryText.setText(toastMsg);
            }
        }
    }
}
