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
package padl.serialiser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

import padl.kernel.IAbstractModel;
import padl.kernel.IAbstractModelSerialiser;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.lang.OpenedModulesGuard;

/**
 * 
 * @author Yann
 * @since  2009/02/23
 */
public class DB4OSerialiser implements IAbstractModelSerialiser {
	private static final String DATE_FORMAT_NOW = "yyyyMMdd'H'HHmmss";
	//	private static final EmbeddedConfiguration configuration =
	//		Db4oEmbedded.newConfiguration();
	//	{
	//	 DB4OSerialiser.configuration.blockSize(100);
	//	 DB4OSerialiser.configuration.bTreeNodeSize(100);
	//	 DB4OSerialiser.configuration.callbacks(false);
	//	 DB4OSerialiser.configuration.callConstructors(false);
	//	 DB4OSerialiser.configuration.freespace().useBTreeSystem();
	//	 DB4OSerialiser.configuration.generateUUIDs(ConfigScope.DISABLED);
	//	 DB4OSerialiser.configuration.generateVersionNumbers(ConfigScope.DISABLED);
	//	 DB4OSerialiser.configuration.activationDepth(10000);
	//	 DB4OSerialiser.configuration.objectClass(IAbstractModel.class).indexed(false);
	//	 DB4OSerialiser.configuration.objectClass(IAbstractModel.class).minimumActivationDepth(2);
	//	DB4OSerialiser.configuration
	//		.common()
	//		.markTransient(
	//			"padl.kernel.impl.GenericContainerOfConstituents.containerConsitituent");
	//	}
	private static IAbstractModelSerialiser UniqueInstance;

	public static IAbstractModelSerialiser getInstance() {
		if (DB4OSerialiser.UniqueInstance == null) {
			DB4OSerialiser.UniqueInstance = new DB4OSerialiser();
		}
		return DB4OSerialiser.UniqueInstance;
	}

	private DB4OSerialiser() {
		OpenedModulesGuard.getInstance().addOpenedModuleCheck("java.base",
				"java.util.HashSet");

		final Optional<String> check = OpenedModulesGuard.getInstance()
				.checkOpenedModules();
		if (check.isPresent()) {
			throw new RuntimeException(check.get());
		}
	}

	public IAbstractModel deserialise(
			final String aSerialisedPADLModelFileName) {
		ObjectContainer odb = null;
		IAbstractModel abstractModel = null;
		try {
			odb = Db4oEmbedded.openFile(
					// DB4OSerialiser.configuration,
					aSerialisedPADLModelFileName);
			final Predicate predictate = new Predicate() {
				private static final long serialVersionUID = 8696338824535129529L;

				@SuppressWarnings("unused")
				public boolean match(final Object o) {
					return true;
				}
			};
			ObjectSet objectSet = odb.query(predictate);
			abstractModel = (IAbstractModel) objectSet.next();
			odb.activate(abstractModel, 10000);
			objectSet = odb.query(predictate);
			abstractModel = (IAbstractModel) objectSet.next();
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		finally {
			if (odb != null) {
				odb.close();
			}
		}
		return abstractModel;
	}

	private String getODBName(final IAbstractModel anAbstractModel,
			final String aTargetPath) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aTargetPath);
		buffer.append(anAbstractModel.getName());
		buffer.append('-');

		final Calendar calendar = Calendar.getInstance();
		final SimpleDateFormat sdf = new SimpleDateFormat(
				DB4OSerialiser.DATE_FORMAT_NOW);
		buffer.append(sdf.format(calendar.getTime()));
		buffer.append(".ptidejdbo");

		return buffer.toString();
	}

	public String serialiseWithAutomaticNaming(
			final IAbstractModel anAbstractModel) {

		final String fileName = this.getODBName(anAbstractModel,
				ProxyDisk.getInstance().directoryTempString());
		this.serialise(anAbstractModel, fileName);
		return fileName;
	}

	public void serialise(final IAbstractModel anAbstractModel,
			final String aFileName) {

		ObjectContainer odb = null;
		try {
			odb = Db4oEmbedded.openFile(aFileName);
			odb.store(anAbstractModel);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		finally {
			if (odb != null) {
				odb.commit();
				odb.close();
			}
		}
	}

	public String serialiseWithAutomaticNaming(
			final IAbstractModel anAbstractModel, final String aTargetPath) {

		final String fileName = this.getODBName(anAbstractModel, aTargetPath);
		this.serialise(anAbstractModel, fileName);
		return fileName;
	}
}
