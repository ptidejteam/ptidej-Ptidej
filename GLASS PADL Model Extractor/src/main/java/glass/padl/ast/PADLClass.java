package glass.padl.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import glass.ast.IType;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;


public class PADLClass extends PADLType{

	public PADLClass(IClass padlType, PADLProject padlProject) {
		super(padlType, padlProject);
	}

	@Override
	public boolean isInterface() {
		return false;
	}
	
	private IType[] getAllSuperInterfaces() {
		IClass padlClass = (IClass) padlType;
		ArrayList<IType> superInterfaces = new ArrayList<IType>();
		final Iterator iterator = padlClass.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			IFirstClassEntity entity = (IFirstClassEntity) iterator.next();
			String[] splitPackages = entity.getDisplayPath().split("\\|");
			String entityName = splitPackages[splitPackages.length - 1];
			IType typeEntity = this.padlProject
					.findType(entityName);
			if (typeEntity != null) {
				superInterfaces.add(typeEntity);
				superInterfaces.addAll(Arrays.asList(typeEntity.getAllSupertypes()));
			}
		}
		IType[] res = new IType[superInterfaces.size()];
		for (int i = 0; i < superInterfaces.size(); i++) {
			res[i] = superInterfaces.get(i);
		}
		return res;
	}
	
	@Override
	public IType[] getAllSupertypes() {
		List<IType> superTypes = new ArrayList<IType>();
		superTypes.addAll(Arrays.asList(this.getAllSuperInterfaces()));
		superTypes.addAll(Arrays.asList(super.getAllSupertypes()));
		return superTypes.toArray(new IType[0]);
	}
	
}
