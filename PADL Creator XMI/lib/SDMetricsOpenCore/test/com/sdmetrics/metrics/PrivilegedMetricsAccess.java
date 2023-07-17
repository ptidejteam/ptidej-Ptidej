package com.sdmetrics.metrics;

import com.sdmetrics.math.ExpressionParser;

/**
 * Grants test-classes from other packages access to selected friendly methods
 * in this package.
 */
public class PrivilegedMetricsAccess {

	public static ExpressionParser getExpressionParser(MetricStore ms) {
		return ms.getExpressionOperations().getExpressionParser();
	}
}
