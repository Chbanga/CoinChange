# The problem

Write a method that, given: 
1. an amount of money
2. an array of coins of different denominations in your wallet  

computes the number of ways to make the amount of money with the coins you have available. Write a class that calls this method and displays the total number of possible solutions and the possible solutions themselves.

Example: for amount = 4 (4€) and available coins=[1,1,1,1,2,3] (4 coins of 1€, 1 of 2€ and 1 of 3€), your program would output 3 — the number of ways to make 4€ with the coins you have available and the different solutions: 

Possible solutions: 3
1. 1€, 1€, 1€, 1€
2. 1€, 1€, 2€
3. 1€, 3€

# The approach

This is a know problem called Coin Change Problem, more details can be found https://en.wikipedia.org/wiki/Change-making_problem  
Our actual problem is relaxed from point of view of the number of coins that are avilable so the constraint on the minimum number of coins is relaxed. My approach is towards Dynamic Programming with heuristics althoug Greedy method may yeld acceptable results for reasonably small dimension of availble coins array.  
Dynamic Programming consits in solving the initial problem by solving a smaller subproblem that needs to solve for a smaller ammount by eliminating one of the coins from initial problem. The procedure repeats until a solution is found or the remaining coins is the array can not be used to form the required ammount (less or stricly greater than the ammount). The function that will solve the problem is called recursively. It is highly probable that for available coins with the same value (in our case 4 coins of 1€) the partial solutions will repeat, my approach is to store the partial solutions is a static variable.
