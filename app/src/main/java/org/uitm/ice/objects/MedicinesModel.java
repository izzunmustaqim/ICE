package org.uitm.ice.objects;

/**
 * Created by Izzun Mustaqim on 25/9/2015.
 */
public class MedicinesModel {

    private String ID, medicines;
    private int type;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMedicines() {
        return medicines;
    }

    public int getType() {return type; }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public void setType(int type) {this.type = type;}
}

