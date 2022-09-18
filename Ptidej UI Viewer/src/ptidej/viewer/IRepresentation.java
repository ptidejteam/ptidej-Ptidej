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
package ptidej.viewer;

import java.util.List;
import java.util.Set;
import padl.kernel.IAbstractModel;
import ptidej.solver.Occurrence;
import ptidej.ui.canvas.Canvas;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/07/17
 */
public interface IRepresentation {
	String TYPE_AOL_CODE = "AOLCodeModel";
	String TYPE_AOL_IDIOM = "AOLIdiomModel";
	String TYPE_ASPECTJ = "AspectJIdiomModel";
	String TYPE_CPP = "CppIdiomModel";
	String TYPE_ECLIPSE_JDT_PROJECT = "EclipseJDTProjectIdiomModel";
	String TYPE_JAVA_CLASSFILES = "JavaClassfilesIdiomModel";
	String TYPE_JAVA_JAVAFILES = "JavaSourcefilesIdiomModel";
	String TYPE_MSE = "MSE Idiom Model";

	void addOccurrences(final Occurrence[] someOccurrences);
	void addSourceFile(final String aFileType, final String aPath);
	void addSourceFileName(final String aFileType, final String aName);
	void clearOccurrences();
	Builder getBuilder();
	Canvas getCanvas();
	// IGraphLayout getGraphLayout();
	Occurrence[] getOccurrences();
	IPrimitiveFactory getPrimitiveFactory();
	List getSourceFiles(final String aFileType);
	List getSourceFilesNames(final String aFileType);
	Set getSourceFileTypes();
	LaidoutModelGraph getSourceGraph();
	IAbstractModel getSourceModel();
	// void setBuilder(final Builder aBuilder);
	// void setCanvas(final Canvas aCanvas);
	// void setGraphLayout(final IGraphLayout aGraphLayout);
	// void setPrimitiveFactory(final IPrimitiveFactory aPrimitiveFactory);
	// void setSourceGraph(final ModelGraph anAbstractModelGraph);
	// void setSourceModel(final IAbstractModel anAbstractModel);
}
