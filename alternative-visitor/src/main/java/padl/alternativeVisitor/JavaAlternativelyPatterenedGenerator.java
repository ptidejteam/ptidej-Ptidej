package padl.alternativeVisitor;

import java.util.Iterator;

import com.ibm.toad.cfparse.utils.Access;

import padl.kernel.Cardinality;
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
import padl.kernel.IPackageGhost;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.util.Util;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;


public class JavaAlternativelyPatterenedGenerator implements IGenerator {
	private final StringBuffer buffer = new StringBuffer();
	private int indentation = 0;

	public void close(IAbstractModel anAbstractModel) {
	}

	public void close(IClass aClass) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');

		this.indentation--;
	}

	public void close(IConstructor aConstructor) {
	}

	public void close(IDelegatingMethod aDelegatingMethod) {
	}

	public void close(IGetter aGetter) {
	}

	public void close(IGhost aGhost) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation--;
	}

	public void close(IInterface anInterface) {
		this.buffer.append('\n');
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation--;
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

		return "Java";
	}

	public void open(IAbstractModel anAbstractModel) {
		this.reset();

		final Iterator it = anAbstractModel.getIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IPackageGhost packageGhostConstituent) {
				this.open(packageGhostConstituent);
				this.close(packageGhostConstituent);
			}
			
			else if (constituent instanceof IPackageDefault packageDefaultConstituent) {
				this.open(packageDefaultConstituent);
				this.close(packageDefaultConstituent);
			}
			
			else if (constituent instanceof IPackage packageConstituent) {
				this.open(packageConstituent);
				this.close(packageConstituent);
			}
		}

	}

	public void open(IClass aClass) {
		this.commentsAndVisibility(aClass);
		this.buffer.append(" class ");
		this.buffer.append(aClass.getName());
		this.inheritedEntities(aClass.getIteratorOnInheritedEntities());
		Iterator iterator = aClass.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			this.buffer.append(" implements ");
			this.namesOfEntities(iterator);
		}
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');
		this.buffer.append('\n');

		this.indentation++;

		final Iterator it = aClass.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberClass memberClassConstituent) {
				this.open(memberClassConstituent);
				this.close(memberClassConstituent);
			}
			
			else if (constituent instanceof IMemberInterface memberInterfaceConstituent) {
				this.open(memberInterfaceConstituent);
				this.close(memberInterfaceConstituent);
			}
			
			else if (constituent instanceof IDelegatingMethod delegatingMethodConstituent) {
				this.open(delegatingMethodConstituent);
				this.close(delegatingMethodConstituent);
			}
			
			else if (constituent instanceof IGetter getterConstituent) {
				this.open(getterConstituent);
				this.close(getterConstituent);
			}
			
			else if (constituent instanceof ISetter setterConstituent) {
				this.open(setterConstituent);
				this.close(setterConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
			
			else if (constituent instanceof IContainerComposition containerCompositionConstituent) {
				this.visit(containerCompositionConstituent);
			}
			
			else if (constituent instanceof IContainerAggregation containerAggregationConstituent) {
				this.visit(containerAggregationConstituent);
			}
			
			else if (constituent instanceof IComposition compositionConstituent) {
				this.visit(compositionConstituent);
			}
			
			else if (constituent instanceof IAggregation aggregationConstituent) {
				this.visit(aggregationConstituent);
			}
			
			else if (constituent instanceof IAssociation associationConstituent) {
				this.visit(associationConstituent);
			}
			
			else if (constituent instanceof ICreation creationConstituent) {
				this.visit(creationConstituent);
			}
			
			else if (constituent instanceof IUseRelationship useRelationshipConstituent) {
				this.visit(useRelationshipConstituent);
			}
		}
	}

	public void open(IConstructor aConstructor) {
		this.commentsAndVisibility(aConstructor);
		this.buffer.append(' ');
		this.nameAndParameters(aConstructor);

		final Iterator it = aConstructor.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IParameter parameterConstituent) {
				this.visit(parameterConstituent);
			}
			
			else if (constituent instanceof IMethodInvocation methodInvocationConstituent) {
				this.visit(methodInvocationConstituent);
			}
		}
	}

	public void open(IDelegatingMethod aDelegatingMethod) {
		if (aDelegatingMethod.getTargetAssoc().getCardinality() == Cardinality.Many) {
			aDelegatingMethod.setCodeLines(
				"for (java.util.Enumeration enum = "
						+ aDelegatingMethod.getTargetAssoc().getDisplayName()
						+ ".elements(); enum.hasMoreElements(); (("
						+ aDelegatingMethod.getTargetAssoc().getTargetEntity()
							.getDisplayID()
						+ ") enum.nextElement())."
						+ aDelegatingMethod.getTargetMethod().getCallDeclaration()
						+ ");");
		}
		else {
			aDelegatingMethod.setCodeLines(
				aDelegatingMethod.getTargetAssoc().getDisplayName() + "."
						+ aDelegatingMethod.getTargetMethod().getCallDeclaration()
						+ ";");
		}

		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Method linked to: ");
		this.buffer.append(aDelegatingMethod.getTargetAssoc().getName());
		this.buffer.append('\n');

		this.open((IMethod) aDelegatingMethod);
	}

	public void open(IGetter aGetter) {
		this.open((IMethod) aGetter);
	}

	public void open(IGhost aGhost) {
		this.commentsAndVisibility(aGhost);
		this.buffer.append(" ghost ");
		this.buffer.append(aGhost.getName());
		this.inheritedEntities(aGhost.getIteratorOnInheritedEntities());
		Iterator iterator = aGhost.getIteratorOnImplementedInterfaces();
		if (iterator.hasNext()) {
			this.buffer.append(" implements ");
			this.namesOfEntities(iterator);
		}
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');

		this.indentation++;

		final Iterator it = aGhost.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberGhost memberGhostConstituent) {
				this.open(memberGhostConstituent);
				this.close(memberGhostConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
		}
	}

	public void open(IInterface anInterface) {
		this.commentsAndVisibility(anInterface);
		this.buffer.append(" interface ");
		this.buffer.append(anInterface.getName());
		this.inheritedEntities(anInterface.getIteratorOnInheritedEntities());
		this.buffer.append(' ');
		this.buffer.append('{');
		this.buffer.append('\n');

		this.indentation++;

		final Iterator it = anInterface.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberClass memberClassConstituent) {
				this.open(memberClassConstituent);
				this.close(memberClassConstituent);
			}
			
			else if (constituent instanceof IMemberInterface memberInterfaceConstituent) {
				this.open(memberInterfaceConstituent);
				this.close(memberInterfaceConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
			
			else if (constituent instanceof IComposition compositionConstituent) {
				this.visit(compositionConstituent);
			}
			
			else if (constituent instanceof IAggregation aggregationConstituent) {
				this.visit(aggregationConstituent);
			}
			
			else if (constituent instanceof IAssociation associationConstituent) {
				this.visit(associationConstituent);
			}
			
			else if (constituent instanceof ICreation creationConstituent) {
				this.visit(creationConstituent);
			}
			
			else if (constituent instanceof IUseRelationship useRelationshipConstituent) {
				this.visit(useRelationshipConstituent);
			}
		}
	}

	public void open(IMemberClass aMemberClass) {
		final Iterator it = aMemberClass.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberClass memberClassConstituent) {
				this.open(memberClassConstituent);
				this.close(memberClassConstituent);
			}
			
			else if (constituent instanceof IMemberInterface memberInterfaceConstituent) {
				this.open(memberInterfaceConstituent);
				this.close(memberInterfaceConstituent);
			}
			
			else if (constituent instanceof IDelegatingMethod delegatingMethodConstituent) {
				this.open(delegatingMethodConstituent);
				this.close(delegatingMethodConstituent);
			}
			
			else if (constituent instanceof IGetter getterConstituent) {
				this.open(getterConstituent);
				this.close(getterConstituent);
			}
			
			else if (constituent instanceof ISetter setterConstituent) {
				this.open(setterConstituent);
				this.close(setterConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
			
			else if (constituent instanceof IContainerComposition containerCompositionConstituent) {
				this.visit(containerCompositionConstituent);
			}
			
			else if (constituent instanceof IContainerAggregation containerAggregationConstituent) {
				this.visit(containerAggregationConstituent);
			}
			
			else if (constituent instanceof IComposition compositionConstituent) {
				this.visit(compositionConstituent);
			}
			
			else if (constituent instanceof IAggregation aggregationConstituent) {
				this.visit(aggregationConstituent);
			}
			
			else if (constituent instanceof IAssociation associationConstituent) {
				this.visit(associationConstituent);
			}
			
			else if (constituent instanceof ICreation creationConstituent) {
				this.visit(creationConstituent);
			}
			
			else if (constituent instanceof IUseRelationship useRelationshipConstituent) {
				this.visit(useRelationshipConstituent);
			}
		}
	}

	public void open(IMemberGhost aMemberGhost) {
		final Iterator it = aMemberGhost.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberGhost memberGhostConstituent) {
				this.open(memberGhostConstituent);
				this.close(memberGhostConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
		}
	}

	public void open(IMemberInterface aMemberInterface) {
		final Iterator it = aMemberInterface.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IMemberClass memberClassConstituent) {
				this.open(memberClassConstituent);
				this.close(memberClassConstituent);
			}
			
			else if (constituent instanceof IMemberInterface memberInterfaceConstituent) {
				this.open(memberInterfaceConstituent);
				this.close(memberInterfaceConstituent);
			}
			
			else if (constituent instanceof IMethod methodConstituent) {
				this.open(methodConstituent);
				this.close(methodConstituent);
			}
			
			else if (constituent instanceof IConstructor constructorConstituent) {
				this.open(constructorConstituent);
				this.close(constructorConstituent);
			}
			
			else if (constituent instanceof IField fieldConstituent) {
				this.visit(fieldConstituent);
			}
			
			else if (constituent instanceof IComposition compositionConstituent) {
				this.visit(compositionConstituent);
			}
			
			else if (constituent instanceof IAggregation aggregationConstituent) {
				this.visit(aggregationConstituent);
			}
			
			else if (constituent instanceof IAssociation associationConstituent) {
				this.visit(associationConstituent);
			}
			
			else if (constituent instanceof ICreation creationConstituent) {
				this.visit(creationConstituent);
			}
			
			else if (constituent instanceof IUseRelationship useRelationshipConstituent) {
				this.visit(useRelationshipConstituent);
			}
		}
	}

	public void open(IMethod aMethod) {
		this.commentsAndVisibility(aMethod);
		this.buffer.append(' ');
		this.buffer.append(aMethod.getReturnType());
		this.buffer.append(' ');
		this.nameAndParameters(aMethod);

		final Iterator it = aMethod.getConcurrentIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IParameter parameterConstituent) {
				this.visit(parameterConstituent);
			}
			
			else if (constituent instanceof IMethodInvocation methodInvocationConstituent) {
				this.visit(methodInvocationConstituent);
			}
		}
	}

	public void open(IPackage aPackage) {
		final Iterator it = aPackage.getIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IPackageGhost packageGhostConstituent) {
				this.open(packageGhostConstituent);
				this.close(packageGhostConstituent);
			}
			
			else if (constituent instanceof IPackage packageConstituent) {
				this.open(packageConstituent);
				this.close(packageConstituent);
			}
			
			else if (constituent instanceof IClass classConstituent) {
				this.open(classConstituent);
				this.close(classConstituent);
			}
			
			else if (constituent instanceof IInterface interfaceConstituent) {
				this.open(interfaceConstituent);
				this.close(interfaceConstituent);
			}
			
			else if (constituent instanceof IGhost ghostConstituent) {
				this.open(ghostConstituent);
				this.close(ghostConstituent);
			}
		}
	}

	public void open(IPackageDefault aPackage) {
		final Iterator it = aPackage.getIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IPackageGhost packageGhostConstituent) {
				this.open(packageGhostConstituent);
				this.close(packageGhostConstituent);
			}
			
			else if (constituent instanceof IPackage packageConstituent) {
				this.open(packageConstituent);
				this.close(packageConstituent);
			}
			
			else if (constituent instanceof IClass classConstituent) {
				this.open(classConstituent);
				this.close(classConstituent);
			}
			
			else if (constituent instanceof IInterface interfaceConstituent) {
				this.open(interfaceConstituent);
				this.close(interfaceConstituent);
			}
			
			else if (constituent instanceof IGhost ghostConstituent) {
				this.open(ghostConstituent);
				this.close(ghostConstituent);
			}
		}
	}

	public void open(IPackageGhost aPackageGhost) {
		final Iterator it = aPackageGhost.getIteratorOnConstituents();

		while (it.hasNext()) {
			final IConstituent constituent = (IConstituent) it.next();

			if (constituent instanceof IPackageGhost packageGhostConstituent) {
				this.open(packageGhostConstituent);
				this.close(packageGhostConstituent);
			}
			
			else if (constituent instanceof IGhost ghostConstituent) {
				this.open(ghostConstituent);
				this.close(ghostConstituent);
			}
		}
	}

	public void open(ISetter aSetter) {
		this.open((IMethod) aSetter);
	}

	public void reset() {
		this.buffer.setLength(0);
		this.indentation = 0;
	}

	public void unknownConstituentHandler(String aCalledMethodName, IConstituent aConstituent) {
		ProxyConsole.getInstance().debugOutput()
			.print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput()
			.print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput()
			.print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	public void visit(IAggregation anAggregation) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Aggregation: ");
		this.buffer.append(anAggregation.getName());
		this.buffer.append('\n');
	}

	public void visit(IAssociation anAssociation) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Association: ");
		this.buffer.append(anAssociation.getName());
		this.buffer.append('\n');
	}

	public void visit(IComposition aComposition) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Composition: ");
		this.buffer.append(aComposition.getName());
		this.buffer.append('\n');
	}

	public void visit(IContainerAggregation aContainerAggregation) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Aggregation: ");
		this.buffer.append(aContainerAggregation.getName());
		this.buffer.append('\n');

		this.visit((IField) aContainerAggregation
			.getConstituentFromName(aContainerAggregation.getOriginFieldName()));
		this.buffer.append('\n');
		this.open((IMethod) aContainerAggregation.getConstituentFromName(
			aContainerAggregation.getOriginGetterMethodName()));
		this.buffer.append('\n');
		this.open((IMethod) aContainerAggregation.getConstituentFromName(
			aContainerAggregation.getOriginSetterMethodName()));
		this.buffer.append('\n');
	}

	public void visit(IContainerComposition aContainerComposition) {
		this.visit((IContainerAggregation) aContainerComposition);
	}

	public void visit(ICreation aCreation) {

	}

	public void visit(IField aField) {
		this.commentsAndVisibility(aField);
		this.buffer.append(' ');
		this.buffer.append(aField.getType());
		this.buffer.append(' ');
		this.buffer.append(aField.getName());
		final String[] codeLines = aField.getCodeLines();
		// Can be null. The case "empty array" is dealt with in the loop.
		if (codeLines != null) {
			this.buffer.append(" = ");
			for (int i = 0; i < codeLines.length; i++) {
				this.buffer.append('\n');
				Util.addTabs(this.indentation + 1, this.buffer);
				this.buffer.append(codeLines[i]);
			}
		}
		this.buffer.append(';');
		this.buffer.append('\n');
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

		return this.buffer.toString();
	}

	private void commentsAndVisibility(final IConstituent aConstituent) {
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("//id= ");
		this.buffer.append(aConstituent.getDisplayID());
		this.buffer.append('\n');
		Util.addTabs(this.indentation, this.buffer);
		if (aConstituent.getComment() != null) {
			this.buffer.append("/* ");
			this.buffer.append(aConstituent.getComment());
			this.buffer.append(" */\n");
			Util.addTabs(this.indentation, this.buffer);
		}
		this.buffer.append(Access.getAsString(aConstituent.getVisibility()));
	}

	private void inheritedEntities(final Iterator anIterator) {
		if (anIterator.hasNext()) {
			this.buffer.append(" extends ");
			this.namesOfEntities(anIterator);
		}
	}

	private void namesOfEntities(final Iterator anIterator) {
		while (anIterator.hasNext()) {
			this.buffer.append(((IFirstClassEntity) anIterator.next()).getName());
			if (anIterator.hasNext()) {
				this.buffer.append(", ");
			}
		}
	}

	private void nameAndParameters(final IOperation anOperation) {
		this.buffer.append(anOperation.getName());
		this.buffer.append('(');
		final Iterator iterator = anOperation.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			this.buffer.append(iterator.next().toString());
			if (iterator.hasNext()) {
				this.buffer.append(", ");
			}
		}
		this.buffer.append(')');
		if (anOperation.isAbstract()) {
			this.buffer.append(';');
			return;
		}
		this.buffer.append(" {\n");
		final String[] codeLines = anOperation.getCodeLines();
		// Can be null. The case "empty array" is dealt with in the loop.
		if (codeLines != null) {
			for (int i = 0; i < codeLines.length; i++) {
				Util.addTabs(this.indentation + 1, this.buffer);
				this.buffer.append(codeLines[i]);
				this.buffer.append('\n');
			}
		}
		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append('}');
		this.buffer.append('\n');
		this.buffer.append('\n');
	}
}
