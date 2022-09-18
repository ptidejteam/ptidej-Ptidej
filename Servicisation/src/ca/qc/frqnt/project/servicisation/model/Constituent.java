package ca.qc.frqnt.project.servicisation.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import ca.qc.frqnt.project.servicisation.util.NullIterator;

public class Constituent implements IConstituent {
	private final String uniqueName;
	private final Map<String, Set<String>> dependenciesInGoing;
	private final Map<String, Set<String>> dependenciesOutGoing;
	private final Set<String> nameSpaces;
	private final Set<String> types;

	public Constituent(final String aUniqueName) {
		this.uniqueName = aUniqueName;
		this.dependenciesInGoing = new HashMap<String, Set<String>>(0);
		this.dependenciesOutGoing = new HashMap<String, Set<String>>(0);
		this.nameSpaces = new HashSet<String>(0);
		this.types = new HashSet<String>();
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getUniqueName()
	 */
	@Override
	public String getUniqueName() {
		return this.uniqueName;
	}

	// In-going dependencies
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#addDependencyInGoing(java.lang.String, ca.qc.frqnt.project.servicisation.model.IConstituent)
	 */
	@Override
	public void addDependencyInGoing(
		final String aDependencyType,
		final IConstituent aConstituent) {

		Set<String> setOfUniqueNames =
			this.dependenciesInGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			setOfUniqueNames = new HashSet<String>();
			this.dependenciesInGoing.put(aDependencyType, setOfUniqueNames);
		}
		setOfUniqueNames.add(aConstituent.getUniqueName());
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnDependencyInGoing(java.lang.String)
	 */
	@Override
	public Iterator<String> getIteratorOnDependencyInGoing(
		final String aDependencyType) {

		final Set<String> setOfUniqueNames =
			this.dependenciesInGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			return new NullIterator<String>();
		}
		return setOfUniqueNames.iterator();
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#hasDependencyInGoing(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hasDependencyInGoing(
		final String aDependencyType,
		final String aUniqueName) {

		final Set<String> setOfUniqueNames =
			this.dependenciesInGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			return false;
		}
		return setOfUniqueNames.contains(aUniqueName);
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnDependenciesTypesInGoing(java.lang.String)
	 */
	@Override
	public Iterator<String> getIteratorOnDependenciesTypesInGoing(
		final String aDependencyType) {

		return this.dependenciesInGoing.keySet().iterator();
	}

	// Out-going dependencies
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#addDependencyOutGoing(java.lang.String, ca.qc.frqnt.project.servicisation.model.IConstituent)
	 */
	@Override
	public void addDependencyOutGoing(
		final String aDependencyType,
		final IConstituent aConstituent) {

		Set<String> setOfUniqueNames =
			this.dependenciesOutGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			setOfUniqueNames = new HashSet<String>();
			this.dependenciesOutGoing.put(aDependencyType, setOfUniqueNames);
		}
		setOfUniqueNames.add(aConstituent.getUniqueName());
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnDependencyOutGoing(java.lang.String)
	 */
	@Override
	public Iterator<String> getIteratorOnDependencyOutGoing(
		final String aDependencyType) {

		final Set<String> setOfUniqueNames =
			this.dependenciesOutGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			return new NullIterator<String>();
		}
		return setOfUniqueNames.iterator();
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#hasDependencyOutGoing(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean hasDependencyOutGoing(
		final String aDependencyType,
		final String aUniqueName) {

		final Set<String> setOfUniqueNames =
			this.dependenciesOutGoing.get(aDependencyType);
		if (setOfUniqueNames == null) {
			return false;
		}
		return setOfUniqueNames.contains(aUniqueName);
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnDependenciesTypesOutGoing(java.lang.String)
	 */
	@Override
	public Iterator<String> getIteratorOnDependenciesTypesOutGoing(
		final String aDependencyType) {

		return this.dependenciesOutGoing.keySet().iterator();
	}

	// Namespaces
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#addNameSpace(java.lang.String)
	 */
	@Override
	public void addNameSpace(final String aNameSpaceName) {
		this.nameSpaces.add(aNameSpaceName);
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnNameSpaces()
	 */
	@Override
	public Iterator<String> getIteratorOnNameSpaces() {
		return this.nameSpaces.iterator();
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#doesBelongToNameSpace(java.lang.String)
	 */
	@Override
	public boolean doesBelongToNameSpace(final String aNameSpaceName) {
		return this.nameSpaces.contains(aNameSpaceName);
	}

	// Types
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#addType(java.lang.String)
	 */
	@Override
	public void addType(final String aTypeName) {
		this.types.add(aTypeName);
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#getIteratorOnTypes()
	 */
	@Override
	public Iterator<String> getIteratorOnTypes() {
		return this.types.iterator();
	}
	/* (non-Javadoc)
	 * @see ca.qc.frqnt.project.servicisation.model.IConstituent#isOfType(java.lang.String)
	 */
	@Override
	public boolean isOfType(final String aTypeName) {
		return this.types.contains(aTypeName);
	}

	// Misc
	@Override
	public String toString() {
		return super.toString();
	}
}
