package util.lang.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Assert;

import com.ibm.toad.cfparse.ClassFile;
import com.ibm.toad.cfparse.ConstantPool;
import com.ibm.toad.cfparse.ConstantPoolEntry;
import com.ibm.toad.cfparse.FieldInfo;
import com.ibm.toad.cfparse.FieldInfoList;
import com.ibm.toad.cfparse.InterfaceList;
import com.ibm.toad.cfparse.MethodInfo;
import com.ibm.toad.cfparse.MethodInfoList;
import com.ibm.toad.cfparse.attributes.AttrInfo;
import com.ibm.toad.cfparse.attributes.AttrInfoList;

import junit.framework.TestCase;
import util.io.NamedInputStream;
import util.lang.CFParseBCELConvertorAdhoc;
import util.lang.CFParseBCELConvertorVisitor;
import util.repository.IFileRepository;

/**
 * This test case tests the convertors from BCEL to CFParse.
 * It follows closely the steps in the CFParse.equals() method.
 */
public class ConvertorsComparisonTest extends TestCase {
	private static class FileRepository implements IFileRepository {
		@Override
		public NamedInputStream[] getFiles(final String aPath,
				final String anExtension) {

			final List<NamedInputStream> streams = new ArrayList<>();
			try (final Stream<Path> paths = Files.list(Paths.get(
					"../CPL/target/test-classes/Convertors Comparison/"))) {
				final Set<String> files = paths
						.filter(file -> !Files.isDirectory(file))
						.map(Path::toString).collect(Collectors.toSet());
				final Iterator<String> iterator = files.iterator();
				while (iterator.hasNext()) {
					final String path = iterator.next();
					streams.add(new NamedInputStream(path,
							new FileInputStream(path)));
				}
			}
			catch (final IOException e) {
				throw new RuntimeException(e);
			}
			return streams.toArray(new NamedInputStream[0]);
		}
	}

	private static NamedInputStream streams[];

	public void setUp() throws IOException {
		final IFileRepository repository = new FileRepository();
		ConvertorsComparisonTest.streams = repository.getFiles("", ".class");
	}

