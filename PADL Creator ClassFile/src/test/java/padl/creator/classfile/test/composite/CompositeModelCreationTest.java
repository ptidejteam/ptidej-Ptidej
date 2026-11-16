package padl.creator.classfile.test.composite;


import java.util.Iterator;


import org.junit.Assert;


import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;


public class CompositeModelCreationTest extends TestCase {
   private static final String PATH = "../PADL Creator ClassFile/target/test-classes/padl/example/composite1/";
   private static final String PKG = "padl.example.composite1.";

   private static ICodeLevelModel CodeLevelModel;

   public CompositeModelCreationTest(final String aName) {
      super(aName);
   }


   protected void setUp() throws CreationException {
      if (CompositeModelCreationTest.CodeLevelModel == null) {
         final CompleteClassFileCreator creator = new CompleteClassFileCreator(
               new String[] { CompositeModelCreationTest.PATH }, true);

         final ICodeLevelModel model = Factory.getInstance()
               .createCodeLevelModel("Composite1");

         model.create(creator);

         CompositeModelCreationTest.CodeLevelModel = model;
      }
   }


   private IFirstClassEntity getEntity(final String aSimpleName) {
      final IFirstClassEntity entity = CompositeModelCreationTest.CodeLevelModel
            .getTopLevelEntityFromID(CompositeModelCreationTest.PKG
                  + aSimpleName);
      Assert.assertNotNull("Missing entity " + aSimpleName, entity);
      return entity;
   }


   private static boolean contains(final Iterator anIterator,
         final String anID) {


      while (anIterator.hasNext()) {
         if (((IConstituent) anIterator.next()).getDisplayID()
               .equals(anID)) {
            return true;
         }
      }
      return false;
   }


   public void testPackages() {
      final IPackage padl = (IPackage) CompositeModelCreationTest.CodeLevelModel
            .getConstituentFromID("padl");
      Assert.assertNotNull(padl);
      final IPackage example = (IPackage) padl.getConstituentFromID("example");
      Assert.assertNotNull(example);
      final IPackage composite1 = (IPackage) example
            .getConstituentFromID("composite1");
      Assert.assertNotNull(composite1);
      Assert.assertSame(this.getEntity("Document"), composite1
            .getConstituentFromID(CompositeModelCreationTest.PKG + "Document"));
   }


   public void testEntities() {
      Assert.assertTrue(this.getEntity("Document") instanceof IClass);
      Assert.assertTrue(this.getEntity("Paragraph") instanceof IClass);
      Assert.assertTrue(
            this.getEntity("IndentedParagraph") instanceof IClass);
      Assert.assertTrue(this.getEntity("Title") instanceof IClass);
      Assert.assertTrue(this.getEntity("Main") instanceof IClass);
      Assert.assertTrue(
            this.getEntity("AbstractDocument") instanceof IInterface);
      Assert.assertTrue(this.getEntity("Element") instanceof IInterface);
   }


   public void testGhosts() {
      Assert.assertTrue(CompositeModelCreationTest.CodeLevelModel
            .getTopLevelEntityFromID("java.util.Vector") instanceof IGhost);
      Assert.assertTrue(CompositeModelCreationTest.CodeLevelModel
            .getTopLevelEntityFromID("java.lang.Object") instanceof IGhost);
   }


   public void testInheritanceAndImplementation() {
      Assert.assertTrue(CompositeModelCreationTest.contains(
            this.getEntity("Element").getIteratorOnInheritedEntities(),
            CompositeModelCreationTest.PKG + "AbstractDocument"));
      Assert.assertTrue(CompositeModelCreationTest.contains(
            this.getEntity("IndentedParagraph")
                  .getIteratorOnInheritedEntities(),
            CompositeModelCreationTest.PKG + "Paragraph"));
      Assert.assertTrue(CompositeModelCreationTest.contains(
            ((IClass) this.getEntity("Paragraph"))
                  .getIteratorOnImplementedInterfaces(),
            CompositeModelCreationTest.PKG + "Element"));
      Assert.assertTrue(CompositeModelCreationTest.contains(
            ((IClass) this.getEntity("Title"))
                  .getIteratorOnImplementedInterfaces(),
            CompositeModelCreationTest.PKG + "Element"));
   }


   public void testMembers() {
      final IFirstClassEntity document = this.getEntity("Document");
      Assert.assertTrue(document.getConstituentFromName(
            "elements") instanceof IField);
      Assert.assertTrue(
            document.getConstituentFromName("addComponent") instanceof IMethod);
      Assert.assertTrue(
            document.getConstituentFromName("printAll") instanceof IMethod);
      Assert.assertTrue(this.getEntity("AbstractDocument")
            .getConstituentFromName("print") instanceof IMethod);
   }


   public void testMethodInvocations() {
      final IMethod printAll = (IMethod) this.getEntity("Document")
            .getConstituentFromName("printAll");


      boolean found = false;
      final Iterator iterator = printAll
            .getIteratorOnConstituents(IMethodInvocation.class);
      while (iterator.hasNext()) {
         final IMethodInvocation invocation = (IMethodInvocation) iterator
               .next();
         if (invocation.getCalledMethod() != null
               && invocation.getCalledMethod().getDisplayName()
                     .equals("print")
               && invocation.getTargetEntity().getDisplayID().equals(
                     CompositeModelCreationTest.PKG + "AbstractDocument")) {
            found = true;
         }
      }
      Assert.assertTrue("printAll() should invoke AbstractDocument.print()",
            found);
   }
}
