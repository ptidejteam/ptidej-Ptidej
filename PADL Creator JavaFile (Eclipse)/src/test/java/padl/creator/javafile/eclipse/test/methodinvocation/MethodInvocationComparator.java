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
/* (c) Copyright 2009 and following years, Aminata SABANE,
 * Ecole Polytechnique de MontrÌ©al.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.creator.javafile.eclipse.test.methodinvocation;

import java.util.Iterator;
import org.junit.Assert;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;

public class MethodInvocationComparator {
	public static boolean isMIofJavaModelMethodIncludedInMIofClassModelMethod(
		final IMethod javaFilesMethod,
		final IMethod classFilesMethod) {

		IMethodInvocation currentJavaMethodInvocation = null;

		boolean included = false;
		final Iterator iteratorOnJavaFilesMIs =
			javaFilesMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iteratorOnJavaFilesMIs.hasNext() && !included) {
			currentJavaMethodInvocation =
				(IMethodInvocation) iteratorOnJavaFilesMIs.next();

			included = false;
			final Iterator iteratorOnClassFilesMIs =
				classFilesMethod
					.getIteratorOnConstituents(IMethodInvocation.class);
			while (iteratorOnClassFilesMIs.hasNext() && !included) {
				final IMethodInvocation currentClassMethodInvocation =
					(IMethodInvocation) iteratorOnClassFilesMIs.next();
				included =
					currentJavaMethodInvocation
						.equals(currentClassMethodInvocation);
			}
		}

		Assert.assertTrue("Cannot find the method invocation"
				+ currentJavaMethodInvocation.toString(), included);

		return included;
	}
}
