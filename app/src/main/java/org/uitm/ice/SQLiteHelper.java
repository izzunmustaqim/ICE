package org.uitm.ice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.uitm.ice.objects.AllergiesModel;
import org.uitm.ice.objects.ContactsModel;
import org.uitm.ice.objects.MedicinesModel;
import org.uitm.ice.objects.ProfileModel;

import java.util.ArrayList;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Izzun Mustaqim on 27-08-2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    //private static final int DATABASE_VERSION = 3;
    private static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public static final String TABLE_NAME = "PEOPLE";
    public static final String TABLE_HEALTH = "HEALTH_RECORD";
    public static final String COLUMN_ID = "ID";
    //public static final String COLUMN_ALLERGIES = "ALLERGIES";
    public static final String COLUMN_MEDICINES = "MEDICINES";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";

    private static final String DATABASE_CREATE_CONTACT = "create table if not exists Contact (id integer primary key autoincrement,  name varchar(100), phone varchar(100) not null);";
    private static final String DATABASE_CREATE_HEALTHRECORD = "create table if not exists healthrecord (id integer primary key autoincrement, type int, content text);";
    private static final String DATABASE_DROP_CONTACT = "drop table if exists Contact";
    private static final String DATABASE_CREATE_PROFILE = "create table profile ( userId INTEGER PRIMARY KEY, name TEXT, dob TEXT, height text, weight text, blood text, area text, path text);";

    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + " VARCHAR, " + COLUMN_LAST_NAME + " VARCHAR);");
        //Log.d("urgence", "Contact oncreate : create table if not exists healthrecord (id integer primary key autoincrement, type int, content text);");
        //db.execSQL("create table " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ALLERGIES + " VARCHAR, " + COLUMN_LAST_NAME + " VARCHAR);");
        db.execSQL(DATABASE_CREATE_HEALTHRECORD);
        //db.execSQL("create table " + TABLE_HEALTH + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MEDICINES + " VARCHAR);");
        //Log.d("urgence", "Contact oncreate : create table if not exists healthrecord (id integer primary key autoincrement, type int, content text);");
        db.execSQL(DATABASE_CREATE_CONTACT);
        db.execSQL(DATABASE_CREATE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("drop table if exists healthrecord");
        db.execSQL(DATABASE_DROP_CONTACT);
        db.execSQL("drop table if exists profile");
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_HEALTH);
        //db.execSQL("create table if not exists healthrecord (id integer primary key autoincrement, type int, content text);");
        onCreate(db);
    }

    public void insertRecord(AllergiesModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("content", Contact.getAllergies());
        //contentValues.put("type", Contact.getType());
        contentValues.put(COLUMN_FIRST_NAME, contact.getAllergies());
        //contentValues.put(COLUMN_LAST_NAME, Contact.getLastName());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void insertRecordM(MedicinesModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COLUMN_FIRST_NAME, Contact.getMedicines());
        //contentValues.put(COLUMN_LAST_NAME, Contact.getLastName());
        contentValues.put("content", contact.getMedicines());
        contentValues.put("type", contact.getType());
        //database.insert(TABLE_NAME, null, contentValues);
        database.insert("healthrecord", null, contentValues);
        database.close();
    }

    public void insertRecordC(ContactsModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("content", Contact.getAllergies());
        //contentValues.put("type", Contact.getType());
        contentValues.put("name", contact.getName());
        contentValues.put("phone", contact.getPhone());
        database.insert("Contact", null, contentValues);
        database.close();
    }

    public void insertRecordP(ProfileModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("dob", contact.getDOB());
        contentValues.put("height",contact.getHeight());
        contentValues.put("weight",contact.getWeight());
        contentValues.put("blood",contact.getBlood());
        contentValues.put("area",contact.getArea());
        contentValues.put("path",contact.getPath());
        database.insert("profile", null, contentValues);
        database.close();
    }

    public void insertRecordAlternate(ContactsModel contact) {
        database = this.getReadableDatabase();
        //database.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_FIRST_NAME + "," + COLUMN_LAST_NAME + ") VALUES('" + Contact.getFirstName() + "','" + Contact.getLastName() + "')");
        //database.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_FIRST_NAME + ") VALUES('" + contact.getAllergies() + "')");
        database.close();
    }

    public ArrayList<AllergiesModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor cursor = database.query("healthrecord", null, null, null, null, null, null);

        ArrayList<AllergiesModel> contacts = new ArrayList<AllergiesModel>();
        AllergiesModel allergiesModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                allergiesModel = new AllergiesModel();
                allergiesModel.setID(cursor.getString(0));
                allergiesModel.setAllergies(cursor.getString(1));
                //allergiesModel.setType(cursor.getInt(1));

                contacts.add(allergiesModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<MedicinesModel> getAllRecordsA() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor cursor = database.query("healthrecord", null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT * FROM healthrecord WHERE type=0", null);

        ArrayList<MedicinesModel> contacts = new ArrayList<MedicinesModel>();
        MedicinesModel medicinesModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                medicinesModel = new MedicinesModel();
                medicinesModel.setID(cursor.getString(0));
                medicinesModel.setMedicines(cursor.getString(2));
                medicinesModel.setType(cursor.getInt(1));

                contacts.add(medicinesModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<MedicinesModel> getAllRecordsM() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor cursor = database.query("healthrecord", null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT * FROM healthrecord WHERE type=2", null);

        ArrayList<MedicinesModel> contacts = new ArrayList<MedicinesModel>();
        MedicinesModel medicinesModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                medicinesModel = new MedicinesModel();
                medicinesModel.setID(cursor.getString(0));
                medicinesModel.setMedicines(cursor.getString(2));
                medicinesModel.setType(cursor.getInt(1));

                contacts.add(medicinesModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<MedicinesModel> getAllRecordsD() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor cursor = database.query("healthrecord", null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT * FROM healthrecord WHERE type=1", null);

        ArrayList<MedicinesModel> contacts = new ArrayList<MedicinesModel>();
        MedicinesModel medicinesModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                medicinesModel = new MedicinesModel();
                medicinesModel.setID(cursor.getString(0));
                medicinesModel.setMedicines(cursor.getString(2));
                medicinesModel.setType(cursor.getInt(1));

                contacts.add(medicinesModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<MedicinesModel> getAllRecordsN() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        //Cursor cursor = database.query("healthrecord", null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT * FROM healthrecord WHERE type=3", null);

        ArrayList<MedicinesModel> contacts = new ArrayList<MedicinesModel>();
        MedicinesModel medicinesModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                medicinesModel = new MedicinesModel();
                medicinesModel.setID(cursor.getString(0));
                medicinesModel.setMedicines(cursor.getString(2));
                medicinesModel.setType(cursor.getInt(1));

                contacts.add(medicinesModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<ContactsModel> getAllRecordsC() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = database.query("Contact", null, null, null, null, null, null);
        ArrayList<ContactsModel> contacts = new ArrayList<ContactsModel>();
        ContactsModel contactsModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                contactsModel = new ContactsModel();
                contactsModel.setId(cursor.getString(0));
                contactsModel.setPhone(cursor.getString(2));
                contactsModel.setName(cursor.getString(1));

                contacts.add(contactsModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public ArrayList<ProfileModel> getProfileDetail() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = database.query("profile", null, null, null, null, null, null);
        ArrayList<ProfileModel> profile = new ArrayList<ProfileModel>();
        ProfileModel profileModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                profileModel = new ProfileModel();
                profileModel.setID(cursor.getString(0));
                profileModel.setName(cursor.getString(1));
                profileModel.setDob(cursor.getString(2));
                profileModel.setHeight(cursor.getString(3));
                profileModel.setWeight(cursor.getString(4));
                profileModel.setBlood(cursor.getString(5));
                profileModel.setArea(cursor.getString(6));
                //profileModel.setPath(cursor.getString(7));

                profile.add(profileModel);
            }
        }
        cursor.close();
        database.close();

        return profile;
    }

    /*
    public ArrayList<ContactsModel> reloadContact() {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = database.query("Contact", null, null, null, null, null, null);
        ArrayList<ContactsModel> contacts = new ArrayList<ContactsModel>();
        ContactsModel contactsModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                contactsModel = new ContactsModel();
                contactsModel.setId(cursor.getString(0));
                contactsModel.setPhone(cursor.getString(2));
                contactsModel.setName(cursor.getString(1));

                contacts.add(contactsModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    } */

    public ArrayList<ContactsModel> getAllRecordsAlternate() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<ContactsModel> contacts = new ArrayList<ContactsModel>();
        ContactsModel ContactsModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                //ContactsModel = new ContactsModel();
                //ContactsModel.setID(cursor.getString(0));
                //ContactsModel.setAllergies(cursor.getString(1));
                //ContactsModel.setLastName(cursor.getString(2));

                //contacts.add(ContactsModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }


    public void updateRecord(AllergiesModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, contact.getAllergies());
        //contentValues.put(COLUMN_LAST_NAME, Contact.getLastName());
        database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void updateRecordM(MedicinesModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", contact.getMedicines());
        //contentValues.put(COLUMN_LAST_NAME, Contact.getLastName());
        database.update("healthrecord", contentValues, "id" + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void updateRecordC(ContactsModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", contact.getPhone());
        contentValues.put("name", contact.getName());
        database.update("Contact", contentValues, "id" + " = ?", new String[]{contact.getId()});
        database.close();
    }

    public void updateRecordP(ProfileModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", contact.getName());
        contentValues.put("dob", contact.getDOB());
        contentValues.put("height", contact.getHeight());
        contentValues.put("weight", contact.getWeight());
        contentValues.put("blood", contact.getBlood());
        contentValues.put("area", contact.getArea());
        //contentValues.put("path", contact.getPath());
        database.update("profile", contentValues, "userId" + " = ?", new String[]{contact.getID()});
        //database.update("profile", contentValues, "userId" + " = 1", new String[]{contact.getId()});
        database.close();
    }

    public void updateRecordAlternate(ContactsModel contact) {
        database = this.getReadableDatabase();
        //database.execSQL("update " + TABLE_NAME + " set " + COLUMN_FIRST_NAME + " = '" + Contact.getFirstName() + "', " + COLUMN_LAST_NAME + " = '" + Contact.getLastName() + "' where " + COLUMN_ID + " = '" + Contact.getID() + "'");
        //database.execSQL("update " + TABLE_NAME + " set " + COLUMN_FIRST_NAME + " = '" + Contact.getFirstName() + "', " + "' where " + COLUMN_ID + " = '" + Contact.getID() + "'");
        database.close();
    }

    public void deleteAllRecords() {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, null, null);
        database.close();
    }

    public void deleteAllRecordsAlternate() {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
        database.close();
    }

    public void deleteRecord(AllergiesModel contact) {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void deleteRecordM(MedicinesModel contact) {
        database = this.getReadableDatabase();
        database.delete("healthrecord", "id" + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void deleteRecordC(ContactsModel contact) {
        database = this.getReadableDatabase();
        database.delete("Contact", "id" + " = ?", new String[]{contact.getId()});
        database.close();
    }

    public void deleteRecordAlternate(ContactsModel contact) {
        database = this.getReadableDatabase();
        //database.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + contact.getID() + "'");
        database.close();
    }

    public Cursor contactNo(ContactsModel contact) {
        database = this.getReadableDatabase();
        //database.execSQL("select from " + "Contact" + " where " + "id" + " = '" + contact.getID() + "'");
        Cursor cursor = database.rawQuery("SELECT phone FROM Contact WHERE phone = ?",new String[] { contact.getPhone() });
        /*
        if (cursor != null) {
            cursor.moveToFirst();
        } */

        return cursor;

    }
    /*
    public ArrayList<ContactsModel> contactNo(String contact) {
        database = this.getReadableDatabase();
        //Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = database.rawQuery("SELECT phone FROM Contact WHERE phone = ?",new String[] { contact });
        ArrayList<ContactsModel> contacts = new ArrayList<ContactsModel>();
        ContactsModel contactsModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                contactsModel = new ContactsModel();
                //contactsModel.setId(cursor.getString(0));
                contactsModel.setPhone(cursor.getString(2));
                //contactsModel.setName(cursor.getString(1));

                contacts.add(contactsModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    } */

    /*
    public DBController(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
    }
    //Creates Table
    @Override
    public void onCreate(SQLiteDatabase database) {
        String query;
        query = "CREATE TABLE users ( userId INTEGER PRIMARY KEY, userName TEXT, udpateStatus TEXT)";
        database.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query;
        query = "DROP TABLE IF EXISTS users";
        database.execSQL(query);
        onCreate(database);
    }
    */
    /**
     * Inserts User into SQLite DB
     * @param queryValues
     */
    public void insertUser(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", queryValues.get("userName"));
        values.put("udpateStatus", "no");
        database.insert("users", null, values);
        database.close();
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllUsers() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM users where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("userId", cursor.getString(0));
                map.put("userName", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM users where udpateStatus = '"+"no"+"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        count = cursor.getCount();
        database.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(String id, String status){
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update users set udpateStatus = '"+ status +"' where userId="+"'"+ id +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }

}
