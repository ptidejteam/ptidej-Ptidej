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
package ptidej.viewer.ui;

import java.awt.Cursor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import javax.swing.JInternalFrame;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;

import padl.event.EventGenerator;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.ModelDeclarationException;
import padl.util.Util;
import ptidej.solver.Occurrence;
import ptidej.ui.IVisibility;
import ptidej.ui.awt.occurrence.PrimitiveFactory;
import ptidej.ui.canvas.event.CanvasEvent;
import ptidej.ui.canvas.event.ICanvasListener;
import ptidej.ui.event.GraphEvent;
import ptidej.ui.event.ISelectionListener;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.builder.AspectJBuilder;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.layout.IUILayout;
import ptidej.ui.primitive.IPrimitiveFactory;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.awt.IAWTRepresentation;
import ptidej.viewer.event.IGraphModelListener;
import ptidej.viewer.event.ISourceModelListener;
import ptidej.viewer.event.SourceAndGraphModelEvent;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.widget.HierarchicalTreeCell;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/31
 */
public abstract class AbstractRepresentationWindow extends JInternalFrame
		implements IAWTRepresentation, IWindow {

	protected static final class CanvasListenerForRefreshing implements
			ICanvasListener {

		private final Callable callbackMethod;

		public CanvasListenerForRefreshing(final Callable aMethodToCallback) {
			this.callbackMethod = aMethodToCallback;
		}
		public void backgroundElementSelected(final CanvasEvent aCanvasEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.backgroundElementSelected(aCanvasEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		public void backgroundElementUnSelected(final CanvasEvent aCanvasEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.backgroundElementUnSelected(aCanvasEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		public void constituentSelected(final GraphEvent aGraphEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.constituentSelected(aGraphEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		public void constituentUnSelected(final GraphEvent aGraphEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.constituentUnSelected(aGraphEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		public void foregroundElementSelected(final CanvasEvent aCanvasEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.foregroundElementSelected(aCanvasEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
		public void foregroundElementUnSelected(final CanvasEvent aCanvasEvent) {
			// Yann 2007/08/19: Canvas!
			// If something changes in the Canvas, some
			// general ICanvasListeners may be interested 
			// in them and should be notified.
			final Iterator iterator =
				DesktopPane.getInstance().getIteratorOnCanvasListeners();
			while (iterator.hasNext()) {
				final ICanvasListener canvasModelListener =
					(ICanvasListener) iterator.next();
				canvasModelListener.foregroundElementUnSelected(aCanvasEvent);
			}

			try {
				this.callbackMethod.call();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
		}
	};
	protected static class SelectionListener implements ISelectionListener {
		private final DefaultMutableTreeNode treeRoot;
		public SelectionListener(final DefaultMutableTreeNode aTreeRoot) {
			this.treeRoot = aTreeRoot;
		}
		public void constituentSelected(final GraphEvent aGraphEvent) {
			this.constituentSelected(aGraphEvent, true);
		}
		private void constituentSelected(
			final GraphEvent aGraphEvent,
			final boolean isSelected) {

			final Enumeration enumeration =
				this.treeRoot.depthFirstEnumeration();
			while (enumeration.hasMoreElements()) {
				final DefaultMutableTreeNode node =
					(DefaultMutableTreeNode) enumeration.nextElement();
				final HierarchicalTreeCell cell =
					(HierarchicalTreeCell) node.getUserObject();

				final IConstituent sourceConstituent = cell.getConstituent();

				final Constituent graphConstituent = aGraphEvent.getSource();
				if (graphConstituent.getName().equals(
					sourceConstituent.getDisplayID())) {
					cell.setSelectedWithoutNotification(isSelected);
				}
			}
		}
		public void constituentUnSelected(final GraphEvent aGraphEvent) {
			this.constituentSelected(aGraphEvent, false);
		}
	}
	private class Worker extends SwingWorker {
		private final Builder builder;
		// As per http://www.oracle.com/technetwork/articles/javase/swingworker-137249.html#Swing
		private final String generatorMethodToCall;
		private final String[] setOfFiles;
		private String name;

		public Worker(
			final String aGeneratorMethodToCall,
			final String[] aSetOfFiles,
			final String[] aSetOfNames,
			final Builder aBuilder) {

			this.generatorMethodToCall = aGeneratorMethodToCall;
			this.setOfFiles = aSetOfFiles;
			final StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < aSetOfNames.length; i++) {
				final String name = aSetOfNames[i];
				buffer.append(name);
				if (i < aSetOfNames.length - 1) {
					buffer.append(", ");
				}
			}
			this.name = buffer.toString();
			this.builder = aBuilder;
		}
		protected Object doInBackground() throws IllegalAccessException,
				IllegalArgumentException, InvocationTargetException,
				ModelDeclarationException, NoSuchMethodException,
				SecurityException {

			// Nothing related to the UI and the EDT...

			final Method theGeneratorMethodToCall =
				ModelGenerator.class.getMethod(
					this.generatorMethodToCall,
					new Class[] { String.class, String[].class });
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) theGeneratorMethodToCall.invoke(
					null,
					new Object[] { this.name, this.setOfFiles });
			return idiomLevelModel;
		}
		protected void done() {
			try {
				final IIdiomLevelModel idiomLevelModel =
					(IIdiomLevelModel) this.get();
				idiomLevelModel
					.addModelListener(AbstractRepresentationWindow.this
						.getModelStatistics());
				idiomLevelModel.walk(EventGenerator.getInstance());
				AbstractRepresentationWindow.this.setBuilder(this.builder);
				AbstractRepresentationWindow.this
					.setSourceModel(idiomLevelModel);
				AbstractRepresentationWindow.this.setCursor(Cursor
					.getDefaultCursor());

				// Yann 2013/09/28: Availability!
				// I should not forget to let everyone know 
				// that a window is now available. It is kind
				// of stupid but it seems the best to do...
				// Yann 2014/03/28: Listeners!
				// Now that the listeners are a cleaner, no need...
				//	AbstractRepresentationWindow.this.setSelected(false);
				//	AbstractRepresentationWindow.this.setSelected(true);
			}
			catch (final InterruptedException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final ExecutionException e) {
				final Throwable cause = e.getCause();
				cause.printStackTrace(ProxyConsole.getInstance().errorOutput());
				final Throwable rootCause = cause.getCause();
				if (rootCause != null) {
					rootCause.printStackTrace(ProxyConsole
						.getInstance()
						.errorOutput());
				}
			}
		}
	}
	private static final long serialVersionUID = 1L;

	private Builder builder;
	protected final Map files;
	protected final Map names;
	private Occurrence[] occurrences;
	protected IAbstractModel sourceModel;

	private int visibleElements;

	protected AbstractRepresentationWindow() {
		this.setFrameIcon(ptidej.ui.Utils.getIcon("PtidejIcon.png"));
		DesktopPane.getInstance().setLayer(this, Resources.PROJECTS_LAYER);
		DesktopPane.getInstance().add(this);

		this.files = new HashMap();
		this.names = new HashMap();
		this.occurrences = new Occurrence[0];
		// Yann 2013/09/28: Logics
		// I set up a default visibility here rather than
		// having the constructors of my subclass do it
		// and notify people of a change that did not happen...
		this.visibleElements =
			IVisibility.HIERARCHY_DISPLAY_ELEMENTS
					| IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS;
	}
	public void addOccurrences(final Occurrence[] someOccurrences) {
		final Occurrence[] temp =
			new Occurrence[this.occurrences.length + someOccurrences.length];
		System.arraycopy(this.occurrences, 0, temp, 0, this.occurrences.length);
		System.arraycopy(
			someOccurrences,
			0,
			temp,
			this.occurrences.length,
			someOccurrences.length);
		this.occurrences = temp;
		this.refresh();
	}
	// TODO: This method kind of conflict with setSourceModel(). 
	// indeed, the building of a model and the storage of the
	// files orgin of this model should be coupled, not separated
	// outside of the IRepresentation because it makes the process
	// more complicated and "outsourced".
	public void addSourceFile(final String aFileTypeName, final String aPath) {
		final List listOfFiles;
		if (this.files.containsKey(aFileTypeName)) {
			listOfFiles = (List) this.files.get(aFileTypeName);
		}
		else {
			listOfFiles = new ArrayList();
		}
		listOfFiles.add(aPath);
		this.files.put(aFileTypeName, listOfFiles);
	}
	public void addSourceFileName(final String aFileTypeName, final String aName) {
		final List listOfNames;
		if (this.names.containsKey(aFileTypeName)) {
			listOfNames = (List) this.names.get(aFileTypeName);
		}
		else {
			listOfNames = new ArrayList();
		}
		listOfNames.add(aName);
		this.names.put(aFileTypeName, listOfNames);
	}
	void bootstrapListeningWindows() {
		// Yann 2014/03/28: Friends!
		// A form of friendship e la C++ between the
		// AbstractExternalWindow and AbstractInternalWindow
		// on the one hand and the AbstractRepresentationWindow
		// on the other hand.
		final Iterator iteratorOnSourceModelListeners =
			DesktopPane.getInstance().getIteratorOnSourceModelListeners();
		while (iteratorOnSourceModelListeners.hasNext()) {
			final ISourceModelListener graphModelListener =
				(ISourceModelListener) iteratorOnSourceModelListeners.next();
			graphModelListener
				.sourceModelAvailable(new SourceAndGraphModelEvent(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()));
		}
		final Iterator iteratorOnGraphModelListeners =
			DesktopPane.getInstance().getIteratorOnGraphModelListeners();
		while (iteratorOnGraphModelListeners.hasNext()) {
			final IGraphModelListener graphModelListener =
				(IGraphModelListener) iteratorOnGraphModelListeners.next();
			graphModelListener
				.graphModelAvailable(new SourceAndGraphModelEvent(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()));
		}
	}
	public final void build() {
		// Will be set to default when the worker thread terminates.
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		// Yann 2007/02/23: Hack to support AOL idiom-level files
		Iterator fileTypes = this.getSourceFileTypes().iterator();
		boolean foundIdiomLevel = false;
		while (fileTypes.hasNext() && !foundIdiomLevel) {
			final String fileTypeName = (String) fileTypes.next();
			if (fileTypeName.equals(IRepresentation.TYPE_AOL_IDIOM)) {
				foundIdiomLevel = true;
			}
		}

		// Yann 2004/08/11: Multilinguism!
		// Being in Canada, better be multilingual ;-). From now on,
		// I just don't store the path to .class, .jar, but I associate
		// paths (to .class, .jar, .cpp, .aol...) with the appropriate
		// creator to analyse these files. Each creator is responsible
		// for adding the appropriate entities to the current idiom level
		// model and their respective elements.
		// TODO: How to *really* treat multilingual projects? For instance
		// how to link a Java class with the appropriate function used as
		// native and defined in a C++ source???
		fileTypes = this.getSourceFileTypes().iterator();
		while (fileTypes.hasNext()) {
			final String fileType = (String) fileTypes.next();

			// List of files
			final List filesList = this.getSourceFiles(fileType);
			final String[] filesArray = new String[filesList.size()];
			filesList.toArray(filesArray);

			// Names (of the set of files)
			final List namesList = this.getSourceFilesNames(fileType);
			final String[] namesArray;
			if (namesList != null) {
				namesArray = new String[namesList.size()];
				namesList.toArray(namesArray);
			}
			else {
				// Yann 2014/03/31: Ptidej file vs. other sources
				// There could be no name if the source model is
				// built from a selected directory full of class
				// files. In that case, FileAction asks the user
				// for a project name.
				// See ptidej.viewer.action.FileAction.createDualHierarchicalProject()
				//     ptidej.viewer.action.FileAction.createGraphicalProject()
				//     ptidej.viewer.action.FileAction.createHierarchicalProject()
				namesArray = new String[] { this.getTitle() };
			}

			if (fileType.equals(IRepresentation.TYPE_AOL_CODE)) {
				//	final ICodeLevelModel codeLevelModel =
				//		ModelGenerator.generateModelFromAOLCodeFiles(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						"generateModelFromAOLCodeFiles",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else if (fileType.equals(IRepresentation.TYPE_AOL_IDIOM)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromAOLDesignFiles(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						"generateModelFromAOLDesignFiles",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else if (fileType.equals(IRepresentation.TYPE_ASPECTJ)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromAspectJLSTFiles(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				//	this.setBuilder(AspectJBuilder.getCurrentBuilder(this
				//		.getPrimitiveFactory()));
				final SwingWorker worker =
					new Worker(
						"generateModelFromAspectJLSTFiles",
						filesArray,
						namesArray,
						AspectJBuilder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else if (fileType.equals(IRepresentation.TYPE_MSE)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromMSEFiles(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						"generateModelFromMSEFiles",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else if (fileType.equals(IRepresentation.TYPE_ECLIPSE_JDT_PROJECT)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromEclipseProject(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						"generateModelFromEclipseProject",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			/*
			else if (fileType.equals(IRepresentation.TYPE_CPP)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromCppFilesUsingEclipse(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				//	this.setBuilder(CPPBuilder.getCurrentBuilder(this
				//		.getPrimitiveFactory()));
				//	this.setSourceModel(idiomLevelModel);
				final SwingWorker worker =
					new Worker(
						"generateModelFromCppFilesUsingEclipse",
						filesArray,
						namesArray,
						CPPBuilder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			*/
			else if (fileType.equals(IRepresentation.TYPE_JAVA_CLASSFILES)
					|| fileType.equals("JavaCode")) {

				// Yann 2013/07/1/: Legacy
				// Former Ptidej project files included a "JavaCode" section
				// that I still support so that they still work here.
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator.generateModelFromClassFilesDirectories(this
				//			.getSourceModel()
				//			.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						"generateModelFromClassFilesDirectories",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else if (fileType.equals(IRepresentation.TYPE_JAVA_JAVAFILES)) {
				//	final IIdiomLevelModel idiomLevelModel =
				//		ModelGenerator
				//			.generateModelFromJavaFilesDirectoriesUsingEclipse(this
				//				.getSourceModel()
				//				.getDisplayName(), filesArray);
				final SwingWorker worker =
					new Worker(
						// "generateModelFromJavaFilesDirectoriesUsingEclipse",
							"generateModelFromJavaFilesDirectoryUsingJavaC",
						filesArray,
						namesArray,
						Builder
							.getCurrentBuilder(AbstractRepresentationWindow.this
								.getPrimitiveFactory()));
				worker.execute();
			}
			else {
				//				OutputManager
				//					.getCurrentOutputManager()
				//					.getNormalOutput()
				//					.println(
				//					MultilanguageManager.getStrResource(
				//						"Err_UNKNOWN_FILE_TYPE",
				//						FileAction.class));
			}
		}
	}
	public void clearOccurrences() {
		this.occurrences = new Occurrence[0];
		this.refresh();
	}
	public final Builder getBuilder() {
		return this.builder;
	}
	public Occurrence[] getOccurrences() {
		return this.occurrences;
	}
	public final IPrimitiveFactory getPrimitiveFactory() {
		return PrimitiveFactory.getInstance();
	}
	public List getSourceFiles(final String fileTypeName) {
		return (List) this.files.get(fileTypeName);
	}
	public List getSourceFilesNames(final String fileTypeName) {
		return (List) this.names.get(fileTypeName);
	}
	public Set getSourceFileTypes() {
		return this.files.keySet();
	}
	public final int getVisibleElements() {
		return this.visibleElements;
	}
	public final void refresh() {
		this.refreshSpecifics();

		// AWT uses validate to cause a container to lay out its subcomponents
		// again after the components it contains have been added to or modified.
		// scrollPane.setSize(c.getSize().width, c.getSize().height);
		//	this.canvasPanel.revalidate();
		//	this.scrollPane.revalidate();
		this.revalidate();
		this.repaint();

		// Yann 2013/09/28: Availability!
		// I should not forget to let everyone know 
		// that a graph is now available. It is kind
		// of stupid but it seems the best to do...
		// Yann 2014/03/28: One year later!
		// Actually, it would be better to notify the 
		// listeners at more fine-grained places but 
		// that will do for the moment...
		final Iterator iteratorOnGraphModelListeners =
			DesktopPane.getInstance().getIteratorOnGraphModelListeners();
		while (iteratorOnGraphModelListeners.hasNext()) {
			final IGraphModelListener graphModelListener =
				(IGraphModelListener) iteratorOnGraphModelListeners.next();
			graphModelListener.graphModelChanged(new SourceAndGraphModelEvent(
				DesktopPane.getInstance().getAbstractRepresentationWindow()));
		}
	}
	protected abstract void refreshSpecifics();
	public final void setBuilder(final Builder aBuilder) {
		this.builder = aBuilder;
	}
	public void setGraphLayout(final IUILayout aGraphLayout) {
		this.getSourceGraph().setGraphLayout(aGraphLayout);
		this.getSourceGraph().build();
		this.refresh();
	}
	public final void setSourceModel(final IAbstractModel anAbstractModel) {
		// Yann 2007/08/31: Entry point!
		// This method is the real entry point to the displayed content.
		this.sourceModel = anAbstractModel;

		final Iterator iteratorOnSourceModelListeners =
			DesktopPane.getInstance().getIteratorOnSourceModelListeners();
		while (iteratorOnSourceModelListeners.hasNext()) {
			final ISourceModelListener sourceModelListener =
				(ISourceModelListener) iteratorOnSourceModelListeners.next();
			sourceModelListener
				.sourceModelAvailable(new SourceAndGraphModelEvent(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()));
			sourceModelListener
				.sourceModelChanged(new SourceAndGraphModelEvent(DesktopPane
					.getInstance()
					.getAbstractRepresentationWindow()));
		}

		this.setSourceModelSpecifics();

		final String title =
			anAbstractModel.getDisplayName()
					+ " ["
					+ Util.computeSimpleName(anAbstractModel
						.getClass()
						.getName()) + ']';
		this.setTitle(title);
	}

	protected abstract void setSourceModelSpecifics();

	public final void setVisibleElements(final int someVisibleElements) {
		this.visibleElements = someVisibleElements;
		this.refresh();
	}
}
