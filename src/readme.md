# Simulation of Blackjack and Card Counting

##About

This program simulates the game of Blackjack for investigation and experimental purposes. 

##Parameters of Simulation

It uses the following parameters that cannot be changed:

1) Uses Basic Strategy (changes with number of decks)
2) No insurance
3) Can only split once
4) Only one player
5) Dealer hits on soft 17

The following parameters can be changed:

1) Number of decks 
2) Penetration before reshuffle
3) Starting bankroll
4) Minimum bet
5) Which card counting method to use (options are TenCount(), HiLoCount() and ZenCount())
6) Number of games in simulation
7) Number of rounds per game

##How to use 

These parameters can be set in the main method of the Game() function. The main method adds the ending bankrolls of each 
game to a double array. The calculate() class contains static methods to find mean, standard deviation, percentiles, minimum value and maximum value in the array. 
