package com.example.mark.mydoctors.DatabaseOperations;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

}
