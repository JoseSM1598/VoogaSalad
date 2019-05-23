Extra Use Cases
===

### Physics
* Player collides with the side of a ground
    * Collision detector generates collisions
    * Collision handler handles generated collisions
    * If the side of a ground collides with a player, no force vectors are added
    * Net force for this instant is appleid to player
* Player attackes while falling
    * Attack body is generated
    * Passive forces added to attack body and player
    * Accelerations generated for player and attack
* Player finishes attack
    * The attack with and ID contained in player is deleted from the PhysicsAttack list
* Player generates attack
    * PhysicsAttack generated from Player's attacks
    * PhysicsAttack added to current list of game elements
    * PhysicsAttack remains for number of steps equal to attack duration
* Player attacks a ground
    * Attack is generated and added to PhysicsObject list
    * Collision detected between attack and ground
    * Collision handler doesnt react to collision with an attack and a ground
* Static Ground undergoes no collisions
    * Ground does not intersect with any other physics object, no collision added
    * Passive gravity acceleration does not effect static ground


### Authoring 

* Creating character animation hit/hurt boxes
    * Allow user to step through animation and draw with provided tool a hitbox and a hurtbox. Allow the user to redraw as necessary, overwriting their last attempt. If a user draws no hitbox, then this is means the user is invulnerable on that frame.

* Program custom behaviors for attacks
    * Implement a groovy console for users to type out behaviors for attacks. Then, these will be stored in data and loaded in when the game is played to apply these custom behaviors.

* Customize box size in stage
    * Have preset options for the size of each block in the stage editor. The user can choose to have larger blocks (and a smaller stage) or smaller blocks and a larger stage. Data will be passed the size of the blocks, whichever preset it is.
    
* Customize key bindings
    * User should be able to type whatever key they desire for the activation of a move. The user should also be able to create key combinations in order to access more moves with a limited amount of controller buttons. The user will define which key maps to a general move (jump, weak attack, strong attack). Then, the user will map those general moves to the move they desire.

* Encapsulate save system
    * When the user presses a save button, automatically determine the file directory to save to and create the file there instead of allowing the user to save anywhere.

* Encapsulate resource options
    * When the user tries to set a resource (background music, background picture), give them a list of items to choose from rather than opening the file chooser.

* Implement loading in an existing character
    * Prompt the user with a list of characters currently stored in the game's location. Have users select a character, and load it in using similar techniques to how game player loads in character data. Be able to modify character's data, and replace the character's XML with an XML of your own.

* Deleting maps
    * If the user clicks the "Delete Map" button, pull up a list of the available maps and allow the user to choose any to delete.

* Deleting characters
    * If the user clicks the "Delete Character" button, pull up a list of the available characters and allow the user to choose any to delete.

### Data Processor

* Store hit/hurt boxes
    * After user inputs hit/hurt box through authoring environment, store the coordinates of each box along with the frame that the box corresponds to.

* Store game state when prompted
    * If the user pauses the game, the user should be able to save and load the current game state, with all of the character's positions, game blocks, and hit and hurt boxes

* Customize parameters
    * If the user chooses a different String to represent a tag or attribute, ensure that the user only has to change this parameter once to implement the change

* Be able to store custom behaviors for animation
    * Use groovy to write custom behaviors, then store them in the character animation file. Then, be able to read them back so game player can access these animations.

### Combat System

* A character lands on the ground after jumping
    * Receives a groundIntersect event from PhysicsEngine
    * Get a list of id of players who are currently on the ground
    * Set this list of player's states to initial