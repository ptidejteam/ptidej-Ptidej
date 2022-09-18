package com.sdmetrics.test;

import java.util.Collection;
import java.util.Iterator;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.metrics.MetricTools;
import com.sdmetrics.metrics.SDMetricsException;
import com.sdmetrics.metrics.SetOperation;
import com.sdmetrics.metrics.Variables;
import com.sdmetrics.model.ModelElement;

public class SetOperationSymmDiff extends SetOperation {

	@Override
	public Collection<?> calculateValue(ModelElement element,
			ExpressionNode node, Variables vars) throws SDMetricsException {

		Collection<?> left = evalSetExpression(element, node.getOperand(0),
				vars);
		Collection<?> right = evalSetExpression(element, node.getOperand(1),
				vars);

		boolean isMultiSet = MetricTools.isMultiSet(right)
				|| MetricTools.isMultiSet(left);
		Collection<?> result = MetricTools.createHashSet(isMultiSet);

		// process elements from the first set
		Iterator<?> it = MetricTools.getFlatIterator(left);
		while (it.hasNext()) {
			processElement(it.next(), result, left, right);
		}

		// process additional elements from the second set
		it = MetricTools.getFlatIterator(right);
		while (it.hasNext()) {
			Object o = it.next();
			if (!left.contains(o)) {
				processElement(o, result, left, right);
			}
		}
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void processElement(Object o, Collection col, Collection<?> left,
			Collection<?> right) {
		int leftCount = MetricTools.elementCount(left, o);
		int rightCount = MetricTools.elementCount(right, o);
		int count = Math.abs(leftCount - rightCount);
		for (int i = 0; i < count; i++)
			col.add(o);
	}
}
