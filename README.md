Welcome to my Implementation of Carcassonne the Board Game.
Follow these simple steps to play the game and have a great time with
your friends!

1. Make sure you have all of the source code, and simply type "gradle run" in the
   terminal.
2. Now the game has started you should see the window, now clear the text file
   and enter the number of players make sure that your are playing with 2-5 players
3. Now hit the start button and the game will display the first tile in the upper 
   right corner. You can choose to place a meeple using the meeple placement buttons (*see side note for how this placement works!)
   beneath the current tile, you can also rotate the tile if you choose. You may also 
   remove the meeple from the tile using the correponding button. 
4. Now that you have your tile where you want it, you may choose a location on the board in the bottom
   left. Make sure that the tilecan be placed there! if it cant and you accidentely try to place 
   a tile though, dont worry the game will simply not update. Once you have placed a tile it will switch players turns
   and draw a new tile.
5. You may also notice that in the middle left there is a table of information telling you the color of your player
   also which players turn it is, along with their score, and the number of meeples each player has.
6. when the game is over the final tile will remain in the corner, and you will not be able to place a tile anywhere!
   this is how you know you are done, you can then look at the player information to see who won!
7. Currently the only way to play another game is to exit the application and then restart from the temrinal with "gradle run", 
   a small price to pay for playing multiple games of Carcassone with your friends.

*(A small side note for placing meeples, the information in the panel tells you which type of structure you may place the meeple on
  and the cardinal directions it spans, so for the starting tile 'U' you may place a meeple on the ROAD segment which spans from
  North to South, dont worry this will automatically update when you rotate your tile! LASTLY when a tile with a monastery presents itself
  the meeple placement selector may only offer locations of the FIELD type, dont worry this simply means your meeple will be placed on the 
  monastery, and you will see your meeple be placed in one of those fields, this is how we choose to represent a meeple on a monastery)