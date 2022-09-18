package fr.emn.oadymppac.widgets;

import java.util.EventObject;

/**
 * @author Mohammad Ghoniem
 *
 * Fill in documentation !
 */
public class ClusterEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4946555641669972335L;
	static final int SINGLE_SWAP = 0;
	static final int ARRAY_SWAP = 1;
	static final int SINGLE_INSERT = 2;
	static final int ARRAY_INSERT = 3;
	int type = -1;
	String node1;
	String node2;
	String[] nodes1;
	String[] nodes2;

	/**
	 * Constructor for ClusterEvent.
	 * @param source
	 */
	public ClusterEvent(final Object source) {
		super(source);
		this.node1 = null;
		this.node2 = null;
	}

	public ClusterEvent(final Object source, final String n1, final String n2) {
		super(source);
		this.node1 = n1;
		this.node2 = n2;
	}

	public ClusterEvent(
		final Object source,
		final String n1,
		final String n2,
		final int type) {
		this(source, n1, n2);
		this.type = type;
	}

	public ClusterEvent(
		final Object source,
		final String n1,
		final String[] n,
		final int type) {
		super(source);
		this.node1 = n1;
		this.nodes1 = n;
		this.type = type;
	}

	public ClusterEvent(
		final Object source,
		final String[] nodes1,
		final String[] nodes2,
		final int type) {
		super(source);
		this.nodes1 = nodes1;
		this.nodes2 = nodes2;
		this.type = type;
		for (int i = 0; i < nodes1.length; i++) {
			System.out.print(nodes1[i] + " * ");
		}
		System.out.print("\n");
		for (int i = 0; i < nodes2.length; i++) {
			System.out.print(nodes2[i] + " * ");
		}
		System.out.print("\n");
	}

	/**
	 * Returns the type.
	 * @return int
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(final int type) {
		this.type = type;
	}

}
