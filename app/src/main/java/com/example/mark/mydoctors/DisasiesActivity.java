package com.example.mark.mydoctors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mark.mydoctors.Adapters.DiseaseAdapter;
import com.example.mark.mydoctors.Model.Disease;
import com.example.mark.mydoctors.dao.DiseaseDao;
import com.example.mark.mydoctors.dao.MedicineDao;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DisasiesActivity extends AppCompatActivity {
    Context context = this;

    DiseaseAdapter arrayAdapter;
    ArrayList array_list;
    private ListView obj;
    DiseaseDao diseaseDao;
    MedicineDao medicineDao;
    int id_pa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disasies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diseaseDao = new DiseaseDao(this);
        medicineDao = new MedicineDao(this);



        final Bundle extras = getIntent().getExtras();
        id_pa = extras.getInt("patients_id");


        array_list = diseaseDao.getPatientsDiseases(id_pa);
        arrayAdapter = new DiseaseAdapter(this,R.layout.disease_content_adapter, array_list);

        obj = (ListView)findViewById(R.id.listViewDisasies);

        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Disease d = (Disease) obj.getItemAtPosition(arg2);

                Intent i = new Intent(getApplicationContext(), MedicinesActivity.class);
                i.putExtra("diseases_id", d.getId());
                startActivity(i);
            }


        });

        obj.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                final Disease d = (Disease) obj.getItemAtPosition(pos);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Želite obrisati " + d.getName() + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "DA",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                medicineDao.deletedDiseaseMedicines(d.getId());
                                diseaseDao.deletedDiseases(d.getId());
                                arrayAdapter.remove(d);
                                arrayAdapter.notifyDataSetChanged();
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



            return true;
        }
    });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.add_disease_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText name = (EditText) promptsView.findViewById(R.id.editTextDialogDiseaseInputName);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(true)
                        .setPositiveButton("DODAJ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                                        Disease d = new Disease();
                                        d.setName(name.getText().toString());
                                        d.setIs_over(currentDateTimeString);
                                        d.setPatient_id(extras.getInt("patients_id"));
                                        diseaseDao.insertDisease(d, extras.getInt("patients_id"));

                                        array_list.clear();
                                        array_list = diseaseDao.getPatientsDiseases(id_pa);
                                        arrayAdapter = new DiseaseAdapter(context, R.layout.disease_content_adapter, array_list);
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
