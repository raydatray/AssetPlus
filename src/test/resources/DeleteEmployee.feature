Feature: Delete Employee
  As an employee, I wish to delete my employee accounts

  Background: 
    Given the following employees exist in the system:
      | email        | password | name | emergencyContact |
      | jeff@ams.com | pass1    | Jeff | (555)555-5555      |
      | john@ams.com | pass2    | John | (444)444-4444      |

  Scenario Outline: Attempting to delete a employee
    When the employee attempts to delete their own account linked to the "<email>"
    Then the employee account linked to the "<email>" shall not exist in the system
    Then the number of guides in the system is "<numberOfEmployees>"

    Examples: 
      | email        | numberOfEmployees |
      | jeff@ams.com |                 1 |
      | john@ams.com |                 1 |
      | kyle@ams.com |                 2 |
      | paul@ams.com |                 2 |

  Scenario Outline: Attempting to delete an employee that does not exist
    When the employee account linked to the "<email>" attemps to be deleted
    Then the number of guides in the system is 2

    Examples: 
      | email             |
      | person1@ams.com   |
      | person2@ams.com   |
