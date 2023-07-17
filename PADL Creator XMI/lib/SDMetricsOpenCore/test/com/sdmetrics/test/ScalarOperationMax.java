package com.sdmetrics.test;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.metrics.MetricTools;
import com.sdmetrics.metrics.SDMetricsException;
import com.sdmetrics.metrics.ScalarOperation;
import com.sdmetrics.metrics.Variables;
import com.sdmetrics.model.ModelElement;

public class ScalarOperationMax extends ScalarOperation {

	@Override
	public Number calculateValue(ModelElement element, ExpressionNode node,
			Variables vars) throws SDMetricsException {

		float result = Float.NEGATIVE_INFINITY;
		for (int index = 0; index < node.getOperandCount(); index++) {
			float value = ((Number) evalExpression(element,
					node.getOperand(index), vars)).floatValue();
			result = Math.max(result, value);
		}
		return MetricTools.getNumber(result);
	}
}
