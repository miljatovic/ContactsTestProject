package com.example.bubica.testprojektcontacts;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetails extends AppCompatActivity {

    TextView name, company, workPhone, homePhone, mobilePhone, street, state, zip, city, birthday, email, favouriteView;
    String favourite;
    String emailString;
    ImageButton favouriteButton;
    ContactAdapter contactAdapter;
    Contact contact;
    ImageButton editButton;
    ImageView largePhoto;
    int placeNumberContact;
    Bitmap largePhotoBitmap;
    DatabaseManager dbManager = DatabaseManager.getInstance(this);
    SQLiteDatabase database = dbManager.getWritableDatabase();

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        contact = (Contact)ContactAdapter.contactList.get(placeNumberContact);
        setViewsValues();

    }
    public void setViewsValues(){
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
        emailString = contact.getEmail();
        favourite = contact.getFavourite();

        if(favourite.equals("1") || favourite.equals("true"))
            favouriteButton.setImageResource(R.drawable.staryellow);
        else
            favouriteButton.setImageResource(R.drawable.startblack);

        editButton.setImageResource(R.drawable.pencil);
        largePhotoBitmap = contact.getLargeImage();
        largePhoto.setImageBitmap(largePhotoBitmap);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        contactAdapter = new ContactAdapter(getApplicationContext(), placeNumberContact);

        final Intent intent = getIntent();
        placeNumberContact = intent.getIntExtra("placeNumber", 0);
        contact = (Contact)ContactAdapter.contactList.get(placeNumberContact);
        MainActivity.listView.setAdapter(contactAdapter);

        //initializing views
        //textViews
        name = (TextView)findViewById(R.id.textViewName);
        company = (TextView)findViewById(R.id.textViewCompany);
        workPhone = (TextView)findViewById(R.id.textViewWork);
        homePhone = (TextView)findViewById(R.id.textViewHome);
        mobilePhone = (TextView)findViewById(R.id.textViewMobile);
        street = (TextView)findViewById(R.id.textViewStreet);
        state = (TextView)findViewById(R.id.textViewState);
        zip = (TextView)findViewById(R.id.textViewZip);
        city = (TextView)findViewById(R.id.textViewCity);
        birthday = (TextView)findViewById(R.id.textViewBirthday);
        email = (TextView)findViewById(R.id.textViewEmail);

        //images
        favouriteButton = (ImageButton)findViewById(R.id.imageButtonStar);
        editButton = (ImageButton)findViewById(R.id.imageButtonEdit);
        largePhoto = (ImageView)findViewById(R.id.imageViewLargePhoto);

        setViewsValues();

        //adding listeners
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favourite.equals("1") || favourite.equals("true")) {
                    favourite = "false";
                    favouriteButton.setImageResource(R.drawable.startblack);
                    dbManager.changePreference(database, "false", emailString);
                    contact.setFavourite("false");
                    contactAdapter.notifyDataSetChanged();

                }else{
                    favourite = "true";
                    favouriteButton.setImageResource(R.drawable.staryellow);
                    dbManager.changePreference(database, "true", emailString);
                    contact.setFavourite("true");
                    contactAdapter.notifyDataSetChanged();
                }
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), PopActivity.class);
                intent1.putExtra("placeNumber", placeNumberContact);
                startActivity(intent1);
            }
        });
    }
}
