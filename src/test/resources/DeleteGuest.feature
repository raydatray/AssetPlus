Feature: Delete Guest
  As an guest, I wish to delete my guest accounts

  Background: 
    Given the following guests exist in the system
      | email          | password | name | emergencyContact |
      | jeff@yahoo.com | pass1    | Jeff | (555)555-5555    |
      | john@gmail.com | pass2    | John | (444)444-4444    |
    Given the following employees exist in the system
      | email       | password | name | emergencyContact |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555    |
      | john@ap.com | pass2    | John | (444)444-4444    |

  Scenario Outline: Successfully deleting a guest
    When the guest attempts to delete their own account linked to the "<email>"
    Then the guest account linked to the "<email>" shall not exist in the system
    Then the number of guests in the system is "<numOfGuests>"

    Examples: 
      | email          | numOfGuests |
      | jeff@yahoo.com |           1 |
      | john@gmail.com |           1 |
      | kyle@yahoo.com |           2 |
      | paul@yahoo.com |           2 |

  Scenario Outline: Successfully deleting an nonexisting guest but employee exists
    When the guest attempts to delete their own account linked to the "<email>"
    Then a guest account linked to the "<email>" shall not exist in the system
    Then an employee account linked to the "<email>" shall exist in the system
    Then the number of employees in the system shall be "2"
    Then the number of guests in the system shall be "2"

    Examples: 
      | email       |
      | jeff@ap.com |
      | john@ap.com |

  Scenario: Successfully deleting an nonexisting guest but manager exists
    When the guest attempts to delete their own account linked to the "manager@ap.com"
    Then a guest account linked to the "manager@ap.com" shall not exist in the system
    Then a manager account linked to the "manager@ap.com" shall exist in the system
    Then the number of guests in the system shall be "2"
