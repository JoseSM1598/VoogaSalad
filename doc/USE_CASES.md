USE_CASES.md
===

### Physics

* A character jumps diagonally
    * Physics engine changes X and Y position of gameElement based on results from:
        * Net Force Calculator
            * Determines downward force (magnitude + direction) that gameElement applies on RigidBody
            * Apply that force to gameElement with opposite direction
        *  Acceleration calculator
            *  Calculate new X and Y positions based gameElement's current momentum vector
* A character moves out of view:
    * Corresponding gameElement is removed from physics engine
    * Physics updates message bus to notify of removed character
    * Game Data creates new gameElement that is a copy of removed gameElement:
        * Decrement life count by 1
        * Reset health and position
        * Game Data updates message bus of new gameElement 
    * Physics adds new gameElement from message bus
    * Physics creates respawn platform (temporary RigidGround) based on new character's position
* Two characters attack each other at the same time:
    * Both characters will be hurt
        * Update Net Force Calculator of new forces
        * Update message bus of decreased health for both
* A character turns around while in mid-air:
    * They will continue with the same momentum, but character will face opposite direction
        * Net Force Calculator and Acceleration Calculator will not change
* A character with multi-jump ability jumps mid-air:
    * The same force will be applied as a normal upward jump
* A character jumps up while directly undernearth a RigidGround:
    * We will have two types of RigidGrounds
        * Impenetrable type
            * RigidBodies will be blocked from jumping into the RigidGround from any direction.
            * The
        * Penetrable type: 
            * RigidBodies will be allowed to phase through the ground from underneath and land on top.
            * Except when landing on top surface of RigidGround, Physics engine will handle movement as if there is no obstacle
* Two characters collide without attacking:
    * They will move past each other
        * Net Force Calculator and Acceleration Calculator will act as if the characters have no obstacle

* Character attacks but doesn't hit anything
    * Input registered and sent to message bus
    * game play class generates the correct attack hitbox
    * hitbox added to collision detection
    * no collision detected -> no force applied

* Player attacks in direction of sprint and hits opponent
    * Input registered and sent to message bus
    * Game play class generates the correct attack hitbox
    * Hitbox added to collision detection
    * Hitbox collision detected 
    * Physics engine calculates force of attack
    * Physics engine calculates impulse on attacker
    * Physics engine calculates net force on attacked game element
    * Physics engine calculates acceleration (change in velocity) of attacker and attacked
    * Game player loops through characters
        * game player applies change in velocity

* Player jumps and lands ontop of opponent
    * Loop through all characters
    * Collision detected between two game elements
    * No physics engine updates due to collision type between two game elements
    * Physics engine calcualtes velocity change due to environment (gravity, air resitance, etc)



### Authoring Environment

* User defined block size and position
    * User can drag-and-drop or click to conveniently place blocks using the render api. These will be stored in the data file of the stage, and will be drawn on the stage screen.
* User chooses custom background image
    * User can select any image on their filesystem from a Dropdown/ImageButton using render api and the image will be copied into the specific game folder.
* User defines behavior for object in groovy console
    * This is going to be rather challenging. However, when the user is presented with the screen to create attacks for characters they are creating, they will be given a little window in to type code (either in java or in groovy). They will be able to define behaviors for that attack, which will be saved in the data along with the attack itself. The API for this will be created using the classloader example from Spike_Voogasalad.
* User defined game physics
    * User can modify fields such as gravity and knockback. This can either be a value the user types or a slider the user can adjust.
* User imports sprite sheet/gif of animation
    * User clicks button on front end to import. On back end, sprite sheet is loaded and divided up into segments based on user input. On front end, animation will be previewable using APIs from the Render Module. 
* User defines moveset for custom character
    * Will be a scrollable list of moves (limited by spritesheet) that also displays default key-bindings. Hopefully each item can have an animated gif. User can select as many items as they want and the list item will be marked with some effect. 
* User defined key inputs/bindings
    * User can right-click move to open new box which allow them to change the key binding to that move.
    * Key Bindings will be assigned using generic names (attack, jump, etc). For example, a special move could be binded to "attack jump down attack". Elsewhere, for each player, the user can assign each action to a key.
* User defines order advancement
    * Ask the user if they want to make one or more levels on the Game Authoring main screen. If more than one level, prompts the user to select/edit/create that many maps and x opponents in each level. In any case, user creates their own character once. 
