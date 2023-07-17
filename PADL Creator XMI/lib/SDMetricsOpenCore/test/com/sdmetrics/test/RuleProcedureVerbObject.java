package com.sdmetrics.test;

import com.sdmetrics.math.ExpressionNode;
import com.sdmetrics.metrics.ProcedureAttributes;
import com.sdmetrics.metrics.Rule;
import com.sdmetrics.metrics.RuleProcedure;
import com.sdmetrics.metrics.SDMetricsException;
import com.sdmetrics.metrics.Variables;
import com.sdmetrics.model.ModelElement;

public class RuleProcedureVerbObject extends RuleProcedure {

	@Override
	public void checkRule(ModelElement element, Rule rule)
			throws SDMetricsException {

		String name = element.getName();

		ProcedureAttributes attributes = rule.getAttributes();
		ExpressionNode term = attributes.getExpression("term");
		Variables vars = new Variables(element);
		if (term != null)
			name = evalExpression(element, term, vars).toString();

		int boundary = name.indexOf(' ');
		if (boundary < 0) {
			reportViolation(element, rule,
					"Element does not specify an object.");
			return;
		}

		String verb = name.substring(0, boundary);
		String object = name.substring(boundary + 1);
		vars.setVariable("_verb", verb);
		vars.setVariable("_object", object);

		ExpressionNode condition = attributes
				.getRequiredExpression("condition");
		if (evalBooleanExpression(element, condition, vars)) {
			Object value = getRuleValue(element, attributes, vars);
			reportViolation(element, rule, value);
		}
	}
}
