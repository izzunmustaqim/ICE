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
 * Created by user on 25/9/2015.
 */
public class TableManipulationMedicines extends AppCompatActivity {

    EditText etMedicines;

    Button btnDML;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_add_update);
        getAllWidgets();
        bindWidgetsWithEvent();
        checkForRequest();
    }

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        if (request.equals(Constants.UPDATE)) {
            btnDML.setText(Constants.UPDATE);
            etMedicines.setText(getIntent().getExtras().get(Constants.MEDICINES).toString());
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
        etMedicines = (EditText) findViewById(R.id.etMedicines);
        //etLastname = (EditText) findViewById(R.id.etLastname);

        btnDML = (Button) findViewById(R.id.btnDML_M);
    }

    private void onButtonClick() {
        //if (etAllergies.getText().toString().equals("") || etLastname.getText().toString().equals("")) {
        if (etMedicines.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Add a Fields", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.MEDICINES, etMedicines.getText().toString());
            //intent.putExtra(Constants.LAST_NAME, etLastname.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
