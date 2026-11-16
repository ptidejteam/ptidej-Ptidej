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
import padl.visitor.IVisitor;
import padl.visitor.repository.JavaGenerator;
import padl.kernel.IContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JavaAlternativelyPatterenedGenerator implements IGenerator {
	private final IGenerator delegate;

	// Inspired by Constituent.cachedCorrespondingInterfaceNames.
	// Caching for Map<String, Method> for performance, if there is no Method with the string, a null entry is entered into methodCache
	private final Map<String, Method> methodCache = new HashMap<>();

	public JavaAlternativelyPatterenedGenerator() {
		this(new JavaGenerator());
	}

	public JavaAlternativelyPatterenedGenerator(IGenerator delegate) {
		this.delegate = delegate;
	}

	public void close(IAbstractModel anAbstractModel) {
		delegate.close(anAbstractModel);
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
		return "JavaAlternativePatterenedGenerator (Own traversal)";
	}

	public void open(IAbstractModel anAbstractModel) {
		delegate.open(anAbstractModel);
		traverseAll(anAbstractModel.getIteratorOnConstituents());
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
		delegate.reset();
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
		return delegate.getCode();
	}

	private void traverseAll(final Iterator anIterator) {
		while (anIterator.hasNext()) {
			traverse((IConstituent) anIterator.next());
		}
	}

	private Method findVisitorMethod(final String aName, final Class<?> aType) {
		final String key = aName + '(' + aType.getName() + ')';
		if (methodCache.containsKey(key)) {
			return methodCache.get(key);
		}
		Method method;
		try {
			method = IVisitor.class.getMethod(aName, aType);
		}
		catch (final NoSuchMethodException e) {
			method = null;
		}
		methodCache.put(key, method);
		return method;
	}

	private void call(final Method aMethod, final IConstituent aConstituent) {
		try {
			aMethod.invoke(this.delegate, aConstituent);
		}
		catch (final InvocationTargetException e) {
			// re-throw exception from invoke
			final Throwable cause = e.getCause();
			if (cause instanceof RuntimeException re) {
				throw re;
			}
			throw new RuntimeException(cause);
		}
		catch (final IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	private void traverse(final IConstituent c) {
		if (!allowed(c)) {
			return;
		}
		for (Class<?> k = c.getClass(); k != null; k = k.getSuperclass()) {
			for (final Class<?> i : k.getInterfaces()) {
				final Method open = findVisitorMethod("open", i);
				final Method close = findVisitorMethod("close", i);
				if (open != null && close != null) {
					call(open, c);
					if (c instanceof IContainer container) {
						traverseChildrenOf(container);
					}
					call(close, c);
					return;
				}
				final Method visit = findVisitorMethod("visit", i);
				if (visit != null) {
					call(visit, c);
					return;
				}
			}
		}

		this.delegate.unknownConstituentHandler("visit", c);
	}

	private void traverseChildrenOf(final IContainer c) {
		if (c instanceof IPackage) {
			traverseAll(c.getIteratorOnConstituents());
		}
		else {
			traverseAll(c.getConcurrentIteratorOnConstituents());
		}
	}

	private boolean allowed(Object o) {
		return (o instanceof IConstituent);
	}
}