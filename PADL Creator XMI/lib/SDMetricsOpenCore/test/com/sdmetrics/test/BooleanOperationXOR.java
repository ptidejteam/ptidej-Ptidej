package com.sdmetrics.test;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.metrics.BooleanOperation;
import com.sdmetrics.metrics.SDMetricsException;
import com.sdmetrics.metrics.Variables;
import com.sdmetrics.model.ModelElement;

public class BooleanOperationXOR extends BooleanOperation {

	@Override
	public boolean calculateValue(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {

		int trueConditions = 0;
		int index = 0;
		while (index < node.getOperandCount() && trueConditions <= 1) {
			if (evalBooleanExpression(element, node.getOperand(index), vars)) {
				trueConditions++;
			}
			index++;
		}
		return trueConditions == 1;
	}
}
