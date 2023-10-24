package ca.mcgill.ecse.assetplus.controller;

import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet6Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static void deleteEmployeeOrGuest(String email) {
		// Determine if email is an employee email or a guest email
		if (email.endsWith("ap.com")) {
			// Set to null to instantiate the variable
			Employee employeeToDelete =  null;
			
			for (Employee e: assetPlus.getEmployees()) {
				if (e.getEmail().equals(email)) {
					// Initiliaze variable to hold reference of matching employee to avoid throwing
					// an error (removing objects while still iterating through list --> ConcurrentModificationException) 
					employeeToDelete = e;
					// Exit loop once match is found
					break;
				}
			}

			// Check if an employee has been matched... if not do nothing
			if (employeeToDelete != null) {
				employeeToDelete.delete();
			}

		} else {

			Guest guestToDelete = null;

			for (Guest g: assetPlus.getGuests()) {
				if (g.getEmail().equals(email)) {
					guestToDelete = g;
					break;
				}
			}

			if (guestToDelete != null) {
				guestToDelete.delete();
			}
		}
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
			
			// The constructor of TOMaintenanceTicket won't allow the use of an ArrayList... It has something to do with varargs in constructor of the class (unpacking of list...) --> forced to loop by index
			TOMaintenanceNote[] maintenanceNoteList = new TOMaintenanceNote[ticket.getTicketNotes().size()];
			for (int i = 0; i < ticket.getTicketNotes().size(); i++) {
				maintenanceNoteList[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
			}
			// Instantiate other attributes
			int id = ticket.getId();
			Date raisedOnDate = ticket.getRaisedOnDate();
			String description = ticket.getDescription();
			String raisedByEmail = ticket.getTicketRaiser().getEmail();
			String assetName = null;
			int expectedLifeSpanInDays = -1;
			Date purchaseDate = null;
			int floorNumber = -1;
			int roomNumber = -1;

			// If MaintenancetTicket is associated to a SpecificAsset instance, get respective values
			if (ticket.getAsset() != null) {
				assetName = ticket.getAsset().getAssetType().getName();
				expectedLifeSpanInDays = ticket.getAsset().getAssetType().getExpectedLifeSpan();
				purchaseDate = ticket.getAsset().getPurchaseDate();
				floorNumber = ticket.getAsset().getFloorNumber();
				roomNumber = ticket.getAsset().getRoomNumber();
			}
			
			// Create a transfer object for each maintenance ticket
			TOMaintenanceTicket transferTicket = new TOMaintenanceTicket(id, raisedOnDate, description, raisedByEmail, assetName, expectedLifeSpanInDays, purchaseDate, floorNumber, roomNumber, ticketImageURLs, maintenanceNoteList);
			// Append new transfer object to the list
			TOMaintenanceTicketList.add(transferTicket);
		}

		return TOMaintenanceTicketList;
  }
}