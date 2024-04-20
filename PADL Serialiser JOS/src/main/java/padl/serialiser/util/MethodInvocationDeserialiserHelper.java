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
package padl.serialiser.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.ISetter;
import padl.util.adapter.WalkerAdapter;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

public class MethodInvocationDeserialiserHelper extends WalkerAdapter {
	private final StringBuffer completeEntityFieldName;
	private IFirstClassEntity enclosingEntity;
	private IOperation enclosingMethod;
	private IAbstractModel enclosingModel;
	private final Map mapOfEntityFieldNamesIDs;
	private String[] transientFieldNames;

	public MethodInvocationDeserialiserHelper(final String aFileName) {
		this.mapOfEntityFieldNamesIDs = new HashMap();
		try {
			final FileReader reader =
				ProxyDisk
					.getInstance()
					.fileAbsoluteInput(
						aFileName
								+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
			final LineNumberReader fileReader = new LineNumberReader(reader);

			String line = fileReader.readLine();
			if (line != null) {
				final List listOfTransientFieldNames = new ArrayList();
				final StringTokenizer tokenizer =
					new StringTokenizer(line, ";");
				while (tokenizer.hasMoreTokens()) {
					listOfTransientFieldNames.add(tokenizer.nextToken());
				}
				this.transientFieldNames =
					new String[listOfTransientFieldNames.size()];
				listOfTransientFieldNames.toArray(this.transientFieldNames);

				while ((line = fileReader.readLine()) != null) {
					final int indexOfEqual = line.indexOf('=');
					final String entityFieldNames =
						line.substring(0, indexOfEqual);

					this.mapOfEntityFieldNamesIDs.put(
						entityFieldNames,
						line.substring(indexOfEqual + 1));
				}
			}
			else {
				// Yann 2010/04/21: Transient fields!
				// No transient fields were identified in the
				// class MethodInvocation, so the line is null...
				this.transientFieldNames = new String[0];
			}

			fileReader.close();
			reader.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		this.completeEntityFieldName = new StringBuffer();
	}
	public void close(final IAbstractModel anAbstractModel) {
		this.enclosingModel = anAbstractModel;
	}
	public void open(final IAbstractModel anAbstractModel) {
		this.enclosingModel = anAbstractModel;
	}
	private void open(final IOperation anAbstractMethod) {
		this.enclosingMethod = anAbstractMethod;
	}
	public void open(final IClass aClass) {
		this.open((IFirstClassEntity) aClass);
	}
	public void open(final IConstructor aConstructor) {
		this.open((IOperation) aConstructor);
	}
	private void open(final IFirstClassEntity anEntity) {
		this.enclosingEntity = anEntity;
	}
	public void open(final IGetter aGetter) {
		this.open((IOperation) aGetter);
	}
	public void open(final IGhost aGhost) {
		this.open((IFirstClassEntity) aGhost);
	}
	public void open(final IInterface anInterface) {
		this.open((IFirstClassEntity) anInterface);
	}
	public void open(final IMemberClass aMemberClass) {
		this.open((IFirstClassEntity) aMemberClass);
	}
	public void open(final IMemberGhost aMemberGhost) {
		this.open((IFirstClassEntity) aMemberGhost);
	}
	public void open(final IMemberInterface aMemberInterface) {
		this.open((IFirstClassEntity) aMemberInterface);
	}
	public void open(final IMethod aMethod) {
		this.open((IOperation) aMethod);
	}
	public void open(final ISetter aSetter) {
		this.open((IOperation) aSetter);
	}
	public void visit(final IMethodInvocation aMethodInvocation) {
		for (int i = 0; i < this.transientFieldNames.length; i++) {
			final String entityFieldName = this.transientFieldNames[i];

			this.completeEntityFieldName.append(this.enclosingEntity.getID());
			this.completeEntityFieldName.append('.');
			this.completeEntityFieldName.append(this.enclosingMethod.getID());
			this.completeEntityFieldName.append('.');
			this.completeEntityFieldName.append(aMethodInvocation.getID());
			this.completeEntityFieldName.append('.');
			this.completeEntityFieldName.append(entityFieldName);

			final String entityName =
				(String) this.mapOfEntityFieldNamesIDs
					.get(this.completeEntityFieldName.toString());
			final IFirstClassEntity firstClassEntity;
			if (entityFieldName.equals(TransientFieldManager.NULL_VALUE)) {
				firstClassEntity = null;
			}
			else {
				firstClassEntity =
					this.enclosingModel.getTopLevelEntityFromID(entityName);
			}

			try {
				final Class methodInvocationClass =
					aMethodInvocation.getClass();
				final Field entityField =
					methodInvocationClass.getDeclaredField(entityFieldName);
				entityField.setAccessible(true);
				entityField.set(aMethodInvocation, firstClassEntity);
			}
			catch (final SecurityException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalArgumentException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final NoSuchFieldException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			this.completeEntityFieldName.setLength(0);
		}
	}
}
