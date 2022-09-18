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
package mendel.family;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import mendel.model.JClassEntity;
import mendel.model.SetOps;

import org.jfree.data.statistics.BoxAndWhiskerCalculator;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.Statistics;

/**
 * @author Simon Denier
 * @since Sep 12, 2008
 *
 */
public class FamilyHomogeneity {

	public class FamilyResults {
		private double jaccardMean;
		private double jaccardDev;
		private double hammingMedian;
		private double hammingIQR;
//		private int hammingLimit;
		private int nbOutliers;
		private PrototypeTag tag;
		
		public PrototypeTag getFamilyCategory() {
			return this.tag;
		}
		public double getJaccardMean() {
			return this.jaccardMean;
		}
		public double getJaccardDev() {
			return this.jaccardDev;
		}
		public double getHammingMedian() {
			return this.hammingMedian;
		}
		public double getHammingIQR() {
			return this.hammingIQR;
		}
//		public int getHammingLimit() {
//			return this.hammingLimit;
//		}
		public int getNbOutliers() {
			return this.nbOutliers;
		}		
	}
	
	public enum PrototypeTag {
		Pure, Homogeneous, HomogeneousOutlier, Heterogeneous, HeterogeneousScattered, NullPrototype  
	}
	
	public FamilyResults computeFamilyStats(Family family, Prototype prototype) {
		FamilyResults stats = basicFamilyStats(family, prototype);
		stats.tag = tag(stats, prototype);
		return stats;
	}
	
	public PrototypeTag tag(Family family, Prototype prototype) {
		return tag(basicFamilyStats(family, prototype), prototype);
	}

	public PrototypeTag tag(FamilyResults results, Prototype prototype) {
		if( prototype.getInterface().size()==0 ) {
			return PrototypeTag.NullPrototype;
		}
		if( results.getJaccardMean()==1 ) {
			return PrototypeTag.Pure;
		}
		if( results.getJaccardMean()>0.5 ) {
			if( results.getJaccardDev()<0.3 ) {
				return PrototypeTag.Homogeneous;
			} else {
				return PrototypeTag.HomogeneousOutlier;
			}
		}
		
		if( results.getJaccardDev()<0.3 ) {
			return PrototypeTag.Heterogeneous;
		} else {
			return PrototypeTag.HeterogeneousScattered;
		}
	}
	
	public FamilyResults basicFamilyStats(Family family, Prototype prototype) {
		FamilyResults results = new FamilyResults();
		double[] jaccardStats = jaccardStats(family, prototype);
		results.jaccardMean = jaccardStats[0];
		results.jaccardDev = jaccardStats[1];
		double[] hammingStats = hammingStats(family, prototype);
		results.hammingMedian = hammingStats[0];
		results.hammingIQR = hammingStats[1];
//		results.nbOutliers = nbOutliers(prototype.getHammingDistances());
		results.nbOutliers = nbOutliers(getJaccardIndex(family, prototype));
		return results;
	}
	
	public Vector<Float> getJaccardIndex(Family family, Prototype prototype) {
		Vector<Float> jaccards = new Vector<Float>();
		for (JClassEntity child : family.children()) {
			jaccards.add(SetOps.jaccardIndex(child.getLocalMethods(), prototype.getInterface()));
		}
		return jaccards;
	}
	
	public double[] jaccardStats(Family family, Prototype prototype) {
		return meanStats(getJaccardIndex(family, prototype));
	}
	
	public double[] meanStats(Collection<? extends Number> values) {
		double[] stats = new double[2];
		stats[0] = Statistics.calculateMean(values);
		stats[1] = Statistics.getStdDev(values.toArray(new Number[0]));
		return stats;
	}

	
	public double[] hammingStats(Family family, Prototype prototype) {
		Vector<Integer> distances = prototype.getHammingDistances();
		return medStats(distances);
	}
	
	public double[] medStats(List<? extends Number> values) {
		double[] stats = new double[2];
		BoxAndWhiskerItem calcul = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(values);
		stats[0] = calcul.getMedian().doubleValue();
		stats[1] = calcul.getQ3().doubleValue() - calcul.getQ1().doubleValue();
		return stats;
	}

	public <E extends Number> int nbDev(Vector<E> distances) {
		double stdDev = Statistics.getStdDev(distances.toArray(new Number[0]));
		int outliers = 0;
		for (E distance : distances) {
			if( distance.doubleValue() > stdDev ) {
				outliers++;
			}
		}
		return outliers;
	}
	
	public <E extends Number> int nbOutliers(Vector<E> distances) {
//		Vector<Integer> distances = prototype.getHammingDistances();
		BoxAndWhiskerItem calcul = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(distances);
		double limit = calcul.getQ3().doubleValue() + 1.5 * (calcul.getQ3().doubleValue() - calcul.getQ1().doubleValue());
		int outliers = 0;
		for (E distance : distances) {
			if( distance.doubleValue() > limit ) {
				outliers++;
			}
		}
		return outliers;
	}

	
	public Set<JClassEntity>[] inclusions(Family family, Prototype prototype) {
		Set<JClassEntity>[] sets = new Set[5];
		for (int i = 0; i < sets.length; i++) {
			sets[i] = new HashSet<JClassEntity>();
		}
		Set<String> protoIt = prototype.getInterface();
		for (JClassEntity entity : family.children()) {
			Set<String> itface = entity.getLocalMethods();
			if( protoIt.equals(itface) ) {
				sets[0].add(entity);
			} else if( protoIt.containsAll(itface) ) {
				sets[1].add(entity);
			} else if( itface.containsAll(protoIt) ) {
				sets[2].add(entity);
			} else if( itface.retainAll(protoIt) ){
				sets[3].add(entity);
			} else {
				sets[4].add(entity);
			}
		}
		return sets;
	}

}
