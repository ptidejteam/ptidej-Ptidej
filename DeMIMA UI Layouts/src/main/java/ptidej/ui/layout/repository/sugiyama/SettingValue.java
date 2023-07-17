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
/**
 * 
 */
package ptidej.ui.layout.repository.sugiyama;

/**
 * @author kahlamoh
 * @since 26/07/2006
 *
 */
// TODO: Very good idea to have a single object holds all the
// parameters values. This object should be passed to the
// SugiyamaLayout constructor.
public abstract class SettingValue {

	// debug!
	public static int debugPrintingLevel; // between 0 and 10

	// Crossing Minimising
	// for n-level crossing minimizing
	public static int maxNumberOfRounds;
	public static int maxNbRoundsWhithoutProgress;

	// maybe not necessairy
	public static int maxRoundsWhithoutProgressSinceReversion;

	// for 2-levels crossing minimising
	public static int maxNbIterationPhase2;
	public static int maxNbIterationPhase1;

	// priority Horizental Layout
	public static int maxIterationsHorizentalLayout;

	// Coordonates setting!
	public static int horizentalDistanceEntities;
	public static int verticalDistanceEntities;
	public static int offsetFromLeftSide;
	public static int offsetFromUpperSide;

	// Lines Drawing!
	public static int defaultSplitter;
	public static int distanceBetweenChannels;
	// select the type of arrows : simple or double Lines
	// or round or ...
	public static int arrowForm; // default : double for generalisations
	// directed for the others!

	// for Graph dimention!
	public static int zoom; // 1 : real size!

	// this option is for when you select an entity
	// and you want to view only this Entity whith 
	// the number of levels selected up and down if present!
	public static int maxViewingLevelSelected;

	// ///////

	/**
	 * 
	 */
	public SettingValue() {

		// defalt values loaded!

		setDebugPrintingLevel(0);
		setMaxNumberOfRounds(20);
		setMaxNbRoundsWhithoutProgress(10);
		setMaxNbIterationPhase2(20);
		setMaxNbIterationPhase1(100);
		setMaxIterationsHorizentalLayout(100);
		setHorizentalDistanceEntities(10);
		setVerticalDistanceEntities(100);
		setOffsetFromLeftSide(20);
		setOffsetFromUpperSide(20);
		setDefaultSplitter(45);
		setDistanceBetweenChannels(45);
		setAarrowForm(10);
		setZoom(0);
		setDebugPrintingLevel(1);
		setMaxViewingLevelSelected(3);
	}

	/**
	 * 
	 */
	public SettingValue(
		int aDebugPrintingLevel,
		int aMaxNumberOfRounds,
		int aMaxNbRoundsWhithoutProgress,
		int aMaxNbIterationPhase2,
		int aMaxNbIterationPhase1,
		int aMaxIterationsHorizentalLayout,
		int aHorizentalDistanceEntities,
		int aVerticalDistanceEntities,
		int aDefaultSplitter,
		int aDistanceBetweenChannels,
		int anAarrowForm,
		int aZoom,
		int aMaxViewingLevelSelected) {

		setDebugPrintingLevel(aDebugPrintingLevel);
		setMaxNumberOfRounds(aMaxNumberOfRounds);
		setMaxNbRoundsWhithoutProgress(aMaxNbRoundsWhithoutProgress);
		setMaxNbIterationPhase2(aMaxNbIterationPhase2);
		setMaxNbIterationPhase1(aMaxNbIterationPhase1);
		setMaxIterationsHorizentalLayout(aMaxIterationsHorizentalLayout);
		setHorizentalDistanceEntities(aHorizentalDistanceEntities);
		setVerticalDistanceEntities(aVerticalDistanceEntities);
		setDefaultSplitter(aDefaultSplitter);
		setDistanceBetweenChannels(aDistanceBetweenChannels);
		setAarrowForm(anAarrowForm);
		setZoom(aZoom);
		setDebugPrintingLevel(aDebugPrintingLevel);
		setMaxViewingLevelSelected(aMaxViewingLevelSelected);

	}

	// ///////

