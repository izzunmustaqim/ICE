package org.uitm.ice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.uitm.ice.activity.TableManipulationActivity;
import org.uitm.ice.activity.TableManipulationContacts;
//import org.uitm.ice.objects.ContactsModel;
import org.uitm.ice.objects.ContactsModel;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Izzun Mustaqim on 22/9/2015.
 */

public class Contacts extends AppCompatActivity {

    Button btnAddNewRecord;
    SQLiteHelper sQLiteHelper;
    private Button callBtn;
    EditText number;
    android.widget.LinearLayout parentLayout;
    LinearLayout layoutDisplayPeople;

    TextView tvNoRecordsFound;
    private String rowID = null;
    public final int PICK_CONTACT = 2015;
    private TextView textView1;
    private TextView textView2;

    //private static final int RESULT_PICK_CONTACT = 85500;

    private ArrayList<HashMap<String, String>> tableData = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        //w.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        w.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.contacts);

        //textView1 = (TextView) findViewById(R.id.textView1);
        //textView2 = (TextView) findViewById(R.id.textView2);
        /*
        (findViewById(R.id.btnAddNewRecordC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                //startActivityForResult(i, PICK_CONTACT);
                //onAddRecord(holder.contacts.toString(),holder.phone.toString());
            }
        }); */


        getAllWidgets();
        sQLiteHelper = new SQLiteHelper(Contacts.this);
        bindWidgetsWithEvent();
        displayAllRecords();

        // add PhoneStateListener for monitoring
        MyPhoneListener phoneListener = new MyPhoneListener();
        TelephonyManager telephonyManager =
                (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        // receive notifications of telephony state changes
        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }


    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //contactPicked(data);
            String name = data.getStringExtra(Constants.CONTACTS);
            String phone = data.getStringExtra(Constants.PHONE);
            //int type = data.getIntExtra(Constants.TYPE, 0);
            //String lastname = data.getStringExtra(Constants.LAST_NAME);

           ContactsModel c = new ContactsModel();
            c.setName(name);
            c.setPhone(phone);
            //Contact.setLastName(lastname);

