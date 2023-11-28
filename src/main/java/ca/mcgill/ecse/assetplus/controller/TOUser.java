/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;

// line 42 "../../../../../AssetPlusTransferObjects.ump"
public class TOUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOUser Attributes
  private String email;
  private String name;
  private String phoneNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOUser(String aEmail, String aName, String aPhoneNumber)
  {
    email = aEmail;
    name = aName;
    phoneNumber = aPhoneNumber;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getEmail()
  {
    return email;
  }

  public String getName()
  {
    return name;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "name" + ":" + getName()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}