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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import padl.kernel.IAbstractModel;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.ui.IDrawable;
import ptidej.ui.layout.repository.SugiyamaLayout;
import ptidej.ui.occurrence.IOccurrencePrimitiveFactory;
import util.help.IHelpURL;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/07/17
 */
public class ViewerCommons {
	private static final int ANALYSIS_TYPE_ABSTRACTMODEL = 1;
	private static final int ANALYSIS_TYPE_REPRESENTATION = 2;
	private static final int ANALYSIS_TYPE_UNKNOWN = 0;

	public static void addAnalyses(
		final IViewer aViewer,
		final String aRepositoryPackageName,
		final String aRepositoryAccessor,
		final String anAnalysesAccessor,
		final boolean areToolsListening) {

		// Yann 2004/05/20: PADL Analyses!
		// I add a separator and a list of buttons to call the analyses.
		// (Plug-ins in Ptidej!!!!!!!! :-)
		// Yann 2004/12/09: Class definition!
		// I don't forget that the Repository of the analyses
		// might not be present...
		try {
			final Class analysisRepositoryClass =
				Class.forName(aRepositoryPackageName);
			final Method instantiationMethod =
				analysisRepositoryClass.getMethod(
					aRepositoryAccessor,
					new Class[0]);
			final Object analysisRepository =
				instantiationMethod.invoke(null, new Object[0]);
			final Method analysesGetterMethod =
				analysisRepositoryClass.getMethod(
					anAnalysesAccessor,
					new Class[0]);
			final Object[] analyses =
				(Object[]) analysesGetterMethod.invoke(
					analysisRepository,
					new Object[0]);

			if (analyses.length == 0) {
				return;
			}

			for (int i = 0; i < analyses.length; i++) {
				final int currentAnalysis = i;
				final Method analysesNameMethod =
					ViewerCommons.getNameMethod(analyses[currentAnalysis]);
				final Method analysesInvokeMethod =
					ViewerCommons.getInvokeMethod(analyses[currentAnalysis]);
				final int analysesType =
					ViewerCommons.getType(analyses[currentAnalysis]);

				final String analysisName =
					(String) analysesNameMethod.invoke(
						analyses[currentAnalysis],
						new Object[0]);
				ActionListener actionListener = null;

				if (analysesType == ViewerCommons.ANALYSIS_TYPE_ABSTRACTMODEL) {
					actionListener = new ActionListener() {
						public void actionPerformed(final ActionEvent e) {
							try {
								aViewer.addSource(
									(IAbstractModel) analysesInvokeMethod
										.invoke(
											analyses[currentAnalysis],
											new Object[] { aViewer
												.getRepresentation()
												.getSourceModel()
												.clone() }),
									aViewer.getRepresentation().getBuilder());
							}
							catch (final CloneNotSupportedException cnse) {
								cnse.printStackTrace(ProxyConsole
									.getInstance()
									.errorOutput());
							}
							catch (final IllegalAccessException iae) {
								iae.printStackTrace(ProxyConsole
									.getInstance()
									.errorOutput());
							}
							catch (final InvocationTargetException ite) {
								ite.printStackTrace(ProxyConsole
									.getInstance()
									.errorOutput());
							}
						}
					};
				}
				else if (analysesType == ViewerCommons.ANALYSIS_TYPE_REPRESENTATION) {
					actionListener = new ActionListener() {
						public void actionPerformed(final ActionEvent e) {
							try {
								analysesInvokeMethod
									.invoke(
										analyses[currentAnalysis],
										new Object[] { aViewer
											.getRepresentation() });
							}
							catch (final IllegalAccessException iae) {
								iae.printStackTrace(ProxyConsole
									.getInstance()
									.errorOutput());
							}
							catch (final InvocationTargetException ite) {
								ite.printStackTrace(ProxyConsole
									.getInstance()
									.errorOutput());
							}
						}
					};
				}
				else {
					ProxyConsole
						.getInstance()
						.errorOutput()
						.println("Unknown analysis type!");
				}

				// Yann 2007/03/30: Demo!
				// I make sure that some essential buttons
				// are still available even for the demo
				// version, which is not listening ;-)
				if (analyses[currentAnalysis] instanceof IHelpURL) {
					aViewer
						.addButton(
							analysisName,
							actionListener,
							false,
							areToolsListening
									|| analysisName
										.startsWith("AAC Relationships")
									|| analysisName
										.startsWith("Model Annotator with Instructions"),
							(IHelpURL) analyses[currentAnalysis]);
				}
				else {
					aViewer
						.addButton(
							analysisName,
							actionListener,
							false,
							areToolsListening
									|| analysisName
										.startsWith("AAC Relationships")
									|| analysisName
										.startsWith("Model Annotator with Instructions"));
				}
			}
		}
		catch (final SecurityException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IllegalArgumentException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private static Method getInvokeMethod(final Object anAnalysis) {
		final Class superInterface = anAnalysis.getClass().getInterfaces()[0];

		// Analyses can declare an invoke(IAbstractModel) method
		// or an invoke(Representation) method.
		// TODO: This whole piece of code is dirty!
		Method analysisMethod;
		try {
			analysisMethod =
				superInterface.getMethod(
					"invoke",
					new Class[] { IAbstractModel.class });
		}
		catch (final NoSuchMethodException e) {
			try {
				analysisMethod =
					superInterface.getMethod(
						"invoke",
						new Class[] { IRepresentation.class });
			}
			catch (final NoSuchMethodException e1) {
				analysisMethod = null;
			}
		}

		return analysisMethod;
	}
	private static Method getNameMethod(final Object anAnalysis) {
		final Class superInterface = anAnalysis.getClass().getInterfaces()[0];
		try {
			return superInterface.getMethod("getName", new Class[0]);
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return null;
		}
	}
	private static int getType(final Object anAnalysis) {
		final Class superInterface = anAnalysis.getClass().getInterfaces()[0];
		try {
			superInterface.getMethod(
				"invoke",
				new Class[] { IAbstractModel.class });
			return ViewerCommons.ANALYSIS_TYPE_ABSTRACTMODEL;
		}
		catch (final NoSuchMethodException e) {
			try {
				superInterface.getMethod(
					"invoke",
					new Class[] { ptidej.viewer.IRepresentation.class });
				return ViewerCommons.ANALYSIS_TYPE_REPRESENTATION;
			}
			catch (final NoSuchMethodException e1) {
				return ViewerCommons.ANALYSIS_TYPE_UNKNOWN;
			}
		}
	}
	//	private static Builder getBuilder(
	//		final IViewer aViewer,
	//		final Object[] someAnalyses,
	//		final int aCurrentAnalysis) {
	//
	//		Builder builder = null;
	//		try {
	//			final Method getBuilder =
	//				someAnalyses[aCurrentAnalysis].getClass().getMethod(
	//					"createBuilder",
	//					new Class[] { IPrimitiveFactory.class });
	//			builder =
	//				(Builder) getBuilder.invoke(
	//					someAnalyses[aCurrentAnalysis],
	//					new Object[] {
	//						 aViewer.getRepresentation().getPrimitiveFactory()});
	//		}
	//		catch (final SecurityException se) {
	//			se.printStackTrace();
	//		}
	//		catch (final NoSuchMethodException nsme) {
	//			// nsme.printStackTrace();
	//		}
	//		catch (final IllegalAccessException iae) {
	//			iae.printStackTrace();
	//		}
	//		catch (final InvocationTargetException ite) {
	//			ite.printStackTrace();
	//		}
	//		if (builder == null) {
	//			builder =
	//				Builder.getCurrentBuilder(
	//					aViewer.getRepresentation().getPrimitiveFactory());
	//		}
	//
	//		return builder;
	//	}
	public static void loadConstraintsData(
		final IRepresentation aRepresentation,
		final Properties properties) {

		final Occurrence[] solutions =
			OccurrenceBuilder.getInstance().getCanonicalOccurrences(properties);

		// Yann 2006/08/20: Refresh...
		// I only set occurrences now, in case some object
		// uses this to refresh itself.
		aRepresentation.addOccurrences(solutions);

		// Yann 2007/10/01: Creation of the visual occurrences.
		// I create the GroupOccurrences and add them to the canvas.
		ViewerCommons.createGroupSolutions(aRepresentation);
	}
	public static GroupOccurrence[] createGroupSolutions(
		final IRepresentation aRepresentation) {

		// Yann 2014/06/20: Don't repeat yourself!
		// Because aRepresentation.getOccurrences() contains
		// all existing occurrences, I remove any already
		// shown group occurrences, they will be added back
		// again below, in the following loop on getOccurrences().
		aRepresentation.getCanvas().EntityRolesCount.clear();
		final IDrawable[] drawables =
			aRepresentation.getCanvas().getBackgroundElements();
		for (int i = 0; i < drawables.length; i++) {
			if (drawables[i] instanceof GroupOccurrence) {
				aRepresentation.getCanvas().removeFromBackground(drawables[i]);
			}
		}
		//	drawables = aRepresentation.getCanvas().getForegroundElements();
		//	for (int i = 0; i < drawables.length; i++) {
		//		if (drawables[i] instanceof IGroupOccurrenceTip) {
		//			aRepresentation.getCanvas().removeFromForeground(drawables[i]);
		//		}
		//	}

		// Yann 2002/08/13: Dick Reverse...
		// I put the solutions in reverse order because the constituents
		// added to the background are displayed also in reverse.
		// (Thus, the more distorted solutions appear first.)
		final Occurrence[] occurrences = aRepresentation.getOccurrences();
		final GroupOccurrence[] groupOccurrences =
			new GroupOccurrence[occurrences.length];
		for (int i = occurrences.length - 1; i >= 0; i--) {
			groupOccurrences[i] =
				new GroupOccurrence(
					aRepresentation.getCanvas(),
					(IOccurrencePrimitiveFactory) aRepresentation
						.getPrimitiveFactory(),
					occurrences[i],
					OccurrenceBuilder.getInstance(),
					aRepresentation.getSourceGraph(),
					new SugiyamaLayout());
			groupOccurrences[i].build();

			// Yann 2002/06/30: Bug fix?
			// I must consider that a solution (sometimes) is not built
			// correctly by the constraint solver (how possible? ;-) and
			// thus might not contain graphical element to display.
			// Yann 2014/05/09: isShowable() un-showable?
			// It is not up to this method to decide what to show or not
			// but up to the Canvas: I must add the groupOccurrence here.
			//	if (groupOccurrences[i].isShowable()) {
			aRepresentation.getCanvas().addToBackground(groupOccurrences[i]);
			//	}
		}

		return groupOccurrences;
	}
}
