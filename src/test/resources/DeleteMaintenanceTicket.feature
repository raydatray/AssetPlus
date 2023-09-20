Feature: Delete Maintenance Ticket
  As a manager, I wish to delete a maintenance ticket

  Background:
    Given the following users exist in the system:
      | email             | password | name  | phoneNumber      |
      | jeff@ams.com      | pass1    | Jeff  | (555)555-5555    |
      | smith@yahoo.com   | pass2    | Smith | (555)555-5555    |
    Given the following asset types exist in the system:
      	| name        | expectedLifeSpan |
      	| lamp        |     20           |
      	| bed         |     35           |
    Given the following assets exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         | 
    Given the following maintenanceTickets exist in the system: (p8)
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate | assetId	| TicketImage | ticketFixer  |
    	| 1  | Low	  	| manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks    | 2		| Dummy URL   | jeff@ams.com |
    	| 2  | Urgent 		| smith@yahoo.com | 2023-07-10   | This is a dummy description 2	| 1-3 hours    | 1		|	      |		     |


  Scenario Outline:  Successfully delete a maintenance ticket
    When the manager attempts to delete the maintenance ticket linked to id  "2"
    Then the maintenance ticket linked to id "2" shall not exist in the system
    Then the number of maintenance tickets in the system shall be "1"
    Then the following assets shall exist in the system:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate | assetId	| TicketImage | ticketFixer  |
    	| 1  | Low	  	| manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks    | 2		| Dummy URL   | jeff@ams.com |

  Scenario Outline: Unsuccessfully delete an  a maintenance ticket does not exist in the system
    When the manager attempts to delete the maintenance ticket linked to id  "3"
    Then the number of tickets in the system shall be "2"
    Then the following tickets shall exist in the system:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate | assetId	| TicketImage | ticketFixer  |
    	| 1  | Low	  	| manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks    | 2		| Dummy URL   | jeff@ams.com |
    	| 2  | Urgent 		| smith@yahoo.com | 2023-07-10   | This is a dummy description 2	| 1-3 hours    | 1		|	      |		     |
    Then the system shall raise the error "The maintenance ticket with id 3 does not exist in the system"