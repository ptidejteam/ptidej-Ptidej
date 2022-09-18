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
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Vertex;

/**
 * A Swing component to manage a graph as an adjacency matrix.
 * Copyright (c) 2002 Ecole des Mines de Nantes, Mohammad Ghoniem
 * and Jean-Daniel Fekete
 * 
 * @version $Revision: 1.2 $
 * @author Mohammad Ghoniem and Jean-Daniel Fekete
 */
public class AdjacencyMatrix {
	NamedGraphDelegator graph; // underlying graph
	Vector columns; // vertex name visualized at each column
	Vector rows; // vertix name visualized at each row
	HashMap columnIndex; // index of colum names
	HashMap rowIndex; // index of row names
	EdgeWeightGetter weightGetter;

	/**
	 * Only one <code>ChangeEvent</code> is needed per object
	 * instance since the event's only (read-only) state is the
	 * source property.  The source of events generated here is
	 * always "this".
	 */
	protected transient ChangeEvent changeEvent = null;
	EventListenerList listenerList = new EventListenerList();

	/**
	 * Construct a AdjacencyMatrix from a Graph.
	 * @param g DOCUMENT ME!
	 */
	public AdjacencyMatrix(final NamedGraphDelegator g) {
		this.graph = g;
		this.columns = new Vector();
		this.rows = new Vector();
		this.columnIndex = new HashMap();
		this.rowIndex = new HashMap();
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

	/**
	 * Adds a column to the matrix with the given name.
	 * @param name DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean addColumn(final String name) {
		if (this.columnIndex.containsKey(name)) {
			return false;
		}

		return this.addColumn(this.findVertex(name));
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param vertex DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean addColumn(final Vertex vertex) {
		final String name = vertex.getLabel();

		if (this.columnIndex.containsKey(name)) {
			return false;
		}

		this.columnIndex.put(name, Integer.valueOf(this.columns.size()));
		this.columns.addElement(name);
		this.fireStateChanged();

		return true;
	}

	/**
	 * Adds a row to the matrix with the given name.
	 * @param name DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean addRow(final String name) {
		if (this.rowIndex.containsKey(name)) {
			return false;
		}

		return this.addRow(this.findVertex(name));
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param vertex DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean addRow(final Vertex vertex) {
		final String name = vertex.getLabel();

		if (this.rowIndex.containsKey(name)) {
			return false;
		}

		this.rowIndex.put(name, Integer.valueOf(this.rows.size()));
		this.rows.addElement(name);
		this.fireStateChanged();

		return true;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param label DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Vertex findVertex(final String label) {
		return this.graph.findVertex(label);
	}

	/**
	 * Runs each <code>ChangeListener</code>'s
	 * <code>stateChanged</code> method.
	 * 
	 * @see #setRangeProperties
	 * @see EventListenerList
	 */
	protected void fireStateChanged() {
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
	 * Returns the number of columns in the matrix.
	 * @return DOCUMENT ME!
	 */
	public int getColumnCount() {
		return this.columns.size();
	}

	/**
	 * Returns the name of the column at the given index.
	 * @param index DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getColumnName(final int index) {
		return (String) this.columns.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param v1 DOCUMENT ME!
	 * @param v2 DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Edge getEdge(final Vertex v1, final Vertex v2) {
		final Iterator iter = this.graph.getEdges(v1).iterator();

		//System.out.println("iterating on "+graph.getEdges(v1).size()+" edges");
		while (iter.hasNext()) {
			final Edge currentEdge = (Edge) iter.next();

			if (currentEdge.getOppositeVertex(v1) == v2) {
				return currentEdge;
			}
		}

		return null;
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
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public float getIntensity(final Edge e) {
		if (this.weightGetter != null) {
			return this.weightGetter.getWeight(e);
		}

		return 1.0f;
	}

	/**
	 * Looks up the edge corresponding the given column and row
	 * indexes and calls the previous method.
	 * @param column DOCUMENT ME!
	 * @param row DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float getIntensity(final int column, final int row) {
		return this.getIntensity(this.getEdge(
			this.getVertex(this.getColumnName(column)),
			this.getVertex(this.getRowName(row))));
	}

	/**
	 * Calls the method <code>getIntensity(EdgeWithHistory e)</code>
	 * with the edge corresponding to the given column and row.
	 * @param columnName DOCUMENT ME!
	 * @param rowName DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public float getIntensity(final String columnName, final String rowName) {
		return this.getIntensity(this.getEdge(
			this.getVertex(columnName),
			this.getVertex(rowName)));
	}

	/**
	 * Returns the number of rows in the matrix.
	 * @return DOCUMENT ME!
	 */
	public int getRowCount() {
		return this.rows.size();
	}

	/**
	 * Returns the name of the row at the given index.
	 * @param index DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public String getRowName(final int index) {
		return (String) this.rows.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param label DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Vertex getVertex(final String label) {
		return this.graph.getVertex(label);
	}

	/**
	 * Returns the weightGetter.
	 * 
	 * @return EdgeWeightGetter
	 */
	public EdgeWeightGetter getWeightGetter() {
		return this.weightGetter;
	}

	/**
	 * Returns the index of the named <code>Column</code>.
	 * @param columnName DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int indexOfColumn(final String columnName) {
		final Integer i = (Integer) this.columnIndex.get(columnName);

		if (i == null) {
			return -1;
		}

		return i.intValue();
	}

	/**
	 * Returns the index of the named <code>Row</code>.
	 * @param rowName DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int indexOfRow(final String rowName) {
		final Integer i = (Integer) this.rowIndex.get(rowName);

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
	 * @param index DOCUMENT ME!
	 * @param name DOCUMENT ME!
	 */
	public void insertColumn(int index, final String name) {
		try {
			this.columns.insertElementAt(name, index);
		}
		catch (final ArrayIndexOutOfBoundsException e) {
			System.err.println(e.toString());
			System.err.println("Putting the column at the end.");
			index = this.columns.size();
			this.columns.addElement(name);
		}
		finally {
			for (int i = index; i < this.columns.size(); i++) {
				this.columnIndex.put(this.columns.elementAt(i), Integer.valueOf(i));
			}
		}

		this.fireStateChanged();
	}

	/**
	 * Tries to insert the given row at the given index. If the index
	 * is out of bounds, the row is appended at the end of the
	 * matrix. Finally, the <code>rowIndex</code> are updated
	 * starting from the element at <code>index</code> to the end.
	 * @param index DOCUMENT ME!
	 * @param name DOCUMENT ME!
	 */
	public void insertRow(int index, final String name) {
		try {
			this.rows.insertElementAt(name, index);
		}
		catch (final ArrayIndexOutOfBoundsException e) {
			System.err.println(e.toString());
			System.err.println("Putting the row at the end.");
			index = this.rows.size();
			this.columns.addElement(name);
		}
		finally {
			for (int i = index; i < this.rows.size(); i++) {
				this.rowIndex.put(this.rows.elementAt(i), Integer.valueOf(i));
			}
		}

		this.fireStateChanged();
	}

	/**
	 * Moves the column at <code>index</code> to the last position.
	 * @param index DOCUMENT ME!
	 */
	public void moveColumnToBack(final int index) {
		if (index >= this.columns.size()) {
			return;
		}

		final String name = this.getColumnName(index);
		this.removeColumn(index);
		this.addColumn(name);
	}

	/**
	 * Moves the column at <code>index</code> to the last position.
	 * @param name DOCUMENT ME!
	 */
	public void moveColumnToBack(final String name) {
		this.moveColumnToBack(this.indexOfColumn(name));
	}

	/**
	 * Moves the column at <code>index</code> to the front.
	 * @param index DOCUMENT ME!
	 */
	public void moveColumnToFront(final int index) {
		if (index >= this.columns.size()) {
			return;
		}

		final String name = this.getColumnName(index);

		/**
		 * To avoid updating the indexes twice, once in the removal
		 * and once in the insertion, the removal is done by hand.
		 */
		this.columnIndex.remove(name);
		this.columns.removeElementAt(index);
		this.insertColumn(0, name);
	}

	/**
	 * Moves the named column to the front.
	 * @param name DOCUMENT ME!
	 */
	public void moveColumnToFront(final String name) {
		this.moveColumnToFront(this.indexOfColumn(name));
	}

	/**
	 * Moves the row at <code>index</code> to the last position.
	 * @param index DOCUMENT ME!
	 */
	public void moveRowToBack(final int index) {
		if (index >= this.rows.size()) {
			return;
		}

		final String name = this.getRowName(index);
		this.removeRow(index);
		this.addRow(name);
	}

	/**
	 * Moves the named row to the last position.
	 * @param name DOCUMENT ME!
	 */
	public void moveRowToBack(final String name) {
		this.moveRowToBack(this.indexOfRow(name));
	}

	/**
	 * Moves the row at <code>index</code> to the front.
	 * @param index DOCUMENT ME!
	 */
	public void moveRowToFront(final int index) {
		if (index >= this.rows.size()) {
			return;
		}

		final String name = this.getRowName(index);

		/**
		 * To avoid updating the indexes twice, once in the removal
		 * and once in the insertion, the removal is done by hand.
		 */
		this.rowIndex.remove(name);
		this.rows.removeElementAt(index);
		this.insertRow(0, name);
	}

	/**
	 * Moves the named row to the front.
	 * @param name DOCUMENT ME!
	 */
	public void moveRowToFront(final String name) {
		this.moveRowToFront(this.indexOfRow(name));
	}

	/**
	 * Populates the rowOrColumn of rows of the matrix from the given
	 * graph.
	 */
	public void populateMatrix() {
		// System.out.println("Filling the matrix...");
		final Iterator iter = this.graph.getVerticesIterator();

		while (iter.hasNext()) {
			final Vertex vertex = (Vertex) iter.next();
			final String name = vertex.getLabel();
			this.addRow(name);
			this.addColumn(name);
		}
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
	 * Removes the column having the given index and updates
	 * <code>columnIndex</code>.
	 * @param index DOCUMENT ME!
	 */
	public void removeColumn(final int index) {
		if (index >= this.columns.size()) {
			return;
		}

		this.columnIndex.remove(this.getColumnName(index));
		this.columns.removeElementAt(index);

		for (int i = index; i < this.columns.size(); i++) {
			this.columnIndex.put(this.columns.elementAt(i), Integer.valueOf(i));
		}

		this.fireStateChanged();
	}

	/**
	 * Removes the column having the given name.
	 * @param name DOCUMENT ME!
	 */
	public void removeColumn(final String name) {
		this.removeColumn(this.indexOfColumn(name));
	}

	/**
	 * Removes the row having the given index.
	 * @param index DOCUMENT ME!
	 */
	public void removeRow(final int index) {
		if (index >= this.rows.size()) {
			return;
		}

		this.rowIndex.remove(this.getRowName(index));
		this.rows.removeElementAt(index);

		for (int i = index; i < this.rows.size(); i++) {
			this.rowIndex.put(this.rows.elementAt(i), Integer.valueOf(i));
		}

		this.fireStateChanged();
	}

	/**
	 * Removes the row having the given name.
	 * @param name DOCUMENT ME!
	 */
	public void removeRow(final String name) {
		this.removeRow(this.indexOfRow(name));
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param row_array DOCUMENT ME!
	 */
	public void setRowOrder(final String[] row_array) {
		boolean modified = false;

		for (int i = 0; i < row_array.length; i++) {
			if (this.rows.elementAt(i) != row_array[i]) {
				modified = true;
				this.rows.setElementAt(row_array[i], i);
				this.rowIndex.put(row_array[i], Integer.valueOf(i));
			}
		}

		if (modified) {
			this.fireStateChanged();
		}
	}

	/**
	 * Sets the weightGetter.
	 * 
	 * @param weightGetter The weightGetter to set
	 */
	public void setWeightGetter(final EdgeWeightGetter weightGetter) {
		this.weightGetter = weightGetter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param comp DOCUMENT ME!
	 */
	public void sortColumns(final Comparator comp) {
		final Object[] col_array = this.columns.toArray();
		Arrays.sort(col_array, comp);
		this.sortColumns((String[]) col_array);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param col_array DOCUMENT ME!
	 */
	public void sortColumns(final String[] col_array) {
		boolean modified = false;

		for (int i = 0; i < col_array.length; i++) {
			if (this.columns.elementAt(i) != col_array[i]) {
				this.columns.setElementAt(col_array[i], i);
				modified = true;
				this.columnIndex.put(col_array[i], Integer.valueOf(i));
			}
		}

		if (modified) {
			this.fireStateChanged();
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param comp DOCUMENT ME!
	 */
	public void sortRows(final Comparator comp) {
		final Object[] row_array = this.rows.toArray();
		Arrays.sort(row_array, comp);
		this.setRowOrder((String[]) row_array);
	}

	/**
	 * Swaps the rowOrColumn by index.
	 * @param index1 DOCUMENT ME!
	 * @param index2 DOCUMENT ME!
	 * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
	 */
	public void swapColumns(final int index1, final int index2) {
		if (index1 == index2) {
			return;
		}

		if (index1 == -1 || index2 == -1) {
			throw new ArrayIndexOutOfBoundsException(
				"Missing column : cannot swap");
		}

		final Object elem1 = this.columns.elementAt(index1);
		final Object elem2 = this.columns.elementAt(index2);

		this.columns.setElementAt(elem2, index1);
		this.columns.setElementAt(elem1, index2);
		this.columnIndex.put(elem1, Integer.valueOf(index2));
		this.columnIndex.put(elem2, Integer.valueOf(index1));
		this.fireStateChanged();
	}

	/**
	 * Swaps the rowOrColumn by name.
	 * @param name1 DOCUMENT ME!
	 * @param name2 DOCUMENT ME!
	 */
	public void swapColumns(final String name1, final String name2) {
		this.swapColumns(this.indexOfColumn(name1), this.indexOfColumn(name2));
	}

	/**
	 * Swaps the rows by index.
	 * @param index1 DOCUMENT ME!
	 * @param index2 DOCUMENT ME!
	 * @throws ArrayIndexOutOfBoundsException DOCUMENT ME!
	 */
	public void swapRows(final int index1, final int index2) {
		if (index1 == index2) {
			return;
		}

		if (index1 == -1 || index2 == -1) {
			throw new ArrayIndexOutOfBoundsException(
				"Missing row : cannot swap");
		}

		final Object elem1 = this.rows.elementAt(index1);
		final Object elem2 = this.rows.elementAt(index2);

		this.rows.setElementAt(elem1, index2);
		this.rows.setElementAt(elem2, index1);
		this.rowIndex.put(elem1, Integer.valueOf(index2));
		this.rowIndex.put(elem2, Integer.valueOf(index1));
		this.fireStateChanged();
	}

	/**
	 * Swaps the rows by name.
	 * @param name1 DOCUMENT ME!
	 * @param name2 DOCUMENT ME!
	 */
	public void swapRows(final String name1, final String name2) {
		this.swapRows(this.indexOfRow(name1), this.indexOfRow(name2));
	}
}