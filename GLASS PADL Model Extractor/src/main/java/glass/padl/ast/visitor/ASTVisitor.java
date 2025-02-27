package glass.padl.ast.visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import glass.ast.IType;
import glass.padl.ast.PADLClass;
import glass.padl.ast.PADLField;
import glass.padl.ast.PADLGhost;
import glass.padl.ast.PADLInterface;
import glass.padl.ast.PADLMethod;
import glass.padl.ast.PADLProject;
import glass.padl.ast.PADLType;
import padl.kernel.IAbstractModel;
import padl.kernel.IAggregation;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IComposition;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IContainerAggregation;
import padl.kernel.IContainerComposition;
import padl.kernel.ICreation;
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;

public class ASTVisitor implements IWalker {

	private PADLProject padlProject;

	private Collection<IType> definedTypes = new ArrayList<IType>();
	private Collection<IType> ghostTypes = new ArrayList<IType>();
	private Stack<PADLType> typeStack;

	public ASTVisitor(PADLProject padlProject) {
		this.padlProject = padlProject;
		this.typeStack = new Stack<PADLType>();
	}

	private void closeType() {
		PADLType padlType = this.typeStack.pop();
		definedTypes.add(padlType);
	}
	
	private void addField(IUseRelationship field) {
		PADLType currentType = this.typeStack.lastElement();
		IFirstClassEntity entity = field.getTargetEntity();
		String[] fieldPath = field.getDisplayPath().split("\\|");
		String fieldLocation = fieldPath[fieldPath.length-1].split("_>PADL<_")[0];
		//System.out.println(field.getDisplayPath());
		//System.out.println(field.getTargetEntity().getDisplayName());
		if (!currentType.containsSameEntity(entity)) {
			PADLField padlField = new PADLField(field, fieldLocation);
			currentType.addField(padlField);	
		}
	}
	
	private void addMethod(IMethod method) {
		PADLType currentType = this.typeStack.lastElement();
		method.setStatic(false);
		currentType.addMethod(new PADLMethod(method));
	}
	
	@Override
	public void close(IAbstractModel arg0) {
	}
	
	@Override
	public void close(IClass arg0) {
		this.closeType();
	}

	@Override
	public void close(IConstructor arg0) {
	}

	@Override
	public void close(IDelegatingMethod arg0) {
	}

	@Override
	public void close(IGetter arg0) {
	}

	@Override
	public void close(IGhost arg0) {
		PADLType padlGhost = this.typeStack.pop();
		ghostTypes.add(padlGhost);
	}

	@Override
	public void close(IInterface arg0) {
		this.closeType();
	}

	@Override
	public void close(IMemberClass arg0) {
		this.closeType();
	}

	@Override
	public void close(IMemberGhost arg0) {
		PADLType padlGhost = this.typeStack.pop();
		ghostTypes.add(padlGhost);
	}

	@Override
	public void close(IMemberInterface arg0) {
		this.closeType();
	}

	@Override
	public void close(IMethod arg0) {
	}

	@Override
	public void close(IPackage arg0) {
	}

	@Override
	public void close(IPackageDefault arg0) {
	}

	@Override
	public void close(ISetter arg0) {
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(IAbstractModel arg0) {
	}

	@Override
	public void open(IClass arg0) {
		this.typeStack.push(new PADLClass(arg0, this.padlProject));
	}

	@Override
	public void open(IConstructor arg0) {
		// Do nothing, as constructors aren't useful
	}

	@Override
	public void open(IDelegatingMethod arg0) {
		this.addMethod(arg0);
	}

	@Override
	public void open(IGetter arg0) {
		this.addMethod(arg0);
	}

	@Override
	public void open(IGhost arg0) {
		this.typeStack.push(new PADLGhost(arg0, this.padlProject));
	}

	@Override
	public void open(IInterface arg0) {
		this.typeStack.push(new PADLInterface(arg0, this.padlProject));
	}

	@Override
	public void open(IMemberClass arg0) {
		this.typeStack.push(new PADLClass(arg0, this.padlProject));
	}

	@Override
	public void open(IMemberGhost arg0) {
		this.typeStack.push(new PADLGhost(arg0, this.padlProject));
	}

	@Override
	public void open(IMemberInterface arg0) {
		this.typeStack.push(new PADLInterface(arg0, this.padlProject));
	}

	@Override
	public void open(IMethod arg0) {
		this.addMethod(arg0);
	}

	@Override
	public void open(IPackage arg0) {
	}

	@Override
	public void open(IPackageDefault arg0) {
	}

	@Override
	public void open(ISetter arg0) {
		this.addMethod(arg0);
	}

	@Override
	public void reset() {
		definedTypes = new ArrayList<IType>();
	}

	@Override
	public void unknownConstituentHandler(String arg0, IConstituent arg1) {
	}

	@Override
	public void visit(IAggregation arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(IAssociation arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(IComposition arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(IContainerAggregation arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(IContainerComposition arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(ICreation arg0) {
		this.addField(arg0);
	}

	@Override
	public void visit(IField arg0) {
	}

	@Override
	public void visit(IMethodInvocation arg0) {
	}

	@Override
	public void visit(IParameter arg0) {
	}

	@Override
	public void visit(IPrimitiveEntity arg0) {
	}

	@Override
	public void visit(IUseRelationship arg0) {
		this.addField(arg0);
	}

	@Override
	public Object getResult() {
		return definedTypes;
	}
	
	public Collection<IType> getDefinedTypes() {
		return definedTypes;
	}
	
	public Collection<IType> getGhostTypes() {
		return ghostTypes;
	}

}
