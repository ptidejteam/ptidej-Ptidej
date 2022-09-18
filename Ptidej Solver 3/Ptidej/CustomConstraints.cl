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

// ***************************
// * A general helper method *
// ***************************

[getAllReachableEntities(
	entity:Entity,
	listOfAllEntities:list<Entity>,
	f:property) : list<Entity>
    ->  // [0] getAllReachableEntities(...),
        let
			cachedProperty:property := getCachedReachableRelationship(f) as property,
        	listOfAllReachableEntities:list<Entity> := list<Entity>(),
        	listOfCachedReachableEntities:any := get(cachedProperty, entity),
            hasChanged:boolean := true,
            firstReachableEntities:any := get(f, entity)
		in (
			// Yann 2002/12/08: Cache!
			// I store in a cache the list of all reachable entities
			// because this list does not change during the solve!
			// [0] f = ~S // f,
			// [0] cache = ~S // cachedProperty,
			// [0] listOfCachedReachableEntities = ~S // listOfCachedReachableEntities,
			// Yann 2002/12/21: Known!?
			// It seems that the semantics of known?
			// has changed between Claire 2 and
			// Claire 3: For a list, known? answers
			// true even if the list has never been
			// initialized!?
			if (known?(listOfCachedReachableEntities) & length(listOfCachedReachableEntities) > 0) (
				// [0] ~S in cache for ~S (~S) // f, entity, listOfCachedReachableEntities,
				listOfCachedReachableEntities as list<Entity>
			)
			else (
	            // [0] listOfAllReachableEntities = ~S // listOfAllReachableEntities,
	
	            // For each related entities of the current
	            // Entity, I put it in the list of all its
	            // related entity, its direct related entities
	            // if they belong to the list of all the
	            // existing Entities.
	
	            // Yann 2002/12/06: Stupid!
	            // Why do I check if the related entity belongs
	            // to the list of all entities? Of course it
	            // does, else it would not exist!
	
	            // [0] entity = ~S // entity.name,
	            for i in (1 .. length(firstReachableEntities)) (
	                // [0] entity.property[~S] = ~S // i, firstReachableEntities[i].name,
					//    if (exists(
					//            j in (1 .. length(listOfAllEntities))
					//            | firstReachableEntities[i].name = listOfAllEntities[j].name)) (
	                // [0] Adding: ~S // firstReachableEntities[i].name,
	                listOfAllReachableEntities :add firstReachableEntities[i],
	                // listOfAllEntities :add firstReachableEntities[i],
	                nil
	            ),
	            // [0] listOfAllReachableEntities = ~S // listOfAllReachableEntities,
	
	            // Then, I perform recursively the same check on all
	            // the super-entities, I take a special care not to
	            // add twice the same super-entity.
				// [0] Recursion,
				while (hasChanged) (
					hasChanged := false,
		            for i in (1 .. length(listOfAllReachableEntities)) (
		            	// [0] length(listOfAllReachableEntities) = ~S // length(listOfAllReachableEntities),
		            	let
		            		reachableEntities := get(f, listOfAllReachableEntities[i])
		            	in (
		                    for j in (1 .. length(reachableEntities)) (
		                        // Here, I make sure I do not add twice the same entity.
								if (not(exists(k in (1 .. length(listOfAllReachableEntities)) |
											reachableEntities[j] = listOfAllReachableEntities[k]))) (
		                        	hasChanged := true,
			                        // [0] reachableEntities[j] = ~S // reachableEntities[j].name,
			                        // [0] added.,
			                        listOfAllReachableEntities :add reachableEntities[j],
			                        // [0] listOfAllReachableEntities = ~S // listOfAllReachableEntities,
			                        nil
								)
		                    )
	                	)
	                )
	            ),

				// Yann 2002/12/21: Known!?
				// I add at the very end of the list of
				// reachable entities the peNil entity.
				// Thus, even if the list is empty, I know
				// whether or not it has been initialized
				// (contains only peNil).
				// I add peNil at the end because I use the
				// index of the entities in the list to
				// compute the distance.
				add(listOfAllReachableEntities, peNil),

				// [0] Updating cache for entity ~S (~S). // entity, length(listOfAllReachableEntities),
				put(cachedProperty, entity, listOfAllReachableEntities),
				// [0] Cache of length ~S // length(get(cachedProperty, entity)),
				listOfAllReachableEntities
	        )
        )
]



// **************************
// * PropertyTypeConstraint *
// **************************

PropertyTypeConstraint <: PtidejBinConstraint(field:property)

