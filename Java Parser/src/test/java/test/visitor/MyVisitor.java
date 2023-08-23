/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package test.visitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

import client.WrapperClientWithLog;

/*
 * A simple test visitor
 */
public class MyVisitor extends ASTVisitor {

    private final WrapperClientWithLog javaProject;

    public MyVisitor(final WrapperClientWithLog javaProject) {
        this.javaProject = javaProject;
    }

    private void cannotSolve(final ASTNode node) {
        // this.javaProject.log("Cannot solve "+node);
    }

    @Override
    public boolean visit(final MethodDeclaration node) {

        final Type type = node.getReturnType2();
        if (type != null) {
            final ITypeBinding typeBinding = type.resolveBinding();

            if (typeBinding != null) {
                this.javaProject.log(typeBinding.getQualifiedName());
            } else {
                this.cannotSolve(type);
            }
        } else {
            this.cannotSolve(node);
        }

        return super.visit(node);
    }
}
