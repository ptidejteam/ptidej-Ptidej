package fr.emn.oadymppac.widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import dram.utils.salvo.MyDirectedGraphImpl;

/**
 * A Swing component to visualize a graph as an adjacency matrix.
 * Copyright (c) 2002 Ecole des Mines de Nantes, Mohammad Ghoniem
 * and Jean-Daniel Fekete
 * 
 * @version $Revision: 1.3 $
 * @author Mohammad Ghoniem and Jean-Daniel Fekete
 */
public class JAdjacencyMatrix extends JComponent implements MouseInputListener,
		ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4908551197487539797L;
	static Color[] ramp = new Color[256];

	static {
		for (int i = 0; i < 255; i++) {
			JAdjacencyMatrix.ramp[i] =
				new Color(i / 255.0f, i / 255.0f, i / 255.0f);
		}
	}

	static final int SORT_ON_DEGREE = 0;
	static final int SORT_ON_INCOMING = 1;
	static final int SORT_ON_OUTGOING = 2;
	Vector columns;
	Vector rows;
	Graph graph;
	HashMap columnIndexes; // index of colum names
	HashMap rowIndexes; // index of row names
	HashMap nameVertex; // association between vertices and their names
	boolean magnifyMode = false;
	boolean editMode = false;

	/*    double maxw, minw = 0;
	   public Color getEdgeColor(double d){
	       return Color.getHSBColor(1.0f, 1.0f, ((float)(d - minw))/((float)(maxw - minw)));
	   }*/
	Color filledCellColor = Color.black;
	Color highlightColor = Color.yellow;
	Color hightlightFilled = Color.red;
	Color matchColor = Color.green;
	int pickedx;
	int pickedy;
	/** DOCUMENT ME! */
	public String pickedColumn;
	public String pickedRow;
	int rowSort = 0;
	int columnSort = 0;

	/**
	 * Creates a new JAdjacencyMatrix object.
	 */
	public JAdjacencyMatrix() {
	}

	/**
	 * Construct a JAdjacencyMatrix from a Graph.
	 * @param g the Graph
	 */
	public JAdjacencyMatrix(final Graph g) {
		this.setPreferredSize(new Dimension(500, 500));
		this.columns = new Vector();
		this.rows = new Vector();
		this.columnIndexes = new HashMap();
		this.rowIndexes = new HashMap();
		this.nameVertex = new HashMap();

		this.graph = g;
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				JAdjacencyMatrix.this.setPreferredSize(JAdjacencyMatrix.this
					.getSize());
				JAdjacencyMatrix.this.repaint();
			}
		});
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.createNameVertex();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void actionPerformed(final ActionEvent e) {
		final Object source = e.getSource();

		if (source instanceof JComboBox) {
			if (((JComboBox) source).getActionCommand().equals("rowSort")) {
				this.rowSort = ((JComboBox) source).getSelectedIndex();
			}
			else if (((JComboBox) source).getActionCommand().equals(
				"columnSort")) {
				this.columnSort = ((JComboBox) source).getSelectedIndex();
			}
		}
		else if (source instanceof JButton) {
			final String command = ((JButton) source).getActionCommand();

			if (command.equals("sortUp")) {
				this.sortRows("descending", this.rowSort);
			}
			else if (command.equals("sortDown")) {
				this.sortRows("ascending", this.rowSort);
			}
			else if (command.equals("sortLeft")) {
				this.sortColumns("ascending", this.columnSort);
			}
			else if (command.equals("sortRight")) {
				this.sortColumns("descending", this.columnSort);
			}
		}

		if (e.getActionCommand().equals("edit")) {
			this.editMode = ((JCheckBox) e.getSource()).isSelected();
		}
		else if (e.getActionCommand().equals("magnify")) {
			this.magnifyMode = ((JCheckBox) e.getSource()).isSelected();
		}
	}

	/**
	 * Adds a column to the matrix with the given name.
	 * @param name DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean addColumn(final String name) {
		if (this.columnIndexes.containsKey(name)) {
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
		final String name = vertex.toString();

		if (this.columnIndexes.containsKey(name)) {
			return false;
		}

		this.columnIndexes.put(name, Integer.valueOf(this.columns.size()));
		this.nameVertex.put(name, vertex);
		this.columns.addElement(name);

		return true;
	}

	/**
	 * Adds a row to the matrix with the given name.
	 * @param name DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public boolean addRow(final String name) {
		if (this.rowIndexes.containsKey(name)) {
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
		final String name = vertex.toString();

		if (this.rowIndexes.containsKey(name)) {
			return false;
		}

		this.rowIndexes.put(name, Integer.valueOf(this.rows.size()));
		this.nameVertex.put(name, vertex);
		this.rows.addElement(name);

		return true;
	}

	/**
	 * Create the association between names and vertices
	 */
	public void createNameVertex() {
		final Iterator iter = this.graph.getVerticesIterator();

		while (iter.hasNext()) {
			final Vertex vertex = (Vertex) iter.next();
			final String name = vertex.toString();
			this.nameVertex.put(name, vertex);
		}
	}

	/**
	 * Returns the vertex having the passed name
	 * if no such vertex exists already, it is 
	 * created and returned.
	 *
	 * @param name the name of the vertex
	 *
	 * @return a Vertex or in case the insertion of the Vertex in the graph fails.
	 */
	public Vertex findVertex(final String name) {
		VertexImpl vertex = (VertexImpl) this.nameVertex.get(name);

		if (vertex == null) {
			vertex = new VertexImpl();
			vertex.setLabel(name);

			try {
				this.graph.add(vertex);
			}
			catch (final Exception e) {
				return null;
			}

			this.nameVertex.put(name, vertex);
		}

		return vertex;
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
	 * @param index DOCUMENT ME!
	 * @param row DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getDegree(final int index, final boolean row) {
		Vertex v = null;

		if (row) {
			v = this.findVertex(this.getRowName(index));
		}
		else {
			v = this.findVertex(this.getColumnName(index));
		}

		return this.graph.getDegree(v);
	}

	/**
	 * différencier les outgoing edges et les incoming edges des
	 * sommets
	 * 
	 * @param name
	 * 
	 * @return the degree of the named vertex
	 */
	public int getDegree(final String name) {
		final Vertex v = this.findVertex(name);

		return this.graph.getDegree(v);
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
		if (v1 == null || v2 == null) {
			return null;
		}

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
	public boolean getEditMode() {
		return this.editMode;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 * @param row DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getIncoming(final int index, final boolean row) {
		Vertex v = null;

		if (row) {
			v = this.findVertex(this.getRowName(index));
		}
		else {
			v = this.findVertex(this.getColumnName(index));
		}

		return ((MyDirectedGraphImpl) this.graph)
			.getIncomingAdjacentVertices(v)
			.size();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getIncoming(final String name) {
		final Vertex v = this.findVertex(name);

		return ((MyDirectedGraphImpl) this.graph)
			.getIncomingAdjacentVertices(v)
			.size();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public float getIntensity(final Edge e) {
		return 0.0f;
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
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean getMagnifyMode() {
		return this.magnifyMode;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 * @param row DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getOutgoing(final int index, final boolean row) {
		Vertex v = null;

		if (row) {
			v = this.findVertex(this.getRowName(index));
		}
		else {
			v = this.findVertex(this.getColumnName(index));
		}

		return ((MyDirectedGraphImpl) this.graph)
			.getOutgoingAdjacentVertices(v)
			.size();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getOutgoing(final String name) {
		final Vertex v = this.findVertex(name);

		return ((MyDirectedGraphImpl) this.graph)
			.getOutgoingAdjacentVertices(v)
			.size();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPickedColumn() {
		return this.pickedColumn;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public String getPickedRow() {
		return this.pickedRow;
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
	 * @param index DOCUMENT ME!
	 * @param row DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getSortValue(final int index, final boolean row, final int select) {
		switch (select) {
			case SORT_ON_DEGREE :
				return this.getDegree(index, row);

			case SORT_ON_INCOMING :
				return this.getIncoming(index, row);

			case SORT_ON_OUTGOING :
				return this.getOutgoing(index, row);

			default :
				return this.getDegree(index, row);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param name DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int getSortValue(final String name, final int select) {
		if (this.graph instanceof DirectedGraph) {
			switch (select) {
				case SORT_ON_DEGREE :
					return this.getDegree(name);

				case SORT_ON_INCOMING :
					return this.getIncoming(name);

				case SORT_ON_OUTGOING :
					return this.getOutgoing(name);

				default :
					return this.getDegree(name);
			}
		}
		else {
			return this.getDegree(name);
		}
	}

	/**
	 * Returns the vertex having the passed name 
	 * or null if no mapping is available for this name.
	 *
	 * @param name the name of the vertex
	 *
	 * @return DOCUMENT ME!
	 */
	public Vertex getVertex(final String name) {
		return (Vertex) this.nameVertex.get(name);
	}

	/**
	 * Returns the index of the named <code>Column</code>.
	 * @param columnName DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int indexOfColumn(final String columnName) {
		final Integer i = (Integer) this.columnIndexes.get(columnName);

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
		final Integer i = (Integer) this.rowIndexes.get(rowName);

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
				this.columnIndexes.put(
					this.columns.elementAt(i),
					Integer.valueOf(i));
			}
		}
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
				this.rowIndexes.put(this.rows.elementAt(i), Integer.valueOf(i));
			}
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseClicked(final MouseEvent e) {
		if (this.editMode) {
			this.repaint();
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseDragged(final MouseEvent e) {
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseEntered(final MouseEvent e) {
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseExited(final MouseEvent e) {
		this.pickedColumn = null;
		this.pickedRow = null;
		this.repaint();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseMoved(final MouseEvent e) {
		this.pickedx = e.getX();
		this.pickedy = e.getY();

		final int labelsize = 30;
		final float w =
			((float) this.getSize().getWidth() - labelsize)
					/ this.getColumnCount();
		final float h =
			((float) this.getSize().getHeight() - labelsize)
					/ this.getRowCount();

		/*        FisheyeLens f = null;
		   x = labelsize;
		   for (int i = 0; i < getColumnCount(); i++){
		       //x = i*w+labelsize;
		        if (fisheyeLenses.size()==0){
		           w = ((float)getSize().getWidth()-labelsize)/getColumnCount();
		       } else {
		           f = checkFisheye(i, 0);
		           if (f != null){
		               switch (f.getTransform()){
		                   case FisheyeLens.X_BIFOCAL :
		                       w = f.getColumnWidth(i, 0); break;
		                   case FisheyeLens.Y_BIFOCAL :
		                       w = 2*(((float)getSize().getWidth()-labelsize)/getColumnCount())/3;
		               }
		           }
		       }
		       if (pickedx > x && pickedx < x + w){
		           pickedColumn = getColumnName(i);
		           if (f !=null)
		               f.setX(i);
		       }
		       for (int j = 0; j < getRowCount(); j++) {
		           y = j*h + labelsize;
		           if (pickedy > y && pickedy < y + w){
		               pickedRow = getRowName(j);
		               if (f !=null)
		                   f.setY(j);
		           }
		       }
		       x = x + w;
		   }
		 */
		final int i = (int) ((this.pickedx - labelsize) / w);

		if (i < 0 || i >= this.getColumnCount()) {
			this.pickedColumn = null;
		}
		else {
			this.pickedColumn = this.getColumnName(i);
		}

		final int j = (int) ((this.pickedy - labelsize) / h);

		if (j < 0 || j >= this.getRowCount()) {
			this.pickedRow = null;
		}
		else {
			this.pickedRow = this.getRowName(j);
		}

		this.repaint();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mousePressed(final MouseEvent e) {
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void mouseReleased(final MouseEvent e) {
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
		this.columnIndexes.remove(name);
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
		this.rowIndexes.remove(name);
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
	 * DOCUMENT ME!
	 *
	 * @param g DOCUMENT ME!
	 */
	public void paintComponent(final Graphics g) {
		final int labelsize = 30;
		float w =
			((float) this.getSize().getWidth() - labelsize)
					/ this.getColumnCount();
		final float h =
			((float) this.getSize().getHeight() - labelsize)
					/ this.getRowCount();
		float x = 0;
		float y = 0;
		String columnName;
		String rowName;

		final Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Float(
			0f,
			0f,
			(float) this.getSize().getWidth(),
			(float) this.getSize().getHeight()));
		g2.setFont(this.getFont());
		g2.setColor(Color.black);

		final FontMetrics fm = g2.getFontMetrics();

		for (int j = 0; j < this.getRowCount(); j++) {
			rowName = (String) this.rows.elementAt(j);

			if (fm.getStringBounds(rowName, g2).getWidth() < labelsize) {
				g2
					.drawString(
						(String) this.rows.elementAt(j),
						(labelsize - ((String) this.rows.elementAt(j)).length()) / 2,
						j * h + 3 * labelsize / 2);
			}

			y = j * h + labelsize;

			/**
			 * Highlights the picked row.
			 */
			if (rowName.equals(this.pickedRow)) {
				g2.setColor(this.highlightColor);
				g2.fill(new Rectangle2D.Float(labelsize, y, (float) this
					.getSize()
					.getWidth() - labelsize, h));
			}
			else if (rowName.equals(this.pickedColumn)) {
				g2.setColor(this.matchColor);
				g2.fill(new Rectangle2D.Float(labelsize, y, (float) this
					.getSize()
					.getWidth() - labelsize, h));
			}
		}

		x = labelsize;

		Vertex vertex;

		for (int i = 0; i < this.getColumnCount(); i++) {
			columnName = (String) this.columns.elementAt(i);
			vertex = this.getVertex(columnName);

			java.util.List l;

			if (this.graph instanceof DirectedGraph) {
				l = ((DirectedGraph) this.graph).getOutgoingEdges(vertex);
			}
			else {
				l = this.graph.getEdges(vertex);
			}

			w =
				((float) this.getSize().getWidth() - labelsize)
						/ this.getColumnCount();

			if (w > 3
					&& fm.getStringBounds(columnName, g2).getWidth() < labelsize) {
				g2.drawString(columnName, x + (labelsize - columnName.length())
						/ 2, labelsize / 2);
			}

			/**
			 * Highlights the picked column
			 */
			if (columnName.equals(this.pickedColumn)) {
				g2.setColor(this.highlightColor);
				g2.fill(new Rectangle2D.Float(x, labelsize, w, (float) this
					.getSize()
					.getHeight() - labelsize));
			}
			else if (columnName.equals(this.pickedRow)) {
				g2.setColor(this.matchColor);
				g2.fill(new Rectangle2D.Float(x, labelsize, w, (float) this
					.getSize()
					.getHeight() - labelsize));
			}

			/**
			 * Draws the matrix cells of the current column.
			 */
			for (final Iterator it = l.iterator(); it.hasNext();) {
				final Edge edge = (Edge) it.next();
				rowName = edge.getOppositeVertex(vertex).toString();
				y = this.indexOfRow(rowName) * h + labelsize;

				if (rowName.equals(this.pickedRow)
						|| columnName.equals(this.pickedColumn)) {
					g2.setColor(this.hightlightFilled);
				}
				else {
					g2
						.setColor(JAdjacencyMatrix.ramp[(int) (this
							.getIntensity(edge) * (JAdjacencyMatrix.ramp.length - 1))]);
				}

				g2.fill(new Rectangle2D.Float(x, y, w, h));
			}

			x = x + w;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param left DOCUMENT ME!
	 * @param right DOCUMENT ME!
	 * @param pivot DOCUMENT ME!
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int partitionIt(
		final int left,
		final int right,
		final double pivot,
		final int order,
		final int select) {
		int leftPtr = left - 1; // left    (after   ++)
		int rightPtr = right; // right-1 (after --)

		switch (order) {
			case 0 :

				while (true) { // find bigger item

					while (this.getSortValue(
						(String) this.rows.elementAt(++leftPtr),
						select) < pivot) {
						; // (nop)
					}

					// find smaller item
					while (rightPtr > 0
							&& this.getSortValue(
								(String) this.rows.elementAt(--rightPtr),
								select) > pivot) {
						; // (nop)
					}

					if (leftPtr > rightPtr) // if pointers cross,

					{
						break; //    partition done
					}
					else // not crossed, so

					{
						this.swapRows(leftPtr, rightPtr); //    swap elements
					}
				}

				;

				break; // end while(true)

			case 1 :

				while (true) {
					while (rightPtr > 0
							&& this.getSortValue(
								(String) this.rows.elementAt(--rightPtr),
								select) < pivot) {
						; // (nop)
					}

					// find bigger item
					while (this.getSortValue(
						(String) this.rows.elementAt(++leftPtr),
						select) > pivot) {
						; // (nop)
					}

					if (leftPtr > rightPtr) // if pointers cross,

					{
						break; //    partition done
					}
					else // not crossed, so

					{
						this.swapRows(leftPtr, rightPtr); //    swap elements
					}
				} // end while(true)
		}

		this.swapRows(leftPtr, right); // restore pivot

		return leftPtr; // return pivot location
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param left DOCUMENT ME!
	 * @param right DOCUMENT ME!
	 * @param pivot DOCUMENT ME!
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public int partitionItB(
		final int left,
		final int right,
		final double pivot,
		final int order,
		final int select) {
		int leftPtr = left - 1; // left    (after   ++)
		int rightPtr = right; // right-1 (after --)

		switch (order) {
			case 0 :

				while (true) { // find bigger item

					while (this.getSortValue(
						(String) this.columns.elementAt(++leftPtr),
						select) < pivot) {
						; // (nop)
					}

					// find smaller item
					while (rightPtr > 0
							&& this.getSortValue(
								(String) this.columns.elementAt(--rightPtr),
								select) > pivot) {
						; // (nop)
					}

					if (leftPtr > rightPtr) // if pointers cross,

					{
						break; //    partition done
					}
					else // not crossed, so

					{
						this.swapColumns(leftPtr, rightPtr); //    swap elements
					}
				}

				;

				break; // end while(true)

			case 1 :

				while (true) {
					while (rightPtr > 0
							&& this.getSortValue(
								(String) this.columns.elementAt(--rightPtr),
								select) < pivot) {
						; // (nop)
					}

					// find bigger item
					while (this.getSortValue(
						(String) this.columns.elementAt(++leftPtr),
						select) > pivot) {
						; // (nop)
					}

					if (leftPtr > rightPtr) // if pointers cross,

					{
						break; //    partition done
					}
					else // not crossed, so

					{
						this.swapColumns(leftPtr, rightPtr); //    swap elements
					}
				} // end while(true)
		}

		this.swapColumns(leftPtr, right); // restore pivot

		return leftPtr; // return pivot location
	}

	/**
	 * Populates the columns of rows of the matrix from the given
	 * graph.
	 */
	public void populateMatrix() {
		System.out.println("Filling the matrix...");

		final Iterator iter = this.graph.getVerticesIterator();

		while (iter.hasNext()) {
			final Vertex vertex = (Vertex) iter.next();
			final String name = vertex.toString();
			this.addRow(name);
			this.addColumn(name);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param left DOCUMENT ME!
	 * @param right DOCUMENT ME!
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 */
	public void recQuickSort(
		final int left,
		final int right,
		final int order,
		final int select) {
		if (right - left <= 0) // if size <= 1,

		{
			return; // already sorted
		}
		else // size is 2 or larger
		{
			final double pivot =
				this.getSortValue((String) this.rows.elementAt(right), select);

			//double pivot = getDegree((String)rows.elementAt(right));
			// rightmost item
			// partition range
			final int partition =
				this.partitionIt(left, right, pivot, order, select);
			this.recQuickSort(left, partition - 1, order, select); // sort left side
			this.recQuickSort(partition + 1, right, order, select); // sort right side
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param left DOCUMENT ME!
	 * @param right DOCUMENT ME!
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 */
	public void recQuickSortB(
		final int left,
		final int right,
		final int order,
		final int select) {
		if (right - left <= 0) // if size <= 1,

		{
			return; // already sorted
		}
		else // size is 2 or larger
		{
			final double pivot =
				this.getSortValue(
					(String) this.columns.elementAt(right),
					select);

			//double pivot = getDegree((String)columns.elementAt(right));
			// rightmost item
			// partition range
			final int partition =
				this.partitionItB(left, right, pivot, order, select);
			this.recQuickSortB(left, partition - 1, order, select); // sort left side
			this.recQuickSortB(partition + 1, right, order, select); // sort right side
		}
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

		this.columnIndexes.remove(this.getColumnName(index));
		this.columns.removeElementAt(index);

		for (int i = index; i < this.columns.size(); i++) {
			this.columnIndexes.put(this.columns.elementAt(i), Integer.valueOf(i));
		}
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

		this.rowIndexes.remove(this.getRowName(index));
		this.rows.removeElementAt(index);

		for (int i = index; i < this.rows.size(); i++) {
			this.rowIndexes.put(this.rows.elementAt(i), Integer.valueOf(i));
		}
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
	 * @param b DOCUMENT ME!
	 */
	public void setEditMode(final boolean b) {
		this.editMode = b;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param g DOCUMENT ME!
	 */
	public void setGraph(final Graph g) {
		this.graph = g;
		this.createNameVertex();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param b DOCUMENT ME!
	 */
	public void setMagnifyMode(final boolean b) {
		this.magnifyMode = b;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 */
	public void sortColumns(final String order, final int select) {
		if (order.equals("ascending")) {
			this.recQuickSortB(0, this.columns.size() - 1, 1, select);
		}

		if (order.equals("descending")) {
			this.recQuickSortB(0, this.columns.size() - 1, 0, select);
		}

		this.repaint();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param order DOCUMENT ME!
	 * @param select DOCUMENT ME!
	 */
	public void sortRows(final String order, final int select) {
		if (order.equals("ascending")) {
			this.recQuickSort(0, this.rows.size() - 1, 1, select);
		}

		if (order.equals("descending")) {
			this.recQuickSort(0, this.rows.size() - 1, 0, select);
		}

		this.repaint();
	}

	/**
	 * Swaps the columns by index.
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
		this.columnIndexes.put(elem1, Integer.valueOf(index2));
		this.columnIndexes.put(elem2, Integer.valueOf(index1));
	}

	/**
	 * Swaps the columns by name.
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
		this.rowIndexes.put(elem1, Integer.valueOf(index2));
		this.rowIndexes.put(elem2, Integer.valueOf(index1));
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