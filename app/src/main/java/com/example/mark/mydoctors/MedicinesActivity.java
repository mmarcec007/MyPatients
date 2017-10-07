package com.example.mark.mydoctors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mark.mydoctors.Adapters.MedicineAdapter;
import com.example.mark.mydoctors.DatabaseOperations.DBHelper;
import com.example.mark.mydoctors.Model.Medicine;

import java.util.ArrayList;

public class MedicinesActivity extends AppCompatActivity {
    Context context = this;
    MedicineAdapter arrayAdapter;
    private ListView obj;
    DBHelper mydb;
    int id_pa = 0;
    int id_pa1 = 0;
    ArrayList array_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mydb = new DBHelper(this);



        final Bundle extras = getIntent().getExtras();
        id_pa = extras.getInt("diseases_id");
        id_pa1 = extras.getInt("patients_id");

        array_list = mydb.getMedicinesForDisease(id_pa);
        arrayAdapter = new MedicineAdapter(this,R.layout.medicine_content_adapter, array_list);

        obj = (ListView)findViewById(R.id.listViewMedicines);

        obj.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
                // TODO Auto-generated method stub
                final Medicine m = (Medicine) obj.getItemAtPosition(arg2);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Želite obrisati " + m.getName() + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "DA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mydb.deleteMedicine(m.getId());
                                arrayAdapter.remove(m);
                            }
                        });

                builder1.setNegativeButton(
                        "NE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_medicine_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText name = (EditText) promptsView.findViewById(R.id.editTextDialogMedicineInputName);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("DODAJ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Medicine m = new Medicine();
                                        m.setName(name.getText().toString());
                                        m.setDisease_id(extras.getInt("diseases_id"));
                                        m.setPatient_id(extras.getInt("patients_id"));
                                        mydb.insertMedicinesForDisease(m, extras.getInt("diseases_id"));


                                        array_list.clear();
                                        array_list = mydb.getMedicinesForDisease(id_pa);
                                        arrayAdapter = new MedicineAdapter(context,R.layout.disease_content_adapter, array_list);
                                        obj.setAdapter(arrayAdapter);
                                        arrayAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        obj.setAdapter(arrayAdapter);
    }
}
