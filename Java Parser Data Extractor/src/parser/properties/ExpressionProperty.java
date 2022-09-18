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
package parser.properties;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ExpressionProperty implements IProperty {

	public boolean isInterestingNode(final ASTNode node) {

		return node instanceof MarkerAnnotation
				|| node instanceof NormalAnnotation
				|| node instanceof SingleMemberAnnotation
				|| node instanceof ArrayAccess
				|| node instanceof ArrayCreation
				|| node instanceof ArrayInitializer
				|| node instanceof Assignment
				|| node instanceof BooleanLiteral
				||
				//node instanceof CastExpression                  ||
				node instanceof CharacterLiteral
				||
				//node instanceof ClassInstanceCreation           ||
				node instanceof ConditionalExpression
				|| node instanceof FieldAccess
				|| node instanceof InfixExpression
				|| node instanceof InstanceofExpression
				|| node instanceof MethodInvocation
				||
				//node instanceof Name							||
				node instanceof NullLiteral
				|| node instanceof NumberLiteral
				||
				//node instanceof ParenthesizedExpression         ||
				node instanceof PostfixExpression
				|| node instanceof PrefixExpression
				|| node instanceof StringLiteral
				|| node instanceof SuperFieldAccess
				|| node instanceof SuperMethodInvocation
				|| node instanceof ThisExpression
				|| node instanceof TypeLiteral
				|| node instanceof VariableDeclarationExpression ||
				// the nodes below are not in Expression hierarchy
				node instanceof VariableDeclarationStatement;
	}

}
