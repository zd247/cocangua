# cocangua

The controller process the user requests.
Based on the user request, the Controller calls methods in the View and
Model to accomplish the requested action. 

Note: The view should not send to the model but it is often useful
for the view to receive update event information from the model. 
However you should not update the model from the view.

Machine algorithm:

While the turn is not done yet
	For each piece in the Piece list of the nest
		Take that piece 
		Use dice 1 by 1 by the order, and save its value to "current dice value"
		If the piece's step is lower than 48
			If the piece both not able to move and able to kick with the first dice, and there is the first turn dice
				If they can do 1 out of 2 action above with the second dice	
					Change the "current dice value" from dice 1 to dice 2
					swap the value of dice 1 and dice 2 (next turn dice will use the value of dice 1)
			If the piece is not blocked or the piece is able to kick
				If there is able to kick condition
					Check for the destination and put that enemy's piece to its nest
				If there is not a piece which is not deployed yet ,or there is a piece (is not deployed yet) and the "current dice value" is 6 and there is the second dice turn, or there is a 6 (dice 1 or 2) and the first dice turn
					if there is a piece is not deployed yet
						if there is the first dice turn and the "current dice value"(store value of dice 1) is not 6 and the second dice is 6
							the "current dice value" will store the value of dice 2
							swap value of 2 dices (next turn dice will use the value of dice 1)
				If the piece's step + current dice value <= 48 (means not pass the home arrival)
					Move piece to its destination
				else the piece will not move
			If the piece cannot move, keep that current dice value for another piece in nest to move
		If the piece's step is higher than 47 and its step if lower than 55 which is maximum step of each piece and (the piece is not blocked with the current dice value or (piece is not blocked by using dice 2 and dice turn is 1))
			If there is the first dice turn and the piece is not blocked by dice2 and (the piece is blocked with dice 1 or dice2 > dice1)
				the "current dice value" will store the value of dice 2
				swap value of 2 dices (next turn dice will use the value of dice 1)
			move the piece
		If the piece can not move when it is at home.
			Keep that current dice value for another piece to move
		After moving the first dice turn, If there is no piece able to use the next dice's value (move, kick, deploy)
			this player's turn is done
If this player's turn is done, reset the dice turn, then auto roll if the next turn is a bot			
					