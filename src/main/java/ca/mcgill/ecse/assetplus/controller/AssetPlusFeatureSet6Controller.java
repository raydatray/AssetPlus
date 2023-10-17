package ca.mcgill.ecse.assetplus.controller;

import java.util.List;

public class AssetPlusFeatureSet6Controller {
  private AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
  
  public static void deleteEmployeeOrGuest(String email) {
    if (getEmployeeByEmail(email) == null && getGuestByEmail(email) == null) {
      throw new IllegalArgumentException("Invalid email address");
    } 
    if (email.contains("@ap.com")){
      Employee employee = getEmployeeByEmail(email);
      employee.delete();
    } else {
      Guest guest = getGuestByEmail(email);
      guest.delete();
    }
  }

  // returns all tickets
  public static List<TOMaintenanceTicket> getTickets() {
    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  private static Employee getEmployeeByEmail(String email) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    List<Employee> employees = assetPlus.getEmployees();
    for (Employee e : employees) {
      if (e.getEmail().equals(email)) {
        return e;
      }
    }
    return null;
  }

  private static Guest getGuestByEmail(String email) {
    AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();
    List<Guest> guests = assetPlus.getGuests();
    for (Guest g : guests) {
      if (g.getEmail().equals(email)) {
        return g;
      }
    }
    return null;
  }

}
