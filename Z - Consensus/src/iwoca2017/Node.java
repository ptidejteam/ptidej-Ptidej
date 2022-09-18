package iwoca2017;

import java.util.ArrayList;
import java.util.List;


public class Node implements Comparable <Node> {
	public int data;
	public int position;
    public List<Node> parents;
    public List<Node> children;
    
    public Node(int i){
    	data= i;
    	position= i*1000;
        parents = new ArrayList<Node>();
        children = new ArrayList<Node>();
    }
    
    public void addParent(Node u){
    	parents.add(u);
    }
    
    public void addChild(Node u){
    	children.add(u);
    }
    
    public boolean hasChildren(){
    	return !(children.size()==0);
    }
    
    public void setPosition(int pos){
    	position = pos;
    }
    
    public String toString (){
    	boolean isDot = true;
	    if (isDot == false){
	    	String s = "{";
	    	for (Node n : parents){
	    		s += n.data+ " ";
	    	}
	    	s += "} > *" + data + "* > {";
	    	for (Node n : children){
	    		s += n.data+ " ";
	    	}
	    	s += "}";
			return s;
    	}else{
    		String s = "";
	    	for (Node n : parents){
	    		if (data < n.data)
	    			s += n.data + " -> "+ data+ "\n";
	    	}
	    	for (Node n : children){
	    		if (data < n.data)
	    			s += data + " -> "+ n.data+ "\n";
	    	}
			return s;
    	}
	}
    
    @Override
	public int compareTo(Node other) {
			return (this.position - other.position);
	}
	
	/*
	
	public boolean equals(Object that) {
		if (this ==that) return true;
		if (!(that instanceof BitVector)) return false;
		
		//return false; // pour methode rapprochement
		
		
		
		//methode a tester (rique?)
		BitVector that2 = (BitVector)that;
		that2.
		
		tab2 = that2.getTab();
		//if (tab[0] != tab2[0]) return false;//je doute de l'effficacite de ceci
		//if (tab[1] != tab2[1]) return false;
		for (int i=0; i<size; i++){
			if (tab[i] != tab2[i]) return false;
		}
		return true;
		
	}*/
	
	@Override
	public int hashCode() { //presentement useless
		int valeur = position;
		return valeur;
	}
	
}