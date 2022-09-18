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
package ptidej.solver.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Utils {

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static List readIntoList(final String path, final boolean hasTitle) {
		final List list = new ArrayList();
		try {
			final BufferedReader bufferedReader =
				new BufferedReader(new FileReader(path));
			String line;
			try {
				// read title? 
				if (hasTitle) {
					line = bufferedReader.readLine();
				}

				// read first line of data
				line = bufferedReader.readLine();

				while (line != null) {
					list.add(line);
					line = bufferedReader.readLine();

				}
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					bufferedReader.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 
	 * @param list
	 * @param title
	 * @param aResultFilePath
	 */
	//	public static void writeListInCSV(
	//		List list,
	//		String title,
	//		String aResultFilePath) {
	//
	//		PrintStream writer;
	//		try {
	//			writer = new PrintStream(new File(aResultFilePath));
	//			writer.println(title);
	//			for (int i = 0; i < list.size(); i++) {
	//				writer.println(list.get(i));
	//			}
	//			writer.close();
	//		}
	//		catch (FileNotFoundException e) {
	//			e.printStackTrace();
	//		}
	//	}

	/**
	 * 
	 * @param dataPath
	 * @return
	 */
	//	public static Map readIntoMap(String dataPath) {
	//		Map map = new HashMap();
	//
	//		try {
	//			BufferedReader bufferedReader =
	//				new BufferedReader(new FileReader(dataPath));
	//			String line;
	//			try {
	//				// read title
	//				line = bufferedReader.readLine();
	//				// read first line of data
	//				line = bufferedReader.readLine();
	//
	//				while (line != null) {
	//					String[] elts = line.split(";");
	//					String[] tab = new String[elts.length - 1];
	//					for (int i = 0; i < tab.length; i++) {
	//						tab[i] = elts[i + 1];
	//					}
	//					map.put(elts[0], tab);
	//					line = bufferedReader.readLine();
	//
	//				}
	//			}
	//			catch (IOException e) {
	//
	//				e.printStackTrace();
	//			}
	//		}
	//		catch (FileNotFoundException e) {
	//			e.printStackTrace();
	//		}
	//
	//		return map;
	//	}

	public static void writeMapInCSV(
		final Map map,
		final String path,
		final String title) {
		PrintStream writer;
		try {
			writer = new PrintStream(new FileOutputStream(new File(path)));
			writer.println(title);
			final Iterator iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				final Entry entry = (Entry) iterator.next();
				final StringBuffer buffer = new StringBuffer();
				buffer.append((String) entry.getKey());
				final String tab[] = (String[]) entry.getValue();
				for (int k = 0; k < tab.length; k++) {
					final String s = tab[k];
					buffer.append(";");
					buffer.append(s);
				}
				writer.println(buffer.toString());
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
