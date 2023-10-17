package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

public class AssetPlusFeatureSet5Controller {
  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Constraint 1: imageURL must not be empty or null
    if (imageURL == null || imageURL.trim().isEmpty()){
      return "Image URL is null or empty";
    }

    //Constraint 2: URL must start with http:// or https://

    if (imageURL.substring(0, 6) != "http://" || imageURL.substring(0,7) != "https://"){
      return "Image URL is not valid (Must begin with http:// or https://)";
    }
    
    //TicketID has to be associated with a ticket
    MaintenanceTicket ticketFound = null;

    for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()) {
      if (ticket.getId()  == ticketID){
        ticketFound = ticket;
      }
    }

    if (ticketFound == null) {
      return "TicketID not found";
    }

    //Constraint 3: two imageURLs cannot be the same for a given ticket
    for (TicketImage image: ticketFound.getTicketImages()){
      if (image.getImageURL() == imageURL) {
        return "Duplicate imageURL for given ticket";
      }
    }

    //Add image to ticket
    ticketFound.addTicketImage(imageURL);
    return "imageURL added to ticket";
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {
    //Find if the ticket exists
    MaintenanceTicket ticketFound = null;

    for (MaintenanceTicket ticket : assetPlus.getMaintenanceTickets()){
      if (ticket.getId()  == ticketID){
        ticketFound = ticket;
      }
    }

    if (ticketFound == null) {
      throw new IllegalArgumentException("Ticket does not exist");
    }
    
    for (TicketImage image : ticketFound.getTicketImages()){
      if (image.getImageURL() == imageURL) {
          image.delete();
      }
    }
  }
}