[makePropertyTypeConstraint(
	ct:class,
	na:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	fd:property) : PtidejBinConstraint
    ->  let
    		propertyTypeConstraint := makePalmBinIntConstraint(
							    			ct,
							    			v1,
							    			v2,
							    			0)
    	in (
            propertyTypeConstraint.name    := na,
            propertyTypeConstraint.command := co,
			propertyTypeConstraint.field   := fd,
            propertyTypeConstraint as PropertyTypeConstraint
        )
]
[self_print(c:PropertyTypeConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:PropertyTypeConstraint) : void
    ->  // %%Pour chaque entité numéroté \textit{i} dans le domaine de la variable $v\sb1$ :%%
        for i in domainSequence(c.v1.bucket) (
            // %%Nous supposons que l'entité numéroté \textit{i} ne référence pas une entité dont le%%
            // %%numéro appartient au domaine de $v\sb2$, la valeur \textit{i} doit être retirée du domaine de $v\sb1$ :%%
            let toBeRemoved := true in (
                // %%Pour chaque entité numérotée \textit{j}, référencée par l'entité numérotée \textit{i} :%%
                for j in (1 .. length(get(c.field, listOfEntities[i]))) (
                    // %%S'il existe une entité numérotée \textit{k} dans le domaine de $v\sb2$ qui%%
                    // %%correspond à une entité référencée à l'entité numérotée \textit{j}:%%
                    if (exists(k in domainSequence(c.v2.bucket) |
                                get(c.field, listOfEntities[i])[j].name = listOfEntities[k].name)) (
                        // %%Alors, la valeur \textit{i} doit être laissée dans le domaine de $v\sb1$ :%%
                        toBeRemoved := false
                    )
                ),
                if (toBeRemoved) (
                    let expl := Explanation() in (
                        self_explain(c, expl), self_explain(c.v2, DOM, expl),
                        removeVal(c.v1, i, c.idx1, expl)
                    )
                )
            )
        ),
        // %%Pour chaque entité numérotée \textit{i} dans le domaine de la variable $v\sb2$ :%%
        for i in domainSequence(c.v2.bucket) (
            // %%Nous supposons qu'il n'y a pas d'entité dans le domaine de $v\sb1$ qui%%
            // %%correspond à une entité liée à l'entité numérotée \textit{i} :%%
            let toBeRemoved := true in (
                // %%Pour chaque entité numérotée \textit{j} dans le domaine de $v\sb1$ :%%
                for j in domainSequence(c.v1.bucket) (
                    // %%S'il existe une entité numérotée \textit{k}, dans la liste des entités que%%
                    // %%l'entité numérotée \textit{j} référence, qui correspond à l'entité numérotée \textit{i} :%%
                    if (exists(k in (1 .. length(get(c.field, listOfEntities[j]))) |
                                get(c.field, listOfEntities[j])[k].name = listOfEntities[i].name)) (
                        // %%Alors, la valeur \textit{i} doit être laissée dans le domaine de $v\sb2$ :%%
                        toBeRemoved := false
                    )
                ),
                if (toBeRemoved) (
                    let expl := Explanation() in (
                        self_explain(c, expl), self_explain(c.v1, DOM, expl),
                        removeVal(c.v2, i, c.idx2, expl)
                    )
                )
            )
        )
]
[awakeOnRem(c:PropertyTypeConstraint, idx:integer, v:integer) : void
    ->  awake(c)
]
[awakeOnRestoreVal(c:PropertyTypeConstraint, idx:integer, v:integer) : void
    ->	awake(c)
]
[doAwake(c:PropertyTypeConstraint) : void
	->	awake(c)
]



// ******************************
// * ListPropertyTypeConstraint *
// ******************************

ListPropertyTypeConstraint <: PtidejBinConstraint(field:property)

