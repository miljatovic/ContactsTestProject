package com.example.bubica.testprojektcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bubica on 27.03.2018.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    //database and table
    public static final String TABLE_NAME = "contacts";
    public static final String DB_NAME = "contacts";
    public static final int DB_VERSION = 1;

    //instance
    private static DatabaseManager dmInstance;

    private static final String createQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(id INTEGER PRIMARY KEY, " +
            ContactContract.ProductEntry.NAME + " VARCHAR, " + ContactContract.ProductEntry.COMPANY + " VARCHAR, " + ContactContract.ProductEntry.FAVOURITE + " VARCHAR, " +
            ContactContract.ProductEntry.SMALLIMAGE + " BLOB, " + ContactContract.ProductEntry.LARGEIMAGE + " BLOB, " + ContactContract.ProductEntry.EMAIL + " VARCHAR, " +
            ContactContract.ProductEntry.WEBSITE + " VARCHAR, " + ContactContract.ProductEntry.BIRTHDATE + " VARCHAR, " + ContactContract.ProductEntry.WORKPHONE + " VARCHAR, " +
            ContactContract.ProductEntry.HOMEPHONE  + " VARCHAR, " + ContactContract.ProductEntry.MOBILEPHONE + " VARCHAR, " +
            ContactContract.ProductEntry.STREET + " VARCHAR, " + ContactContract.ProductEntry.CITY + " VARCHAR, " +
            ContactContract.ProductEntry.STATE + " VARCHAR, " + ContactContract.ProductEntry.COUNTRY + " VARCHAR, " +
            ContactContract.ProductEntry.ZIP + " VARCHAR, " + ContactContract.ProductEntry.LATITUDE + " NUMERIC, " + ContactContract.ProductEntry.LONGITUDE + " NUMERIC)" ;

    private DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i("Database manager", "Database created...");
    }
    public static synchronized DatabaseManager getInstance(Context context){

        if(dmInstance == null){

            dmInstance = new DatabaseManager(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
        }
        return dmInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createQuery);
        Log.i("Database manager", "Table created...");

    }
    public void addInformation(SQLiteDatabase database, String name, String company, String favourite,  byte[] smallImage,
                               byte[] largeImage, String email, String website, String birthdate,
                               String workPhone, String homePhone, String mobilePhone, String street, String city, String state,
                               String country, String zip, double latitude, double longitude ){


        ContentValues contentValues= new ContentValues();

        contentValues.put(ContactContract.ProductEntry.NAME, name);
        contentValues.put(ContactContract.ProductEntry.COMPANY, company);
        contentValues.put(ContactContract.ProductEntry.FAVOURITE, favourite);
        contentValues.put(ContactContract.ProductEntry.SMALLIMAGE, smallImage);
        contentValues.put(ContactContract.ProductEntry.LARGEIMAGE, largeImage);
        contentValues.put(ContactContract.ProductEntry.EMAIL, email);
        contentValues.put(ContactContract.ProductEntry.WEBSITE, website);
        contentValues.put(ContactContract.ProductEntry.BIRTHDATE, birthdate);
        contentValues.put(ContactContract.ProductEntry.WORKPHONE, workPhone);
        contentValues.put(ContactContract.ProductEntry.HOMEPHONE, homePhone);
        contentValues.put(ContactContract.ProductEntry.MOBILEPHONE, mobilePhone);
        contentValues.put(ContactContract.ProductEntry.STREET, street);
        contentValues.put(ContactContract.ProductEntry.CITY, city);
        contentValues.put(ContactContract.ProductEntry.STATE, state);
        contentValues.put(ContactContract.ProductEntry.COUNTRY, country);
        contentValues.put(ContactContract.ProductEntry.ZIP, zip);
        contentValues.put(ContactContract.ProductEntry.LATITUDE, latitude);
        contentValues.put(ContactContract.ProductEntry.LONGITUDE, longitude);

        //adding one row of the data to the table
        database.insert(TABLE_NAME, null, contentValues);
        Log.i("Database manager", "One row of data inserted...");

    }

    public void updateInformation(SQLiteDatabase database, String name, String company, String email, String birthdate,
                               String workPhone, String homePhone, String mobilePhone, String street, String city, String state,
                               String zip){


        ContentValues contentValues= new ContentValues();

        contentValues.put(ContactContract.ProductEntry.NAME, name);
        contentValues.put(ContactContract.ProductEntry.COMPANY, company);
        contentValues.put(ContactContract.ProductEntry.EMAIL, email);
        contentValues.put(ContactContract.ProductEntry.BIRTHDATE, birthdate);
        contentValues.put(ContactContract.ProductEntry.WORKPHONE, workPhone);
        contentValues.put(ContactContract.ProductEntry.HOMEPHONE, homePhone);
        contentValues.put(ContactContract.ProductEntry.MOBILEPHONE, mobilePhone);
        contentValues.put(ContactContract.ProductEntry.STREET, street);
        contentValues.put(ContactContract.ProductEntry.CITY, city);
        contentValues.put(ContactContract.ProductEntry.STATE, state);
        contentValues.put(ContactContract.ProductEntry.ZIP, zip);

        //adding one row of the data to the table
        String where = ContactContract.ProductEntry.EMAIL+"=?";
        String[] whereArgs = new String[] {String.valueOf(email)};
        database.update(DatabaseManager.TABLE_NAME, contentValues, where, whereArgs);
        Log.i("Data changed", "Values updated");

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor getInformation(SQLiteDatabase db){

        Cursor c = db.rawQuery("SELECT * FROM contacts", null);

        return c;
    }
    public void changePreference (SQLiteDatabase database, String favourite, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactContract.ProductEntry.FAVOURITE, favourite);
        contentValues.put(ContactContract.ProductEntry.EMAIL, email);
        String where = ContactContract.ProductEntry.EMAIL+"=?";
        String[] whereArgs = new String[] {String.valueOf(email)};
        database.update(DatabaseManager.TABLE_NAME, contentValues, where, whereArgs);
        Log.i("Data changed", "Preference updated: " + favourite)   ;
    }

}
