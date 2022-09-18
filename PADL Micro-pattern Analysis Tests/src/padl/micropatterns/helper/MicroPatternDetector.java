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
package padl.micropatterns.helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.micropattern.repository.AugmentedTypeDetection;
import padl.micropattern.repository.BoxDetection;
import padl.micropattern.repository.CanopyDetection;
import padl.micropattern.repository.CobolLikeDetection;
import padl.micropattern.repository.CommonStateDetection;
import padl.micropattern.repository.CompoundBoxDetection;
import padl.micropattern.repository.DataManagerDetection;
import padl.micropattern.repository.DesignatorDetection;
import padl.micropattern.repository.ExtenderDetection;
import padl.micropattern.repository.FunctionObjectDetection;
import padl.micropattern.repository.FunctionPointerDetection;
import padl.micropattern.repository.ImmutableDetection;
import padl.micropattern.repository.ImplementorDetection;
import padl.micropattern.repository.JoinerDetection;
import padl.micropattern.repository.OutlineDetection;
import padl.micropattern.repository.OverriderDetection;
import padl.micropattern.repository.PoolDetection;
import padl.micropattern.repository.PseudoClassDetection;
import padl.micropattern.repository.PureTypeDetection;
import padl.micropattern.repository.RecordDetection;
import padl.micropattern.repository.RestrictedCreationDetection;
import padl.micropattern.repository.SamplerDetection;
import padl.micropattern.repository.SinkDetection;
import padl.micropattern.repository.StateLessDetection;
import padl.micropattern.repository.StateMachineDetection;
import padl.micropattern.repository.TaxonomyDetection;
import padl.micropattern.repository.TraitDetection;

/**
 * @author Pierre Leduc & Julien Tanteri
 * @since  2005/02/05
 */

/*
 * A *Major* refactoring is necessary to separate each detection
 * in it's own class.
 */
public final class MicroPatternDetector {
	// MicroPattern detection class
	private AugmentedTypeDetection augmentedType =
		new AugmentedTypeDetection();
	private BoxDetection box = new BoxDetection();
	private CanopyDetection canopy = new CanopyDetection();
	private CobolLikeDetection cobolLike = new CobolLikeDetection();
	private CommonStateDetection commonState = new CommonStateDetection();
	private CompoundBoxDetection compoundBox = new CompoundBoxDetection();
	private DataManagerDetection dataManager = new DataManagerDetection();
	private final DesignatorDetection designator = new DesignatorDetection();
	private final ExtenderDetection extender = new ExtenderDetection();
	private final FunctionObjectDetection functionObject =
		new FunctionObjectDetection();
	private final FunctionPointerDetection functionPointer =
		new FunctionPointerDetection();
	private final ImmutableDetection immutable = new ImmutableDetection();
	private final ImplementorDetection implementor =
		new ImplementorDetection();
	private final JoinerDetection joiner = new JoinerDetection();
	// Holds the total number of classes for statistics.
	private long numberOfClasses = 0;
	private final OutlineDetection outline = new OutlineDetection();
	private final OverriderDetection overrider = new OverriderDetection();
	private final PoolDetection pool = new PoolDetection();
	private final PseudoClassDetection pseudoClass =
		new PseudoClassDetection();
	private final PureTypeDetection pureType = new PureTypeDetection();
	private final RecordDetection record = new RecordDetection();
	private final RestrictedCreationDetection restrictedCreation =
		new RestrictedCreationDetection();
	private final SamplerDetection sampler = new SamplerDetection();
	private final SinkDetection sink = new SinkDetection();
	private final StateLessDetection stateless = new StateLessDetection();
	private final StateMachineDetection stateMachine =
		new StateMachineDetection();
	private final TaxonomyDetection taxonomy = new TaxonomyDetection();
	private final TraitDetection trait = new TraitDetection();

