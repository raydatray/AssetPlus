package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;
import ca.mcgill.ecse.assetplus.model.AssetPlus;

public class AssetPlusFeatureSet5Controller {
  // Completed by Ray Liu, raydatray on Github

  private static AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    // Constraint 1: imageURL must not be empty or null
    if (imageURL == null || imageURL.trim().isEmpty()){
      return "Image URL cannot be empty";
    }

    //Constraint 2: URL must start with http:// or https://

    if (!(imageURL.startsWith("http://") || imageURL.startsWith("https://"))){
      return "Image URL must start with http:// or https://";
    }
    
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
    return "";
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

  }
}
