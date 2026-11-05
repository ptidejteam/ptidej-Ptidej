package padl.visitor.traversal;

import java.lang.reflect.Proxy;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import junit.framework.TestCase;
import padl.kernel.Cardinality;
import padl.kernel.IAbstractModel;
import padl.kernel.IAssociation;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstructor;
import padl.kernel.IFactory;
import padl.kernel.IField;
import padl.kernel.IGetter;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.ISetter;
import padl.kernel.impl.Factory;
import padl.visitor.IFilter;
import padl.visitor.IWalker;
import padl.visitor.repository.JavaGenerator;

public class TraversalTest extends TestCase {
  private static final String OPEN_MODEL = "open(IAbstractModel) TraversalModel";
  private static final String CLOSE_MODEL = "close(IAbstractModel) TraversalModel";

  private final IFactory factory = Factory.getInstance();
  private IAbstractModel model;

  public TraversalTest(final String name) {
    super(name);
  }

  protected void setUp() {
    this.model = this.createModel(true);
  }

  /**
   * The container composition of this model has no origin field, getter, or setter, which makes
   * JavaGenerator throw a NullPointerException when visiting it.
   */
  private IAbstractModel createModel(final boolean withContainerComposition) {
    final IAbstractModel aModel = this.factory.createCodeLevelModel("TraversalModel");

    final IPackage aPackage = this.factory.createPackage("p".toCharArray());
    aModel.addConstituent(aPackage);

    final IClass b = this.factory.createClass("p.B".toCharArray(), "B".toCharArray());
    final IInterface i = this.factory.createInterface("p.I".toCharArray(), "I".toCharArray());
    final IGhost g = this.factory.createGhost("p.G".toCharArray(), "G".toCharArray());
    final IClass a = this.factory.createClass("p.A".toCharArray(), "A".toCharArray());
    aPackage.addConstituent(a);
    aPackage.addConstituent(b);
    aPackage.addConstituent(i);
    aPackage.addConstituent(g);

    final IField field =
        this.factory.createField("f".toCharArray(), "f".toCharArray(), "int".toCharArray(), 0);
    a.addConstituent(field);

    final IConstructor constructor =
        this.factory.createConstructor("A()".toCharArray(), "A".toCharArray());
    a.addConstituent(constructor);

    final IMethod method = this.factory.createMethod("m(B)".toCharArray(), "m".toCharArray());
    final IParameter parameter = this.factory.createParameter(b, "aB".toCharArray(), 0);
    method.addConstituent(parameter);
    final IMethodInvocation invocation =
        this.factory.createMethodInvocation(
            IMethodInvocation.CLASS_INSTANCE, Cardinality.One, 0, b);
    method.addConstituent(invocation);
    a.addConstituent(method);

    final IGetter getter = this.factory.createGetter("getF()".toCharArray(), "getF".toCharArray());
    a.addConstituent(getter);
    final ISetter setter = this.factory.createSetter("setF()".toCharArray(), "setF".toCharArray());
    a.addConstituent(setter);

    final IAssociation association =
        this.factory.createAssociationRelationship("toB".toCharArray(), b, Cardinality.One);
    a.addConstituent(association);
    a.addConstituent(
        this.factory.createAggregationRelationship("aggB".toCharArray(), b, Cardinality.Many));
    a.addConstituent(
        this.factory.createCompositionRelationship("compB".toCharArray(), b, Cardinality.Many));
    if (withContainerComposition) {
      a.addConstituent(
          this.factory.createContainerCompositionRelationship(
              "contB".toCharArray(), b, Cardinality.Many));
    }
    a.addConstituent(
        this.factory.createCreationRelationship("newB".toCharArray(), b, Cardinality.One));

    final IMemberClass member =
        this.factory.createMemberClass("p.A$M".toCharArray(), "M".toCharArray());
    member.addConstituent(
        this.factory.createField("g".toCharArray(), "g".toCharArray(), "int".toCharArray(), 0));
    a.addConstituent(member);

    return aModel;
  }