	public MicroPatternDetector(final IAbstractModel anAbstractModel) {
		// Iterate through all class of the model and detect MicroPatters
		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity currentEntity = (IFirstClassEntity) iterator.next();
			if ((currentEntity instanceof IClass)
				|| (currentEntity instanceof IInterface)) {

				this.detectMicroPatterns(currentEntity);
			}
		}
	}

	private void addUnique(final Set destSet, final Set sourceSet) {
		final Iterator iter = sourceSet.iterator();
		while (iter.hasNext()) {
			final Object obj = iter.next();
			if (!destSet.contains(obj)) {
				destSet.add(obj);
			}
		}
	}

	public final void detectMicroPatterns(final IFirstClassEntity anEntity) {
		//		if (! this.Designator.detect(anEntity)) 
		//		if (! this.Taxonormy.detect(anEntity))
		//		if (! this.Joiner.detect(anEntity))
		//		if (! this.Pool.detect(anEntity))
		//		if (! this.FunctionPointer.detect(anEntity))
		//		if (! this.FunctionObject.detect(anEntity))
		//		if (! this.CobolLike.detect(anEntity))
		//		if (! this.Stateless.detect(anEntity))
		//		if (! this.CommonState.detect(anEntity))
		//		if (! this.Immutable.detect(anEntity))
		//		if (! this.RestrictedCreation.detect(anEntity)) 
		//		if (! this.Sampler.detect(anEntity))
		//		if (! this.Box.detect(anEntity))
		//		if (! this.Canopy.detect(anEntity))
		//			this.CompoundBox.detect(anEntity);

		this.designator.detect(anEntity);
		this.taxonomy.detect(anEntity);
		this.joiner.detect(anEntity);
		this.pool.detect(anEntity);
		this.functionPointer.detect(anEntity);
		this.functionObject.detect(anEntity);
		this.cobolLike.detect(anEntity);
		this.stateless.detect(anEntity);
		this.commonState.detect(anEntity);
		this.immutable.detect(anEntity);
		this.restrictedCreation.detect(anEntity);
		this.sampler.detect(anEntity);
		this.box.detect(anEntity);
		this.canopy.detect(anEntity);
		this.compoundBox.detect(anEntity);
		this.record.detect(anEntity);
		this.dataManager.detect(anEntity);
		this.sink.detect(anEntity);
		this.outline.detect(anEntity);
		this.trait.detect(anEntity);
		this.stateMachine.detect(anEntity);
		this.pureType.detect(anEntity);
		this.augmentedType.detect(anEntity);
		this.pseudoClass.detect(anEntity);
		this.implementor.detect(anEntity);
		this.overrider.detect(anEntity);
		this.extender.detect(anEntity);

		this.numberOfClasses++;
	}

	public double getCoverage() {
		final Set coverageSet = new HashSet();

		this.addUnique(coverageSet, this.designator.getEntities());
		this.addUnique(coverageSet, this.taxonomy.getEntities());
		this.addUnique(coverageSet, this.joiner.getEntities());
		this.addUnique(coverageSet, this.pool.getEntities());
		this.addUnique(coverageSet, this.functionPointer.getEntities());
		this.addUnique(coverageSet, this.functionObject.getEntities());
		this.addUnique(coverageSet, this.cobolLike.getEntities());
		this.addUnique(coverageSet, this.stateless.getEntities());
		this.addUnique(coverageSet, this.commonState.getEntities());
		this.addUnique(coverageSet, this.immutable.getEntities());
		this.addUnique(coverageSet, this.restrictedCreation.getEntities());
		this.addUnique(coverageSet, this.sampler.getEntities());
		this.addUnique(coverageSet, this.box.getEntities());
		this.addUnique(coverageSet, this.canopy.getEntities());
		this.addUnique(coverageSet, this.compoundBox.getEntities());
		this.addUnique(coverageSet, this.record.getEntities());
		this.addUnique(coverageSet, this.dataManager.getEntities());
		this.addUnique(coverageSet, this.sink.getEntities());
		this.addUnique(coverageSet, this.outline.getEntities());
		this.addUnique(coverageSet, this.trait.getEntities());
		this.addUnique(coverageSet, this.stateMachine.getEntities());
		this.addUnique(coverageSet, this.pureType.getEntities());
		this.addUnique(coverageSet, this.augmentedType.getEntities());
		this.addUnique(coverageSet, this.pseudoClass.getEntities());
		this.addUnique(coverageSet, this.implementor.getEntities());
		this.addUnique(coverageSet, this.overrider.getEntities());
		this.addUnique(coverageSet, this.extender.getEntities());

		return coverageSet.size() * 100 / this.numberOfClasses;
	}

	/**
	 * @return Returns AugmentedType.
	 */
	public long getNumberOfAugmentedType() {
		return this.augmentedType.getEntities().size();
	}

	/**
	 * @return Returns Box.
	 */
	public int getNumberOfBox() {
		return this.box.getEntities().size();
	}
	/**
	 * @return Returns Canopy.
	 */
	public int getNumberOfCanopy() {
		return this.canopy.getEntities().size();
	}

	/**
	 * @return Returns OtherClass.
	 */
	public long getNumberOfClass() {
		return this.numberOfClasses;
	}
	/**
	 * @return Returns CobolLike.
	 */
	public int getNumberOfCobolLike() {
		return this.cobolLike.getEntities().size();
	}
	/**
	 * @return Returns CommonState.
	 */
	public int getNumberOfCommonState() {
		return this.commonState.getEntities().size();
	}
	/**
	 * @return Returns CompoundBox.
	 */
	public int getNumberOfCompoundBox() {
		return this.compoundBox.getEntities().size();
	}
	/**
	 * @return Returns DataManager.
	 */
	public int getNumberOfDataManager() {
		return this.dataManager.getEntities().size();
	}
	/**
	 * @return Returns Designator.
	 */
	public int getNumberOfDesignator() {
		return this.designator.getEntities().size();
	}
	/**
	 * @return Returns Extender.
	 */
	public int getNumberOfExtender() {
		return this.extender.getEntities().size();
	}
	/**
	 * @return Returns FunctionObject.
	 */
	public int getNumberOfFunctionObject() {
		return this.functionObject.getEntities().size();
	}
	/**
	 * @return Returns FunctionPointer.
	 */
	public int getNumberOfFunctionPointer() {
		return this.functionPointer.getEntities().size();
	}
	/**
	 * @return Returns Immutable.
	 */
	public int getNumberOfImmutable() {
		return this.immutable.getEntities().size();
	}
	/**
	 * @return Returns Implementor.
	 */
	public int getNumberOfImplementor() {
		return this.implementor.getEntities().size();
	}
	/**
	 * @return Returns Joiner.
	 */
	public int getNumberOfJoiner() {
		return this.joiner.getEntities().size();
	}
	/**
	 * @return Returns OutLine.
	 */
	public int getNumberOfOutLine() {
		return this.outline.getEntities().size();
	}
	/**
	 * @return Returns Overrider.
	 */
	public int getNumberOfOverrider() {
		return this.overrider.getEntities().size();
	}
	/**
	 * @return Returns Pool.
	 */
	public int getNumberOfPool() {
		return this.pool.getEntities().size();
	}
	/**
	 * @return Returns PseudoClass.
	 */
	public int getNumberOfPseudoClass() {
		return this.pseudoClass.getEntities().size();
	}
	/**
	 * @return Returns PureType.
	 */
	public int getNumberOfPureType() {
		return this.pureType.getEntities().size();
	}
	/**
	 * @return Returns Record.
	 */
	public int getNumberOfRecord() {
		return this.record.getEntities().size();
	}
	/**
	 * @return Returns RestrictedCreation.
	 */
	public int getNumberOfRestrictedCreation() {
		return this.restrictedCreation.getEntities().size();
	}
	/**
	 * @return Returns Sampler.
	 */
	public int getNumberOfSampler() {
		return this.sampler.getEntities().size();
	}
	/**
	 * @return Returns Sink.
	 */
	public int getNumberOfSink() {
		return this.sink.getEntities().size();
	}
	/**
	 * @return Returns Stateless.
	 */
	public int getNumberOfStateless() {
		return this.stateless.getEntities().size();
	}
	/**
	 * @return Returns StateMachine.
	 */
	public int getNumberOfStateMachine() {
		return this.stateMachine.getEntities().size();
	}
	/**
	 * @return Returns Taxonomy.
	 */
	public int getNumberOfTaxonomy() {
		return this.taxonomy.getEntities().size();
	}
	/**
	 * @return Returns Trait.
	 */
	public int getNumberOfTrait() {
		return this.trait.getEntities().size();
	}
}
