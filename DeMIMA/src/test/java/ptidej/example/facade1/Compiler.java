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
package ptidej.example.facade1;

public class Compiler {
	private Parser parser;
	private Node node;
	private CodeGenerator codeGenerator;
	public void compile() {
		System.out.println("Compiling...");
		this.parser = new Parser();
		this.parser.parse();
		this.node = new StatementNode();
		this.node.print();
		this.node = new ExpressionNode();
		this.node.print();
		this.codeGenerator = new StackMachineCodeGenerator(this.node);
		this.codeGenerator.generate();
		System.out.println("Done.");
	}
}
