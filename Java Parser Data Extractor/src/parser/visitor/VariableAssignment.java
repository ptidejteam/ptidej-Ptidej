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
package parser.visitor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import parser.wrapper.ExtendedASTVisitor;
import parser.wrapper.NamedCompilationUnit;

public class VariableAssignment extends ExtendedASTVisitor {
	private String fileName;
	static FileOutputStream fos;
	public static OutputStreamWriter out;

	static {
		try {
			VariableAssignment.fos =
				new FileOutputStream("variableAssignment.csv");
			VariableAssignment.out =
				new OutputStreamWriter(VariableAssignment.fos);
		}
		catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String deleteNewLine(final String someNodeInfo) {

		final String info = someNodeInfo.replaceAll("\\n", "");
		return info;
	}

	@Override
	public boolean visit(final ExpressionStatement node) {
		//		System.out.println(this.fileName
		//				+ deleteNewLine(node.getExpression().toString()));
		if (node.getNodeType() == ASTNode.ASSIGNMENT) {
			try {
				VariableAssignment.out.append(this.fileName
						+ this.deleteNewLine(node.getExpression().toString()));
				VariableAssignment.out.append("\n");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		System.out.println("visiting: "
				+ aNamedCompilationUnit.getJavaFilePath());
		this.fileName =
			aNamedCompilationUnit.getJavaFilePath().toString() + "::";
		return super.visit(aNamedCompilationUnit);
	}

	@Override
	public boolean visit(final VariableDeclarationFragment node) {
		final String LHS = this.deleteNewLine(node.getName().toString());
		String RHS = "";
		if (node.getInitializer() != null) {
			RHS = this.deleteNewLine(node.getInitializer().toString());
			//			System.out.println(this.fileName + LHS + "=" + RHS);
			try {
				VariableAssignment.out.append(this.fileName + LHS + "=" + RHS);
				VariableAssignment.out.append("\n");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return super.visit(node);
	}

}
