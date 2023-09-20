Feature: Delete Equipment
  As a manager, I want to delete existing asset types in the system so that it is no longer available.

  Background: 
    Given the following asset types exist in the system
      | name   | expectedLifeSpan |
      | lamp   |               60 |
      | pillow |               24 |
      | bed    |               96 |

  Scenario: Successfully delete an asset type
    When the manager attempts to delete an asset type in the system with name "lamp"
    Then the number of asset types in the system shall be "3"
    Then an asset type with name "lamp", and expectedLifeSpan "<expectedLifeSpan>" shall not exist in the system
    Then the following asset types shall exist in the system
      | name   | expectedLifeSpan |
      | pillow |               24 |
      | bed    |               96 |

  Scenario Outline: Successful delete an asset type that does not exist in the system
    When the manager attempts to delete an asset type in the system with name "<name>"
    Then the number of asset types in the system shall be "3"
    Then the following asset types shall exist in the system
      | name   | expectedLifeSpan |
      | lamp   |               60 |
      | pillow |               24 |
      | bed    |               96 |

    Examples: 
      | name       |
      | table lamp |
      | desk       |
