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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

/**
 * @author Simon Denier
 * @since Mar 14, 2008
 *
 */
public class Util {
	
	/*
	 * General purpose properties loading and instance creation
	 */

	public static Properties loadPropertiesFile(String propertiesFile) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public static String[] extractValues(Properties prop, String key) {
		String values = prop.getProperty(key);
		if( values!=null ) {
			return values.split(",");
		} else
			return new String[0];
	}

	/**
	 * @param className
	 * @return
	 */
	public static Object createInstance(String className) {
		try {
			return Class.forName(className).getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Classfiles extraction from files/paths
	 */
	public static File[] extractClassFiles(String[] paths) {
		Set classfiles = new HashSet();
		for (int i = 0; i < paths.length; i++) {
			File file = new File(paths[i]);
			try {
				file = file.getCanonicalFile();
				if( !file.exists() ) {
					System.err.println("Warning! " + file.getCanonicalPath() + " does not exist. Check your parameters.");
				} else {
					addClassFiles(file, classfiles);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (File[]) classfiles.toArray(new File[0]);
		
	}

	/**
	 * @param classfiles
	 * @param string
	 */
	public static void addClassFiles(File file, Set classfiles) {
		if( isClassFile(file) ) {
			classfiles.add(file);
		}
		if( file.isDirectory() ) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				addClassFiles(files[i], classfiles);
			}	
		}		
	}

	/**
	 * @param file
	 * @return
	 */
	public static boolean isClassFile(File file) {
		return file.getName().endsWith(".class");
	}


	public static String extractBasename(File file) {
		String name = file.getName();
		return name.substring(0, name.lastIndexOf('.'));
//		return ( name.endsWith(".class") )? classname.substring(0, classname.length()-6):classname;
	}
	
	public static String escape(String name) {
		return name.replace(' ', '_');
	}
	
	public static String join(Collection strings, String token, StringBuffer buf) {
		for (Iterator it = strings.iterator(); it.hasNext();) {
			buf.append(it.next()).append(token);
		}
		buf.delete(buf.lastIndexOf(token), buf.length()); // delete last token
		return buf.toString();
	}

	public static Number[] convertToNumbers(String[] data) {
		Number[] numbers = new Number[data.length];
		for (int i = 0; i < data.length; i++) {
			numbers[i] = Double.valueOf(data[i]);
		}
		return numbers;
	}

	public static Collection selectDataByKey(Collection objects, Object key) {
		Vector data = new Vector();
		for (Iterator it = objects.iterator(); it.hasNext();) {
			Map record = (Map) it.next();
			data.add(record.get(key));
		}
		return data;
	}

}
