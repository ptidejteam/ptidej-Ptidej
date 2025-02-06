package ca.uqam.latece.aspects.extractor.lattice.graph.model;

import java.io.Serializable;

public class NodeFeatureType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1139024176362154163L;
	private String FeatureTypeName;
	private String anchor;
	private float coverage;
	public String getFeatureTypeName() {
		return FeatureTypeName;
	}
	public void setFeatureTypeName(String featureTypeName) {
		FeatureTypeName = featureTypeName;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public float getCoverage() {
		return coverage;
	}
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}
	@Override
	public String toString() {
		return "NodeFeatureType [FeatureTypeName=" + FeatureTypeName + ", anchor=" + anchor + ", coverage=" + coverage
				+ "]";
	}

	
}
