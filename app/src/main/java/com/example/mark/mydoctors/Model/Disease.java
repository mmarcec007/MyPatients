package com.example.mark.mydoctors.Model;

/**
 * Created by Mark on 21.05.2016..
 */

public class Disease {

    private int id;
    private String name;
    private String is_over;
    private int patient_id;

    public Disease() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIs_over() {
        return is_over;
    }

    public void setIs_over(String is_over) {
        this.is_over = is_over;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }
}
