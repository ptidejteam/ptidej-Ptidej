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
package padl.creator.aolfile.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.creator.aolfile.AOLCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import padl.kernel.IRelationship;
import padl.kernel.impl.Factory;
import util.io.ProxyDisk;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/08/15
 */
public class GenerateCSVLists {
	public static void main(final String[] args) {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		//	final AOLCreator aolCreator =
		//		new AOLCreator(new String[] { "rsc/Mozilla/moz-1.0.rel.n.aol" });

		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/Firefox/AddBookmark_subset1.aol" });

		//	final AOLCreator aolCreator =
		//		new AOLCreator(new String[] { "rsc/Firefox/AddBookmark.aol" });

		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.0-concat_des_2006-02-15114305.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.1-concat_des_2006-02-14082728.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.2-concat_des_2006-02-13180947.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.2.1-concat_des_2006-02-16005222.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.3-concat_des_2006-02-15115441.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.4-concat_des_2006-02-14072834.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.5-concat_des_2006-02-13183653.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.5a-concat_des_2006-02-15105456.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.5b-concat_des_2006-02-14091043.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.6-concat_des_2006-02-14215550.aol" });
		//		final AOLCreator aolCreator =
		//			new AOLCreator(
		//				new String[] { "rsc/mozilla-1.6a-concat_des_2006-02-14081224.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7-concat_des_2006-02-16032602.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.10-concat_des_2006-02-16004450.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.11-concat_des_2006-02-13183354.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.12-concat_des_2006-02-14085209.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.2-concat_des_2006-02-13181115.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.3-concat_des_2006-02-14230509.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.5-concat_des_2006-02-13180538.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.6-concat_des_2006-02-15103130.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.7-concat_des_2006-02-14070904.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7.8-concat_des_2006-02-14194920.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/mozilla-1.7a-concat_des_2006-02-14211553.aol" });
		final AOLCreator aolCreator =
			new AOLCreator(
				new String[] { "rsc/mozilla-1.7b-concat_des_2006-02-13183827.aol" });
		aolCreator.create(codeLevelModel, true, true);

		// General output writer.
		Writer writer;

		// Print out the list of all classes.
		//	{
		//		try {
		//			System.out.println("Classes...");
		//			writer = new FileWriter("C:/Temp/Classes.csv");
		//			final Iterator entities =
		//				codeLevelModel.getIteratorOnConstituents(IEntity.class);
		//			while (entities.hasNext()) {
		//				final IEntity entity = (IEntity) entities.next();
		//				writer.write(entity.getName());
		//				if (entities.hasNext()) {
		//					writer.write('\n');
		//				}
		//			}
		//			writer.close();
		//		}
		//		catch (final IOException ioe) {
		//			ioe.printStackTrace();
		//		}
		//	}

		// Print out the list of all operations,
		// prefixed with their declaring class names.
		//	{
		//		try {
		//			System.out.println("Methods...");
		//			writer = new FileWriter("C:/Temp/Methods.csv");
		//			final Iterator entities =
		//				codeLevelModel.getIteratorOnConstituents(IEntity.class);
		//			while (entities.hasNext()) {
		//				final IEntity entity = (IEntity) entities.next();
		//				final Iterator methods =
		//					entity.getIteratorOnConstituents(IMethod.class);
		//				while (methods.hasNext()) {
		//					final IMethod method = (IMethod) methods.next();
		//					writer.write(entity.getName());
		//					writer.write(",\"");
		//					writer.write(method.toString().replace('\n', ' '));
		//					writer.write('"');
		//					if (methods.hasNext()) {
		//						writer.write('\n');
		//					}
		//				}
		//			}
		//			writer.close();
		//		}
		//		catch (final IOException ioe) {
		//			ioe.printStackTrace();
		//		}
		//	}

		// Print out the list of all attributes,
		// prefixed with their declaring class names.
		//	{
		//		try {
		//			System.out.println("Fields...");
		//			writer = new FileWriter("C:/Temp/Fields.csv");
		//			final Iterator entities =
		//				codeLevelModel.getIteratorOnConstituents(IEntity.class);
		//			while (entities.hasNext()) {
		//				final IEntity entity = (IEntity) entities.next();
		//				final Iterator fields =
		//					entity.getIteratorOnConstituents(IField.class);
		//				while (fields.hasNext()) {
		//					final IField field = (IField) fields.next();
		//					writer.write(entity.getName());
		//					writer.write(',');
		//					writer.write(field.toString());
		//					writer.write('\n');
		//				}
		//			}
		//			writer.close();
		//		}
		//		catch (final IOException ioe) {
		//			ioe.printStackTrace();
		//		}
		//	}

		// Print out the data requied by Paolo:
		// Class name, number of fields, number of methods, number of associations, vector of identifiers
		{
			try {
				System.out.println("Data...");
				writer =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						"../PADL Creator AOL/tmp/Data.csv");
				writer
					.write("Class name, Number of fields, Number of methods, Number of associations, Identifiers\n");
				final Iterator entities =
					codeLevelModel
						.getIteratorOnConstituents(IFirstClassEntity.class);
				final List identifiers = new ArrayList();
				while (entities.hasNext()) {
					final IFirstClassEntity firstClassEntity =
						(IFirstClassEntity) entities.next();
					writer.write(firstClassEntity.getName());
					writer.write(',');

					identifiers.clear();

					writer.write(""
							+ firstClassEntity
								.getNumberOfConstituents(IField.class));
					writer.write(',');
					final Iterator fields =
						firstClassEntity
							.getIteratorOnConstituents(IField.class);
					while (fields.hasNext()) {
						final IField field = (IField) fields.next();
						identifiers.add(field.getName());
					}

					writer.write(""
							+ firstClassEntity
								.getNumberOfConstituents(IMethod.class));
					writer.write(',');
					final Iterator methods =
						firstClassEntity
							.getIteratorOnConstituents(IMethod.class);
					while (methods.hasNext()) {
						final IMethod method = (IMethod) methods.next();
						identifiers.add(method.getName());

						final Iterator parameters =
							method.getIteratorOnConstituents(IParameter.class);
						while (parameters.hasNext()) {
							final IParameter parameter =
								(IParameter) parameters.next();
							identifiers.add(parameter.getName());
						}
					}

					writer.write(""
							+ firstClassEntity
								.getNumberOfConstituents(IRelationship.class));
					writer.write(',');

					writer.write(identifiers.toString());

					if (entities.hasNext()) {
						writer.write('\n');
					}
				}
				writer.close();
			}
			catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
