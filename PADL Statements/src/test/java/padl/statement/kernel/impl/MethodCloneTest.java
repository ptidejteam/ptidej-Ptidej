package padl.statement.kernel.impl;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.util.ModelStatistics;
import padl.kernel.impl.Method;
import padl.kernel.impl.Operation;
import padl.kernel.IStatement;


public class MethodCloneTest extends TestCase{

	public MethodCloneTest(final String aName) {
		super(aName);
	}

	public void setUp() throws Exception {
		super.setUp();
	}
	public void testStartCloneSessiononMethods() {
		final ModelStatistics modelListenerOne = new ModelStatistics();
		final ModelStatistics modelListenerTwo = new ModelStatistics();
		
		final Method m1 = new Method("m1");
		final Method m2 = new Method("m2");
		
		m1.addModelListener(modelListenerOne);
		m2.addModelListener(modelListenerTwo);
		

		m1.startCloneSession();
		m1.performCloneSession();
		m1.endCloneSession();
		
		m2.startCloneSession();
		m2.performCloneSession();
		m2.endCloneSession();
		
		ModelStatistics m1Listener = (ModelStatistics) m1.getIteratorOnModelListeners().next();
		ModelStatistics m1CloneListener = (ModelStatistics) ((Operation) m1.getClone()).getIteratorOnModelListeners().next();
		
		ModelStatistics m2Listener = (ModelStatistics) m2.getIteratorOnModelListeners().next();
		ModelStatistics m2CloneListener = (ModelStatistics) ((Operation) m2.getClone()).getIteratorOnModelListeners().next();
		
		Assert.assertEquals(m1Listener, m1CloneListener);
		Assert.assertEquals(m2Listener, m2CloneListener);
		

	}
	
	public void testStartCloneSessionOnMethodFields() {
		final ModelStatistics modelListenerOne = new ModelStatistics();
		final ModelStatistics modelListenerTwo = new ModelStatistics();
		
		final Method m1 = new Method("m1");
		final Method m2 = new Method("m2");
		
		char[] statementLiteralOne = {'5', '*', '2'};
		char[] statementLiteralTwo = {'4', '*', '7'};
		
		IStatement statementOne = new Statement(statementLiteralOne);
		IStatement statementTwo = new Statement(statementLiteralTwo);
		
		m1.addConstituent(statementOne);
		m2.addConstituent(statementTwo);
		
		
		m1.addModelListener(modelListenerOne);
		m2.addModelListener(modelListenerTwo);
		
		
		m1.startCloneSession();
		m1.performCloneSession();
		m1.endCloneSession();
		
		m2.startCloneSession();
		m2.performCloneSession();
		m2.endCloneSession();
		
		Statement stmt1 = (Statement) m1.getConstituentFromName(statementLiteralOne);
		Statement clonedStmt1 = (Statement) ((Operation) m1.getClone()).getConstituentFromName(statementLiteralOne);
		
		Statement stmt2 = (Statement) m2.getConstituentFromName(statementLiteralTwo);
		Statement clonedStmt2 = (Statement) ((Operation) m2.getClone()).getConstituentFromName(statementLiteralTwo);
		
		Assert.assertEquals(stmt1.getName(), clonedStmt1.getName());
		Assert.assertEquals(stmt2.getName(), clonedStmt2.getName());
		

	}
	

}
