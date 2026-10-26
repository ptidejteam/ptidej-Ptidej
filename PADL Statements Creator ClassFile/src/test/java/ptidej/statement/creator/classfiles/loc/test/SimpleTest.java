package ptidej.statement.creator.classfiles.loc.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;

import junit.framework.TestCase;
import ptidej.statement.creator.classfiles.loc.BCEL2PADLAdaptor;
import ptidej.statement.creator.classfiles.loc.BCELLOCFinder;
import ptidej.statement.creator.classfiles.loc.BCELLOCFinderSimplified;

public class SimpleTest extends TestCase {
	private static final String PATH = "../PADL Statements Creator ClassFile/target/test-classes/padl/kernel/impl/AbstractGenericContainerOfConstituents.class";

	private static final String CLASSID = "padl.kernel.impl.AbstractGenericContainerOfConstituents";
	private static final String METHODID = "broadcastAdditionOfConstituent(padl.kernel.IConstituent)";

	public void testBCELLOCFinders() {
		try {
			final FileInputStream fis = new FileInputStream(SimpleTest.PATH);
			final ClassParser parser = new ClassParser(fis, SimpleTest.PATH);
			final JavaClass clazz = parser.parse();

			final BCEL2PADLAdaptor adaptor = new BCEL2PADLAdaptor();

			final BCELLOCFinder bcelLOCFinder = new BCELLOCFinder();
			bcelLOCFinder.setAdaptor(adaptor);
			clazz.accept(bcelLOCFinder);

			final BCELLOCFinderSimplified bcelLOCFinderSimplified = new BCELLOCFinderSimplified();
			bcelLOCFinderSimplified.setAdaptor(adaptor);
			bcelLOCFinderSimplified.visitJavaClass(clazz);

			final Integer value = bcelLOCFinder.getInstructionCount(CLASSID,
					METHODID);
			final Integer valueSimplified = bcelLOCFinderSimplified
					.getInstructionCount(CLASSID, METHODID);

			Assert.assertNotNull(value);
			Assert.assertNotNull(valueSimplified);
			Assert.assertEquals(value, valueSimplified);

			fis.close();
		}
		catch (final ClassFormatException | IOException e) {
			e.printStackTrace();
		}
	}
}
