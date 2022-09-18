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

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
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

public class MethodInvocationSerialiserHelper extends WalkerAdapter {
	private IFirstClassEntity enclosingEntity;
	private IOperation enclosingMethod;
	private Writer methodInvocationWriter;
	private Field[] transientFields;

	public MethodInvocationSerialiserHelper(final String aFileName) {
		this.methodInvocationWriter =
			ProxyDisk.getInstance().fileAbsoluteOutput(
				aFileName + TransientFieldManager.METHOD_INVOCATION_EXTENSION);
	}

	public void close(final IAbstractModel anAbstractModel) {
		try {
			this.methodInvocationWriter.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
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
		try {
			if (this.transientFields == null) {
				this.transientFields =
					TransientFieldManager
						.getTransientEntityFields(aMethodInvocation.getClass());

				for (int i = 0; i < this.transientFields.length; i++) {
					final Field transientField = this.transientFields[i];
					this.methodInvocationWriter.write(transientField.getName());
					if (i < this.transientFields.length - 1) {
						this.methodInvocationWriter.write(';');
					}
					else {
						this.methodInvocationWriter.write('\n');
					}
				}
			}

			final Map mapOfEntityFieldNamesEntityIDs =
				TransientFieldManager.getTransientEntityFieldNamesEntityIDs(
					this.transientFields,
					aMethodInvocation);
			final Iterator iteratorOnEntities =
				mapOfEntityFieldNamesEntityIDs.keySet().iterator();
			while (iteratorOnEntities.hasNext()) {
				final String entityFieldName =
					(String) iteratorOnEntities.next();
				final String entityID =
					(String) mapOfEntityFieldNamesEntityIDs
						.get(entityFieldName);

				this.methodInvocationWriter.write(this.enclosingEntity.getID());
				this.methodInvocationWriter.write('.');
				this.methodInvocationWriter.write(this.enclosingMethod.getID());
				this.methodInvocationWriter.write('.');
				this.methodInvocationWriter.write(aMethodInvocation.getID());
				this.methodInvocationWriter.write('.');
				this.methodInvocationWriter.write(entityFieldName);
				this.methodInvocationWriter.write('=');
				this.methodInvocationWriter.write(entityID);
				this.methodInvocationWriter.write('\n');
			}
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
