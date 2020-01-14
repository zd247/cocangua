# cocangua
RMIT University Vietnam
Course: INTE2512 Object-Oriented Programming
Semester: 2019C
Assessment name: Final Project
Team name: Team 5
Team members: 
- Tran Kim Bao: S3740819
- Phan Quoc Binh: s3715271
- Tran Mach So Han: s3750789
- Nguyen Huu Duy: S3703336
- Nguyen Minh Trang: s3751450


1. INTRODUCTION
Our team is tasked with India Game called Pachisi make through Java program.
The project takes 4 weeks to finish. 

2. FEATURES
    
        - Roll dices: 
+In the beginning ,the turn of the player will be decided by
the rolling dice. and the turn will be made clock-wise, 
+In each player turn , there are 2 colors of the nest-circle notifies the turn
of the user. (orange is the turn of the player to roll, green is the turn
of the player to move)
+User may choose one dice for this piece or another, however dice value will
be set by order
        - Move:
+ click a piece of player and make it move by the dice value display
+ The piece will move by order of the dice. 
+ The piece will move 1 dice if it has only 50 percentage
+ The piece is moving by the pause transition and translate transition
        - Stop : The player will choose an image in the center to stop the game immediately
        - Score : 
+ At the beginning, the score of all player on the nest is 0
+ The score will be updated when the piece of players moves from the house arrival to
tthe home path
+ The score will be inccreased by 1 when tthe piece move from the house step by
step
+ The score of player will be increased by 2 when tthe piece of the player 
kicks the opponent's piece
+ The score of player will be decreased by 2 when tthe piece of the player 
was kicked and sent to home
        - Sound : music and SFXs are turned on by default. User can choose to muste 
/unmute them.
+ when the piece is deployed 
+ when the piece is moving 
+ when the piece is kicked
+ when the piece moves inside the hosue path
+ when the game finished
        - Play again or Quit :the game finishes when all the piece of player reaches 
by order 6-5-4-3, player can replay or quit the game
If user replays , the score from all previous rounds are stacked up
I not , the point will not be updated
        - Game status: during the game, context such as dice value, status of moving
and updated score are displayed
        -Language : the language can be changed from vietnamese to english

3. DESIGN
- The map contains 48 circle spaces, 16 rectangle home paths, 4 different 
color nests, and each nest has 4 pieces. 4 different colors are used in 
this maps are blue, yellow, green and red
- Each node has coordinator X, Y to help the piece easily keep track and
moving
- The animation using is translate and pause
4. INSTALLATION
- Unzip "cocangua.zip"
- Make sure folders such as "data" and src and out are in the project
- Execute Main.class on JVM
5. KNOWN BUGS
- For the bot player, very little cases, there is a lag animation(if sout is applied for checking, we can confirm that it move correctly), and lead to the piece image is displayed wrong place (only from home arrival).
- score.txt file should be empty before starting game because some how it can read the line correctly (only first run happened), for any next run, it will be okay and read the file correctly.


6. ACKNOWLEDGEMENT
* https://beginnersbook.com/2013/12/hashmap-in-java-with-example/
* https://www.javatpoint.com/javafx-translate-transition
Pseudo code fore turn dice logic:
- Use dice by dice
   - If the first dice turn which use the first dice not able to move any piece and the second dice is possible, change to use the second dice first, then the next turn dice will use the value of dice 1 to move (if it is possible - this will be checked by auto checking).  
   - Piece in nest will use the dice which has value of 6, and leave another one to the next turn
   - Piece in home or at home arrival, it will use the dice has higher value (in case that dice is able to use to move piece).



Pseudo code for Machine Player:
Machine algorithm:

+ How to win this game: 
- After researching, there is a simple way to win this game easily. More specifically, firstly, after deploying the first piece, you should let it move until there is a block, after that, if there is a block, move to use another piece. Then, whenever the piece is at home, try to let it move to the highest position if it is possible. Therefore, this machine player is just applied the same logic with what we found out. 


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
					- If there is a piece is not deployed yet
						- If there is the first dice turn and the "current dice value"(store value of dice 1) is not 6 and the second dice is 6
							- The "current dice value" will store the value of dice 2
							- swap value of 2 dices (next turn dice will use the value of dice 1)
					- If the piece's step + current dice value <= 48 (means not pass the home arrival)
						- Move piece to its destination
					- Else the piece will not move
				- If the piece cannot move, keep that current dice value for another piece in nest to move
		- If the piece's step is higher than 47 and its step if lower than 55 which is maximum step of each piece and (the piece is not blocked with the current dice value or (piece is not blocked by using dice 2 and dice turn is 1))
			- If there is the first dice turn and the piece is not blocked by dice2 and (the piece is blocked with dice 1 or dice2 > dice1)
				- The "current dice value" will store the value of dice 2
				- swap value of 2 dices (next turn dice will use the value of dice 1)
			- move the piece
		- If the piece cannot move when it is at home.
			- Keep that current dice value for another piece to move
		- After moving the first dice turn, if there is no piece able to use the next dice's value (move, kick, deploy)
			- This player's turn is done
	- After a piece in nest is moved and successfully use a dice's value (no matter dice 1 or 2), break for loop and start a loop again for moving the same piece with the last dice (if it is available), otherwise, it will search for another piece in nest. 
- If this player's turn is done, reset the dice turn, move to the next turn, then auto roll if the next turn is a bot			
					
