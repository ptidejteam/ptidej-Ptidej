package glass.lattice.model.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import glass.lattice.model.IRelation;


public class Relation implements IRelation{


	/**
	 * relation structure is hashmap where the key is the object, and the value
	 * is the set of images
	 */
	private HashMap<Object, Set<Object>> relationStore;

	public Relation() {
		relationStore = new HashMap<Object, Set<Object>>();
	}

	@Override
	public void addToDomain(Object anObject) {
		// check if domain value exists. if it does, do nothing. Else
		// make an entry <anObject,empty set>
		if (!relationStore.containsKey(anObject)) {
			relationStore.put(anObject, new HashSet<Object>());
		}

	}

	@Override
	public void removeFromDomain(Object anObject) {
		if (relationStore.containsKey(anObject)) {
			relationStore.remove(anObject);
		}

	}

	@Override
	public void addRelation(Object key, Object value) {
		Set<Object> image = null;
		if (!domainContains(key)) {
			image = new HashSet<Object>();
			relationStore.put(key, image);
		} else {
			image = relationStore.get(key);
		}
		image.add(value);
	}

	@Override
	public void removeRelation(Object key, Object value) {
		if (containsRelation(key, value)) {
			// don't use getImage(...) which returns a copy of the
			// actual image set
			Set<Object> image = relationStore.get(key);
			image.remove(value);
		}

	}

	@Override
	public Set<Object> getDomain() {
		return relationStore.keySet();
	}

	@Override
	public Set<Object> getImage(Object domainElement) {
		Set<Object> image = new HashSet<Object>();
		image.addAll(relationStore.get(domainElement));
		return image;
	}

	@Override
	public boolean domainContains(Object anObject) {
		return relationStore.containsKey(anObject);
	}

	@Override
	public boolean containsRelation(Object key, Object value) {
		if (domainContains(key)) {
			Set<Object> image = relationStore.get(key);
			return image.contains(value);
		}
		// else, return false
		return false;
	}

	@Override
	/**
	 * here we code it in the general case by doing the union of all images. In
	 * special cases, we use custom code that optimizes
	 */
	public Set<Object> getAllImages() {
		Set<Object> allImages = new HashSet<Object>();
		for (Object domainElement : getDomain()) {
			allImages.addAll(getImage(domainElement));
		}
		return allImages;
	}

	@Override
	public String printString() {
		StringBuffer buffer = new StringBuffer();
		// print the images by sorted key values
		HashMap<String, Object> keyMap = new HashMap<String, Object>();
		SortedSet<String> sortedKeys = new TreeSet<String>();
		for (Object key : getDomain()) {
			String keyString = printDomainObject(key);
			sortedKeys.add(keyString);
			keyMap.put(keyString, key);
		}

		//
		for (String keyString : sortedKeys) {
			Object key = keyMap.get(keyString);
			// print the image of key
			// -> first the key
			buffer.append(keyString + " =====> [");
			// then the image of the current element
			Set<Object> image = getImage(key);
			for (Object obj : image) {
				buffer.append(" " + printImageObject(obj) + ",");
			}
			// remove the last character which is the extraneous ","
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append(" ]\n");
		}
		
		return buffer.toString();
	}

	/**
	 * prints image objects. Subclasses can override this
	 * @param obj
	 * @return
	 */
	protected String printImageObject(Object obj) {
		return obj.toString();
	}

	/**
	 * prints domain objects. Subclasses can override this
	 * @param key
	 * @return
	 */
	protected String printDomainObject(Object key) {
		return key.toString();
	}
}
