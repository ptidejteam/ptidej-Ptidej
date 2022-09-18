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
package padl.creator.javafile.eclipse.test.others;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import util.io.AutoFlushPrintWriter;
import util.io.ProxyDisk;

public class HibernateORMv364Final extends TestCase {
	public HibernateORMv364Final(final String aName) {
		super(aName);
	}
	public void testHibernate() {
		//	final String sourcePath =
		//		"C:/Data/Java Programs/hibernate-orm-3.6.4.Final/";
		final String sourcePath =
			"C:/Data/Java Programs/hibernate-orm-3.6.1.Final/";
		final String classPathEntry = "";

		try {
			final Writer writer =
				new AutoFlushPrintWriter(ProxyDisk
					.getInstance()
					.fileTempOutput("Result.txt"));

			final ICodeLevelModel model =
				Utils.createCompleteJavaFilesPadlModel(
					"",
					sourcePath,
					classPathEntry);

			writer.write("Summary for: ");
			writer.write(model.getDisplayName());

			//Print the model by the generator

			writer.write("\nNumber of top-level entities: ");
			writer.write(Integer.toString(model.getNumberOfTopLevelEntities()));
			final Iterator iter = model.getIteratorOnTopLevelEntities();
			while (iter.hasNext()) {
				final IFirstClassEntity entity =
					(IFirstClassEntity) iter.next();
				writer.write('\n');
				writer.write(entity.getDisplayID());
				writer.write(" ");
				writer
					.write(Integer.toString(entity.getNumberOfConstituents()));
				writer.write(" ");
				writer.write(entity.getClass().getName());
			}
			writer.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		Assert.assertTrue(true);
	}
}
