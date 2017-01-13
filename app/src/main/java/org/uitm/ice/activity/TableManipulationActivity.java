package org.uitm.ice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.uitm.ice.Constants;
import org.uitm.ice.R;

/**
 * Created by Izzun Mustaqim on 01-09-2015.
 */
public class TableManipulationActivity extends AppCompatActivity {

    EditText etAllergies;
    //EditText etFirstname;
    //EditText etLastname;

    Button btnDML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_manipulation);
        getAllWidgets();
        bindWidgetsWithEvent();
        checkForRequest();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etAllergies.setText(getIntent().getExtras().get(Constants.ALLERGIES).toString());
            //etLastname.setText(getIntent().getExtras().get(Constants.LAST_NAME).toString());
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

    private void getAllWidgets() {
        etAllergies = (EditText) findViewById(R.id.etAllergies);
        //etLastname = (EditText) findViewById(R.id.etLastname);

        btnDML = (Button) findViewById(R.id.btnDML);
    }

    private void onButtonClick() {
        //if (etAllergies.getText().toString().equals("") || etLastname.getText().toString().equals("")) {
        if (etAllergies.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add a Fields", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.ALLERGIES, etAllergies.getText().toString());
            //intent.putExtra(Constants.LAST_NAME, etLastname.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
