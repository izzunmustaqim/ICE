package org.uitm.ice;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import org.uitm.ice.activity.TableManipulationMedicines;
//import org.uitm.ice.objects.MedicinesModel;
import com.rey.material.widget.EditText;

import org.uitm.ice.objects.ProfileModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 18/11/2015.
 */
public class Profile_edit extends AppCompatActivity {
    Button btnDML_P;
    SQLiteHelper sQLiteHelper;
    //ProfileModel profileDetail;
    TextView tvFirstName,tvDOB,tvHeight,tvWeight,tvBlood,tvArea;
    ImageView ivContact;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String rowID = null;

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

        setContentView(R.layout.contact_detail_layout);
        getAllWidgets();
        sQLiteHelper = new SQLiteHelper(Profile_edit.this);
        bindWidgetsWithEvent();
        displayProfileDetail();
        //editContact();
        //profileDetail = getProfileDetail();
        //setProfileDetail();
        //displayAllRecords();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra(Constants.NAME);
            String dob = data.getStringExtra(Constants.DOB);
            String height = data.getStringExtra(Constants.HEIGHT);
            String weight = data.getStringExtra(Constants.WEIGHT);
            String blood = data.getStringExtra(Constants.BLOOD);
            String area = data.getStringExtra(Constants.AREA);
            String path = data.getStringExtra(Constants.PATH);

            ProfileModel p = new ProfileModel();
            //p.setMedicines(medicines);
            //p.setType(type);
            p.setName(name);
            p.setDob(dob);
            p.setHeight(height);
            p.setWeight(weight);
            p.setBlood(blood);
            p.setArea(area);
            p.setPath(path);

            if (requestCode == Constants.ADD_RECORD) {
                sQLiteHelper.insertRecordP(p);
            }

            else if (requestCode == Constants.UPDATE_RECORD) {
                //p.setID(rowID);
                //sQLiteHelper.updateRecord(firstname, lastname, rowID);
                p.setID("1");
                sQLiteHelper.updateRecordP(p);
            }
            /*
            else if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
                displayProfileDetail();
            }

            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            */
            displayProfileDetail();
            //displayAllRecords();

        }
    }


    private void getAllWidgets() {
        btnDML_P = (Button) findViewById(R.id.btnDML_P);
        tvFirstName = (TextView) findViewById(R.id.tvContactDetailName);
        tvDOB = (TextView) findViewById(R.id.tvContactDetailDOB);
        tvHeight = (TextView) findViewById(R.id.tvContactDetailHeight);
        tvWeight = (TextView) findViewById(R.id.tvcontactDetailWeight);
        tvBlood = (TextView) findViewById(R.id.tvcontactDetailBlood);
        tvArea = (TextView) findViewById(R.id.tvcontactDetailArea);
        ivContact = (ImageView) findViewById(R.id.ivContactImage);

        //parentLayout = (LinearLayout) findViewById(R.id.parentLayoutM);
        //layoutDisplayPeople = (LinearLayout) findViewById(R.id.layoutDisplayM);

        //tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFoundM);
    }

    public void editContact() {
        ivContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private void bindWidgetsWithEvent() {
        btnDML_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<ProfileModel> profile = sQLiteHelper.getProfileDetail();


                if (profile.size() > 0) {
                    ProfileModel profileModel;

                    for (int i = 0; i < profile.size(); i++) {

                        profileModel = profile.get(i);

                        final Holder holder = new Holder();
                        holder.name = profileModel.getName();
                        holder.dob = profileModel.getDOB();
                        holder.height = profileModel.getHeight();
                        holder.weight = profileModel.getWeight();
                        holder.blood = profileModel.getBlood();
                        holder.area = profileModel.getArea();
                        //holder.path = profileModel.getPath();

                        onUpdateRecord(holder.name, holder.dob, holder.height, holder.weight, holder.blood, holder.area);
                    }
                    //onUpdateRecord("Alia", "7-11-1993", "160", "40", "A", "Johor");
                }
                else
                    onAddRecord();
            }
        });
    }

    private void onAddRecord() {
        Intent intent = new Intent(Profile_edit.this, Profile.class);
        intent.putExtra(Constants.DML_TYPE, Constants.INSERT);
        startActivityForResult(intent, Constants.ADD_RECORD);
    }

    private void onUpdateRecord(String firstname, String dob, String height, String weight, String blood, String area) {
        Intent intent = new Intent(Profile_edit.this, Profile.class);
        intent.putExtra(Constants.NAME, firstname);
        intent.putExtra(Constants.DOB, dob);
        intent.putExtra(Constants.HEIGHT, height);
        intent.putExtra(Constants.WEIGHT, weight);
        intent.putExtra(Constants.BLOOD, blood);
        intent.putExtra(Constants.AREA, area);
        //intent.putExtra(Constants.PATH, path);
        intent.putExtra(Constants.DML_TYPE, Constants.UPDATE);
        startActivityForResult(intent, Constants.UPDATE_RECORD);
    }

    private void displayProfileDetail() {

        //TextView tv = (TextView) findViewById(R.id.tvContactDetailName);
        ArrayList<ProfileModel> profile = sQLiteHelper.getProfileDetail();

        if (profile.size() > 0) {
            ProfileModel profileModel;

            for (int i = 0; i < profile.size(); i++) {

                profileModel = profile.get(i);
                final Holder holder = new Holder();
                holder.name = profileModel.getName();
                holder.dob = profileModel.getDOB();
                holder.height = profileModel.getHeight();
                holder.weight = profileModel.getWeight();
                holder.blood = profileModel.getBlood();
                holder.area = profileModel.getArea();

                tvFirstName.setText(holder.name);
                tvDOB.setText(holder.dob);
                tvHeight.setText(holder.height);
                tvWeight.setText(holder.weight);
                tvBlood.setText(holder.blood);
                tvArea.setText(holder.area);

                //holder.pname = (TextView) findViewById(R.id.tvContactDetailName);
                //holder.name = profileModel.getName();
                //String personName = holder.name;
                //holder.pname.setText(personName);
                //holder.pname = profileModel.getName();
                // = (EditText) profileModel.getName();

            }

        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_edit.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivContact.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ivContact.setImageBitmap(thumbnail);
    }

    private class Holder {
        String dob,height,weight,blood,area;
        String name,path;
    }

}

