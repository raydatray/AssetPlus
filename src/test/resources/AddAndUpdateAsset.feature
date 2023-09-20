Feature: Add/Update Asset
  As the manager, I want to add an asset in the system.

  Background:
    Given the following asset types exist in the system:
      	| name        | expectedLifeSpan |
      	| lamp        |     20           |
      	| bed         |     35           |
    Given the following assets exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |

  Scenario Outline: Successfully add an asset
    When the manager attempts to add a new asset to the system with type "<type>", purchaseDate "<purchaseDate>", floorNumber "<floorNumber>", and roomNumber "<roomNumber>"
    Then the number of assets in the system shall be "3"
    Then the asset "<type>" with purchaseDate "<purchaseDate>", floorNumber "<floorNumber>", and roomNumber "<roomNumber>" shall exist in the system
# assume autouniqe id
    Examples: 
      | type	| purchaseDate	| floorNumber | roomNumber |
      | bed	| 2020-06-23	| 2	      | 32	   |
      | lamp 	| 2012-01-01	| 3	      | 78	   |

  Scenario Outline: Unsuccessfully add an asset with a type that does not exist
    When the manager attempts to add a new asset to the system with type "<type>"
    Then the number of assets in the system shall be "2"
    Then the asset with type "<type>", and purchaseDate "<purchaseDate>", floorNumber "<floorNumber>", roomNumber "<roomNumber>" shall not exist in the system
    Then the following asset types shall exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    Then error "<error>" shall be raised 

    Examples: 
      | type       | purchaseDate | floorNumber | roomNumber | error			     |
      | television | 2012-01-01   | 5           | 43	     | The asset type does not exist |

 Scenario Outline: Unsuccessfully add an asset with invalid information
    When the manager attempts to add a new asset to the system with type "<type>"
    Then the number of assets in the system shall be "2"
    Then the asset with type "<type>", and purchaseDate "<purchaseDate>", floorNumber "<floorNumber>", roomNumber "<roomNumber>" shall not exist in the system
    Then the following asset shall exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    Then error "<error>" shall be raised 

    Examples: 
      | id | type       | purchaseDate | floorNumber | roomNumber | error					 |
      | 1  | lamp 	| 2012-01-01   | -5          | 13	     | The floor number shall not be less than 0 |
      | 2  | bed 	| 2012-01-01   | 7           | -4	     | The room number shall not be less than 0	 |


  Scenario Outline: Successfully update all information for an asset
    When the manager attempts to update an asset in the system with type "<type>", purchaseDate "<purchaseDate>", floorNumber "<oldFloorNumber>", and roomNumber "<oldRoomNumber>"
    Then the number of assets in the system shall be "2"
    Then the asset with type "<type>", and purchaseDate "<purchaseDate>", floorNumber "<newFloorNumber>", and roomNumber "<newRoomNumber>" shall exist in the system

    Examples: 
      | id | type	| purchaseDate | oldFloorNumber | oldRoomNumber | newFloorNumber | newRoomNumber |
      | 1  | lamp  	| 2022-03-20   | 9           	| 23         	| 12		 | 10		 |
      | 2  | bed 	| 2010-01-30   | 10          	| 35         	| 9		 | 2		 |

  Scenario Outline: Unsuccessfully update an asset with invalid information
    When the manager attempts to update an asset with type "<type>", purchaseDate "<purchaseDate>", floorNumber "<oldFloorNumber>", and roomNumber "<oldRoomNumber>"
    Then the number of assets in the system shall be "2"
    Then the following assets shall exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    Then the system shall raise the error "<error>"

    Examples: 
      | id | type	| purchaseDate | oldFloorNumber | oldRoomNumber | newFloorNumber | newRoomNumber | error			|
      | 1  | lamp  	| 1999-04-20   | 9           	| 23         	| 12		 | 10		 | purchaseDate is incorrect	|
      | 2  | bed 	| 2010-01-30   | 10          	| 33         	| 9		 | 2		 | Old Room number is incorrect |

  Scenario Outline: Unsuccessfully update an asset that does not exist in the system
    When the manager attempts to update an asset with type "<type>", purchaseDate "<purchaseDate>", floorNumber "<oldFloorNumber>", and roomNumber "<oldRoomNumber>"
    Then the number of assets in the system shall be "2"
    Then the following assets shall exist in the system:
    	| id | type		| purchaseDate | floorNumber | roomNumber |
    	| 1  | lamp	  	| 2022-03-20   | 9           | 23         |
    	| 2  | bed 		| 2010-01-30   | 10          | 35         |
    Then the error "<error>" shall be raised

    Examples: 
      | type	| purchaseDate | oldFloorNumber | oldRoomNumber | newFloorNumber | newRoomNumber | error		 |
      | couch  	| 1999-04-20   | 9           	| 23         	| 12		 | 10		 | Assest does not exist |