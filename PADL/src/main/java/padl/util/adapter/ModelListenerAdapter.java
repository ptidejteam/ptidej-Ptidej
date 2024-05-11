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
package padl.util.adapter;

import padl.event.AnalysisEvent;
import padl.event.ElementEvent;
import padl.event.EntityEvent;
import padl.event.IModelListener;
import padl.event.IdentificationEvent;
import padl.event.InvokeEvent;

public class ModelListenerAdapter implements IModelListener {
	public void elementAdded(final ElementEvent elementEvent) {
	}
	public void elementAnalyzed(final AnalysisEvent analisysEvent) {
	}
	public void elementIdentified(final IdentificationEvent recognitionEvent) {
	}
	public void elementRemoved(final ElementEvent elementEvent) {
	}
	public void elementSkipped(final AnalysisEvent analisysEvent) {
	}
	public void entityAdded(final EntityEvent entityEvent) {
	}
	public void entityAnalyzed(final AnalysisEvent analisysEvent) {
	}
	public void entityIdentified(final IdentificationEvent recognitionEvent) {
	}
	public void entityRemoved(final EntityEvent entityEvent) {
	}
	public void entitySkipped(final AnalysisEvent analisysEvent) {
	}
	public void invokeAdded(final InvokeEvent invokeEvent) {
	}
	public void invokeAnalyzed(final AnalysisEvent analisysEvent) {
	}
	public void invokeIdentified(final IdentificationEvent recognitionEvent) {
	}
	public void invokeRemoved(final InvokeEvent invokeEvent) {
	}
	public void invokeSkipped(final AnalysisEvent analisysEvent) {
	}
}