            if (requestCode == Constants.ADD_RECORD) {
                sQLiteHelper.insertRecordC(c);
            } else if (requestCode == Constants.UPDATE_RECORD) {
                c.setId(rowID);
                sQLiteHelper.updateRecordC(c);
            }
            displayAllRecords();
        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            (new normalizePhoneNumberTask()).execute(cursor.getString(column));
        }
    } */
    /*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_CONTACT:
                    contactPicked(data);
                    break;
            }

        } else {
            Log.e("MainActivity", "Failed to pick Contact");
        }
    } */
    /*
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null ;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            int  phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int  nameIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

            phoneNo = cursor.getString(phoneIndex);
            name = cursor.getString(nameIndex);

            textView1.setText(name);
            textView2.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    } */


    private void getAllWidgets() {
        btnAddNewRecord = (Button) findViewById(R.id.btnAddNewRecordC);

        parentLayout = (LinearLayout) findViewById(R.id.parentLayoutC);
        layoutDisplayPeople = (LinearLayout) findViewById(R.id.layoutDisplayContacts);

        tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFoundC);
        //callBtn = (Button) findViewById(R.id.call);
    }

    private void bindWidgetsWithEvent() {
        btnAddNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddRecord();
            }
        });
    }

    private void onAddRecord() {
        //Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        Intent intent = new Intent(Contacts.this, TableManipulationContacts.class);
        //intent.putExtra(Constants.CONTACTS, contacts);
        //intent.putExtra(Constants.PHONE, phone);
        intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
        startActivityForResult(intent, Constants.ADD_RECORD);
    }

    private void onUpdateRecord(String firstname, String phone) {
        Intent intent = new Intent(Contacts.this, TableManipulationContacts.class);
        intent.putExtra(Constants.CONTACTS, firstname);
        intent.putExtra(Constants.PHONE, phone);
        intent.putExtra(Constants.DML_TYPE, Constants.UPDATE);
        startActivityForResult(intent, Constants.UPDATE_RECORD);
    }

    /*
    private void callNo (){

        try {
            // set the data
            String uri = "tel:"+number.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

            startActivity(callIntent);

        }catch(Exception e) {
            Toast.makeText(getApplicationContext(),"Your call has failed...",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    } */

    private void displayAllRecords() {

        com.rey.material.widget.LinearLayout inflateParentView;
        parentLayout.removeAllViews();

        ArrayList<ContactsModel> contacts = sQLiteHelper.getAllRecordsC();

        if (contacts.size() > 0) {
            tvNoRecordsFound.setVisibility(View.GONE);
            ContactsModel contactsModel;
            for (int i = 0; i < contacts.size(); i++) {

                contactsModel = contacts.get(i);

                final Holder holder = new Holder();
                final View view = LayoutInflater.from(this).inflate(R.layout.inflate_record, null);
                inflateParentView = (com.rey.material.widget.LinearLayout) view.findViewById(R.id.inflateParentView);
                holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);


                view.setTag(contactsModel.getId());
                holder.contacts = contactsModel.getName();
                holder.phone = contactsModel.getPhone();
                //holder.lastname = ContactsModel.getLastName();
                String personName = holder.contacts + " " + holder.phone;
                //String personName = holder.contacts;
                holder.tvFullName.setText(personName);

                final CharSequence[] items = {Constants.UPDATE, Constants.DELETE , Constants.CALL};
                inflateParentView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Contacts.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {

                                    rowID = view.getTag().toString();
                                    //onUpdateRecord(holder.firstname, holder.lastname.toString());
                                    onUpdateRecord(holder.contacts.toString(),holder.phone.toString());
                                }
                                //else
                                else if (which == 1)
                                {
                                    AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Contacts.this);
                                    deleteDialogOk.setTitle("Delete Contact?");
                                    deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //sQLiteHelper.deleteRecord(view.getTag().toString());
                                                    ContactsModel Contact = new ContactsModel();
                                                    Contact.setId(view.getTag().toString());
                                                    sQLiteHelper.deleteRecordC(Contact);
                                                    displayAllRecords();
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

                                else {

                                    //ContactsModel Contact = new ContactsModel();
                                    //Contact.setId(view.getTag().toString());
                                    /*
                                    //number = (EditText) sQLiteHelper.contactNo(Contact);
                                    //ArrayList<ContactsModel> contacts = sQLiteHelper.contactNo(Contact.getPhone);
                                    //number = contacts.get(0).getPhone();
                                    Cursor number = sQLiteHelper.contactNo(Contact);
                                    Toast.makeText(Contacts.this, "on call...",
                                            Toast.LENGTH_LONG).show(); */
                                    ArrayList<ContactsModel> contacts = sQLiteHelper.getAllRecordsC();

                                   // if (contacts.size() > 0) {
                                        ContactsModel contactsModel;
                                        //contactsModel.setId(view.getTag().toString());
                                        //String uri = null;

                                        for (int i = 0; i < contacts.size(); i++) {

                                            //if (contacts.get(i).getPhone().equalsIgnoreCase("01112995348")) {
                                            //if (contacts.get(i).getId() == view.getTag()) {
                                            if (contacts.get(i).getId().equalsIgnoreCase(view.getTag().toString())) {

                                                contactsModel = contacts.get(i);
                                                //String c = contactsModel.getName();
                                                String uri = "tel:" + contactsModel.getPhone();

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


                                    //ContactsModel Contact = new ContactsModel();
                                    //String uri = "tel:01112734058";
                                    /*
                                    try {

                                        ContactsModel Contact = new ContactsModel();
                                        Contact.setId(view.getTag().toString());
                                        number = (EditText) sQLiteHelper.contactNo(Contact);


                                        // set the data
                                        //String uri = "tel:"+number.getText().toString();
                                        //String uri = "tel:"+number;
                                        //String uri = "tel:01112734058";
                                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));

                                        startActivity(callIntent);

                                    }catch(Exception e) {
                                        Toast.makeText(getApplicationContext(),"Your call has failed...",
                                                Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    } */

                                    //callNo();
                                    /*
                                    Intent call = new Intent(Intent.ACTION_CALL);
                                    try {
                                        call.setData(Uri.parse("tel:9951037343"));
                                        startActivity(call);
                                    } catch (Exception e) {
                                        Log.e("Calling", "" + e.getMessage());
                                    }
                                    startActivity(call); */

                                }
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                    }
                });
                parentLayout.addView(view);
            }
        } else {
            tvNoRecordsFound.setVisibility(View.VISIBLE);
        }
    }

    private class Holder {
        TextView tvFullName;
        String contacts;
        String phone;

    }

    private class MyPhoneListener extends PhoneStateListener {

        private boolean onCall = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    // phone ringing...
                    Toast.makeText(Contacts.this, incomingNumber + " calls you",
                            Toast.LENGTH_LONG).show();
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    // one call exists that is dialing, active, or on hold
                    Toast.makeText(Contacts.this, "on call...",
                            Toast.LENGTH_LONG).show();
                    //because user answers the incoming call
                    onCall = true;
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    // in initialization of the class and at the end of phone call

                    // detect flag from CALL_STATE_OFFHOOK
                    if (onCall == true) {
                        Toast.makeText(Contacts.this, "restart app after call",
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

