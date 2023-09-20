Feature: Delete Employee
  As an employee, I wish to delete my employee accounts

  Background: 
    Given the following employees exist in the system
      | email       | password | name | emergencyContact |
      | jeff@ap.com | pass1    | Jeff | (555)555-5555    |
      | john@ap.com | pass2    | John | (444)444-4444    |

  Scenario Outline: Successfully deleting a employee
    When the employee attempts to delete their own account linked to the "<email>"
    Then the employee account linked to the "<email>" shall not exist in the system
    Then the number of guides in the system shall be "<numberOfEmployees>"

    Examples: 
      | email       | numberOfEmployees |
      | jeff@ap.com |                 1 |
      | john@ap.com |                 1 |
      | kyle@ap.com |                 2 |
      | paul@ap.com |                 2 |

  Scenario: Successfully deleting an employee that does not exist but manager exists
    When the employee attempts to delete their own account linked to the "manager@ap.com"
    Then a employee account linked to the "manager@ap.com" shall not exist in the system
    Then a manager account linked to the "manager@ap.com" shall exist in the system
    Then the number of employees in the system shall be "2"
