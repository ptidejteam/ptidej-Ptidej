/**
 * @author Mathieu Lemoine
 * @created 2009-05-19 (�?�)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package padl.test.defaultpackage;

import java.util.Arrays;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.kernel.Constants;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IFactory;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.impl.Factory;

public class TestDefaultPackage extends TestCase {

	private final IFactory factory = Factory.getInstance();

	public TestDefaultPackage(final String name) {
		super(name);
	}

	public void testCreationOfDefaultPackage() {
		final IConstituent p = this.factory.createPackageDefault();

		Assert.assertTrue(
			"A default package is not a Package",
			p instanceof IPackage);
		Assert.assertTrue(
			"A default package is not a Default Package",
			p instanceof IPackageDefault);
	}

	public void testRetrieveDefaultPackageByName() {
		final IAbstractModel model =
			this.factory.createCodeLevelModel("".toCharArray());
		final IPackage p = this.factory.createPackageDefault();

		model.addConstituent(p);

		Assert.assertTrue(
			"Default Package name is not equal to char[0]",
			Arrays.equals(new char[0], p.getName()));
		Assert.assertTrue(
			"The model does not recognize the very exact name object",
			model.doesContainConstituentWithName(p.getName()));
		Assert
			.assertTrue(
				"It is impossible to retreive the default package with only its name",
				model.doesContainConstituentWithName(new char[0]));
	}

	public void testRetrieveDefaultPackageByID() {
		final IAbstractModel model =
			this.factory.createCodeLevelModel("".toCharArray());
		final IPackage p = this.factory.createPackageDefault();

		model.addConstituent(p);

		Assert.assertTrue(
			"Default Package ID is not equal to \"DEFAULT_PACKAGE_ID\"",
			Arrays.equals("DEFAULT_PACKAGE_ID".toCharArray(), p.getID()));
		Assert
			.assertTrue(
				"It is impossible to retreive the default package with the ID Constant",
				model
					.doesContainConstituentWithID(Constants.DEFAULT_PACKAGE_ID));
		Assert.assertTrue(
			"The model does not recognize the very exact ID object",
			model.doesContainConstituentWithID(p.getID()));
		Assert
			.assertTrue(
				"It is impossible to retreive the default package with only its ID",
				model.doesContainConstituentWithID("DEFAULT_PACKAGE_ID"
					.toCharArray()));
	}
}
