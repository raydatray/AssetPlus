/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 68 "../../../../../AssetPlus.ump"
public abstract class WorkerAccout extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //WorkerAccout Attributes
  private boolean isMaintenanceStaff;

  //WorkerAccout Associations
  private List<MaintenanceTicket> openTickets;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public WorkerAccout(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
    isMaintenanceStaff = true;
    openTickets = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsMaintenanceStaff(boolean aIsMaintenanceStaff)
  {
    boolean wasSet = false;
    isMaintenanceStaff = aIsMaintenanceStaff;
    wasSet = true;
    return wasSet;
  }

  public boolean getIsMaintenanceStaff()
  {
    return isMaintenanceStaff;
  }
  /* Code from template association_GetMany */
  public MaintenanceTicket getOpenTicket(int index)
  {
    MaintenanceTicket aOpenTicket = openTickets.get(index);
    return aOpenTicket;
  }

  public List<MaintenanceTicket> getOpenTickets()
  {
    List<MaintenanceTicket> newOpenTickets = Collections.unmodifiableList(openTickets);
    return newOpenTickets;
  }

  public int numberOfOpenTickets()
  {
    int number = openTickets.size();
    return number;
  }

  public boolean hasOpenTickets()
  {
    boolean has = openTickets.size() > 0;
    return has;
  }

  public int indexOfOpenTicket(MaintenanceTicket aOpenTicket)
  {
    int index = openTickets.indexOf(aOpenTicket);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOpenTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addOpenTicket(MaintenanceTicket aOpenTicket)
  {
    boolean wasAdded = false;
    if (openTickets.contains(aOpenTicket)) { return false; }
    WorkerAccout existingAssignedTo = aOpenTicket.getAssignedTo();
    if (existingAssignedTo == null)
    {
      aOpenTicket.setAssignedTo(this);
    }
    else if (!this.equals(existingAssignedTo))
    {
      existingAssignedTo.removeOpenTicket(aOpenTicket);
      addOpenTicket(aOpenTicket);
    }
    else
    {
      openTickets.add(aOpenTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOpenTicket(MaintenanceTicket aOpenTicket)
  {
    boolean wasRemoved = false;
    if (openTickets.contains(aOpenTicket))
    {
      openTickets.remove(aOpenTicket);
      aOpenTicket.setAssignedTo(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOpenTicketAt(MaintenanceTicket aOpenTicket, int index)
  {  
    boolean wasAdded = false;
    if(addOpenTicket(aOpenTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpenTickets()) { index = numberOfOpenTickets() - 1; }
      openTickets.remove(aOpenTicket);
      openTickets.add(index, aOpenTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOpenTicketAt(MaintenanceTicket aOpenTicket, int index)
  {
    boolean wasAdded = false;
    if(openTickets.contains(aOpenTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOpenTickets()) { index = numberOfOpenTickets() - 1; }
      openTickets.remove(aOpenTicket);
      openTickets.add(index, aOpenTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOpenTicketAt(aOpenTicket, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while( !openTickets.isEmpty() )
    {
      openTickets.get(0).setAssignedTo(null);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "isMaintenanceStaff" + ":" + getIsMaintenanceStaff()+ "]";
  }
}