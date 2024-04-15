/**
 * @author Mathieu Lemoine
 * @created 2009-02-28 (土)
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

package jct.kernel.impl;

import java.util.HashSet;
import java.util.Set;
import jct.kernel.Constants;
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTIntersectionType;
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.JCTPrimitiveTypes;

/**
 * This class represents an arithmetic binary operator
 *
 * @author Mathieu Lemoine
 */
abstract class JCTArithmeticBinaryOperator extends JCTBinaryOperator {
	JCTArithmeticBinaryOperator(
		final IJCTRootNode aRootNode,
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		super(aRootNode, leftOperand, rightOperand);
	}

	public IJCTType getTypeResult() {
		return JCTArithmeticBinaryOperator.computeTypeResult(this
			.getLeftOperand()
			.getTypeResult(), this.getRightOperand().getTypeResult());
	}

	private static IJCTType resolveType(final IJCTType t) {
		if (null == t)
			return null;

		if (t instanceof IJCTPrimitiveType)
			return t;

		final IJCTClass c = ((IJCTClassType) t).getSelector().getElement();

		if (Constants.CLASSPATH_STRING.equals(c.getFQN()))
			return t;
		else if (Constants.CLASSPATH_DOUBLE.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.DOUBLE);
		else if (Constants.CLASSPATH_FLOAT.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.FLOAT);
		else if (Constants.CLASSPATH_LONG.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.LONG);
		else if (Constants.CLASSPATH_INT.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.INTEGER);
		else if (Constants.CLASSPATH_SHORT.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.SHORT);
		else if (Constants.CLASSPATH_BYTE.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.BYTE);
		else if (Constants.CLASSPATH_BOOLEAN.equals(c.getFQN()))
			return t.getRootNode().getType(JCTPrimitiveTypes.BOOLEAN);
		else
			return t.getRootNode().getType(JCTPrimitiveTypes.VOID);
	}

	private static IJCTType computeTypeResult(final IJCTType l, final IJCTType r) {
		if ((l instanceof IJCTIntersectionType)
				|| (r instanceof IJCTIntersectionType)) {
			final Set<IJCTType> result = new HashSet<IJCTType>();

			if ((l instanceof IJCTIntersectionType)
					&& (r instanceof IJCTIntersectionType))
				for (final IJCTType li : ((IJCTIntersectionType) l).getTypes())
					for (final IJCTType ri : ((IJCTIntersectionType) r)
						.getTypes())
						result.add(JCTArithmeticBinaryOperator
							.computeTypeResult(li, ri));
			else if (l instanceof IJCTIntersectionType)
				for (final IJCTType li : ((IJCTIntersectionType) l).getTypes())
					result.add(JCTArithmeticBinaryOperator.computeTypeResult(
						li,
						r));
			else
				//if(r instanceof IJCTIntersectionType)
				for (final IJCTType ri : ((IJCTIntersectionType) r).getTypes())
					result.add(JCTArithmeticBinaryOperator.computeTypeResult(
						l,
						ri));

			return l.getRootNode().getType(
				new JCTIntersectionType(l.getRootNode(), result
					.toArray(new IJCTType[0])).getTypeName(),
				IJCTType.class);
		}
		else {
			JCTPrimitiveTypes lp = null;
			JCTPrimitiveTypes rp = null;

			IJCTType type = JCTArithmeticBinaryOperator.resolveType(l);

			if (type instanceof IJCTPrimitiveType)
				lp = ((IJCTPrimitiveType) type).getType();
			else
				return type;

			type = JCTArithmeticBinaryOperator.resolveType(r);

			if (type instanceof IJCTPrimitiveType)
				rp = ((IJCTPrimitiveType) type).getType();
			else
				return type;

			return l.getRootNode().getType(lp.compareTo(rp) > 0 ? lp : rp);
		}
	}

	private static final long serialVersionUID = 3579689325212112308L;

}
