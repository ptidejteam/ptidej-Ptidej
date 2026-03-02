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
package padl.event;

public interface IModelListener {
	String ELEMENT_ADDED = "elementAdded";
	String ELEMENT_ANALYZED = "elementAnalyzed";
	String ELEMENT_RECOGNIZED = "elementRecognized";
	String ELEMENT_REMOVED = "elementRemoved";
	String ELEMENT_SKIPPED = "elementSkipped";

	String ENTITY_ADDED = "entityAdded";
	String ENTITY_ANALYZED = "entityAnalyzed";
	String ENTITY_RECOGNIZED = "entityRecognized";
	String ENTITY_REMOVED = "entityRemoved";
	String ENTITY_SKIPPED = "entitySkipped";

	String INVOKE_ADDED = "invokeAdded";
	String INVOKE_ANALYZED = "invokeAnalyzed";
	String INVOKE_RECOGNIZED = "invokeRecognized";
	String INVOKE_REMOVED = "invokeRemoved";
	String INVOKE_SKIPPED = "invokeSkipped";

	void elementAdded(final ElementEvent elementEvent);
	void elementAnalyzed(final AnalysisEvent analisysEvent);
	void elementIdentified(final IdentificationEvent recognitionEvent);
	void elementRemoved(final ElementEvent elementEvent);
	void elementSkipped(final AnalysisEvent analisysEvent);

	void entityAdded(final EntityEvent entityEvent);
	void entityAnalyzed(final AnalysisEvent analisysEvent);
	void entityIdentified(final IdentificationEvent recognitionEvent);
	void entityRemoved(final EntityEvent entityEvent);
	void entitySkipped(final AnalysisEvent analisysEvent);

	void invokeAdded(final InvokeEvent invokeEvent);
	void invokeAnalyzed(final AnalysisEvent analisysEvent);
	void invokeIdentified(final IdentificationEvent recognitionEvent);
	void invokeRemoved(final InvokeEvent invokeEvent);
	void invokeSkipped(final AnalysisEvent analisysEvent);
}
