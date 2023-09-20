Feature: Add ticket image
  As a hotelstaff, I want to add ticket image to the ticket

  Background: 
    Given the following users exist in the system
      | email        | password | name  | phoneNumber   |
      | jeff@ap.com  | pass1    | Jeff  | (555)555-5555 |
      | smith@ap.com | pass2    | Smith | (555)555-5555 |
    Given the following asset types exist in the system
      | name | expectedLifeSpan |
      | lamp |               20 |
      | bed  |               35 |
    Given the following assets exist in the system
      | id | type | purchaseDate | floorNumber | roomNumber |
      |  1 | lamp |   2022-03-20 |           9 |         23 |
      |  2 | bed  |   2010-01-30 |          10 |         35 |
    Given the following ticket images exist in the system
      | imageUrl                   |
      | https://imageurl.com       |
      | http://thisimage.com/1.png |
    Given the following maintenanceTickets exist in the system
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer | ticketImage                |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com | https://imageurl.com       |
      |  2 | Urgent        | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |    1-3 hours |       1 |             | http://thisimage.com/1.png |

  Scenario Outline: Successfully add ticket image to a ticket
    When the hotelstaff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>"
    Then the image with url "<imageUrl>" shall exist in the system
    Then the ticket with id "<ticketId>" shall have image with url "<imageUrl>"
    Then the number of images in the system shall be "3"

    Examples: 
      | imageUrl                   | ticketId |
      | https://imageurl1.com      |        1 |
      | http://thisimage.com/2.png |        2 |

  Scenario Outline: Successfully add existing ticket image to a ticket
    When the hotelstaff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>"
    Then the image with url "<imageUrl>" shall exist in the system
    Then the ticket with id "<ticketId>" shall have image with url "<imageUrl>"
    Then the number of images in the system shall be "2"

    Examples: 
      | imageUrl                   | ticketId |
      | http://thisimage.com/1.png |        1 |
      | https://imageurl.com       |        2 |

  Scenario Outline: Fail to add ticket image
    When the hotelstaff adds an image with url "<imageUrl>" to the ticket with id "<ticketId>"
    Then the image with url "<imageUrl>" shall not exist in the system
    Then the ticket with id "<ticketId>" shall not have image with url "<imageUrl>"
    Then the number of images in the system shall be "2"
    Then the following "<error>" shall be raised

    Examples: 
      | imageUrl                          | ticketId | error                                |
      | thisisnotanurl                    |        1 | Invalid image url.                   |
      | http://this is not url.com/ 3.png |        2 | Invalid image url.                   |
      | http://thisimage.com/3.png        |        3 | Ticket does not exist in the system. |
      | https://imageurl.com              |        1 | Image already exists for the ticket. |
