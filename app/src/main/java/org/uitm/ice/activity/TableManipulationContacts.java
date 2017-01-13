package org.uitm.ice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.provider.ContactsContract;
import android.database.Cursor;
import android.util.Log;
import android.net.Uri;

import org.uitm.ice.Constants;
import org.uitm.ice.R;


/**
 * Created by Izzun Mustaqim on 25/9/2015.
 */
public class TableManipulationContacts extends AppCompatActivity  {

    EditText etContacts,etPhone;
    private static final int PICK_CONTACT = 2015;

    //private TextView textView1;
    //private TextView textView2;

    Button btnDML,btnPick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_add_update);
        getAllWidgets();
        bindWidgetsWithEvent();
        checkForRequest();
        PickContacts();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etContacts.setText(getIntent().getExtras().get(Constants.CONTACTS).toString());
            etPhone.setText(getIntent().getExtras().get(Constants.PHONE).toString());
        } else {
            btnDML.setText(Constants.INSERT);
        }
    }

        private void bindWidgetsWithEvent() {
        btnDML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick();
            }
        });
    }

    public  void PickContacts() {
        btnPick.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(i, PICK_CONTACT);
                    }
                }
        );
    }

    /*
    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnDML :
                onButtonClick();
                break;
            case R.id.btnPick :
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
                break;
        }
    }*/

    private void getAllWidgets() {
        etContacts = (EditText) findViewById(R.id.etContacts);
        etPhone = (EditText) findViewById(R.id.etPhone);

        btnDML = (Button) findViewById(R.id.btnDML_C);
        //btnDML.setOnClickListener(this);
        btnPick = (Button) findViewById (R.id.btnPick);
        //btnPick.setOnClickListener(this);

    }

    private void onButtonClick() {
        //if (etAllergies.getText().toString().equals("") || etLastname.getText().toString().equals("")) {
        if (etContacts.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add a Fields", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.CONTACTS, etContacts.getText().toString());
            intent.putExtra(Constants.PHONE, etPhone.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

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
    }

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

            etContacts.setText(name);
            etPhone.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
