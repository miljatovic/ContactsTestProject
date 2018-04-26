package com.example.bubica.testprojektcontacts;

import android.graphics.Bitmap;


/**
 * Created by Bubica on 28.03.2018.
 */

public class Contact{

    private String name, company, favourite, email, website, birthdate, workPhone,
            homePhone, mobilePhone, street, city, state, country, zip;
   Bitmap smallImage,largeImage;

    private double latitude, longitude;

    public Contact(){

    }

    public Contact(String name, String company, String favourite, String email, String website,
                   String birthdate, String workPhone, String homePhone, String mobilePhone, String street,
                   String city, String state, String country, String zip, Bitmap smallImage,
                   Bitmap largeImage, double latitude, double longitude) {
        this.name = name;
        this.company = company;
        this.favourite = favourite;
        this.email = email;
        this.website = website;
        this.birthdate = birthdate;
        this.workPhone = workPhone;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
        this.smallImage = smallImage;
        this.largeImage = largeImage;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getFavourite() {
        return favourite;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public Bitmap getSmallImage() {
        return smallImage;
    }

    public Bitmap getLargeImage() {
        return largeImage;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {

        return longitude;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setSmallImage(Bitmap smallImage) {
        this.smallImage = smallImage;
    }

    public void setLargeImage(Bitmap largeImage) {
        this.largeImage = largeImage;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
