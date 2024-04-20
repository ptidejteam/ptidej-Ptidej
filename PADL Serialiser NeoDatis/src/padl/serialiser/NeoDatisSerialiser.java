/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package padl.serialiser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;
import padl.kernel.IAbstractModel;
import padl.kernel.IAbstractModelSerialiser;
import padl.serialiser.util.AbstractModelWrapper;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * 
 * @author Yann
 * @since  2009/02/23
 */

public class NeoDatisSerialiser implements IAbstractModelSerialiser {
	private static final String DATE_FORMAT_NOW = "yyyyMMdd'H'HHmmss";
	private static IAbstractModelSerialiser UniqueInstance;
	public static IAbstractModelSerialiser getInstance() {
		if (NeoDatisSerialiser.UniqueInstance == null) {
			NeoDatisSerialiser.UniqueInstance = new NeoDatisSerialiser();
		}
		return NeoDatisSerialiser.UniqueInstance;
	}
	private NeoDatisSerialiser() {
	}
	public IAbstractModel deserialise(final String aSerialisedPADLModelFileName) {
		ODB odb = null;
		IAbstractModel abstractModel = null;
		try {
			odb = ODBFactory.open(aSerialisedPADLModelFileName);
			//	abstractModel =
			//		((AbstractModelWrapper) odb.getObjects(
			//			AbstractModelWrapper.class).getFirst()).getAbstractModel();
			final IQuery query = new CriteriaQuery(AbstractModelWrapper.class);
			abstractModel =
				((AbstractModelWrapper) odb.getObjects(query))
					.getAbstractModel();
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
	private String getODBName(
		final IAbstractModel anAbstractModel,
		final String aTargetPath) {

		final StringBuffer buffer = new StringBuffer();
		buffer.append(aTargetPath);
		buffer.append(anAbstractModel.getName());
		buffer.append('-');

		final Calendar calendar = Calendar.getInstance();
		final SimpleDateFormat sdf =
			new SimpleDateFormat(NeoDatisSerialiser.DATE_FORMAT_NOW);
		buffer.append(sdf.format(calendar.getTime()));

		return buffer.toString();
	}
	public String serialiseWithAutomaticNaming(
		final IAbstractModel anAbstractModel) {

		final String fileName =
			this.getODBName(anAbstractModel, ProxyDisk
				.getInstance()
				.directoryTempString());
		this.serialise(anAbstractModel, fileName);
		return fileName;
	}
	public void serialise(
		final IAbstractModel anAbstractModel,
		final String aFileName) {

		ODB odb = null;
		try {
			odb = ODBFactory.open(aFileName);
			odb.store(new AbstractModelWrapper(anAbstractModel));
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		finally {
			if (odb != null) {
				odb.close();
			}
		}
	}
	public String serialiseWithAutomaticNaming(
		final IAbstractModel anAbstractModel,
		final String aTargetPath) {

		final String fileName = this.getODBName(anAbstractModel, aTargetPath);
		this.serialise(anAbstractModel, fileName);
		return fileName;
	}
}
