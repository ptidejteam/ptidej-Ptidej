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
package parser.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Comment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import parser.reader.NamedReader;

public class NamedCompilationUnit {
	private final String javaFilePath;
	private final CompilationUnit compilationUnit;

	public NamedCompilationUnit(
		final String javaFilePath,
		final CompilationUnit compilationUnit) {
		this.javaFilePath = javaFilePath;
		this.compilationUnit = compilationUnit;
	}

	public void accept(final ExtendedASTVisitor visitor) {
		if (visitor != null) {
			if (visitor.visit(this)) {
				if (visitor.visitJavaFilePath(this.javaFilePath)) {
					this.compilationUnit.accept(visitor);
				}
				visitor.endVisitJavaFilePath(this.javaFilePath);
			}
			visitor.endVisit(this);
		}
	}

	public String getCommentContent(
		final Comment comment,
		final NamedReader sourceReader) {
		final NamedReader[] sources = sourceReader.read();

		String src = "";

		if (sources != null) {
			for (final NamedReader source : sources) {
				src += source.getName();
			}
		}

		return src.substring(
			comment.getStartPosition(),
			comment.getStartPosition() + comment.getLength());
	}

	public CompilationUnit getCompilationUnit() {
		return this.compilationUnit;
	}

	public String getJavaFilePath() {
		return this.javaFilePath;
	}

	public List<Comment> getLineAndBlockComment() {
		if (this.compilationUnit != null) {
			final List<Comment> comments =
				this.compilationUnit.getCommentList();
			if (comments != null) {
				final List<Comment> lineAndBlockComments =
					new ArrayList<Comment>();
				for (final Comment comment : comments) {
					if (comment.getNodeType() != ASTNode.JAVADOC) {
						lineAndBlockComments.add(comment);
					}
				}
				return Collections.unmodifiableList(lineAndBlockComments);
			}
		}
		return null;
	}
}
