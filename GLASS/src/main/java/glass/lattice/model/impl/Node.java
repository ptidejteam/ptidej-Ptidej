package glass.lattice.model.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;



public class Node implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1103587923653477580L;
	
	private String name;
	private List<NodeFeatureType> types = new ArrayList<NodeFeatureType>();
	//private FeatureType type;
	private String extent;
	private String intent;
	
	private int ID;
	
	public String getExtent() {
		return extent;
	}
	public void setExtent(String extent) {
		this.extent = extent;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	private List<Node> children = new CopyOnWriteArrayList<Node>();
	
	public Node(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node connection) {
		children.add(connection);
	}
	
	
	

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(name, other.name);
	}


	public List<NodeFeatureType> getTypes() {
		return types;
	}
	public void setTypes(List<NodeFeatureType> types) {
		this.types = types;
	}
	@Override
	public String toString() {
		return "Node [name=" + name + ", types=" + types + ", extent=" + extent + ", intent=" + intent + ", ID=" + ID
				+ ", children=" + children + "], hashcode "+ Objects.hash(extent,intent);
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	
}
