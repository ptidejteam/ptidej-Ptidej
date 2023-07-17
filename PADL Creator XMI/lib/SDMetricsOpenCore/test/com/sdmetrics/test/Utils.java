package com.sdmetrics.test;

import com.sdmetrics.model.MetaModelElement;
import com.sdmetrics.model.ModelElement;

public class Utils {

	public final static String TEST_APP_DIR = "test/com/sdmetrics/app/";
	public final static String TEST_METRICS_DIR = "test/com/sdmetrics/metrics/";
	public final static String TEST_MODEL_DIR = "test/com/sdmetrics/model/";
	public final static String TEST_OUTPUT_DIR = "test/com/sdmetrics/output/";
	public final static String TEST_UTIL_DIR = "test/com/sdmetrics/util/";

	public static String dump(ModelElement e) {
		MetaModelElement type = e.getType();
		StringBuilder s = new StringBuilder(type.getName());
		s.append("(");
		for (String attrName : type.getAttributeNames()) {
			if (s.length() > 0)
				s.append(',');
			s.append(attrName);
			s.append(':');
			if (type.isSetAttribute(attrName))
				s.append(e.getSetAttribute(attrName));
			else if (type.isRefAttribute(attrName))
				s.append(e.getRefAttribute(attrName) + " (ref)");
			else
				s.append(e.getPlainAttribute(attrName) + " (data)");
		}
		s.append(')');
		return s.toString();
	}
}
