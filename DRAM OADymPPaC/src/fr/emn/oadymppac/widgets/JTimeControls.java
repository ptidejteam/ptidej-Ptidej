/*
 * $Id: JTimeControls.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

package fr.emn.oadymppac.widgets;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.StoppedListener;

public class JTimeControls extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6737648276633933109L;
	JButton stop;
	JToggleButton play_pause;
	JButton step;
	JButton step_5;
	JButton step_10;
	Solver solver;
	int currentStep;
	int nextStep;
	BasicSolverEvent lastEvent = new BasicSolverEvent();
	String lastEventClass;
	JLabel feedback;
	Vector stoppedListeners = new Vector();
	boolean repaintPending = true;

	public JTimeControls(final Solver solver) {
		this.solver = solver;
		Icon icon;

		icon = new ImageIcon("images/stop.gif");
		this.stop = new JButton(icon);
		this.stop.setActionCommand("Stop");
		this.stop.setToolTipText("Stop");
		this.stop.addActionListener(this);
		this.add(this.stop);

		final ImageIcon playicon = new ImageIcon("images/play.gif");
		icon = new ImageIcon("images/pause.gif");
		this.play_pause = new JToggleButton(playicon);
		this.play_pause.setSelectedIcon(icon);
		this.play_pause.setActionCommand("Play/Pause");
		this.play_pause.setToolTipText("Play/Pause");
		this.play_pause.addActionListener(this);
		this.add(this.play_pause);

		this.step = new JButton("1", playicon);
		this.step.setActionCommand("Step1");
		this.step.setToolTipText("Step1");
		this.step.addActionListener(this);
		this.add(this.step);

		this.step_5 = new JButton("5", playicon);
		this.step_5.setActionCommand("Step5");
		this.step_5.setToolTipText("Step5");
		this.step_5.addActionListener(this);
		this.add(this.step_5);

		this.step_10 = new JButton("10", playicon);
		this.step_10.setActionCommand("Step10");
		this.step_10.setToolTipText("Step10");
		this.step_10.addActionListener(this);
		this.add(this.step_10);

		this.feedback = new JLabel("0:no event  "); // 12 characters, used later
		this.add(this.feedback);
	}

	public void actionPerformed(final ActionEvent e) {
		this.doAction(e.getActionCommand());
	}

	public void addStoppedListener(final StoppedListener l) {
		this.stoppedListeners.add(l);
	}

	public void doAction(final String action) {
		if (action.equals("Stop")) {
			this.stop();
		}
		else if (action.equals("Play/Pause")) {
			this.playPause();
		}
		else if (action.equals("Step1")) {
			this.step(1);
		}
		else if (action.equals("Step5")) {
			this.step(5);
		}
		else if (action.equals("Step10")) {
			this.step(10);
		}
	}

	public void fireStoppedListener() {
		for (int i = 0; i < this.stoppedListeners.size(); i++) {
			final StoppedListener l =
				(StoppedListener) this.stoppedListeners.elementAt(i);
			l.stopped(this.lastEvent);
		}
	}

	public boolean isRepaintPending() {
		return this.repaintPending;
	}

	public synchronized void paint(final Graphics g) {
		super.paint(g);
		this.repaintPending = false;
		this.notifyAll();
	}

	public void pause() {
		if (!this.play_pause.isSelected()) {
			this.nextStep = 0; // pause next time
		}
	}

	public void play() {
		if (this.play_pause.isSelected()) {
			this.nextStep = Integer.MAX_VALUE;
			this.step.setEnabled(false);
			this.step_5.setEnabled(false);
			this.step_10.setEnabled(false);
			this.resumeSolver();
		}
	}

	public void playPause() {
		if (this.play_pause.isSelected()) {
			this.play();
		}
		else {
			this.pause();
		}
	}

	public void removeStoppedListener(final StoppedListener l) {
		for (int i = 0; i < this.stoppedListeners.size(); i++) {
			if (l == this.stoppedListeners.elementAt(i)) {
				this.stoppedListeners.removeElementAt(i);
				return;
			}
		}
	}

	public void repaint() {
		this.repaintPending = true;
	}

	public void resumeSolver() {
		this.stop.setEnabled(true);
		this.solver.resume();
	}

	public void step(final int s) {
		this.nextStep = this.currentStep + s - 1;
		this.resumeSolver();
	}

	public void stepPerformed(final BasicSolverEvent ev, final boolean doSuspend) {
		this.lastEventClass = ev.getClass().getName();
		if (this.lastEvent.getN() != ev.getN()) {
			this.lastEvent.copy(ev);
			this.currentStep++;
			if (doSuspend || this.currentStep > this.nextStep) {
				this.suspendSolver();
			}
		}
	}

	public void stop() {
		this.solver.suspend();
	}

	public void suspendSolver() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JTimeControls.this.updateFeedback();
				JTimeControls.this.fireStoppedListener();
			}
		});

		this.solver.suspend();
	}

	protected void updateFeedback() {
		String msg =
			""
					+ this.lastEvent.getN()
					+ ":"
					+ this.lastEventClass.substring("fr.emn.oadymppac.event."
						.length());
		if (msg.length() > 12) {
			msg = msg.substring(0, 12);
		}

		this.feedback.setText(msg);
		this.updateState();
	}

	protected void updateState() {
		this.stop.setEnabled(false);
		if (!this.play_pause.isSelected()) {
			this.step.setEnabled(true);
			this.step_5.setEnabled(true);
			this.step_10.setEnabled(true);
		}
		else {
			this.step.setEnabled(false);
			this.step_5.setEnabled(false);
			this.step_10.setEnabled(false);
		}
	}
}
