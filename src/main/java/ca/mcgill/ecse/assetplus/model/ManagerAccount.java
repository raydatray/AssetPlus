/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.32.1.6535.66c005ced modeling language!*/

package ca.mcgill.ecse.assetplus.model;
import java.util.*;

// line 16 "../../../../../AssetPlus.ump"
public class ManagerAccount extends WorkerAccout
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static ManagerAccount theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ManagerAccount Attributes
  private String email;
  private String password;

  //ManagerAccount Associations
  private List<EmployeeAccount> employees;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private ManagerAccount(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
    email = "manager@ap.com";
    password = "manager";
    employees = new ArrayList<EmployeeAccount>();
  }

  public static ManagerAccount getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new ManagerAccount();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetMany */
  public EmployeeAccount getEmployee(int index)
  {
    EmployeeAccount aEmployee = employees.get(index);
    return aEmployee;
  }

  public List<EmployeeAccount> getEmployees()
  {
    List<EmployeeAccount> newEmployees = Collections.unmodifiableList(employees);
    return newEmployees;
  }

  public int numberOfEmployees()
  {
    int number = employees.size();
    return number;
  }

  public boolean hasEmployees()
  {
    boolean has = employees.size() > 0;
    return has;
  }

  public int indexOfEmployee(EmployeeAccount aEmployee)
  {
    int index = employees.indexOf(aEmployee);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployees()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public EmployeeAccount addEmployee(String aEmail, String aPassword)
  {
    return new EmployeeAccount(aEmail, aPassword, this);
  }

  public boolean addEmployee(EmployeeAccount aEmployee)
  {
    boolean wasAdded = false;
    if (employees.contains(aEmployee)) { return false; }
    ManagerAccount existingManagedBy = aEmployee.getManagedBy();
    boolean isNewManagedBy = existingManagedBy != null && !this.equals(existingManagedBy);
    if (isNewManagedBy)
    {
      aEmployee.setManagedBy(this);
    }
    else
    {
      employees.add(aEmployee);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployee(EmployeeAccount aEmployee)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployee, as it must always have a managedBy
    if (!this.equals(aEmployee.getManagedBy()))
    {
      employees.remove(aEmployee);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeeAt(EmployeeAccount aEmployee, int index)
  {  
    boolean wasAdded = false;
    if(addEmployee(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeeAt(EmployeeAccount aEmployee, int index)
  {
    boolean wasAdded = false;
    if(employees.contains(aEmployee))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployees()) { index = numberOfEmployees() - 1; }
      employees.remove(aEmployee);
      employees.add(index, aEmployee);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeeAt(aEmployee, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=employees.size(); i > 0; i--)
    {
      EmployeeAccount aEmployee = employees.get(i - 1);
      aEmployee.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}