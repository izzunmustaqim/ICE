package org.uitm.ice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.uitm.ice.activity.TableManipulationMedicines;
import org.uitm.ice.objects.MedicinesModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Izzun Mustaqim on 22/9/2015.
 */
public class Notices extends AppCompatActivity {

    Button btnAddNewRecord;
    SQLiteHelper sQLiteHelper;

    android.widget.LinearLayout parentLayout;
    LinearLayout layoutDisplayPeople;

    TextView tvNoRecordsFound;
    private String rowID = null;

    private ArrayList<HashMap<String, String>> tableData = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);

        setContentView(R.layout.notices);
        getAllWidgets();
        sQLiteHelper = new SQLiteHelper(Notices.this);
        bindWidgetsWithEvent();
        displayAllRecords();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String medicines = data.getStringExtra(Constants.MEDICINES);
            int type = data.getIntExtra(Constants.TYPE,3);
            //String lastname = data.getStringExtra(Constants.LAST_NAME);

            MedicinesModel m = new MedicinesModel();
            m.setMedicines(medicines);
            m.setType(type);
            //Contact.setLastName(lastname);

            if (requestCode == Constants.ADD_RECORD) {
                //sQLiteHelper.insertRecord(firstname, lastname);
                sQLiteHelper.insertRecordM(m);
            } else if (requestCode == Constants.UPDATE_RECORD) {
                m.setID(rowID);
                //sQLiteHelper.updateRecord(firstname, lastname, rowID);
                sQLiteHelper.updateRecordM(m);
            }
            displayAllRecords();
        }
    }


    private void getAllWidgets() {
        btnAddNewRecord = (Button) findViewById(R.id.btnAddNewRecordN);

        parentLayout = (LinearLayout) findViewById(R.id.parentLayoutN);
        layoutDisplayPeople = (LinearLayout) findViewById(R.id.layoutDisplayN);

        tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFoundN);
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
        Intent intent = new Intent(Notices.this, TableManipulationMedicines.class);
        intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
        startActivityForResult(intent, Constants.ADD_RECORD);
    }

    private void onUpdateRecord(String firstname) {
        Intent intent = new Intent(Notices.this, TableManipulationMedicines.class);
        intent.putExtra(Constants.MEDICINES, firstname);
        //intent.putExtra(Constants.LAST_NAME, lastname);
        intent.putExtra(Constants.DML_TYPE, Constants.UPDATE);
        startActivityForResult(intent, Constants.UPDATE_RECORD);
    }


    private void displayAllRecords() {

        com.rey.material.widget.LinearLayout inflateParentView;
        parentLayout.removeAllViews();

        ArrayList<MedicinesModel> contacts = sQLiteHelper.getAllRecordsN();

        if (contacts.size() > 0) {
            tvNoRecordsFound.setVisibility(View.GONE);
            MedicinesModel medicinesModel;
            //this.result = sQLiteHelper.rawQuery("SELECT * FROM healthrecord WHERE type=0");

            for (int i = 0; i < contacts.size(); i++) {

                medicinesModel = contacts.get(i);

                final Holder holder = new Holder();
                final View view = LayoutInflater.from(this).inflate(R.layout.inflate_record, null);
                inflateParentView = (com.rey.material.widget.LinearLayout) view.findViewById(R.id.inflateParentView);
                holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);


                view.setTag(medicinesModel.getID());
                holder.medicines = medicinesModel.getMedicines();
                //holder.lastname = ContactsModel.getLastName();
                //String personName = holder.firstname + " " + holder.lastname;
                String personName = holder.medicines;
                holder.tvFullName.setText(personName);

                final CharSequence[] items = {Constants.UPDATE, Constants.DELETE};
                inflateParentView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Notices.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {

                                    rowID = view.getTag().toString();
                                    //onUpdateRecord(holder.firstname, holder.lastname.toString());
                                    onUpdateRecord(holder.medicines.toString());
                                } else {
                                    AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(Notices.this);
                                    deleteDialogOk.setTitle("Delete Notices?");
                                    deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //sQLiteHelper.deleteRecord(view.getTag().toString());
                                                    MedicinesModel contact = new MedicinesModel();
                                                    contact.setID(view.getTag().toString());
                                                    sQLiteHelper.deleteRecordM(contact);
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
        String medicines;
        //String firstname;
        //String lastname;
    }

}

