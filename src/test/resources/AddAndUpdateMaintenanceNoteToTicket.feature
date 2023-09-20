Feature: Add/Update Note
  As a manager, I want to add and update a maintenance Note to a ticket in the system.

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
      | id | noteTaker    | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com   |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |

  Scenario Outline: Successfully add a note to a maintenance ticket to the system by an employee
    When an employee with email "<noteTaker>" attempts to add a new note to an existing maintenance ticket "<ticketId>" to the system with id "<id>", added on "<date>" , and description "<description>"
    Then the number of tickets in the system shall be "3"
    Then the note with id "<id>", noteTaker "<noteTaker>", addedOnDate "<date>", description "<description>", and ticketId "<ticketId>" shall exist in the system

    Examples: 
      | id | ticketId | noteTaker    | date       | description                   |
      |  3 |        1 | jeff@ap.com  | 2023-09-23 | This is a dummy description 1 |
      |  3 |        1 | smith@ap.com | 2023-10-05 | This is a dummy description 2 |

  Scenario Outline: Successfully add a note to a maintenance ticket to the system by the manager
    When the manager with email "<noteTaker>" attempts to add a new note to an existing maintenance ticket "<ticketId>" to the system with id "<id>", added on "<date>" , and description "<description>"
    Then the number of notes in the system shall be "3"
    Then the note with id "<id>", noteTaker "<noteTaker>", addedOnDate "<date>", description "<description>", and ticketId "<ticketId>" shall exist in the system

    Examples: 
      | id | ticketId | noteTaker      | date       | description                   |
      |  3 |        1 | manager@ap.com | 2023-09-23 | This is a dummy description 1 |

  Scenario Outline: Unsuccessfully add a maintenance note to the system.
    When a hotelstaff with email "<noteTaker>" attempts to add a new note with id "<id>" to a maintenance ticket "<ticketId>" to the system, addedOn "<addedOnDate>", and description "<description>"
    Then the number of notes in the system shall be "2"
    Then the note with id "<id>" shall not exist in the system
    Then the following notes shall exist in the system
      | id | noteTaker    | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com   |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
    Then the system shall raise the error "<error>"

    Examples: 
      | id | ticketId | noteTaker      | date       | description                   | error                                |
      |  3 |        1 | abdul@ap.com   | 2023-09-23 | This is a dummy description 1 | User does not exist in the system.   |
      |  3 |        3 | manager@ap.com | 2023-10-05 | This is a dummy description 2 | Ticket does not exist in the system. |

  Scenario Outline: Successfully update a note information.
    When a user attempts to update a note in the system with id "<id>", from note taker "<oldNoteTaker>" to "<newNoteTaker>", ticket Id from "<oldTicketId>" to "<newTicketId>", added on date from "<oldAddedOnDate>" to "<newAddedOnDate>" and discription from "<oldDescription>" to "<newDescription>"
    Then the number of notes in the system shall be "2"
    Then the note with id "<id>", note taker "<newNoteTaker>", ticket Id "<newTicketId>", added on date "<newAddedOnDate>", and description "<newDescription>", shall exist in the system

    Examples: 
      | id | oldNoteTaker | newNoteTaker   | oldTicketId | newTicketId | oldAddedOnDate | newAddedOnDate | oldDescription                             | newDescription         |
      |  1 | eff@ap.com   | eff@ap.com     |           2 |           2 |     2023-09-01 |     2023-09-20 | This is a dummy note 1 for a ticket.       | This is new dummy note |
      |  2 | smith@ap.com | manager@ap.com |           1 |           1 |     2023-09-10 |     2023-09-20 | This is another dummy note 2 for a ticket. | This is new dummy note |

  Scenario Outline: Unsuccessfully update a note with invalid information
    When a user attempts to update a note in the system with id "<id>", from note taker "<oldNoteTaker>" to "<newNoteTaker>", ticket Id from "<oldTicketId>" to "<newTicketId>", added on date from "<oldAddedOnDate>" to "<newAddedOnDate>" and discription from "<oldDescription>" to "<newDescription>"
    Then the number of notes in the system shall be "2"
    Then the note with id "<id>" shall not exist in the system
    Then the following notes shall exist in the system
      | id | noteTaker    | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com   |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
    Then the system shall raise the error "<error>"

    Examples: 
      | id | oldNoteTaker   | newNoteTaker   | oldTicketId | newTicketId | oldAddedOnDate | newAddedOnDate | oldDescription                             | newDescription         | error                             |
      |  3 | eff@ap.com     | eff@ap.com     |           2 |           2 |     2023-09-01 |     2023-09-20 | This is a dummy note 1 for a ticket.       | This is new dummy note | Note does not exist in the system |
      |  2 | smith@ap.com   | manager@ap.com |           1 |           1 |     2023-09-10 |     2023-09-20 | This is another dummy note 2 for a ticket. |                        | Description can not be empty      |
      |  1 | manager@ap.com | manager@ap.com |           1 |           1 |     2023-09-10 |     2023-09-20 | This is another dummy note 2 for a ticket. |                        | Old note taker is incorrect       |
