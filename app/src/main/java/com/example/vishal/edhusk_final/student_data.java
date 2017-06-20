package com.example.vishal.edhusk_final;

import android.view.View;

/**
 * Created by vishal on 14/06/17.
 */

public class student_data {

    private String _id;
    private String type;
    private String days;
    private String travel;
    public int age;
    private String batch;
    private String name;
    private String gender;
    private String email;
    private String contact;
    private String street;
    private int location;
    private String subjects;
    private String comment;
    private double latitude;
    private double longitude;


    private View.OnClickListener requestBtnClickListener;



    public student_data(String _id, String type, String days, String travel, int age, String batch, String name, String gender, String email, String contact, String street, int location, String subjects , String comment , int latitude, int longitude){

        this._id = _id;
        this.type = type;
        this.days = days;
        this.travel = travel;
        this.age = age;
        this.batch = batch;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.contact = contact;
        this.street = street;
        this.location = location;
        this.subjects = subjects;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;


    }



    public student_data(){}




        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getTravel() {
            return travel;
        }

        public void setTravel(String travel) {
            this.travel = travel;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getBatch() {
            return batch;
        }

        public void setBatch(String batch) {
            this.batch = batch;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public String getSubjects() {
            return subjects;
        }

        public void setSubjects(String subjects) {
            this.subjects = subjects;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }


    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

}
