package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.persistence.AssetPlusPersistence;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

public class AssetPlusFeatureSet5Controller {
  // Completed by Ray Liu, raydatray on Github

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  /**
   * Adds a TicketImage to a MaintenanceTicket given the MaintenanceTicket's ID
   * @param imageURL The URL of the image to be added
   * @param ticketID The ID of the MaintenanceTicket to add the image to
   * @return A string representing the error message, or an empty string if there were no errors
   */

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Constraint 1: imageURL must not be empty or null
    if (imageURL == null || imageURL.trim().isEmpty()){
      return "Image URL cannot be empty";
    }

    //Constraint 2: URL must start with http:// or https://

    if (!(imageURL.startsWith("http://") || imageURL.startsWith("https://"))){
      return "Image URL must start with http:// or https://";
    }
    
    try {
      //TicketID has to be associated with a ticket
      MaintenanceTicket ticketFound = null;

      for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()) {
        if (ticket.getId()  == ticketID){
          ticketFound = ticket;
        }
      }

      if (ticketFound == null) {
        return "Ticket does not exist";
      }

      //Constraint 3: two imageURLs cannot be the same for a given ticket
      for (TicketImage image: ticketFound.getTicketImages()){
        if (image.getImageURL().equals(imageURL)) {
          return "Image already exists for the ticket";
        }
      }

      //Add image to ticket
      ticketFound.addTicketImage(imageURL);
      
      AssetPlusPersistence.save();
      return "";
    } catch (RuntimeException e) {
      return e.getMessage();
    }

  }

  /**
   * Deletes a TicketImage of a MaintenanceTicket given the MaintenanceTicket's ID
   * @param imageURL The URL of the image to be deleted
   * @param ticketID The ID of the MaintenanceTicket to delete the image from
   */

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {

    try {
      //Find if the ticket exists
      MaintenanceTicket ticketFound = null;

      for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
        if (ticket.getId()  == ticketID){
          ticketFound = ticket;
        }
      }

      if (ticketFound == null) {
        return;
      }

      TicketImage foundImage = null;
      
      for (TicketImage image : ticketFound.getTicketImages()){
        if (image.getImageURL().equals(imageURL)) {
            foundImage = image;
        }
      }

      if (foundImage != null){
        foundImage.delete();
      }
      
      AssetPlusPersistence.save();
    } catch (Exception e) {
      throw e;
    } 
  }
}
