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
package padl.creator.javafile.eclipse.test.util;

import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.test.helper.ModelComparator;
import util.io.ProxyDisk;

public class ModelComparatorReporter extends ModelComparator {
	protected PrintWriter writer;

	public ModelComparatorReporter(final IAbstractModel anAbstractModel) {
		super(anAbstractModel);
		this.writer =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				"ModelComparatorReport.txt"));
		this.writer.println("Report Beginning");
		this.writer
			.println("***************************************************");
	}

	public void close(final IAbstractLevelModel anAbstractLevelModel) {
		this.writer
			.println("***************************************************");
		this.writer.println("Report End");

		this.writer.close();
	}

	protected void compare(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		/*
		 * System.out.print("Comparing:\n\t");
		 * System.out.println(aConstituent.getName());
		 * System.out.print("with:\n\t");
		 * System.out.println(anotherConstituent.getName());
		 */

		final Class constituentClass = aConstituent.getClass();
		final Field[] constituentFields = constituentClass.getDeclaredFields();
		for (int i = 0; i < constituentFields.length; i++) {
			final Field field = constituentFields[i];
			field.setAccessible(true);

			if (!field
				.getType()
				.getName()
				.startsWith("padl.kernel.impl.GenericContainer")) {

				try {
					if (field.getType().isArray()) {
						final int currentLength =
							Array.getLength(field.get(aConstituent));
						final int anotherLength =
							Array.getLength(field.get(anotherConstituent));

						if (currentLength != anotherLength) {
							this.writer
								.println(" Differences between arrays fields lengths of constituents : field Name "
										+ field.getName());
							this.writer.println("          Constituent "
									+ aConstituent.toString() + " type :"
									+ aConstituent.getClass().getName());
							this.writer.println("          anotherConstituent "
									+ anotherConstituent.toString() + " type :"
									+ anotherConstituent.getClass().getName());
							continue;
						}

						for (int j = 0; j < currentLength; j++) {
							final Object currentObject =
								Array.get(field.get(aConstituent), j);
							final Object anotherObject =
								Array.get(field.get(anotherConstituent), j);
							if (!currentObject.equals(anotherObject)) {
								this.writer
									.println(" Differences between arrays fields values : field Name "
											+ field.getName());
								this.writer.println("          Constituent "
										+ aConstituent.toString() + " type :"
										+ aConstituent.getClass().getName());
								this.writer
									.println("          anotherConstituent "
											+ anotherConstituent.toString()
											+ " type :"
											+ anotherConstituent
												.getClass()
												.getName());
								break;
							}

						}
					}
					else {
						final Object aConstituentAttributeValue =
							field.get(aConstituent);
						final Object anotherConstituentAttributeValue =
							field.get(anotherConstituent);
						if (aConstituentAttributeValue instanceof IConstituent
								&& anotherConstituentAttributeValue instanceof IConstituent) {

							this
								.compare(
									(IConstituent) aConstituentAttributeValue,
									(IConstituent) anotherConstituentAttributeValue);
						}
						else {

							if (!field.get(aConstituent).equals(
								field.get(anotherConstituent))) {
								this.writer
									.println(" Differences between fields values : field Name "
											+ field.getName());
								this.writer.println("          Constituent "
										+ aConstituent.toString() + " type :"
										+ aConstituent.getClass().getName());
								this.writer
									.println("          anotherConstituent "
											+ anotherConstituent.toString()
											+ " type :"
											+ anotherConstituent
												.getClass()
												.getName());
								continue;
							}

						}
					}
				}
				catch (final IllegalArgumentException e) {
					e.printStackTrace();
				}
				catch (final IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void compareWithSanityCheck(
		final IConstituent aConstituent,
		final IConstituent anotherConstituent) {

		if (aConstituent == null) {
			this.writer.println(" Null constituent here");
			return;
		}

		if (anotherConstituent == null) {
			this.writer
				.println(" Another constituent null here (in comparison to "
						+ aConstituent.toString() + ')');
			return;
		}

		if (!aConstituent
			.getClass()
			.getName()
			.equals(anotherConstituent.getClass().getName())) {
			this.writer.println(" Differences between constituents types : ");
			this.writer.println("          Constituent "
					+ aConstituent.toString() + " type :"
					+ aConstituent.getClass().getName());
			this.writer.println("          anotherConstituent "
					+ anotherConstituent.toString() + " type :"
					+ anotherConstituent.getClass().getName());

			return;

		}
		this.compare(aConstituent, anotherConstituent);
	}

	protected void open(final IElement anElement) {
		this.writer.println();
		this.writer.println(" 		Element type" + anElement.getClass().getName()
				+ " entity ID " + anElement.getDisplayID());

		super.open(anElement);
	}

	protected void open(final IFirstClassEntity anEntity) {
		this.writer.println();
		this.writer.println("----------------------------------------------");
		this.writer.println(" Entity type" + anEntity.getClass().getName()
				+ " entity ID " + anEntity.getDisplayID());
		super.open(anEntity);
	}

}
