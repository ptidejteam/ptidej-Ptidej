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
package padl.creator.classfile.test.example;

import java.util.Iterator;
import org.junit.Assert;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.exception.CreationException;
import padl.util.Util;

public class Composite1 extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities;

	public Composite1(final String name) {
		super(name);
	}
	protected void setUp()
			throws IllegalAccessException, InstantiationException,
			CreationException, UnsupportedSourceModelException {

		if (Composite1.FirstClassEntities == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
				.getFactory()
				.createCodeLevelModel("padl.example.composite1");
			codeLevelModel.create(
				new CompleteClassFileCreator(
					new String[] {
							"../PADL Creator ClassFile/target/test-classes/padl/example/composite1/" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Composite1.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);
		}
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			11,
			Composite1.FirstClassEntities.length);
	}
	public void testDocument() {
		final IClass document = (IClass) Composite1.FirstClassEntities[5];
		Assert.assertEquals(
			"Document name",
			"padl.example.composite1.Document",
			document.getDisplayID());
		Assert.assertEquals(
			"Document super-entity",
			1,
			document.getNumberOfInheritedEntities());
		Assert.assertEquals(
			"Document super-entity name",
			"java.lang.Object",
			((IFirstClassEntity) document
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
		Assert.assertEquals(
			"Elements of Document",
			15,
			document.getNumberOfConstituents());
	}
	public void testIndentedParagraph() {
		final IClass indentedParagraph =
			(IClass) Composite1.FirstClassEntities[7];
		Assert.assertEquals(
			"IndentedParagraph name",
			"padl.example.composite1.IndentedParagraph",
			indentedParagraph.getDisplayID());
		Assert.assertEquals(
			"IndentedParagraph super-entity",
			1,
			indentedParagraph.getNumberOfInheritedEntities());
		Assert.assertEquals(
			"IndentedParagraph super-entity name",
			"padl.example.composite1.Paragraph",
			((IFirstClassEntity) indentedParagraph
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
		Assert.assertEquals(
			"Elements of IndentedParagraph",
			2,
			indentedParagraph.getNumberOfConstituents());
	}
	public void testMain() {
		final IClass main = (IClass) Composite1.FirstClassEntities[8];
		Assert.assertEquals(
			"Main name",
			"padl.example.composite1.Main",
			main.getDisplayID());
		Assert.assertEquals(
			"Main super-entity",
			1,
			main.getNumberOfInheritedEntities());
		Assert.assertEquals(
			"Main super-entity name",
			"java.lang.Object",
			((IFirstClassEntity) main.getIteratorOnInheritedEntities().next())
				.getDisplayID());
		Assert.assertEquals(
			"Elements of Main",
			13,
			main.getNumberOfConstituents());
	}
	public void testParagraph() {
		final IClass paragraph = (IClass) Composite1.FirstClassEntities[9];
		Assert.assertEquals(
			"Paragraph name",
			"padl.example.composite1.Paragraph",
			paragraph.getDisplayID());
		Assert.assertEquals(
			"Paragraph super-class",
			1,
			paragraph.getNumberOfInheritedEntities());
		Assert.assertEquals(
			"Paragraph super-class name",
			"java.lang.Object",
			((IFirstClassEntity) paragraph
				.getIteratorOnInheritedEntities()
				.next()).getDisplayID());
		Assert.assertEquals(
			"Paragraph super-interface",
			1,
			paragraph.getNumberOfImplementedInterfaces());
		Assert
			.assertEquals(
				"Paragraph super-interface name",
				"padl.example.composite1.Element",
				((IFirstClassEntity) paragraph
					.getIteratorOnImplementedInterfaces()
					.next()).getDisplayID());
		Assert.assertEquals(
			"Elements of Paragraph",
			3,
			paragraph.getNumberOfConstituents());
	}
	public void testTitle() {
		final IClass title = (IClass) Composite1.FirstClassEntities[10];
		Assert.assertEquals(
			"Title name",
			"padl.example.composite1.Title",
			title.getDisplayID());
		Assert.assertEquals(
			"Title super-class",
			1,
			title.getNumberOfInheritedEntities());
		Assert.assertEquals(
			"Title super-class name",
			"java.lang.Object",
			((IFirstClassEntity) title.getIteratorOnInheritedEntities().next())
				.getDisplayID());
		Assert.assertEquals(
			"Title super-interface",
			1,
			title.getNumberOfImplementedInterfaces());
		Assert
			.assertEquals(
				"Title super-interface name",
				"padl.example.composite1.Element",
				((IFirstClassEntity) title
					.getIteratorOnImplementedInterfaces()
					.next()).getDisplayID());
		Assert.assertEquals(
			"Elements of Title",
			3,
			title.getNumberOfConstituents());
	}
	public void testAbstractDocument() {
		final IInterface abstractDocument =
			(IInterface) Composite1.FirstClassEntities[4];
		Assert.assertEquals(
			"AbstractDocument name",
			"padl.example.composite1.AbstractDocument",
			abstractDocument.getDisplayID());
		Assert.assertEquals(
			"AbstractDocument super-entity",
			1,
			abstractDocument.getNumberOfInheritedEntities());
		//	Assert.assertEquals(
		//		"AbstractDocument super-entity name",
		//		"java.lang.Object",
		//		((IEntity) abstractDocument.listOfInheritedEntities().get(0))
		//			.getDisplayName());
		Assert.assertEquals(
			"AbstractDocument method",
			1,
			abstractDocument.getNumberOfConstituents());
		Assert.assertEquals(
			"AbstractDocument method signature",
			"public abstract void print();",
			((IMethod) Util.getArrayOfConstituents(abstractDocument)[0])
				.toString());
	}
	public void testElement() {
		final IInterface element =
			(IInterface) Composite1.FirstClassEntities[6];
		Assert.assertEquals(
			"Element name",
			"padl.example.composite1.Element",
			element.getDisplayID());
		Assert.assertEquals(
			"Element super-entity",
			2,
			element.getNumberOfInheritedEntities());

		final Iterator iteratorOnInheritedEntities =
			element.getIteratorOnInheritedEntities();
		Assert.assertEquals(
			"Element super-entity name",
			"java.lang.Object",
			((IFirstClassEntity) iteratorOnInheritedEntities.next())
				.getDisplayID());
		Assert.assertEquals(
			"Element super-entity name",
			"padl.example.composite1.AbstractDocument",
			((IFirstClassEntity) iteratorOnInheritedEntities.next())
				.getDisplayID());
		Assert.assertEquals(
			"Elements of Element",
			0,
			element.getNumberOfConstituents());
	}
	public void testGhosts() {
		Assert.assertEquals(
			"java.lang.Object",
			"java.lang.Object",
			Composite1.FirstClassEntities[0].getDisplayID());
		Assert.assertEquals(
			"java.util.Vector",
			"java.util.Vector",
			Composite1.FirstClassEntities[3].getDisplayID());
		Assert.assertEquals(
			"java.util.Enumeration",
			"java.util.Enumeration",
			Composite1.FirstClassEntities[2].getDisplayID());
		Assert.assertEquals(
			"java.lang.String",
			"java.lang.String",
			Composite1.FirstClassEntities[1].getDisplayID());
	}
}
