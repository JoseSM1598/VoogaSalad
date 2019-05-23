API_REVIEW
===


##Part 1
1. What about your API/design is intended to be flexible?
	Much of the API will be flexible. From the editor standpoint, the user will be able to create any attack (melee so far) that they desire, given the proper animation sprite sheet. They will be able to draw hurtboxes for any attack, and set other values, as well as code in several different properties depending on what they desire.
2. How is your API/design encapsulating your implementation decisions?
	The API is deisgned to provide as little information to the public as possible. Most properties are settled within. 
3. How is your part linked to other parts of the project?
	The editor is pretty independent for the most part. However, the editor uses several aspects form the render API. The render API is a standard API many areas in the project use to create boxes, draw windows, etc.
4. What exceptions (error cases) might occur in your part and how will you handle them (or not, by throwing)?
	Exceptions can include whenever the user selects files for images that don't exist or aren't in the relevant directory.
5. Why do you think your API/design is good (also define what your measure of good is)?
	I think it's very good because code stays very shy overall. Most apis are pretty independent of each other, and dependencies are few and far between. Within each API, methods are short and to the point. Of course, much code needs to be cleaned up/written, but the APIs are pretty easy to use.


##Part 2
1. Discuss the use cases/issues that you are responsible for: are they descriptive, appropriate, reasonably sized?
	We are responsible for the editor use cases, all 10 of them. They are quite descriptive and while some of them are more difficult than others, we will make an honest attempt to tackle all of them.
2. Estimate how long you think each will take and why. What, if anything, makes estimating these tasks uncertain?
	I think each one will take a couple of hours of solid work. Most of the challenge is, as always, designing infrastructure that makes other parts easier. However, once we create solid infrastructure, each additional feature will not be too hard.
3. What feature/design problem are you most excited to work on?
	I'm looking forward to implementing custom behaviors with groovy. It seems like a very interesting challenge, and I'm not quite sure how to address it right now. But it'll be amazing once (if) it works.
4. What feature/design problem are you most worried about working on?
	I'm pretty worried about the same feature, to be honest. Most other features I can visualize in my head how exactly (or approximately) to do it, but I have very little idea of what to do for groovy coding. It'll be a fun challenge, however.
5. What major feature do you plan to implement this weekend?
	Finish off the character editor, including ways to set stats for characters, animations, as well as draw hitboxes. Start integrating with data team.