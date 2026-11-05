package padl.creator.classfile.test.traversal;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IOperation;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.visitor.IWalker;
import padl.visitor.repository.JavaGenerator;
import padl.visitor.traversal.BreadthFirstTraversal;
import padl.visitor.traversal.DepthFirstTraversal;
import padl.visitor.traversal.ITraversal;

/**
 * Compares the traversals of padl.visitor.traversal with the accept()-based traversal on a model
 * built by CompleteClassFileCreator from a real library, rather than on a hand-built model.
 *
 * @author Chen Qian
 */
public final class TraversalOnClassFilesTest extends TestCase {
  private static final String JAR =
      "../PADL Creator ClassFile/target/test-classes/Inheritance/jdiui.jar";

  private ICodeLevelModel model;

  public TraversalOnClassFilesTest(final String name) {
    super(name);
  }

  protected void setUp() throws CreationException {
    this.model = Factory.getInstance().createCodeLevelModel("jdiui");
    this.model.create(new CompleteClassFileCreator(new String[] {JAR}));
  }

  /** One call to the visitor: the method, its declared parameter type, and its argument. */
  private record Callback(String method, String type, Object argument) {
    public String toString() {
      final String name =
          this.argument instanceof IConstituent
              ? ((IConstituent) this.argument).getDisplayID()
              : ((IAbstractModel) this.argument).getDisplayName();
      return this.method + '(' + this.type + ") " + name;
    }
  }

  private static IWalker createRecorder(final List<Callback> callbacks) {
    return (IWalker)
        Proxy.newProxyInstance(
            IWalker.class.getClassLoader(),
            new Class[] {IWalker.class},
            (proxy, method, args) -> {
              switch (method.getName()) {
                case "getName":
                  return "Recorder";
                case "getResult":
                  return callbacks;
                case "reset":
                  callbacks.clear();
                  return null;
                case "unknownConstituentHandler":
                  callbacks.add(new Callback("unknown", (String) args[0], args[1]));
                  return null;
                default:
                  callbacks.add(
                      new Callback(
                          method.getName(),
                          method.getParameterTypes()[0].getSimpleName(),
                          args[0]));
                  return null;
              }
            });
  }

  private List<Callback> recordWithAccept() {
    final List<Callback> callbacks = new ArrayList<>();
    this.model.walk(TraversalOnClassFilesTest.createRecorder(callbacks));
    return callbacks;
  }

  private List<Callback> recordWith(final ITraversal aTraversal) {
    final List<Callback> callbacks = new ArrayList<>();
    aTraversal.walk(this.model, TraversalOnClassFilesTest.createRecorder(callbacks));
    return callbacks;
  }

  private static List<String> asStrings(final List<Callback> callbacks) {
    final List<String> strings = new ArrayList<>(callbacks.size());
    for (final Callback callback : callbacks) {
      strings.add(callback.toString());
    }
    return strings;
  }

  private static Map<String, Integer> count(final List<Callback> callbacks) {
    final Map<String, Integer> counts = new HashMap<>();
    for (final Callback callback : callbacks) {
      counts.merge(callback.toString(), 1, Integer::sum);
    }
    return counts;
  }

  private static int count(final List<Callback> callbacks, final String method, final String type) {
    int count = 0;
    for (final Callback callback : callbacks) {
      if (callback.method().equals(method) && callback.type().equals(type)) {
        count++;
      }
    }
    return count;
  }

  public void testDepthFirstMatchesAcceptExactlyOnFreshModel() {
    // The new traversal runs first, so no accept() has sorted the model yet.
    final List<Callback> viaTraversal = this.recordWith(new DepthFirstTraversal());
    final List<Callback> viaAccept = this.recordWithAccept();

    assertEquals(asStrings(viaAccept), asStrings(viaTraversal));

    // The model is not trivial and no constituent type is missing from the dispatcher.
    assertTrue(viaTraversal.size() > 10000);
    final Set<String> types = new TreeSet<>();
    for (final Callback callback : viaTraversal) {
      assertFalse(callback.toString(), callback.method().equals("unknown"));
      types.add(callback.type());
    }
    assertTrue(
        types.toString(),
        types.containsAll(
            Set.of(
                "IAbstractModel",
                "IPackage",
                "IPackageGhost",
                "IClass",
                "IInterface",
                "IGhost",
                "IMemberClass",
                "IMemberGhost",
                "IMemberInterface",
                "IConstructor",
                "IMethod",
                "IField",
                "IParameter",
                "IMethodInvocation")));
  }

  public void testDepthFirstGeneratesSameJavaCodeAsAccept() {
    final String viaTraversal = new DepthFirstTraversal().generate(this.model, new JavaGenerator());
    final String viaAccept = this.model.generate(new JavaGenerator());

    assertTrue(viaAccept.length() > 0);
    assertEquals(viaAccept, viaTraversal);
  }

  public void testBreadthFirstMakesSameCallsLevelByLevel() {
    final List<Callback> depthFirst = this.recordWith(new DepthFirstTraversal());
    final List<Callback> breadthFirst = this.recordWith(new BreadthFirstTraversal());

    assertEquals(count(depthFirst), count(breadthFirst));

    // The depth of each constituent, measured by the nesting of the depth-first callbacks.
    final Map<Object, Integer> depths = new IdentityHashMap<>();
    int depth = 0;
    for (final Callback callback : depthFirst) {
      if (callback.method().equals("close")) {
        depth--;
      } else {
        depths.putIfAbsent(callback.argument(), depth);
        if (callback.method().equals("open")) {
          depth++;
        }
      }
    }
    assertEquals(0, depth);

    // Breadth-first never goes back to a shallower constituent.
    int previousDepth = 0;
    for (final Callback callback : breadthFirst) {
      if (callback.argument() instanceof IConstituent) {
        final int currentDepth = depths.get(callback.argument());
        assertTrue(callback.toString(), currentDepth >= previousDepth);
        previousDepth = currentDepth;
      }
    }
    assertTrue(previousDepth >= 4);
  }

  public void testFilterPrunesOperationsOnRealModel() {
    final List<Callback> all = this.recordWith(new DepthFirstTraversal());
    final List<Callback> withoutOperations =
        this.recordWith(
            new DepthFirstTraversal(aConstituent -> !(aConstituent instanceof IOperation)));

    for (final Callback callback : withoutOperations) {
      assertFalse(callback.toString(), callback.argument() instanceof IOperation);
    }
    assertTrue(count(all, "visit", "IParameter") > 0);
    assertEquals(0, count(withoutOperations, "visit", "IParameter"));
    assertEquals(0, count(withoutOperations, "visit", "IMethodInvocation"));
    assertEquals(count(all, "visit", "IField"), count(withoutOperations, "visit", "IField"));
    assertEquals(count(all, "open", "IClass"), count(withoutOperations, "open", "IClass"));
  }
}