  /**
   * One standalone constituent for each of the 25 constituent types that IVisitor has a callback
   * for: 3 packages, 6 first-class entities, 5 operations, and 11 leaves.
   */
  private List<IConstituent> createOneConstituentOfEachType() {
    final IClass target = this.factory.createClass("q.T".toCharArray(), "T".toCharArray());
    final IAssociation association =
        this.factory.createAssociationRelationship("assoc".toCharArray(), target, Cardinality.One);
    final IMethod supportMethod =
        this.factory.createMethod("support()".toCharArray(), "support".toCharArray());

    return List.of(
        this.factory.createPackage("q".toCharArray()),
        this.factory.createPackageDefault(),
        this.factory.createPackageGhost("qg".toCharArray()),
        target,
        this.factory.createMemberClass("q.T$MC".toCharArray(), "MC".toCharArray()),
        this.factory.createInterface("q.I".toCharArray(), "I".toCharArray()),
        this.factory.createMemberInterface("q.T$MI".toCharArray(), "MI".toCharArray()),
        this.factory.createGhost("q.G".toCharArray(), "G".toCharArray()),
        this.factory.createMemberGhost("q.T$MG".toCharArray(), "MG".toCharArray()),
        this.factory.createConstructor("T()".toCharArray(), "T".toCharArray()),
        supportMethod,
        this.factory.createGetter("getX()".toCharArray(), "getX".toCharArray()),
        this.factory.createSetter("setX()".toCharArray(), "setX".toCharArray()),
        this.factory.createDelegatingMethod(
            "delegate()".toCharArray(), association, supportMethod),
        this.factory.createField("x".toCharArray(), "x".toCharArray(), "int".toCharArray(), 0),
        this.factory.createParameter(target, "aT".toCharArray(), 0),
        this.factory.createMethodInvocation(
            IMethodInvocation.CLASS_INSTANCE, Cardinality.One, 0, target),
        this.factory.createPrimitiveEntity("int".toCharArray()),
        association,
        this.factory.createAggregationRelationship("agg".toCharArray(), target, Cardinality.Many),
        this.factory.createCompositionRelationship(
            "comp".toCharArray(), target, Cardinality.Many),
        this.factory.createContainerAggregationRelationship(
            "cagg".toCharArray(), target, Cardinality.Many),
        this.factory.createContainerCompositionRelationship(
            "ccomp".toCharArray(), target, Cardinality.Many),
        this.factory.createCreationRelationship("new".toCharArray(), target, Cardinality.One),
        this.factory.createUseRelationship("use".toCharArray(), target, Cardinality.One));
  }

  private static IWalker createRecorder(final List<String> events) {
    return TraversalTest.createRecorder(events, anEvent -> {});
  }

  /**
   * A walker recording each call as "name(DeclaredParameterType) constituent", so the events show
   * which overload of open(), close(), or visit() each dispatch selected. A call to
   * unknownConstituentHandler() is recorded as "unknown methodName constituent". After recording
   * an open(), close(), or visit() call, the walker passes the event to the hook, which may throw
   * to simulate a failing visitor or modify the model.
   */
  private static IWalker createRecorder(final List<String> events, final Consumer<String> hook) {
    return (IWalker)
        Proxy.newProxyInstance(
            IWalker.class.getClassLoader(),
            new Class[] {IWalker.class},
            (proxy, method, args) -> {
              switch (method.getName()) {
                case "getName":
                  return "Recorder";
                case "getResult":
                  return events;
                case "reset":
                  events.clear();
                  return null;
                case "unknownConstituentHandler":
                  final String calledMethod = (String) args[0];
                  final int parenthesis = calledMethod.indexOf('(');
                  events.add(
                      "unknown "
                          + (parenthesis < 0 ? calledMethod : calledMethod.substring(0, parenthesis))
                          + ' '
                          + ((IConstituent) args[1]).getDisplayID());
                  return null;
                default:
                  final Object argument = args[0];
                  final String name =
                      argument instanceof IConstituent
                          ? ((IConstituent) argument).getDisplayID()
                          : ((IAbstractModel) argument).getDisplayName();
                  final String event =
                      method.getName()
                          + '('
                          + method.getParameterTypes()[0].getSimpleName()
                          + ") "
                          + name;
                  events.add(event);
                  hook.accept(event);
                  return null;
              }
            });
  }

  private static List<String> recordWithAccept(final IAbstractModel aModel) {
    final List<String> events = new ArrayList<>();
    aModel.walk(TraversalTest.createRecorder(events));
    return events;
  }

  private static List<String> recordWith(
      final ITraversal aTraversal, final IAbstractModel aModel) {
    final List<String> events = new ArrayList<>();
    aTraversal.walk(aModel, TraversalTest.createRecorder(events));
    return events;
  }

