Feature: Update Manager (p1)
  As a manager, I wish to update my personal information linked to my account

  Background: 
    Given the following account exist in the system: (p1)
      | email           | password | name | phoneNumber      |
      | manager@ams.com | pass1    | Lily | (555)555-5555    |
      | jeff@ams.com    | pass1    | Jeff | (555)555-5555    |
      | john@ams.com    | pass2    | John | (444)444-4444    |

  Scenario Outline: A manager updates their info successfully
    When a manager with "<email>" attempts to update their account information to "<newPassword>", "<newName>", and "<newPhoneNumber>" (p1)
    Then their account information will be updated and is now "<email>", "<newPassword>", "<newName>", and "<newPhoneNumber>" (p1)
    Then the number of account in the system is 3 (p1)

    Examples: 
      | email            | newPassword | newName | newEmergencyContact |
      | manager@ams.com  | pass5       | Jake    | (111)111-1111       |
      | manager@ams.com  | pass1       | Lily    | (111)111-1111       |

  Scenario Outline: A manager updates their email unsuccessfully
    When a manager with "<email>" attempts to update the manager account information to "<newEmail>", "<newPassword>", "<newName>", and "<newEmergencyContact>" (p1)
    Then the following "<error>" shall be raised (p1)
    Then the manager account information will not be updated and will keep "<email>", "<password>", "<name>", and "<emergencyContact>" (p1)
    Then the number of guides in the system is 3 (p1)

    Examples: 
      | email            | password    | name    | phoneNumber    | email                 | newPassword | newName | newEmergencyContact | error                                |
      | manager@ams.com  | pass1       |  Lily   | (555)555-5555  | evil_manager@ams.com  | pass5       | Spot    | (888)888-8888       | You may not change the email address |
      