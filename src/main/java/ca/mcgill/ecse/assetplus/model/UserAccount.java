/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;

// line 3 "../../../../../AssetPlus.ump"
public abstract class UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String name;
  private String email;
  private String password;
  private int phoneNumber;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aEmail, String aPassword)
  {
    name = null;
    email = aEmail;
    password = aPassword;
    phoneNumber = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(int aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }

  public int getPhoneNumber()
  {
    return phoneNumber;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "]";
  }
}