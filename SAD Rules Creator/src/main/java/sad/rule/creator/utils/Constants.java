/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.rule.creator.utils;

public interface Constants {

	// constants : ordinal values
	final int VERY_HIGH = 6;
	final int HIGH = 5;
	final int MEDIUM = 4;
	final int LOW = 3;
	final int VERY_LOW = 2;
	final int NONE = 1;

	// Cardinalities.
	final int CARDINALITY_ONE = 1;
	final int CARDINALITY_MANY = 2;
	final int CARDINALITY_ONE_OR_MANY = 3;
	final int CARDINALITY_OPTIONNALY_ONE = 4;

	// Semantic attribute
	final int CLASS_NAME = 1;
	final int METHOD_NAME = 2;
	final int FIELD_NAME = 3;

	// Structural element v2.0
	final int METHOD_ACCESSOR = 1;
	final int METHOD_NO_PARAM = 2;
	final int GLOBAL_VARIABLE = 3;
	final int DIFFERENT_PARAMETER = 4;
	final int ONE_METHOD = 5;
	final int PRIVATE_FIELD = 6;
	final int MULTIPLE_INTERFACE = 7;
	final int PUBLIC_FIELD = 8;
	final int IS_ABSTRACT = 9;

	// Relation
	final int RELATION_ASSOC = 1;
	final int RELATION_AGGREG = 2;
	final int RELATION_COMPOS = 3;
	final int RELATION_INHERIT = 4;

	// Operators
	final int OPERATOR_INTER = 1;
	final int OPERATOR_UNION = 2;
	final int OPERATOR_DIFF = 3;
	final int OPERATOR_INCL = 4;
	final int OPERATOR_NEG = 5;

	// Metric Operators
	final int PLUS = 0;
	final int MINUS = 1;
	// Metric Comparison Operators
	final int INF = 2; // (inferior) <  
	final int INF_EQ = 3; // (inferior or equal) <=
	final int EQ = 4; // (equal) ==
	final int SUP = 5; // (superior) >
	final int SUP_EQ = 6; // (superior or equal) >=
	final int NOT_EQ = 7; // (different) !=

}
