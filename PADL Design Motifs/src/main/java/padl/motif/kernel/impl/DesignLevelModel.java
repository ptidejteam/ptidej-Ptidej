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
package padl.motif.kernel.impl;

import padl.event.IMotifModelListener;
import padl.event.PatternEvent;
import padl.kernel.Constants;
import padl.kernel.IConstituent;
import padl.kernel.IEntity;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;
import padl.kernel.exception.ModelDeclarationException;
import padl.kernel.impl.AbstractLevelModel;
import padl.motif.IDesignMotifModel;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.IDesignLevelModelCreator;
import util.lang.ConcreteReceiverGuard;
import util.multilingual.MultilingualManager;

class DesignLevelModel extends AbstractLevelModel implements IDesignLevelModel,
		Cloneable {

	private static final long serialVersionUID = 3774766840113450647L;

	public DesignLevelModel() {
		this(Constants.DEFAULT_DESIGNLEVELMODEL_ID);
	}
	public DesignLevelModel(final char[] aName) {
		super(aName);
	}
	public final void addConstituent(final IConstituent aConstituent) {
		// Yann 2008/11/04: No no-package!
		// In the process of adding the packages consistently,
		// I choose that no entity can be without package. Even
		// though it may not make sense for languages like C++.
		//	if (aConstituent instanceof IEntity) {
		//		this.addConstituent((IEntity) aConstituent);
		//	}
		//	else 
		if (aConstituent instanceof IEntity) {
			this.addConstituent((IEntity) aConstituent);
		}
		else 		if (aConstituent instanceof IPackage) {
			this.addConstituent((IPackage) aConstituent);
		}
		else if (aConstituent instanceof IDesignMotifModel) {
			this.addConstituent((IDesignMotifModel) aConstituent);
		}
		else {
			throw new ModelDeclarationException(MultilingualManager.getString(
				"ENT_OR_PATTERN_ADD",
				DesignLevelModel.class));
		}
	}
	public final void addConstituent(final IDesignMotifModel aMotif) {
		this.addConstituent(aMotif);
		this.fireModelChange(
			IMotifModelListener.PATTERN_ADDED,
			new PatternEvent(this, aMotif));
	}
	public void addConstituent(final IPackage aPackage) {
		super.addConstituent(aPackage);
	}
	public void create(final IDesignLevelModelCreator aDesignLevelModelCreator)
			throws CreationException {

		ConcreteReceiverGuard
			.getInstance()
			.checkCallingClassName(
				"padl.generator.helper.ModelGenerator",
				"Please use the methods in \"padl.generator.helper.ModelGenerator\" to obtain design-level models.");

		aDesignLevelModelCreator.create(this);
	}
}
