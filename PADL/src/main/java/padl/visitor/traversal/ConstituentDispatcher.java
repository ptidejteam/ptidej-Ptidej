package padl.visitor.traversal;

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
import padl.visitor.IVisitor;

/**
 * Typed replacement for the reflective dispatch of Constituent.accept(). It calls the open(),
 * close(), or visit() overload of an IVisitor that matches the type of a constituent, but never
 * traverses the constituent's children: traversing is the job of an ITraversal. Unlike the
 * reflective dispatch, it neither catches the exceptions thrown by the visitor nor caches failed
 * lookups, so a failing callback reaches the caller and does not disable later callbacks.
 *
 * <p>The compiler rejects a case that is dominated by a preceding case, so a sub-interface (e.g.,
 * IMemberClass) is always tested before its super-interface (e.g., IClass). The only ordering that
 * the compiler cannot check is between unrelated interfaces implemented by the same class: in the
 * kernel, Method extends Constructor, so the IMethod family must come before IConstructor.
 *
 * @author Chen Qian
 */
public final class ConstituentDispatcher {
  private ConstituentDispatcher() {}

  /**
   * Containers are the constituents whose accept() opens them, visits their children, and closes
   * them. IContainerAggregation is an IContainer but accept() visits it as a leaf, so it is not.
   */
  public static boolean isContainer(final IConstituent aConstituent) {
    return aConstituent instanceof IPackage
        || aConstituent instanceof IFirstClassEntity
        || aConstituent instanceof IOperation;
  }

  public static void open(final IConstituent aConstituent, final IVisitor aVisitor) {
    switch (aConstituent) {
      case IPackageGhost aPackageGhost -> aVisitor.open(aPackageGhost);
      case IPackageDefault aPackageDefault -> aVisitor.open(aPackageDefault);
      case IPackage aPackage -> aVisitor.open(aPackage);
      case IMemberClass aMemberClass -> aVisitor.open(aMemberClass);
      case IClass aClass -> aVisitor.open(aClass);
      case IMemberInterface aMemberInterface -> aVisitor.open(aMemberInterface);
      case IInterface anInterface -> aVisitor.open(anInterface);
      case IMemberGhost aMemberGhost -> aVisitor.open(aMemberGhost);
      case IGhost aGhost -> aVisitor.open(aGhost);
      case IGetter aGetter -> aVisitor.open(aGetter);
      case ISetter aSetter -> aVisitor.open(aSetter);
      case IDelegatingMethod aDelegatingMethod -> aVisitor.open(aDelegatingMethod);
      case IMethod aMethod -> aVisitor.open(aMethod);
      case IConstructor aConstructor -> aVisitor.open(aConstructor);
      default -> ConstituentDispatcher.unknown("open", aConstituent, aVisitor);
    }
  }

  public static void close(final IConstituent aConstituent, final IVisitor aVisitor) {
    switch (aConstituent) {
      case IPackageGhost aPackageGhost -> aVisitor.close(aPackageGhost);
      case IPackageDefault aPackageDefault -> aVisitor.close(aPackageDefault);
      case IPackage aPackage -> aVisitor.close(aPackage);
      case IMemberClass aMemberClass -> aVisitor.close(aMemberClass);
      case IClass aClass -> aVisitor.close(aClass);
      case IMemberInterface aMemberInterface -> aVisitor.close(aMemberInterface);
      case IInterface anInterface -> aVisitor.close(anInterface);
      case IMemberGhost aMemberGhost -> aVisitor.close(aMemberGhost);
      case IGhost aGhost -> aVisitor.close(aGhost);
      case IGetter aGetter -> aVisitor.close(aGetter);
      case ISetter aSetter -> aVisitor.close(aSetter);
      case IDelegatingMethod aDelegatingMethod -> aVisitor.close(aDelegatingMethod);
      case IMethod aMethod -> aVisitor.close(aMethod);
      case IConstructor aConstructor -> aVisitor.close(aConstructor);
      default -> ConstituentDispatcher.unknown("close", aConstituent, aVisitor);
    }
  }

  public static void visit(final IConstituent aConstituent, final IVisitor aVisitor) {
    switch (aConstituent) {
      case IField aField -> aVisitor.visit(aField);
      case IParameter aParameter -> aVisitor.visit(aParameter);
      case IMethodInvocation aMethodInvocation -> aVisitor.visit(aMethodInvocation);
      case IPrimitiveEntity aPrimitiveEntity -> aVisitor.visit(aPrimitiveEntity);
      case IContainerComposition aContainerComposition -> aVisitor.visit(aContainerComposition);
      case IContainerAggregation aContainerAggregation -> aVisitor.visit(aContainerAggregation);
      case IComposition aComposition -> aVisitor.visit(aComposition);
      case IAggregation anAggregation -> aVisitor.visit(anAggregation);
      case IAssociation anAssociation -> aVisitor.visit(anAssociation);
      case ICreation aCreation -> aVisitor.visit(aCreation);
      case IUseRelationship aUse -> aVisitor.visit(aUse);
      default -> ConstituentDispatcher.unknown("visit", aConstituent, aVisitor);
    }
  }

  private static void unknown(
      final String aMethodName, final IConstituent aConstituent, final IVisitor aVisitor) {
    aVisitor.unknownConstituentHandler(
        aMethodName + '(' + aConstituent.getClass().getName() + ')', aConstituent);
  }
}
