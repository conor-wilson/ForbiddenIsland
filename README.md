# Forbidden-Island

## Software Engineering - COMP41670

Authors: Robert Riordan and Conor Wilson

# How to play

1. Run src/gameplay/ForbiddenIsland.java .
2. When prompted, provide the number of players playing.
3. When prompted, each player should give their name.
    1. Take note of the role you have been assigned and what special skill you have.
    2. These skills will take priority over any other rules in the game.
4. Choose the difficulty level.
5. The game is now set up. Follow the prompts on screen and try to escape with the treasure!

Your starting position is based on your role and you will start with 2 treasure cards.

# Rules

Your goal is to work as a team to escape the Forbidden Island with all the treasures. While you do this the Island will slowly sink, making you task increasingly difficult.

## On your turn

You can take up to 3 actions on your turn.

These are:

* Move to an adjacent tile.
* Shore up an adjacent tile.
* Capture a treasure.
* Give another player a treasure card.

Once you have completed these actions you will draw treasure cards and flood cards.

### Moving

You can move on any tile which has not sunk. You may only move horizontally or vertically.

### Shoring up

You can shore up the tile you are currently on or any adjacent tile. This will dry a flooded tile. You cannot shore up a sunken tile; a sunken tile has been lost and you'll have to work around it.

### Capturing a treasure

To capture a treasure a player must:

* Get to a tile which is a source of treasure.
  * These are marked on the board with the treasure you will find there.
* Discard 4 matching treasure cards.

### Giving another player treasure cards

You can give treasure cards to any other player who is on the same till as you. You cannot give special action cards to other players.

## Drawing treasure cards

You cannot have more than 5 cards in your hand. If you have more than 5 cards in your hand you will be asked to discard back down to the hand limit. If you discard a special action card you can use it too.

If you draw a waters rise card it will be immediately discarded. The water level will increase and any discarded flood cards will be shuffled and placed back at the top of the flood deck draw pile.

## Drawing flood cards

The number of flooded cards drawn is related to the water level. When a flood card is drawn the corresponding tile is flooded if it is dry or sunken if it is flooded.

## Special action cards

There are 2 types of special action cards in the treasure deck. These can be used at any time and take no actions.

### Helicopter lift

Move any number of players who are on the same tile to any other tile on the board.

### Sandbag

Shore up any tile on the board.

# Winning

Gather all the players on Fools Landing, with all the treasures, and escape using a Helicopter Lift card.

# Losing

There are a number of ways to lose

* Fools Landing sinks
* All sources of an uncaptured treasure sink
* A tile with a player on it sinks and the player cannot move to a safe tile
