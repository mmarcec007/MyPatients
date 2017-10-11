package com.example.mark.mydoctors.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.mark.mydoctors.Model.Patient;

import java.util.ArrayList;

/**
 * Created by Mark on 07.10.2017..
 */

public class PatientDao extends CoreDao {

    public PatientDao(Context context) {
        super(context);
    }

    public int numberOfPatientsRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "patients");
        return numRows;
    }

    public boolean insertPatient(Patient p)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", p.getName());
        contentValues.put("surname", p.getSurname());
        contentValues.put("phone", p.getPhone());
        contentValues.put("email", p.getEmail());
        contentValues.put("street", p.getStreet());
        contentValues.put("place", p.getPlace());
        db.insert("patients", null, contentValues);

        return true;
    }

    public boolean updatePatient (Patient p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", p.getName());
        contentValues.put("surname", p.getSurname());
        contentValues.put("phone", p.getPhone());
        contentValues.put("email", p.getEmail());
        contentValues.put("street", p.getStreet());
        contentValues.put("place", p.getPlace());
        db.update("patients", contentValues, "id = ? ", new String[] { Integer.toString(p.getId()) } );
        return true;
    }

    public Integer deletePatient (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("patients",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Patient> getAllPatients()
    {
        ArrayList<Patient> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from patients", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Patient p = new Patient();
            p.setId(Integer.parseInt(res.getString(0)));
            p.setName(res.getString(1));
            p.setSurname(res.getString(2));
            p.setPhone(res.getString(3));
            p.setEmail(res.getString(4));
            p.setStreet(res.getString(5));
            p.setPlace(res.getString(6));
            array_list.add(p);

            res.moveToNext();
        }

        return array_list;
    }
}
