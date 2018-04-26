package com.example.bubica.testprojektcontacts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bubica on 28.03.2018.
 */

public class ContactAdapter extends ArrayAdapter {

    //variables declaration
    public static List contactList = new ArrayList();

    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Contact contact) {
        contactList.add(contact);
        super.add(contact);
        Log.i("Contact object added", String.valueOf(contactList.size()));
    }

    @Override
    public int getCount() {

        return contactList.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return contactList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ContactHolder contactHolder;

        if(row == null){

            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.customlayout, parent, false);
            contactHolder = new ContactHolder();
            contactHolder.textViewName = (TextView) row.findViewById(R.id.textViewName);
            contactHolder.textViewTelephone = (TextView)row.findViewById(R.id.textViewNumber);
            contactHolder.imageViewSmall = (ImageView)row.findViewById(R.id.imageViewLargePhoto);
            row.setTag(contactHolder);

        }else{

            contactHolder = (ContactHolder) row.getTag();
        }
        Contact contact = (Contact) getItem(position);

        contactHolder.textViewName.setText(contact.getName().toString());
        contactHolder.textViewTelephone.setText(contact.getHomePhone().toString());
        contactHolder.imageViewSmall.setImageBitmap(contact.getSmallImage());
        return  row;
    }
    static class ContactHolder{

        TextView textViewName, textViewTelephone;
        ImageView imageViewSmall;
    }

}
