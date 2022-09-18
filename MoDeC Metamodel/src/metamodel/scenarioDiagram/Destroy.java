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
package metamodel.scenarioDiagram;

import java.util.Iterator;
import java.util.List;

public class Destroy extends metamodel.scenarioDiagram.Message {

	public Destroy(String signature, List arguments,
		Classifier sourceClassifier,
		Classifier destinationClassifier) {
		super(signature, arguments, sourceClassifier, destinationClassifier);
	}

	//	public Destroy (String info)
	//	{
	//		super(info);
	//	}

	public String toString() {
		String info = super.toString() + "<DESTROY>" + this.signature + " (";
		Iterator lt = (Iterator) this.arguments.iterator();
		while (lt.hasNext())
			info += (Argument) lt.next() + ", ";

		if (info.lastIndexOf(",") != -1)
			info = info.substring(0, info.lastIndexOf(","));

		return info + ") CALLEE " + this.destinationClassifier + " CALLER " + this.sourceClassifier + "\n";
	}

}
