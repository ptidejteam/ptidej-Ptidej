package glass.lattice.model;

import java.util.Set;

public interface IRelation {
	/**
	 * add anObject to the domain of the relation. If the object was already in, do nothing.
	 * Else, add it with no images
	 * @param anObject
	 */
	public void addToDomain(Object anObject);
	
	/**
	 * remove anObject from the domain of the relation. If it is not part of the domain, it does
	 * nothing. Else, it removes-- along with its image
	 * @param anObject
	 */
	public void removeFromDomain(Object anObject);
	
	
	/**
	 * adds a pair to the relation.	
	 * @param key
	 * @param value
	 */
	public void addRelation(Object key, Object value);
	
	/**
	 * removes the pair <key,value> from the relation/self
	 * @param key
	 * @param value
	 */
	public void removeRelation(Object key, Object value);
	
	/**
	 * returns the domain of the relation
	 * @return
	 */
	public Set<Object> getDomain();
	
	/**
	 * returns the image of a given element belonging to the domain.
	 * If the argument is not part of the domain, it returns null.
	 * If it is part of the domain, but with no images, it returns an empty set.
	 * @param domainElement
	 * @return
	 */
	public Set<Object> getImage(Object domainElement);
	
	/**
	 * checks whether the domain of a relation contains an object 
	 * @param anObject
	 * @return
	 */
	public boolean domainContains(Object anObject);
	
	/**
	 * checks whether relation (self) contains the pair <key,value>
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean containsRelation(Object key,Object value);
	
	/**
	 * returns the set of all images
	 * @return
	 */
	public Set<Object> getAllImages();
	
	/**
	 * Generates the print string. Wanted to use a different name from toString() because this could be very heavy
	 * and wouldn't want to lose toString() which can be used in debugging
	 *  
	 * @return
	 */
	public String printString();
}
