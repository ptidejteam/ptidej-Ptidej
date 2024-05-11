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

import padl.kernel.IFactory;
import padl.kernel.impl.Factory;
import padl.motif.IDesignMotifModel;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.IMotifFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @author Sebastien Colladon
 * @since 2013/07/11
 */
public class MotifFactory extends Factory implements IMotifFactory {
	private static final long serialVersionUID = 3342247491732965777L;

	private static IFactory UniqueInstance;
	public static IFactory getInstance() {
		if (MotifFactory.UniqueInstance == null) {
			MotifFactory.UniqueInstance = new MotifFactory();
		}
		return MotifFactory.UniqueInstance;
	}

	private MotifFactory() {
	}

	public IDesignLevelModel createDesignLevelModel(char[] anID) {
		final IDesignLevelModel designLevelModel = new DesignLevelModel(anID);
		((DesignLevelModel) designLevelModel).setFactory(this);
		((DesignLevelModel) designLevelModel).setEventGenerator(this
			.getEventGenerator());
		return designLevelModel;
	}
	public IDesignMotifModel createDesignMotifModel(char[] anID) {
		final IDesignMotifModel designMotifModel = new DesignMotifModel(anID);
		((DesignMotifModel) designMotifModel).setFactory(this);
		// TODO Shouldn't it be implemented? 
		//	((DesignMotifModel) designMotifModel).setEventGenerator(this.getEventGenerator());
		return designMotifModel;
	}
}
