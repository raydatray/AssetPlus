Feature: Delete Asset (p7)
  As an employee, I wish to delete an asset

  Background: 
    Given the following asset types exist in the system: (p7)
      	| name        | expectedLifeSpan |
      	| lamp        |     20           |
      	| bed         |     35           |
    Given the following assets exist in the system: (p7)
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    	| 3  | lamp 		| 2007-12-27   | 99          | 99         |


  Scenario Outline:  Successfully delete an asset
    When the manager attempts to delete the asset linked to id  "2" (p7)
    Then the asset linked to id "2" shall not exist in the system (p7)
    Then the number of assets in the system shall be "2" (p7)
    Then the following assets shall exist in the system: (p7)
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 3  | lamp 		| 2007-12-27   | 99          | 99         |

  Scenario Outline: Unsuccessfully delete an asset that does not exist in the system
    When the manager attempts to delete the asset linked to id  "4" (p7)
    Then the number of assets in the system shall be "3" (p7)
    Then the following assets shall exist in the system: (p7)
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    	| 3  | lamp 		| 2007-12-27   | 99          | 99         |
    Then the system shall raise the error "The asset with id 4 does not exist in the system" (p7)