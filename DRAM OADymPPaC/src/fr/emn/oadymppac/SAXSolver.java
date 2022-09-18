/*
 * $Id: SAXSolver.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import java.util.StringTokenizer;
import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import fr.emn.oadymppac.event.ActivateEvent;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.DeactivateEvent;
import fr.emn.oadymppac.event.EventAttributes;
import fr.emn.oadymppac.event.NewConstraintEvent;
import fr.emn.oadymppac.event.NewStageEvent;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.RejectEvent;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.SelectConstraintEvent;
import fr.emn.oadymppac.event.SelectUpdateEvent;
import fr.emn.oadymppac.event.SolutionEvent;
import fr.emn.oadymppac.event.SolverEvent;
import fr.emn.oadymppac.event.StageEvent;
import fr.emn.oadymppac.event.SuspendEvent;
import fr.emn.oadymppac.event.TellEvent;
import fr.emn.oadymppac.event.TrueEvent;
import fr.emn.oadymppac.event.WakeUpEvent;

/**
 * Implementation of the <code>Solver</code> interface relying on the
 * <code>SAX</code> API for parsing XML documents.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public class SAXSolver extends AbstractSolver implements
		org.xml.sax.ContentHandler {
	/**
	 * Initializes all the attributes of the event <code>ev</code> with the
	 * values passed in the XML stream or, if the values are missing,
	 * the default values are applied :<br>
	 * <blockquote>
	 * <code>depth</code> : <code>BasicSolverEvent.DEPTH_NONE</code>,<br>
	 * <code>n</code> : -1,<br>
	 * <code>time</code> : <code>SoverEvent.TIME_NONE</code>,<br>
	 * <code>context</code> : <code>null</code>.
	 * </blockquote>
	 */
	static void parseBasicSolverAttributes(
		final Attributes atts,
		final BasicSolverEvent ev) {
		String val;

		val = atts.getValue("depth");
		ev.setDepth(EventAttributes.DEPTH_NONE);

		if (val != null) {
			try {
				ev.setDepth(Integer.parseInt(val));
			}
			catch (final NumberFormatException e) { // try harder
				System.err.println("Invalid number format for depth: " + val);

				for (int i = 0; i < val.length(); i++) {
					if (Character.isDigit(val.charAt(i))) {
						int j = i + 1;

						while (j < val.length()
								&& Character.isDigit(val.charAt(j))) {
							j++;
						}

						final int res = Integer.parseInt(val.substring(i, j));
						System.err.println(" number found: " + res);
						ev.setDepth(res);

						break;
					}
				}
			}
		}

		ev.setN(-1);
		val = atts.getValue("n");

		if (val != null) {
			try {
				ev.setN(Integer.parseInt(val));
			}
			catch (final NumberFormatException e) {
			}
		}

		ev.setTime(EventAttributes.TIME_NONE);
		val = atts.getValue("time");

		if (val != null) {
			try {
				ev.setTime(Long.parseLong(val));
			}
			catch (final NumberFormatException e) {
			}
		}

		ev.setContext(atts.getValue("context"));

		ev.setStages(null);
		val = atts.getValue("stages");

		if (val != null) {
			final StringTokenizer tok = new StringTokenizer(val);
			final Vector v = new Vector();

			while (tok.hasMoreTokens()) {
				v.addElement(tok.nextToken().intern());
			}

			final String[] alloc_stages = (String[]) v.toArray();
			ev.setStages(alloc_stages);
		}
	}
	/**
	 * Calls <code>parseBasicSolverAttributes</code> and initializes
	 * the additional constraint attributes with the values passed in the
	 * XML stream or with their default values :<br>
	 * <blockquote>
	 * <code>cname</code> : <code>null</code>,<br>
	 * <code>externalRep</code> : <code>null</code>,<br>
	 * <code>internelRep</code> : <code>null</code>.
	 * </blockquote>
	 */
	static void parseConstraintAttributes(
		final Attributes atts,
		final SolverEvent ev) {
		String val;
		SAXSolver.parseBasicSolverAttributes(atts, ev);
		val = atts.getValue("cname");

		if (val != null) {
			val = val.intern();
		}

		ev.setCName(val);
		ev.setExternalRep(atts.getValue("externalrep"));
		ev.setInternalRep(atts.getValue("internalrep"));
	}
	/**
	 * Parses a string representation of values taken by variables which can be
	 * a numeral or a string.
	 */
	static int parseInt(final String val) {
		try {
			return Integer.parseInt(val);
		}
		catch (final NumberFormatException e) {
			return -1;
		}
	}
	/**
	 * Returns the precision of information conveyed in the <code>state</code>.
	 * Possible values are :<br>
	 * <blockquote>
	 * <code>State.PRECISION_CHANGED</code>,<br>
	 * <code>State.PRECISION_FULL</code>,<br>
	 * <code>State.PRECISION_NONE</code>.
	 * </blockquote>
	 */
	static short parsePrecision(final String val) {
		if (val.equals("changed")) {
			return State.PRECISION_CHANGED;
		}
		else if (val.equals("full")) {
			return State.PRECISION_FULL;
		}

		return State.PRECISION_NONE;
	}
	/**
	 * Returns the type of updates. Possible types are :<br>
	 * <blockquote>
	 * <code>SolverConstants.TYPE_NONE</code>,<br>
	 * <code>SolverConstants.TYPE_GROUND</code>,<br>
	 * <code>SolverConstants.TYPE_ANY</code>,<br>
	 * <code>SolverConstants.TYPE_MIN</code>,<br>
	 * <code>SolverConstants.TYPE_MAX</code>,<br>
	 * <code>SolverConstants.TYPE_MINMAX</code>,<br>
	 * <code>SolverConstants.TYPE_EMPTY</code>,<br>
	 * <code>SolverConstants.TYPE_VAL</code>,<br>
	 * <code>SolverConstants.TYPE_NOTHING</code>.
	 * </blockquote>
	 */
	static short parseUpdateType(final String val) {
		if (val == null) {
			return SolverConstants.TYPE_NONE;
		}

		if (val.equals("ground")) {
			return SolverConstants.TYPE_GROUND;
		}
		else if (val.equals("any")) {
			return SolverConstants.TYPE_ANY;
		}
		else if (val.equals("min")) {
			return SolverConstants.TYPE_MIN;
		}
		else if (val.equals("max")) {
			return SolverConstants.TYPE_MAX;
		}
		else if (val.equals("minmax")) {
			return SolverConstants.TYPE_MINMAX;
		}
		else if (val.equals("empty")) {
			return SolverConstants.TYPE_EMPTY;
		}
		else if (val.equals("val")) {
			return SolverConstants.TYPE_VAL;
		}
		else if (val.equals("nothing")) {
			return SolverConstants.TYPE_NOTHING;
		}

		return SolverConstants.TYPE_NONE;
	}
	/**
	 * Returns the type of variables. Possible types are :<br>
	 * <blockquote>
	 * <code>NewVariableEvent.TYPE_INT</code>,<br>
	 * <code>NewVariableEvent.TYPE_RAT</code>,<br>
	 * <code>NewVariableEvent.TYPE_REAL</code>,<br>
	 * <code>NewVariableEvent.TYPE_ENUM</code>,<br>
	 * <code>NewVariableEvent.TYPE_STRING</code>.
	 * </blockquote>
	 */
	static short parseVarType(final String val) {
		if (val.equals("int")) {
			return NewVariableEvent.TYPE_INT;
		}
		else if (val.equals("rat")) {
			return NewVariableEvent.TYPE_RAT;
		}
		else if (val.equals("real")) {
			return NewVariableEvent.TYPE_REAL;
		}
		else if (val.equals("enum")) {
			return NewVariableEvent.TYPE_ENUM;
		}
		else if (val.equals("string")) {
			return NewVariableEvent.TYPE_STRING;
		}

		// signal error
		return NewVariableEvent.TYPE_INT;
	}
	boolean in_values;
	boolean in_misc;
	StringBuffer collected_chars = new StringBuffer();
	DefaultValueList valueList;
	DefaultState state;
	DefaultUpdateList updateList;
	DefaultUpdateList causeList;
	DefaultUpdate update;
	DefaultExplanation explanation;
	Vector constraintList;
	DefaultVarDomainList varDomainList;
	DefaultMiscList miscList;
	DefaultMisc misc;
	NewVariableEvent newVariableEvent = new NewVariableEvent();
	NewConstraintEvent newConstraintEvent = new NewConstraintEvent();
	ReduceEvent reduceEvent = new ReduceEvent();
	RestoreEvent restoreEvent = new RestoreEvent();
	WakeUpEvent wakeUpEvent = new WakeUpEvent();
	SuspendEvent suspendEvent = new SuspendEvent();
	TrueEvent trueEvent = new TrueEvent();
	RejectEvent rejectEvent = new RejectEvent();
	SelectConstraintEvent selectConstraintEvent = new SelectConstraintEvent();
	SelectUpdateEvent selectUpdateEvent = new SelectUpdateEvent();

	ActivateEvent activateEvent = new ActivateEvent();

	TellEvent tellEvent = new TellEvent();

	DeactivateEvent deactivateEvent = new DeactivateEvent();

	SolutionEvent solutionEvent = new SolutionEvent();

	NewStageEvent newStageEvent = new NewStageEvent();

	StageEvent stageEvent = new StageEvent();

	/**
	 * Default constructor.
	 */
	public SAXSolver() {
	}

	/**
	 * Collects the data included between a pair of XML starting and closing
	 * tags such as <code>values</code> and <code>misc</code>.
	 */
	public void characters(final char[] ch, final int start, final int length) {
		if (this.in_values) {
			final String s = new String(ch, start, length);
			final StringTokenizer tok = new StringTokenizer(s);

			while (tok.hasMoreTokens()) {
				this.valueList.addValue(SAXSolver.parseInt(tok.nextToken()));
			}
		}
		else if (this.in_misc) {
			this.collected_chars.append(ch, start, length);
		}
	}

	protected synchronized void doResume() {
		this.notifyAll();
	}

	protected void doStart() {
	}

	protected void doStop() {
	}

	protected void doSuspend() {
	}

	// Called at the beginnig of each notification to perform the stop/suspend
	protected synchronized void doValidateState() {
		if (super.state == AbstractSolver.STATE_SUSPENDED) {
			try {
				this.wait();
			}
			catch (final InterruptedException e) {
			}
		}
	}

	/**
	 * Calls method <code>end()</code> at the end of the document.
	 */
	public void endDocument() {
		this.stop();
	}

	/**
	 * Finishes the initialization of events upon reading the corresponding
	 * closing XML tags and notifies the registered listeners. This mehod also
	 * handles the sub-elements as well.
	 */
	public void endElement(
		final String namespaceURI,
		final String localName,
		final String sName) {
		if (sName.equals("new-variable")) {
			this.newVariableEvent.setValueList(this.valueList);
			this.valueList = null;
			this.newVariableEvent.setSolver(this);
			this.notifyNewVariableListener(this.newVariableEvent);
		}
		else if (sName.equals("new-constraint")) {
			this.newConstraintEvent.setUpdateList(this.updateList);
			this.updateList = null;
			this.newConstraintEvent.setSolver(this);
			this.notifyNewConstraintListener(this.newConstraintEvent);
		}
		else if (sName.equals("reduce")) {
			this.reduceEvent.setState(this.state);
			this.state = null;
			this.reduceEvent.setUpdateList(this.updateList);
			this.updateList = null;
			this.reduceEvent.setExplanation(this.explanation);
			this.explanation = null;
			this.reduceEvent.setSolver(this);
			this.notifyReduceListener(this.reduceEvent);
		}
		else if (sName.equals("restore")) {
			this.restoreEvent.setState(this.state);
			this.state = null;
			this.restoreEvent.setUpdateList(this.updateList);
			this.updateList = null;
			this.restoreEvent.setSolver(this);
			this.notifyRestoreListener(this.restoreEvent);
		}
		else if (sName.equals("wake-up")) {
			this.wakeUpEvent.setState(this.state);
			this.state = null;
			this.wakeUpEvent.setCauseList(this.causeList);
			this.causeList = null;
			this.wakeUpEvent.setSolver(this);
			this.notifyWakeUpListener(this.wakeUpEvent);
		}
		else if (sName.equals("suspend")) {
			this.suspendEvent.setState(this.state);
			this.state = null;
			this.suspendEvent.setSolver(this);
			this.notifySuspendListener(this.suspendEvent);
		}
		else if (sName.equals("true")) {
			this.trueEvent.setState(this.state);
			this.state = null;
			this.trueEvent.setSolver(this);
			this.notifyTrueListener(this.trueEvent);
		}
		else if (sName.equals("reject")) {
			this.rejectEvent.setState(this.state);
			this.state = null;
			this.rejectEvent.setExplanation(this.explanation);
			this.explanation = null;
			this.rejectEvent.setSolver(this);
			this.notifyRejectListener(this.rejectEvent);
		}
		else if (sName.equals("select-constraint")) {
			this.selectConstraintEvent.setState(this.state);
			this.state = null;
			this.selectConstraintEvent.setSolver(this);
			this.notifySelectConstraintListener(this.selectConstraintEvent);
		}
		else if (sName.equals("select-update")) {
			this.selectUpdateEvent.setState(this.state);
			this.state = null;
			this.selectUpdateEvent.setValueList(this.valueList);
			this.valueList = null;
			this.selectUpdateEvent.setSolver(this);
			this.notifySelectUpdateListener(this.selectUpdateEvent);
		}
		else if (sName.equals("activate")) {
			this.activateEvent.setState(this.state);
			this.state = null;
			this.activateEvent.setExplanation(this.explanation);
			this.explanation = null;
			this.activateEvent.setSolver(this);
			this.notifyActivateListener(this.activateEvent);
		}
		else if (sName.equals("tell")) {
			this.tellEvent.setState(this.state);
			this.state = null;
			this.tellEvent.setSolver(this);
			this.notifyTellListener(this.tellEvent);
		}
		else if (sName.equals("deactivate")) {
			this.deactivateEvent.setState(this.state);
			this.state = null;
			this.deactivateEvent.setSolver(this);
			this.notifyDeactivateListener(this.deactivateEvent);
		}
		else if (sName.equals("solution")) {
			this.solutionEvent.setState(this.state);
			this.state = null;
			this.solutionEvent.setSolver(this);
			this.notifySolutionListener(this.solutionEvent);
		}
		else if (sName.equals("new-stage")) {
		}
		else if (sName.equals("stage")) {
		}
		else if (sName.equals("start-stage")) {
		}
		else if (sName.equals("suspend-stage")) {
		}
		else if (sName.equals("resume-stage")) {
		}
		else if (sName.equals("end-stage")) {
		}

		// end of events, beginning of sub-elements
		// nothing for values
		// nothing for range
		else if (sName.equals("vardomain")) {
			final DefaultVarDomain varDomain =
				(DefaultVarDomain) this.valueList;
			this.varDomainList.addVarDomain(varDomain);
			this.valueList = null;
		}

		// nothing for state
		else if (sName.equals("alist")) {
			if (this.constraintList.size() != 0) {
				final String[] alist = new String[this.constraintList.size()];
				this.state.setAList((String[]) this.constraintList
					.toArray(alist));
			}

			this.constraintList.clear();
		}
		else if (sName.equals("slist")) {
			if (this.constraintList.size() != 0) {
				final String[] slist = new String[this.constraintList.size()];
				this.state.setSList((String[]) this.constraintList
					.toArray(slist));
			}

			this.constraintList.clear();
		}
		else if (sName.equals("qlist")) {
			if (this.constraintList.size() != 0) {
				final String[] qlist = new String[this.constraintList.size()];
				this.state.setQList((String[]) this.constraintList
					.toArray(qlist));
			}

			this.constraintList.clear();
		}
		else if (sName.equals("tlist")) {
			if (this.constraintList.size() != 0) {
				final String[] tlist = new String[this.constraintList.size()];
				this.state.setTList((String[]) this.constraintList
					.toArray(tlist));
			}

			this.constraintList.clear();
		}
		else if (sName.equals("rlist")) {
			if (this.constraintList.size() != 0) {
				final String[] rlist = new String[this.constraintList.size()];
				this.state.setRList((String[]) this.constraintList
					.toArray(rlist));
			}

			this.constraintList.clear();
		}
		else if (sName.equals("ulist")) {
			this.state.setUList(this.updateList);
			this.updateList = null;
		}
		else if (sName.equals("vlist")) {
			this.state.setVList(this.varDomainList);
			this.valueList = null;
		}
		else if (sName.equals("misc")) {
			this.misc.setValue(this.collected_chars.toString());
			this.miscList.addMisc(this.misc);
			this.misc = null;
			this.collected_chars.setLength(0);
			this.in_misc = false;
		}

		// nothing for constraint
		else if (sName.equals("update")) {
			this.update.setValueList(this.valueList);
			this.valueList = null;
			this.updateList.addUpdate(this.update);
			this.update = null;
		}
		else if (sName.equals("cause")) {
			this.update.setValueList(this.valueList);
			this.valueList = null;
			this.causeList.addUpdate(this.update);
			this.update = null;
		}
		else if (sName.equals("explanation")) {
			if (this.constraintList != null) {
				if (this.explanation == null) {
					this.explanation = new DefaultExplanation();
				}
				final String[] list = new String[this.constraintList.size()];
				this.explanation
					.setConstraintList((String[]) this.constraintList
						.toArray(list));
			}
		}
	}

	/**
	 *
	 */
	public void endPrefixMapping(final String prefix) {
	}

	/**
	 *
	 */
	public void ignorableWhitespace(
		final char[] ch,
		final int start,
		final int length) {
	}

	/**
	 *
	 */
	public void processingInstruction(final String target, final String data) {
	}

	/**
	 * Resets <code>valueList</code>, <code>state</code>,
	 * <code>updateList</code>, <code>explanation</code>.
	 */
	protected void reset() {
		this.valueList = null;
		this.state = null;
		this.updateList = null;
		this.explanation = null;
		this.constraintList = null;
	}

	public void setDocumentLocator(final Locator locator) {
	}

	/**
	 *
	 */
	public void skippedEntity(final String name) {
	}

	/**
	 * Calls method <code>start()</code> at the beginning of the document.
	 */
	public void startDocument() {
		//System.out.println("Starting");
		this.start();
	}

	/**
	 * Creates the adequate events upon reading the corresponding XML tags
	 * and intializes their fields. This mehod also handles the sub-elements
	 * as well.
	 */
	public void startElement(
		final String namespaceURI,
		final String localName,
		final String sName,
		final Attributes atts) {
		//System.out.println(sName);
		String value;

		if (sName.equals("new-variable")) {
			this.reset();
			SAXSolver.parseBasicSolverAttributes(atts, this.newVariableEvent);
			value = atts.getValue("vname");

			if (value != null) {
				this.newVariableEvent.setVName(value.intern());
			}
			else {
				this.newVariableEvent.setVName(null);
			}

			value = atts.getValue("type");

			if (value != null) {
				this.newVariableEvent.setType(SAXSolver.parseVarType(value));
			}
			else {
				this.newVariableEvent.setType(NewVariableEvent.TYPE_INT);
			}

			this.newVariableEvent.setExternalRep(atts.getValue("externalrep"));
			this.newVariableEvent.setInternalRep(atts.getValue("internalrep"));
		}
		else if (sName.equals("new-constraint")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.newConstraintEvent);
			value = atts.getValue("orig");

			if (value != null) {
				this.newConstraintEvent.setUser(value.equals("user"));
			}
		}
		else if (sName.equals("reduce")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.reduceEvent);
		}
		else if (sName.equals("restore")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.restoreEvent);
		}
		else if (sName.equals("wake-up")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.wakeUpEvent);
		}
		else if (sName.equals("suspend")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.suspendEvent);
		}
		else if (sName.equals("true")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.trueEvent);
		}
		else if (sName.equals("reject")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.rejectEvent);
		}
		else if (sName.equals("select-constraint")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(
				atts,
				this.selectConstraintEvent);
		}
		else if (sName.equals("select-update")) {
			this.reset();
			SAXSolver.parseBasicSolverAttributes(atts, this.selectUpdateEvent);
			this.selectUpdateEvent.setVName(atts.getValue("vname"));
			this.selectUpdateEvent.setType(SAXSolver.parseUpdateType(atts
				.getValue("type")));
		}
		else if (sName.equals("activate")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.activateEvent);
		}
		else if (sName.equals("tell")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.tellEvent);
		}
		else if (sName.equals("deactivate")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.deactivateEvent);
		}
		else if (sName.equals("solution")) {
			this.reset();
			SAXSolver.parseConstraintAttributes(atts, this.solutionEvent);
		}
		else if (sName.equals("new-stage")) {
		}
		else if (sName.equals("stage")) {
		}
		else if (sName.equals("start-stage")) {
		}
		else if (sName.equals("suspend-stage")) {
		}
		else if (sName.equals("resume-stage")) {
		}
		else if (sName.equals("end-stage")) {
		}

		// end of events, beginning of sub-elements
		else if (sName.equals("values")) {
			this.in_values = true;

			if (this.valueList == null) {
				this.valueList = new DefaultValueList();
			}
		}
		else if (sName.equals("range")) {
			if (this.valueList == null) {
				this.valueList = new DefaultValueList();
			}

			final int from = SAXSolver.parseInt(atts.getValue("from"));
			final int to = SAXSolver.parseInt(atts.getValue("to"));
			this.valueList.addRange(from, to);
		}
		else if (sName.equals("vardomain")) {
			if (this.varDomainList == null) {
				this.varDomainList = new DefaultVarDomainList();
			}

			final DefaultVarDomain varDomain = new DefaultVarDomain();
			value = atts.getValue("vname");

			if (value != null) {
				varDomain.setVName(value.intern());
			}

			this.valueList = varDomain;
		}
		else if (sName.equals("state")) {
			this.state = new DefaultState();
			value = atts.getValue("precision");

			if (value != null) {
				this.state.precision = SAXSolver.parsePrecision(value);
			}
			else {
				this.state.precision = State.PRECISION_NONE;
			}
		}

		// nothing for alist
		// nothing for slist
		// nothing for qlist
		// nothing for tlist
		// nothing for rlist
		// nothing for ulist
		// nothing for vlist
		else if (sName.equals("misc")) {
			if (this.miscList == null) {
				this.miscList = new DefaultMiscList();
			}

			this.misc = new DefaultMisc();
			this.misc.type = atts.getValue("type");
			this.in_misc = true;
		}
		else if (sName.equals("constraint")) {
			if (this.constraintList == null) {
				this.constraintList = new Vector();
			}

			value = atts.getValue("cname");

			if (value != null) {
				this.constraintList.addElement(value.intern());
			}
		}
		else if (sName.equals("update")) {
			if (this.updateList == null) {
				this.updateList = new DefaultUpdateList();
			}

			this.update = new DefaultUpdate();
			value = atts.getValue("cname");

			if (value != null) {
				this.update.setCName(value.intern());
			}
			else {
				this.update.setCName(null);
			}

			value = atts.getValue("vname");

			if (value != null) {
				this.update.setVName(value.intern());
			}
			else {
				this.update.setVName(null);
			}

			this.update
				.setType(SAXSolver.parseUpdateType(atts.getValue("type")));
		}
		else if (sName.equals("cause")) {
			if (this.causeList == null) {
				this.causeList = new DefaultUpdateList();
			}

			this.update = new DefaultUpdate();
			value = atts.getValue("cname");

			if (value != null) {
				this.update.setCName(value.intern());
			}
			else {
				this.update.setCName(null);
			}

			value = atts.getValue("vname");

			if (value != null) {
				this.update.setVName(value.intern());
			}
			else {
				this.update.setVName(null);
			}

			this.update
				.setType(SAXSolver.parseUpdateType(atts.getValue("type")));
		}

		// nothing for explanation
	}

	/**
	 *
	 */
	public void startPrefixMapping(final String prefix, final String uri) {
	}
}