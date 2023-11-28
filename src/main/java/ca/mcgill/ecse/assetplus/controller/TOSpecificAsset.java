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

  //TOSpecificAsset Associations
  private TOAssetType assetType;

  //Helper Variables
  private boolean canSetAssetType;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOSpecificAsset(int aAssetNumber, int aFloorNumber, int aRoomNumber, Date aPurchaseDate, TOAssetType aAssetType)
  {
    assetNumber = aAssetNumber;
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    purchaseDate = aPurchaseDate;
    canSetAssetType = true;
    if (!setAssetType(aAssetType))
    {
      throw new RuntimeException("Unable to create TOSpecificAsset due to aAssetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_GetOne */
  public TOAssetType getAssetType()
  {
    return assetType;
  }
  /* Code from template association_SetUnidirectionalOne */
  private boolean setAssetType(TOAssetType aNewAssetType)
  {
    boolean wasSet = false;
    if (!canSetAssetType) { return false; }
    canSetAssetType = false;
    if (aNewAssetType != null)
    {
      assetType = aNewAssetType;
      wasSet = true;
    }
    return wasSet;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "," +
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null");
  }
}