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
import padl.kernel.IDelegatingMethod;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMemberGhost;
import padl.kernel.IMemberInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.IPrimitiveEntity;
import padl.kernel.IUseRelationship;
import padl.util.Util;
import padl.visitor.EmptyVisitor;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;

/**
 * A Java generator built on a visitor that drives its own traversal.
 *
 * With PADL's classic Visitor, each constituent's accept()
 * decides how the model is walked (open, children, close) and picks the
 * visitor method by reflection. Here, AbstractModel.generateAlternative()
 * only calls open(IAbstractModel) and close(IAbstractModel):
 * the visitor walks the model itself, from open(IAbstractModel),
 * and chooses what to do for each constituent with an explicit
 * instanceof dispatch in walk(IConstituent).
 *
 * The per-constituent output is the one of JavaGenerator, so that both 
 * produce the same code. The inherited open/close/visit methods stay 
 * inert (they come from EmptyVisitor), which means that calling
 * model.generate(thisGenerator) yields the same code as 
 * generateAlternative() instead of generating twice.
 */
public class JavaAlternativelyPatterenedGenerator extends EmptyVisitor implements IGenerator {

	private final StringBuffer buffer = new StringBuffer();
	private int indentation = 0;

	/**
	 * Entry point: the traversal starts here, not in the model.
	 */
	@Override
	public void open(final IAbstractModel anAbstractModel) {
		this.reset();
		// Same iterator as AbstractModel.generate().
		this.walkChildren(anAbstractModel.getIteratorOnConstituents());
	}

	@Override
	public String getCode() {
		return this.buffer.toString();
	}

	@Override
	public String getName() {
		return "Java (self-traversing)";
	}

	@Override
	public void reset() {
		this.buffer.setLength(0);
		this.indentation = 0;
	}

	@Override
	public void unknownConstituentHandler(
		final String aCalledMethodName,
		final IConstituent aConstituent) {

		ProxyConsole.getInstance().debugOutput().print(this.getClass().getName());
		ProxyConsole.getInstance().debugOutput().print(" does not know what to do for \"");
		ProxyConsole.getInstance().debugOutput().print(aCalledMethodName);
		ProxyConsole.getInstance().debugOutput().print("\" (");
		ProxyConsole.getInstance().debugOutput().print(aConstituent.getDisplayID());
		ProxyConsole.getInstance().debugOutput().println(')');
	}

	// ------------------------------------------------------------------
	// Traversal
	// ------------------------------------------------------------------

	private void walkChildren(final Iterator anIterator) {
		while (anIterator.hasNext()) {
			this.walk((IConstituent) anIterator.next());
		}
	}

	/**
	 * The order of the tests matters: a more specific interface must be tested
	 * before the interface it extends (members before their entity kind,
	 * methods before constructors because the implementation of IMethod
	 * extends the one of IConstructor, container aggregations before
	 * associations). Note that IContainerAggregation is an IContainer but, as
	 * with accept(), its constituents are not walked: it is handled as a leaf.
	 */
	private void walk(final IConstituent aConstituent) {
		if (aConstituent instanceof IPackage) {
			// IPackage, IPackageDefault, and IPackageGhost emit nothing.
			this.walkChildren(((IPackage) aConstituent).getIteratorOnConstituents());
		}
		else if (aConstituent instanceof IMemberClass
				|| aConstituent instanceof IMemberGhost
				|| aConstituent instanceof IMemberInterface) {
			// Member entities emit nothing but their constituents are walked.
			this.walkChildren(((IFirstClassEntity) aConstituent).getConcurrentIteratorOnConstituents());
		}
		else if (aConstituent instanceof IClass) {
			this.walkClass((IClass) aConstituent);
		}
		else if (aConstituent instanceof IGhost) {
			this.walkGhost((IGhost) aConstituent);
		}
		else if (aConstituent instanceof IInterface) {
			this.walkInterface((IInterface) aConstituent);
		}
		else if (aConstituent instanceof IMethod) {
			// Also IGetter, ISetter, and IDelegatingMethod.
			if (aConstituent instanceof IDelegatingMethod) {
				this.emitDelegatingMethod((IDelegatingMethod) aConstituent);
			}
			else {
				this.emitMethod((IMethod) aConstituent);
			}
			this.walkChildren(
				((IMethod) aConstituent).getConcurrentIteratorOnConstituents());
		}
		else if (aConstituent instanceof IConstructor) {
			this.emitConstructor((IConstructor) aConstituent);
			this.walkChildren(((IConstructor) aConstituent).getConcurrentIteratorOnConstituents());
		}
		else if (aConstituent instanceof IField) {
			this.emitField((IField) aConstituent);
		}
		else if (aConstituent instanceof IContainerAggregation) {
			// Also IContainerComposition.
			this.emitContainerAggregation((IContainerAggregation) aConstituent);
		}
		else if (aConstituent instanceof IComposition) {
			this.emitRelationship("Composition", (IAssociation) aConstituent);
		}
		else if (aConstituent instanceof IAggregation) {
			this.emitRelationship("Aggregation", (IAssociation) aConstituent);
		}
		else if (aConstituent instanceof IAssociation) {
			this.emitRelationship("Association", (IAssociation) aConstituent);
		}
		else if (aConstituent instanceof IUseRelationship
				|| aConstituent instanceof IMethodInvocation
				|| aConstituent instanceof IParameter
				|| aConstituent instanceof IPrimitiveEntity) {
			// Nothing to emit (ICreation is an IUseRelationship).
		}
		else {
			this.unknownConstituentHandler("walk", aConstituent);
		}
	}

