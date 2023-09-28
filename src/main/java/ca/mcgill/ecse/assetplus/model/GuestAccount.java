/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 11 "../../../../../AssetPlus.ump"
public class GuestAccount extends UserAccount
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GuestAccount Associations
  private List<MaintenanceTicket> requests;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GuestAccount(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
    requests = new ArrayList<MaintenanceTicket>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public MaintenanceTicket getRequest(int index)
  {
    MaintenanceTicket aRequest = requests.get(index);
    return aRequest;
  }

  public List<MaintenanceTicket> getRequests()
  {
    List<MaintenanceTicket> newRequests = Collections.unmodifiableList(requests);
    return newRequests;
  }

  public int numberOfRequests()
  {
    int number = requests.size();
    return number;
  }

  public boolean hasRequests()
  {
    boolean has = requests.size() > 0;
    return has;
  }

  public int indexOfRequest(MaintenanceTicket aRequest)
  {
    int index = requests.indexOf(aRequest);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfRequests()
  {
    return 0;
  }
  /* Code from template association_AddManyToOptionalOne */
  public boolean addRequest(MaintenanceTicket aRequest)
  {
    boolean wasAdded = false;
    if (requests.contains(aRequest)) { return false; }
    GuestAccount existingTicketCreator = aRequest.getTicketCreator();
    if (existingTicketCreator == null)
    {
      aRequest.setTicketCreator(this);
    }
    else if (!this.equals(existingTicketCreator))
    {
      existingTicketCreator.removeRequest(aRequest);
      addRequest(aRequest);
    }
    else
    {
      requests.add(aRequest);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeRequest(MaintenanceTicket aRequest)
  {
    boolean wasRemoved = false;
    if (requests.contains(aRequest))
    {
      requests.remove(aRequest);
      aRequest.setTicketCreator(null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addRequestAt(MaintenanceTicket aRequest, int index)
  {  
    boolean wasAdded = false;
    if(addRequest(aRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
      requests.remove(aRequest);
      requests.add(index, aRequest);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveRequestAt(MaintenanceTicket aRequest, int index)
  {
    boolean wasAdded = false;
    if(requests.contains(aRequest))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfRequests()) { index = numberOfRequests() - 1; }
      requests.remove(aRequest);
      requests.add(index, aRequest);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addRequestAt(aRequest, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while( !requests.isEmpty() )
    {
      requests.get(0).setTicketCreator(null);
    }
    super.delete();
  }

}