[makeListPropertyTypeConstraint(
	cst:class,
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	f:property) : PtidejBinConstraint
    ->	let
    		propertyTypeConstraint := makePalmBinIntConstraint(
    									cst,
    									v1,
    									v2,
    									0)
    	in (
            propertyTypeConstraint.name    := n,
            propertyTypeConstraint.command := co,
            propertyTypeConstraint.field   := f,
            propertyTypeConstraint as PtidejBinConstraint
		)
]
[self_print(c:ListPropertyTypeConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:ListPropertyTypeConstraint) : void
    ->  // [0] ListPropertyTypeConstraint awaken.\n,
        for i in domainSequence(c.v1.bucket) (
        	// [0] 1.,
            if (length(get(c.field, listOfEntities[i])) = 0) (
                let expl := Explanation() in (
                    self_explain(c, expl),
                    // Because I remove this particular Composite:
                    self_explain(c.v1, VAL, i, expl),
                    // I shall remove this Composite if it has no components:
                    removeVal(c.v1, i, c.idx1, expl)
                )
            )
        ),
        for i in domainSequence(c.v2.bucket) (
            let removeComponent := true in (
                for j in domainSequence(c.v1.bucket) (
                	// [0] 2.,
                    for k in (1 .. length(get(c.field, listOfEntities[j]))) (
                    	// [0] 3.,
                        for l in (1 .. length(get(c.field, listOfEntities[j])[k])) (
                        	// [0] 4.,
                            if (get(c.field, listOfEntities[j])[k][l].name = listOfEntities[i].name) (
                                removeComponent := false
                            )
                        )
                    )
                ),
                if (removeComponent) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        // Because of all the Composites (that do not aggregate this Component):
                        self_explain(c.v1, DOM, expl),
                        // I must remove this Component:
                        removeVal(c.v2, i, c.idx2, expl)
                    )
                )
            )
        )
]
[awakeOnRem(c:ListPropertyTypeConstraint, idx:integer, v:integer) : void
    ->  // [0] ListPropertyTypeConstraint awaken on remove.\n,
        // [0] idx = ~S, v = ~S // idx, v,
        if (idx = 1) (
            // We remove a Composite entity
            // [0] 5.,
            // For each list of Components in the Composite removed:
            for whatList in (1 .. length(get(c.field, listOfEntities[v]))) (
                // [0] 6.,
                let componentsList := get(c.field, listOfEntities[v])[whatList],
                    removeComponent := true in (
                    for i in (1 .. length(componentsList)) (
                        for j in (domainSequence(c.v1.bucket) but v) (
                        	// [0] 7.,
                            for k in (1 .. length(get(c.field, listOfEntities[j]))) (
                            	// [0] 8.,
                                for l in (1 .. length(get(c.field, listOfEntities[j])[k])) (
                                	// [0] 9.,
                                    if (get(c.field, listOfEntities[j])[k][l].name = componentsList[i].name) (
										// If the Component exists in another Composite, I shall keep it:
                                        removeComponent := false
                                    )
                                )
                            )
                        ),
                        if (removeComponent) (
                            for j in domainSequence(c.v2.bucket) (
                                if (listOfEntities[j].name = componentsList[i].name) (
                                    let expl := Explanation() in (
                                        self_explain(c, expl),
                                        // Because of this Composite that is removed:
                                        self_explain(c.v1, VAL, v, expl),
                                        // I must remove this Component:
                                        removeVal(c.v2, j, c.idx2, expl)
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
        else (
            // We remove a Component entity
            for i in domainSequence(c.v1.bucket) (
            	// [0] 10.,
            	// For each list of Components in the Composite removed:
                for whatList in (1 .. length(get(c.field, listOfEntities[i]))) (
                	// [0] 11.,
                    let
                    	componentsList := get(c.field, listOfEntities[i])[whatList],
                        removeComposite := true
					in (
                        // If this Composite aggregates the Component removed:
                        if (exists(pEntity in componentsList | pEntity.name = listOfEntities[v].name)) (
							// For each Component of this Composite (except the Component removed):
                            for j in ((1 .. length(componentsList)) but v) (
                                if (exists(pEntity in listOfEntities | pEntity.name = componentsList[j].name)) (
									// If this Composite aggregates Component still existing, I shall keep it:
                                    removeComposite := false
                                )
                            ),
                            if (removeComposite) (
                                let expl := Explanation() in (
                                    self_explain(c, expl),
                                    // Because of this Component that is removed:
                                    self_explain(c.v2, VAL, v, expl),
                                    // I must remove this Composite:
                                    removeVal(c.v1, i, c.idx1, expl)
                                )
                            )
                        )
                    )
                )
            )
        ),
        // [0] ListPropertyTypeConstraint awaken on remove ends.,
        nil
]
[awakeOnRestoreVal(c:ListPropertyTypeConstraint, idx:integer, v:integer) : void
    ->  // [0] ListPropertyTypeConstraint awaken on restore val.\n,
        awake(c)
]
[doAwake(c:ListPropertyTypeConstraint) : void
	->	awake(c)
]



// ******************************
// * PropertyDistanceConstraint *
// ******************************

PropertyDistanceConstraint <: PtidejLargeConstraint(field:property)

[makePropertyDistanceConstraint(
	cst:class,
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	counter:PalmIntVar,
	f:property) : PropertyDistanceConstraint
    ->	// [0] makePropertyDistanceConstraint,
    	let
    		l:list<PalmIntVar> := list<PalmIntVar>(v1, v2, counter),
    		propertyDistanceConstraint := makePalmLargeIntConstraint(
    										cst,
    										l,
    										0)
    	in (
    		// [0] propertyDistanceConstraint,
            propertyDistanceConstraint.name := n,
            propertyDistanceConstraint.command := co,
            propertyDistanceConstraint.field := f,
            propertyDistanceConstraint as PropertyDistanceConstraint
        )
]
[self_print(c:PropertyDistanceConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:PropertyDistanceConstraint) : void
    ->  // [0] PropertyDistanceConstraint awaken.,
    	// [0] c.field = ~S // c.field,
		// For each entity in the first list list
        for i in domainSequence(c.vars[1].bucket) (
          	// I assume this entity has no associated entity or is its own associated entity
			let
				ok := false,
                listOfAllReachableEntities:list<Entity> :=
                    getAllReachableEntities(
                        listOfEntities[i],
                        listOfEntities,
                        c.field)
			in (
                // [0] ~S, listOfAllReachableEntities = ~S // listOfEntities[i], listOfAllReachableEntities,
				// For each destination entity (possibly the entity itself)
                for j in domainSequence(c.vars[2].bucket) (
                    for k in (1 .. length(listOfAllReachableEntities)) (
                        if (listOfAllReachableEntities[k].name = listOfEntities[j].name) (
							// If I can find a super-entity, then I shall keep the entity
                            ok := true
                        )
                    )
                ),
				// If I can't find any valid super-entity
                if (not(ok)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.vars[2] (super-entities),
                        self_explain(c.vars[2], DOM, expl),
						// I remove this entity
                        removeVal(c.vars[1], i, c.indices[1], expl)
                        // removeVal(c.vars[1], i, expl)
                    )
                )
            )
        ),
		// [0] For each entity in the destination list,
        for j in domainSequence(c.vars[2].bucket) (
			// I assume this destination entity has no entity
            let ok := false in (
            	// [0] For each entity,
                for i in domainSequence(c.vars[1].bucket) (
                    let listOfAllReachableEntities:list<Entity> :=
                        getAllReachableEntities(
                            listOfEntities[i],
                            listOfEntities,
                            c.field) in (

                        // [0] For each entity in the list of reachable entities,
                        // [0] ~S, listOfAllReachableEntities = ~S // listOfEntities[i], listOfAllReachableEntities,
                        for k in (1 .. length(listOfAllReachableEntities)) (
                            if (listOfAllReachableEntities[k].name = listOfEntities[j].name) (
								// If I can find a entity with the super-entity, then I shall keep the super-entity
                                ok := true
	                    	)
                        )
                    )
                ),
				// [0] If I can't find a entity with this destination entity,
                if (not(ok)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.vars[1] (entities),
                        self_explain(c.vars[1], DOM, expl),
						// I remove this super-entity
                        removeVal(c.vars[2], j, c.indices[2], expl)
                        // removeVal(c.vars[2], j, expl)
                    )
                )
            )
        ),

		// [0] I update the bounds of the third variable,
    	let
    		minBound:integer := maxBoundValue,
    		maxBound:integer := minBoundValue
    	in (
			for i in domainSequence(c.vars[1].bucket) (
				let
	                listOfAllReachableEntities:list<Entity> :=
	                    getAllReachableEntities(
	                        listOfEntities[i],
	                        listOfEntities,
	                        c.field)
	        	in (
	        		for j in (1 .. length(listOfAllReachableEntities)) (
                    	// [0] minBound := min(minBound, j),
	        			if (exists(k in domainSequence(c.vars[2].bucket) |
	        						listOfAllReachableEntities[j].name = listOfEntities[k].name)) (
	        				minBound := min(minBound, j)
	        			),
                    	// [0] maxBound := max(maxBound, j),
	        			if (exists(k in domainSequence(c.vars[2].bucket) |
	        						listOfAllReachableEntities[j].name = listOfEntities[k].name)) (
	        				maxBound := max(maxBound, j)
	        			)
	        		)
	        	)
			),
    		// [0] minBound = ~S // minBound,
    		// [0] maxBound = ~S // maxBound,

            let expl := Explanation() in (
	            self_explain(c, expl),
				// Because of the domain of c.vars[1] (entities),
	            self_explain(c.vars[1], DOM, expl),
	            self_explain(c.vars[2], DOM, expl),
				// [0] I update the inf bound,
	    		updateInf(c.vars[3], minBound, c.indices[3], expl)
	    	),
            let expl := Explanation() in (
	            self_explain(c, expl),
				// Because of the domain of c.vars[1] (entities),
	            self_explain(c.vars[1], DOM, expl),
	            self_explain(c.vars[2], DOM, expl),
				// [0] I update the sup bound,
	    		updateSup(c.vars[3], maxBound, c.indices[3], expl)
	    	),
	    	// [0] ~S // c.vars[1],
	    	// [0] ~S // c.vars[2],
	    	// [0] ~S // c.vars[3],
	    	nil
		)
]
[awakeOnRestoreInf(c:PropertyDistanceConstraint, idx:integer) : void
    ->	awake(c)
]
[awakeOnRem(c:PropertyDistanceConstraint, idx:integer, v:integer) : void
    ->	awake(c)
]
[awakeOnRestoreSup(c:PropertyDistanceConstraint, idx:integer) : void
    ->	awake(c)
]
[awakeOnRestoreVal(c:PropertyDistanceConstraint, idx:integer, v:integer) : void
    ->	awake(c)
]
[doAwake(c:PropertyDistanceConstraint) : void
	->	awake(c)
]
[propagate(c:PropertyDistanceConstraint) : void -> awake(c)]



// ***************************************
// * Equality and inequality constraints *
// ***************************************

EqualConstraint <: PalmEqualxyc(
	name:string,
	command:string,
	thisConstraint:any = unknown,
	nextConstraint:any = unknown
)

[makeEqualConstraint(
	na:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : EqualConstraint
    ->  let equalConstraint := makePalmBinIntConstraint(EqualConstraint, v1, v2, 0)
    	in (
            equalConstraint.name := na,
            equalConstraint.command := co,
            equalConstraint.thisConstraint := makeEqualConstraint @ string,
            equalConstraint.nextConstraint := nil,
            equalConstraint as EqualConstraint
        )
]
[self_print(c:EqualConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:EqualConstraint) : void
    ->  // [0] EqualConstraint awaken.\n,
		if (length(domainSequence(c.v1.bucket)) = 1) (
			// [0] ~S (~S) // c.v1, domainSequence(c.v1.bucket),
            let
            	i:integer := firstElement(c.v1.bucket),
            	js:list<integer> := list<integer>(),
            	areEqual:boolean := true
            in (
                for j in domainSequence(c.v2.bucket) (
                	// [0] ~S = ~S? // listOfEntities[i].name, listOfEntities[j].name,
					if (not(listOfEntities[i].name = listOfEntities[j].name)) (
						areEqual := false,
						add(js, j)
                    )
                ),
                if (not(areEqual)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v1, DOM, expl),
                        for j in js (
	                        removeVal(c.v2, j, c.idx2, expl)
	                 	)
                    )
                )
            )
        ),
		if (length(domainSequence(c.v2.bucket)) = 1) (
			// [0] ~S (~S) // c.v2, domainSequence(c.v2.bucket),
            let
            	i:integer := firstElement(c.v2.bucket),
            	js:list<integer> := list<integer>(),
            	areEqual:boolean := true
            in (
                for j in domainSequence(c.v1.bucket) (
                	// [0] ~S = ~S? // listOfEntities[i].name, listOfEntities[j].name,
					if (not(listOfEntities[i].name = listOfEntities[j].name)) (
						areEqual := false,
						add(js, j)
                    )
                ),
                if (not(areEqual)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v2, DOM, expl),
                        for j in js (
	                        removeVal(c.v1, j, c.idx1, expl)
	                 	)
                    )
                )
            )
        )
]
[awakeOnRem(c:EqualConstraint, idx:integer, v:integer) : void
    -> awake(c)
]
[awakeOnRestoreVal(c:EqualConstraint, idx:integer, v:integer) : void
    -> awake(c)
]
[doAwake(c:EqualConstraint) : void
	->	awake(c)
]



NotEqualConstraint <: PalmNotEqualxyc(
	name:string,
	command:string,
	thisConstraint:any = unknown,
	nextConstraint:any = nil
)

[makeNotEqualConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : NotEqualConstraint
    ->  let
    		notEqualConstraint := makePalmBinIntConstraint(
    								NotEqualConstraint,
    								v1,
									v2,
									0)
		in (
            notEqualConstraint.name := n,
            notEqualConstraint.command := co,
            notEqualConstraint.thisConstraint := makeNotEqualConstraint @ string,
            notEqualConstraint as NotEqualConstraint
        )
]
[self_print(c:NotEqualConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:NotEqualConstraint) : void
    ->  // [0] \nNotEqualConstraint awaken.,
		if (length(domainSequence(c.v1.bucket)) = 1) (
			// [0] ~S (~S) // c.v1, domainSequence(c.v1.bucket),
            let
            	i:integer := firstElement(c.v1.bucket),
            	js:list<integer> := list<integer>(),
            	areDifferent:boolean := true
            in (
                for j in domainSequence(c.v2.bucket) (
                	// [0] ~S = ~S? // listOfEntities[i].name, listOfEntities[j].name,
					if (listOfEntities[i].name = listOfEntities[j].name) (
						areDifferent := false,
						add(js, j)
                    )
                ),
                if (not(areDifferent)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v1, DOM, expl),
                        for j in js (
	                        removeVal(c.v2, j, c.idx2, expl)
	                 	)
                    )
                )
            )
        ),
        if (length(domainSequence(c.v2.bucket)) = 1) (
			// [0] ~S (~S) // c.v2, domainSequence(c.v2.bucket),
            let
            	i:integer := firstElement(c.v2.bucket),
            	js:list<integer> := list<integer>(),
            	areDifferent:boolean := true
            in (
                for j in domainSequence(c.v1.bucket) (
                	// [0] ~S = ~S? // listOfEntities[i].name, listOfEntities[j].name,
					if (listOfEntities[i].name = listOfEntities[j].name) (
						areDifferent := false,
						add(js, j)
                    )
                ),
                if (not(areDifferent)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v2, DOM, expl),
                        for j in js (
	                        removeVal(c.v1, j, c.idx1, expl)
	                 	)
                    )
                )
            )
        ),
        // [0] NotEqualConstraint done.,
        nil
]
[awakeOnRem(c:NotEqualConstraint, idx:integer, v:integer) : void
    -> awake(c)
]
[awakeOnRestoreVal(c:NotEqualConstraint, idx:integer, v:integer) : void
    -> awake(c)
]
[doAwake(c:NotEqualConstraint) : void
	->	awake(c)
]



