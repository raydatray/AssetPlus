package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;

import java.sql.Date;


// completed by Alice Godbout (aliceg01 on github)

public class AssetPlusFeatureSet7Controller {

 
  /**
   * adds a maintenance note for a given ticket based on given details
   * 
   * @param date date of maintenance note
   * @param description description of maintenance note
   * @param ticketID ID of the maintenance ticket for which the note is being added
   * @param email email of the hotel staff who is adding the note
   * @return string containing error message (empty string if no error)
   */
  public static String addMaintenanceNote(Date date, String description, int ticketID, String email) {
    
    // check if inputs are valid
    var error = isValidMaintenanceNote(date, description, ticketID, email);

    if (!error.isEmpty()) {
        System.out.println(error);
        return error.trim();
    }

    try {
        // find ticket associated with ticket ID; check if there exists a ticket associated to given ticket ID
        MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
        if (ticket == null) {
            error = "Ticket not found. ";
            return error;
        }

        // find note taker associated with email; check if there exists a hotel staff (note taker) associated to given email
        HotelStaff noteTaker = (HotelStaff) HotelStaff.getWithEmail(email);
        if (noteTaker == null) {
            error = "Staff with the provided email not found. ";
            return error;
        }

        // if no errors and ticket, note taker exist, add maintenance note to ticket
        ticket.addTicketNote(date, description, noteTaker); 


    } catch (RuntimeException e) {
        error = e.getMessage();
    }

    return error; // empty string means operation was successful (no error)
    
  }


  /**
   * Updates the date, description and email (associated with hotel staff) of a maintenance note associated with a given ticket at a specific index.
   * 
   * @param ticketID The ID of the maintenance ticket whose note needs to be updated.
   * @param index The index of the maintenance note within the ticket's notes list (0-based).
   * @param newDate The new date to be set for the maintenance note.
   * @param newDescription The new description/content to be set for the maintenance note.
   * @param newEmail The new email of the hotel staff (Note Taker) for the maintenance note.
   * @return string indicating any errors that occurred during the update process (empty if no error)
   * @author Alice Godbout

   */
  public static String updateMaintenanceNote(int ticketID, int index, Date newDate, String newDescription, String newEmail) {
    
    // Input validation - index starts at 0
    var error = isValidMaintenanceNote(newDate, newDescription, ticketID, newEmail);
    if (index < 0) {
        error += "Index must be a positive integer.";
    } if (!error.isEmpty()) {
        System.out.println(error);
        return error.trim();
    }

    try {
      // find ticket associated with ticket ID; check if there exists a ticket associated to given ticket ID
      MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
      if (ticket == null) {
          error = "Ticket not found.";
          return error;
      }
     
      //find note associated with index; check if there exists a note associated to given index
      MaintenanceNote note = ticket.getTicketNote(index);
      if (note == null) {
          error = "Maintenance note at index " + index + " not found. ";
          return error;
      }

      // find note taker associated with email; check if there exists a hotel staff (note taker) associated to given email
      HotelStaff newNoteTaker = (HotelStaff) HotelStaff.getWithEmail(newEmail);
      if (newNoteTaker == null) {
          error = "Staff with the provided new email not found.";
          return error;
      }

      // update date, description and note taker
      note.setDate(newDate);
      note.setDescription(newDescription);
      note.setNoteTaker(newNoteTaker);

    } catch (RuntimeException e) {
      error = e.getMessage();
      
    }
    return error; // empty string means operation was successful (no error)
  }

  /**
   * deletes a maintenance note associated with a given ticket ID at a specific index
   * 
   * @param ticketID ID of maintenance ticket containing note to be deleted
   * @param index index of the maintenance note within the ticket's notes list (starts at 0)
   * @throws IllegalArgumentException if provided ticketID or index is negative
   * @author Alice Godbout
   */
  public static void deleteMaintenanceNote(int ticketID, int index) {
    
    // input validation - index starts at 0
    if (ticketID < 0) {
      throw new IllegalArgumentException("TicketID must be a positive integer.");
    } if (index < 0) {
      throw new IllegalArgumentException("Index must be a positive integer.");
    }

    // find note with given ticketID and index
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
     if (ticket == null) {
         throw new IllegalArgumentException("Ticket not found.");
      }
    
    MaintenanceNote note = ticket.getTicketNote(index);
    if (note == null) {
        throw new IllegalArgumentException("Maintenance note at index " + index + " not found. ");
      }


    // if note exists, delete note
    note.delete();

  }


   /**
  * helper method, validates input details for a maintenance note given the constraints
  *
  * @param date date of maintenance note
  * @param description description of maintenance note
  * @param ticketID ticket ID of maintenance ticket containing maintenance note
  * @param email email address associate to hotel staff
  * @return string containing error message if the maintenance note inputs are invalid (empty string if valid)
  * @author Alice Godbout
  */
  private static String isValidMaintenanceNote(Date date, String description, int ticketID, String email) {
    // input validation
    var error = "";
     if (description == null || description.trim().isEmpty()) {
        error += "Description must not be empty or null ";
    } if (date == null) {
        error += "Date cannot be null.";
    } if (email == null || email.trim().isEmpty()) {
        error += "Email address cannot be empty.";
    }  if (ticketID <= 0) { // Verify
      error += "Ticket ID must be greater than 0";
    }
    return error.trim(); // returns string containing errors
  }

}
  