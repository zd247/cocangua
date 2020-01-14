# cocangua
RMIT University Vietnam
Course: INTE2512 Object-Oriented Programming
Semester: 2019C
Assessment name: Final Project
Team name: Team 5
Team members: 
- Tran Kim Bao: S3740819
- Phan Quoc Binh
- 
-
=

1. INTRODUCTION
One short paragraph to describe what this software is about.
2. FEATURES
List the implemented features of the software in point-form.
3. DESIGN
Describe major design decisions and major algorithms.
4. INSTALLATION
Provide instructions on how to install and run the software.
5. KNOWN BUGS
List the unfixed bugs and the workarounds, if any.
6. ACKNOWLEDGEMENT
List the resources and help that you used to complete this project.Failing to do so might be considered as plagiarism.


Pseudocode for Machine Player:
Machine algorithm:

+ How to win this game: 
- After researching, there is a simple way to win this game easily. More specifically, firstly, after deploying the first piece, you should let it move until there is a block, after that, if there is a block, move to use another piece. Then, whenever the piece is at home,
try to let it move to the highest position if it is possible. Therefore, this machine player is just applied the same logic with what we found out. 


+ Logic:
- While the turn is not done yet
	- For each piece in the Piece list of the nest
		- Take that piece 
		- Use dice 1 by 1 by the order, and store its value to "current dice value", dice turn 1 store the dice 1's value, dice turn 2 store the dice 2's value
		- If the piece's step is lower than 48
			- If the piece both not be able to move and not be able to kick with the first dice, and there is the first turn dice
				- If they can do 1 out of 2 actions above with the second dice	
					- Change the "current dice value" from dice 1 to dice 2
					- swap the value of dice 1 and dice 2 (next turn dice will use the value of dice 1)
			- If the piece is not blocked or the piece is able to kick
				- If there is able to kick condition
					- Check for the destination and put that enemy's piece to its nest
				- If (there is not a piece which is not deployed yet) or (there is a piece (is not deployed yet) and the "current dice value" is 6 and there is the second dice turn) or (there is a 6 (dice 1 or 2) and the first dice turn)
					- if there is a piece is not deployed yet
						- if there is the first dice turn and the "current dice value"(store value of dice 1) is not 6 and the second dice is 6
							- the "current dice value" will store the value of dice 2
							- swap value of 2 dices (next turn dice will use the value of dice 1)
					- If the piece's step + current dice value <= 48 (means not pass the home arrival)
						- Move piece to its destination
					- else the piece will not move
				- If the piece cannot move, keep that current dice value for another piece in nest to move
		- If the piece's step is higher than 47 and its step if lower than 55 which is maximum step of each piece and (the piece is not blocked with the current dice value or (piece is not blocked by using dice 2 and dice turn is 1))
			- If there is the first dice turn and the piece is not blocked by dice2 and (the piece is blocked with dice 1 or dice2 > dice1)
				- the "current dice value" will store the value of dice 2
				- swap value of 2 dices (next turn dice will use the value of dice 1)
			- move the piece
		- If the piece can not move when it is at home.
			- Keep that current dice value for another piece to move
		- After moving the first dice turn, If there is no piece able to use the next dice's value (move, kick, deploy)
			- this player's turn is done
	- After a piece in nest is moved and successfully use a dice's value (no matter dice 1 or 2), break the for loops and start a loop again for moving the the same piece with the last dice (if it is available), otherwise, it will search for another piece in nest. 
- If this player's turn is done, reset the dice turn, move to the next turn, then auto roll if the next turn is a bot			
					