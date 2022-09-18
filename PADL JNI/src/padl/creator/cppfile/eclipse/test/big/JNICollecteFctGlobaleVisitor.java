package padl.creator.cppfile.eclipse.test.big;

import java.util.ArrayList;
import java.util.Iterator;
import padl.cpp.kernel.ICPPClass;
import padl.cpp.kernel.ICPPGhost;
import padl.cpp.kernel.ICPPMemberClass;
import padl.cpp.kernel.ICPPMemberGhost;
import padl.cpp.kernel.IDestructor;
import padl.cpp.kernel.IEnum;
import padl.cpp.kernel.IEnumValue;
import padl.cpp.kernel.IGlobalField;
import padl.cpp.kernel.IGlobalFunction;
import padl.cpp.kernel.IMemberStructure;
import padl.cpp.kernel.IStructure;
import padl.cpp.kernel.IUnion;
import padl.cpp.visitor.ICPPWalker;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import padl.util.adapter.WalkerAdapter;

public class JNICollecteFctGlobaleVisitor extends WalkerAdapter
		implements ICPPWalker {

	final ArrayList<String> listOfJNIMethods = new ArrayList<String>();
	final ArrayList<String> listOfcppclasss = new ArrayList<String>();
	final ArrayList<String> listOfcurrentglobalfunction =
		new ArrayList<String>();
	final ArrayList<String> listOfcurrentmethod = new ArrayList<String>();
	final ArrayList<String> listOfcurrentparameter = new ArrayList<String>();

	@Override
	public Object getResult() {
		return this.listOfJNIMethods;
	}

	@Override
	public void open(final IClass aClass) {
	}

	@Override
	public void open(final IGlobalFunction aGlobalFunction) {
		this.listOfcurrentglobalfunction.add(aGlobalFunction.getDisplayName());

		final Iterator<?> iterator =
			aGlobalFunction.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			final IConstituent constituent = (IConstituent) iterator.next();
			if (constituent instanceof IParameter) {
				final IParameter parameter = (IParameter) constituent;
				if (parameter.getDisplayName().startsWith("env") && parameter
					.getDisplayTypeName()
					.startsWith("ProblemType")) {

					this.listOfJNIMethods.add(aGlobalFunction.getDisplayName());
				}
			}
		}
	}

	@Override
	public void open(final ICPPClass aCPPClass) {
		this.listOfcppclasss.add(aCPPClass.getDisplayName());
	}

	@Override
	public void open(final IMethod aMethod) {
	}

	@Override
	public void visit(final IParameter aParameter) {
	}

	@Override
	public void close(ICPPClass aCPPClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ICPPGhost aCPPGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ICPPMemberClass aCPPMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(ICPPMemberGhost aCPPMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IDestructor aDestructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IEnum anEnum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IGlobalField aGlobalField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IGlobalFunction aGlobalFunction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IMemberStructure aMemberStructure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IStructure aStruct) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(IUnion anUnion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(ICPPGhost aCPPGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(ICPPMemberClass aCPPMemberClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(ICPPMemberGhost aCPPMemberGhost) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IDestructor aDestructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IEnum anEnum) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IGlobalField aGlobalField) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IMemberStructure aMemberStructure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IStructure aStruct) {
		// TODO Auto-generated method stub

	}

	@Override
	public void open(IUnion anUnion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IEnumValue anEnumValue) {
		// TODO Auto-generated method stub

	}
}
