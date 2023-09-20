Feature: Update Manager
  As a manager, I wish to update my personal information linked to my account

  Background: 
    Given the following employee exist in the system
      | email          | password | name | phoneNumber   |
      | jeff@ap.com    | pass1    | Jeff | (555)555-5555 |
      | john@ap.com    | pass2    | John | (444)444-4444 |

  Scenario Outline: A manager updates their info successfully
    When a manager with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>"
    Then their account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>"
    Then the number of employees in the system shall be "2"

    Examples: 
      | email          | newPassword | newName | newEmergencyContact |
      | manager@ap.com | pass5       | Jake    | (111)111-1111       |
      | manager@ap.com | pass1       | Lily    | (111)111-1111       |

  Scenario Outline: A manager updates their email unsuccessfully
    When a manager with "<email>" attempts to update the manager account information to "<newEmail>", "<newPassword>", "<newName>", and "<newEmergencyContact>"
    Then the following "<error>" shall be raised
    Then the manager account information will not be updated and will keep "<email>", "<password>", "<name>", and "<emergencyContact>"
    Then the number of employees in the system is 2

    Examples: 
      | email          | password | name | phoneNumber   | email               | newPassword | newName | newEmergencyContact | error                                |
      | manager@ap.com | pass1    | Lily | (555)555-5555 | evil_manager@ap.com | pass5       | Spot    | (888)888-8888       | You may not change the email address |
