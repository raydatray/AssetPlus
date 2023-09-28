/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;
import java.sql.Date;

// line 28 "../../../../../AssetPlus.ump"
public class MaintenanceTicket
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TimeEstimate { Lt1Day, OneTo3Days, ThreeTo7Days, OneTo3Weeks, Gt3Weeks }
  public enum PriorityLevel { Urgent, Normal, Low }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static int nextNumber = 1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //MaintenanceTicket Attributes
  private TimeEstimate timeEstimate;
  private PriorityLevel priorityLevel;
  private String description;
  private List<String> pictureURLs;
  private boolean isResolved;
  private boolean needsApproval;
  private boolean managerHasApproved;
  private Date openedOn;

  //Autounique Attributes
  private int number;

  //MaintenanceTicket Associations
  private List<Note> extraNotes;
  private WorkerAccout assignedTo;
  private GuestAccount ticketCreator;
  private Asset brokenAsset;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public MaintenanceTicket(TimeEstimate aTimeEstimate, PriorityLevel aPriorityLevel, String aDescription, boolean aIsResolved, Date aOpenedOn, Asset aBrokenAsset)
  {
    timeEstimate = aTimeEstimate;
    priorityLevel = aPriorityLevel;
    description = aDescription;
    pictureURLs = new ArrayList<String>();
    isResolved = aIsResolved;
    needsApproval = false;
    managerHasApproved = false;
    openedOn = aOpenedOn;
    number = nextNumber++;
    extraNotes = new ArrayList<Note>();
    boolean didAddBrokenAsset = setBrokenAsset(aBrokenAsset);
    if (!didAddBrokenAsset)
    {
      throw new RuntimeException("Unable to create activeTicket due to brokenAsset. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTimeEstimate(TimeEstimate aTimeEstimate)
  {
    boolean wasSet = false;
    timeEstimate = aTimeEstimate;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriorityLevel(PriorityLevel aPriorityLevel)
  {
    boolean wasSet = false;
    priorityLevel = aPriorityLevel;
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
  /* Code from template attribute_SetMany */
  public boolean addPictureURL(String aPictureURL)
  {
    boolean wasAdded = false;
    wasAdded = pictureURLs.add(aPictureURL);
    return wasAdded;
  }

  public boolean removePictureURL(String aPictureURL)
  {
    boolean wasRemoved = false;
    wasRemoved = pictureURLs.remove(aPictureURL);
    return wasRemoved;
  }

  public boolean setIsResolved(boolean aIsResolved)
  {
    boolean wasSet = false;
    isResolved = aIsResolved;
    wasSet = true;
    return wasSet;
  }

  public boolean setNeedsApproval(boolean aNeedsApproval)
  {
    boolean wasSet = false;
    needsApproval = aNeedsApproval;
    wasSet = true;
    return wasSet;
  }

  public boolean setManagerHasApproved(boolean aManagerHasApproved)
  {
    boolean wasSet = false;
    managerHasApproved = aManagerHasApproved;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpenedOn(Date aOpenedOn)
  {
    boolean wasSet = false;
    openedOn = aOpenedOn;
    wasSet = true;
    return wasSet;
  }

  public TimeEstimate getTimeEstimate()
  {
    return timeEstimate;
  }

  public PriorityLevel getPriorityLevel()
  {
    return priorityLevel;
  }

  public String getDescription()
  {
    return description;
  }
  /* Code from template attribute_GetMany */
  public String getPictureURL(int index)
  {
    String aPictureURL = pictureURLs.get(index);
    return aPictureURL;
  }

  public String[] getPictureURLs()
  {
    String[] newPictureURLs = pictureURLs.toArray(new String[pictureURLs.size()]);
    return newPictureURLs;
  }

  public int numberOfPictureURLs()
  {
    int number = pictureURLs.size();
    return number;
  }

  public boolean hasPictureURLs()
  {
    boolean has = pictureURLs.size() > 0;
    return has;
  }

  public int indexOfPictureURL(String aPictureURL)
  {
    int index = pictureURLs.indexOf(aPictureURL);
    return index;
  }

  public boolean getIsResolved()
  {
    return isResolved;
  }

  public boolean getNeedsApproval()
  {
    return needsApproval;
  }

  public boolean getManagerHasApproved()
  {
    return managerHasApproved;
  }

  public Date getOpenedOn()
  {
    return openedOn;
  }

  public int getNumber()
  {
    return number;
  }
  /* Code from template association_GetMany */
  public Note getExtraNote(int index)
  {
    Note aExtraNote = extraNotes.get(index);
    return aExtraNote;
  }

  public List<Note> getExtraNotes()
  {
    List<Note> newExtraNotes = Collections.unmodifiableList(extraNotes);
    return newExtraNotes;
  }

  public int numberOfExtraNotes()
  {
    int number = extraNotes.size();
    return number;
  }

  public boolean hasExtraNotes()
  {
    boolean has = extraNotes.size() > 0;
    return has;
  }

  public int indexOfExtraNote(Note aExtraNote)
  {
    int index = extraNotes.indexOf(aExtraNote);
    return index;
  }
  /* Code from template association_GetOne */
  public WorkerAccout getAssignedTo()
  {
    return assignedTo;
  }

  public boolean hasAssignedTo()
  {
    boolean has = assignedTo != null;
    return has;
  }
  /* Code from template association_GetOne */
  public GuestAccount getTicketCreator()
  {
    return ticketCreator;
  }

  public boolean hasTicketCreator()
  {
    boolean has = ticketCreator != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Asset getBrokenAsset()
  {
    return brokenAsset;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfExtraNotes()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Note addExtraNote(String aNote, Date aDate)
  {
    return new Note(aNote, aDate, this);
  }

  public boolean addExtraNote(Note aExtraNote)
  {
    boolean wasAdded = false;
    if (extraNotes.contains(aExtraNote)) { return false; }
    MaintenanceTicket existingTicket = aExtraNote.getTicket();
    boolean isNewTicket = existingTicket != null && !this.equals(existingTicket);
    if (isNewTicket)
    {
      aExtraNote.setTicket(this);
    }
    else
    {
      extraNotes.add(aExtraNote);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeExtraNote(Note aExtraNote)
  {
    boolean wasRemoved = false;
    //Unable to remove aExtraNote, as it must always have a ticket
    if (!this.equals(aExtraNote.getTicket()))
    {
      extraNotes.remove(aExtraNote);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addExtraNoteAt(Note aExtraNote, int index)
  {  
    boolean wasAdded = false;
    if(addExtraNote(aExtraNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExtraNotes()) { index = numberOfExtraNotes() - 1; }
      extraNotes.remove(aExtraNote);
      extraNotes.add(index, aExtraNote);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveExtraNoteAt(Note aExtraNote, int index)
  {
    boolean wasAdded = false;
    if(extraNotes.contains(aExtraNote))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfExtraNotes()) { index = numberOfExtraNotes() - 1; }
      extraNotes.remove(aExtraNote);
      extraNotes.add(index, aExtraNote);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addExtraNoteAt(aExtraNote, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setAssignedTo(WorkerAccout aAssignedTo)
  {
    boolean wasSet = false;
    WorkerAccout existingAssignedTo = assignedTo;
    assignedTo = aAssignedTo;
    if (existingAssignedTo != null && !existingAssignedTo.equals(aAssignedTo))
    {
      existingAssignedTo.removeOpenTicket(this);
    }
    if (aAssignedTo != null)
    {
      aAssignedTo.addOpenTicket(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setTicketCreator(GuestAccount aTicketCreator)
  {
    boolean wasSet = false;
    GuestAccount existingTicketCreator = ticketCreator;
    ticketCreator = aTicketCreator;
    if (existingTicketCreator != null && !existingTicketCreator.equals(aTicketCreator))
    {
      existingTicketCreator.removeRequest(this);
    }
    if (aTicketCreator != null)
    {
      aTicketCreator.addRequest(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBrokenAsset(Asset aBrokenAsset)
  {
    boolean wasSet = false;
    if (aBrokenAsset == null)
    {
      return wasSet;
    }

    Asset existingBrokenAsset = brokenAsset;
    brokenAsset = aBrokenAsset;
    if (existingBrokenAsset != null && !existingBrokenAsset.equals(aBrokenAsset))
    {
      existingBrokenAsset.removeActiveTicket(this);
    }
    brokenAsset.addActiveTicket(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (extraNotes.size() > 0)
    {
      Note aExtraNote = extraNotes.get(extraNotes.size() - 1);
      aExtraNote.delete();
      extraNotes.remove(aExtraNote);
    }
    
    if (assignedTo != null)
    {
      WorkerAccout placeholderAssignedTo = assignedTo;
      this.assignedTo = null;
      placeholderAssignedTo.removeOpenTicket(this);
    }
    if (ticketCreator != null)
    {
      GuestAccount placeholderTicketCreator = ticketCreator;
      this.ticketCreator = null;
      placeholderTicketCreator.removeRequest(this);
    }
    Asset placeholderBrokenAsset = brokenAsset;
    this.brokenAsset = null;
    if(placeholderBrokenAsset != null)
    {
      placeholderBrokenAsset.removeActiveTicket(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "number" + ":" + getNumber()+ "," +
            "description" + ":" + getDescription()+ "," +
            "isResolved" + ":" + getIsResolved()+ "," +
            "needsApproval" + ":" + getNeedsApproval()+ "," +
            "managerHasApproved" + ":" + getManagerHasApproved()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "timeEstimate" + "=" + (getTimeEstimate() != null ? !getTimeEstimate().equals(this)  ? getTimeEstimate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "priorityLevel" + "=" + (getPriorityLevel() != null ? !getPriorityLevel().equals(this)  ? getPriorityLevel().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openedOn" + "=" + (getOpenedOn() != null ? !getOpenedOn().equals(this)  ? getOpenedOn().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignedTo = "+(getAssignedTo()!=null?Integer.toHexString(System.identityHashCode(getAssignedTo())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticketCreator = "+(getTicketCreator()!=null?Integer.toHexString(System.identityHashCode(getTicketCreator())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "brokenAsset = "+(getBrokenAsset()!=null?Integer.toHexString(System.identityHashCode(getBrokenAsset())):"null");
  }
}