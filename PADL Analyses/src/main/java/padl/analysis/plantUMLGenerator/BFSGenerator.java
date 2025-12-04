package padl.analysis.plantUMLGenerator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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
import padl.kernel.IRelationship;
import padl.kernel.ISetter;
import padl.kernel.IUseRelationship;
import padl.visitor.IGenerator;
import util.io.ProxyConsole;



public abstract class BFSGenerator<T extends IGenerator> implements IGenerator {

    protected final T delegate;

    private final Deque<IConstituent> stack = new ArrayDeque<>();
    private final Map<IConstituent, List<IConstituent>> childrenMap = new HashMap<>();
    private final Map<IConstituent, List<Runnable>> eventsMap = new IdentityHashMap<>();

    public BFSGenerator(T delegate) {
        this.delegate = delegate;
    }

    // -------------------- Helpers --------------------

    private void registerChild(IConstituent parent, IConstituent child) {
        if (parent != null) {
            childrenMap.computeIfAbsent(parent, k -> new ArrayList<>()).add(child);
        }
        eventsMap.putIfAbsent(child, new ArrayList<>());
    }

    private void appendEvent(IConstituent node, Runnable r) {
        if (node != null) {
            eventsMap.computeIfAbsent(node, k -> new ArrayList<>()).add(r);
        }
    }

    // -------------------- BFS replay --------------------

	private void runBFS() {
	    // find roots (nodes with no parent)
	    Set<IConstituent> allNodes = new LinkedHashSet<>(eventsMap.keySet());
	    Set<IConstituent> children = new HashSet<>();
	    childrenMap.values().forEach(children::addAll);
	    allNodes.removeAll(children);
	
	    Queue<IConstituent> queue = new ArrayDeque<>(allNodes);
	
	    while (!queue.isEmpty()) {
	        IConstituent node = queue.poll();
	
	        List<Runnable> events = eventsMap.get(node);
	        if (events != null) {
	            for (Runnable r : events) r.run();
	        }
	
	        List<IConstituent> kids = childrenMap.get(node);
	        if (kids != null) queue.addAll(kids);
	    }
	}

    private void handleOpen(IConstituent container, Runnable realOpen) {
        IConstituent parent = stack.peek();
        registerChild(parent, container);
        appendEvent(parent, realOpen);
        stack.push(container);
    }

    private void handleClose(IConstituent container, Runnable realClose) {
        appendEvent(container, realClose);
        if (!stack.isEmpty() && stack.peek() == container) stack.pop();
        if (stack.isEmpty()) runBFS();
    }

    private void handleVisit(IConstituent visited, Runnable realVisit) {
        IConstituent parent = stack.peek();
        registerChild(parent, visited);
        appendEvent(parent, realVisit);
    }

    public String getCode() { return delegate.getCode(); }
    public String getName() { return delegate.getName(); }
    public void reset() { delegate.reset(); }

    public void open(IAbstractModel m)  { delegate.open(m); }
    public void open(IClass c)          { handleOpen(c, () -> delegate.open(c)); }
    public void open(IInterface i)      { handleOpen(i, () -> delegate.open(i)); }
    public void open(IPackage p)        { handleOpen(p, () -> delegate.open(p)); }
    public void open(IPackageDefault p) { handleOpen(p, () -> delegate.open(p)); }
    public void open(IMemberClass m)    { handleOpen(m, () -> delegate.open(m)); }
    public void open(IMemberInterface m){ handleOpen(m, () -> delegate.open(m)); }
    public void open(IMemberGhost m)    { handleOpen(m, () -> delegate.open(m)); }
    public void open(IGhost g)          { handleOpen(g, () -> delegate.open(g)); }
    public void open(IMethod m)         { handleOpen(m, () -> delegate.open(m)); }
    public void open(IConstructor c)    { handleOpen(c, () -> delegate.open(c)); }
    public void open(IDelegatingMethod m){ handleOpen(m, () -> delegate.open(m)); }
    public void open(ISetter s)         { handleOpen(s, () -> delegate.open(s)); }
    public void open(IGetter g)         { handleOpen(g, () -> delegate.open(g)); }

    public void close(IAbstractModel m)  { delegate.close(m); }
    public void close(IClass c)          { handleClose(c, () -> delegate.close(c)); }
    public void close(IInterface i)      { handleClose(i, () -> delegate.close(i)); }
    public void close(IPackage p)        { handleClose(p, () -> delegate.close(p)); }
    public void close(IPackageDefault p) { handleClose(p, () -> delegate.close(p)); }
    public void close(IMemberClass m)    { handleClose(m, () -> delegate.close(m)); }
    public void close(IMemberInterface m){ handleClose(m, () -> delegate.close(m)); }
    public void close(IMemberGhost m)    { handleClose(m, () -> delegate.close(m)); }
    public void close(IGhost g)          { handleClose(g, () -> delegate.close(g)); }
    public void close(IMethod m)         { handleClose(m, () -> delegate.close(m)); }
    public void close(IConstructor c)    { handleClose(c, () -> delegate.close(c)); }
    public void close(IDelegatingMethod m){ handleClose(m, () -> delegate.close(m)); }
    public void close(ISetter s)        { handleClose(s, () -> delegate.close(s)); }
    public void close(IGetter g)        { handleClose(g, () -> delegate.close(g)); }

    public void visit(IField f)                 { handleVisit(f, () -> delegate.visit(f)); }
    public void visit(IAggregation a)           { handleVisit(a, () -> delegate.visit(a)); }
    public void visit(IAssociation a)           { handleVisit(a, () -> delegate.visit(a)); }
    public void visit(IComposition c)           { handleVisit(c, () -> delegate.visit(c)); }
    public void visit(ICreation c)              { handleVisit(c, () -> delegate.visit(c)); }
    public void visit(IMethodInvocation m)      { handleVisit(m, () -> delegate.visit(m)); }
    public void visit(IParameter p)             { handleVisit(p, () -> delegate.visit(p)); }
    public void visit(IPrimitiveEntity p)       { handleVisit(p, () -> delegate.visit(p)); }
    public void visit(IUseRelationship u)       { handleVisit(u, () -> delegate.visit(u)); }
    public void visit(IContainerAggregation c)  { handleVisit(c, () -> delegate.visit(c)); }
    public void visit(IContainerComposition c)  { handleVisit(c, () -> delegate.visit(c)); }

    public void unknownConstituentHandler(String method, IConstituent c) {
        handleVisit(c, () -> delegate.unknownConstituentHandler(method, c));
    }

}
