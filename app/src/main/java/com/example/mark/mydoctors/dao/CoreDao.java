package com.example.mark.mydoctors.dao;

import android.content.Context;

import com.example.mark.mydoctors.DatabaseOperations.DBHelper;

/**
 * Created by Mark on 10.10.2017..
 */

public class CoreDao extends DBHelper {

    private Context contextField;

    public CoreDao(Context context)
    {
        super(context);
        contextField = context;
    }

    public PatientDao instantiatePatientDao()
    {
        return new PatientDao(contextField);
    }

    public DiseaseDao instantiateDiseaseDao()
    {
        return new DiseaseDao(contextField);
    }

    public MedicineDao instantiateMedicinenDao()
    {
        return new MedicineDao(contextField);
    }
}
