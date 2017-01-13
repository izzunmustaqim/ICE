package org.uitm.ice;

import org.uitm.ice.apps.Shout;
import org.uitm.ice.objects.ContactsModel;
//import org.uitm.ice.apps.Wipe;

//import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
//import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class InTheClear extends AppCompatActivity implements OnClickListener {
    boolean isGPSEnabled = false;
    SharedPreferences _sp;
    LinearLayout launchPanic;
    GridView launchGrid;
    SQLiteHelper sQLiteHelper;
    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    final Context context = this;
    final Context context2 = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        /*w.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); */

        setContentView(R.layout.main);
        /*
        _sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(_sp.getBoolean("IsVirginUser", true)) {
        	launchWizard();
      	} */

        sQLiteHelper = new SQLiteHelper(InTheClear.this);
        launchPanic = (LinearLayout) findViewById(R.id.launchPanic);
        launchPanic.setOnClickListener(this);

        launchGrid = (GridView) findViewById(R.id.launchGrid);
        launchGrid.setAdapter(new ImageAdapter(this));
        launchGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0)
                    launchProfile();
                else if (position == 1)
                    launchContact();
                else if (position == 2)
                    launchHome();
                else if (position == 3)
                    launchMaps();
                else if (position == 4)
                    launchService();
                else if (position == 5)
                    launchPreferences();

            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );

    }

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
			/*Toast.makeText(InTheClear.this,
					"Provider disabled by the user. GPS turned off",
					Toast.LENGTH_LONG).show(); */
        }

        public void onProviderEnabled(String s) {
			/* Toast.makeText(InTheClear.this,
					"Provider enabled by the user. GPS turned on",
					Toast.LENGTH_LONG).show(); */
        }

    }

    private void launchProfile() {
        Intent i = new Intent(this, Profile_edit.class);
        //Intent i = new Intent(this,MainProfile.class);
        startActivity(i);
    }


    private void launchContact() {
        Intent i = new Intent(this, Contacts.class);
        startActivity(i);
    }

    private void launchShout() {
        Intent i = new Intent(this, Shout.class);
        startActivity(i);
    }

    private void launchSMS() {
        Intent i = new Intent(this, Panic.class);
        startActivity(i);
    }

    private void launchService() {
        Intent i = new Intent(this, Service.class);
        startActivity(i);
    }

    private void launchPanic() {
        Intent i = new Intent(this, Panic.class);
        startActivity(i);
    }

    private void launchPreferences() {
        Intent i = new Intent(this, Setting.class);
        startActivity(i);
    }

    private void launchHome() {
        Intent i = new Intent(this, Record.class);
        startActivity(i);
    }

    private void launchMaps() {
        //Intent i = new Intent(this,MapTab.class);
        Intent i = new Intent(this, NearbyPlace.class);
        startActivity(i);
    }


    @Override
    public void onClick(View v) {
        if (v == launchPanic) {
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {

                ArrayList<ContactsModel> contacts = sQLiteHelper.getAllRecordsC();
                SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String str1 = localSharedPreferences.getString("textMessage", "");
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                if (location != null) {
                    String message = String.format(
                            "Current Location \n Longitude: %1$s \n Latitude: %2$s",
                            location.getLongitude(), location.getLatitude()
                    );
                }

                //message+="https://www.google.co.id/maps/@"+latitude+","+longitude http://maps.google.com/?q=;
                //String phoneNo = textPhoneNo.getText().toString();
                //String sms = textSMS.getText().toString() + " i'm at https://maps.google.com/?q=" + latitude + "," + longitude;
                String sms = str1 + " i'm at https://maps.google.com/?q=" + latitude + "," + longitude;

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
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "SMS faild, please try again later!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context2);
                    // set title
                    alertDialogBuilder.setTitle("No Contact!");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Cannot send alert message. No phone numbers found")
                            .setCancelable(false)
                            .setNegativeButton("Okay",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int id) {
                                            // if this button is clicked, just close
                                            // the dialog box and do nothing
                                            dialog.cancel();
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                    alertDialog.show();
                }
            } else {
                //showSettingsAlert();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

                // Setting Dialog Title
                alertDialog.setTitle("GPS disabled");

                // Setting Dialog Message
                alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

                // On pressing Settings button
                alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                });

                // on pressing cancel button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        }
    }

    public class ImageAdapter extends BaseAdapter {
        Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View MyView = convertView;

            if (convertView == null) {
                //Inflate the layout
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.grid_item, null);

                //Add image & text
                TextView tv = (TextView) MyView.findViewById(R.id.grid_item_text);
                ImageView iv = (ImageView) MyView.findViewById(R.id.grid_item_image);
                MyView.setId(position);

                switch (position) {
                    case 0:
                        tv.setText("Personal Information");
                        iv.setImageResource(R.drawable.personal);
                        break;

                    case 1:
                        tv.setText("Emergency Contacts");
                        iv.setImageResource(R.drawable.persons_168);
                        break;
                    case 2:
                        tv.setText("Health Record");
                        iv.setImageResource(R.drawable.health);
                        break;
                    case 3:
                        tv.setText("Local Council");
                        iv.setImageResource(R.drawable.navigation);
                        break;
                    case 4:
                        tv.setText("Emergency Service");
                        iv.setImageResource(R.drawable.phone);
                        break;
                    case 5:
                        tv.setText("Settings");
                        iv.setImageResource(R.drawable.settings);
                        break;

                }
            }
            return MyView;
        }
		/*
		public void showSettingsAlert(){
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

			// Setting Dialog Title
			alertDialog.setTitle("GPS is disabled");

			// Setting Dialog Message
			alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

			// On pressing Settings button
			alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int which) {
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					mContext.startActivity(intent);
				}
			});

			// on pressing cancel button
			alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});

			// Showing Alert Message
			alertDialog.show();
		} */

        @Override
        public Object getItem(int arg0) {
            // TODO auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO auto-generated method stub
            return 0;
        }
    }
}