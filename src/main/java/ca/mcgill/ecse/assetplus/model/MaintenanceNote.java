/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 46 "../../../../../AssetPlus.ump"
public class MaintenanceNote
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, MaintenanceNote> maintenancenotesById = new HashMap<Integer, MaintenanceNote>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceNote Attributes
  private int id;
  private Date date;
  private String description;

  //MaintenanceNote Associations
  private AssetPlus assetPlus;
  private HotelStaff noteTaker;
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceNote(int aId, Date aDate, String aDescription, AssetPlus aAssetPlus, HotelStaff aNoteTaker, MaintenanceTicket aTicket)
  {
    date = aDate;
    description = aDescription;
    if (!setId(aId))
    {
      throw new RuntimeException("Cannot create due to duplicate id. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddAssetPlus = setAssetPlus(aAssetPlus);
    if (!didAddAssetPlus)
    {
      throw new RuntimeException("Unable to create maintenanceNote due to assetPlus. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddNoteTaker = setNoteTaker(aNoteTaker);
    if (!didAddNoteTaker)
    {
      throw new RuntimeException("Unable to create maintenanceNote due to noteTaker. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create ticketNote due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(int aId)
  {
    boolean wasSet = false;
    Integer anOldId = getId();
    if (anOldId != null && anOldId.equals(aId)) {
      return true;
    }
    if (hasWithId(aId)) {
      return wasSet;
    }
    id = aId;
    wasSet = true;
    if (anOldId != null) {
      maintenancenotesById.remove(anOldId);
    }
    maintenancenotesById.put(aId, this);
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setDescription(String aDescription)
  {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public int getId()
  {
    return id;
  }
  /* Code from template attribute_GetUnique */
  public static MaintenanceNote getWithId(int aId)
  {
    return maintenancenotesById.get(aId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithId(int aId)
  {
    return getWithId(aId) != null;
  }

  public Date getDate()
  {
    return date;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template association_GetOne */
  public AssetPlus getAssetPlus()
  {
    return assetPlus;
  }
  /* Code from template association_GetOne */
  public HotelStaff getNoteTaker()
  {
    return noteTaker;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getTicket()
  {
    return ticket;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAssetPlus(AssetPlus aAssetPlus)
  {
    boolean wasSet = false;
    if (aAssetPlus == null)
    {
      return wasSet;
    }

    AssetPlus existingAssetPlus = assetPlus;
    assetPlus = aAssetPlus;
    if (existingAssetPlus != null && !existingAssetPlus.equals(aAssetPlus))
    {
      existingAssetPlus.removeMaintenanceNote(this);
    }
    assetPlus.addMaintenanceNote(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setNoteTaker(HotelStaff aNoteTaker)
  {
    boolean wasSet = false;
    if (aNoteTaker == null)
    {
      return wasSet;
    }

    HotelStaff existingNoteTaker = noteTaker;
    noteTaker = aNoteTaker;
    if (existingNoteTaker != null && !existingNoteTaker.equals(aNoteTaker))
    {
      existingNoteTaker.removeMaintenanceNote(this);
    }
    noteTaker.addMaintenanceNote(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTicket(MaintenanceTicket aTicket)
  {
    boolean wasSet = false;
    if (aTicket == null)
    {
      return wasSet;
    }

    MaintenanceTicket existingTicket = ticket;
    ticket = aTicket;
    if (existingTicket != null && !existingTicket.equals(aTicket))
    {
      existingTicket.removeTicketNote(this);
    }
    ticket.addTicketNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    maintenancenotesById.remove(getId());
    AssetPlus placeholderAssetPlus = assetPlus;
    this.assetPlus = null;
    if(placeholderAssetPlus != null)
    {
      placeholderAssetPlus.removeMaintenanceNote(this);
    }
    HotelStaff placeholderNoteTaker = noteTaker;
    this.noteTaker = null;
    if(placeholderNoteTaker != null)
    {
      placeholderNoteTaker.removeMaintenanceNote(this);
    }
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeTicketNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "description" + ":" + getDescription()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assetPlus = "+(getAssetPlus()!=null?Integer.toHexString(System.identityHashCode(getAssetPlus())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "noteTaker = "+(getNoteTaker()!=null?Integer.toHexString(System.identityHashCode(getNoteTaker())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}