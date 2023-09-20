Feature: Add/Update Asset Type
  As the manager, I want to add an asset type in the system.

  Background: 
    Given the following asset types exist in the system:
      | name   | expectedLifeSpan |
      | lamp   |               60 |
      | pillow |               24 |
      | bed    |               96 |

  Scenario Outline: Successfully add an asset type
    When the manager attempts to add a new asset type to the system with name "<name>", and expectedLifeSpan "<expectedLifeSpan>" months
    Then the number of asset types in the system shall be "4"
    Then the asset type with name "<name>", and expectedLifeSpan "<expectedLifeSpan>" months shall exist in the system

    Examples: 
      | name      | expectedLifeSpan |
      | fridge    |               96 |
      | microwave |               72 |

  Scenario Outline: Unsuccessfully add an asset type with invalid information
    When the manager attempts to add a new asset type to the system with name "<name>", and expectedLifeSpan "<expectedLifeSpan>" month
    Then the number of asset types in the system shall be "3"
    Then the asset type with name "<name>", and expectedLifeSpan "<expectedLifeSpan>" months shall not exist in the system
    Then the system shall raise the error "<error>"

    Examples: 
      | name       | expectedLifeSpan | error                                                 |
      | television |                0 | The weight must be greater than 0                     |
      | television |             -180 | The price per week must be greater than or equal to 0 |
      |            |              180 | The name must not be empty                            |
      | lamp       |               60 | The asset type already exists                         |
      | bed        |               96 | The asset type already exists                         |

  Scenario Outline: Successfully update all information for an asset type
    When the manager attempts to update an asset type in the system with name "<oldName>" to have name "<newName>", and expectedLifeSpan "<newExpectedLifeSpan>"
    Then the number of pieces of equipment in the system shall be "3"
    Then the piece of equipment with name "<oldName>", and expectedLifeSpan "<oldExpectedLifeSpan>" shall not exist in the system
    Then the piece of equipment with name "<newName>", and expectedLifeSpan "<newExpectedLifeSpan>" shall exist in the system

    Examples: 
      | oldName | oldExpectedLifeSpan | newName | newExpectedLifeSpan |
      | lamp    |                  60 | lamp    |                  65 |
      | pillow  |                  24 | closet  |                  70 |
      | bed     |                  96 | pot     |                  10 |

  Scenario Outline: Unsuccessfully update an asset type with invalid information
    When the manager attempts to update an asset type with name "lamp" to have name "<newName>", and expectedLifeSpan "<newExpectedLifeSpan>
    Then the number of asset type in the system shall be "3"
    Then the following asset types shall exist in the system:
      | name   | expectedLifeSpan |
      | lamp   |               60 |
      | pillow |               24 |
      | bed    |               96 |
    Then the system shall raise the error "<error>"

    Examples: 
      | newName    | newExpectedLifeSpan | error                                         |
      | table lamp |                   0 | The expected life span must be greater than 0 |
      | table lamp |                 -30 | The expected life span must be greater than 0 |
      |            |                  30 | The name must not be empty                    |
      | pillow     |                 240 | The asset type already exists                 |

  Scenario Outline: Unsuccessfully update an asset type that does not exist in the system
    When the manager attempts to update an asset type in the system with name "table lamp" to have name "<newName>", and expectedLifeSpan "<newExpectedLifeSpan>"
    Then the number of asset types in the system shall be "3"
    Then the following asset types shall exist in the system:
      | name   | expectedLifeSpan |
      | lamp   |               60 |
      | pillow |               24 |
      | bed    |               96 |
    Then the system shall raise the error "The asset type does not exist"

    Examples: 
      | newName | newExpectedLifeSpan |
      | desk    |                 240 |