  private static Map<String, Integer> count(final List<String> events) {
    final Map<String, Integer> counts = new HashMap<>();
    for (final String event : events) {
      counts.merge(event, 1, Integer::sum);
    }
    return counts;
  }

  private static int indexOfFirst(final List<String> events, final String prefix) {
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).startsWith(prefix)) {
        return i;
      }
    }
    return -1;
  }

  private static String last(final List<String> events) {
    return events.get(events.size() - 1);
  }

  /**
   * Rebuilds the tree of nested callbacks and sorts the children of each container, so that two
   * sequences compare equal when they make the same calls with the same nesting, whatever the
   * order of siblings. Also checks that every open() is closed by the matching close().
   */
  private static String canonical(final List<String> events) {
    final Deque<String> openContainers = new ArrayDeque<>();
    final Deque<List<String>> children = new ArrayDeque<>();
    children.push(new ArrayList<>());
    for (final String event : events) {
      if (event.startsWith("open(")) {
        openContainers.push(event.substring("open".length()));
        children.push(new ArrayList<>());
      } else if (event.startsWith("close(")) {
        final String container = openContainers.pop();
        assertEquals("Unbalanced close()", container, event.substring("close".length()));
        final List<String> containerChildren = children.pop();
        Collections.sort(containerChildren);
        children.peek().add(container + containerChildren);
      } else {
        children.peek().add(event);
      }
    }
    assertTrue("Unclosed containers: " + openContainers, openContainers.isEmpty());
    final List<String> roots = children.pop();
    Collections.sort(roots);
    return roots.toString();
  }

  // Depth-first equivalence with accept().

  public void testDepthFirstMatchesAcceptUpToSiblingOrderOnFreshModel() {
    // The new traversal runs first, so no accept() has sorted the model yet.
    final List<String> viaTraversal = recordWith(new DepthFirstTraversal(), this.model);
    final List<String> viaAccept = recordWithAccept(this.model);

    assertEquals(count(viaAccept), count(viaTraversal));
    assertEquals(canonical(viaAccept), canonical(viaTraversal));
  }

  public void testDepthFirstMatchesAcceptExactlyOnceAcceptHasSortedModel() {
    // walk() sorts the package in place, so the traversal that follows sees the sorted order.
    final List<String> viaAccept = recordWithAccept(this.model);
    final List<String> viaTraversal = recordWith(new DepthFirstTraversal(), this.model);

    assertEquals(viaAccept, viaTraversal);
  }

  public void testDepthFirstFollowsCurrentOrderUntilAcceptSortsPackage() {
    final IAbstractModel aModel = this.factory.createCodeLevelModel("OrderModel");
    final IPackage aPackage = this.factory.createPackage("q".toCharArray());
    aModel.addConstituent(aPackage);
    aPackage.addConstituent(this.factory.createClass("q.B".toCharArray(), "B".toCharArray()));
    aPackage.addConstituent(this.factory.createClass("q.A".toCharArray(), "A".toCharArray()));

    final List<String> before = recordWith(new DepthFirstTraversal(), aModel);
    final List<String> viaAccept = recordWithAccept(aModel);
    final List<String> after = recordWith(new DepthFirstTraversal(), aModel);

    assertTrue(before.indexOf("open(IClass) q.B") < before.indexOf("open(IClass) q.A"));
    assertTrue(viaAccept.indexOf("open(IClass) q.A") < viaAccept.indexOf("open(IClass) q.B"));
    assertEquals(viaAccept, after);
    assertEquals(canonical(before), canonical(after));
  }

  public void testDepthFirstGeneratesSameCodeAsAccept() {
    final IAbstractModel aModel = this.createModel(false);
    final String expected = aModel.generate(new JavaGenerator());
    final String actual = new DepthFirstTraversal().generate(aModel, new JavaGenerator());

    assertTrue("The generated code should not be empty", expected.length() > 0);
    assertEquals(expected, actual);
  }

  public void testWalkReturnsResultOfWalker() {
    final List<String> events = new ArrayList<>();
    final IWalker recorder = createRecorder(events);

    assertSame(events, new DepthFirstTraversal().walk(this.model, recorder));
    assertSame(events, new BreadthFirstTraversal().walk(this.model, recorder));
  }

  // Typed dispatch.

  public void testDispatchSelectsSameOverloadAsReflectiveAccept() {
    final Set<String> selectedParameterTypes = new HashSet<>();
    for (final IConstituent constituent : this.createOneConstituentOfEachType()) {
      final List<String> viaAccept = new ArrayList<>();
      constituent.accept(createRecorder(viaAccept));

      final List<String> viaDispatcher = new ArrayList<>();
      final IWalker recorder = createRecorder(viaDispatcher);
      if (ConstituentDispatcher.isContainer(constituent)) {
        ConstituentDispatcher.open(constituent, recorder);
        ConstituentDispatcher.close(constituent, recorder);
      } else {
        ConstituentDispatcher.visit(constituent, recorder);
      }

      final String type = constituent.getClass().getName();
      assertEquals(type, viaAccept, viaDispatcher);
      for (final String event : viaDispatcher) {
        assertFalse(type + " reached the unknown handler", event.startsWith("unknown"));
        selectedParameterTypes.add(event.substring(event.indexOf('(') + 1, event.indexOf(')')));
      }
    }

    // Each constituent selected a different overload, so every case of the dispatcher ran.
    assertEquals(25, selectedParameterTypes.size());
  }

  public void testTypedDispatchSelectsMostSpecificOverload() {
    final List<String> events = recordWith(new DepthFirstTraversal(), this.model);

    assertTrue(events.contains("open(IPackage) p"));
    assertTrue(events.contains("open(IClass) p.A"));
    assertTrue(events.contains("open(IInterface) p.I"));
    assertTrue(events.contains("open(IGhost) p.G"));
    assertTrue(events.contains("open(IMemberClass) p.A$M"));
    assertTrue(events.contains("open(IConstructor) A()"));
    assertTrue(events.contains("open(IMethod) m(B)"));
    assertTrue(events.contains("open(IGetter) getF()"));
    assertTrue(events.contains("open(ISetter) setF()"));
    assertTrue(events.contains("visit(IField) f"));
    // Parameters, invocations, and relationships get a generated suffix in their IDs.
    assertTrue(indexOfFirst(events, "visit(IParameter) Parameter") >= 0);
    assertTrue(indexOfFirst(events, "visit(IMethodInvocation) Method Invocation") >= 0);
    assertTrue(indexOfFirst(events, "visit(IAssociation) toB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IAggregation) aggB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IComposition) compB") >= 0);
    assertTrue(indexOfFirst(events, "visit(IContainerComposition) contB") >= 0);
    assertTrue(indexOfFirst(events, "visit(ICreation) newB") >= 0);
  }

  public void testUnknownConstituentReachesHandler() {
    final IConstituent unknown =
        (IConstituent)
            Proxy.newProxyInstance(
                IConstituent.class.getClassLoader(),
                new Class[] {IConstituent.class},
                (proxy, method, args) ->
                    method.getName().equals("getDisplayID") ? "unknown constituent" : null);
    final List<String> events = new ArrayList<>();
    final IWalker recorder = createRecorder(events);

    assertFalse(ConstituentDispatcher.isContainer(unknown));
    ConstituentDispatcher.open(unknown, recorder);
    ConstituentDispatcher.close(unknown, recorder);
    ConstituentDispatcher.visit(unknown, recorder);

    assertEquals(
        List.of(
            "unknown open unknown constituent",
            "unknown close unknown constituent",
            "unknown visit unknown constituent"),
        events);
  }

  // Breadth-first order.

  public void testBreadthFirstVisitsLevelByLevel() {
    final List<String> depthFirst = recordWith(new DepthFirstTraversal(), this.model);
    final List<String> breadthFirst = recordWith(new BreadthFirstTraversal(), this.model);

    // Same calls, different order.
    assertEquals(count(depthFirst), count(breadthFirst));
    assertFalse(depthFirst.equals(breadthFirst));

    // Model first and last.
    assertEquals(OPEN_MODEL, breadthFirst.get(0));
    assertEquals(CLOSE_MODEL, last(breadthFirst));

    // Depth 2 (entities) before depth 3 (their members) before depth 4 (parameters, fields of
    // member classes).
    final int lastEntity = breadthFirst.lastIndexOf("close(IGhost) p.G");
    final int firstMember = indexOfFirst(breadthFirst, "visit(IField) f");
    final int lastMember = breadthFirst.lastIndexOf("close(IMemberClass) p.A$M");
    final int firstParameter = indexOfFirst(breadthFirst, "visit(IParameter)");
    assertTrue(lastEntity < firstMember);
    assertTrue(lastMember < firstParameter);
    assertTrue(breadthFirst.indexOf("visit(IField) g") > lastMember);

    // Containers are opened and closed immediately.
    final int open = breadthFirst.indexOf("open(IClass) p.A");
    assertEquals("close(IClass) p.A", breadthFirst.get(open + 1));
  }

  // Filters.

  public void testFilterPrunesWholeSubtree() {
    final List<String> events =
        recordWith(
            new DepthFirstTraversal(aConstituent -> !(aConstituent instanceof IOperation)),
            this.model);

    assertEquals(-1, indexOfFirst(events, "open(IMethod)"));
    assertEquals(-1, indexOfFirst(events, "open(IConstructor)"));
    assertEquals(-1, indexOfFirst(events, "open(IGetter)"));
    assertEquals(-1, indexOfFirst(events, "visit(IParameter)"));
    assertEquals(-1, indexOfFirst(events, "visit(IMethodInvocation)"));
    assertTrue(events.contains("visit(IField) f"));
    assertTrue(events.contains("visit(IField) g"));
    assertTrue(events.contains("open(IClass) p.A"));
  }

  public void testBreadthFirstFilterPrunesWholeSubtree() {
    final List<String> events =
        recordWith(
            new BreadthFirstTraversal(aConstituent -> !(aConstituent instanceof IClass)),
            this.model);

    // Classes A and B are rejected, so the members of A, including member class M, are not
    // reached.
    assertEquals(-1, indexOfFirst(events, "open(IClass)"));
    assertEquals(-1, indexOfFirst(events, "open(IMemberClass)"));
    assertEquals(-1, indexOfFirst(events, "visit(IField)"));
    assertEquals(-1, indexOfFirst(events, "open(IMethod)"));
    assertTrue(events.contains("open(IInterface) p.I"));
    assertTrue(events.contains("open(IGhost) p.G"));
  }

  public void testFilterIsNotConsultedForDescendantsOfRejectedConstituent() {
    for (final boolean depthFirst : new boolean[] {true, false}) {
      final List<IConstituent> consulted = new ArrayList<>();
      final IFilter skipOperations =
          aConstituent -> {
            consulted.add(aConstituent);
            return !(aConstituent instanceof IOperation);
          };
      final ITraversal traversal =
          depthFirst
              ? new DepthFirstTraversal(skipOperations)
              : new BreadthFirstTraversal(skipOperations);
      traversal.walk(this.model, createRecorder(new ArrayList<>()));

      boolean consultedAnOperation = false;
      for (final IConstituent constituent : consulted) {
        consultedAnOperation |= constituent instanceof IOperation;
        assertFalse(constituent.getDisplayID(), constituent instanceof IParameter);
        assertFalse(constituent.getDisplayID(), constituent instanceof IMethodInvocation);
      }
      assertTrue(consultedAnOperation);
    }
  }

  public void testFilterRejectingEverythingOnlyOpensAndClosesModel() {
    final List<String> expected = List.of(OPEN_MODEL, CLOSE_MODEL);

    assertEquals(expected, recordWith(new DepthFirstTraversal(aConstituent -> false), this.model));
    assertEquals(
        expected, recordWith(new BreadthFirstTraversal(aConstituent -> false), this.model));
  }

  public void testEmptyModelOnlyOpensAndClosesModel() {
    final IAbstractModel empty = this.factory.createCodeLevelModel("Empty");
    final List<String> expected =
        List.of("open(IAbstractModel) Empty", "close(IAbstractModel) Empty");

    assertEquals(expected, recordWith(new DepthFirstTraversal(), empty));
    assertEquals(expected, recordWith(new BreadthFirstTraversal(), empty));
  }

  public void testNullFilterIsRejected() {
    try {
      new DepthFirstTraversal(null);
      fail("A depth-first traversal should reject a null filter");
    } catch (final IllegalArgumentException e) {
      // Expected.
    }
    try {
      new BreadthFirstTraversal(null);
      fail("A breadth-first traversal should reject a null filter");
    } catch (final IllegalArgumentException e) {
      // Expected.
    }
  }

  // Failing visitors.

  public void testVisitorExceptionsArePropagatedUnlikeAccept() {
    final IllegalStateException failure = new IllegalStateException("visit(IField) f failed");
    final Consumer<String> failOnF =
        anEvent -> {
          if (anEvent.equals("visit(IField) f")) {
            throw failure;
          }
        };

    // The reflective accept() catches the exception, reports the field as unknown, and carries on.
    final List<String> viaAccept = new ArrayList<>();
    this.model.walk(createRecorder(viaAccept, failOnF));
    assertTrue(viaAccept.contains("unknown visit f"));
    assertEquals(CLOSE_MODEL, last(viaAccept));

    // The typed dispatch calls the visitor directly, so the exception reaches the caller and the
    // traversal stops at the failing callback, without the remaining close() calls.
    final List<String> depthFirst = new ArrayList<>();
    try {
      new DepthFirstTraversal().walk(this.model, createRecorder(depthFirst, failOnF));
      fail("The depth-first traversal should not swallow the exception");
    } catch (final IllegalStateException e) {
      assertSame(failure, e);
    }
    assertEquals("visit(IField) f", last(depthFirst));
    assertFalse(depthFirst.contains("close(IClass) p.A"));
    assertFalse(depthFirst.contains(CLOSE_MODEL));

    final List<String> breadthFirst = new ArrayList<>();
    try {
      new BreadthFirstTraversal().walk(this.model, createRecorder(breadthFirst, failOnF));
      fail("The breadth-first traversal should not swallow the exception");
    } catch (final IllegalStateException e) {
      assertSame(failure, e);
    }
    assertEquals("visit(IField) f", last(breadthFirst));
    assertFalse(breadthFirst.contains(CLOSE_MODEL));
  }

  public void testFailedCallbackDisablesLaterCallbacksOnlyInAccept() {
    final AtomicBoolean failed = new AtomicBoolean();
    final Consumer<String> failOnFirstField =
        anEvent -> {
          if (anEvent.startsWith("visit(IField)") && failed.compareAndSet(false, true)) {
            throw new IllegalStateException("First field failed");
          }
        };

    // accept(): after the failure on f, its cache marks IField as having no callback, so field g
    // is skipped without any call, even to the unknown handler.
    final List<String> viaAccept = new ArrayList<>();
    final IWalker acceptRecorder = createRecorder(viaAccept, failOnFirstField);
    this.model.walk(acceptRecorder);
    assertTrue(viaAccept.contains("visit(IField) f"));
    assertTrue(viaAccept.contains("unknown visit f"));
    assertFalse(viaAccept.contains("visit(IField) g"));
    assertFalse(viaAccept.contains("unknown visit g"));

    // The cache survives while the same visitor is used, so a second walk visits no field at all.
    viaAccept.clear();
    this.model.walk(acceptRecorder);
    assertEquals(-1, indexOfFirst(viaAccept, "visit(IField)"));
    assertEquals(-1, indexOfFirst(viaAccept, "unknown"));

    // Typed dispatch: the failure stops the first traversal, but the same visitor receives every
    // field in the next one.
    failed.set(false);
    final List<String> viaTraversal = new ArrayList<>();
    final IWalker traversalRecorder = createRecorder(viaTraversal, failOnFirstField);
    try {
      new DepthFirstTraversal().walk(this.model, traversalRecorder);
      fail("The first field should make the traversal fail");
    } catch (final IllegalStateException e) {
      // Expected.
    }
    viaTraversal.clear();
    new DepthFirstTraversal().walk(this.model, traversalRecorder);
    assertTrue(viaTraversal.contains("visit(IField) f"));
    assertTrue(viaTraversal.contains("visit(IField) g"));
  }

  // Modifications during a traversal.

  public void testConstituentAddedAfterChildrenAreReadIsNotVisited() {
    final IPackage aPackage = (IPackage) this.model.getConstituentFromID("p");
    final IClass a = (IClass) aPackage.getConstituentFromID("p.A");
    final List<String> events = new ArrayList<>();

    // The children of A have already been read when its field f is visited.
    new DepthFirstTraversal()
        .walk(
            this.model,
            createRecorder(
                events,
                anEvent -> {
                  if (anEvent.equals("visit(IField) f")) {
                    a.addConstituent(
                        this.factory.createField(
                            "late".toCharArray(),
                            "late".toCharArray(),
                            "int".toCharArray(),
                            0));
                  }
                }));

    assertNotNull(a.getConstituentFromID("late"));
    assertFalse(events.contains("visit(IField) late"));
    assertEquals(CLOSE_MODEL, last(events));
  }
}
