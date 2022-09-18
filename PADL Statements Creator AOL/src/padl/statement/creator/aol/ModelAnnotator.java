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
package padl.statement.creator.aol;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractModel;

public abstract class ModelAnnotator implements IAnalysis {
	private final String[] fileNames;
	private final Map qualifedMethodsMetrics;

	public ModelAnnotator(final String[] someFileNames) {
		this.fileNames = someFileNames;
		this.qualifedMethodsMetrics = new HashMap();
	}
	private void annotateFromDir(final IAbstractModel anAbstractModel) {
		final IMetricValueAdder adder = this.getMetricValueAdder();
		adder.setQualifiedMethodsMetrics(this.qualifedMethodsMetrics);
		anAbstractModel.walk(adder);
	}
	private String findLine(final LineNumberReader reader, final String aHeader) {
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith(aHeader)) {
					return line.substring(aHeader.length());
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getName() {
		return "Annotator with Conditionals (AOL)";
	}
	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		for (int i = 0; i < this.fileNames.length; i++) {
			final String fileName = this.fileNames[i];
			this.relateQualifiedMethodMetric(fileName);
		}
		this.annotateFromDir(anAbstractModel);
		return anAbstractModel;
	}
	private void relateQualifiedMethodMetric(final String aFileName) {
		final String fileName =
			aFileName.replaceAll("concat_", "").replaceAll(".aol", ".met");
		try {
			final LineNumberReader reader =
				new LineNumberReader(new FileReader(fileName));
			String line;
			while ((line = this.findLine(reader, "	METHOD ")) != null) {
				final String qualifiedName =
					line.replace('!', '.').replace(' ', '_');
				final String metricValue =
					this.findLine(reader, this.getMetricHeader());
				this.qualifedMethodsMetrics.put(qualifiedName, metricValue);
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public abstract String getMetricHeader();
	public abstract IMetricValueAdder getMetricValueAdder();
}
