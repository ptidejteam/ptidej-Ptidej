pe675956139:Entity := Entity(name = "A")
pe2117151299:Entity := Entity(name = "B")
pe412343461:Entity := Entity(name = "C")
pe714909307:Entity := Entity(name = "D")
pe1350683038:Entity := Entity(name = "E")
pe655721215:Entity := Entity(name = "F")
pe1211134935:Entity := Entity(name = "java.lang.Object")

listOfEntities: list<Entity> := list<Entity>(
	pe675956139,
	pe2117151299,
	pe412343461,
	pe714909307,
	pe1350683038,
	pe655721215,
	pe1211134935)

[makeUseAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeUseAC4Constraint @ string,
		nil)
]
[makeAssociationAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeAssociationAC4Constraint @ string,
		makeUseAC4Constraint @ string)
]
[makeAggregationAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeAggregationAC4Constraint @ string,
		makeAssociationAC4Constraint @ string)
]
[makeCompositionAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeCompositionAC4Constraint @ string,
		makeAggregationAC4Constraint @ string)
]
[makeContainerAggregationAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeContainerAggregationAC4Constraint @ string,
		makeAssociationAC4Constraint @ string)
]
[makeContainerCompositionAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeContainerCompositionAC4Constraint @ string,
		makeContainerAggregationAC4Constraint @ string)
]
[makeCreationAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeCreationAC4Constraint @ string,
		nil)
]
[makeInheritanceAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(tuple(1,1),tuple(1,7),tuple(2,2),tuple(2,1),tuple(3,3),
			 tuple(3,2),tuple(4,4),tuple(4,7),tuple(5,5),tuple(5,4),
			 tuple(6,6),tuple(6,7),tuple(7,7)),
		makeInheritanceAC4Constraint @ string,
		nil)
]
[makeInheritancePathAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(tuple(1,1),tuple(1,7),tuple(2,2),tuple(2,1),tuple(2,7),
			 tuple(3,3),tuple(3,2),tuple(3,1),tuple(3,7),tuple(4,4),
			 tuple(4,7),tuple(5,5),tuple(5,4),tuple(5,7),tuple(6,6),
			 tuple(6,7),tuple(7,7)),
		makeInheritancePathAC4Constraint @ string,
		nil)
]
[makeStrictInheritanceAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(tuple(1,7),tuple(2,1),tuple(3,2),tuple(4,7),tuple(5,4),
			 tuple(6,7)),
		makeStrictInheritanceAC4Constraint @ string,
		nil)
]
[makeStrictInheritancePathAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(tuple(1,7),tuple(2,1),tuple(2,7),tuple(3,2),tuple(3,1),
			 tuple(3,7),tuple(4,7),tuple(5,4),tuple(5,7),tuple(6,7)),
		makeStrictInheritancePathAC4Constraint @ string,
		nil)
]
[makeAwarenessAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		true,
		list<tuple(integer,integer)>(),
		makeAwarenessAC4Constraint @ string,
		nil)
]
[makeIgnoranceAC4Constraint(
	name:string,
	command:string,
	v1:PtidejVar,
	v2:PtidejVar) : PtidejAC4Constraint
	->	makePtidejAC4Constraint(
		name,
		command,
		v1,
		v2,
		false,
		list<tuple(integer,integer)>(),
		makeIgnoranceAC4Constraint @ string,
		nil)
]
