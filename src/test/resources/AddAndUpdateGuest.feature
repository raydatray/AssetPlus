Feature: Add/Update Guest
  As an guest, I wish to register an guest account in the system

  Background: 
    Given the following guests exist in the system
      | email          | password | name | phoneNumber   |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |
      | john@gmail.com | pass2    | John | (444)444-4444 |

  Scenario Outline: An guest registers successfully
    When a new guest attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>"
    Then a new guest account shall exist with "<email>", "<password>", "<name>", and "<phoneNumber>"
    Then the number of guest in the system shall be "3"

    Examples: 
      | email          | password | name | emergencyContact |
      | lisa@gmail.com | pass4    | Lisa | (888)888-8888    |
      | liam@yahoo.com | pass5    | Liam | (777)777-7777    |

  Scenario Outline: An guest registers unsuccessfully
    When a new guest attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>"
    Then the following "<error>" shall be raised
    Then the number of guest in the system shall be "2"

    Examples: 
      | email           | password | name | emergencyContact | error                                    |
      | manager@ap.com  | pass1    | Paul | (111)111-1111    | Email cannot be manager@ap.com           |
      | jeff@ap.com     | pass1    | Jeff | (111)111-1111    | Email domain cannot be @ap.com           |
      | jeff@gmail.com  | pass2    | Jeff | (111)777-7777    | Email already linked to an guest account |
      | bart @ ap.com   | pass3    | Bart | (444)666-6666    | Email must not contain any spaces        |
      | dony@gmail@.com | pass4    | Dony | (777)555-7777    | Invalid email                            |
      | kyle@gmail.     | pass5    | Kyle | (666)777-6666    | Invalid email                            |
      | greg.ap@com     | pass6    | Greg | (777)888-7777    | Invalid email                            |
      | @gmail.com      | pass7    | Otto | (111)777-6666    | Invalid email                            |
      | karl@.com       | pass8    | Karl | (111)777-6661    | Invalid email                            |
      |                 | pass9    | Vino | (777)888-5555    | Email cannot be empty                    |
      | luke@gmail.com  |          | Luke | (999)888-5555    | Password cannot be empty                 |
      | owen@gmail.com  | pass10   |      | (888)888-5555    | Name cannot be empty                     |
      | noah@gmail.com  | pass11   | Noah |                  | Phone number cannot be empty             |

  Scenario Outline: An guest updates their info successfully
    When the guest with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>"
    Then their guest account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>"
    Then the number of guest in the system shall be "2"

    Examples: 
      | email          | newPassword | newName | newPhoneNumber |
      | jeff@gmail.com | pass5       | Jake    | (111)111-1111  |
      | john@gmail.com | pass6       | Johnny  | (111)777-7777  |

  Scenario Outline: An guest updates their info unsuccessfully
    When the guest with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>"
    Then the following "<error>" shall be raised
    Then the guest account information will not be updated and will keep "<email>", "<password>", "<name>", and "<phoneNumber>"
    Then the number of guest in the system shall be "2"

    Examples: 
      | email          | password | name | phoneNumber   | newPassword | newName | newPhoneNumber | error                             |
      | jeff@gmail.com | pass1    | Jeff | (555)555-5555 |             | Jeff    | (555)666-5555  | Password cannot be empty          |
      | john@gmail.com | pass2    | John | (444)444-4444 | pass2       |         | (444)444-7777  | Name cannot be empty              |
      | john@gmail.com | pass2    | John | (444)444-4444 | pass2       | John    |                | Emergency contact cannot be empty |
