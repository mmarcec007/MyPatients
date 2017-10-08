package com.example.mark.mydoctors.DatabaseOperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mark.mydoctors.Model.Disease;
import com.example.mark.mydoctors.Model.Medicine;
import com.example.mark.mydoctors.Model.Patient;

import java.util.ArrayList;

/**
 * Created by Mark on 21.05.2016..
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName2.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 7);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL("create table patients " + "(id integer primary key, name text,surname text, phone text,email text, street text,place text)");
        db.execSQL("create table diseases " + "(id INTEGER PRIMARY KEY, name text, is_over text, patient_id integer, FOREIGN KEY(patient_id) REFERENCES patients(id))");
        db.execSQL("create table medicine " + "(id INTEGER PRIMARY KEY, name text, disease_id integer, patient_id integer, FOREIGN KEY(disease_id) REFERENCES diseases(id), FOREIGN KEY(patient_id) REFERENCES patients(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS patients");
        db.execSQL("DROP TABLE IF EXISTS diseases");
        db.execSQL("DROP TABLE IF EXISTS medicine");
        onCreate(db);
    }

    public boolean insertPatient(Patient p)
    {
        SQLiteDatabase db = this.getWritableDatabase();
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

    public boolean insertDisease(Disease d, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", d.getName());
        contentValues.put("is_over", d.getIs_over());
        contentValues.put("patient_id", id);
        db.insert("diseases", null, contentValues);
        return true;
    }

    public boolean insertMedicinesForDisease(Medicine m, int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", m.getName());
        contentValues.put("disease_id", id);
        db.insert("medicine", null, contentValues);
        return true;
    }

    public Cursor getPatientsData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from patients where id="+id+"", null );
        return res;
    }

    /*
    public Cursor getPatientsDiseases(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from patients where id="+id+"", null );
        return res;
    }
*/
    public ArrayList<Disease> getPatientsDiseases(int id)
    {
        ArrayList<Disease> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "SELECT * FROM diseases WHERE patient_id = " + id + " ORDER BY id DESC", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Disease d = new Disease();
            d.setId(Integer.parseInt(res.getString(0)));
            d.setName(res.getString(1));
            d.setIs_over(res.getString(2));
            d.setPatient_id(Integer.parseInt(res.getString(3)));
            array_list.add(d);

            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<Medicine> getMedicinesForDisease(int id)
    {
        ArrayList<Medicine> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM medicine WHERE disease_id = " + id + "", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Medicine m = new Medicine();
            m.setId(Integer.parseInt(res.getString(0)));
            m.setName(res.getString(1));
            m.setDisease_id(Integer.parseInt(res.getString(2)));
            array_list.add(m);

            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<Disease> getAllDiseases()
    {
        ArrayList<Disease> array_list = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "SELECT * FROM diseases", null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            Disease d = new Disease();
            d.setId(Integer.parseInt(res.getString(0)));
            d.setName(res.getString(1));
            d.setIs_over(res.getString(2));
            d.setPatient_id(Integer.parseInt(res.getString(3)));
            array_list.add(d);

            res.moveToNext();
        }

        return array_list;
    }

    public int numberOfPatientsRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "patients");
        return numRows;
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

    public Integer deletedDiseases (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diseases",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deletedPatientsDiseases (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diseases",
                "patient_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deletedDiseaseMedicines (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("medicine",
                "disease_id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteMedicine (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("medicine",
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
