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
package squad.quality.qmood;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import squad.quality.IQualityAttribute;
import util.io.ProxyConsole;
import util.io.SubtypeLoader;
import util.repository.FileAccessException;
import util.repository.IRepository;
import util.repository.impl.FileRepositoryFactory;
import com.ibm.toad.cfparse.ClassFile;

public class QMOODRepository implements IRepository {
	private static QMOODRepository UniqueInstance;
	public static QMOODRepository getInstance() {
		if (QMOODRepository.UniqueInstance == null) {
			QMOODRepository.UniqueInstance = new QMOODRepository();
		}
		return QMOODRepository.UniqueInstance;
	}

	private final IQualityAttribute[] qualityAttributes;
	private final Map<String, IQualityAttribute> mapOfAttributes =
		new HashMap<String, IQualityAttribute>();

	private QMOODRepository() {
		final List<IQualityAttribute> listOfQualityAttributes =
			new ArrayList<IQualityAttribute>();
		try {
			final ClassFile[] classFiles =
				SubtypeLoader.loadSubtypesFromStream(
					"squad.quality.IQualityAttribute",
					FileRepositoryFactory
						.getInstance()
						.getFileRepository(this)
						.getFiles(),
					"squad.quality.qmood.repository",
					".class");

			for (int i = 0; i < classFiles.length; i++) {
				try {
					@SuppressWarnings("unchecked")
					final Class<IQualityAttribute> attributeClass =
						(Class<IQualityAttribute>) Class.forName(classFiles[i]
							.getName());
					final IQualityAttribute qualityAttribute =
						attributeClass.getDeclaredConstructor().newInstance();

					this.mapOfAttributes.put(
						qualityAttribute.getName(),
						qualityAttribute);
					listOfQualityAttributes.add(qualityAttribute);
				}
				catch (final ClassNotFoundException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				catch (final IllegalArgumentException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				catch (final SecurityException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				catch (final InstantiationException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}
				catch (final IllegalAccessException e) {
					e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
		catch (final FileAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		this.qualityAttributes =
			new IQualityAttribute[listOfQualityAttributes.size()];
		listOfQualityAttributes.toArray(this.qualityAttributes);
	}
	public IQualityAttribute getQualityAttribute(final String anAttributeName) {
		return (IQualityAttribute) this.mapOfAttributes.get(anAttributeName);
	}
	public IQualityAttribute[] getQualityAttributes() {
		return this.qualityAttributes;
	}
}
