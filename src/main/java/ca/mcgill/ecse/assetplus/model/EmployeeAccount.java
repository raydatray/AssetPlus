/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 23 "../../../../../AssetPlus.ump"
public class EmployeeAccount extends WorkerAccout
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //EmployeeAccount Associations
  private ManagerAccount managedBy;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public EmployeeAccount(String aEmail, String aPassword, ManagerAccount aManagedBy)
  {
    super(aEmail, aPassword);
    boolean didAddManagedBy = setManagedBy(aManagedBy);
    if (!didAddManagedBy)
    {
      throw new RuntimeException("Unable to create employee due to managedBy. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public ManagerAccount getManagedBy()
  {
    return managedBy;
  }
  /* Code from template association_SetOneToMany */
  public boolean setManagedBy(ManagerAccount aManagedBy)
  {
    boolean wasSet = false;
    if (aManagedBy == null)
    {
      return wasSet;
    }

    ManagerAccount existingManagedBy = managedBy;
    managedBy = aManagedBy;
    if (existingManagedBy != null && !existingManagedBy.equals(aManagedBy))
    {
      existingManagedBy.removeEmployee(this);
    }
    managedBy.addEmployee(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ManagerAccount placeholderManagedBy = managedBy;
    this.managedBy = null;
    if(placeholderManagedBy != null)
    {
      placeholderManagedBy.removeEmployee(this);
    }
    super.delete();
  }

}