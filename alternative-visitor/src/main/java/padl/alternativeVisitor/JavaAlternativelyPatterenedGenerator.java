package padl.alternativeVisitor;

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
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;

public class JavaAlternativelyPatterenedGenerator implements IGenerator {

	public void close(IAbstractModel anAbstractModel) {
	}

	public void close(IClass aClass) {
	}

	public void close(IConstructor aConstructor) {
	}

	public void close(IDelegatingMethod aDelegatingMethod) {
	}

	public void close(IGetter aGetter) {
	}

	public void close(IGhost aGhost) {
	}

	public void close(IInterface anInterface) {

	}

	public void close(IMemberClass aMemberClass) {

	}

	public void close(IMemberGhost aMemberGhost) {

	}

	public void close(IMemberInterface aMemberInterface) {

	}

	public void close(IMethod aMethod) {

	}

	public void close(IPackage aPackage) {

	}

	public void close(IPackageDefault aPackage) {

	}

	public void close(IPackageGhost aPackageGhost) {

	}

	public void close(ISetter aSetter) {

	}

	public String getName() {

		return null;
	}

	public void open(IAbstractModel anAbstractModel) {

	}

	public void open(IClass aClass) {

	}

	public void open(IConstructor aConstructor) {

	}

	public void open(IDelegatingMethod aDelegatingMethod) {

	}

	public void open(IGetter aGetter) {

	}

	public void open(IGhost aGhost) {

	}

	public void open(IInterface anInterface) {

	}

	public void open(IMemberClass aMemberClass) {

	}

	public void open(IMemberGhost aMemberGhost) {

	}

	public void open(IMemberInterface aMemberInterface) {

	}

	public void open(IMethod aMethod) {

	}

	public void open(IPackage aPackage) {

	}

	public void open(IPackageDefault aPackage) {

	}

	public void open(IPackageGhost aPackageGhost) {

	}

	public void open(ISetter aSetter) {

	}

	public void reset() {

	}

	public void unknownConstituentHandler(String aCalledMethodName, IConstituent aConstituent) {

	}

	public void visit(IAggregation anAggregation) {

	}

	public void visit(IAssociation anAssociation) {

	}

	public void visit(IComposition aComposition) {

	}

	public void visit(IContainerAggregation aContainerAggregation) {

	}

	public void visit(IContainerComposition aContainerComposition) {

	}

	public void visit(ICreation aCreation) {

	}

	public void visit(IField aField) {

	}

	public void visit(IMethodInvocation aMethodInvocation) {

	}

	public void visit(IParameter aParameter) {

	}

	public void visit(IPrimitiveEntity aPrimitiveEntity) {

	}

	public void visit(IUseRelationship aUse) {

	}

	public String getCode() {

		return null;
	}

}
