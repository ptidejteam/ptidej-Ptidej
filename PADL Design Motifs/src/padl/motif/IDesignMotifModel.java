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
package padl.motif;

import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.motif.detector.IDetector;

/**
 * @author Yann-Gaël Guéhéneuc
 */
public interface IDesignMotifModel extends IAbstractModel, IConstituent {
	// Classification type constants.
	int BEHAVIORAL_DESIGN_MOTIF = 1;
	int CREATIONAL_DESIGN_MOTIF = 2;
	int DEFECT_MOTIF = 4;
	int SOLUTION_MOTIF = 8;
	int STRUCTURAL_DESIGN_MOTIF = 16;
	int TEST_MOTIF = 32;
	int UNDEFINED_MOTIF = 64;

	int getClassification();
	IDetector getDetector();
	String getIntent();
	void setClassification(final int aClassification);
	void setDetector(final IDetector aDetector);
	void setIntent(final String anIntent);
}
