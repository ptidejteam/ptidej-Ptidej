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
package ptidej.solver.java.domain;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.util.Util;
import padl.visitor.IWalker;
import util.io.ProxyConsole;
import util.io.ReaderInputStream;
import util.io.WriterOutputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/05/16
 */
public class Manager {
	private static final String NAME = "name";
	private static final String IS_ABSTRACT = "isAbstract";
	private static final String IS_GHOST = "isGhost";
	private static final String IS_INTERFACE = "isInterface";
	private static final String NUMBER_OF_ENTITIES = "NumberOfEntities";

	public static List build(final IAbstractModel abstractModel) {
		final IWalker generator = new Generator();
		return Manager.build(abstractModel, generator);
	}

	public static List build(final IAbstractModel abstractModel,
			final IWalker aGenerator) {
		abstractModel.walk(aGenerator);
		final List listOfEntities = (List) aGenerator.getResult();
		return listOfEntities;
	}

	private static Entity findEntity(final List listOfEntities,
			final String anEntityName) {

		final Iterator iterator = listOfEntities.iterator();
		while (iterator.hasNext()) {
			final Entity entity = (Entity) iterator.next();
			if (entity.getName().equals(anEntityName)) {
				return entity;
			}
		}
		return null;
	}

	public static List load(final Reader reader) {
		final Properties properties = new Properties();
		try {
			properties.load(new ReaderInputStream(reader));
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		final int numberOfEntities = Integer
				.parseInt(properties.getProperty(Manager.NUMBER_OF_ENTITIES));
		final List listOfEntities = new ArrayList(numberOfEntities);
		final StringBuffer buffer = new StringBuffer();

		// First, I create empty shells for the entities.
		for (int i = 0; i < numberOfEntities; i++) {
			buffer.setLength(0);
			buffer.append(i);
			buffer.append('.');
			buffer.append(Manager.NAME);
			final String name = buffer.toString();

			buffer.setLength(0);
			buffer.append(i);
			buffer.append('.');
			buffer.append(Manager.IS_ABSTRACT);
			final String isAbstract = buffer.toString();

			buffer.setLength(0);
			buffer.append(i);
			buffer.append('.');
			buffer.append(Manager.IS_GHOST);
			final String isGhost = buffer.toString();

			buffer.setLength(0);
			buffer.append(i);
			buffer.append('.');
			buffer.append(Manager.IS_INTERFACE);
			final String isInterface = buffer.toString();

			listOfEntities.add(new Entity(properties.getProperty(name),
					Boolean.getBoolean(isAbstract),
					Boolean.getBoolean(isInterface),
					Boolean.getBoolean(isGhost)));
		}

		// Then, I fill up the blanks.
		for (int i = 0; i < numberOfEntities; i++) {
			final Field[] fields = Entity.class.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				final String fieldName = fields[j].getName();
				final String fieldType = fields[j].getType().getName();

				if (!fieldName.equals(Manager.NAME)
						&& !fieldName.equals("methodNames")
						&& fieldType.endsWith("Set")) {

					buffer.setLength(0);
					buffer.append(i);
					buffer.append('.');
					buffer.append(fieldName);
					final String list = properties
							.getProperty(buffer.toString());
					final StringTokenizer tokenizer = new StringTokenizer(list,
							"[,] ");

					try {
						buffer.setLength(0);
						buffer.append("add");
						if (fieldName.startsWith("allReachable")) {
							// Yann 2005/10/13: Conversion...
							// I convert from a fieldName, i.e.,
							// allReachableAggregatedEntities
							// in the corresponding method name, i.e.,
							// addAggregatedEntity
							buffer.append(fieldName.substring(12));
						}
						else {
							// Yann 2005/10/13: Conversion...
							// I convert from a fieldName, i.e.,
							// aggregatedEntities
							// in the corresponding method name, i.e.,
							// addAggregatedEntity
							buffer.append(Util.capitalizeFirstLetter(
									fieldName.toCharArray()));
						}
						buffer.replace(buffer.length() - 3, buffer.length(),
								"y");
						final Method setter = Entity.class.getMethod(
								buffer.toString(),
								new Class[] { Entity.class });
						while (tokenizer.hasMoreTokens()) {
							final String token = tokenizer.nextToken();
							setter.invoke(listOfEntities.get(i),
									new Object[] { Manager.findEntity(
											listOfEntities, token) });
						}
					}
					catch (final SecurityException se) {
						se.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
					}
					catch (final IllegalArgumentException iae) {
						iae.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
					}
					catch (final NoSuchMethodException nsme) {
						nsme.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
					}
					catch (final IllegalAccessException iae) {
						iae.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
					}
					catch (final InvocationTargetException ite) {
						ite.printStackTrace(
								ProxyConsole.getInstance().errorOutput());
					}
				}
			}
		}

		return listOfEntities;
	}

	public static void save(final IAbstractLevelModel abstractLevelModel,
			final Writer writer) {

		final List listOfEntities = Manager.build(abstractLevelModel);
		final int numberOfEntities = listOfEntities.size();
		final Properties properties = new Properties();
		properties.put(Manager.NUMBER_OF_ENTITIES,
				Integer.toString(numberOfEntities));

		final StringBuffer key = new StringBuffer();
		final StringBuffer name = new StringBuffer();
		final StringBuffer value = new StringBuffer();
		for (int i = 0; i < numberOfEntities; i++) {
			final Field[] fields = Entity.class.getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				final String fieldName = fields[j].getName();

				key.setLength(0);
				key.append(i);
				key.append('.');
				key.append(fieldName);

				Method getter = null;
				try {
					name.setLength(0);
					name.append("get");
					name.append(Util
							.capitalizeFirstLetter(fieldName.toCharArray()));

					getter = Entity.class.getMethod(name.toString(),
							new Class[0]);

				}
				catch (final SecurityException e) {
				}
				catch (final NoSuchMethodException e) {
				}
				try {
					// Yann 2007/08/31: isAbstract & Co.
					// I now manage the case of the boolean fields.
					name.setLength(0);
					name.append(fieldName);

					getter = Entity.class.getMethod(name.toString(),
							new Class[0]);

				}
				catch (final SecurityException e) {
				}
				catch (final NoSuchMethodException e) {
				}

				try {
					value.setLength(0);
					value.append(
							getter.invoke(listOfEntities.get(i), new Object[0])
									.toString());
				}
				catch (final IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
				catch (final InvocationTargetException e) {
					e.printStackTrace();
				}

				properties.put(key.toString(), value.toString());
			}
		}

		try {
			properties.store(new WriterOutputStream(writer),
					"JPtidejSolver domain model");
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
