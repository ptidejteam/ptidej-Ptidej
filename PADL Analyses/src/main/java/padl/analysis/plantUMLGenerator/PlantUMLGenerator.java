package padl.analysis.plantUMLGenerator;

import java.util.Iterator;

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
import padl.kernel.IGeneralisation;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.IPackageDefault;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;

/**
 * @author Vishnu Rameshbabu
 * @since 2024/05/07
 */
public class PlantUMLGenerator implements IGenerator {

	protected final StringBuffer plantUMLBuilder = new StringBuffer();
	protected final StringBuffer plantUMLBuilderRelationship = new StringBuffer();
	private IFirstClassEntity currentEntity;

	public PlantUMLGenerator() {
		this.plantUMLBuilder.append("\n@startuml\n");
	}


	public String getCode() {

		return this.plantUMLBuilder.toString();
	}

	public void visit(IFirstClassEntity entity) {

		this.plantUMLBuilder.append("}\n");

	}

	public void visit(IRelationship relationship) {
	}


	public void visit(final IAggregation aggregation) {
		if (currentEntity != null) {
			this.plantUMLBuilderRelationship.append(currentEntity.getName()).append(" o-- ")
					.append(aggregation.getTargetEntity().getName()).append(' ').append(": aggregation\n");
		}
	}


	public void visit(IAssociation association) {
		if (currentEntity != null) {
			plantUMLBuilderRelationship.append("\n").append(currentEntity.getName()).append(" -- ")
					.append(association.getTargetEntity().getName()).append("\n");
		}

	}


	public void visit(IComposition composition) {
		if (currentEntity != null) {
			plantUMLBuilderRelationship.append("\n").append(currentEntity.getName()).append(" *-- ")
					.append(composition.getTargetEntity().getName()).append("\n");
		}

	}

	public void visit(ICreation aCreation) {

	}


	public void visit(IField aField) {

	}


	public void visit(IMethodInvocation aMethodInvocation) {

	}


	public void visit(IParameter aParameter) {

	}


	public void open(IAbstractModel model) {

	}


	public void close(IAbstractModel model) {
		this.plantUMLBuilder.append("\n" + this.plantUMLBuilderRelationship.toString() + "\n");
		this.plantUMLBuilder.append("@enduml");
	}


	public void open(IClass cls) {
		currentEntity = cls;
		String className = String.valueOf(cls.getName());
		plantUMLBuilder.append("\n");
		if (cls.isAbstract()) {
			plantUMLBuilder.append("abstract ");
		}
		this.plantUMLBuilder.append("class " + className + " {");
		plantUMLBuilder.append("\n");
		Iterator  iterator = cls.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {
				IFirstClassEntity entity = (IFirstClassEntity) iterator.next();
				if (String.valueOf(entity.getName()).equals("Object")) {
					continue;
				}
				this.plantUMLBuilderRelationship.append("\n");
				this.plantUMLBuilderRelationship.append(entity.getName());
				this.plantUMLBuilderRelationship.append(" --^ ");
				this.plantUMLBuilderRelationship.append(className);

				if (iterator.hasNext()) {
					this.plantUMLBuilderRelationship.append("\n");

				}
			}
		}

		iterator = cls.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {
				this.plantUMLBuilderRelationship.append("\n");
				this.plantUMLBuilderRelationship.append(((IFirstClassEntity) (iterator.next())).getName());

				this.plantUMLBuilderRelationship.append(" ..^ ");
				this.plantUMLBuilderRelationship.append(className);
				if (iterator.hasNext()) {
					this.plantUMLBuilderRelationship.append("\n");

				}
			}
		}

	}


	public void close(IClass cls) {
		currentEntity = null;
		this.plantUMLBuilder.append("\n}\n");
	}


	public void open(IInterface iInterface) {

		String interfaceName = String.valueOf(iInterface.getName());

		this.plantUMLBuilder.append("\n");
		this.plantUMLBuilder.append("interface " + interfaceName + " {\n");

		Iterator  iterator = iInterface.getIteratorOnConstituents();
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {
				IConstituent constituent = (IConstituent) iterator.next();
				System.out.println(constituent);
			}

		}
		this.plantUMLBuilderRelationship.append("\n");

		iterator = iInterface.getIteratorOnInheritingEntities();
		if (iterator.hasNext()) {
			while (iterator.hasNext()) {

				IFirstClassEntity entity = (IFirstClassEntity) iterator.next();

				this.plantUMLBuilderRelationship.append("\n");
				this.plantUMLBuilderRelationship.append(interfaceName);
				this.plantUMLBuilderRelationship.append(" --^ ");
				this.plantUMLBuilderRelationship.append(entity.getName());
				if (iterator.hasNext()) {
					this.plantUMLBuilderRelationship.append("\n");
				}
			}
		}
	}


	public void close(IInterface iface) {

		this.plantUMLBuilder.append("\n}\n");
	}


	public void open(IMethod method) {

	}


	public void close(IMethod method) {

	}


	public void open(IPackage pkg) {

	}


	public void close(IPackage pkg) {

	}


	public void close(IConstructor aConstructor) {

	}


	public void close(IDelegatingMethod aDelegatingMethod) {

	}


	public void close(IGetter aGetter) {

	}


	public void close(IGhost aGhost) {

	}


	public void close(IMemberClass aMemberClass) {

	}


	public void close(IMemberGhost aMemberGhost) {

		// plantUMLBuilder.append("// close member ghost
		// ").append(aMemberGhost.getDisplayName()).append("\n");
	}


	public void close(IMemberInterface aMemberInterface) {

	}


	public void close(IPackageDefault aPackage) {

	}


	public void close(ISetter aSetter) {

	}


	public void open(IConstructor aConstructor) {

	}


	public void open(IDelegatingMethod aDelegatingMethod) {

	}


	public void open(IGetter aGetter) {

	}


	public void open(IGhost aGhost) {

	}


	public void open(IMemberClass aMemberClass) {

	}


	public String getName() {
		return "PlantUMLGenerator";
	}


	public void open(IMemberGhost aMemberGhost) {

	}


	public void open(IMemberInterface aMemberInterface) {

	}


	public void open(IPackageDefault aPackage) {

	}


	public void open(ISetter aSetter) {

	}


	public void reset() {
		plantUMLBuilder.setLength(0);

	}


	public void visit(IContainerAggregation aContainerAggregation) {

	}


	public void visit(IContainerComposition aContainerComposition) {

	}


	public void visit(IPrimitiveEntity aPrimitiveEntity) {
	}


	public void visit(IUseRelationship aUse) {

	}


	public final void unknownConstituentHandler(final String aCalledMethodName, final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput().print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput().print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput().print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public Object getResult() {

		return plantUMLBuilder.toString();
	}
}