API Review - skm44/jrf36
=======
# Part 1
### What about your API/design is intended to be flexible?
* Coordinates are designed to be easily extended to multiple dimensions. 
### How is your API/design encapsulating your implementation decisions?
* Encapsulates all location updating logic. 
### How is your part linked to other parts of the project?
* Governs how physics bodies are positioned throughout the game. 
### What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
* If another part of the program uses one more dimension than is supported by the rest of the game, the physics engine will automatically truncate that object to 2 dimensions. 
### Why do you think your API/design is good (also define what your measure of good is)?
* It is shy, and tells the other guy. 
Part 2
### Discuss the use cases/issues that you are responsible for: are they descriptive, appropriate, reasonably sized?
* Most use cases involve how game elements interact with eachother. They are reasonably sized but vital to the game 
### Estimate how long you think each will take and why. What, if anything, makes estimating these tasks uncertain?
* Hard to say how long each will take. The biggest thing will be governing how collisions are handled. 
### What feature/design problem are you most excited to work on?
* Collision handling
### What feature/design problem are you most worried about working on?
* Collision handling
### What major feature do you plan to implement this weekend?
* Basic physics without collisions
# Part 2
### Discuss the use cases/issues that you are responsible for: are they descriptive, appropriate, reasonably sized?
* I am responsible for handling the cases for Physics and Game engine. They are descriptive and reasonably sized, as large features are well split up into smaller cases.
### Estimate how long you think each will take and why. What, if anything, makes estimating these tasks uncertain?
* The physics engine should take a few days of work based on the current state of the repository. The game engine could take longer, since it will take rigorous testing and that could lead to flaws in our design.
### What feature/design problem are you most excited to work on?
* I am most excited about creating the active Forces and velocities on a gameElement and condensing the forces into a single vector, making the next position easy to calculate.
### What feature/design problem are you most worried about working on?
* I am most worried about integrating the physics engine with the game authoring environment so that users can customize the current physics environment. I am unsure how many properties should be customizable.
### What major feature do you plan to implement this weekend?
* I plan to complete the physics engine so that gameElements are able to move and their movements comply with customizable physics rules.