	// setting methods
	/**
	 * @param aNumber
	 */
	public static void setDebugPrintingLevel(int aNumber) {
		if (aNumber >= 0)
			debugPrintingLevel = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxNumberOfRounds(int aNumber) {
		if (aNumber >= 0)
			maxNumberOfRounds = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxNbRoundsWhithoutProgress(int aNumber) {
		if (aNumber >= 0)
			maxNbRoundsWhithoutProgress = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxNbIterationPhase2(int aNumber) {
		if (aNumber >= 0)
			maxNbIterationPhase2 = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxNbIterationPhase1(int aNumber) {
		if (aNumber >= 0)
			maxNbIterationPhase1 = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxIterationsHorizentalLayout(int aNumber) {
		if (aNumber >= 0)
			maxIterationsHorizentalLayout = aNumber;
	}

	/**
	 * 
	 */
	public static void setHorizentalDistanceEntities(int aNumber) {
		if (aNumber >= 0)
			horizentalDistanceEntities = aNumber;
	}

	/**
	 * 
	 */
	public static void setVerticalDistanceEntities(int aNumber) {
		if (aNumber >= 0)
			verticalDistanceEntities = aNumber;
	}

	/**
	 * 
	 */
	public static void setOffsetFromLeftSide(int aNumber) {
		if (aNumber >= 0)
			offsetFromLeftSide = aNumber;
	}

	/**
	 * 
	 */
	public static void setOffsetFromUpperSide(int aNumber) {
		if (aNumber >= 0)
			offsetFromUpperSide = aNumber;
	}

	/**
	 * 
	 */
	public static void setDefaultSplitter(int aNumber) {
		if (aNumber >= 0)
			defaultSplitter = aNumber;
	}

	/**
	 * 
	 */
	public static void setDistanceBetweenChannels(int aNumber) {
		if (aNumber >= 0)
			distanceBetweenChannels = aNumber;
	}

	/**
	 * 
	 */
	public static void setAarrowForm(int aNumber) {
		if (aNumber >= 0)
			arrowForm = aNumber;
	}

	/**
	 * 
	 */
	public static void setZoom(int aNumber) {
		if (aNumber > 0)
			zoom = aNumber;
	}

	/**
	 * 
	 */
	public static void setMaxViewingLevelSelected(int aNumber) {
		if (aNumber >= 0)
			maxViewingLevelSelected = aNumber;
	}

	// setting methods

	/**
	 * 
	 */
	public static int getDebugPrintingLevel() {
		return debugPrintingLevel;
	}

	/**
	 * 
	 */
	public static int getMaxNumberOfRounds() {
		return maxNumberOfRounds;
	}

	/**
	 * 
	 */
	public static int getMaxNbRoundsWhithoutProgress() {
		return maxNbRoundsWhithoutProgress;
	}

	/**
	 * 
	 */
	public static int getMaxNbIterationPhase2() {
		return maxNbIterationPhase2;
	}

	/**
	 * 
	 */
	public static int getMaxNbIterationPhase1() {
		return maxNbIterationPhase1;
	}

	/**
	 * 
	 */
	public static int getMaxIterationsHorizentalLayout() {
		return maxIterationsHorizentalLayout;
	}

	/**
	 * 
	 */
	public static int getHorizentalDistanceEntities() {
		return horizentalDistanceEntities;
	}

	/**
	 * 
	 */
	public static int getVerticalDistanceEntities() {
		return verticalDistanceEntities;
	}

	/**
	 * 
	 */
	public static int getOffsetFromLeftSide() {
		return offsetFromLeftSide;
	}

	/**
	 * 
	 */
	public static int getOffsetFromUpperSide() {
		return offsetFromUpperSide;
	}

	/**
	 * 
	 */
	public static int getDefaultSplitter() {
		return defaultSplitter;
	}

	/**
	 * 
	 */
	public static int getDistanceBetweenChannels() {
		return distanceBetweenChannels;
	}

	/**
	 * 
	 */
	public static int getAarrowForm() {
		return arrowForm;
	}

	/**
	 * 
	 */
	public static int getZoom() {
		return zoom;
	}

	/**
	 * 
	 */
	public static int getMaxViewingLevelSelected() {
		return maxViewingLevelSelected;
	}

}
