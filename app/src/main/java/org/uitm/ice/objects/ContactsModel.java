package org.uitm.ice.objects;

  public class ContactsModel
  {
  String id;
  String name;
  String phone;
  //String status;

  /*
  public ContactsModel(String paramInt, String paramString1, String paramString2)
  {
    this.id = paramInt;
    this.name = paramString1;
    this.phone = paramString2;
    //this.status = paramString3;
  } */
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPhone()
  {
    return this.phone;
  }
  
  /*public String getStatus()
  {
    return this.status;
  } */
  
  public void setId(String paramInt)
  {
    this.id = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }
  /*
  public void setStatus(String paramString)
  {
    this.status = paramString;
  } */
}