// **************************
// * Comparison constraints *
// **************************

GreaterOrEqualPtidejConstraint <: PalmGreaterOrEqualxc(
	name:string,
	command:string,
	thisConstraint:any = unknown,
	nextConstraint:any = nil
)

[makeGreaterOrEqualPtidejConstraint(
	n:string,
	co:string,
	v:PtidejBoundVar,
	i:integer) : GreaterOrEqualPtidejConstraint
    ->  let 
    		greaterOrEqualConstraint := makePalmUnIntConstraint(
    										GreaterOrEqualPtidejConstraint,
    										v,
    										i)
    	in (
            greaterOrEqualConstraint.name := n,
            greaterOrEqualConstraint.command := co,
            greaterOrEqualConstraint.thisConstraint := makeGreaterOrEqualPtidejConstraint @ string,
            greaterOrEqualConstraint as GreaterOrEqualPtidejConstraint
        )
]
[self_print(c:GreaterOrEqualPtidejConstraint) : void
    ->  printf("~A >= ~S", c.v1.name, c.cste)
]
[getConstant(c:GreaterOrEqualPtidejConstraint) : integer
	->	c.cste
]



LessOrEqualPtidejConstraint <: PalmLessOrEqualxc(
	name:string,
	command:string,
	thisConstraint:any = unknown,
	nextConstraint:any = nil
)

