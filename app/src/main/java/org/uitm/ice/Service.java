package org.uitm.ice;

//import org.uitm.ice.apps.Panic;
import org.uitm.ice.apps.Shout;
import org.uitm.ice.objects.ContactsModel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
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

public class Service extends AppCompatActivity implements OnClickListener  {
    SharedPreferences _sp;
    LinearLayout launchPanic;
    GridView launchGrid;
    TextView service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        /*
        _sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(_sp.getBoolean("IsVirginUser", true)) {
        	launchWizard();
      	} */

        //launchPanic = (LinearLayout) findViewById(R.id.launchPanic);
        //launchPanic.setOnClickListener(this);
        service = (TextView)findViewById(R.id.tajuk);
        service.setText(Constants.SERVICES);
        launchGrid = (GridView)findViewById(R.id.launchGrid2);
        launchGrid.setAdapter(new ImageAdapter(this));
        launchGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if(position == 0)
                    launchPolice();
                else if(position == 1)
                    launchDiseases();
                else if(position == 2)
                    launchMedicines();
                else if(position == 3)
                    launchNotices();
            }
        });

        // add PhoneStateListener for monitoring
        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    }

    private void launchMedicines() {
        AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Service.this);
        deleteDialogOk.setTitle("Call 991 ?");
        deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = "tel:123";

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                            startActivity(callIntent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }
        );
        deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteDialogOk.show();
    }


    private void launchDiseases() {
        AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Service.this);
        deleteDialogOk.setTitle("Call 994 ?");
        deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = "tel:123";

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                            startActivity(callIntent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }
        );
        deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteDialogOk.show();
    }

    private void launchPolice() {
//        Intent i = new Intent(this,Allergies.class);
//        startActivity(i);

        AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Service.this);
        deleteDialogOk.setTitle("Call 999 ?");
        deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = "tel:123";

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                            startActivity(callIntent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }
        );
        deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteDialogOk.show();
    }


	private void launchNotices() {
        AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Service.this);
        deleteDialogOk.setTitle("Call Plus ?");
        deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String uri = "tel:123";

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                            startActivity(callIntent);

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Your call has failed...",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }
        );
        deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        deleteDialogOk.show();
	}



    @Override
    public void onClick(View v) {
        //if(v == launchPanic)
        //	launchPanic();
    }

    public class ImageAdapter extends BaseAdapter
    {
        Context mContext;

        public ImageAdapter(Context c)
        {
            mContext = c;
        }

        @Override
        public int getCount()
        {
            return 4;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View MyView = convertView;

            if (convertView == null)
            {
                //Inflate the layout
                LayoutInflater li = getLayoutInflater();
                MyView = li.inflate(R.layout.grid_item, null);

                //Add image & text
                TextView tv = (TextView)MyView.findViewById(R.id.grid_item_text);
                ImageView iv = (ImageView)MyView.findViewById(R.id.grid_item_image);
                MyView.setId(position);

                switch(position){
                    case 0:
                        tv.setText("Police");
                        iv.setImageResource(R.drawable.police);
                        break;

                    case 1:
                        tv.setText("Firefighter");
                        iv.setImageResource(R.drawable.fireman);
                        break;
                    case 2:
                        tv.setText("Civil Defences");
                        iv.setImageResource(R.drawable.civil);
                        break;
                    case 3:
                        tv.setText("PLUS");
                        iv.setImageResource(R.drawable.plus);
                        break;
                }
            }
            return MyView;
        }

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

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(Service.this, incomingNumber + " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(Service.this, "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // in initialization of the class and at the end of phone call

                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        Toast.makeText(Service.this, "restart app after call",
                                Toast.LENGTH_LONG).show();

                        // restart our application
                        Intent restart = getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getBaseContext().getPackageName());
                        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(restart);

                        onCall = false;
                    }
                    break;
                default:
                    break;
            }

        }
    }

}