%a fish is free or caught
%a fisherman is fishing, bredouille or victorious

%initial state
fish(maurice).
fish(maurice_2).

free(maurice).
free(maurice_2).


fisherman(tom).
fishing(tom).

victorious(X) :- 
	fisherman(X),
	fish(Y),
	caught(X,Y).
	
caught(X,Y) :-
	print_C(("caught",X,Y)),
	free(Y),
	fishing(X),
	jpl_call('prologTest.PrologCalls',hooked,[X,Y],R),
	jpl_is_true(R).
	
print_C(N):-
	write(N),
	nl.
	

	
