//*****************************************************************************
//* Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
//* All rights reserved. This program and the accompanying materials
//* are made available under the terms of the GNU Public License v2.0
//* which accompanies this distribution, and is available at
//* http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
//* 
//* Contributors:
//*     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
//*****************************************************************************

// Model of an entity within the constraint solver.
Entity <: ephemeral_object
Entity <: ephemeral_object(
    name:            								string,

	// Yann 2002/12/20: Diet...
	// It seems that the C++ code generator
	// has some limitations with respect to
	// handling field as property... I can't
	// use reflection to get the cache from
	// the field name... I must use a dumb
	// counter... Actually, maybe not... It
	// seems that the following line bug the
	// compiler off:
	//	listOfCachedReachableEntities:list<Entity> := get(cachedProperty, entity) as list<Entity>
	// while this following does not!?
	//	listOfCachedReachableEntities:list<Entity> := get(cachedProperty, entity)

	// 1
    superEntities:   								list<Entity>,
    cachedReachableSuperEntities:					list<Entity>,

	// 2
	aggregatedEntities: 							list<Entity>,
	cachedReachableAggregatedEntities:				list<Entity>,

	// 3
	associatedEntities: 							list<Entity>,
	cachedReachableAssociatedEntities:				list<Entity>,

	// 4
    componentsType:  								list<Entity>,
    composedEntities:								list<list<Entity>>,

	// 5
    containerAggregatedEntities:					list<Entity>,
    cachedReachableContainerAggregatedEntities:		list<Entity>,

	// 6
    containerComponentsType:  						list<Entity>,
    containerComposedEntities:						list<list<Entity>>,
    cachedReachableContainerComposedEntities:		list<Entity>,

	// 7
    createdEntities: 								list<Entity>,
    cachedReachableCreatedEntities:					list<Entity>,

	// 8
    knownEntities:   								list<Entity>,
    cachedReachableKnownEntities:					list<Entity>,

	// 9
    unknownEntities: 								list<Entity>,
    cachedReachableUnknownEntities:				list<Entity>
)
[self_print(e:Entity) : void
    ->  if known?(name, e) (
            printf("~S", e.name)
        )
        else (
            printf("Unnamed instance of Entity?!")
        )
]
// Yann 2002/12/08: Cache!
// The following method returns the
// cache associated with all
// reachable entities throug a given
// relationship.
[getCachedReachableRelationship(relationship:property) : property
	->	// [0] relationship = ~S // relationship,
		let
			cache:property := relationship
		in (
			if (relationship = superEntities) (
				cache := cachedReachableSuperEntities
			),
			if (relationship = aggregatedEntities) (
				cache := cachedReachableAggregatedEntities
			),
			if (relationship = associatedEntities) (
				cache := cachedReachableAssociatedEntities
			),
			if (relationship = containerAggregatedEntities) (
				cache := cachedReachableContainerAggregatedEntities
			),
			if (relationship = containerComposedEntities) (
				cache := cachedReachableContainerComposedEntities
			),
			if (relationship = createdEntities) (
				cache := cachedReachableCreatedEntities
			),
			if (relationship = knownEntities) (
				cache := cachedReachableKnownEntities
			),
			if (relationship = unknownEntities) (
				cache := cachedReachableUnknownEntities
			),
			// [0] cache = ~S // cache,
			cache
		)
]

// Global entities.
// Yann 2002/08/11: Entity declarations!
// I do not treat specially the java.lang.Object entity anymore
// because the meta-model now possesses the notion of Ghost:
// An entity that is references by some entities of the model
// but that is not really part of the model (i.e., about which
// we know nothing but its existence and name).
// 		peJavaLangObject:Entity := Entity(name = "java.lang.Object", componentsType = list(peNil), components = list(), knownEntities = list(), unknownEntities = list(), createdEntities = list())
//                                 (peJavaLangObject.superEntities := list(peJavaLangObject))
// Yann 2002/10/08: Back!
// I now use arrays instead of lists to handle entities properties.
// Thus, I need a 'neutral' entity which I can use for array declarations.

peNil:Entity          := Entity(name = "Nil")
minBoundValue:integer := 0
maxBoundValue:integer := 999999

// This global variable is define into the domain file (Domain.cl).

listOfEntities:list<Entity> := list<Entity>()

[self_print(l:list<Entity>) : void
    ->  printf("("),
        for i in (1 .. length(l)) (
            printf("~S", l[i]),
            if (i < length(l)) (
                printf(", ")
            )
        ),
        printf(")")
]
