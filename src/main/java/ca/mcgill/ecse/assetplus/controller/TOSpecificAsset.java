/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.controller;
import java.sql.Date;

// line 49 "../../../../../AssetPlusTransferObjects.ump"
public class TOSpecificAsset
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOSpecificAsset Attributes
  private int assetNumber;
  private int floorNumber;
  private int roomNumber;
  private Date purchaseDate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOSpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate)
  {
    assetNumber = aAssetNumber;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    purchaseDate = aPurchaseDate;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getAssetNumber()
  {
    return assetNumber;
  }

  public int getFloorNumber()
  {
    return floorNumber;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "," +
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}