* User defines ai behavior
    * This is similar to the earlier use case of defining behavior for objects in console. Every computer player will have an API, with the key function being "determineNextMove()". The user will be able to type, in either java or groovy (undecided), their own code for an implementation of the determineNextMove() function.
* User defines custom splash screens/instructions
    * Will be available to modify in the Gameplay editor page. There will be a filechooser for the splash screen background image and a textbox where the user can write a short description and provide keybindings for moves
* User defined audio for each stage/level
    * Will be available to modify in the Gameplay editor page. Communicates with audio system using the message bus. User can select from a set playlist or upload their own music files 


### Data Processor
* User saves a game created through the game editor: 
    * Build and save a formatted game file document
* User loads a game file to play:
    * Parse the game file and send information to other modules so that the game will load properly
* User creates a save while playing a game:
    * Build and save a formatted game save state document
* User loads a game save file while playing a game:
    * Parse the file and send information to other modules to load the state contained in the file properly
* User chooses an incorrectly formatted data file:
    * Throw an error and prompt the user to choose a different file.

### INPUT HANDLER
* User inputs one of their pre-defined combos
    * There will be a timeHandler class that essentially checks 2 things:
        * Did the user just input a sequence of inputs matching one of the pre-defined combos?
        * Did the user input those keys all within a certain time interval of each other?
    * If both of those things are met, the inputSystem will replace the inputs with a new input
      that is the combo
* User inputs another command (attack) before the previous one is done
    * The commands will be placed on a queue so that they are able to be processed even when the user
      inputs commands too fast. However, if a command is in the queue for longer than a specified time interval,
      then the command it dropped. This prevents the user from clicking every key, and the fighter essentially
      performing every command that the user pressed.
* User inputs two keys at once (for example moving sideways while also jumping)
    * There will be a hierarchy of priority for commands to solve this. For example, if the user wants to jump and move sideways, then if Jump has a higher priority, it will be the one to execute
    * If Javafx allows me to be able to handle multiple, simultaneous key inputs, then add them both to the queue instead.
* Conditional moves (Some moves may not be able to be used until lower healths, smash meter)
    * The CombatSystem will hold all of the information that is relevant to the combat. Therefore, whenever a fighter is below a certain treshold, or when their smash meter is full, the combat system will send a Message to the InputHandler that tells it that certain moves are able to be used. 
    * If a fighter uses their final smash, a message will be sent back to the combat system that the fighter just used their final smash and that it needs to be set back to 0. 
* Customizable controllers (user defining key bindings)
    * This is something the user decides when they are authoring the game. 

### AUDIO
* Outputting a sound for a specific attack
    * There will be a player file system wheneve the user loads in a specific fighter. In that file system, there
      will be a sound file for each of their attacks that require sound. 
    * The AudioSystem will simply recieve a message from the InputHandler detailing what key was just pressed,
      and the AudioSystem will find the corresponding .mp3 file and play the sound
* Prioritizing sounds (some may be heard over others)
    * Sounds that are newer will have more priority, but the commands that are have the highest priority are final
      smashes. They essentially stop all other sounds and have a much higher volume than normal attacks
* Stopping all sound immediately (game ends)
    * All of the current media events playing are stopped when the AudioSystem recieves a message from the CombatSystem
      that the game is over


### COMBAT SYSTEM
* User presses jump button three times in a row:
    * The character will double jump once.
    * Inside the combat system, the character's isJumping state which the jump command is executed on is marked as true.
    * The combat system will send a message indicating that the third jump command will do nothing since the character already used his double jump and in air

* User spams attack button:
    * The character's isAttacking state is marked as true. So, only first attack command will be effective and the rest will be invalid and not interrupt the current attack animation. 
    * After a fixed amount of time or determined by the character's attack speed, the character's isAttacking state will be marked as false again so that new attack animation can begin.

* User spams jump or attack button when his character is being hit:
    * The user's character's beingAttacked state will be true
    * The character cannot perform any action while he is being hit
    * Both the jump and attack command will be invalid
    * beingAttacked state will be reset to false if the hit boxes detect that the character is no longer being attacked

* One character hits another character:
    * The health and beingAttacked state of the attacked character will be updated.
    * The combat system will send a message via message bus to tell the frontend to update HUD of health bar
    * If the updated health is below 0, then send a message to indicate that this character is dead

### Scene System 
* User finish selecting their characters and start to play the game.
    * The scene system will turn on physics engine and input handler to for the game loop to use.


