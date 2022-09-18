/*
 * $Id: JConstraintManager.java,v 1.2 2005/10/14 21:34:08 guehene Exp $
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.SwingUtilities;
import att.grappa.Edge;
import att.grappa.Graph;
import att.grappa.GrappaColor;
import att.grappa.GrappaPanel;
import att.grappa.GrappaSupport;
import att.grappa.Node;
import att.grappa.Subgraph;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.ConstraintManager;
import fr.emn.oadymppac.ObjectLocator;
import fr.emn.oadymppac.Variable;
import fr.emn.oadymppac.VariableManager;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ConstraintChangedListener;

/**
 * A <code>JConstraintManager</code> displays the domains maintained by
 * a <code>ConstraintManager</code> using the AT&and;T grappa graph
 * visualizing library.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.2 $
 */
public class JConstraintManager extends GrappaPanel implements
		ConstraintChangedListener, ObjectLocator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 761799377052762671L;
	ConstraintManager constManager;
	VarColorManager colorMgr;
	VariableManager varManager;

	HashMap constraintSubgraphs;
	HashMap constraintNodes;

	public final static String SCRIPT = "layoutGraph.bat";

	boolean graphModified = false;

	public JConstraintManager(final ConstraintManager m) {
		super(new Graph("Constraint Graph", false, false));
		this.setScaleToFit(true);
		this.getGraph().setEdgeAttribute("len", "1.2"); // hack
		this.constManager = m;
		this.constManager.addConstraintChangedListener(this);
		this.constraintSubgraphs = new HashMap();
		// each constraint with its variables are a subgraph
		this.constraintNodes = new HashMap();
	}

	public void activateConstraint(final Constraint c) {
		final String cname = c.getCName();
		final Subgraph sub = (Subgraph) this.constraintSubgraphs.get(cname);
		synchronized (this.getGraph()) {
			Node cnode = sub.findNodeByName(cname);
			if (cnode == null) {
				cnode = this.getSubgraph().findNodeByName(cname);
			}
			if (cnode == null) {
				System.err.println("Couldn't find node " + cname);
				return;
			}
			if (!c.isActive()) {
				cnode.setAttribute("color", "gray");
			}
			else {
				System.err.println("Activating subgraph for " + cname);
				cnode.setAttribute("color", "black");
			}
		}
	}

	public void addConstraint(final Constraint c) {
		final String cname = c.getCName();
		synchronized (this.getGraph()) {
			final Subgraph sub =
				new Subgraph(this.getSubgraph(), "subgraph_" + cname);
			System.out.println("Adding subgraph " + cname);
			this.constraintSubgraphs.put(cname, sub);

			final Node constNode = new Node(sub, cname);
			this.constraintNodes.put(cname, constNode);
			constNode.setAttribute("shape", "box");
			if (c.getExternalRep() != null) {
				//System.out.println("Adding node "+c.getExternalRep());
				constNode.setAttribute("tip", c.getExternalRep());
			}
			if (!c.isActive()) {
				constNode.setAttribute("color", "gray");
			}

			for (int i = 0; i < c.getVariableCount(); i++) {
				final String vname = c.getVariableAt(i);
				Node vnode = sub.findNodeByName(vname);
				if (vnode == null) {
					vnode = this.getSubgraph().findNodeByName(vname);
				}
				if (vnode == null) {
					vnode = new Node(sub, vname);
					final Variable v = this.getVariable(vname);
					if (v.getExternalRep() != null) {
						vnode.setAttribute("label", v.getExternalRep());
					}

					vnode.setAttribute("shape", "circle");
					vnode.setAttribute(
						"color",
						GrappaColor.getColorName(this.getVarColor(v)));
					//System.out.println("Adding variable "+vname);
				}
				new Edge(sub, vnode, constNode);
				System.out.println("Linking " + cname + " to " + vname);
			}
		}
		this.graphModified = true;
	}

	public void computeLayout() {
		if (!this.graphModified) {
			return;
		}
		final Graph graph = this.getGraph();
		Object connector = null;
		try {
			connector = Runtime.getRuntime().exec(JConstraintManager.SCRIPT);
		}
		catch (final Exception ex) {
			System.err.println("Exception while setting up Process: "
					+ ex.getMessage() + "\nTrying URLConnection...");
			connector = null;
		}
		if (connector == null) {
			try {
				connector =
					new URL(
						"http://www.research.att.com/~john/cgi-bin/format-graph")
						.openConnection();
				final URLConnection urlConn = (URLConnection) connector;
				urlConn.setDoInput(true);
				urlConn.setDoOutput(true);
				urlConn.setUseCaches(false);
				urlConn.setRequestProperty(
					"Content-Type",
					"application/x-www-form-urlencoded");
			}
			catch (final Exception ex) {
				System.err.println("Exception while setting up URLConnection: "
						+ ex.getMessage() + "\nLayout not performed.");
				connector = null;
			}
		}
		if (connector != null) {
			synchronized (graph) {
				if (!GrappaSupport.filterGraph(graph, connector)) {
					System.err.println("ERROR: somewhere in filterGraph");
				}
				if (connector instanceof Process) {
					try {
						final int code = ((Process) connector).waitFor();
						if (code != 0) {
							System.err.println("WARNING: proc exit code is: "
									+ code);
						}
					}
					catch (final InterruptedException ex) {
						System.err
							.println("Exception while closing down proc: "
									+ ex.getMessage());
						ex.printStackTrace(System.err);
					}
				}
				connector = null;
			}
		}
		graph.repaint();
		this.graphModified = false;
	}

	public void constraintChanged(final ConstraintChangedEvent ev) {
		switch (ev.getAction()) {
			case ConstraintChangedEvent.ADDED :
				this.addConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.REMOVED :
				this.removeConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.ACTIVATED :
				this.activateConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.DEACTIVATED :
				this.deactivateConstraint(ev.getConstraint());
				break;
		}
	}

	public void deactivateConstraint(final Constraint c) {
		this.activateConstraint(c);
	}

	public Object getConstraintAttribute(final String cname, final String attr) {
		final Node cnode = (Node) this.constraintNodes.get(cname);
		if (cnode == null) {
			return null;
		}
		return cnode.getAttributeValue(attr);
	}

	/**
	 * Get the value of constraintManager.
	 * @return value of constraintManager.
	 */
	public ConstraintManager getConstraintManager() {
		return this.constManager;
	}

	public Graph getGraph() {
		return this.getSubgraph().getGraph();
	}

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getPositions(final Object o, final Vector positions) {
		String name = null;
		if (o instanceof Variable) {
			name = ((Variable) o).getVName();
		}
		else if (o instanceof Constraint) {
			name = ((Constraint) o).getCName();
		}
		return this.getPositions(name, positions);
	}

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getPositions(final String name, final Vector positions) {
		final Point p = new Point();
		if (name != null) {
			final Subgraph sub = (Subgraph) this.constraintSubgraphs.get(name);
			Node node = sub.findNodeByName(name);
			if (node == null) {
				node = this.getSubgraph().findNodeByName(name);
			}
			if (node != null) {
				p.x = (int) ((Point2D) node.getCenterPoint()).getX();
				p.y = (int) ((Point2D) node.getCenterPoint()).getY();
				SwingUtilities.convertPointToScreen(p, this);
				positions.addElement(p);
			}
		}
		return positions;
	}

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getShapes(final Object o, final Vector shapes) {
		String name = null;
		if (o instanceof Variable) {
			name = ((Variable) o).getVName();
		}
		else if (o instanceof Constraint) {
			name = ((Constraint) o).getCName();
		}
		return this.getShapes(name, shapes);
	}

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getShapes(final String name, final Vector shapes) {
		/**
		 * First we calculate the transform needed to express the coordinates
		 * in the screen coordinate system. Then we look up the node and return
		 * its shape.
		 */
		final Point tp = new Point(0, 0); // translation vector
		SwingUtilities.convertPointToScreen(tp, this);
		final AffineTransform at = new AffineTransform();
		at.setToTranslation(tp.getX(), tp.getY());
		if (name != null) {
			final Subgraph sub = (Subgraph) this.constraintSubgraphs.get(name);
			Node node = sub.findNodeByName(name);
			if (node == null) {
				node = this.getSubgraph().findNodeByName(name);
			}
			if (node != null) {
				final GeneralPath gp = new GeneralPath();
				gp.append(node.getGrappaNexus().getPathIterator(at), true);
				shapes.addElement(gp);
			}
			else {
				System.err.println("Could not add shape : " + name
						+ " not found !");
			}
		}
		return shapes;
	}

	public Color getVarColor(final Variable v) {
		if (this.colorMgr == null) {
			this.colorMgr =
				VarColorManager.getVarColorManager(this.constManager
					.getSolver());
		}
		return this.colorMgr.getColor(v);
	}

	public Variable getVariable(final String vname) {
		if (this.varManager == null) {
			this.varManager =
				VariableManager.getVariableManager(this.constManager
					.getSolver());
		}
		return this.varManager.getVariable(vname);
	}

	public void paint(final Graphics g) {
		synchronized (this.getGraph()) {
			super.paint(g);
		}
	}

	public void removeConstraint(final Constraint c) {
		final String cname = c.getCName();
		final Subgraph sub = (Subgraph) this.constraintSubgraphs.get(cname);
		System.err.println("Removing subgraph for " + cname);

		if (sub != null) {
			synchronized (this.getGraph()) {
				this.getSubgraph().removeSubgraph("subgraph_" + cname);
				this.constraintSubgraphs.remove(cname);
				this.constraintNodes.remove(cname);
			}
			this.graphModified = true;
		}
		else {
			System.err.println("Error, cannot find subgraph for " + cname);
		}
	}

	public void setConstraintAttribute(
		final String cname,
		final String attr,
		final String val) {
		final Node cnode = (Node) this.constraintNodes.get(cname);
		if (cnode == null) {
			return;
		}
		cnode.setAttribute(attr, val);
	}

	/**
	 * Set the value of constraintManager.
	 * @param v  Value to assign to constraintManager.
	 */
	public void setConstraintManager(final ConstraintManager v) {
		this.constManager = v;
	}
}
