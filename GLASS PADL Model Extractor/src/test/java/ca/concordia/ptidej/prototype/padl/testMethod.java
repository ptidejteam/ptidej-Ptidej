package ca.concordia.ptidej.prototype.padl;

import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import ca.concordia.ptidej.prototype.ast.IMethod;
import ca.concordia.ptidej.prototype.ast.IProject;
import ca.concordia.ptidej.prototype.ast.IType;
import ca.concordia.ptidej.prototype.padl.ast.PADLProject;

public class testMethod {

	
	private static final String filePath = "../prototype.padl/src/test/ressources/src";
	private static final String className = "ShopFloorStaff";
	private static final String packageName = "testPackage.impl";
	private static IProject project;
	private static IType type;
	private static IMethod[] methods;
	
	@BeforeAll
	public static void setup() {
        project = new PADLProject(filePath);
        Collection<IType> types = project.getDefinedTypes();
        Iterator<IType> iterator = types.iterator();
        boolean found = false;
        while (iterator.hasNext() && !found) {
        	type = iterator.next();
        	found = type.getElementName().equals(className);
        }
        methods = type.getMethods();
	}
	
	@Test
	public void testGetSignature() {
		IMethod simpleMethod = methods[0];
		Assertions.assertEquals("public java.lang.Integer capabilities()",
				simpleMethod.getSignature());
		
		IMethod complexMethod = methods[1];
		Assertions.assertEquals("public testPackage.IRessource compatibleStaff(testPackage.impl.Machinery[])",
				complexMethod.getSignature());
	}
	
	@Test
	public void testGetElementName() {
		Assertions.assertEquals("capabilities", methods[0].getElementName());
	}
	
	@Test
	public void testGetParameterNames() {
		Assertions.assertArrayEquals(new String[0], methods[0].getParameterNames());
		String[] singleParameter = {"testPackage.impl.Machinery[]"};
		Assertions.assertArrayEquals(singleParameter, methods[1].getParameterNames());
	}
	
	@Test
	public void testGetReturnType() {
		Assertions.assertEquals("testPackage.IRessource", methods[1].getReturnType());
	}
		
		
		
}
