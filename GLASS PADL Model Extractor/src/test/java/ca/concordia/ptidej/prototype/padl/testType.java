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


public class testType{
	
	
	private static final String filePath = "../prototype.padl/src/test/ressources/src";
	private static final String className = "ShopFloorStaff";
	private static final String packageName = "testPackage.impl";
	private static IProject project;
	private static IType type;
	
	
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
	}
	
	@Test
	public void testGetMethods() {
        IMethod[] methods = type.getMethods();

        Assertions.assertEquals("capabilities", methods[0].getElementName());
        Assertions.assertEquals("compatibleStaff", methods[1].getElementName());
        
        Assertions.assertEquals("public java.lang.Integer capabilities()", methods[0].getSignature());
        Assertions.assertEquals("public testPackage.IRessource CompatibleStaff(testPackage.impl.Machinery[])",
        		methods[1].getSignature()); // for some reason we have a ghost package
	}
	
	@Test
	public void testIsInterface() {
		Assertions.assertFalse(type.isInterface());
	}
	
	@Test
	public void testElementName() {
		Assertions.assertEquals(className, type.getElementName());
	}
	
	@Test
	public void testFullyQualifiedName() {
		Assertions.assertEquals("testPackage.impl.ShopFloorStaff", type.getFullyQualifiedName());
	}
	
	@Test
	public void testSubtypes() {
		IType[] subTypes = type.getAllSubtypes();
		Assertions.assertEquals("testPackage.impl.Driver", subTypes[0].getFullyQualifiedName());
	}

}
