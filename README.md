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

This is a know problem called Coin Change Problem, more details can be found https://en.wikipedia.org/wiki/Change-making_problem and http://www.algorithmist.com/index.php/Coin_Change 
Our actual problem is relaxed from point of view of the number of coins that are available so the constraint on the minimum number of coins is relaxed. My approach is towards Dynamic Programming with heuristics despite the fact a Greedy method may yield acceptable results for reasonably small dimension of available coins array.  
Dynamic Programming consists in solving the initial problem by solving a smaller subproblem that needs to solve for a smaller amount by eliminating one of the coins from initial problem. The procedure repeats until a solution is found or the remaining coins is the array can not be used to form the required amount (less or strictly greater than the amount). The function that will solve the problem is called recursively. It is highly probable that for available coins with the same value (in our case 4 coins of 1€) the partial solutions will repeat, my approach is to store the partial solutions is a static variable. 

We will use the following notations:
- *coins* is the array of coins
- *amount* is the target amount that we want to obtain with available coins
- *solution* is a structure where we store solutions for a problem of type change(amount, coins). A solution consist of an array of coins or -1 if there is no solution. There are a couple of special cases:
  - no coins are given, in this case no change can be made. Solution will be null.
  - the amount is 0, in this case we do not have a proper problem. Solution will be null.
  - the amount is greater than the sum of all coins, in this case it is not possible to obtain the amount even if we use all coins. Solution will be null.
  - the amount is less than any coin, in this case is not possible to obtain the amount by using any coin. Solution will be null.  
The algorithm is supposed to solve the initial problem change(amount, coins) using the steps:
- check for a valid amount conditions and put solution<amount, coins> = null; return
- check if we already have a solution for this input data and if so then return
- order coins in ascending order
- for each element in coins check if we can solve the problem with that coin_value or try to solve the problem by using that coin_value and solve the problem for amount-coin_value and the rest of the coins 


## Pseudo code: 
```
static solution

change(amount, coins, partial_solution) ->
	if coins.size() == 0 then
		solution<amount, partial_solution> = null
	if amount > coins.sum() then
		solution<amount, partial_solution> = null
		return
	if amount < coins.min() then
		solution<amount, partial_solution> = null
		return
	if amount == 0 then
		solution<amount, partial_solution> = null
		return
	for each coin_value in coins
		current_solution = partial_solution.push(coin_value)
		current_solution.sort()
  		if amount = coin_value and solution<amount, coins> does not contain current_solution then
  			solution<amount, coins>.push(current_solution)
  			next coin_value
  		if solution<amount, coins> != null then
  			solution<amount, coins>.push(coin_value.push(<change<amount-coin_value, coins.pop(coin_value), current_solution >))
	return

change(4, [1, 1, 1, 1, 2, 3], [])
print solution(4)
```