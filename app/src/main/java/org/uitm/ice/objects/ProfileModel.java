package org.uitm.ice.objects;

import java.util.Date;

public class ProfileModel
{
    String ID;
    String name;
    String dob;
    String height;
    String weight,path;
    String blood,area;


    public String getID()
    {
        return this.ID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDOB()
    {
        return this.dob;
    }

    public String getHeight()
    {
        return this.height;
    }

    public String getWeight()
    {
        return this.weight;
    }

    public String getBlood()
    {
        return this.blood;
    }

    public String getArea()
    {
        return this.area;
    }

    public String getPath()
    {
        return this.path;
    }

    public void setID(String paramInt)
    {
        this.ID = paramInt;
    }

    public void setName(String paramString)
    {
        this.name = paramString;
    }

    public void setDob(String paramString)
    {
        this.dob = paramString;
    }

    public void setHeight(String paramString)
    {
        this.height = paramString;
    }

    public void setWeight(String paramString)
    {
        this.weight = paramString;
    }

    public void setBlood(String paramString)
    {
        this.blood = paramString;
    }

    public void setArea(String paramString)
    {
        this.area = paramString;
    }

    public void setPath(String paramString)
    {
        this.area = paramString;
    }

}
