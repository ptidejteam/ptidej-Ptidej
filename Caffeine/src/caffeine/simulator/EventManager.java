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
package caffeine.simulator;

import JIP.engine.JIPAtom;
import JIP.engine.JIPFunctor;
import JIP.engine.JIPList;
import JIP.engine.JIPNumber;
import JIP.engine.JIPTerm;
import JIP.engine.JIPXCall;
import caffeine.Constants;
import caffeine.logic.Event;

/**
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */
public final class EventManager extends JIPXCall {
	private static String[] EventData = new String[0];
	private static int NumberOfEvents = 0;

	/*
	 * Some private static variable to link the
	 * EventManager, the corresponding Java Virtual
	 * Machine, and the Prolog engine.
	 * These fields are static because the Prolog
	 * engine creates an instance of this class each
	 * time the invoke(JIPTerm) method returns.
	 */
	private static boolean IsJVMInitialized = false;

	public JIPTerm getOutput() {
		// Yann 2002/07/03: Commented events
		// If the line from the data starts with //,
		// then I skip it and go to the next line of
		// the data.
		// Warning! I assume there is a least ONE
		// un-commented event after commented ones...
		while (EventManager
			.EventData[EventManager
			.NumberOfEvents]
			.startsWith("//")) {

			EventManager.NumberOfEvents++;
		}
		final Event event =
			Event.fromTraceData(
				EventManager.NumberOfEvents,
				EventManager.EventData[EventManager.NumberOfEvents]);
		EventManager.NumberOfEvents++;

		JIPTerm result =
			JIPFunctor.getNewFunctor(
				event.getType(),
				JIPList.getNewList(
					JIPNumber.getNewNumber(event.getIdentifier()),
					JIPList.getNewList(
						JIPAtom.getNewAtom(event.getName()),
						JIPList.getNewList(
							JIPNumber.getNewNumber(event.getReceiverID()),
							JIPList.getNewList(
								JIPNumber.getNewNumber(event.getArgumentID()),
								JIPList.getNewList(
									JIPAtom.getNewAtom(event.getComment()),
									JIPList.getNewList(
										JIPAtom.getNewAtom(
											event
												.getReturnedValue()
												.toString()),
										JIPList.getNewList(
											JIPAtom.getNewAtom(
												event.getSourceName()),
											JIPList.getNewList(
												JIPNumber.getNewNumber(
													event.getLineNumber()),
												null)))))))));

		if (Constants.DEBUG) {
			System.out.print("EventManager.getOutput() = ");
			System.out.println(result);
		}

		return result;
	}
	public boolean invoke(final JIPTerm jipTerm) {
		if (!EventManager.IsJVMInitialized) {
			EventManager.EventData =
				((PrologEngine) this.getJIPEngine()).getEventData();
			EventManager.IsJVMInitialized = true;
		}
		if (EventManager.NumberOfEvents < EventManager.EventData.length) {
			return true;
		}
		else {
			return false;
		}
	}
	public boolean isDeterministic() {
		return false;
	}
}