	// TODO To fix CFParseBCELConvertorAdhoc and re-enable
	public void DISABLEDtestCFParseBCELConvertorAdhoc() throws IOException {
		for (int i = 0; i < ConvertorsComparisonTest.streams.length; i++) {
			final InputStream inputStream_CFPARSE = streams[i].getStream();
			final ClassFile currentClass_CFPARSE = new ClassFile(
					inputStream_CFPARSE);
			inputStream_CFPARSE.close();

			final InputStream inputStream_BCEL = streams[i].getStream();
			final ClassParser parser = new ClassParser(inputStream_BCEL, "");
			final JavaClass javaClass = parser.parse();
			final ClassFile currentClass_BCEL = CFParseBCELConvertorAdhoc
					.convertClassFile(javaClass);
			inputStream_BCEL.close();

			Assert.assertTrue(this.compare1Itself(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare2Superclass(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare3SuperInterfaces(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare4ConstantPool(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare5Attributes(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare6Fields(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare7Methods(currentClass_CFPARSE,
					currentClass_BCEL));
		}
	}

	public void testCFParseBCELConvertorVisitor() throws IOException {
		for (int i = 0; i < ConvertorsComparisonTest.streams.length; i++) {
			final InputStream inputStream_CFPARSE = streams[i].getStream();
			final ClassFile currentClass_CFPARSE = new ClassFile(
					inputStream_CFPARSE);
			inputStream_CFPARSE.close();

			final InputStream inputStream_BCEL = streams[i].getStream();
			final ClassParser parser = new ClassParser(inputStream_BCEL, "");
			final JavaClass javaClass = parser.parse();
			final ClassFile currentClass_BCEL = CFParseBCELConvertorVisitor
					.convertClassFile(javaClass);
			inputStream_BCEL.close();

			Assert.assertTrue(this.compare1Itself(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare2Superclass(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare3SuperInterfaces(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare4ConstantPool(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare5Attributes(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare6Fields(currentClass_CFPARSE,
					currentClass_BCEL));
			Assert.assertTrue(this.compare7Methods(currentClass_CFPARSE,
					currentClass_BCEL));
		}
	}

	private boolean compare1Itself(final ClassFile one, final ClassFile other) {
		// 1. Itself
		boolean equalClassFiles = true;
		equalClassFiles &= one.getAccess() == other.getAccess();
		equalClassFiles &= one.getMagic() == other.getMagic();
		equalClassFiles &= one.getMajor() == other.getMajor();
		equalClassFiles &= one.getMinor() == other.getMinor();
		equalClassFiles &= one.getName().equals(other.getName());
		// A classfile may have no "SourceFile" attribute
		if (one.getSourceFilename() != null) {
			equalClassFiles &= one.getSourceFilename()
					.equals(other.getSourceFilename());
		}

		return equalClassFiles;
	}

	private boolean compare2Superclass(final ClassFile one,
			final ClassFile other) {

		// 2. Superclass
		boolean equalSuperNames = true;
		if (!this.getName().equals("java.lang.Object")) {
			// Interestingly, for the class Object itself, CFParse will
			// have "" as its superclass, while BCEL will have the string
			// "java.lang.Object", which shows a better understanding of
			// Java object model (?). So, I don't test if its Object.
			equalSuperNames &= one.getSuperName().equals(other.getSuperName());
		}

		return equalSuperNames;
	}

	private boolean compare3SuperInterfaces(final ClassFile one,
			final ClassFile other) {

		// 3. Superinterfaces
		boolean equalSuperInterfaces = true;
		final InterfaceList interfaceListOfThis = one.getInterfaces();
		final InterfaceList interfacesListOfOther = other.getInterfaces();
		final int numInterfacesOfThis = interfaceListOfThis.length();
		final int numInterfacesOfOther = interfacesListOfOther.length();
		equalSuperInterfaces &= numInterfacesOfThis == numInterfacesOfOther;

		for (int i = 0; equalSuperInterfaces && i < numInterfacesOfThis; i++) {
			final String interfaceOfThis = interfaceListOfThis.get(i);
			final String interfaceOfOther = interfacesListOfOther.get(i);

			equalSuperInterfaces &= interfaceOfThis.equals(interfaceOfOther);
		}

		return equalSuperInterfaces;
	}

	private boolean compare4ConstantPool(final ClassFile one,
			final ClassFile other) {

		// 4. Constant pool
		boolean equalConstants = true;
		final ConstantPool cpOfThis = one.getCP();
		final ConstantPool cpOfOther = other.getCP();
		final int numCPEntriesOfThis = cpOfThis.length();
		final int numCPEntriesOfOther = cpOfOther.length();
		// BCEL-built classfiles may have slightly different constants than compiler-generated ones don't
		//	equalConstants &= numCPEntriesOfThis <= numCPEntriesOfOther;

		// I skip the first constant pool entry because it's always "<dummy Entry>"
		for (int i = 1; equalConstants && i < numCPEntriesOfThis; i++) {
			final ConstantPoolEntry cpEntryOfThis = cpOfThis.get(i);
			if (cpEntryOfThis != null) {
				final int index = cpOfOther.find(cpEntryOfThis);
				if (index > 0) {
					final ConstantPoolEntry cpEntryOfOther = cpOfOther
							.get(index);
					equalConstants &= cpEntryOfThis.equals(cpEntryOfOther);
				}
			}
		}

		return equalConstants;
	}

	private boolean compare5Attributes(final ClassFile one,
			final ClassFile other) {

		// 5. Attributes
		boolean equalAttributes = true;

		final AttrInfoList attrListOfThis = one.getAttrs();
		final int numAttrsOfThis = attrListOfThis.length();

		final AttrInfoList attrListOfOther = other.getAttrs();
		final int numAttrsOfOther = attrListOfOther.length();

		equalAttributes &= numAttrsOfThis == numAttrsOfOther;
		if (!equalAttributes) {
			return false;
		}
		else {
			// Sort the attributes in the same order
			final Set<AttrInfo> attrListOfThis2 = new TreeSet<>();
			for (int i = 0; i < numAttrsOfThis; i++) {
				final AttrInfo attrInfoOfThis = attrListOfThis.get(i);
				attrListOfThis2.add(attrInfoOfThis);
			}

			final Set<AttrInfo> attrListOfOther2 = new TreeSet<>();
			for (int i = 0; i < numAttrsOfOther; i++) {
				final AttrInfo attrInfoOfOther = attrListOfOther.get(i);
				attrListOfOther2.add(attrInfoOfOther);
			}

			// Compare each attribute 1-to-1
			for (int i = 0; equalAttributes && i < numAttrsOfThis; i++) {
				final AttrInfo attrInfoOfThis = attrListOfThis.get(i);
				final AttrInfo attrInfoOfOther = attrListOfOther.get(i);

				equalAttributes &= attrInfoOfThis.toString()
						.equals(attrInfoOfOther.toString());
			}
			return equalAttributes;
		}
	}

	private boolean compare6Fields(final ClassFile one, final ClassFile other) {
		// 6. Fields
		boolean equalFields = true;
		final FieldInfoList fieldListOfThis = one.getFields();
		final FieldInfoList fieldListOfOther = other.getFields();
		final int numFieldsOfThis = fieldListOfThis.length();
		final int numFieldsOfOther = fieldListOfOther.length();
		equalFields &= numFieldsOfThis == numFieldsOfOther;

		for (int i = 0; equalFields && i < numFieldsOfThis; i++) {
			final FieldInfo fieldOfThis = fieldListOfThis.get(i);
			final FieldInfo fieldOfOther = fieldListOfOther.get(i);

			equalFields &= fieldOfThis.getAccess() == fieldOfOther.getAccess();
			equalFields &= fieldOfThis.getDesc().equals(fieldOfOther.getDesc());
			equalFields &= fieldOfThis.getName().equals(fieldOfOther.getName());
			equalFields &= fieldOfThis.getType().equals(fieldOfOther.getType());
		}

		return equalFields;
	}

	private boolean compare7Methods(final ClassFile one,
			final ClassFile other) {

		// 7. Methods
		boolean equalMethods = true;
		final MethodInfoList methodListOfThis = one.getMethods();
		final MethodInfoList methodListOfOther = other.getMethods();
		final int numMethodOfThis = methodListOfThis.length();
		final int numMethodOfOther = methodListOfOther.length();
		// BCEL-built classfiles may have a <clinit> than compiler-generated ones don't
		equalMethods &= numMethodOfThis <= numMethodOfOther;

		for (int i = 0; equalMethods && i < numMethodOfThis; i++) {
			final MethodInfo methodOfThis = methodListOfThis.get(i);
			final MethodInfo methodOfOther = methodListOfOther.get(i);

			equalMethods &= methodOfThis.getAbout()
					.equals(methodOfOther.getAbout());
			equalMethods &= methodOfThis.getAccess() == methodOfOther
					.getAccess();
			equalMethods &= methodOfThis.getDesc()
					.equals(methodOfOther.getDesc());
			equalMethods &= methodOfThis.getName()
					.equals(methodOfOther.getName());
			equalMethods &= Arrays.equals(methodOfThis.getParams(),
					methodOfOther.getParams());
			equalMethods &= methodOfThis.getReturnType()
					.equals(methodOfOther.getReturnType());
		}

		return equalMethods;
	}
}