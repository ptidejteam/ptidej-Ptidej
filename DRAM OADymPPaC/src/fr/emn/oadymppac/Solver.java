/*
 * $Id: Solver.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac;

import fr.emn.oadymppac.event.ActivateListener;
import fr.emn.oadymppac.event.DeactivateListener;
import fr.emn.oadymppac.event.NewConstraintListener;
import fr.emn.oadymppac.event.NewVariableListener;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.event.RejectListener;
import fr.emn.oadymppac.event.RestoreListener;
import fr.emn.oadymppac.event.SelectConstraintListener;
import fr.emn.oadymppac.event.SelectUpdateListener;
import fr.emn.oadymppac.event.SolutionListener;
import fr.emn.oadymppac.event.SolverListener;
import fr.emn.oadymppac.event.StageListener;
import fr.emn.oadymppac.event.SuspendListener;
import fr.emn.oadymppac.event.TellListener;
import fr.emn.oadymppac.event.TrueListener;
import fr.emn.oadymppac.event.WakeUpListener;

/**
 * Interface for accessing events read from a trace file.
 * For each event generated in the trace, the interface specifies a
 * listener list.  Each instance interested in specific event should
 * register a listener and specify the precision it requires for this
 * event.  The application and parser are allowed to send more details,
 * for instance if another registered instance required these details,
 * or if the implementation doesn't really filter it.
 * It is expected that implementations will forward the precision
 * information to the running program to reduce the amount of
 * information produced in the trace.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public interface Solver {
	public static final short FILTER_NONE = 0;
	public static final short FILTER_VALUE_LIST = 1;
	public static final short FILTER_UPDATE_LIST = 2;
	public static final short FILTER_STATE_ALIST = 4;
	public static final short FILTER_STATE_SLIST = 8;
	public static final short FILTER_STATE_QLIST = 16;
	public static final short FILTER_STATE_TLIST = 32;
	public static final short FILTER_STATE_RLIST = 64;
	public static final short FILTER_STATE_ULIST = 128;
	public static final short FILTER_STATE_VLIST = 256;
	public static final short FILTER_STATE_MISC = 512;
	public static final short FILTER_STATE = Solver.FILTER_STATE_ALIST
			| Solver.FILTER_STATE_SLIST | Solver.FILTER_STATE_QLIST
			| Solver.FILTER_STATE_TLIST | Solver.FILTER_STATE_RLIST
			| Solver.FILTER_STATE_ULIST | Solver.FILTER_STATE_VLIST
			| Solver.FILTER_STATE_MISC;
	public static final short FILTER_EXPLANATION_LIST = 1024;
	public static final short FILTER_EXPLANATION_CAUSES = 2048;
	public static final short FILTER_EXPLANATION_CONSTRAINTS = 4096;
	public static final short FILTER_CAUSE_LIST = 8192;
	public static final short FILTER_VARDOMAIN_LIST = 16384;
	public static final short FILTER_ALL = 32767;

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_EXPLANATION_LIST
	 **/
	public void addActivateListener(ActivateListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addDeactivateListener(DeactivateListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_UPDATE_LIST
	 **/
	public void addNewConstraintListener(NewConstraintListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_VALUE_LIST
	 **/
	public void addNewVariableListener(NewVariableListener l, short filter);

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_UPDATE_LIST
	 * - FILTER_EXPLANATION_LIST
	 **/
	public void addReduceListener(ReduceListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_EXPLANATION_LIST
	 **/
	public void addRejectListener(RejectListener l, short filter);

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_UPDATE_LIST
	 **/
	public void addRestoreListener(RestoreListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addSelectConstraintListener(
		SelectConstraintListener l,
		short filter);

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_VALUE_LIST
	 **/
	public void addSelectUpdateListener(SelectUpdateListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addSolutionListener(SolutionListener l, short filter);

	public void addSolverListener(SolverListener l);
	public void addStageListener(StageListener l, short filter);

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addSuspendListener(SuspendListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addTellListener(TellListener l, short filter);

	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 **/
	public void addTrueListener(TrueListener l, short filter);
	/**
	 * Meaningful values for filter:
	 * - FILTER_STATE*
	 * - FILTER_CAUSE_LIST
	 * - FILTER_VARDOMAIN_LIST
	 **/
	public void addWakeUpListener(WakeUpListener l, short filter);

	/**
	 * Returns the value of the property with the specified key.  Only
	 * properties added with <code>putClientProperty</code> will return
	 * a non-null value.  
	 * 
	 * @return the value of this property or null
	 * @see #putClientProperty
	 */
	public Object getClientProperty(Object key);
	/**
	 * Adds an arbitrary key/value "client property" to this component.
	 * <p>
	 * The <code>get/putClientProperty</code> methods provide access to 
	 * a small per-instance hashtable. Callers can use get/putClientProperty
	 * to annotate solvers that were created by another module. 
	 * If value is null this method will remove the property.
	 * 
	 * @see #getClientProperty
	 * @see #addPropertyChangeListener
	 */
	public void putClientProperty(Object key, Object value);

	public void removeActivateListener(ActivateListener l);
	public void removeDeactivateListener(DeactivateListener l);

	public void removeNewConstraintListener(NewConstraintListener l);
	public void removeNewVariableListener(NewVariableListener l);

	public void removeReduceListener(ReduceListener l);
	public void removeRejectListener(RejectListener l);

	public void removeRestoreListener(RestoreListener l);
	public void removeSelectConstraintListener(SelectConstraintListener l);

	public void removeSelectUpdateListener(SelectUpdateListener l);
	public void removeSolutionListener(SolutionListener l);

	public void removeSolverListener(SolverListener l);
	public void removeStageListener(StageListener l);

	public void removeSuspendListener(SuspendListener l);
	public void removeTellListener(TellListener l);

	public void removeTrueListener(TrueListener l);
	public void removeWakeUpListener(WakeUpListener l);

	public void resume();
	public void start();

	public void stop();

	public void suspend();
}
