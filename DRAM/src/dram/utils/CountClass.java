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
package dram.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Samah
 */
public class CountClass {
	private static FileReader rd;

	private static BufferedReader buf;

	public static void main(final String[] args) {
		try {
			HashMap hashMapClass = new HashMap();
			//HashMap hashMapMethod = new HashMap();
			//Hashtable hashMapClass = new Hashtable();
			//Hashtable hashMapMethod = new Hashtable();
			String chaine = null;
			String class1 = null;
			String class2 = null;
			String method = null;

			rd =
				new FileReader(
					"C:\\Documents and Settings\\rachedsa\\Bureau\\RJhotDrawRectCercle.txt");
			buf = new BufferedReader(rd);

			while ((chaine = buf.readLine()) != null) {
				HashMap hashMapMethodClass1 = new HashMap();
				HashMap hashMapMethodClass2 = new HashMap();
				//hashMapMethod.clear();
				StringTokenizer st = new StringTokenizer(chaine, "|");
				st.nextToken();
				class1 = st.nextToken();
				class2 = st.nextToken();
				method = st.nextToken();
				int nbre;

				if (!hashMapClass.containsKey(class1)) {
					hashMapClass.put(class1, hashMapMethodClass1);
				}
				if (!hashMapClass.containsKey(class2)) {
					hashMapMethodClass2.put(method, Integer.toString(1));
					hashMapClass.put(class2, hashMapMethodClass2);
				}
				else {
					//hashMapMethod = (Hashtable) hashMapClass.get(class2);
					hashMapMethodClass2 = (HashMap) hashMapClass.get(class2);
					if (hashMapMethodClass2.isEmpty()) {
						hashMapMethodClass2.put(method, Integer.toString(1));
						hashMapClass.put(class2, hashMapMethodClass2);
					}
					else {
						if (hashMapMethodClass2.containsKey(method)) {
							String n = (String) hashMapMethodClass2.get(method);
							nbre = Integer.parseInt(n) + 1;
							hashMapMethodClass2.put(
								method,
								Integer.toString(nbre));
							hashMapClass.put(class2, hashMapMethodClass2);
						}
						else {
							hashMapMethodClass2
								.put(method, Integer.toString(1));
							hashMapClass.put(class2, hashMapMethodClass2);
						}
					}
				}
			}

			printHashtable(hashMapClass);
		}
		catch (Exception ex) {
			System.err.println("Syntax error line ");
			ex.printStackTrace();

		}
	}
	public static void printHashtable(HashMap Hashmap) {
		Set set = Hashmap.keySet();
		Iterator iterator = set.iterator();

		//Enumeration valeurs = hashtable.elements();
		//Enumeration cles = hashtable.keys();
		// Hashtable hh = new Hashtable();

		System.out.println("cles de la hashtable : ");
		//        while ( cles.hasMoreElements()) {
		//            System.out.println(cles.nextElement());
		////            System.out.println(valeurs.nextElement());

		// }

		//        while (valeurs.hasMoreElements() && cles.hasMoreElements()) {
		//            System.out.println(cles.nextElement());
		//            System.out.println(valeurs.nextElement());
		//
		//        }
		System.out.println("valeurs de la hashtable : ");
		//  System.out.println(Hashmap.toString());
		System.out.println("fin de la hashtable : ");
		while (iterator.hasNext()) {
			Object o = iterator.next();
			System.out.println(o);
			System.out.println(Hashmap.get(o));
			//System.out.println(cles.nextElement());
		}
		System.out.println("Nombre de classes : " + Hashmap.size());
	}

}
