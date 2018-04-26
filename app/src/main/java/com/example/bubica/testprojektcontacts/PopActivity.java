package com.example.bubica.testprojektcontacts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PopActivity extends AppCompatActivity {
    public static boolean saved = true;
    DatabaseManager dbManager = DatabaseManager.getInstance(this);
    Button saveButton;
    Contact contact;
    Button closeButton;
    int placeNumberContact;
    ContactAdapter contactAdapter;
    SQLiteDatabase database = dbManager.getWritableDatabase();
    EditText name, company, workPhone, homePhone, mobilePhone, street, state, zip, city, birthday, email;
    String nameString, companyString, workPhoneString, homePhoneString, mobilePhoneString, streetString,
            stateString, zipString, cityString, birthdayString, emailString;

    public void closeWindow (View view){
        if(saved == true)
            finish();
    }
    public void saveChanges(View button){
        //updating the database
        nameString = name.getText().toString();
        companyString = company.getText().toString();
        workPhoneString = workPhone.getText().toString();
        homePhoneString = homePhone.getText().toString();
        mobilePhoneString = mobilePhone.getText().toString();
        streetString = street.getText().toString();
        stateString = state.getText().toString();
        zipString = zip.getText().toString();
        cityString = city.getText().toString();
        birthdayString = birthday.getText().toString();
        emailString = email.getText().toString();
        dbManager.updateInformation(database, nameString, companyString, emailString, workPhoneString,birthdayString, homePhoneString, mobilePhoneString, streetString, cityString,zipString, stateString);

        //updating object
        contact.setName(nameString);
        contact.setCompany(companyString);
        contact.setWorkPhone(workPhoneString);
        contact.setHomePhone(homePhoneString);
        contact.setMobilePhone(mobilePhoneString);
        contact.setStreet(streetString);
        contact.setState(stateString);
        contact.setZip(zipString);
        contact.setCity(cityString);
        contact.setBirthdate(birthdayString);
        contact.setEmail(emailString);


        MainActivity.listView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);
        saveButton = (Button)findViewById(R.id.saveButton);
        closeButton = (Button) findViewById(R.id.closeButton);
        database = dbManager.getWritableDatabase();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .9), (int)(height * .9));

        Intent intent = getIntent();
        placeNumberContact = intent.getIntExtra("placeNumber", 0);
        contactAdapter = new ContactAdapter(getApplicationContext(), placeNumberContact);
        contact = (Contact)ContactAdapter.contactList.get(placeNumberContact);

        name = (EditText)findViewById(R.id.textViewName);
        company = (EditText)findViewById(R.id.textViewCompany);
        workPhone = (EditText)findViewById(R.id.textViewWork);
        homePhone = (EditText)findViewById(R.id.textViewHome);
        mobilePhone = (EditText)findViewById(R.id.textViewMobile);
        street = (EditText)findViewById(R.id.textViewStreet);
        state = (EditText)findViewById(R.id.textViewState);
        zip = (EditText)findViewById(R.id.textViewZip);
        city = (EditText)findViewById(R.id.textViewCity);
        birthday = (EditText)findViewById(R.id.textViewBirthday);
        email = (EditText)findViewById(R.id.textViewEmail);

        //setting values
        name.setText(contact.getName());
        company.setText(contact.getCompany());
        workPhone.setText(contact.getWorkPhone());
        homePhone.setText(contact.getHomePhone());
        mobilePhone.setText(contact.getMobilePhone());
        street.setText(contact.getStreet());
        state.setText(contact.getState());
        zip.setText(contact.getZip());
        city.setText(contact.getCity());
        birthday.setText(contact.getBirthdate());
        email.setText(contact.getEmail());


    }
}
