Feature: View status of maintenance tickets for a day / employee / manager
  As a hotelstaff, I want to review all maintenance tickets for a specific time. 

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
      |  3 | bed  |   2010-01-30 |           1 |         35 |
    Given the following maintenanceTickets exist in the system
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com |
      |  2 | Urgent        | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |    1-3 hours |       1 |             |
      |  3 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       3 |             |
    Given the following notes exist in the system
      | id | noteTaker      | ticketId | addedOnDate | description                                |
      |  1 | eff@ap.com     |        2 |  2023-09-01 | This is a dummy note 1 for a ticket.       |
      |  2 | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1              |

  Scenario: View all maintenance tickets for a day
    When a hotelstaff attempts to view all maintenance tickets raised on 2023-07-20
    Then the following maintenance tickets should be presented
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com |
      |  3 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       3 |             |
    Then the ticket with id "1" should have the following notes
      | id | noteTaker      | ticketId | addedOnDate | description                                |
      |  2 | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1              |
    Then the ticket with id "3" should have no notes

  Scenario: View empty maintenance tickets for a day
    When a hotelstaff attempts to view all maintenance tickets raised on 2023-07-21
    Then no maintenance tickets should be presented

  Scenario: View all maintenance tickets for an employee
    When a hotelstaff attempts to view all maintenance tickets raised by "smith@ap.com"
    Then the following maintenance tickets should be presented
      | id | priorityLevel | ticketRaiser | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer |
      |  2 | Urgent        | smith@ap.com |   2023-07-10 | This is a dummy description 2 |    1-3 hours |       1 |             |
    Then the ticket with id "2" should have the following notes
      | id | noteTaker  | ticketId | addedOnDate | description                          |
      |  1 | eff@ap.com |        2 |  2023-09-01 | This is a dummy note 1 for a ticket. |

  Scenario: View all maintenance tickets for a manager
    When a hotelstaff attempts to view all maintenance tickets raised by "manager@ap.com"
    Then the following maintenance tickets should be presented
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com |
      |  3 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       3 |             |
    Then the ticket with id "1" should have the following notes
      | id | noteTaker      | ticketId | addedOnDate | description                                |
      |  2 | smith@ap.com   |        1 |  2023-09-10 | This is another dummy note 2 for a ticket. |
      |  3 | manager@ap.com |        1 |  2023-09-23 | This is a dummy description 1              |
    Then the ticket with id "3" should have no notes

  Scenario: View empty maintenance tickets for an employee
    When a hotelstaff attempts to view all maintenance tickets raised by "jeff@ap.com"
    Then no maintenance tickets should be presented

  Scenario: Fail to view maintenance tickets for an invalid employee
    When a hotelstaff attempts to view all maintenance tickets raised by "nonexist@ap.com"
    Then no maintenance tickets should be presented
    Then the system should show an error message "Employee does not exist"
