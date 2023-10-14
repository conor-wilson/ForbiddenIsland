# Forbidden-Island

# Project Background Information
This project began life as a group project by Conor Wilson (me) and Robert Riordan for their end-of-semester project in
the ***Software Engineering (COMP41670)*** module at University College Dublin. This module was completed as part of our
journey to completing our ME degree in Electronic and Computer Engineering, and received an A grade when it was
submitted on the 23rd of December 2019. The assignment brief called for a digital recreation of the  [Forbidden Island
boardgame](https://boardgamegeek.com/boardgame/65244/forbidden-island) that could be played within the terminal.

This project was a real highlight of our degree, and was the first time either of us had worked (or attempted to work)
with professional programming principles in mind. Since then, we have both become professional Software Engineers, and
I (Conor) have decided to return to this project as a side project, and I intend to develop it into a desktop 
application for a bit of fun. 

Unfortunately, due to both of our university emails being no-longer available (something that we were assured by the
university would not happen), as well as poor archiving on my part, the blame and credit history of this project has
been lost. With this in mind, you can assume that any code committed to this repo by the @CnrWilson account (my old
university account that I was actually still logged into locally on my machine when I created this repo) can be credited
equally to both Robert and me. In fact, the entirety of the code base including mistakes and weird code design has been
preserved within the first commit made by this account. In the future I may clone this original version over to its own
repo for the sake of nostalgia and maintaining the orignial legacy of this project.

Below is the contents of the original ReadMe for the submitted assignment, but this will probably be changed around in
the future as this side project evolves.

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
