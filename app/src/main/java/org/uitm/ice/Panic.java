package org.uitm.ice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.uitm.ice.objects.ContactsModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class Panic extends AppCompatActivity {

    Button buttonSend;
    EditText textPhoneNo;
    EditText textSMS;
    SQLiteHelper sQLiteHelper;
    //ArrayList<ContactsModel> contacts ;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds

    protected LocationManager locationManager;

    protected Button retrieveLocationButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shout);
        sQLiteHelper = new SQLiteHelper(Panic.this);

        //retrieveLocationButton = (Button) findViewById(R.id.retrieve_location_button);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

        buttonSend = (Button) findViewById(R.id.shoutBtn);
        //textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
        //textSMS = (EditText) findViewById(R.id.editTextSMS);
        textPhoneNo = (EditText)findViewById(R.id.configuredFriendsText);
        textSMS = (EditText) findViewById(R.id.panicMessageText);

        buttonSend.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                ArrayList<ContactsModel> contacts = sQLiteHelper.getAllRecordsC();
                //contacts = sQLiteHelper.getAllRecordsC();

                //reloadContact();
                //SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                //String str1 = localSharedPreferences.getString("textMessage", "");

                //showCurrentLocation();

                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                
                //message+="https://www.google.co.id/maps/@"+latitude+","+longitude;

                if (location != null) {
                    String message = String.format(
                            "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                            location.getLongitude(), location.getLatitude()
                    );
                    //Toast.makeText(Panic.this, message,
                      //      Toast.LENGTH_LONG).show();
                }

                //message+="https://www.google.co.id/maps/@"+latitude+","+longitude http://maps.google.com/?q=;
                //String phoneNo = textPhoneNo.getText().toString();
                String sms = textSMS.getText().toString() + " i'm at https://maps.google.com/?q="+latitude+","+longitude;

                //sendSMS(sms);

                if (contacts.size() > 0) {
                    ContactsModel contactsModel;
                    for (int i = 0; i < contacts.size(); i++) {

                        contactsModel = contacts.get(i);
                        //String c = contactsModel.getName();
                        String phoneNo = contactsModel.getPhone();

                        try {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                            Toast.makeText(getApplicationContext(), "SMS Sent!",
                                    Toast.LENGTH_LONG).show();
                        }

                        catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS faild, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }
                /*
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } */

            }
        });

    }

    /*
    protected void showCurrentLocation() {

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Location myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        //message+="https://www.google.co.id/maps/@"+latitude+","+longitude;

        if (location != null) {
            String message = String.format(
                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            Toast.makeText(Panic.this, message,
                    Toast.LENGTH_LONG).show();
        }

    }*/

    private class MyLocationListener implements LocationListener {

        public void onLocationChanged(Location location) {
            String message = String.format(
                    "New Location \n Longitude: %1$s \n Latitude: %2$s",
                    location.getLongitude(), location.getLatitude()
            );
            //Toast.makeText(Panic.this, message, Toast.LENGTH_LONG).show();
        }

        public void onStatusChanged(String s, int i, Bundle b) {
            //Toast.makeText(Panic.this, "Provider status changed",
                    //Toast.LENGTH_LONG).show();
        }

        public void onProviderDisabled(String s) {
            Toast.makeText(Panic.this,
                    "Provider disabled by the user. GPS turned off",
                    Toast.LENGTH_LONG).show();
        }

        public void onProviderEnabled(String s) {
            Toast.makeText(Panic.this,
                    "Provider enabled by the user. GPS turned on",
                    Toast.LENGTH_LONG).show();
        }

    }

    /*
    private void sendAlert()
    {
        reloadContact();
        if (this.contacts.size() > 0)
        {
            if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("sendGps", false))
            {
                this.lManager = ((LocationManager)getSystemService("location"));
                this.lManager.requestLocationUpdates("gps", 1000L, 0.0F, this);
            }
            this.alertDialog2 = new AlertDialog.Builder(this).create();
            this.alertDialog2.setTitle("");
            this.sendMsg = getString(2131034138);
            this.alertDialog2.setMessage(this.sendMsg + "\n" + String.valueOf(this.n) + " s.");
            this.alertDialog2.setButton(getString(2131034120), new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                {
                    if (Urgence.this.timer != null) {
                        Urgence.this.timer.cancel();
                    }
                    Urgence.this.timer = null;
                    Urgence.this.n = Urgence.this.MAX_N;
                }
            });
            this.alertDialog2.show();
            if (this.timer != null)
            {
                this.timer.cancel();
                this.timer = null;
            }
            this.timer = new Timer();
            this.timer.schedule(new RemindTask(), 1000L, 1000L);
            return;
        }
        this.alertDialog2 = new AlertDialog.Builder(this).create();
        this.alertDialog2.setTitle("");
        this.sendMsg = getString(2131034142);
        this.alertDialog2.setMessage(this.sendMsg);
        this.alertDialog2.setButton(getString(2131034120), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                Message localMessage = new Message();
                localMessage.what = 258;
                Urgence.this.myViewUpdateHandler.sendMessage(localMessage);
            }
        });
        this.alertDialog2.show();
    }
    */
    /*
    public void sendSMS (String sms)
    {
        reloadContact();
        if (contacts.size() > 0) {
            ContactsModel contactsModel;
            for (int i = 0; i < contacts.size(); i++) {

                contactsModel = contacts.get(i);
                //String c = contactsModel.getName();
                String phoneNo = contactsModel.getPhone();

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "SMS Sent!",
                            Toast.LENGTH_LONG).show();
                }

                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again later!",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        }

    }

    public void reloadContact()
    {
        ArrayList<ContactsModel> contacts = sQLiteHelper.getAllRecordsC();
        //contacts = sQLiteHelper.getAllRecordsC();

        if (contacts.size() > 0) {
            ContactsModel contactsModel;
            for (int i = 0; i < contacts.size(); i++) {

                contactsModel = contacts.get(i);
                //String c = contactsModel.getName();
                String phoneNo = contactsModel.getPhone();

            }
        }
    } */


}