/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.tree;

import infovis.Tree;
import infovis.utils.RowIterator;
import java.util.EventListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * Adaptor class for using the JTree component on an Infovis Tree.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class SwingTreeAdaptor implements TreeModel, RowMapper {
	protected Tree tree;

	public SwingTreeAdaptor(Tree tree) {
		this.tree = tree;
	}
	EventListenerList listenerList = new EventListenerList();

	/**
	 * Adds a listener for the TreeModelEvent posted after the tree changes.
	 *
	 * @see     #removeTreeModelListener
	 * @param   l       the listener to add
	 */
	public void addTreeModelListener(TreeModelListener l) {
		this.listenerList.add(TreeModelListener.class, l);
	}

	/**
	 * Removes a listener previously added with <B>addTreeModelListener()</B>.
	 *
	 * @see     #addTreeModelListener
	 * @param   l       the listener to remove
	 */
	public void removeTreeModelListener(TreeModelListener l) {
		this.listenerList.remove(TreeModelListener.class, l);
	}

	/**
	 * Returns an array of all the tree model listeners
	 * registered on this model.
	 *
	 * @return all of this model's <code>TreeModelListener</code>s
	 *         or an empty
	 *         array if no tree model listeners are currently registered
	 *
	 * @see #addTreeModelListener
	 * @see #removeTreeModelListener
	 *
	 * @since 1.4
	 */
	public TreeModelListener[] getTreeModelListeners() {
		return (TreeModelListener[]) this.listenerList
			.getListeners(TreeModelListener.class);
	}

	/**
	 * Notifies all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 *
	 * @param source the node being changed
	 * @param path the path to the root node
	 * @param childIndices the indices of the changed elements
	 * @param children the changed elements
	 * @see EventListenerList
	 */
	protected void fireTreeNodesChanged(
		Object source,
		Object[] path,
		int[] childIndices,
		Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent e = null;

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e =
						new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesChanged(e);
			}
		}
	}

	/**
	 * Notifies all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 *
	 * @param source the node where new elements are being inserted
	 * @param path the path to the root node
	 * @param childIndices the indices of the new elements
	 * @param children the new elements
	 * @see EventListenerList
	 */
	protected void fireTreeNodesInserted(
		Object source,
		Object[] path,
		int[] childIndices,
		Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent e = null;

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e =
						new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeNodesInserted(e);
			}
		}
	}

	/**
	 * Notifies all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 *
	 * @param source the node where the tree model has changed
	 * @param path the path to the root node
	 * @param childIndices the indices of the affected elements
	 * @param children the affected elements
	 * @see EventListenerList
	 */
	protected void fireTreeStructureChanged(
		Object source,
		Object[] path,
		int[] childIndices,
		Object[] children) {
		// Guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		TreeModelEvent e = null;

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeModelListener.class) {
				// Lazily create the event:
				if (e == null)
					e =
						new TreeModelEvent(source, path, childIndices, children);
				((TreeModelListener) listeners[i + 1]).treeStructureChanged(e);
			}
		}
	}

	/**
	 * Notifies all listeners that have registered interest for
	 * notification on this event type.  The event instance
	 * is lazily created using the parameters passed into
	 * the fire method.
	 *
	 * @param source the node where the tree model has changed
	 * @param path the path to the root node
	 * @see EventListenerList
	 */
	//    private void fireTreeStructureChanged(Object source, TreePath path) {
	//	// Guaranteed to return a non-null array
	//	Object[]	   listeners = this.listenerList.getListenerList();
	//	TreeModelEvent e = null;
	//
	//	// Process the listeners last to first, notifying
	//	// those that are interested in this event
	//	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	//	    if (listeners[i] == TreeModelListener.class) {
	//		// Lazily create the event:
	//		if (e == null)
	//		    e = new TreeModelEvent(source, path);
	//		((TreeModelListener)listeners[i + 1]).treeStructureChanged(e);
	//	    }
	//	}
	//    }

	/**
	 * Returns an array of all the objects currently registered
	 * as <code><em>Foo</em>Listener</code>s
	 * upon this model.
	 * <code><em>Foo</em>Listener</code>s are registered using the
	 * <code>add<em>Foo</em>Listener</code> method.
	 *
	 * <p>
	 *
	 * You can specify the <code>listenerType</code> argument
	 * with a class literal,
	 * such as
	 * <code><em>Foo</em>Listener.class</code>.
	 * For example, you can query a
	 * <code>DefaultTreeModel</code> <code>m</code>
	 * for its tree model listeners with the following code:
	 *
	 * <pre>TreeModelListener[] tmls = (TreeModelListener[])(m.getListeners(TreeModelListener.class));</pre>
	 *
	 * If no such listeners exist, this method returns an empty array.
	 *
	 * @param listenerType the type of listeners requested; this parameter
	 *          should specify an interface that descends from
	 *          <code>java.util.EventListener</code>
	 * @return an array of all objects registered as
	 *          <code><em>Foo</em>Listener</code>s on this component,
	 *          or an empty array if no such
	 *          listeners have been added
	 * @exception ClassCastException if <code>listenerType</code>
	 *          doesn't specify a class or interface that implements
	 *          <code>java.util.EventListener</code>
	 *
	 * @see #getTreeModelListeners
	 *
	 * @since 1.3
	 */
	public EventListener[] getListeners(Class listenerType) {
		return this.listenerList.getListeners(listenerType);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param child DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int childIndex(Object child) {
		return ((Integer) child).intValue();
	}

	/**
	 * @see javax.swing.tree.TreeModel#getChild(Object, int)
	 */
	public Object getChild(Object parent, int index) {
		int par = childIndex(parent);
		return Integer.valueOf(this.tree.getChild(par, index));
	}

	/**
	 * @see javax.swing.tree.TreeModel#getChildCount(Object)
	 */
	public int getChildCount(Object parent) {
		int par = childIndex(parent);

		return this.tree.getDegree(par);
	}

	/**
	 * @see javax.swing.tree.TreeModel#getIndexOfChild(Object, Object)
	 */
	public int getIndexOfChild(Object parent, Object c) {
		int par = childIndex(parent);
		int search = childIndex(c);
		int i = 0;
		for (RowIterator it = this.tree.childrenIterator(par); it.hasNext();) {
			int child = it.nextRow();
			if (child == search)
				return i;
			i++;
		}
		return -1;
	}

	/**
	 * @see javax.swing.tree.TreeModel#getRoot()
	 */
	public Object getRoot() {
		return Integer.valueOf(0);
	}

	/**
	 * @see javax.swing.tree.TreeModel#isLeaf(Object)
	 */
	public boolean isLeaf(Object node) {
		return this.tree.isLeaf(childIndex(node));
	}

	/**
	 * @see javax.swing.tree.TreeModel#valueForPathChanged(TreePath, Object)
	 */
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	/**
	 * @see javax.swing.tree.RowMapper#getRowsForPaths(TreePath[])
	 */
	public int[] getRowsForPaths(TreePath[] path) {
		if (path == null || path.length == 0)
			return null;
		int[] rows = new int[path.length];
		for (int i = 0; i < path.length; i++) {
			rows[i] = childIndex(path[i].getLastPathComponent());
		}
		return rows;
	}

}
