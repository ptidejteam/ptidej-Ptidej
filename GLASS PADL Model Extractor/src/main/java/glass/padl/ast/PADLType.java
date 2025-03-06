package glass.padl.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import glass.ast.IField;
import glass.ast.IMethod;
import glass.ast.IType;
import padl.kernel.IFirstClassEntity;

abstract public class PADLType implements IType {

	protected IFirstClassEntity padlType;
	private Collection<IField> fields;
	private Collection <IMethod> methods;
	protected PADLProject padlProject;

	public PADLType(IFirstClassEntity padlType, PADLProject padlProject) {
		this.padlType = padlType;
		this.fields = new ArrayList<IField>();
		this.methods = new ArrayList<IMethod>();
		this.padlProject = padlProject;
	}

	public void addField(IField field) {
		this.fields.add(field);
	}

	public void addMethod(IMethod method) {
		this.methods.add(method);
	}


	@Override
	public boolean isAnonymous() {
		// We shouldn't detect anonymous types.
		return false;
	}

	@Override
	abstract public boolean isInterface();

	@Override
	public IMethod[] getMethods() {
		return this.methods.toArray(new IMethod[this.methods.size()]);
	}

	@Override
	public IField[] getFields() {
		return this.fields.toArray(new IField[this.fields.size()]);
	}

	@Override
	public String getElementName() {
		return padlType.getDisplayName();
	}

	@Override
	public String getFullyQualifiedName() {
		String[] splitPackages = this.padlType.getDisplayPath().split("\\|");
		return splitPackages[splitPackages.length - 1];
	}

	@Override
	public String getFullyQualifiedParameterizedName() {
		return this.getFullyQualifiedName();
	}

	@Override
	public String[][] resolveType(String typeName) {
		return null;	// shouldn't be used since PADL already takes care of the resolution
	}

	@Override
	public IType[] getAllSubtypes() {

		ArrayList<IType> subTypes = new ArrayList<IType>();
		final Iterator iterator = this.padlType
				.getIteratorOnInheritingEntities();
		while (iterator.hasNext()) {
			IFirstClassEntity entity = (IFirstClassEntity) iterator.next();
			String[] splitPackages = entity.getDisplayPath().split("\\|");
			String entityName = splitPackages[splitPackages.length - 1];
			IType typeEntity = this.padlProject.findType(entityName);
			if (typeEntity != null) {
				subTypes.add(typeEntity);
				subTypes.addAll(Arrays.asList(typeEntity.getAllSubtypes()));
			}
		}

		IType[] res = new IType[subTypes.size()];
		for (int i = 0; i < subTypes.size(); i++) {
			res[i] = subTypes.get(i);
		}
		return res;
	}

	@Override
	public IType[] getAllSupertypes() {
		ArrayList<IType> superTypes = new ArrayList<IType>();
		final Iterator iterator = this.padlType
				.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			IFirstClassEntity entity = (IFirstClassEntity) iterator.next();
			String[] splitPackages = entity.getDisplayPath().split("\\|");
			String entityName = splitPackages[splitPackages.length - 1];
			IType typeEntity = this.padlProject
					.findType(entityName);
			if (typeEntity != null) {
				superTypes.add(typeEntity);
				superTypes.addAll(Arrays.asList(typeEntity.getAllSupertypes()));
			}
		}

		IType[] res = new IType[superTypes.size()];
		for (int i = 0; i < superTypes.size(); i++) {
			res[i] = superTypes.get(i);
		}
		return res;
	}

	@Override
	public IType[] getImplementingClasses() {
		return null; // only used for interfaces
	}
	

	public boolean containsSameEntity(IFirstClassEntity entity) {
		String[] splitPackages = entity.getDisplayPath().split("\\|");
		String entityName = splitPackages[splitPackages.length - 1];
		return this.getFullyQualifiedName().equals(entityName);
	}
	
	@Override
	public String toString() {
		return this.getElementName();
	}

}
