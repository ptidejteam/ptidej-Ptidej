package fr.emn.oadymppac.graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import salvo.jesus.graph.Vertex;

/**
 * DOCUMENT ME!
 * 
 * @author Jean-Daniel Fekete
 */
public class AdjacencyMatrixRowColumn {
	/**
	 * Only one <code>ChangeEvent</code> is needed per object
	 * instance since the event's only (read-only) state is the
	 * source property.  The source of events generated here is
	 * always "this".
	 */
	protected transient ChangeEvent changeEvent = null;
	HashMap index; // index of row/colum names
	Vector rowOrColumn; // vertex name visualized at each row or column
	NamedGraphDelegator graph; // underlying graph
	EventListenerList listenerList = new EventListenerList();
	boolean notify = true;

	/**
	 * Creates a new AdjacencyMatrixRowColumn object.
	 * 
	 * @param graph DOCUMENT ME!
	 */
	public AdjacencyMatrixRowColumn(final NamedGraphDelegator graph) {
		this.graph = graph;
		this.index = new HashMap();
		this.rowOrColumn = new Vector();
	}

	/**
	 * Adds a column to the matrix with the given name.
	 * 
	 * @param name DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean add(final String name) {
		if (this.index.containsKey(name)) {
			return false;
		}

		return this.add(this.graph.findVertex(name));
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param vertex DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean add(final Vertex vertex) {
		if (vertex == null) {
			return false;
		}

		final String name = vertex.getLabel();

		if (this.index.containsKey(name)) {
			return false;
		}

		this.index.put(name, Integer.valueOf(this.rowOrColumn.size()));
		this.rowOrColumn.addElement(name);
		this.fireStateChanged();

		return true;
	}

	/**
	 * Adds a <code>ChangeListener</code>.  The change listeners are
	 * run each time any one of the properties changes.
	 * 
	 * @param l the ChangeListener to add
	 * 
	 * @see #removeChangeListener
	 * @see BoundedRangeModel#addChangeListener
	 */
	public void addChangeListener(final ChangeListener l) {
		this.listenerList.add(ChangeListener.class, l);
	}

	public void disableNotify() {
		this.notify = false;
	}

	public void enableNotify() {
		this.notify = true;
	}

