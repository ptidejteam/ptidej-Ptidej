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
package padl.motif.kernel.impl;

import java.util.Arrays;
import java.util.Iterator;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentExtension;
import padl.kernel.exception.ModelDeclarationException;
import padl.kernel.impl.AbstractModel;
import padl.kernel.impl.Constituent;
import padl.kernel.impl.GenericContainerConstants;
import padl.motif.IDesignMotifModel;
import padl.motif.detector.IDetector;
import padl.motif.visitor.IMotifGenerator;
import padl.motif.visitor.IMotifWalker;
import padl.path.IConstants;
import padl.visitor.IGenerator;
import padl.visitor.IVisitor;
import padl.visitor.IWalker;
import util.io.ProxyConsole;

class DesignMotifModel extends AbstractModel implements IDesignMotifModel {
	private static final long serialVersionUID = 5943218392258815564L;

	private int classification;
	private IDetector defaultDetector;
	private IConstituent embeddedConstituent;
	private IConstituentExtension[] extensions;
	private int indexInPrimeNumbersArray;
	private String intent;
	private int numberOfExtensions;

	public DesignMotifModel(final char[] anID) {
		super(anID);
		this.embeddedConstituent = new Constituent(anID) {
			private static final long serialVersionUID = -4888313345209939932L;

			protected char getPathSymbol() {
				return 0;
			}
		};
	}
	public void accept(final IVisitor aVisitor) {
		if (aVisitor instanceof IWalker) {
			this.walk((IWalker) aVisitor);
		}
		else if (aVisitor instanceof IGenerator) {
			this.generate((IGenerator) aVisitor);
		}
		else {
			aVisitor.unknownConstituentHandler(
				"Object DesignMotifModel.walk(IWalker)",
				this);
		}
	}
	public void addExtension(final IConstituentExtension anExtension) {
		if (anExtension instanceof IConstituentExtension) {
			final int minCapacity = this.numberOfExtensions + 1;
			final int oldCapacity = this.extensions.length;
			if (minCapacity > oldCapacity) {
				final IConstituentExtension[] oldData = this.extensions;
				this.indexInPrimeNumbersArray++;
				int newCapacity =
					GenericContainerConstants.PRIME_NUMBERS[this.indexInPrimeNumbersArray];
				if (newCapacity < minCapacity)
					newCapacity = minCapacity;
				this.extensions = new IConstituentExtension[newCapacity];
				System.arraycopy(
					oldData,
					0,
					this.extensions,
					0,
					this.numberOfExtensions);
			}
			this.extensions[this.numberOfExtensions] = anExtension;
			this.numberOfExtensions++;

			anExtension.setExtendedConstituent(this);
		}
		else {
			throw new ModelDeclarationException(anExtension
				.getClass()
				.getName() + " must be a subtype of IConstituentExtension");
		}
	}
	public Object clone() throws CloneNotSupportedException {
		final DesignMotifModel clonedModel = (DesignMotifModel) super.clone();
		this.embeddedConstituent.startCloneSession();
		this.embeddedConstituent.performCloneSession();
		this.embeddedConstituent.endCloneSession();
		clonedModel.embeddedConstituent = this.embeddedConstituent.getClone();
		return clonedModel;
	}
	public void endCloneSession() {
		// Do nothing, see clone();
	}
	public final String generate(final IGenerator aGenerator) {
		if (aGenerator instanceof IMotifGenerator) {
			((IMotifGenerator) aGenerator).open(this);
			final Iterator iterator = this.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				((IConstituent) iterator.next()).accept(aGenerator);
			}
			((IMotifGenerator) aGenerator).close(this);
			return aGenerator.getCode();
		}
		else if (aGenerator instanceof IGenerator) {
			return super.generate(aGenerator);
		}
		else {
			aGenerator.unknownConstituentHandler(
				"String DesignMotifModel.generate(IGenerator)",
				this);
			return "";
		}
	}
	public int getClassification() {
		return this.classification;
	}
	public IConstituent getClone() {
		try {
			return (IConstituent) this.clone();
		}
		catch (final CloneNotSupportedException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	public String[] getCodeLines() {
		return this.embeddedConstituent.getCodeLines();
	}
	public String getComment() {
		return this.embeddedConstituent.getComment();
	}
	/**
	 * This method returns a list of hashtable. Each hashtable represents a solution
	 * found. A solution is a set of keys representing the entities, associated to the
	 * real entities found in the given source code.
	 */
	//	public List compare(final IAbstractModel model) {
	//		final List solutions = new ArrayList();
	//		Map partialSolutions;
	//
	//		if (this.getDetector() == null) {
	//			OutputManager.getCurrentOutputManager().getErrorOutput().println(
	//				MultilanguageManager.getStrResource(
	//					"Err_INIT_ALMD",
	//					resourceBaseName
	//				)
	//			);
	//			return new ArrayList();
	//		}
	//		this.getDetector().setPattern(this);
	//		partialSolutions =
	//			((Detector) this.getDetector()).buildPartialSolution(model);
	//		if (partialSolutions.size() > 0) {
	//			partialSolutions =
	//				((Detector) this.getDetector()).applyCriterias(
	//					partialSolutions,
	//					Detector.AllCriterias);
	//			if (partialSolutions.size() > 0) {
	//				solutions.add(partialSolutions);
	//			}
	//		}
	//		return solutions;
	//	}
	public final IDetector getDetector() {
		return this.defaultDetector;
	}
	public String getDisplayID() {
		return this.embeddedConstituent.getDisplayID();
	}
	public IConstituentExtension getExtension(final char[] anExtensionName) {
		for (int i = 0; i < this.extensions.length; i++) {
			final IConstituentExtension extension = this.extensions[i];
			if (Arrays.equals(extension.getName(), anExtensionName)) {
				return extension;
			}
		}
		return null;
	}
	public char[] getID() {
		return this.embeddedConstituent.getID();
	}
	public String getIntent() {
		return this.intent;
	}
	public char[] getPath() {
		return this.getID();
	}
	protected char getPathSymbol() {
		return IConstants.DESIGN_MOTIF_MODEL_SYMBOL;
	}
	public int getVisibility() {
		return this.embeddedConstituent.getVisibility();
	}
	public int getWeight() {
		return this.embeddedConstituent.getWeight();
	}
	public boolean isAbstract() {
		return this.embeddedConstituent.isAbstract();
	}
	public boolean isFinal() {
		return this.embeddedConstituent.isFinal();
	}
	public boolean isPrivate() {
		return this.embeddedConstituent.isPrivate();
	}
	public boolean isProtected() {
		return this.embeddedConstituent.isProtected();
	}
	public boolean isPublic() {
		return this.embeddedConstituent.isPublic();
	}
	public boolean isStatic() {
		return this.embeddedConstituent.isStatic();
	}
	public void performCloneSession() {
		// Do nothing, see clone();
	}
	public void resetCodeLines() {
		this.embeddedConstituent.resetCodeLines();
	}
	public void setAbstract(boolean aBoolean) {
		this.embeddedConstituent.setAbstract(aBoolean);
	}
	public void setClassification(final int aClassification) {
		this.classification = aClassification;
	}
	public void setCodeLines(String someCode) {
		this.embeddedConstituent.setCodeLines(someCode);
	}
	public void setCodeLines(String[] someCode) {
		this.embeddedConstituent.setCodeLines(someCode);
	}
	public void setComment(String aComment) {
		this.embeddedConstituent.setComment(aComment);
	}
	public final void setDetector(final IDetector aPatternDetector) {
		this.defaultDetector = aPatternDetector;
	}
	public void setDisplayName(String aName) {
		this.embeddedConstituent.setDisplayName(aName);
	}
	public void setFinal(boolean aBoolean) {
		this.embeddedConstituent.setFinal(aBoolean);
	}
	public void setIntent(final String anIntent) {
		this.intent = anIntent;
	}
	public void setName(char[] aName) {
		this.embeddedConstituent.setName(aName);
	}
	public void setPrivate(boolean aBoolean) {
		this.embeddedConstituent.setPrivate(aBoolean);
	}
	public void setProtected(boolean aBoolean) {
		this.embeddedConstituent.setProtected(aBoolean);
	}
	public void setPublic(boolean aBoolean) {
		this.embeddedConstituent.setPublic(aBoolean);
	}
	public void setStatic(boolean aBoolean) {
		this.embeddedConstituent.setStatic(aBoolean);
	}
	public void setVisibility(int aVisibility) {
		this.embeddedConstituent.setVisibility(aVisibility);
	}
	public void setWeight(int aWeight) {
		this.embeddedConstituent.setWeight(aWeight);
	}
	public void startCloneSession() {
		// Do nothing, see clone();
	}
	public String toString(final int tab) {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append("Model of design motif ");
		codeEq.append(this.getName());
		return codeEq.toString();
	}
	public final Object walk(final IWalker aWalker) {
		if (aWalker instanceof IMotifWalker) {
			((IMotifWalker) aWalker).open(this);
			final Iterator iterator = this.getIteratorOnConstituents();
			while (iterator.hasNext()) {
				((IConstituent) iterator.next()).accept(aWalker);
			}
			((IMotifWalker) aWalker).close(this);
			return aWalker.getResult();
		}
		else if (aWalker instanceof IWalker) {
			return super.walk(aWalker);
		}
		else {
			aWalker.unknownConstituentHandler(
				"Object DesignMotifModel.walk(IWalker)",
				this);
			return null;
		}
	}
}
