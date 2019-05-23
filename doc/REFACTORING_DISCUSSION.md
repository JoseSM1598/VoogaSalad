Refactoring Discussion
===

-Get rid of magic values - allow the user to input more
-Store other magic values as variables so we only have to change it in one place
-reduce the length of many methods
	-for example, the constructor of the carousel object we created to remove duplicate code was too long
	-can improve this by combining code in the constructor and pulling it out into a separate method that has one single function and is easy to understand as an independent entity
-complicated logic (if statements within loops)
	-we can simplify this significantly to reduce the complexity of the code and make it easier to understand as well as more flexible