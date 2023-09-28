/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 58 "../../../../../AssetPlus.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private int floorNumber;
  private int roomNumber;

  //Location Associations
  private List<Asset> items;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(int aFloorNumber, int aRoomNumber)
  {
    floorNumber = aFloorNumber;
    roomNumber = aRoomNumber;
    items = new ArrayList<Asset>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFloorNumber(int aFloorNumber)
  {
    boolean wasSet = false;
    floorNumber = aFloorNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomNumber(int aRoomNumber)
  {
    boolean wasSet = false;
    roomNumber = aRoomNumber;
    wasSet = true;
    return wasSet;
  }

  public int getFloorNumber()
  {
    return floorNumber;
  }

  public int getRoomNumber()
  {
    return roomNumber;
  }
  /* Code from template association_GetMany */
  public Asset getItem(int index)
  {
    Asset aItem = items.get(index);
    return aItem;
  }

  public List<Asset> getItems()
  {
    List<Asset> newItems = Collections.unmodifiableList(items);
    return newItems;
  }

  public int numberOfItems()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItems()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public int indexOfItem(Asset aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Asset addItem(Date aPurchaseDate, Date aExpectedLifespan, AssetType aAssetType)
  {
    return new Asset(aPurchaseDate, aExpectedLifespan, aAssetType, this);
  }

  public boolean addItem(Asset aItem)
  {
    boolean wasAdded = false;
    if (items.contains(aItem)) { return false; }
    Location existingLocation = aItem.getLocation();
    boolean isNewLocation = existingLocation != null && !this.equals(existingLocation);
    if (isNewLocation)
    {
      aItem.setLocation(this);
    }
    else
    {
      items.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(Asset aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a location
    if (!this.equals(aItem.getLocation()))
    {
      items.remove(aItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(Asset aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(Asset aItem, int index)
  {
    boolean wasAdded = false;
    if(items.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItems()) { index = numberOfItems() - 1; }
      items.remove(aItem);
      items.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (items.size() > 0)
    {
      Asset aItem = items.get(items.size() - 1);
      aItem.delete();
      items.remove(aItem);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "floorNumber" + ":" + getFloorNumber()+ "," +
            "roomNumber" + ":" + getRoomNumber()+ "]";
  }
}