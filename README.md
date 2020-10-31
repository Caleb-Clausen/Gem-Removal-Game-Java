# Java-Gem-Removal-Game

*  Run Instructions
1. Download the full repository 
2. Navigate to the directory then with the Java SE installed and open a cmd terminal.
3. Type “javac ModernGems.java” in the command line.
4. Then type “java ModernGems” in the command line to start the game
##Note All commands are case sensitive
* Menu commands:
new, load, quit e.g
to load a map load helloworld.md

* Board selection commands:
tiny, cross, almostCross, curve, normal, skinny, large

* Gem removal Policies:
WholeRowColRemovalPolicy, AdjacentRemovalPolicy, SingleRemovalPolicy

* Game player choices, player or AI makes gem removal choices
GreedyPlayer,  InteractivePlayer

* In game commands for board
move row col, e.g move 1 2
save filename.mg e.g save helloworld.mg
 quit

* Gem Removal Program Summery
This program creates a 2D text board on the command line and prompts the user to start a new game of gem removal. The idea is to remove all the gems on the 2D grid to score the most points possible. Winning the game means you cleared all the gems off the screen. The program lets the user choose to remove the gems themselves or select an AI to remove all the gems with one of the best possible outcomes. Multiple board types can be chosen, and multiple removal policies can be used to change how you play. If the player does not have enough time to complete the game there is a built in load and saving function as well.
Demonstration 




![GitHub Logo](https://github.com/Caleb-Clausen/Java-Maze-Runner/blob/master/Maze%20Runner/ReadMeImages/Demo%20Map.PNG)
