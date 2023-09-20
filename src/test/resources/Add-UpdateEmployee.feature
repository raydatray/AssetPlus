Feature: Add/Update Employee (p2)
  As an employee, I wish to register an employee account in the system

  Background: 
    Given the following employee exist in the system: (p2)
      | email          | password | name | phoneNumber      |
      | jeff@ams.com   | pass1    | Jeff | (555)555-5555    |
      | john@ams.com   | pass2    | John | (444)444-4444    |

  Scenario Outline: An employee registers successfully
    When a new employee attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p2)
    Then a new employee account shall exist with "<email>", "<password>", "<name>", and "<phoneNumber>" (p2)
    Then the number of employee in the system is 3 (p2)

    Examples: 
      | email        | password | name | emergencyContact |
      | lisa@ams.com | pass4    | Lisa | (888)888-8888    |
      | liam@ams.com | pass5    | Liam | (777)777-7777    |


  Scenario Outline: An employee registers unsuccessfully
    When a new employee attempts to register with "<email>", "<password>", "<name>", and "<phoneNumber>" (p2)
    Then the following "<error>" shall be raised (p2)
    Then the number of employee in the system is 2 (p2)

    Examples: 
      | email             | password | name  | emergencyContact | error                                        |
      | manager@ams.com   | pass1    | Paul  | (111)111-1111    | Email cannot be manager@ams.com              |
      | jeff@ams.com      | pass2    | Jeff  | (111)777-7777    | Email already linked to an employee account  |
      | bart @ ams.com    | pass3    | Bart  | (444)666-6666    | Email must not contain any spaces            |
      | dony@ams@.com     | pass4    | Dony  | (777)555-7777    | Invalid email                                |
      | kyle@ams.         | pass5    | Kyle  | (666)777-6666    | Invalid email                                |
      | greg.ams@com      | pass6    | Greg  | (777)888-7777    | Invalid email                                |
      | @ams.com          | pass7    | Otto  | (111)777-6666    | Invalid email                                |
      | karl@.com         | pass8    | Karl  | (111)777-6661    | Invalid email                                |
      |                   | pass9    | Vino  | (777)888-5555    | Email cannot be empty                        |
      | luke@ams.com      |          | Luke  | (999)888-5555    | Password cannot be empty                     |
      | owen@ams.com      | pass10   |       | (888)888-5555    | Name cannot be empty                         |
      | noah@ams.com      | pass11   | Noah  |                  | Phone number cannot be empty                 |

  Scenario Outline: An employee updates their info successfully
    When the employee with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then their employee account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then the number of employee in the system is 2 (p11)

    Examples: 
      | email          | newPassword | newName | newPhoneNumber |
      | jeff@ams.com | pass5       | Jake    | (111)111-1111  |
      | john@ams.com | pass6       | Johnny  | (111)777-7777  |

  Scenario Outline: An employee updates their info unsuccessfully
    When the employee with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p11)
    Then the following "<error>" shall be raised (p11)
    Then the employee account information will not be updated and will keep "<email>", "<password>", "<name>", and "<phoneNumber>" (p11)
    Then the number of employee in the system is 2 (p11)

    Examples: 
      | email          | password | name | phoneNumber    | newPassword | newName | newPhoneNumber      | error                             |
      | jeff@ams.com | pass1    | Jeff | (555)555-5555    |             | Jeff    | (555)666-5555       | Password cannot be empty          |
      | john@ams.com | pass2    | John | (444)444-4444    | pass2       |         | (444)444-7777       | Name cannot be empty              |
      | john@ams.com | pass2    | John | (444)444-4444    | pass2       | John    |                     | Emergency contact cannot be empty |
