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
package ptidej.viewer.ui;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.JDesktopPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import padl.motif.IDesignMotifModel;
import ptidej.ui.canvas.event.ICanvasListener;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.ISourceModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.ui.rulecard.IRuleCardListener;
import ptidej.viewer.ui.rulecard.RuleCardEvent;
import ptidej.viewer.ui.window.RuleCardWindow;
import ptidej.viewer.ui.window.SourceDualHierarchicalModelWindow;
import ptidej.viewer.ui.window.SourceGraphicalModelWindow;
import ptidej.viewer.ui.window.SourceHierarchicalModelWindow;
import ptidej.viewer.utils.Resources;

public class DesktopPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private static DesktopPane UniqueInstance;

	public static DesktopPane getInstance() {
		if (DesktopPane.UniqueInstance == null) {
			DesktopPane.UniqueInstance = new DesktopPane();
		}
		return DesktopPane.UniqueInstance;
	}

	private final List canvasListeners;
	private IWindow currentDesktopWindow;
	private Set designDefects;
	private final List graphModelListeners;
	private IDesignMotifModel pattern;
	private char[] patternName;
	private int problem;
	private RuleCardEvent ruleCardEvent;
	private final List ruleCardListeners;
	private int solver;
	private final List sourceModelListeners;

	private DesktopPane() {
		this.setBackground(Color.GRAY);

		this.canvasListeners = new ArrayList();
		this.graphModelListeners = new ArrayList();
		this.ruleCardListeners = new ArrayList();
		this.sourceModelListeners = new ArrayList();
	}
	public void addCanvasListener(final ICanvasListener aCanvasListener) {
		this.canvasListeners.add(aCanvasListener);
	}
	public void addGraphModelListener(
		final IGraphModelListener aGraphModelListener) {

		this.graphModelListeners.add(aGraphModelListener);
	}
	public void addRuleCardListener(final IRuleCardListener aRuleCardListener) {
		this.ruleCardListeners.add(aRuleCardListener);
	}
	public void addSourceModelListener(
		final ISourceModelListener aSourceModelListener) {

		this.sourceModelListeners.add(aSourceModelListener);
	}
	public void createDualHierarchicalModelWindow() {
		// I create a model window to display this project.
		// I attach to this window all the object required 
		// for its good functioning ;-).
		final AbstractRepresentationWindow window =
			new SourceDualHierarchicalModelWindow();
		// I don't forget to tell everyone what is the new window!
		this.currentDesktopWindow = window;
		this.setWindowProperties(window);
		// I don't forget to tell everyone what is the new window!
		// Subtlety: I must set the currentDesktopWindow after opening
		// the window because its opening will trigger any other window
		// to deactivate and, thus, set currentDesktopWindow to null!
		this.currentDesktopWindow = window;
	}
	public void createGraphicalModelWindow() {
		// I create a model window to display this project.
		// I attach to this window all the object required 
		// for its good functioning ;-).
		final AbstractRepresentationWindow window =
			new SourceGraphicalModelWindow();
		// I don't forget to tell everyone what is the new window!
		this.currentDesktopWindow = window;
		this.setWindowProperties(window);
		// I don't forget to tell everyone what is the new window!
		// Subtlety: I must set the currentDesktopWindow after opening
		// the window because its opening will trigger any other window
		// to deactivate and, thus, set currentDesktopWindow to null!
		this.currentDesktopWindow = window;
	}
	public void createHierarchicalModelWindow() {
		// I create a model window to display this project.
		// I attach to this window all the object required 
		// for its good functioning ;-).
		final AbstractRepresentationWindow window =
			new SourceHierarchicalModelWindow();
		// I don't forget to tell everyone what is the new window!
		this.currentDesktopWindow = window;
		this.setWindowProperties(window);
		// Subtlety: I must set the currentDesktopWindow after opening
		// the window because its opening will trigger any other window
		// to deactivate and, thus, set currentDesktopWindow to null!
		this.currentDesktopWindow = window;
	}
	public void createRuleCardWindow(
		final File ruleCardFile,
		final String defectName) {

		final RuleCardWindow window =
			new RuleCardWindow(ruleCardFile.getName());
		DesktopPane.this.currentDesktopWindow = window;
		window.setResizable(true);
		window.setIconifiable(true);
		window.setClosable(true);
		window.setMaximizable(true);
		window.setBounds(10, 10, 500, 500);
		window.setRuleCard(ruleCardFile, defectName);
		window.addInternalFrameListener(new InternalFrameListener() {
			public void internalFrameActivated(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow =
					(IWindow) e.getInternalFrame();
				//DesktopPane.this.notifyRuleCardChange(new RuleCardEvent());
			}
			public void internalFrameClosed(final InternalFrameEvent e) {
			}
			public void internalFrameClosing(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				DesktopPane.this.notifyRuleCardUnavailability();
			}
			public void internalFrameDeactivated(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				DesktopPane.this.notifyRuleCardUnavailability();
			}
			public void internalFrameDeiconified(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow =
					(IWindow) e.getInternalFrame();
				DesktopPane.this.notifyRuleCardChange(new RuleCardEvent());
			}
			public void internalFrameIconified(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				DesktopPane.this.notifyRuleCardUnavailability();
			}
			public void internalFrameOpened(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow =
					(IWindow) e.getInternalFrame();
				DesktopPane.this
					.notifyRuleCardAvailability(new RuleCardEvent());
			}
		});

		this.setLayer(window, Resources.PROJECTS_LAYER);
		window.setVisible(true);
	}
	public Set getDesignDefects() {
		// Create the HashSet if not already created
		if (this.designDefects == null) {
			this.designDefects = new HashSet();
		}
		return this.designDefects;
	}
	public Iterator getIteratorOnGraphModelListeners() {
		return this.graphModelListeners.iterator();
	}
	public Iterator getIteratorOnCanvasListeners() {
		return this.canvasListeners.iterator();
	}
	public Iterator getIteratorOnSourceModelListeners() {
		return this.sourceModelListeners.iterator();
	}
	public AbstractRepresentationWindow getAbstractRepresentationWindow() {
		return (AbstractRepresentationWindow) this.currentDesktopWindow;
	}
	public IDesignMotifModel getPattern() {
		return this.pattern;
	}
	public char[] getPatternName() {
		return this.patternName;
	}

	public int getProblem() {
		return this.problem;
	}
	public RuleCardEvent getRuleCardEvent() {
		return this.ruleCardEvent;
	}
	public RuleCardWindow getRuleCardWindow() {
		return (RuleCardWindow) this.currentDesktopWindow;
	}
	public int getSolver() {
		return this.solver;
	}
	public boolean isRuleCardWindowActivated() {
		return this.currentDesktopWindow instanceof RuleCardWindow;
	}
	public void notifyRuleCardAvailability(final RuleCardEvent aRuleCardEvent) {
		for (int i = 0; i < this.ruleCardListeners.size(); i++) {
			((IRuleCardListener) this.ruleCardListeners.get(i))
				.ruleCardAvailable(aRuleCardEvent);
		}
	}
	public void notifyRuleCardChange(final RuleCardEvent aRuleCardEvent) {
		for (int i = 0; i < this.ruleCardListeners.size(); i++) {
			((IRuleCardListener) this.ruleCardListeners.get(i))
				.ruleCardChanged(aRuleCardEvent);
		}
	}
	public void notifyRuleCardUnavailability() {
		for (int i = 0; i < this.ruleCardListeners.size(); i++) {
			((IRuleCardListener) this.ruleCardListeners.get(i))
				.ruleCardUnavailable();
		}
	}
	public void setDesignDefects(final Set aDesignDefectList) {
		this.designDefects = aDesignDefectList;
	}
	public void setPattern(final IDesignMotifModel aPattern) {
		this.pattern = aPattern;
	}
	public void setPatternName(final char[] patternName) {
		this.patternName = patternName;
	}
	public void setProblem(final int problem) {
		this.problem = problem;
	}
	public void setRuleCardEvent(final RuleCardEvent aRuleCardEvent) {
		this.ruleCardEvent = aRuleCardEvent;
	}
	public void setSolver(final int solver) {
		this.solver = solver;
	}
	private void setWindowProperties(final AbstractRepresentationWindow aWindow) {
		// Fourth, I configure the window graphic attributes.
		aWindow.setResizable(true);
		aWindow.setIconifiable(true);
		aWindow.setClosable(true);
		aWindow.setMaximizable(true);
		aWindow.setBounds(10, 10, 500, 500);

		// Fifth, ...
		aWindow.show();

		// Yann 2014/03/28: Listener!
		// I add a listener to the Window after it is
		// created because I don't want the call to
		// show() above to trigger needlessly other
		// listeners while the source model and source
		// graph are not created yet.
		aWindow.addInternalFrameListener(new InternalFrameListener() {
			public void internalFrameActivated(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow =
					(IWindow) e.getInternalFrame();
				this.available();
			}
			public void internalFrameClosed(final InternalFrameEvent e) {
			}
			public void internalFrameClosing(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				this.unavailable();
			}
			public void internalFrameDeactivated(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				this.unavailable();
			}
			public void internalFrameDeiconified(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow =
					(IWindow) e.getInternalFrame();
				this.changed();
			}
			public void internalFrameIconified(final InternalFrameEvent e) {
				DesktopPane.this.currentDesktopWindow = null;
				this.unavailable();
			}
			public void internalFrameOpened(final InternalFrameEvent e) {
			}
			private void available() {
				for (int i = 0; i < DesktopPane.this.graphModelListeners.size(); i++) {
					((IGraphModelListener) DesktopPane.this.graphModelListeners
						.get(i))
						.graphModelAvailable(new SourceAndGraphModelEvent(
							DesktopPane.this.getAbstractRepresentationWindow()));
				}
				for (int i = 0; i < DesktopPane.this.sourceModelListeners
					.size(); i++) {
					((ISourceModelListener) DesktopPane.this.sourceModelListeners
						.get(i))
						.sourceModelAvailable(new SourceAndGraphModelEvent(
							DesktopPane.this.getAbstractRepresentationWindow()));
				}
			}
			private void changed() {
				for (int i = 0; i < DesktopPane.this.graphModelListeners.size(); i++) {
					((IGraphModelListener) DesktopPane.this.graphModelListeners
						.get(i))
						.graphModelChanged(new SourceAndGraphModelEvent(
							DesktopPane.this.getAbstractRepresentationWindow()));
				}
				for (int i = 0; i < DesktopPane.this.sourceModelListeners
					.size(); i++) {
					((ISourceModelListener) DesktopPane.this.sourceModelListeners
						.get(i))
						.sourceModelChanged(new SourceAndGraphModelEvent(
							DesktopPane.this.getAbstractRepresentationWindow()));
				}
			}
			private void unavailable() {
				for (int i = 0; i < DesktopPane.this.graphModelListeners.size(); i++) {
					((IGraphModelListener) DesktopPane.this.graphModelListeners
						.get(i)).graphModelUnavailable();
				}
				for (int i = 0; i < DesktopPane.this.sourceModelListeners
					.size(); i++) {
					((ISourceModelListener) DesktopPane.this.sourceModelListeners
						.get(i)).sourceModelUnavailable();
				}
			}
		});
	}
}
