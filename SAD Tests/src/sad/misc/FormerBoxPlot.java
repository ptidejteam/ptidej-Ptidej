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
package sad.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * DO NOT USE
 * Legacy code which fails test!
 * (Kept here for the educational purpose :-))
 */
public class FormerBoxPlot {
	public final static int TUKEY = 1;

	public final static int TEXAS_INSTRUMENTS = 2;

	/**
	 * The multplicity is by default 1.5 Why 1.5? The "official" answer from
	 * John Tukey (when I asked) is: because 1 is too small and 2 is too large.
	 */
	private final double facteurBoxPlot = 1.5;

	private final Map mapOfNormalValues;
	private final Map mapOfHigerValues;
	private final Map mapOfLowerValues;
	private double minBound;
	private double maxBound;

	private final int methodMode;

	/**
	 * BoxPlot_Duke by Tukey's Method, just select 1 (with duplication) BoxPlot_Duke by
	 * The TexasInstruments Corp Method, just select 2 (with supression)
	 */
	public FormerBoxPlot(final int methodMode) {
		this.methodMode = methodMode;

		/**
		 * TODO throw an exception if methodMode is not 1 nor 2!!
		 */

		this.mapOfLowerValues = new HashMap();
		this.mapOfNormalValues = new HashMap();
		this.mapOfHigerValues = new HashMap();

		this.setMinBound(-1);
		this.setMaxBound(-1);
	}

	private void compute(
		final double min,
		final double max,
		final Map mapOfEntities) {
		this.mapOfLowerValues.clear();
		this.mapOfNormalValues.clear();
		this.mapOfHigerValues.clear();

		final List orderedEntities = this.sortEntities(mapOfEntities);
		double quartile01;
		double quartile03;

		/**
		 * Juste pour savoir si l'ensemble contient un nombre d'éléments pair ou
		 * impair
		 */
		if ((double) orderedEntities.size() / 2 == (int) ((double) orderedEntities
			.size() / 2)) {
			// Si Pair

			quartile01 =
				Double.parseDouble(mapOfEntities.get(orderedEntities
					.get(orderedEntities.size() / 4)) + "");
			quartile03 =
				Double.parseDouble(mapOfEntities.get(orderedEntities
					.get(orderedEntities.size() - orderedEntities.size() / 4))
						+ "");
		}
		else
		// Si Impair
		{
			switch (this.methodMode) {
				case 1 :
					final int indMiddle01 = orderedEntities.size() / 2;
					final Object obj01 = orderedEntities.get(indMiddle01);

					orderedEntities.add(orderedEntities.size() / 2, obj01);

					quartile01 =
						Double.parseDouble(mapOfEntities.get(orderedEntities
							.get(indMiddle01 / 2)) + "");
					quartile03 =
						Double.parseDouble(mapOfEntities.get(orderedEntities
							.get(orderedEntities.size() - indMiddle01 / 2 - 1))
								+ "");
					break;
				case 2 :
					final int indMiddle02 = orderedEntities.size() / 2;
					final Object obj02 = orderedEntities.get(indMiddle02);

					orderedEntities.remove(obj02);

					quartile01 =
						Double.parseDouble(mapOfEntities.get(orderedEntities
							.get(indMiddle02 / 2)) + "");
					quartile03 =
						Double.parseDouble(mapOfEntities.get(orderedEntities
							.get(orderedEntities.size() - indMiddle02 / 2 - 1))
								+ "");
					break;
				default :
					quartile01 = 0;
					quartile03 = 0;
			}
		}

		final double IRQ = quartile03 - quartile01;

		if (min != -1 || max != -1) {
			this.setMinBound(min);
			this.setMaxBound(max);
		}
		else {
			this.setMinBound(quartile01 - IRQ * this.facteurBoxPlot);
			this.setMaxBound(quartile03 + IRQ * this.facteurBoxPlot);
		}

		final Iterator iter = mapOfEntities.keySet().iterator();

		while (iter.hasNext()) {
			final Object entity = iter.next();

			if (Double.parseDouble(mapOfEntities.get(entity).toString()) < this
				.getMinBound()) {
				this.mapOfLowerValues.put(entity, mapOfEntities.get(entity));
			}
			else if (this.getMaxBound() < Double.parseDouble(mapOfEntities.get(
				entity).toString())) {
				this.mapOfHigerValues.put(entity, mapOfEntities.get(entity));
			}
			else {
				this.mapOfNormalValues.put(entity, mapOfEntities.get(entity));
			}
		}
	}

	public void compute(final Map mapOfEntities) {
		this.compute(-1, -1, mapOfEntities);
	}

	public void compute(
		final Map mapOfEntities,
		final double min,
		final double max) {
		this.compute(min, max, mapOfEntities);
	}

	public Map getHigherValues() {
		return this.mapOfHigerValues;
	}

	public Map getLowerValues() {
		return this.mapOfLowerValues;
	}

	public double getMaxBound() {
		return this.maxBound;
	}

	public double getMinBound() {
		return this.minBound;
	}

	public Map getNormalValues() {
		return this.mapOfNormalValues;
	}

	private void setMaxBound(final double maxBound) {
		this.maxBound = maxBound;
	}

	private void setMinBound(final double minBound) {
		this.minBound = minBound;
	}

	/*
	 * TODO ameliorer l'algo de trie!!
	 */

	private List sortEntities(final Map mapOfEntities) {
		final List orderedEntities = new ArrayList();

		final Iterator iter01 = mapOfEntities.keySet().iterator();

		while (iter01.hasNext()) {

			final Object entity = iter01.next();

			if (orderedEntities.size() == 0) {
				orderedEntities.add(entity);
			}

			final Iterator iter02 = orderedEntities.iterator();

			while (iter02.hasNext()) {
				final Object entity02 = iter02.next();

				if (Double.parseDouble(mapOfEntities.get(entity).toString()) < Double
					.parseDouble(mapOfEntities.get(entity02).toString())) {
					orderedEntities.add(
						orderedEntities.indexOf(entity02),
						entity);
					break;
				}
			}

			if (orderedEntities.indexOf(entity) == -1) {
				orderedEntities.add(entity);
			}
		}

		return orderedEntities;
	}

}
