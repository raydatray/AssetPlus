/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.sql.Date;

// line 43 "../../../../../AssetPlus.ump"
public class Note
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Note Attributes
  private String note;
  private Date date;

  //Note Associations
  private MaintenanceTicket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Note(String aNote, Date aDate, MaintenanceTicket aTicket)
  {
    note = aNote;
    date = aDate;
    boolean didAddTicket = setTicket(aTicket);
    if (!didAddTicket)
    {
      throw new RuntimeException("Unable to create extraNote due to ticket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNote(String aNote)
  {
    boolean wasSet = false;
    note = aNote;
    wasSet = true;
    return wasSet;
  }

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public String getNote()
  {
    return note;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public MaintenanceTicket getTicket()
  {
    return ticket;
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
      existingTicket.removeExtraNote(this);
    }
    ticket.addExtraNote(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    MaintenanceTicket placeholderTicket = ticket;
    this.ticket = null;
    if(placeholderTicket != null)
    {
      placeholderTicket.removeExtraNote(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "note" + ":" + getNote()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}