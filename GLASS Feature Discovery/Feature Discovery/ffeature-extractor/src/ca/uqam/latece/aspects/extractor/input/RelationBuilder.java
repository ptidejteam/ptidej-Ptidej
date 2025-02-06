package ca.uqam.latece.aspects.extractor.input;

import ca.uqam.latece.aspects.extractor.lattice.model.Relation;

import org.eclipse.core.resources.IProject;

public interface RelationBuilder {
	
	public Relation buildRelationFrom(IProject aJavaProject);

}
