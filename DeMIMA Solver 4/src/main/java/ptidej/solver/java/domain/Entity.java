/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.java.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/16
 */
public class Entity {
	private final Set aggregatedEntities;
	private Set allReachableAggregatedEntities = null;
	private Set allReachableAssociatedEntities = null;
	private Set allReachableComposedEntities = null;
	private Set allReachableContainerAggregatedEntities = null;
	private Set allReachableContainerComposedEntities = null;
	private Set allReachableCreatedEntities = null;
	private Set allReachableKnownEntities = null;
	private Set allReachableSuperEntities = null;
	private Set allReachableUnknownEntities = null;
	private final Set associatedEntities;
	private final Set composedEntities;
	private final Set containerAggregatedEntities;
	private final Set containerComposedEntities;
	private final Set createdEntities;
	// Yann 2007/08/31: Naming!
	// The Manager to write/read Entities assume that
	// the fields have certain names, be CAREFUL!!!
	private final boolean isAbstract;
	private final boolean isGhost;
	private final boolean isInterface;
	private final Set knownEntities;
	private final Set methodNames;
	private final String name;
	private final Set superEntities;
	private final Set unknownEntities;

	public Entity(
		final String aName,
		final boolean isAbstract,
		final boolean isInterface,
		final boolean isGhost) {

		this.name = aName;
		this.isAbstract = isAbstract;
		this.isInterface = isInterface;
		this.isGhost = isGhost;
		this.aggregatedEntities = new HashSet();
		this.associatedEntities = new HashSet();
		this.composedEntities = new HashSet();
		this.containerAggregatedEntities = new HashSet();
		this.containerComposedEntities = new HashSet();
		this.createdEntities = new HashSet();
		this.knownEntities = new HashSet();
		this.superEntities = new HashSet();
		this.unknownEntities = new HashSet();
		this.methodNames = new HashSet();
	}
	public void addAggregatedEntity(final Entity anEntity) {
		this.aggregatedEntities.add(anEntity);
	}
	public void addAssociatedEntity(final Entity anEntity) {
		this.associatedEntities.add(anEntity);
	}
	public void addComposedEntity(final Entity anEntity) {
		this.composedEntities.add(anEntity);
	}
	public void addContainerAggregatedEntity(final Entity anEntity) {
		this.containerAggregatedEntities.add(anEntity);
	}
	public void addContainerComposedEntity(final Entity anEntity) {
		this.containerComposedEntities.add(anEntity);
	}
	public void addCreatedEntity(final Entity anEntity) {
		this.createdEntities.add(anEntity);
	}
	public void addKnownEntity(final Entity anEntity) {
		this.knownEntities.add(anEntity);
	}
	public void addMethodName(final String aMethodName) {
		this.methodNames.add(aMethodName);
	}
	public void addSuperEntity(final Entity anEntity) {
		this.superEntities.add(anEntity);
	}
	public void addUnknownEntity(final Entity anEntity) {
		this.unknownEntities.add(anEntity);
	}
	public Set getAggregatedEntities() {
		return this.aggregatedEntities;
	}
	public Set getAllReachableAggregatedEntities() {
		if (this.allReachableAggregatedEntities != null) {
			return this.allReachableAggregatedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableAggregatedEntities");
		}
	}
	public Set getAllReachableAssociatedEntities() {
		if (this.allReachableAssociatedEntities != null) {
			return this.allReachableAssociatedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableAssociatedEntities");
		}
	}
	public Set getAllReachableComposedEntities() {
		if (this.allReachableComposedEntities != null) {
			return this.allReachableComposedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableComposedEntities");
		}
	}
	public Set getAllReachableContainerAggregatedEntities() {
		if (this.allReachableContainerAggregatedEntities != null) {
			return this.allReachableContainerAggregatedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableContainerAggregatedEntities");
		}
	}
	public Set getAllReachableContainerComposedEntities() {
		if (this.allReachableContainerComposedEntities != null) {
			return this.allReachableContainerComposedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableContainerComposedEntities");
		}
	}
	public Set getAllReachableCreatedEntities() {
		if (this.allReachableCreatedEntities != null) {
			return this.allReachableCreatedEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableCreatedEntities");
		}
	}
	public Set getAllReachableEntities(final String aFieldName) {
		final ArrayList tempList = new ArrayList();

		final String str = aFieldName.substring(12);
		final String first = "" + str.charAt(0);
		final String field = first.toLowerCase() + str.substring(1);

		final Set firstReachable = this.getList(field);
		tempList.addAll(firstReachable);

		boolean hasChanged = true;

		while (hasChanged) {
			hasChanged = false;

			for (int i = 0; i < tempList.size(); ++i) {
				final Entity e = (Entity) tempList.get(i);
				final Set nextReachable = e.getList(field);
				final Iterator iter = nextReachable.iterator();
				while (iter.hasNext()) {
					final Entity et = (Entity) iter.next();
					if (!tempList.contains(et)) {
						tempList.add(et);
						hasChanged = true;
					}
				}
			}
		}

		final Set list = new HashSet(tempList);
		this.setAllReachableList(list, aFieldName);
		return list;
	}
	public Set getAllReachableKnownEntities() {
		if (this.allReachableKnownEntities != null)
			return this.allReachableKnownEntities;
		else
			return getAllReachableEntities("allReachableKnownEntities");
	}
	public Set getAllReachableList(String listName) {
		Set list = null;

		if (listName.equals("superEntities")) {
			list = this.allReachableSuperEntities;
		}
		else if (listName.equals("unknownEntities")) {
			list = this.allReachableUnknownEntities;
		}
		else if (listName.equals("knownEntities")) {
			list = this.allReachableKnownEntities;
		}
		else if (listName.equals("createdEntities")) {
			list = this.allReachableCreatedEntities;
		}
		else if (listName.equals("containerComposedEntities")) {
			list = this.allReachableContainerComposedEntities;
		}
		else if (listName.equals("containerAggregatedEntities")) {
			list = this.allReachableContainerAggregatedEntities;
		}
		else if (listName.equals("composedEntities")) {
			list = this.allReachableComposedEntities;
		}
		else if (listName.equals("associatedEntities")) {
			list = this.allReachableAssociatedEntities;
		}
		else if (listName.equals("aggregatedEntities")) {
			list = this.allReachableAggregatedEntities;
		}

		return list;
	}
	public Set getAllReachableSuperEntities() {
		if (this.allReachableSuperEntities != null) {
			return this.allReachableSuperEntities;
		}
		else {
			return this.getAllReachableEntities("allReachableSuperEntities");
		}
	}
	public Set getAllReachableUnknownEntities() {
		if (this.allReachableUnknownEntities != null) {
			return this.allReachableUnknownEntities;
		}
		else {
			return this.getAllReachableEntities(
				"allReachableUnknownEntities");
		}
	}
	public Set getAssociatedEntities() {
		return this.associatedEntities;
	}
	public Set getComposedEntities() {
		return this.composedEntities;
	}
	public Set getContainerAggregatedEntities() {
		return this.containerAggregatedEntities;
	}
	public Set getContainerComposedEntities() {
		return this.containerComposedEntities;
	}
	public Set getCreatedEntities() {
		return this.createdEntities;
	}
	public Set getKnownEntities() {
		return this.knownEntities;
	}
	public Set getList(final String aFieldName) {
		Set list = null;

		if (aFieldName.equals("superEntities")) {
			list = this.superEntities;
		}
		else if (aFieldName.equals("unknownEntities")) {
			list = this.unknownEntities;
		}
		else if (aFieldName.equals("knownEntities")) {
			list = this.knownEntities;
		}
		else if (aFieldName.equals("createdEntities")) {
			list = this.createdEntities;
		}
		else if (aFieldName.equals("containerComposedEntities")) {
			list = this.containerComposedEntities;
		}
		else if (aFieldName.equals("containerAggregatedEntities")) {
			list = this.containerAggregatedEntities;
		}
		else if (aFieldName.equals("composedEntities")) {
			list = this.composedEntities;
		}
		else if (aFieldName.equals("associatedEntities")) {
			list = this.associatedEntities;
		}
		else if (aFieldName.equals("aggregatedEntities")) {
			list = this.aggregatedEntities;
		}

		return list;
	}
	public Set getMethodNames() {
		return this.methodNames;
	}
	public String getName() {
		return this.name;
	}
	public Set getSuperEntities() {
		return this.superEntities;
	}
	public Set getUnknownEntities() {
		return this.unknownEntities;
	}
	public boolean isAbstract() {
		return this.isAbstract;
	}
	public boolean isGhost() {
		return this.isGhost;
	}
	public boolean isInterface() {
		return this.isInterface;
	}
	public void setAllReachableList(
		final Set aSet,
		final String aFieldName) {
		if (aFieldName.equals("allReachableSuperEntities")) {
			this.allReachableSuperEntities = aSet;
		}
		else if (aFieldName.equals("allReachableUnknownEntities")) {
			this.allReachableUnknownEntities = aSet;
		}
		else if (aFieldName.equals("allReachableKnownEntities")) {
			this.allReachableKnownEntities = aSet;
		}
		else if (aFieldName.equals("allReachableCreatedEntities")) {
			this.allReachableCreatedEntities = aSet;
		}
		else if (
			aFieldName.equals("allReachableContainerComposedEntities")) {
			this.allReachableContainerComposedEntities = aSet;
		}
		else if (
			aFieldName.equals("allReachableContainerAggregatedEntities")) {
			this.allReachableContainerAggregatedEntities = aSet;
		}
		else if (aFieldName.equals("allReachableComposedEntities")) {
			this.allReachableComposedEntities = aSet;
		}
		else if (aFieldName.equals("allReachableAssociatedEntities")) {
			this.allReachableAssociatedEntities = aSet;
		}
		else if (aFieldName.equals("allReachableAggregatedEntities")) {
			this.allReachableAggregatedEntities = aSet;
		}
	}
	public String toString() {
		return this.getName();
	}
}
