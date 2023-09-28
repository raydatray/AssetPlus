/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;
import java.util.*;

// line 48 "../../../../../AssetPlus.ump"
public class Asset
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextAssetNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Asset Attributes
  private Date purchaseDate;
  private Date expectedLifespan;
  private List<MaintenanceTicket> maintenanceHistory;

  //Autounique Attributes
  private int assetNumber;

  //Asset Associations
  private List<MaintenanceTicket> activeTickets;
  private AssetType assetType;
  private Location location;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Asset(Date aPurchaseDate, Date aExpectedLifespan, AssetType aAssetType, Location aLocation)
  {
    purchaseDate = aPurchaseDate;
    expectedLifespan = aExpectedLifespan;
    maintenanceHistory = new ArrayList<MaintenanceTicket>();
    assetNumber = nextAssetNumber++;
    activeTickets = new ArrayList<MaintenanceTicket>();
    boolean didAddAssetType = setAssetType(aAssetType);
    if (!didAddAssetType)
    {
      throw new RuntimeException("Unable to create asset due to assetType. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddLocation = setLocation(aLocation);
    if (!didAddLocation)
    {
      throw new RuntimeException("Unable to create item due to location. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPurchaseDate(Date aPurchaseDate)
  {
    boolean wasSet = false;
    purchaseDate = aPurchaseDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setExpectedLifespan(Date aExpectedLifespan)
  {
    boolean wasSet = false;
    expectedLifespan = aExpectedLifespan;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetMany */
  public boolean addMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    boolean wasAdded = false;
    wasAdded = maintenanceHistory.add(aMaintenanceHistory);
    return wasAdded;
  }

  public boolean removeMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    boolean wasRemoved = false;
    wasRemoved = maintenanceHistory.remove(aMaintenanceHistory);
    return wasRemoved;
  }

  public Date getPurchaseDate()
  {
    return purchaseDate;
  }

  public Date getExpectedLifespan()
  {
    return expectedLifespan;
  }
  /* Code from template attribute_GetMany */
  public MaintenanceTicket getMaintenanceHistory(int index)
  {
    MaintenanceTicket aMaintenanceHistory = maintenanceHistory.get(index);
    return aMaintenanceHistory;
  }

  public MaintenanceTicket[] getMaintenanceHistory()
  {
    MaintenanceTicket[] newMaintenanceHistory = maintenanceHistory.toArray(new MaintenanceTicket[maintenanceHistory.size()]);
    return newMaintenanceHistory;
  }

  public int numberOfMaintenanceHistory()
  {
    int number = maintenanceHistory.size();
    return number;
  }

  public boolean hasMaintenanceHistory()
  {
    boolean has = maintenanceHistory.size() > 0;
    return has;
  }

  public int indexOfMaintenanceHistory(MaintenanceTicket aMaintenanceHistory)
  {
    int index = maintenanceHistory.indexOf(aMaintenanceHistory);
    return index;
  }

  public int getAssetNumber()
  {
    return assetNumber;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getActiveTicket(int index)
  {
    MaintenanceTicket aActiveTicket = activeTickets.get(index);
    return aActiveTicket;
  }

  public List<MaintenanceTicket> getActiveTickets()
  {
    List<MaintenanceTicket> newActiveTickets = Collections.unmodifiableList(activeTickets);
    return newActiveTickets;
  }

  public int numberOfActiveTickets()
  {
    int number = activeTickets.size();
    return number;
  }

  public boolean hasActiveTickets()
  {
    boolean has = activeTickets.size() > 0;
    return has;
  }

  public int indexOfActiveTicket(MaintenanceTicket aActiveTicket)
  {
    int index = activeTickets.indexOf(aActiveTicket);
    return index;
  }
  /* Code from template association_GetOne */
  public AssetType getAssetType()
  {
    return assetType;
  }
  /* Code from template association_GetOne */
  public Location getLocation()
  {
    return location;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfActiveTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public MaintenanceTicket addActiveTicket(MaintenanceTicket.TimeEstimate aTimeEstimate, MaintenanceTicket.PriorityLevel aPriorityLevel, String aDescription, boolean aIsResolved)
  {
    return new MaintenanceTicket(aTimeEstimate, aPriorityLevel, aDescription, aIsResolved, this);
  }

  public boolean addActiveTicket(MaintenanceTicket aActiveTicket)
  {
    boolean wasAdded = false;
    if (activeTickets.contains(aActiveTicket)) { return false; }
    Asset existingBrokenAsset = aActiveTicket.getBrokenAsset();
    boolean isNewBrokenAsset = existingBrokenAsset != null && !this.equals(existingBrokenAsset);
    if (isNewBrokenAsset)
    {
      aActiveTicket.setBrokenAsset(this);
    }
    else
    {
      activeTickets.add(aActiveTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeActiveTicket(MaintenanceTicket aActiveTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aActiveTicket, as it must always have a brokenAsset
    if (!this.equals(aActiveTicket.getBrokenAsset()))
    {
      activeTickets.remove(aActiveTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addActiveTicketAt(MaintenanceTicket aActiveTicket, int index)
  {  
    boolean wasAdded = false;
    if(addActiveTicket(aActiveTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActiveTickets()) { index = numberOfActiveTickets() - 1; }
      activeTickets.remove(aActiveTicket);
      activeTickets.add(index, aActiveTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveActiveTicketAt(MaintenanceTicket aActiveTicket, int index)
  {
    boolean wasAdded = false;
    if(activeTickets.contains(aActiveTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfActiveTickets()) { index = numberOfActiveTickets() - 1; }
      activeTickets.remove(aActiveTicket);
      activeTickets.add(index, aActiveTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addActiveTicketAt(aActiveTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetType(AssetType aAssetType)
  {
    boolean wasSet = false;
    if (aAssetType == null)
    {
      return wasSet;
    }

    AssetType existingAssetType = assetType;
    assetType = aAssetType;
    if (existingAssetType != null && !existingAssetType.equals(aAssetType))
    {
      existingAssetType.removeAsset(this);
    }
    assetType.addAsset(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLocation(Location aLocation)
  {
    boolean wasSet = false;
    if (aLocation == null)
    {
      return wasSet;
    }

    Location existingLocation = location;
    location = aLocation;
    if (existingLocation != null && !existingLocation.equals(aLocation))
    {
      existingLocation.removeItem(this);
    }
    location.addItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=activeTickets.size(); i > 0; i--)
    {
      MaintenanceTicket aActiveTicket = activeTickets.get(i - 1);
      aActiveTicket.delete();
    }
    AssetType placeholderAssetType = assetType;
    this.assetType = null;
    if(placeholderAssetType != null)
    {
      placeholderAssetType.removeAsset(this);
    }
    Location placeholderLocation = location;
    this.location = null;
    if(placeholderLocation != null)
    {
      placeholderLocation.removeItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "assetNumber" + ":" + getAssetNumber()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "purchaseDate" + "=" + (getPurchaseDate() != null ? !getPurchaseDate().equals(this)  ? getPurchaseDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "expectedLifespan" + "=" + (getExpectedLifespan() != null ? !getExpectedLifespan().equals(this)  ? getExpectedLifespan().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetType = "+(getAssetType()!=null?Integer.toHexString(System.identityHashCode(getAssetType())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "location = "+(getLocation()!=null?Integer.toHexString(System.identityHashCode(getLocation())):"null");
  }
}