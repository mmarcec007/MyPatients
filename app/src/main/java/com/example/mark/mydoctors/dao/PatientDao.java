package com.example.mark.mydoctors.dao;

import android.content.ContentValues;

import com.example.mark.mydoctors.Model.Patient;

/**
 * Created by Mark on 07.10.2017..
 */

public class PatientDao {

    public ContentValues insertPatient(Patient p)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", p.getName());
        contentValues.put("surname", p.getSurname());
        contentValues.put("phone", p.getPhone());
        contentValues.put("email", p.getEmail());
        contentValues.put("street", p.getStreet());
        contentValues.put("place", p.getPlace());

        return contentValues;
    }

    public ContentValues updatePatient (Patient p)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", p.getName());
        contentValues.put("surname", p.getSurname());
        contentValues.put("phone", p.getPhone());
        contentValues.put("email", p.getEmail());
        contentValues.put("street", p.getStreet());
        contentValues.put("place", p.getPlace());

        return contentValues;
    }
}