[makeLessOrEqualPtidejConstraint(
	n:string,
	co:string,
	v:PtidejBoundVar,
	i:integer) : LessOrEqualPtidejConstraint
    ->  let 
    		lessOrEqualConstraint := makePalmUnIntConstraint(
    									LessOrEqualPtidejConstraint,
    									v,
    									i)
    	in (
            lessOrEqualConstraint.name := n,
            lessOrEqualConstraint.command := co,
            lessOrEqualConstraint.thisConstraint := makeLessOrEqualPtidejConstraint @ string,
            lessOrEqualConstraint as LessOrEqualPtidejConstraint
        )
]
[self_print(c:LessOrEqualPtidejConstraint) : void
    ->  printf("~A <= ~S", c.v1.name, c.cste)
]
[getConstant(c:LessOrEqualPtidejConstraint) : integer
	->	c.cste
]



// *************************
// * AggregationConstraint *
// *************************

AggregationConstraint <: PropertyTypeConstraint()

[makeAggregationConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : AggregationConstraint
    ->  // [0] AggregationConstraint.\n,
        let
        	aggregationConstraint := makePropertyTypeConstraint(
        								AggregationConstraint,
        								n,
        								co,
        								v1,
        								v2,
        								aggregatedEntities)
        in (
            aggregationConstraint.thisConstraint := makeAggregationConstraint @ string,
            aggregationConstraint.nextConstraint := makeAssociationConstraint @ string,
            aggregationConstraint as AggregationConstraint
        )
]



// *************************
// * AssociationConstraint *
// *************************

AssociationConstraint <: PropertyTypeConstraint()

[makeAssociationConstraint(
	na:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : AssociationConstraint
    ->  let
        	associationConstraint := makePropertyTypeConstraint(
        								AssociationConstraint,
        								na,
        								co,
        								v1,
        								v2,
        								associatedEntities)
        in (
            associationConstraint.thisConstraint := makeAssociationConstraint @ string,
            associationConstraint.nextConstraint := makeUseConstraint @ string,
            associationConstraint as AssociationConstraint
        )
]



// *************************
// * CompositionConstraint *
// *************************

CompositionConstraint <: ListPropertyTypeConstraint()

[makeCompositionConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : CompositionConstraint
    ->  // [0] makeCompositionConstraint.\n,
        let
        	compositionConstraint := makeListPropertyTypeConstraint(
        								CompositionConstraint,
        								n,
        								co,
        								v1,
        								v2,
        								composedEntities)
        in (
            compositionConstraint.thisConstraint := makeCompositionConstraint @ string,
            compositionConstraint.nextConstraint := makeAggregationConstraint @ string,
            compositionConstraint as CompositionConstraint
        )
]



// **********************************
// * ContainerAggregationConstraint *
// **********************************

ContainerAggregationConstraint <: PropertyTypeConstraint()

[makeContainerAggregationConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : ContainerAggregationConstraint
    ->  // [0] ContainerAggregationConstraint.\n,
        let
        	containerAggregationConstraint := makePropertyTypeConstraint(
        										ContainerAggregationConstraint,
        										n,
        										co,
        										v1,
        										v2,
        										containerAggregatedEntities)
        in (
            containerAggregationConstraint.thisConstraint := makeContainerAggregationConstraint @ string,
            containerAggregationConstraint.nextConstraint := makeAssociationConstraint @ string,
            containerAggregationConstraint as ContainerAggregationConstraint
        )
]



// **********************************
// * ContainerCompositionConstraint *
// **********************************

ContainerCompositionConstraint <: ListPropertyTypeConstraint()

[makeContainerCompositionConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : ContainerCompositionConstraint
    ->  // [0] ContainerCompositionConstraint.\n,
        let
        	containerCompositionConstraint := makeListPropertyTypeConstraint(
        										ContainerCompositionConstraint,
        										n,
        										co,
        										v1,
        										v2,
        										containerComposedEntities)
        in (
            containerCompositionConstraint.thisConstraint := makeContainerCompositionConstraint @ string,
            containerCompositionConstraint.nextConstraint := makeContainerAggregationConstraint @ string,
            containerCompositionConstraint as ContainerCompositionConstraint
        )
]



// ***********************
// * UseConstraint *
// ***********************

UseConstraint <: PropertyTypeConstraint()

[makeUseConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : UseConstraint
    ->  // [0] UseConstraint.\n,
    	let
    		relatedEntitiesConstraint := makePropertyTypeConstraint(
    										UseConstraint,
							    			n,
							    			co,
							    			v1,
							    			v2,
							    			knownEntities)
    	in (
            relatedEntitiesConstraint.thisConstraint := makeUseConstraint @ string,
            relatedEntitiesConstraint as UseConstraint
        )
]



// ***********************
// * IgnoranceConstraint *
// ***********************

IgnoranceConstraint <: PropertyTypeConstraint()

[makeIgnoranceConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : IgnoranceConstraint
    ->  // [0] IgnoranceConstraint.\n,
    	let
    		unRelatedEntitiesConstraint := makePropertyTypeConstraint(
    											IgnoranceConstraint,
								    			n,
								    			co,
								    			v1,
								    			v2,
								    			unknownEntities)
    	in (
            unRelatedEntitiesConstraint.thisConstraint := makeIgnoranceConstraint @ string,
            unRelatedEntitiesConstraint as IgnoranceConstraint
        )
]



// **********************
// * CreationConstraint *
// **********************

CreationConstraint <: PropertyTypeConstraint()

[makeCreationConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : CreationConstraint
    ->  // [0] CreationConstraint.\n,
    	let
    		relatedEntitiesConstraint := makePropertyTypeConstraint(
    										CreationConstraint,
							    			n,
							    			co,
							    			v1,
											v2,
											createdEntities)
		in (
            relatedEntitiesConstraint.thisConstraint := makeCreationConstraint @ string,
            relatedEntitiesConstraint as CreationConstraint
        )
]



// *********************************
// * AssociationDistanceConstraint *
// *********************************

AssociationDistanceConstraint <: PropertyDistanceConstraint()

[makeAssociationDistanceConstraint(
	na:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	distance:PalmIntVar) : AssociationDistanceConstraint
    ->  // [0] AssociationDistanceConstraint.\n,
        let
        	associationDistanceConstraint := makePropertyDistanceConstraint(
        										AssociationDistanceConstraint,
								        		na,
								        		co,
								        		v1,
								        		v2,
								        		distance,
								        		associatedEntities)
        in (
            associationDistanceConstraint.thisConstraint := makeAssociationDistanceConstraint @ string,
            associationDistanceConstraint as AssociationDistanceConstraint
        )
]



// *********************************
// * AssociationDistanceConstraint *
// *********************************

UseDistanceConstraint <: PropertyDistanceConstraint()

[makeUseDistanceConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	counter:PalmIntVar) : UseDistanceConstraint
    ->  // [0] UseDistanceConstraint.\n,
        let
        	useDistanceConstraint := makePropertyDistanceConstraint(
												UseDistanceConstraint,
								        		n,
								        		co,
								        		v1,
								        		v2,
								        		counter,
								        		knownEntities)
        in (
            useDistanceConstraint.thisConstraint := makeUseDistanceConstraint @ string,
            useDistanceConstraint as UseDistanceConstraint
        )
]



