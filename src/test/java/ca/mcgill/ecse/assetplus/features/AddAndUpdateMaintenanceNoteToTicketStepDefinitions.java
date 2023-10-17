package ca.mcgill.ecse.assetplus.features;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;
import ca.mcgill.ecse.assetplus.model.HotelStaff;
import ca.mcgill.ecse.assetplus.model.MaintenanceNote;
import ca.mcgill.ecse.assetplus.model.MaintenanceTicket;
import ca.mcgill.ecse.assetplus.model.Manager;
import ca.mcgill.ecse.assetplus.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.*;

public class AddAndUpdateMaintenanceNoteToTicketStepDefinitions {

private AssetPlus assetPlus;
private String error;

  @Given("the following employees exist in the system \\(p3)")
  public void the_following_employees_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      assetPlus.addEmployee(row.get("email"), row.get("name"), row.get("password"), row.get("phoneNumber"));
    }
  }

  @Given("the following manager exists in the system \\(p3)")
  public void the_following_manager_exists_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      Manager manager = new Manager(row.get("email"), "", row.get("password"), "", assetPlus);
      assetPlus.setManager(manager);
    }
  }

  @Given("the following asset types exist in the system \\(p3)")
  public void the_following_asset_types_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      assetPlus.addAssetType(row.get("name"), Integer.parseInt(row.get("expectedLifeSpan")));
    }
  }

  @Given("the following assets exist in the system \\(p3)")
  public void the_following_assets_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      assetPlus.addSpecificAsset(Integer.parseInt(row.get("assetNumber")), Integer.parseInt(row.get("floorNumber")), Integer.parseInt(row.get("roomNumber")), Date.valueOf(row.get("purchaseDate")), AssetType.getWithName(row.get("type")));
    }
  }

  @Given("the following tickets exist in the system \\(p3)")
  public void the_following_tickets_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      assetPlus.addMaintenanceTicket(Integer.parseInt(row.get("id")), Date.valueOf(row.get("raisedOnDate")), row.get("description"), User.getWithEmail(row.get("ticketRaiser")));
    }
  }

  @Given("the following notes exist in the system \\(p3)")
  public void the_following_notes_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(row.get("ticketId")));
      maintenanceTicket.addTicketNote(Date.valueOf(row.get("addedOnDate")), row.get("description"), (HotelStaff)HotelStaff.getWithEmail(row.get("noteTaker")));
    }
  }

  @When("hotel staff with email {string} attempts to add a new note with date {string} and description {string} to an existing maintenance ticket {string} \\(p3)")
  public void hotel_staff_with_email_attempts_to_add_a_new_note_with_date_and_description_to_an_existing_maintenance_ticket_p3(
      String userEmail, String addedOnDate, String noteDescription, String noteId) {
    error = AssetPlusFeatureSet7Controller.addMaintenanceNote(Date.valueOf(addedOnDate), noteDescription, Integer.parseInt(noteId), userEmail);
  }

  @When("the manger attempts to update note number {string} for maintenance ticket {string} with note taker {string}, date {string}, and description {string} \\(p3)")
  public void the_manger_attempts_to_update_note_number_for_maintenance_ticket_with_note_taker_date_and_description_p3(
      String noteId, String ticketId, String noteTaker, String dateAdded, String noteDescription) {
    error = AssetPlusFeatureSet7Controller.updateMaintenanceNote(Integer.parseInt(ticketId), Integer.parseInt(noteId), Date.valueOf(dateAdded), noteDescription, noteTaker);
  }

  @Then("the number of notes in the system shall be {string} \\(p3)")
  public void the_number_of_notes_in_the_system_shall_be_p3(String expectedAmountOfNotes) {
    int amountOfNotes = 0;
    for (MaintenanceTicket maintenanceTicket : assetPlus.getMaintenanceTickets()){
      amountOfNotes += maintenanceTicket.getTicketNotes().size();
    }
    assertEquals(Integer.parseInt(expectedAmountOfNotes), amountOfNotes);
  }

  @Then("the number of notes for ticket {string} in the system shall be {string} \\(p3)")
  public void the_number_of_notes_for_ticket_in_the_system_shall_be_p3(String ticketId,
      String expectedAmountOfNotes) {
    MaintenanceTicket maintenanceTicket = assetPlus.getMaintenanceTicket(Integer.parseInt(ticketId));
    assertEquals(Integer.parseInt(expectedAmountOfNotes), maintenanceTicket.getTicketNotes().size());
  }

  @Then("the note number {string} for ticket {int} with noteTaker {string}, date {string}, and description {string} shall exist in the system \\(p3)")
  public void the_note_number_for_ticket_with_note_taker_date_and_description_shall_exist_in_the_system_p3(
      String noteId, Integer ticketId, String noteTaker, String dateAdded, String noteDescription) {

        MaintenanceTicket maintenanceTicket = assetPlus.getMaintenanceTicket(ticketId);
    MaintenanceNote maintenanceNote = maintenanceTicket.getTicketNote(Integer.parseInt(noteId));
    assertNotNull(maintenanceNote);
    assertTrue(noteTaker.equals(maintenanceNote.getNoteTaker().getEmail()));
    assertTrue(dateAdded.equals(maintenanceNote.getDate().toString()));
    assertTrue(noteDescription.equals(maintenanceNote.getDescription()));
  }

  @Then("the following notes shall exist in the system \\(p3)")
  public void the_following_notes_shall_exist_in_the_system_p3(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> row : rows){
      MaintenanceTicket maintenanceTicket = MaintenanceTicket.getWithId(Integer.parseInt(row.get("ticketId")));
      assertNotNull(maintenanceTicket);
      List<MaintenanceNote> maintenanceNotes = maintenanceTicket.getTicketNotes();
      for (MaintenanceNote note : maintenanceNotes){
        assertNotNull(note);
        assertTrue(row.get("noteTaker").equals(note.getNoteTaker().getEmail()));
        assertTrue(row.get("addedOnDate").equals(note.getDate().toString()));
        assertTrue(row.get("description").equals(note.getDescription()));
      }
    }
  }

  @Then("the system shall raise the error {string} \\(p3)")
  public void the_system_shall_raise_the_error_p3(String errorMessage) {
    assertTrue(error.contains(errorMessage));
  }
}
