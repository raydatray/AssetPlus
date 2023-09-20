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
      | http://thisimage.com/2.png |
    Given the following maintenanceTickets exist in the system
      | id | priorityLevel | ticketRaiser   | raisedOnDate | description                   | timeEstimate | assetId | ticketFixer | ticketImage                                     |
      |  1 | Low           | manager@ap.com |   2023-07-20 | This is a dummy description 1 |    1-3 weeks |       2 | jeff@ap.com | https://imageurl.com,http://thisimage.com/2.png |
      |  2 | Urgent        | smith@ap.com   |   2023-07-10 | This is a dummy description 2 |    1-3 hours |       1 |             | http://thisimage.com/1.png,https://imageurl.com |

  Scenario Outline: Successfully delete ticket image from a ticket
    When the hotelstaff deletes the ticket image "<ticketImage>" from the ticket "<ticketId>"
    Then the image with url "<imageUrl>" shall not exist in the system
    Then the ticket with id "<ticketId>" shall not have image with url "<imageUrl>"
    Then the number of images in the system shall be "<num>"

    Examples: 
      | ticketId | ticketImage                | num |
      |        1 | http://thisimage.com/2.png |   2 |
      |        2 | http://thisimage.com/1.png |   2 |
      |        2 | https://notexist.com       |   3 |

  Scenario Outline: Successfully delete ticket image used by multiple tickets
    When the hotelstaff deletes the ticket image "<ticketImage>" from the ticket "<ticketId>"
    Then the image with url "<imageUrl>" shall exist in the system
    Then the ticket with id "<ticketId>" shall not have image with url "<imageUrl>"
    Then the number of images in the system shall be "<num>"

    Examples: 
      | ticketId | ticketImage          | num |
      |        1 | https://imageurl.com |   3 |
      |        2 | https://imageurl.com |   3 |