// **********************************
// * InheritanceTreeDepthConstraint *
// **********************************

InheritanceTreeDepthConstraint <: PropertyDistanceConstraint()

[makeInheritanceTreeDepthConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar,
	counter:PalmIntVar) : InheritanceTreeDepthConstraint
    ->  // [0] InheritanceTreeDepthConstraint.\n,
        let
        	inheritanceTreeDepthConstraint := makePropertyDistanceConstraint(
												InheritanceTreeDepthConstraint,
								        		n,
								        		co,
								        		v1,
								        		v2,
								        		counter,
								        		superEntities)
        in (
            inheritanceTreeDepthConstraint.thisConstraint := makeInheritanceTreeDepthConstraint @ string,
            inheritanceTreeDepthConstraint as InheritanceTreeDepthConstraint
        )
]



// *****************************
// * InheritancePathConstraint *
// *****************************

InheritancePathConstraint <: PtidejBinConstraint()

[makeInheritancePathConstraint(n:string, co:string, v1:PtidejVar, v2:PtidejVar) : InheritancePathConstraint
    ->  let inheritancePathConstraint := makePalmBinIntConstraint(InheritancePathConstraint, v1, v2, 0) in (
            inheritancePathConstraint.name := n,
            inheritancePathConstraint.command := co,
            inheritancePathConstraint.thisConstraint := makeInheritancePathConstraint @ string,
            inheritancePathConstraint as InheritancePathConstraint
        )
]
[self_print(c:InheritancePathConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:InheritancePathConstraint) : void
    ->  // [0] InheritancePathConstraint awaken.\n,
        // [0]     Super-entities ~S // domainSequence(c.v2.bucket),
        // [0]           Entities ~S // domainSequence(c.v1.bucket),
        for i in domainSequence(c.v1.bucket) (                        // For each entity
            let ok := false,                          // I assume this entity has no super-entity or is its own super-entity
                listOfAllSuperEntities:list<Entity> :=
                    getAllReachableEntities(
                        listOfEntities[i],
                        listOfEntities,
                        superEntities) in (

                for j in (domainSequence(c.v2.bucket) but i) (        // For each super-entity that is not the entity itself
                    // [0]           ~S, listOfAllSuperEntities = ~S // listOfEntities[i], listOfAllSuperEntities,
                    for k in (1 .. length(listOfAllSuperEntities)) (
                        if (listOfAllSuperEntities[k].name = listOfEntities[j].name) (
                            ok := true              // If I can find a super-entity, then I shall keep the entity
                        )
                    )
                ),
                if (not(ok)) (                          // If I can't find any valid super-entity
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v2, DOM, expl),    // Because of the domain of c.v2 (super-entities),
                        removeVal(c.v1, i, c.idx1, expl), // I remove this entity
                        // [0]           Removed entity ~S // listOfEntities[i],
                        nil
                    )
                )
            )
        ),
        for j in domainSequence(c.v2.bucket) (                        // For each super-entity
            let ok := false in (                      // I assume this super-entity has no entity
                for i in domainSequence(c.v1.bucket) (                // For each entity
                    let listOfAllSuperEntities:list<Entity> :=
                        getAllReachableEntities(
                            listOfEntities[i],
                            // convertFromIndexesToEntities(domainSequence(c.v2.bucket))) in (
                            listOfEntities,
                            superEntities) in (
                            
                        // [0]           ~S, listOfAllSuperEntities = ~S // listOfEntities[i], listOfAllSuperEntities,
                        for k in (1 .. length(listOfAllSuperEntities)) (
                            if (listOfAllSuperEntities[k].name = listOfEntities[j].name) (
                                ok := true              // If I can find a entity with the super-entity, then I shall keep the super-entity
                            )
                        )
                    )
                ),
                if (not(ok)) (                          // If I can't find a entity with this super-entity
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v1, DOM, expl),   // Because of the domain of c.v1 (entities),
                        removeVal(c.v2, j, c.idx2, expl) // I remove this super-entity
                    )
                )
            )
        ),
        // [0]     Super-entities ~S // domainSequence(c.v2.bucket),
        // [0]           Entities ~S // domainSequence(c.v1.bucket),
        nil
]
[awakeOnRem(c:InheritancePathConstraint, idx:integer, v:integer) : void
    ->  // [0] InheritancePathConstraint awaken on remove.\n,
        awake(c)
]
[awakeOnRestoreVal(c:InheritancePathConstraint, idx:integer, v:integer) : void
    ->  // [0] InheritancePathConstraint awaken on restore val.\n,
        awake(c)
]
[doAwake(c:InheritancePathConstraint) : void -> awake(c)]



