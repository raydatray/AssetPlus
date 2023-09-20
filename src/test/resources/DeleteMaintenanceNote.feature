Feature: Delete Note
  As a manager, I want to delete a maintenance note for a ticket in the system.

  Background: 
    Given the following emloyees exist in the system
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following manager exist in the system
      | email          | password | name  | phoneNumber   |
      | manager@ap.com | secret   | Tofic | (123)456-7891 |
    Given the following asset types exist in the system
      | name | expectedLifeSpan |
      | lamp |               20 |
      | bed  |               35 |
    Given the following assets exist in the system
      | id | type | purchaseDate | floorNumber | roomNumber |
      |  1 | lamp |   2022-03-20 |           9 |         23 |
      |  2 | bed  |   2010-01-30 |          10 |         35 |
    Given the following maintenanceTickets exist in the system
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com |
      |  2 | Urgent        | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |    1-3 hours |       1 |             |
    Given the following notes exist in the system
      | id | noteTaker      | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com     |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1              |

  Scenario Outline: Successfully delete a note for a maintenance ticket in the system
    When a hotelstaff with email "<noteTaker>" attempts to delete a note "<id>" from an existing maintenance ticket "<ticketId>"
    Then the number of tickets in the system shall be "2"
    Then the note with id "<id>", noteTaker "<noteTaker>", addedOnDate "<date>", description "<description>", and ticketId "<ticketId>" shall not exist in the system

    Examples: 
      | id | ticketId | noteTaker      | date       | description                                |
      |  1 |        2 | eff@ap.com     | 2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 |        1 | smith@ap.com   | 2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 |        1 | manager@ap.com | 2023-09-23 | This is a dummy description 1              |

  Scenario Outline: Unsuccessfully delete a maintenance note from the system.
    When a hotelstaff with email "<noteTaker>" attempts to delete a new note with id "<id>" from from an existing maintenance ticket "<ticketId>"
    Then the number of notes in the system shall be "3"
    Then the note with id "<id>" shall not exist in the system
    Then the following notes shall exist in the system
      | id | noteTaker      | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com     |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1              |
    Then the system shall raise the error "<error>".

    Examples: 
      | id | ticketId | noteTaker      | date       | description                   | error                                |
      |  1 |        2 | dummy@ap.com   | 2023-09-23 | This is a dummy description 1 | User does not exist in the system    |
      |  4 |        1 | eff@ap.com     | 2023-09-23 | This is a dummy description 1 | Note does not exist in the system    |
      |  1 |        9 | manager@ap.com | 2023-10-05 | This is a dummy description 2 | Ticket does not exist in the system. |
