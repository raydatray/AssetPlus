package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet6Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static void deleteEmployeeOrGuest(String email) {
    // Input validation for email
    if (email == null 
    || email.contains(" ") 
    || email.indexOf("@") <= 0 // index starts at zero
    || email.indexOf("@") != email.lastIndexOf("@")
    || email.indexOf("@") >= email.lastIndexOf(".") - 1
    || email.lastIndexOf(".") >= email.length() - 1) {
    	throw new IllegalArgumentException("Input email is not a valid email.");
    }

    // Iterate through employeeList and guestList to get emails
    for (Employee e: assetPlus.getEmployees()) {
    	if (e.getEmail() == email) {
			e.delete();
		}
    }

    
    // Are we supposed to throw an error if the input email does not exist in the system? i.e. no employee or guest email has been matched to the input email?
  }

  // returns all tickets
  public static List<TOMaintenanceTicket> getTickets() {
	
	List<TOMaintenanceTicket> TOMaintenanceTicketList = new ArrayList<>();

	for (MaintenanceTicket ticket: assetPlus.getMaintenanceTickets()) {

		// Create a list for the image URLs
		List<String> ticketImageURLs = new ArrayList<>();
		for (TicketImage image: ticket.getTicketImages()) {
			ticketImageURLs.add(image.getImageURL());
		}
		
		// Idk why, but the constructor of TOMaintenanceTicket won't let me use an ArrayList... I think it has something to do with varargs in constructor of the class
		TOMaintenanceNote[] maintenanceNoteList = new TOMaintenanceNote[ticket.getTicketNotes().size()];
		// So forced to loop by index sadly...
		for (int i = 0; i < ticket.getTicketNotes().size(); i++) {
			maintenanceNoteList[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
		}

		// Create a transfer object for each maintenance ticket
		TOMaintenanceTicket transferTicket = new TOMaintenanceTicket(ticket.getId(), ticket.getRaisedOnDate(), ticket.getDescription(), ticket.getTicketRaiser().getEmail(), ticket.getAsset().getAssetType().getName(), ticket.getAsset().getAssetType().getExpectedLifeSpan(), ticket.getAsset().getPurchaseDate(), ticket.getAsset().getFloorNumber(), ticket.getAsset().getRoomNumber(), ticketImageURLs, maintenanceNoteList);

		// Append new transfer object to the list
		TOMaintenanceTicketList.add(transferTicket);
	}

	return TOMaintenanceTicketList;
  }
}