	/**
	 * Runs each <code>ChangeListener</code>'s
	 * <code>stateChanged</code> method.
	 * 
	 * @see #setRangeProperties
	 * @see EventListenerList
	 */
	protected void fireStateChanged() {
		if (!this.notify) {
			return;
		}
		final Object[] listeners = this.listenerList.getListenerList();

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				if (this.changeEvent == null) {
					this.changeEvent = new ChangeEvent(this);
				}

				((ChangeListener) listeners[i + 1])
					.stateChanged(this.changeEvent);
			}
		}
	}

	/**
	 * Returns an array of all the change listeners registered on
	 * this <code>BasicAjacencyMatrix</code>.
	 * 
	 * @return all of this object <code>ChangeListener</code>s or an
	 *         empty array if no change listeners are currently
	 *         registered
	 * 
	 * @since 1.4
	 * @see #addChangeListener
	 * @see #removeChangeListener
	 */
	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) this.listenerList
			.getListeners(ChangeListener.class);
	}

	/**
	 * Returns the number of rowOrColumn in the matrix.
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getCount() {
		return this.rowOrColumn.size();
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public NamedGraphDelegator getGraph() {
		return this.graph;
	}

	/**
	 * Returns the name of the column at the given index.
	 * 
	 * @param index DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getName(final int index) {
		return (String) this.rowOrColumn.elementAt(index);
	}

	public Vector getVertices() {
		return this.rowOrColumn;
	}

	/**
	 * Returns the index of the named row or column.
	 * 
	 * @param columnName DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int indexOf(final String columnName) {
		final Integer i = (Integer) this.index.get(columnName);

		if (i == null) {
			return -1;
		}

		return i.intValue();
	}

	/**
	 * Tries to insert the given column at the given index. If the
	 * index is out of bounds, the column is appended at the end of
	 * the matrix. Finally, the <code>columnIndex</code> are updated
	 * starting from the element at <code>index</code> to the end.
	 * 
	 * @param pos DOCUMENT ME!
	 * @param name DOCUMENT ME!
	 */
	public void insert(int pos, final String name) {
		try {
			this.rowOrColumn.insertElementAt(name, pos);
		}
		catch (final ArrayIndexOutOfBoundsException e) {
			System.err.println(e.toString());
			System.err.println("Putting the column at the end.");
			pos = this.rowOrColumn.size();
			this.rowOrColumn.addElement(name);
		}
		finally {
			for (int i = pos; i < this.rowOrColumn.size(); i++) {
				this.index.put(this.rowOrColumn.elementAt(i), Integer.valueOf(i));
			}
		}

		this.fireStateChanged();
	}

	/**
	 * Moves the column at <code>index</code> to the last position.
	 * 
	 * @param pos DOCUMENT ME!
	 */
	public void moveToBack(final int pos) {
		if (pos >= this.rowOrColumn.size()) {
			return;
		}

		final String name = this.getName(pos);
		this.remove(pos);
		this.add(name);
	}

	/**
	 * Moves the column at <code>index</code> to the last position.
	 * 
	 * @param name DOCUMENT ME!
	 */
	public void moveToBack(final String name) {
		this.moveToBack(this.indexOf(name));
	}

	/**
	 * Moves the column at <code>index</code> to the front.
	 * 
	 * @param pos DOCUMENT ME!
	 */
	public void moveToFront(final int pos) {
		if (pos >= this.rowOrColumn.size() || pos < 0) {
			return;
		}

		final String name = this.getName(pos);

		/**
		 * To avoid updating the indexes twice, once in the removal
		 * and once in the insertion, the removal is done by hand.
		 */
		this.index.remove(name);
		this.rowOrColumn.removeElementAt(pos);
		this.insert(0, name);
	}

	/**
	 * Moves the named column to the front.
	 * 
	 * @param name DOCUMENT ME!
	 */
	public void moveToFront(final String name) {
		this.moveToFront(this.indexOf(name));
	}

	public void moveToPosition(final String name, final int newpos) {

		if (newpos >= this.rowOrColumn.size() || newpos < 0) {
			return;
		}

		final int pos = this.indexOf(name);

		if (pos == newpos) {
			return;
		}

		if (pos < 0) {
			System.out.println("vertex : " + name + " not found !");
			return;
		}

		this.rowOrColumn.removeElementAt(pos);
		this.index.remove(name);

		if (pos > newpos) {
			// moving forward	
			this.insert(newpos, name);
		}
		else {
			this.insert(newpos - 1, name);
		}
	}

	public void notifyListeners() {
		this.fireStateChanged();
	}

	/**
	 * Populates the rowOrColumn of the matrix from the given
	 * graph.
	 */
	public void populateMatrix() {
		// System.out.println("Filling the matrix...");
		final Iterator iter = this.graph.getVerticesIterator();

		while (iter.hasNext()) {
			final Vertex vertex = (Vertex) iter.next();
			final String name = vertex.getLabel();
			this.add(name);
		}
	}

	/**
	 * Populates the rowOrColumn of the matrix from the given
	 * array of names.
	 */
	public void populateMatrix(final String[] names) {
		for (int i = 0; i < names.length; i++) {
			this.add(names[i]);
		}
	}

	/**
	 * Removes the column having the given index and updates
	 * <code>columnIndex</code>.
	 * 
	 * @param pos DOCUMENT ME!
	 */
	public void remove(final int pos) {
		if (pos >= this.rowOrColumn.size()) {
			return;
		}

		this.index.remove(this.getName(pos));
		this.rowOrColumn.removeElementAt(pos);

		for (int i = pos; i < this.rowOrColumn.size(); i++) {
			this.index.put(this.rowOrColumn.elementAt(i), Integer.valueOf(i));
		}

		this.fireStateChanged();
	}

	/**
	 * Removes the column having the given name.
	 * 
	 * @param name DOCUMENT ME!
	 */
	public void remove(final String name) {
		this.remove(this.indexOf(name));
	}

	/**
	 * Removes all the columns and their mappings.
	 */
	public void removeAll() {
		this.index.clear();
		this.rowOrColumn.clear();
	}

	/**
	 * Removes a <code>ChangeListener</code>.
	 * 
	 * @param l the <code>ChangeListener</code> to remove
	 * 
	 * @see #addChangeListener
	 * @see BoundedRangeModel#removeChangeListener
	 */
	public void removeChangeListener(final ChangeListener l) {
		this.listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param comp DOCUMENT ME!
	 */
	public void sort(final Comparator comp) {
		if (this.rowOrColumn.isEmpty()) {
			return;
		}
		final Object[] col_array = this.rowOrColumn.toArray();
		Arrays.sort(col_array, comp);
		this.sort(col_array);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param col_array DOCUMENT ME!
	 */
	public void sort(final Object[] col_array) {
		boolean modified = false;

		for (int i = 0; i < col_array.length; i++) {
			if (this.rowOrColumn.elementAt(i) != col_array[i]) {
				this.rowOrColumn.setElementAt(col_array[i], i);
				modified = true;
				this.index.put(col_array[i], Integer.valueOf(i));
			}
		}

		if (modified) {
			this.fireStateChanged();
		}
	}

	/**
	 * Swaps the rowOrColumn by index.
	 * 
	 * @param index1 DOCUMENT ME!
	 * @param index2 DOCUMENT ME!
	 * 
	 * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
	 */
	public void swap(final int index1, final int index2) {
		if (index1 == index2) {
			return;
		}

		if (index1 == -1 || index2 == -1) {
			throw new ArrayIndexOutOfBoundsException(
				"Missing column : cannot swap");
		}

		final Object elem1 = this.rowOrColumn.elementAt(index1);
		final Object elem2 = this.rowOrColumn.elementAt(index2);

		this.rowOrColumn.setElementAt(elem2, index1);
		this.rowOrColumn.setElementAt(elem1, index2);
		this.index.put(elem1, Integer.valueOf(index2));
		this.index.put(elem2, Integer.valueOf(index1));
		this.fireStateChanged();
	}

	/**
	 * Swaps the rowOrColumn by name.
	 * 
	 * @param name1 DOCUMENT ME!
	 * @param name2 DOCUMENT ME!
	 */
	public void swap(final String name1, final String name2) {
		this.swap(this.indexOf(name1), this.indexOf(name2));
	}
}