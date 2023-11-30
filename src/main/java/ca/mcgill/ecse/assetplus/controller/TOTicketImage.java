/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;

// line 58 "../../../../../AssetPlusTransferObjects.ump"
public class TOTicketImage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOTicketImage Attributes
  private String imageURL;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTicketImage(String aImageURL)
  {
    imageURL = aImageURL;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getImageURL()
  {
    return imageURL;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "imageURL" + ":" + getImageURL()+ "]";
  }
}