// ***********************************
// * StrictInheritancePathConstraint *
// ***********************************

StrictInheritancePathConstraint <: PtidejBinConstraint()

[makeStrictInheritancePathConstraint(n:string, co:string, v1:PtidejVar, v2:PtidejVar) : StrictInheritancePathConstraint
    ->  let inheritancePathConstraint := makePalmBinIntConstraint(InheritancePathConstraint, v1, v2, 0) in (
            inheritancePathConstraint.name := n,
            inheritancePathConstraint.command := co,
            inheritancePathConstraint.thisConstraint := makeStrictInheritancePathConstraint @ string,
            inheritancePathConstraint.nextConstraint := makeInheritancePathConstraint @ string,
            inheritancePathConstraint as StrictInheritancePathConstraint
        )
]
[self_print(c:StrictInheritancePathConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:StrictInheritancePathConstraint) : void
    ->  // [0] InheritancePathConstraint awaken.\n,
        // [0]     Super-entities ~S // domainSequence(c.v2.bucket),
        // [0]           Entities ~S // domainSequence(c.v1.bucket),
        for i in domainSequence(c.v1.bucket) (                        // For each entity
            let ok := false,                          // I assume this entity has no super-entity or is its own super-entity
                listOfAllSuperEntities:list<Entity> :=
                    getAllReachableEntities(
                        listOfEntities[i],
                        listOfEntities,
                        superEntities) in (

                for j in (domainSequence(c.v2.bucket) but i) (        // For each super-entity that is not the entity itself
                    // [0]           ~S, listOfAllSuperEntities = ~S // listOfEntities[i], listOfAllSuperEntities,
                    for k in (1 .. length(listOfAllSuperEntities)) (
                        if (listOfAllSuperEntities[k].name = listOfEntities[j].name) (
                            ok := true              // If I can find a super-entity, then I shall keep the entity
                        )
                    )
                ),
                if (not(ok)) (                          // If I can't find any valid super-entity
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v2, DOM, expl),    // Because of the domain of c.v2 (super-entities),
                        removeVal(c.v1, i, c.idx1, expl), // I remove this entity
                        // [0]           Removed entity ~S // listOfEntities[i],
                        nil
                    )
                )
            )
        ),
        for j in domainSequence(c.v2.bucket) (                         // For each super-entity
            let ok := false in (                       // I assume this super-entity has no entity
                for i in domainSequence(c.v1.bucket) (                 // For each entity
                    let listOfAllSuperEntities:list<Entity> :=
                        getAllReachableEntities(
                            listOfEntities[i],
                            // convertFromIndexesToEntities(domainSequence(c.v2.bucket))) in (
                            listOfEntities,
                            superEntities) in (
                            
                        // [0]           ~S, listOfAllSuperEntities = ~S // listOfEntities[i], listOfAllSuperEntities,
                        for k in (1 .. length(listOfAllSuperEntities)) (
                            if (listOfAllSuperEntities[k].name = listOfEntities[j].name) (
                                ok := true              // If I can find a entity with the super-entity, then I shall keep the super-entity
                            )
                        )
                    )
                ),
                if (not(ok)) (                          // If I can't find a entity with this super-entity
                    let expl := Explanation() in (
                        self_explain(c, expl),
                        self_explain(c.v1, DOM, expl),   // Because of the domain of c.v1 (entities),
                        removeVal(c.v2, j, c.idx2, expl) // I remove this super-entity
                    )
                )
            )
        ),
        // [0]     Super-entities ~S // domainSequence(c.v2.bucket),
        // [0]           Entities ~S // domainSequence(c.v1.bucket),
        nil
]
[awakeOnRem(c:StrictInheritancePathConstraint, idx:integer, v:integer) : void
    ->  // [0] StrictInheritancePathConstraint awaken on remove.\n,
        awake(c)
]
[awakeOnRestoreVal(c:StrictInheritancePathConstraint, idx:integer, v:integer) : void
    ->  // [0] StrictInheritancePathConstraint awaken on restore val.\n,
        awake(c)
]
[doAwake(c:StrictInheritancePathConstraint) : void -> awake(c)]



// *************************
// * InheritanceConstraint *
// *************************

InheritanceConstraint <: PtidejBinConstraint()

[makeInheritanceConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : InheritanceConstraint
    ->  let
    		inheritanceConstraint := makePalmBinIntConstraint(
    									InheritanceConstraint,
    									v1,
    									v2,
    									0)
    	in (
            inheritanceConstraint.name := n,
            inheritanceConstraint.command := co,
            inheritanceConstraint.thisConstraint := makeInheritanceConstraint @ string,
            inheritanceConstraint.nextConstraint := makeStrictInheritancePathConstraint @ string,
            inheritanceConstraint as InheritanceConstraint
        )
]
[self_print(c:InheritanceConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:InheritanceConstraint) : void
    ->  // [0] InheritanceConstraint awaken.,
        // [0] Before: ~S ~S // domainSequence(c.v1.bucket), domainSequence(c.v2.bucket),
		// For each entity
        for i in domainSequence(c.v1.bucket) (
			// I assume this entity has no super-entity
            let
            	toBeRemoved := true
            in (
				// For each super-entity
                for j in domainSequence(c.v2.bucket) (
                	if (listOfEntities[i].name = listOfEntities[j].name) (
						// If I can find a super-entity, then I shall keep the entity
                        toBeRemoved := false,
                        break(toBeRemoved)
                	)
                	else (
	                    for k in (1 .. length(listOfEntities[i].superEntities)) (
	                        if (listOfEntities[i].superEntities[k].name = listOfEntities[j].name) (
								// If I can find a super-entity, then I shall keep the entity
	                            toBeRemoved := false,
		                        break(toBeRemoved)
	                        )
	                    )
					)
                ),
				// If I can't find any valid super-entity
                if (toBeRemoved) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.v2 (super-entities),
                        self_explain(c.v2, DOM, expl),
						// I remove this entity
                        removeVal(c.v1, i, c.idx1, expl)
                    )
                )
            )
        ),
		// For each super-entity
        for i in domainSequence(c.v2.bucket) (
			// I assume this super-entity has no entity
            let
            	toBeRemoved := true
            in (
				// For each entity
                for j in domainSequence(c.v1.bucket) (
                	if (listOfEntities[j].name = listOfEntities[i].name) (
						// If I can find a entity with the super-entity, then I shall keep the super-entity
                        toBeRemoved := false
                	)
                	else (
	                    for k in (1 .. length(listOfEntities[j].superEntities)) (
	                        if (listOfEntities[j].superEntities[k].name = listOfEntities[i].name) (
								// If I can find a entity with the super-entity, then I shall keep the super-entity
	                            toBeRemoved := false
	                        )
	                    )
					)
                ),
				// If I can't find a entity with this super-entity
                if (toBeRemoved) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.v1 (entities),
                        self_explain(c.v1, DOM, expl),
						// I remove this super-entity
                        removeVal(c.v2, i, c.idx2, expl)
                    )
                )
            )
        ),
        // [0] After:  ~S ~S // domainSequence(c.v1.bucket), domainSequence(c.v2.bucket),
        nil
]
[awakeOnRem(c:InheritanceConstraint, idx:integer, v:integer) : void
    ->  // [0] InheritanceConstraint awaken on remove.\n,
        awake(c)
]
[awakeOnRestoreVal(c:InheritanceConstraint, idx:integer, v:integer) : void
    ->  // [0] InheritanceConstraint awaken on restore val.\n,
        awake(c)
]
[doAwake(c:InheritanceConstraint) : void -> awake(c)]



