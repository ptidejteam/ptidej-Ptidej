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

import java.util.Map;

public class MapToArray {
	
	private String[] keys;
	
	public MapToArray() {
	}
	
	public MapToArray(String[] keys) {
		this.keys = keys;
	}
	
	public MapToArray(Map data) {
		initialize(data);
	}

	public void initialize(Map map) {
		this.keys = keysFor(map);
	}
	
	public String[] keys() {
		return this.keys;
	}
	
	public Object[] record(Map map) {
		return recordFor(map, keys());
	}
	
	public static String[] keysFor(Map map) {
		return (String[]) map.keySet().toArray(new String[0]);
	}
	
	public static Object[] recordFor(Map map, String[] keys) {
		Object[] record = new Object[keys.length];
		for (int i = 0; i < record.length; i++) {
			record[i] = map.get(keys[i]);
		}
		return record;
	}
	
	

	
}
