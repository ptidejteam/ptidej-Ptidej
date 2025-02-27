package glass.padl.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import glass.ast.IProject;
import glass.ast.IType;
import glass.padl.ast.visitor.ASTVisitor;
import padl.creator.javafile.eclipse.CompleteJavaFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import padl.kernel.impl.Factory;
import padl.generator.helper.ModelGenerator;
import padl.visitor.IWalker;

public class PADLProject implements IProject{
	
	private Collection<IType> definedTypes;
	private Collection<IType> ghostTypes;
	private IIdiomLevelModel model;
	
	public PADLProject(String filePath) {
		
		this.model = ModelGenerator.generateModelFromJavaFilesDirectoryUsingEclipse(filePath);
		this.definedTypes = new ArrayList<IType>();
		this.ghostTypes = new ArrayList<IType>();
		
		/*
		final Iterator<IFirstClassEntity> classIterator = this.model.getIteratorOnTopLevelEntities();
		while (classIterator.hasNext()) {
			IFirstClassEntity entity = classIterator.next();
			//System.out.println(classEntity.getDisplayID());
			if (entity instanceof IClass) {
				definedTypes.add(new PADLClass((IClass) entity, this));
			}
			if (entity instanceof IInterface) {
				definedTypes.add(new PADLInterface((IInterface) entity, this));
			}
			//definedTypes.add(new PADLClass(classEntity, this));
		}
		*/
		
		final ASTVisitor walker = new ASTVisitor(this);
		model.walk(walker);
		
		this.definedTypes = walker.getDefinedTypes();
		this.ghostTypes = walker.getGhostTypes();
		
	}
	
	
	private String getFullyQualifiedName(IFirstClassEntity type) { // Problem : incorrect package
		String[] splitPackages = type.getDisplayPath().split("\\|");
		return splitPackages[splitPackages.length-1];
	}

	@Override
	public Collection<IType> getDefinedTypes() {
		return definedTypes;
	}

	@Override
	public IType findType(String typeName) {
		return Stream.concat(definedTypes.stream(), ghostTypes.stream())
				.filter(t -> t.getFullyQualifiedName().equals(typeName))
				.findFirst()
				.orElse(null);
	}

}
