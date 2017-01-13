package org.uitm.ice;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.content.Intent;
import android.widget.ImageView;

import org.uitm.ice.sms.Utils;

//import com.example.takeimage.R;

public class Profile extends AppCompatActivity {

    private Button button;
    private EditText editBirthDate;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    private ImageView mImgEdit,mImgCamera,mImgProfile;
    private Uri fileUri;
    private File file;
    EditText etName;
    EditText etHeight,etWeight;
    Spinner spinner1,spinner2;
    ImageView ivContact;
    private String path = null;
    private static final int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    //Button btnSelect;
    //ImageView ivImage;
    //String path = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();
        addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        bindWidgetsWithEvent();
        checkForRequest();
        //editContact();
    }

    private View.OnClickListener mImgEditOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,SELECT_FILE);
        }
    };

    private View.OnClickListener mImgCameraOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private void checkForRequest() {
        String request = getIntent().getExtras().get(Constants.DML_TYPE).toString();
        //int i,j;
        if (request.equals(Constants.UPDATE)) {
            button.setText(Constants.UPDATE);
            etName.setText(getIntent().getExtras().get(Constants.NAME).toString());
            editBirthDate.setText(getIntent().getExtras().get(Constants.DOB).toString());
            etHeight.setText(getIntent().getExtras().get(Constants.HEIGHT).toString());
            etWeight.setText(getIntent().getExtras().get(Constants.WEIGHT).toString());

            //path = getIntent().getExtras().get(Constants.PATH).toString();
            /*
            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(path, options);

            ivContact.setImageBitmap(bm); */

            //etBlood.setText(getIntent().getExtras().get(Constants.BLOOD).toString());
            //etArea.setText(getIntent().getExtras().get(Constants.AREA).toString());

            if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("A+"))
                spinner1.setSelection(0);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("A-"))
                spinner1.setSelection(1);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("B+"))
                spinner1.setSelection(2);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("B-"))
                spinner1.setSelection(3);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("AB+"))
                spinner1.setSelection(4);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("AB-"))
                spinner1.setSelection(5);
            else if (getIntent().getExtras().get(Constants.BLOOD).toString().equalsIgnoreCase("0+"))
                spinner1.setSelection(6);
            else
                spinner1.setSelection(7);

            if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Selangor"))
                spinner2.setSelection(0);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Johor"))
                spinner2.setSelection(1);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Melaka"))
                spinner2.setSelection(2);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Negeri Sembilan"))
                spinner2.setSelection(3);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Kualau Lumpur"))
                spinner2.setSelection(4);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Perak"))
                spinner2.setSelection(5);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Kedah"))
                spinner2.setSelection(6);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Pulau Pinang"))
                spinner2.setSelection(7);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Perlis"))
                spinner2.setSelection(8);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Kelantan"))
                spinner2.setSelection(9);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Terengganu"))
                spinner2.setSelection(10);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Pahang"))
                spinner2.setSelection(11);
            else if (getIntent().getExtras().get(Constants.AREA).toString().equalsIgnoreCase("Sarawak"))
                spinner2.setSelection(12);
            else
                spinner2.setSelection(13);

        } else {
            button.setText(Constants.INSERT);
        }
    }

    private void findViewsById() {
        editBirthDate = (EditText) findViewById(R.id.editBirthDate);
        editBirthDate.setInputType(InputType.TYPE_NULL);
        etName = (EditText) findViewById (R.id.editName);
        etHeight = (EditText) findViewById (R.id.editHeight);
        etWeight = (EditText) findViewById(R.id.editWeight);
        ivContact = (ImageView) findViewById(R.id.ivContactImage);
        mImgCamera = (ImageView) findViewById(R.id.imgCamera);
        mImgEdit = (ImageView) findViewById(R.id.imgEdit);
        mImgProfile = (ImageView) findViewById(R.id.imgProfile);
        //etBlood = spinner1.getSelectedItem();
        //etArea = (EditText) findViewById(R.id.editBlood);
    }


    private void bindWidgetsWithEvent() {
        editBirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
        mImgEdit.setOnClickListener(mImgEditOnClickListener);
        mImgCamera.setOnClickListener(mImgCameraOnClickListener);
    }
    /*
    public void editContact() {
        ivContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    } */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap result = null;
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case SELECT_FILE:
                file = previewSelectedImage(this, data.getData(), null, false, false, 0, true, null);
                GroupActivity.performCrop(Uri.fromFile(file), EditProfileActivity.this, CROP_IMAGE_REQUEST_CODE);
                break;
            case CAMERA_CAPTURE_IMAGE_REQUEST_CODE:
                GroupActivity.performCrop(fileUri, EditProfileActivity.this, CROP_IMAGE_REQUEST_CODE);
                break;
            case CROP_IMAGE_REQUEST_CODE:
                if (data.getStringExtra(CropImage.IMAGE_PATH) == null) {
                    return;
                }
                result = BitmapFactory.decodeFile(data.getStringExtra(CropImage.IMAGE_PATH));
                file = Utils.getFile(this, result, true, null);
                file = GroupActivity.previewCapturedImage(this, Uri.parse(file.getAbsolutePath()), imgProfile, false, false, 0, true, null);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
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
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);
        //path = selec;
        path = "NICE";

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

        mImgProfile.setImageBitmap(bm);
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

    private void setDateTimeField() {
        //editBirthDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker v, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                editBirthDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("Selangor");
        list.add("Johor");
        list.add("Melaka");
        list.add("Negeri Sembilan");
        list.add("Kuala Lumpur");
        list.add("Perak");
        list.add("Kedah");
        list.add("Pulau Pinang");
        list.add("Perlis");
        list.add("Kelantan");
        list.add("Terengganu");
        list.add("Pahang");
        list.add("Sarawak");
        list.add("Sabah");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        button = (Button) findViewById(R.id.button);
        //bt_date = (Button) findViewById(R.id.dialog_bt_date);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                /*
                if (String.valueOf(spinner1.getSelectedItem()).equalsIgnoreCase("A+"))
                {
                    Intent i = new Intent(this,Record.class);
                    startActivity(i);
                }*/
                /*
                Toast.makeText(Profile.this,
                        "Result : " +
                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show(); */
                onButtonClick();

            }

        });

    }

    private void onButtonClick() {
        //if (etAllergies.getText().toString().equals("") || etLastname.getText().toString().equals("")) {
        if (etName.getText().toString().equals("")||etHeight.getText().toString().equals("")||etWeight.getText().toString().equals("")||editBirthDate.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please complete your information data", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(Constants.NAME, etName.getText().toString());
            intent.putExtra(Constants.HEIGHT, etHeight.getText().toString());
            intent.putExtra(Constants.WEIGHT, etWeight.getText().toString());
            intent.putExtra(Constants.DOB, editBirthDate.getText().toString());
            intent.putExtra(Constants.BLOOD, String.valueOf(spinner1.getSelectedItem()));
            intent.putExtra(Constants.AREA, String.valueOf(spinner2.getSelectedItem()));
            intent.putExtra(Constants.PATH, path);
            setResult(RESULT_OK, intent);
            finish();
            Toast.makeText(getApplicationContext(), "Done Update", Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
        }
    }

    /*

    public static Uri getOutputMediaFileUri(Context context, boolean isDelete, File file) {
        return Uri.fromFile(getOutputMediaFile(context, isDelete, file));
    }

    public static File getOutputMediaFile(Context context, boolean isDelete, File mediaStorageDir) {
        if (mediaStorageDir == null) {
            mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.folder_name) + "/" +
                    context.getResources().getString(R.string.temp));
        }

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        } else {
            if (isDelete) {
                boolean isDirDeleted = deleteDir(mediaStorageDir);
                if (isDirDeleted) {
                    mediaStorageDir.mkdirs();
                }
            }
        }
        String timeStamp = setCalendarDate("yyyyMMdd_HHmmss_SSS", new Date());
        return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children.length > 0) {
                for (int i = 0; i < children.length - 1; i++) {
                    boolean success = deleteDir(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        assert dir != null;
        return dir.delete();
    }

    public static String setCalendarDate(String dateFormat, Date date) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.getDefault());
        try {
            result = format.format(date);
        } catch (Exception ex) {
            ex.fillInStackTrace();
        } finally {
            if (result == null) {
                //org.joda.time.DateTime dt = new org.joda.time.DateTime(date);
                //result = dt.toString(dateFormat);
            }
        }
        return result;
    }

    public static File previewSelectedImage(Context context, Uri selectedImage, final ImageView img,
                                            final boolean isCrop, boolean isGropPicSmall, int requestedCode, boolean isDelete, File direct) {
        if (getRealPathFromURI(context, selectedImage) != null) {
            File file = new File(getRealPathFromURI(context, selectedImage));
            file = convertImageToSmall(context, file, isGropPicSmall, isDelete, direct);
            DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
            int mHeight = mDisplayMetrics.heightPixels;
            int mWidth = mDisplayMetrics.widthPixels;
            if (!isCrop) {
                if (img != null) {
                    Glide.with(context)
                            .load(file.getAbsolutePath())
                            .asBitmap()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.empty_photo)
                            .into(new SimpleTarget<Bitmap>(mWidth, mHeight) {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                    img.setImageBitmap(bitmap);
                                }
                            });
                }
            } else {
                boolean isSuccess = performCrop(selectedImage, context, requestedCode);
                if (!isSuccess) {
                    if (img != null) {
                        Glide.with(context)
                                .load(file.getAbsolutePath())
                                .asBitmap()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.empty_photo)
                                .into(new SimpleTarget<Bitmap>(mWidth, mHeight) {
                                    @Override
                                    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                                        img.setImageBitmap(bitmap);
                                    }
                                });
                    }
                }
            }
            return file;
        } else {
            return null;
        }
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        String result = null;
        try {
            cursor = context.getContentResolver().query(contentUri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        } catch (Exception ex) {
            ex.fillInStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        if (contentUri != null && result == null) {
            try {
                File file = new File(new URI(contentUri.toString()));
                result = file.getAbsolutePath();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static File convertImageToSmall(Context context, File file, boolean isGropPicSmall, boolean isDelete, File direct) {
        if (file != null) {
            InputStream is = null;
            int sampleSize = 0;
            try {
                sampleSize = calculateBitmapSampleSize(Uri.fromFile(file), context, isGropPicSmall);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is = context.getContentResolver().openInputStream(Uri.fromFile(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = sampleSize;
            Bitmap b = BitmapFactory.decodeStream(is, null, option);
            return getFile(context, b, isDelete, direct);
        } else return null;
    }

    public static int calculateBitmapSampleSize(Uri bitmapUri, Context context, boolean isGroupPicSmall) throws IOException {
        InputStream is = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            is = context.getContentResolver().openInputStream(bitmapUri);
            BitmapFactory.decodeStream(is, null, options); // Just get image size
        } finally {
            closeSilently(is);
        }

        int maxSize = getMaxImageSize();
        int sampleSize = 1;
        if (!isGroupPicSmall) {
            while (options.outHeight / sampleSize > maxSize || options.outWidth / sampleSize > maxSize) {
                sampleSize = sampleSize << 1;
            }
        } else {
            while (options.outHeight / sampleSize > 600 || options.outWidth / sampleSize > 600) {
                sampleSize = sampleSize << 1;
            }
        }
        return sampleSize;
    } */

    /*
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    } */
}