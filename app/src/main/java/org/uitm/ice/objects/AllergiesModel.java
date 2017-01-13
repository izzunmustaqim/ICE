package org.uitm.ice.objects;

public class AllergiesModel {

    private String ID, allergies;
    private int type;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAllergies() {
        return allergies;
    }

    public int getType() {
        return type;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setType(int type) {
        this.type = type;
    }
}
