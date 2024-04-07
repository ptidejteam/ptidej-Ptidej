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
package padl.kernel.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.IConstituent;
import padl.kernel.IMethodInvocation;
import padl.kernel.exception.ModelDeclarationException;
import padl.test.helper.Reflector;
import com.ibm.toad.cfparse.utils.Access;

/**
 * Basic unit test for padl.kernel.impl.AbstractContainer abstract base class.
 * 
 * @author Stéphane Vaucher
 * @since  2005/11/25
 */
public class AbstractContainerTest extends TestCase {
	private Class clazz;
	private java.lang.reflect.Field containerField;
	public AbstractContainerTest(final String aName) {
		super(aName);
	}

	public void setUp() throws Exception {
		super.setUp();
		this.clazz = new Class("Dummy".toCharArray(), "Dummy".toCharArray());
		this.containerField =
			FirstClassEntity.class.getDeclaredField("container");
		this.containerField.setAccessible(true);
	}
	public void testAddEntity() {
		String m1 = "foo()";
		String m2 = "bar()";

		this.clazz.addConstituent(new Method(m1));
		this.clazz.addConstituent(new Method(m2));

		Assert.assertNotNull(this.clazz.getConstituentFromID(m1));
		Assert.assertNotNull(this.clazz.getConstituentFromID(m2));
	}
	public void testAddEntitySameType() {
		try {
			this.clazz.addConstituent(new Method("foo()"));
			this.clazz.addConstituent(new Method("foo()"));
		}
		catch (ModelDeclarationException e) {
			Assert.assertTrue("Two methods with the same id should cause a "
					+ ModelDeclarationException.class.getName(), true);
			return;
		}
		Assert.fail("Two methods with the same id should cause a "
				+ ModelDeclarationException.class.getName());
	}
	public void testAddEqualEntity() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {

		final Method method = new Method("a");
		final FirstClassEntity firstClassEntity =
			new FirstClassEntity("calledEntity".toCharArray()) {
				private static final long serialVersionUID =
					3509108360306890991L;
			};

		MethodInvocation minvocation =
			new MethodInvocation(
				IMethodInvocation.CLASS_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);
		method.addConstituent(minvocation);

		minvocation =
			new MethodInvocation(
				IMethodInvocation.CLASS_CLASS,
				0,
				Access.ACC_PUBLIC,
				firstClassEntity);
		method.addConstituent(minvocation);

		Reflector.callMethodOnReflectedField(
			FirstClassEntity.class,
			"container",
			this.clazz,
			"addConstituent",
			IConstituent.class,
			method);
	}
	public void testAddEqualEntity2() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException {

		try {
			final padl.kernel.impl.Field field =
				new padl.kernel.impl.Field("a");
			Reflector.callMethodOnReflectedField(
				FirstClassEntity.class,
				"container",
				this.clazz,
				"addConstituent",
				IConstituent.class,
				field);

			final Method method = new Method("a");
			Reflector.callMethodOnReflectedField(
				FirstClassEntity.class,
				"container",
				this.clazz,
				"addConstituent",
				IConstituent.class,
				method);
		}
		catch (final ModelDeclarationException e) {
			Assert
				.assertTrue(
					"Class should support a method and field with different IDs only.",
					true);
			return;
		}
		catch (final InvocationTargetException e) {
			Assert
				.assertTrue(
					"Class should support a method and field with different IDs only.",
					true);
			return;
		}
		Assert.assertTrue(
			"Class should support a method and field with different IDs only.",
			false);
	}
	public void testAddEqualEntity3() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, NoSuchMethodException,
			InvocationTargetException {

		final padl.kernel.impl.Field field = new padl.kernel.impl.Field("a");
		Reflector.callMethodOnReflectedField(
			FirstClassEntity.class,
			"container",
			this.clazz,
			"addConstituent",
			IConstituent.class,
			field);

		final Method method = new Method("a()");
		Reflector.callMethodOnReflectedField(
			FirstClassEntity.class,
			"container",
			this.clazz,
			"addConstituent",
			IConstituent.class,
			method);
	}
	public void testGetIteratorOnEntities() throws Exception {
		final Method m1 = new Method("m1");
		final Method m2 = new Method("m2");
		final padl.kernel.impl.Field f = new padl.kernel.impl.Field("f");

		this.clazz.addConstituent(m1);
		this.clazz.addConstituent(m2);
		this.clazz.addConstituent(f);

		final Iterator it = this.clazz.getIteratorOnConstituents();
		final ArrayList l = new ArrayList();
		while (it.hasNext()) {
			l.add(it.next());
		}

		Assert.assertEquals(
			"Iterator should return all of the elements contained",
			3,
			l.size());
	}
	public void testGetIteratorOnEntitiesClass() throws Exception {
		final Method m1 = new Method("m1");
		final Method m2 = new Method("m2");
		final padl.kernel.impl.Field f = new padl.kernel.impl.Field("f");

		this.clazz.addConstituent(m1);
		this.clazz.addConstituent(m2);
		this.clazz.addConstituent(f);

		final Iterator it =
			this.clazz.getConcurrentIteratorOnConstituents(Method.class);
		final ArrayList l = new ArrayList();
		while (it.hasNext()) {
			l.add(it.next());
		}

		Assert.assertEquals(
			"Iterator should only return the elements of type Method",
			2,
			l.size());
	}
	public void testRemoveEntityFromID() throws Exception {
		final Method m = new Method("m1");
		Assert.assertNull(
			"Should not already hold this element",
			this.clazz.getConstituentFromID("m1"));
		this.clazz.addConstituent(m);

		Assert.assertEquals(
			"Method returned is not the same as was inserted.",
			m,
			this.clazz.getConstituentFromID("m1"));

		this.clazz.removeConstituentFromID(m.getID());
		Assert.assertNull(
			"Should not already hold this element",
			this.clazz.getConstituentFromID("m1"));

	}
	public void testRemoveEntityFromID2() throws Exception {
		final Method m = new Method("m1");
		final char[] mName = "foo".toCharArray();
		m.setName(mName);
		Assert.assertNull(
			"Should not already hold this element",
			this.clazz.getConstituentFromID("m1"));
		this.clazz.addConstituent(m);

		Assert.assertEquals(
			"Method returned is not the same as was inserted.",
			m,
			this.clazz.getConstituentFromName(mName));

		this.clazz.removeConstituentFromID(m.getID());
		Assert.assertNull(
			"Should not already hold this element",
			this.clazz.getConstituentFromName(mName));

	}
	public void testResetListOfEntities() throws Exception {
		this.clazz.addConstituent(new Method("m1"));
		Reflector.callMethodOnReflectedField(
			FirstClassEntity.class,
			"container",
			this.clazz,
			"resetListOfConstituents");

		Assert.assertEquals(
			"List of actors should be reset",
			0,
			this.clazz.getNumberOfConstituents());
	}
}
