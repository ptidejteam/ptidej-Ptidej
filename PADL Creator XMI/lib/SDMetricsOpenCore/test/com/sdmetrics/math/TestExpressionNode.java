package com.sdmetrics.math;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class TestExpressionNode {

	@Test
	public void construction() {

		ExpressionNode identifier = new ExpressionNode("HAL9000");
		assertTrue(identifier.isIdentifier());
		assertFalse(identifier.isOperation() || identifier.isNumberConstant()
				|| identifier.isStringConstant());
		assertEquals("HAL9000", identifier.getValue());
		assertNull(identifier.getLeftNode());
		assertNull(identifier.getRightNode());
		assertEquals(0, identifier.getOperandCount());
		try {
			identifier.getOperand(0);
			fail("Exception expected");
		} catch (IllegalArgumentException ex) {
			assertEquals("No argument at index 0 for operator 'HAL9000'.",
					ex.getMessage());
		}

		ExpressionNode number = new ExpressionNode(NodeType.NUMBER, "2001");
		assertTrue(number.isNumberConstant());
		assertFalse(number.isOperation() || number.isIdentifier()
				|| number.isStringConstant());
		assertEquals("2001", number.getValue());

		ExpressionNode unary = new ExpressionNode("-",
				new ExpressionNode[] { number });
		assertEquals(1, unary.getOperandCount());
		assertSame(number, unary.getLeftNode());
		assertSame(null, unary.getRightNode());

		ExpressionNode operation = new ExpressionNode("+",
				new ExpressionNode[] { identifier, number });
		assertTrue(operation.isOperation());
		assertFalse(operation.isNumberConstant() || operation.isIdentifier()
				|| operation.isStringConstant());
		assertEquals("+", operation.getValue());
		assertEquals(2, operation.getOperandCount());
		assertSame(identifier, operation.getLeftNode());
		assertSame(identifier, operation.getOperand(0));
		assertSame(number, operation.getRightNode());
		assertSame(number, operation.getOperand(1));
	}

	@Test
	public void setter() {
		ExpressionNode string = new ExpressionNode("");
		string.setValue(NodeType.STRING, "Discovery", null);
		assertTrue(string.isStringConstant());
		assertFalse(string.isNumberConstant() || string.isIdentifier()
				|| string.isOperation());
		assertEquals("Discovery", string.getValue());
		assertNull(string.getLeftNode());
		assertNull(string.getRightNode());
	}
}
