position(pos)
java_object(Controller, [], controller)

oppositeDirection(LEFT,RIGHT).
oppositeDirection(RIGHT,LEFT).
oppositeDirection(UP,DOWN).
oppositeDirection(DOWN,UP).



act :- 
				controller <- isPortal returns portal,
				portal == true,
				finish.
				
act :- 
				controller <- isWind returns wind
				controller <- isSmell returns smell

finish :-
				controller <- finish.

move(Direction) :- 
									first(path,X),
									!oppositeDirection(Direction, X),
									controller <- canIMove(Direction) returns boolean,
									boolean == true,
									controller <- moveTo(Direction),
									append(Direction, path, path).

first([H|T],H).


