package com.example.mark.mydoctors.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mark.mydoctors.DatabaseOperations.DBHelper;
import com.example.mark.mydoctors.Model.Medicine;

import java.util.ArrayList;

/**
 * Created by Mark on 07.10.2017..
 */

public class MedicineDao extends DBHelper {

    public MedicineDao(Context context) {
        super(context);
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
}
