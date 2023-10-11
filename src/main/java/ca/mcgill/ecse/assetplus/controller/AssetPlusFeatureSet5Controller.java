package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.TicketImage;

public class AssetPlusFeatureSet5Controller {
  
  public static String addImageToMaintenanceTicket(String imageURL, int ticketID) {
    
    // Create a new ticket image object with an existing ticket
    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    TicketImage ticketimage = new TicketImage(imageURL, ticket); 

    // add the ticket image to the ticket
    ticket.addTicketImage(ticketimage);

    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

  public static void deleteImageFromMaintenanceTicket(String imageURL, int ticketID) {

    MaintenanceTicket ticket = MaintenanceTicket.getWithId(ticketID);
    
    for (TicketImage image: ticket.getTicketImages()){
      if (image.getImageURL().equals(imageURL)){
        //remove the image from the maintenace ticket
        ticket.removeTicketImage(image);
        //Deletes the image
        image.delete();
      }
    }

    // Remove this exception when you implement this method
    throw new UnsupportedOperationException("Not Implemented!");
  }

}
