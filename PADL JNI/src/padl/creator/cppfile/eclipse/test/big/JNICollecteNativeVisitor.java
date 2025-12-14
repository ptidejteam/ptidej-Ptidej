package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;

import com.ibm.toad.cfparse.utils.Access;

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
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IWalker;

@SuppressWarnings("unused")
public class JNICollecteNativeVisitor implements IWalker {
	private final ArrayList<String> listOfNativeMethods = new ArrayList<String>();
	private IClass currentclass;
	private final ArrayList<String> listclass = new ArrayList<String>();

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return this.listOfNativeMethods;
	}

	@Override
	public void open(IClass aClass) {
		// TODO Auto-generated method stub
		this.currentclass = aClass;
		String name1 = aClass.getDisplayName();

		this.listclass.add(name1);

	}

	@Override
	public void open(IMethod aMethod) {
		// TODO Auto-generated method stub
		if (Access.isNative(aMethod.getVisibility())) {
			final String name = aMethod.getDisplayName();
			//+ " " +currentclass.getDisplayName();

			this.listOfNativeMethods.add(name);
		}
	}
	// ********************** NOT USED YET ***********************

	@Override
	public void close(IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IClass aClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IGetter aGetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberClass aMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMethod aMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IPackage aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IPackageDefault aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ISetter aSetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(IAbstractModel anAbstractModel) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IConstructor aConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IDelegatingMethod aDelegatingMethod) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IGetter aGetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IGhost aGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IInterface anInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IMemberClass aMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IMemberGhost aMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IMemberInterface aMemberInterface) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IPackage aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IPackageDefault aPackage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(ISetter aSetter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void unknownConstituentHandler(String aCalledMethodName,
			IConstituent aConstituent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IAggregation anAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IAssociation anAssociation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IComposition aComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IContainerAggregation aContainerAggregation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IContainerComposition aContainerComposition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ICreation aCreation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IField aField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IMethodInvocation aMethodInvocation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IParameter aParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IPrimitiveEntity aPrimitiveEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IUseRelationship aUse) {
		// TODO Auto-generated method stub

	}
}
