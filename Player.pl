java_object(Player, [], player)

pos(0,0).
crackFound(0).
monsterFound(0).
alreadyVisited([]).

oppositeDirection(LEFT,RIGHT).
oppositeDirection(RIGHT,LEFT).
oppositeDirection(UP,DOWN).
oppositeDirection(DOWN,UP).

rule 1: windy(X) => assert(crack(UP)), assert(crack(DOWN)), assert(crack(LEFT)), assert(crack(RIGHT)), opposite(lastMove, Y), retract(crack(Y)).
rule 1: smelly(X) => assert(smelly(UP)), assert(smelly(DOWN)), assert(smelly(LEFT)), assert(smelly(RIGHT)), opposite(lastMove, Y), retract(smelly(Y)).
rule 3 : windy(X)	
rule 4 : move(_) => retract(smelly(_)), retract(windy(_)).


move(UP) :- pos(X,Y), append(pos(X,Y), alreadyVisited(L)), Y1 is Y, Y is Y1-1.
move(DOWN) :- pos(X,Y), Y1 is Y, Y is Y1+1.
move(LEFT) :- pos(X,Y), X1 is X, Y is X1-1.
move(RIGHT) :- pos(X,Y), X1 is X, Y is X1+1.
move(_) :- 



/*act(X) :- 
				Player <- isPortal returns portal,
				portal == true,
				finish.
				
act(X) :- 
				Player <- isWind returns wind,
				Player <- noChoice returns boolean,
				boolean == true,
				

act(X) :- 
				Player <- isWind returns wind
				Player <- noChoice returns boolean,
				boolean == false,
				

act(X) :- 
				Player <- isSmell returns smell


act(X) :- 
				Player <- isSmell returns smell

act(X) :-
				


finish :-
				Player <- finish.

move(Direction) :- 
									first(path,X),
									!oppositeDirection(Direction, X),
									Player <- canIMove(Direction) returns boolean,
									boolean == true,
									Player <- moveTo(Direction),
									append(Direction, path, path).

first([H|T],H).*/


