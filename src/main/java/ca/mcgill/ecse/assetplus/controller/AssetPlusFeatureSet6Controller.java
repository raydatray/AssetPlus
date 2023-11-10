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
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;

public class AssetPlusFeatureSet6Controller {

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

	/**
	 * Deletes an employee or guest account based on the provided email address.
	 *
	 * @param email The email address associated with the account to be deleted.
	 *
	 * This function determines whether the given email corresponds to an employee or a guest account.
	 * If the email ends with "ap.com," it is considered an employee email. Otherwise, it is assumed
	 * to be a guest email. If it is a guest email, the function searches for a matching account based 
	 * on the email provided. If a match is found, the corresponding account is deleted. If no match
	 * is found, the function does not do anything.
	 *
	 * Note: Deleting objects from a list while iterating through it can lead to a
	 * ConcurrentModificationException. To prevent this, the function initializes a variable to
	 * store the reference to the matching account and breaks out of the loop as soon as a match is found.
	 * If no match is found, no action is taken.
	 */
  public static void deleteEmployeeOrGuest(String email) {

		try {
			if (email.endsWith("ap.com")) {
			
			Employee employeeToDelete =  null;
			
			for (Employee e: assetPlus.getEmployees()) {
				if (e.getEmail().equals(email)) {
					employeeToDelete = e;
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
			AssetPlusPersistence.save();
		} catch (Exception e) {
			throw e;
		}

  }

	/**
	 * @author Alexander Liu and Alice Godbout

	 * Retrieves a list of transfer objects (TOMaintenanceTicket) representing maintenance tickets in the system.
	 *
	 * This method processes the maintenance tickets in the system and creates corresponding transfer objects
	 * to encapsulate ticket details for external use. Each TOMaintenanceTicket contains information about the ticket,
	 * including its ID, raised date, description, and associated data such as asset information, ticket images,
	 * and maintenance notes. The method handles different attributes and associations, initializing them as needed.
	 *
	 * If a maintenance ticket is associated with a specific asset, additional asset-related information is included.
	 *
	 * @return A list of TOMaintenanceTicket objects, representing the maintenance tickets in the system.
	 *
	 * Note: The constructor of TOMaintenanceTicket does not accept an ArrayList for certain attributes due to varargs.
	 * As a result, the method iterates through maintenance notes to create the maintenanceNoteList.
	 */
  public static List<TOMaintenanceTicket> getTickets() {
	
		List<TOMaintenanceTicket> TOMaintenanceTicketList = new ArrayList<>();

		try {

			for (MaintenanceTicket ticket: assetPlus.getMaintenanceTickets()) {

			// Create a list for the image URLs
			List<String> ticketImageURLs = new ArrayList<>();
			for (TicketImage image: ticket.getTicketImages()) {
				ticketImageURLs.add(image.getImageURL());
			}
			
			// The constructor of TOMaintenanceTicket won't allow the use of an ArrayList... It has something to do with varargs in constructor of the class (unpacking of list...) --> forced to loop by index
			TOMaintenanceNote[] allNotes = new TOMaintenanceNote[ticket.getTicketNotes().size()];
			for (int i = 0; i < ticket.getTicketNotes().size(); i++) {
				allNotes[i] = new TOMaintenanceNote(ticket.getTicketNote(i).getDate(), ticket.getTicketNote(i).getDescription(), ticket.getTicketNote(i).getNoteTaker().getEmail());
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

			String fixedByEmail;
			String timeToResolve;
			String priority;
			boolean approvalRequired;

			String status = ticket.getStatusFullName();
			if (status.equals("Open")){
			fixedByEmail = "";
			timeToResolve = "";
			priority = "";
			approvalRequired = false;
			}
			else{
			fixedByEmail = ticket.getTicketFixer().getEmail();
			timeToResolve = ticket.getTimeToResolve().toString();
			priority = ticket.getPriority().toString();
			approvalRequired = ticket.hasFixApprover();}

			// If MaintenancetTicket is associated to a SpecificAsset instance, get respective values
			if (ticket.getAsset() != null) {
				assetName = ticket.getAsset().getAssetType().getName();
				expectedLifeSpanInDays = ticket.getAsset().getAssetType().getExpectedLifeSpan();
				purchaseDate = ticket.getAsset().getPurchaseDate();
				floorNumber = ticket.getAsset().getFloorNumber();
				roomNumber = ticket.getAsset().getRoomNumber();
				
			}
			
			// Create a transfer object for each maintenance ticket			
			TOMaintenanceTicket transferTicket = new TOMaintenanceTicket(
    	id, raisedOnDate, description, raisedByEmail, status, fixedByEmail, 
   		timeToResolve, priority, approvalRequired, assetName, expectedLifeSpanInDays, 
   		purchaseDate, floorNumber, roomNumber, ticketImageURLs, allNotes);

			TOMaintenanceTicketList.add(transferTicket);
			}

			AssetPlusPersistence.save();
		} catch (Exception e) {
			throw e;
		}
		return TOMaintenanceTicketList;
  }
}