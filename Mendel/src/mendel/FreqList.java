/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package mendel;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * A list where items can be put together with a number (a frequency, a cardinality). 
 * Consequently, items can be retrieved by order of number.
 * 
 * TODO: use in MemoBag?
 * 
 * @author deniersi
 *
 */
public class FreqList {
	
	private Vector<Item> list;
	
	private boolean sorted; 

	public FreqList() {
		this.list = new Vector<Item>();
	}
	
	public void add(Object o, int number) {
		this.sorted = false;
		this.list.add(new Item(o, number));
	}
	
	public void add(Object o) {
		add(o, 1);
	}
	
	public List<Item> getSortedList() {
		if( !this.sorted ) { // small optimization - sort only if modifications
			Collections.sort(this.list, new Comparator<Item>() {
				public int compare(Item o1, Item o2) {
					// Natural ordering = descending order (higher number first) so we inverse the order here
					if( o1.getNumber() > o2.getNumber() )
						return -1;
					if( o1.getNumber() == o2.getNumber() )
						return 0;
					return 1;
				}
			});
			this.sorted = true;
		}
		return this.list;
	}
	
	public Iterator iterator() {
		return getSortedList().iterator();
	}
	
	public List values() {
		Vector v = new Vector();
		for (Item i : getSortedList()) {
			v.add(i.getObject());
		}
		return v;
	}
	
	public List numbers() {
		Vector v = new Vector();
		for (Item i : getSortedList()) {
			v.add(i.getNumber());
		}
		return v;		
	}
	

	
	public class Item {
		
		private Object object;
		
		private int number;
		
		public Item(Object object, int number) {
			this.object = object;
			this.number = number;
		}

		public Object getObject() {
			return this.object;
		}

		public int getNumber() {
			return this.number;
		}
		
	}



	/**
	 * @param threshold
	 * @return
	 */
	public List collectFrequentValues(double threshold, double total) {
		Vector v = new Vector();
		List<Item> list2 = getSortedList();
		for (Item item : list2) {
			double freq = item.getNumber() / total;
			if( freq > threshold ) {
				v.add(item.getObject());
			}
		}
		return v;
	}
	
}