	private void walkClass(final IClass aClass) {
		this.commentsAndVisibility(aClass);
		this.buffer.append(" class ");
		this.buffer.append(aClass.getName());
		this.appendNames(" extends ", aClass.getIteratorOnInheritedEntities());
		this.appendNames(" implements ", aClass.getIteratorOnImplementedInterfaces());
		this.buffer.append(" {\n\n");
		this.indentation++;

		this.walkChildren(aClass.getConcurrentIteratorOnConstituents());

		this.buffer.append("\n}\n");
		this.indentation--;
	}

	private void walkGhost(final IGhost aGhost) {
		this.commentsAndVisibility(aGhost);
		this.buffer.append(" ghost ");
		this.buffer.append(aGhost.getName());
		this.appendNames(" extends ", aGhost.getIteratorOnInheritedEntities());
		this.appendNames(" implements ", aGhost.getIteratorOnImplementedInterfaces());
		this.buffer.append(" {\n");
		this.indentation++;

		this.walkChildren(aGhost.getConcurrentIteratorOnConstituents());

		this.buffer.append("\n}\n\n");
		this.indentation--;
	}

	private void walkInterface(final IInterface anInterface) {
		this.commentsAndVisibility(anInterface);
		this.buffer.append(" interface ");
		this.buffer.append(anInterface.getName());
		this.appendNames(" extends ", anInterface.getIteratorOnInheritedEntities());
		this.buffer.append(" {\n");
		this.indentation++;

		this.walkChildren(anInterface.getConcurrentIteratorOnConstituents());

		this.buffer.append("\n}\n\n");
		this.indentation--;
	}

	// ------------------------------------------------------------------
	// Emission (same output as padl.visitor.repository.JavaGenerator)
	// ------------------------------------------------------------------

	private void appendNames(final String aKeyword, final Iterator anIterator) {
		if (anIterator.hasNext()) {
			this.buffer.append(aKeyword);
			while (anIterator.hasNext()) {
				this.buffer.append(((IFirstClassEntity) anIterator.next()).getName());
				if (anIterator.hasNext()) {
					this.buffer.append(", ");
				}
			}
		}
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

	private void emitConstructor(final IConstructor aConstructor) {
		this.commentsAndVisibility(aConstructor);
		this.buffer.append(' ');
		this.nameAndParameters(aConstructor);
	}

	private void emitMethod(final IMethod aMethod) {
		this.commentsAndVisibility(aMethod);
		this.buffer.append(' ');
		this.buffer.append(aMethod.getReturnType());
		this.buffer.append(' ');
		this.nameAndParameters(aMethod);
	}

	private void emitDelegatingMethod(final IDelegatingMethod aDelegatingMethod) {
		if (aDelegatingMethod.getTargetAssoc().getCardinality() == Cardinality.Many) {

			aDelegatingMethod.setCodeLines(
				"for (java.util.Enumeration enum = "
						+ aDelegatingMethod.getTargetAssoc().getDisplayName()
						+ ".elements(); enum.hasMoreElements(); (("
						+ aDelegatingMethod.getTargetAssoc().getTargetEntity()
							.getDisplayID()
						+ ") enum.nextElement())."
						+ aDelegatingMethod.getTargetMethod()
							.getCallDeclaration()
						+ ");");
		}
		else {
			aDelegatingMethod.setCodeLines(
				aDelegatingMethod.getTargetAssoc().getDisplayName() + "."
						+ aDelegatingMethod.getTargetMethod()
							.getCallDeclaration()
						+ ";");
		}

		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// Method linked to: ");
		this.buffer.append(aDelegatingMethod.getTargetAssoc().getName());
		this.buffer.append('\n');

		this.emitMethod(aDelegatingMethod);
	}

	private void emitField(final IField aField) {
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

	private void emitRelationship(
		final String aKind,
		final IAssociation anAssociation) {

		Util.addTabs(this.indentation, this.buffer);
		this.buffer.append("// ");
		this.buffer.append(aKind);
		this.buffer.append(": ");
		this.buffer.append(anAssociation.getName());
		this.buffer.append('\n');
	}

	private void emitContainerAggregation(
		final IContainerAggregation aContainerAggregation) {

		// As in JavaGenerator, container compositions are also labelled
		// "Aggregation".
		this.emitRelationship("Aggregation", aContainerAggregation);

		// Deliberate difference with JavaGenerator: getOrigin*Name() return
		// IDs ("~ID1"...), not names. JavaGenerator looks them up with
		// getConstituentFromName(), which returns null, so it throws a
		// NullPointerException that accept() swallows before falling back to
		// visit(IAssociation), producing garbage that even depends on
		// accept()'s static cache. Looking them up by ID does what was meant.
		this.emitField((IField) aContainerAggregation.getConstituentFromID(aContainerAggregation.getOriginFieldName()));
		this.buffer.append('\n');
		this.emitMethod((IMethod) aContainerAggregation.getConstituentFromID(aContainerAggregation.getOriginGetterMethodName()));
		this.buffer.append('\n');
		this.emitMethod((IMethod) aContainerAggregation.getConstituentFromID(aContainerAggregation.getOriginSetterMethodName()));
		this.buffer.append('\n');
	}
}