Feature: Add/Update Asset
  As a manager, I want to add and update a maintenance ticket in the system.

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
    Given the following maintenanceTickets exist in the system:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate | assetId	| TicketImage | ticketFixer  |
    	| 1  | Low	  	| manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks    | 2		| Dummy URL   | jeff@ams.com |
    	| 2  | Urgent 		| smith@yahoo.com | 2023-07-10   | This is a dummy description 2	| 1-3 hours    | 1		|	      |		     |

# Guests and employees should not add priorite levels not time estimates, only the manager
  Scenario Outline: Successfully add a maintenance ticket to the system by a guest or employee
    When the user with email "<ticketRaiser>" attempts to add a new maintenance ticket to the system with id "<id>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>"
    Then the number of tickets in the system shall be "3"
    Then the ticket with id "<id>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>" shall exist in the system

    Examples:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate 	| assetId	| TicketImage 	|
    	| 3  |	  		| jeff@ams.com 	  | 2023-09-23   | This is a dummy description 1	|     		| 2		|		|
    	| 4  | 			| smith@yahoo.com | 2023-10-05   | This is a dummy description 2	| 	    	| 1		| Dummy URL	|

  Scenario Outline: successfully add a maintenance ticket to the system by a manager
    When the user with email "<ticketRaiser>" attempts to add a new maintenance ticket to the system with id "<id>", priorityLevel  "<priorityLevel>", raisedOnDate "<raisedOnDate>", description "<description>", timeEstimate "<timeEstimate>", and assetId "<assetId>"
    Then the number of tickets in the system shall be "3"
    Then the ticket with id "<id>", priorityLevel "<priorityLevel>", ticketRaiser "<ticketRaiser>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>" shall exist in the system

    Examples:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate 	| assetId	| TicketImage 	|
    	| 3  | Low  		| manager@ams.com | 2023-09-23   | This is a dummy description 1	| 1-3 weeks     | 2		| Dummy URL	|

 Scenario Outline: Unsuccessfully add a maintenance ticket to the system.
    When the user with email "<ticketRaiser>" attempts to add a new maintenance ticket to the system with id "<id>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>"
    Then the number of tickets in the system shall be "2"
    Then the ticket with id "<id>" shall not exist in the system
    Then the following tickets shall exist in the system:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate | assetId	| TicketImage | ticketFixer  |
    	| 1  | Low	  	| manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks    | 2		| Dummy URL   | jeff@ams.com |
    	| 2  | Urgent 		| smith@yahoo.com | 2023-07-10   | This is a dummy description 2	| 1-3 hours    | 1		|	      |		     |
    Then the system shall raise the error "<error>".
    Examples:
    	| id | priorityLevel	| ticketRaiser 	  | raisedOnDate | description 				| timeEstimate 	| assetId	| TicketImage 	| error					|
    	| 3  | Low  		| manager@ams.com | 2023-09-23   | This is a dummy description 1	| 1-3 weeks     | 3		| Dummy URL	| Asset id does not exit in the system. |
    	| 3  |  		| smith@yahoo.com | 2023-09-23   | 					| 1-3 weeks     | 3		| Dummy URL	| Ticket description can not be empty.	|

  Scenario Outline: Successfully update a maintenance ticket information. 
    When the manager attempts to update a maintenance tickey in the system with id "<id>", from priority level "<oldPriorityLevel>" to "<newPriorityLevel>", time estimate from "<oldTimeEstimate>" to "<newTimeEstimate>", ticket image from "<oldTicketImage>" to "<newTicketImage>" and ticketFixer from "<oldTicketFixer>" to "<newTicketFixer>"
    Then the number of ticket in the system shall be "2"
    Then the ticket with id "<id>", priorityLevel "<priorityLevel>", ticketRaiser "<ticketRaiser>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>" shall exist in the system

    Examples:
    	| id | oldPriorityLevel | newPriorityLevel | ticketRaiser    | raisedOnDate | description 			| oldTimeEstimate | newTimeEstimate | assetId	| TicketImage | oldticketFixer | newticketFixer  |
    	| 1  | Low	  	| Urgent	   | manager@ams.com | 2023-07-20   | lamp should be fized ASAP		| 1-3 weeks	  | 1-3 hours	    | 2		| Dummy URL   | jeff@ams.com   | jeff@ams.com    |
    	| 2  | Urgent 		| Urgent	   | smith@yahoo.com | 2023-07-10   | This is not a dummy description	| 1-3 weeks.      | 1-3 hours	    | 1		| Image URL.  |		       | jeff@ams.com    |

  Scenario Outline: Unsuccessfully update a maintenance ticket with invalid information 
    When the manager attempts to update a maintenance tickey in the system with id "<id>", from priority level "<oldPriorityLevel>" to "<newPriorityLevel>", time estimate from "<oldTimeEstimate>" to "<newTimeEstimate>", ticket image from "<oldTicketImage>" to "<newTicketImage>" and ticketFixer from "<oldTicketFixer>" to "<newTicketFixer>"
    Then the number of ticket in the system shall be "2"
    Then the ticket with id "<id>", priorityLevel "<priorityLevel>", ticketRaiser "<ticketRaiser>", raisedOnDate "<raisedOnDate>", description "<description>", and assetId "<assetId>" shall exist in the system
    Then the system shall raise the error "<error>"

    Examples:
    	| id | oldPriorityLevel | newPriorityLevel | ticketRaiser    | raisedOnDate | description 			| oldTimeEstimate | newTimeEstimate | assetId	| TicketImage | oldticketFixer | newticketFixer  | error						|
    	| 3  | Low	  	| Low	   	   | manager@ams.com | 2023-07-20   | This is a dummy description 1	| 1-3 weeks	  | 1-3 weeks	    | 2		| Dummy URL   | jeff@ams.com   | jeff@ams.com    | Maintenance ticket id does not exist in the system	|
    	| 2  | Urgent 		| Urgent	   | smith@yahoo.com | 2023-07-10   | 					| 1-3 weeks.      | 1-3 hours	    | 1		| Image URL.  |		       | jeff@ams.com    | Ticket description can not be empty.			|

 