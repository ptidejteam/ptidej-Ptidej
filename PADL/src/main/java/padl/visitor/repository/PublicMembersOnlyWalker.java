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
package padl.visitor.repository;

import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfEntity;
import padl.kernel.IField;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.util.adapter.WalkerAdapter;
import padl.visitor.ISelectiveVisitor;

public class PublicMembersOnlyWalker extends WalkerAdapter
		implements ISelectiveVisitor {

	private final StringBuilder result = new StringBuilder();
	private int publicMemberCount = 0;
	private int skippedMemberCount = 0;

	@Override
	public String getName() {
		return "Public Members Only Walker";
	}

	@Override
	public boolean shouldVisit(final IConstituent aConstituent) {
		if (!(aConstituent instanceof IConstituentOfEntity)) {
			return true;
		}
		final IConstituentOfEntity member = (IConstituentOfEntity) aConstituent;
		if (member.isPublic()) {
			return true;
		}
		else {
			this.skippedMemberCount++;
			return false;
		}
	}

	@Override
	public boolean shouldDescend(final IConstituent aConstituent) {
		if (aConstituent instanceof IClass) {
			final IClass clazz = (IClass) aConstituent;
			final String name = String.valueOf(clazz.getName());
			if (name.endsWith("Util") || name.endsWith("Helper")) {
				this.result
					.append("  [Skipping utility class: ")
					.append(name)
					.append("]\n");
				return false;
			}
		}
		return true;
	}

	@Override
	public void open(final IClass aClass) {
		this.result.append("Public API of class: ").append(aClass.getName()).append(
			"\n");
	}

	@Override
	public void open(final IInterface anInterface) {
		this.result
			.append("Public API of interface: ")
			.append(anInterface.getName())
			.append("\n");
	}

	public void visit(final IMethod aMethod) {
		if (aMethod.isPublic()) {
			this.publicMemberCount++;
			this.result
				.append("  + Method: ")
				.append(aMethod.getDisplayName())
				.append("\n");
		}
	}

	@Override
	public void visit(final IField aField) {
		if (aField.isPublic()) {
			this.publicMemberCount++;
			this.result
				.append("  + Field: ")
				.append(aField.getDisplayName())
				.append(" : ")
				.append(aField.getDisplayTypeName())
				.append("\n");
		}
	}

	@Override
	public void close(final IClass aClass) {
	}

	@Override
	public Object getResult() {
		final StringBuilder summary = new StringBuilder();
		summary
			.append("=== Public Members Summary ===\n")
			.append("Public members found: ")
			.append(this.publicMemberCount)
			.append("\n")
			.append("Private members skipped: ")
			.append(this.skippedMemberCount)
			.append("\n\n")
			.append(this.result.toString());
		return summary.toString();
	}

	@Override
	public void reset() {
		this.result.setLength(0);
		this.publicMemberCount = 0;
		this.skippedMemberCount = 0;
	}
}
