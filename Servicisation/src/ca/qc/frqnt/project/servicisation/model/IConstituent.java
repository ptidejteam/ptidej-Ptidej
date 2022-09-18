package ca.qc.frqnt.project.servicisation.model;

import java.util.Iterator;

public interface IConstituent {
	String getUniqueName();

	// In-going dependencies
	void addDependencyInGoing(
		String aDependencyType,
		IConstituent aConstituent);
	Iterator<String> getIteratorOnDependencyInGoing(String aDependencyType);
	boolean hasDependencyInGoing(String aDependencyType, String aUniqueName);
	Iterator<String> getIteratorOnDependenciesTypesInGoing(
		String aDependencyType);

	// Out-going dependencies
	void addDependencyOutGoing(
		String aDependencyType,
		IConstituent aConstituent);
	Iterator<String> getIteratorOnDependencyOutGoing(String aDependencyType);
	boolean hasDependencyOutGoing(String aDependencyType, String aUniqueName);
	Iterator<String> getIteratorOnDependenciesTypesOutGoing(
		String aDependencyType);

	// Namespaces
	void addNameSpace(String aNameSpaceName);
	Iterator<String> getIteratorOnNameSpaces();
	boolean doesBelongToNameSpace(String aNameSpaceName);

	// Types
	void addType(String aTypeName);
	Iterator<String> getIteratorOnTypes();
	boolean isOfType(String aTypeName);
}