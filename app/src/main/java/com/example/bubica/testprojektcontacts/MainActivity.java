package com.example.bubica.testprojektcontacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Initialization globals
    SQLiteDatabase database;
    public static ListView listView;
    ContactAdapter contactAdapter;

    public Bitmap downloadImage(String bitmapUrl)  {

        try {

            URL url = new URL(bitmapUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream input = connection.getInputStream();
            Bitmap image;

            image = BitmapFactory.decodeStream(input);

            if(image == null)
                image = BitmapFactory.decodeResource(this.getResources(), R.drawable.bart);

            return image;

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return BitmapFactory.decodeResource(this.getResources(), R.drawable.bart);
    }

    public class DownloadTask extends AsyncTask <String, Contact, String>{

        @Override
        protected String doInBackground(String... strings) {

            String jsonString = "";
            URL url;
            HttpURLConnection connection;

            try {
                url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                InputStream input = connection.getInputStream();
                InputStreamReader reader = new InputStreamReader(input);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;
                    jsonString += current;
                    data = reader.read();
                }

                //creating JSONObject from the result String
                //Log.i("JSONString", jsonString);
                //JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = new JSONArray(jsonString);
                DatabaseManager databaseManager = DatabaseManager.getInstance(getApplicationContext());
                database = databaseManager.getWritableDatabase();
                //Log.i("Array length", String.valueOf(jsonArray.length()));
                database.execSQL("DELETE FROM contacts");
                Log.i("Numbers of rows read", "Database deleted!!!");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);

                    //reading the data from json object
                    Log.i("Reading ...", "Normal JSON objects");

                    String name = object.optString("name", "");
                    String company = object.optString("company", "");
                    String favourite = object.optString("favorite", "");
                    String smallImageUrl = object.optString("smallImageURL", "");
                    String largeImageUrl = object.optString("largeImageURL", "");
                    String email = object.optString("email", "");
                    String website = object.optString("website", "");
                    String birthdate = object.optString("birthdate", "");
                    Log.i("Reading ...", "Nested JSON objects");
                    Log.i("Small image url", String.valueOf(smallImageUrl.length()));
                    Log.i("Large image url", String.valueOf(largeImageUrl.length()));

                    //getting nested JSOn objects
                    JSONObject phoneObject = object.getJSONObject("phone");
                    JSONObject addressObject = object.getJSONObject("address");

                    //getting phones
                    String phoneWork = phoneObject.optString("work", "");
                    String phoneHome = phoneObject.optString("home", "");
                    String phoneMobile = phoneObject.optString("mobile", "");

                    //getting address details
                    String street = addressObject.optString("street", "");
                    String city = addressObject.optString("city", "");
                    String state = addressObject.optString("state", "");
                    String country = addressObject.optString("country", "");
                    String zip = addressObject.optString("zip", "");
                    double latitude = addressObject.optDouble("latitude", 0);
                    double longitude = addressObject.optDouble("longitude", 0);
                    Log.i("Reading ...", "All objects read");
                    Bitmap smallImage;
                    Bitmap largeImage;

                    //dealing with images
                    //downloading the images
                    Log.i("Reading ...", "Downloading bitmaps");

                    smallImage = downloadImage(smallImageUrl);
                    largeImage = downloadImage(largeImageUrl);

                    //create bytestream
                    Log.i("Reading ...", "Bitmaps downloaded");
                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();

                    //compressing and creating bytes array
                    smallImage.compress(Bitmap.CompressFormat.PNG, 100, stream1);
                    largeImage.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    byte[] byteArraySmallImage = stream1.toByteArray();
                    byte[] byteArrayLargeImage = stream2.toByteArray();

                    //writting into database

                    databaseManager.addInformation(database, name, company, favourite, byteArraySmallImage, byteArrayLargeImage, email, website, birthdate,
                            phoneWork, phoneHome, phoneMobile, street, city, state, country, zip, latitude, longitude);

                    //getting the files
                }
                Cursor c = databaseManager.getInformation(database);



                int nameIndex = c.getColumnIndex("name");
                int companyIndex = c.getColumnIndex("company");
                int favouriteIndex = c.getColumnIndex("favourite");
                int smallImageIndex = c.getColumnIndex("smallImage");
                int largeImageIndex = c.getColumnIndex("largeImage");
                int emailIndex = c.getColumnIndex("email");
                int websiteIndex = c.getColumnIndex("website");
                int birthdateIndex = c.getColumnIndex("birthdate");
                int workPhoneIndex = c.getColumnIndex("workPhone");
                int homePhoneIndex = c.getColumnIndex("homePhone");
                int mobilePhoneIndex = c.getColumnIndex("mobilePhone");
                int streetIndex = c.getColumnIndex("street");
                int cityIndex = c.getColumnIndex("city");
                int stateIndex = c.getColumnIndex("state");
                int countryIndex = c.getColumnIndex("country");
                int zipIndex = c.getColumnIndex("zip");
                int latitudeIndex = c.getColumnIndex("latitude");
                int longitudeIndex = c.getColumnIndex("longitude");

                //byte[] image = cursor.getBlob(1);
                Log.i("Name", String.valueOf(nameIndex));

                c.moveToFirst();

                do {
                    byte[] large = c.getBlob(largeImageIndex);
                    byte[] small = c.getBlob(smallImageIndex);

                    Bitmap largeBitmap = BitmapFactory.decodeByteArray(large, 0, large.length);
                    Bitmap smallBitmap = BitmapFactory.decodeByteArray(small, 0, small.length);

                    Contact contact = new Contact();

                    contact.setName(c.getString(nameIndex));
                    contact.setCompany(c.getString(companyIndex));
                    contact.setFavourite(c.getString(favouriteIndex));

                    contact.setLargeImage(largeBitmap);
                    contact.setSmallImage(smallBitmap);
                    contact.setEmail(c.getString(emailIndex));

                    contact.setWebsite(c.getString(websiteIndex));
                    contact.setBirthdate(c.getString(birthdateIndex));
                    contact.setWorkPhone(c.getString(workPhoneIndex));

                    contact.setHomePhone(c.getString(homePhoneIndex));
                    contact.setMobilePhone(c.getString(mobilePhoneIndex));
                    contact.setStreet(c.getString(streetIndex));

                    contact.setCity(c.getString(cityIndex));
                    contact.setState(c.getString(stateIndex));
                    contact.setCountry(c.getString(countryIndex));

                    contact.setZip(c.getString(zipIndex));
                    contact.setLatitude(c.getDouble(latitudeIndex));
                    contact.setLongitude(c.getDouble(longitudeIndex));

                    publishProgress(contact);


                } while (c.moveToNext());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        @Override
        protected void onProgressUpdate(Contact... values) {
            contactAdapter.add(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            listView.setAdapter(contactAdapter);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.myListView);
        contactAdapter = new ContactAdapter(getApplicationContext(), R.layout.customlayout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), ContactDetails.class);
                intent.putExtra("placeNumber", i);
                startActivity(intent);
            }
        });

        DownloadTask task = new DownloadTask();
        task.execute("https://waltken.de/Contacts_v2.json");


    }
}