// *******************************
// * StrictInheritanceConstraint *
// *******************************

StrictInheritanceConstraint <: PtidejBinConstraint()

[makeStrictInheritanceConstraint(
	n:string,
	co:string,
	v1:PtidejVar,
	v2:PtidejVar) : StrictInheritanceConstraint
    ->	let
    		strictInheritanceConstraint := makePalmBinIntConstraint(
    											StrictInheritanceConstraint,
    											v1,
    											v2,
    											0)
    	in (
            strictInheritanceConstraint.name := n,
            strictInheritanceConstraint.command := co,
            strictInheritanceConstraint.thisConstraint := makeStrictInheritanceConstraint @ string,
            strictInheritanceConstraint.nextConstraint := makeInheritanceConstraint @ string,
            strictInheritanceConstraint as StrictInheritanceConstraint
        )
]
[self_print(c:StrictInheritanceConstraint) : void
    ->  printf("~S", c.name)
]
[awake(c:StrictInheritanceConstraint) : void
    ->  // [0] StrictInheritanceConstraint awaken.\n,
		// For each entity
        for i in domainSequence(c.v1.bucket) (
			// I assume this entity has no super-entity or is its own super-entity
            let ok := false in (
				// For each super-entity that is not the entity itself
                for j in (domainSequence(c.v2.bucket) but i) (
                    for k in (1 .. length(listOfEntities[i].superEntities)) (
                        if (listOfEntities[i].superEntities[k].name = listOfEntities[j].name) (
							// If I can find a super-entity, then I shall keep the entity
                            ok := true
                        )
                    )
                ),
				// If I can't find any valid super-entity
                if (not(ok)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.v2 (super-entities),
                        self_explain(c.v2, DOM, expl),
						// I remove this entity
                        removeVal(c.v1, i, c.idx1, expl)
                    )
                )
            )
        ),
		// For each super-entity
        for j in domainSequence(c.v2.bucket) (
			// I assume this super-entity has no entity
            let ok := false in (
				// For each entity
                for i in domainSequence(c.v1.bucket) (
                    for k in (1 .. length(listOfEntities[i].superEntities)) (
                        if (listOfEntities[i].superEntities[k].name = listOfEntities[j].name) (
							// If I can find a entity with the super-entity, then I shall keep the super-entity
                            ok := true
                        )
                    )
                ),
				// If I can't find a entity with this super-entity
                if (not(ok)) (
                    let expl := Explanation() in (
                        self_explain(c, expl),
						// Because of the domain of c.v1 (entities),
                        self_explain(c.v1, DOM, expl),
						// I remove this super-entity
                        removeVal(c.v2, j, c.idx2, expl)
                    )
                )
            )
        )
]
[awakeOnRem(c:StrictInheritanceConstraint, idx:integer, v:integer) : void
    ->  // [0] StrictInheritanceConstraint awaken on remove.\n,
        if (idx = 1) (
            for h in (1 .. length(listOfEntities[v].superEntities)) (
                let superEntityName := listOfEntities[v].superEntities[h].name,
                    ok := false in (                    // I assume the super-entity of this entity has no more child
                    for i in domainSequence(c.v1.bucket) (             // For each entity
                        for j in (1 .. length(listOfEntities[i].superEntities)) (
                            if (listOfEntities[i].superEntities[j].name = superEntityName) (
                                ok := true              // If I can find a entity which father is the super-entity, I shall keep the super-entity
                            )
                        )
                    ),
                    if (not(ok)) (
                        for j in domainSequence(c.v2.bucket) (
                            if (listOfEntities[j].name = superEntityName) (
                                let expl := Explanation() in (
                                    self_explain(c, expl),
                                    self_explain(c.v1, DOM, expl),   // Because of the domain of c.v1 (entities),
                                    removeVal(c.v2, j, c.idx2, expl) // I remove this super-entity
                                )
                            )
                        )
                    )
                )
            )
        )
        else (
            for i in domainSequence(c.v1.bucket) (                            // For each entity
                for j in (1 .. length(listOfEntities[i].superEntities)) (
                    if (listOfEntities[i].superEntities[j].name = listOfEntities[v].name) (
                        let expl := Explanation() in (
                            self_explain(c, expl),
                            self_explain(c.v2, VAL, v, expl), // Because I remove this particular super-entity,
                            removeVal(c.v1, i, c.idx1, expl)  // I remove this entity if it has not the super-entity that is being removed for super-entity
                        )
                    )
                )
            )
        )
]
[awakeOnRestoreVal(c:StrictInheritanceConstraint, idx:integer, v:integer) : void
    -> awake(c)
]
[doAwake(c:StrictInheritanceConstraint) : void -> awake(c)]
