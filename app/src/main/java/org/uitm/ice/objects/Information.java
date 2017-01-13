package org.uitm.ice.objects;

import java.util.Date;

public class Information
{
  String id;
  String name;
  Date dob;
  double height;
  double weight;
  String blood,area;


  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public Date getDOB()
  {
    return this.dob;
  }

  public double getHeight()
  {
    return this.height;
  }

  public double getWeight()
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

  public void setId(String paramInt)
  {
    this.id = paramInt;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setDob(Date paramString)
  {
    this.dob = paramString;
  }

  public void setHeight(Double paramString)
  {
    this.height = paramString;
  }

  public void setWeight(Double paramString)
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

}
