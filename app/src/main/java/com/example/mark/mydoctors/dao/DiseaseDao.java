package com.example.mark.mydoctors.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mark.mydoctors.DatabaseOperations.DBHelper;
import com.example.mark.mydoctors.Model.Disease;

import java.util.ArrayList;

/**
 * Created by Mark on 07.10.2017..
 */

public class DiseaseDao extends DBHelper {

    public DiseaseDao(Context context) {
        super(context);
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

    public Integer deletedDiseases (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diseases",
                "id = ? ",
                new String[] { Integer.toString(id) });
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

    public Integer deletedPatientsDiseases (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diseases",
                "patient_id = ? ",
                new String[] { Integer.toString(id) });
    }
}
