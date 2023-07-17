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
package ptidej.viewer.action;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.SVGAbstractTranscoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import padl.kernel.IAbstractModel;
import padl.util.ExternalDataProcessor;
import ptidej.ui.occurrence.awt.PrimitiveFactory;
import ptidej.viewer.IRepresentation;
import ptidej.viewer.awt.IAWTRepresentation;
import ptidej.viewer.ui.AbstractRepresentationWindow;
import ptidej.viewer.ui.DesktopFrame;
import ptidej.viewer.ui.DesktopPane;
import ptidej.viewer.utils.Resources;
import ptidej.viewer.utils.Utils;
import util.awt.NameDialog;
import util.io.ProxyConsole;
import util.multilingual.MultilingualManager;

public class FileAction extends AbstractAction {
	private static final String PROJECT_FILE_HEADER = "[Ptidej Project]";
	private static final String PROJECT_NAME_KEY = "Name";
	private static final long serialVersionUID = -5999604008088129163L;

	private static FileAction UniqueInstance;
	public static FileAction getInstance() {
		return (FileAction.UniqueInstance == null) ? FileAction.UniqueInstance =
			new FileAction() : FileAction.UniqueInstance;
	}

	private FileAction() {
	}
	public void actionPerformed(final ActionEvent anActionEvent) {
		final String action = anActionEvent.getActionCommand();

		if (action.equals(Resources.NEW_GRAPHICAL_PROJECT)) {
			this.createGraphicalProject();
		}
		else if (action.equals(Resources.NEW_HIERARCHICAL_PROJECT)) {
			this.createHierarchicalProject();
		}
		else if (action.equals(Resources.NEW_DUAL_HIERARCHICAL_PROJECT)) {
			this.createDualHierarchicalProject();
		}
		else if (action.equals(Resources.LOAD_GRAPHICAL_PROJECT)) {
			this.loadGraphicalProject();
		}
		else if (action.equals(Resources.LOAD_HIERARCHICAL_PROJECT)) {
			this.loadHierarchicalProject();
		}
		else if (action.equals(Resources.LOAD_DUAL_HIERARCHICAL_PROJECT)) {
			this.loadDualHierarchicalProject();
		}
		else if (action.equals(Resources.LOAD_EXTRINSIC)) {
			this.loadExtrinsicData();
		}
		else if (action.equals(Resources.ECLIPSE_JDT_PROJECT)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_ECLIPSE_JDT_PROJECT",
					FileAction.class), "project", MultilingualManager
					.getString(
						"TYPE_ECLIPSE_JDT_PROJECT_NAME",
						FileAction.class), false);
			this.processSelectedFile(
				IRepresentation.TYPE_ECLIPSE_JDT_PROJECT,
				file);
		}
		else if (action.equals(Resources.MSE_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_MSE_FILE",
					FileAction.class), "mse", MultilingualManager.getString(
					"TYPE_MSE_NAME",
					FileAction.class), false);
			this.processSelectedFile(IRepresentation.TYPE_MSE, file);
		}
		else if (action.equals(Resources.PTIDEJ_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_PTIDEJ_FILE",
					FileAction.class), "ptidej", MultilingualManager.getString(
					"TYPE_PTIDEJ_NAME",
					FileAction.class), false);
			this.processSelectedFile(file);
		}
		else if (action.equals(Resources.ASPECTJ_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_ASPECTJ_FILE",
					FileAction.class), "lst", MultilingualManager.getString(
					"TYPE_ASPECTJ_NAME",
					FileAction.class), false);
			this.processSelectedFile(IRepresentation.TYPE_ASPECTJ, file);
		}
		else if (action.equals(Resources.JAVA_CLASS_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_JAVA_FILE",
					FileAction.class), "class", MultilingualManager.getString(
					"TYPE_JAVA_NAME",
					FileAction.class), true);
			this
				.processSelectedFile(IRepresentation.TYPE_JAVA_CLASSFILES, file);
		}
		else if (action.equals(Resources.JAVA_ARCHIVE_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_JAR_FILE",
					FileAction.class), "jar", MultilingualManager.getString(
					"TYPE_JAVA_NAME",
					FileAction.class), false);
			this
				.processSelectedFile(IRepresentation.TYPE_JAVA_CLASSFILES, file);
		}
		else if (action.equals(Resources.JAVA_SOURCE_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_JAVA_SOURCE_FILE",
					FileAction.class), "java", MultilingualManager.getString(
					"TYPE_JAVA_NAME",
					FileAction.class), true);
			this.processSelectedFile(IRepresentation.TYPE_JAVA_JAVAFILES, file);
		}
		else if (action.equals(Resources.CPP_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_CPP_FILE",
					FileAction.class), "cpp", MultilingualManager.getString(
					"TYPE_CPP_NAME",
					FileAction.class), true);
			this.processSelectedFile(IRepresentation.TYPE_CPP, file);
		}
		else if (action.equals(Resources.AOL_CODE_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_AOL_CODE_FILE",
					FileAction.class), "aol", MultilingualManager.getString(
					"TYPE_AOL_CODE_NAME",
					FileAction.class), false);
			this.processSelectedFile(IRepresentation.TYPE_AOL_CODE, file);
		}
		else if (action.equals(Resources.AOL_IDIOM_FILE)) {
			final File file =
				this.selectFile(MultilingualManager.getString(
					"ADD_AOL_IDIOM_FILE",
					FileAction.class), "aol", MultilingualManager.getString(
					"TYPE_AOL_IDIOM_NAME",
					FileAction.class), false);
			this.processSelectedFile(IRepresentation.TYPE_AOL_IDIOM, file);
		}
		else if (action.equals(Resources.CLOSE_ALL)) {
			DesktopPane.getInstance().removeAll();
		}
		else if (action.equals(Resources.CLOSE_ACTIVE)) {
			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();
			DesktopPane.getInstance().remove(window);
		}
		else if (action.equals(Resources.CLOSE_ALL_EXPECT_ACTIVE)) {
			final AbstractRepresentationWindow window =
				DesktopPane.getInstance().getAbstractRepresentationWindow();
			DesktopPane.getInstance().removeAll();
			DesktopPane.getInstance().add(window);
		}
		else if (action.equals(Resources.EXPORT_SVG)) {
			this.exportSVG();
		}
		else if (action.equals(Resources.SAVE_ALL)) {
			// TODO: To implement!
		}
		else if (action.equals(Resources.SAVE_ACTIVE)) {
			// TODO: To implement!
		}
		else if (action.equals(Resources.PRINT)) {
			// TODO: To implement!
		}
		else if (action.equals(Resources.EXIT)) {
			System.exit(0);
		}
	}
	private void build(final IAWTRepresentation aRepresentation) {
		((AbstractRepresentationWindow) aRepresentation).build();

		// Yann 2007/06/13: Update...
		// I do not forget to notify listeners of the changes.
		// Yann 2014/03/28: Update on updates...
		// It seems that this update is useless now that the
		// events are more consistently manged between UI elements.
		//	DesktopPane.getInstance().notifySourceModelAvailability(
		//		new SourceModelEvent(DesktopPane.getInstance().getModelWindow()));
	}
	private void createDualHierarchicalProject() {
		final String name =
			new NameDialog((Frame) DesktopFrame.getInstance(), "Project name")
				.getName();
		if (!name.equals("")) {
			DesktopPane.getInstance().createDualHierarchicalModelWindow();
			DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow()
				.setTitle(name);
		}
	}
	private void createGraphicalProject() {
		final String name =
			new NameDialog((Frame) DesktopFrame.getInstance(), "Project name")
				.getName();
		if (!name.equals("")) {
			DesktopPane.getInstance().createGraphicalModelWindow();
			DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow()
				.setTitle(name);
		}
	}
	private void createHierarchicalProject() {
		final String name =
			new NameDialog((Frame) DesktopFrame.getInstance(), "Project name")
				.getName();
		if (!name.equals("")) {
			DesktopPane.getInstance().createHierarchicalModelWindow();
			DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow()
				.setTitle(name);
		}
	}
	private void exportSVG() {
		final AbstractRepresentationWindow window =
			DesktopPane.getInstance().getAbstractRepresentationWindow();
		if (window != null) {
			final File file =
				this.selectFile("SVG Export", "svg", "SVG File", false);

			if (file != null) {
				// Get a DOMImplementation.
				final DOMImplementation domImpl =
					SVGDOMImplementation.getDOMImplementation();
				final String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;

				// Create an instance of org.w3c.dom.Document.
				final Document document =
					domImpl.createDocument(svgNS, "svg", null);

				// Create an instance of the SVG Generator.
				final SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

				// Ask the test to render into the SVG Graphics2D implementation.
				final PrimitiveFactory primitiveFactory =
					(PrimitiveFactory) window.getPrimitiveFactory();
				final Graphics oldGraphics = primitiveFactory.getGraphics();
				primitiveFactory.setGraphics(svgGenerator);
				window.getAWTCanvas().paint(svgGenerator);
				primitiveFactory.setGraphics(oldGraphics);

				// Yann 2010/02/07: Size!
				// I set the size of the SVG graphic.
				//	final Element svgRoot = document.getDocumentElement();
				//	svgRoot.setAttributeNS(null, "width", String.valueOf(window
				//		.getCanvas()
				//		.getDimension().width));
				//	svgRoot.setAttributeNS(null, "height", String.valueOf(window
				//		.getCanvas()
				//		.getDimension().height));
				svgGenerator
					.setSVGCanvasSize(window.getCanvas().getDimension());
				svgGenerator.transform(AffineTransform.getScaleInstance(
					2.0,
					2.0));
				svgGenerator.dispose();

				//	// Yann 2010/02/007: Populating the document...
				//	// Apparently, the "document" is only used as a factory
				//	// and does not get populated by the SVGGraphics2D, so
				//	// I must force its "populating"... 
				//	// See https://issues.apache.org/bugzilla/show_bug.cgi?id=21259
				//	final Element root = document.getDocumentElement();
				//	svgGenerator.getRoot(root);

				// Finally, stream out SVG to the standard 
				// output using UTF-8 encoding.
				try {
					final boolean useCSS = true;
					// we want to use CSS style attributes
					final Writer out =
						new OutputStreamWriter(
							new FileOutputStream(file),
							"UTF-8");
					svgGenerator.stream(out, useCSS);
					out.flush();
					out.close();

					try {
						Thread.sleep(1000);
					}
					catch (final InterruptedException e) {
						e.printStackTrace();
					}

					// Yann 2010/02/07: Good measure!
					// For good measure, I also generate a PNG
					// version of the image with the same name..
					// Create a JPEG transcoder
					final JPEGTranscoder t = new JPEGTranscoder();
					t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, Float.valueOf(
						1));
					t.addTranscodingHint(
						SVGAbstractTranscoder.KEY_HEIGHT,
						Float.valueOf(window.getCanvas().getDimension().height));
					t.addTranscodingHint(
						SVGAbstractTranscoder.KEY_WIDTH,
						Float.valueOf(window.getCanvas().getDimension().width));
					final TranscoderInput input =
						new TranscoderInput(new FileReader(file));
					final OutputStream ostream =
						new FileOutputStream(file.getAbsoluteFile() + ".jpg");
					final TranscoderOutput output =
						new TranscoderOutput(ostream);
					t.transcode(input, output);
					ostream.flush();
					ostream.close();
				}
				catch (final FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (final UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				catch (final SVGGraphics2DIOException e) {
					e.printStackTrace();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
				catch (final TranscoderException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void loadDualHierarchicalProject() {
		final File file =
			Utils.loadFile(
				DesktopFrame.getInstance(),
				false,
				"Choose Ptidej project file",
				"ptidej",
				"Ptidej project files");
		if (file == null) {
			return;
		}

		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		}
		catch (final IOException e) {
			// No file found.
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return;
		}

		DesktopPane.getInstance().createDualHierarchicalModelWindow();
		this.processSelectedFile(file);
	}
	private void loadExtrinsicData() {
		final AbstractRepresentationWindow window =
			DesktopPane.getInstance().getAbstractRepresentationWindow();

		// TODO: The path must not be harcoded, an improvement will be to
		// save the last accessed path somewhere to reuse it.
		final File iniFile =
			Utils.loadFile(
				DesktopFrame.getInstance(),
				false,
				"Choose an PADL file",
				"padl",
				"PADL files");
		if (iniFile == null) {
			return;
		}
		this.loadExtrinsicData(window, iniFile);
	}
	private void loadExtrinsicData(
		final AbstractRepresentationWindow window,
		final File anIniFile) {
		try {
			final Properties properties = new Properties();
			properties.load(new FileInputStream(anIniFile));
			final IAbstractModel abstractModel = window.getSourceModel();
			ExternalDataProcessor.process(properties, abstractModel);

			// Yann 2002/08/05: Build!
			// I must re-build the graphical representation
			// to include the modifications. This is highly
			// poorly coded...
			// Yann 2014/03/28: Not any more :-)
			// I now not only create only one instance of 
			// the ModelGraph but reset it as needed...
			window.setSourceModel(abstractModel);
		}
		catch (final FileNotFoundException fnfe) {
			fnfe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException ioe) {
			ioe.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void loadGraphicalProject() {
		final File file =
			Utils.loadFile(
				DesktopFrame.getInstance(),
				false,
				"Choose Ptidej project file",
				"ptidej",
				"Ptidej project files");
		if (file == null) {
			return;
		}

		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		}
		catch (final IOException e) {
			// No file found.
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return;
		}

		DesktopPane.getInstance().createGraphicalModelWindow();
		this.processSelectedFile(file);
	}
	private void loadHierarchicalProject() {
		final File file =
			Utils.loadFile(
				DesktopFrame.getInstance(),
				false,
				"Choose Ptidej project file",
				"ptidej",
				"Ptidej project files");
		if (file == null) {
			return;
		}

		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		}
		catch (final IOException e) {
			// No file found.
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return;
		}

		DesktopPane.getInstance().createHierarchicalModelWindow();
		this.processSelectedFile(file);
	}
	private void processSelectedFile(final File file) {
		final IAWTRepresentation window =
			DesktopPane.getInstance().getAbstractRepresentationWindow();

		final String parentPath = file.getParentFile().getAbsolutePath();
		final Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		}
		catch (final IOException e) {
			// No file found.
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			return;
		}

		// Yann 2004/08/11: Multilingualism!
		// I now get the files from the project file according to their type.
		// Yann 2014/03/29: Multilingualism again...
		// I want now to keep track of the name associated with a file type.
		// The thing is that property file are unordered so, if several file
		// types exist, there is no way to associate one name to each...
		// I assume for now that there is only one name...
		// TODO Number the properties or do not use property files but some linear reader
		final List names = new ArrayList();
		final Iterator fileTypeNames = properties.keySet().iterator();
		while (fileTypeNames.hasNext()) {
			final String fileTypeName = (String) fileTypeNames.next();

			// Yann 2004/08/12: Paths.
			// I don't add to the paths the name of the project,
			// the classpath (if its an old Java-only project),
			// and the file header.
			if (fileTypeName.equals(PROJECT_NAME_KEY)) {
				names.add(properties.getProperty(fileTypeName));
			}
			else if (!fileTypeName.equals("Classpath")
					&& !PROJECT_FILE_HEADER.startsWith(fileTypeName)) {

				final StringTokenizer files =
					new StringTokenizer(
						properties.getProperty(fileTypeName),
						";");
				while (files.hasMoreTokens()) {
					// Yann 2003/10/24: Relativity!
					// I allow both absolute and relative path in a
					// Ptidej project file to ease installation on
					// other computer.
					String path = files.nextToken();
					if (!new File(path).exists()) {
						path = parentPath + File.separator + path;
					}
					path = util.io.Files.normalizePath(path);
					window.addSourceFile(fileTypeName, path);
				}
			}
		}
		final Iterator iteratorOnFileTypes =
			window.getSourceFileTypes().iterator();
		while (iteratorOnFileTypes.hasNext()) {
			final String fileType = (String) iteratorOnFileTypes.next();
			final Iterator interatorOnNames = names.iterator();
			while (interatorOnNames.hasNext()) {
				final String name = (String) interatorOnNames.next();
				window.addSourceFileName(fileType, name);
			}
		}

		this.build(window);
	}
	private void processSelectedFile(final String fileType, final File file) {
		if (file != null) {
			// Yann 2013/05/23: Directory vs. File vs. Files vs. Directories
			// I do not iterate through whatever is given me anymore, I let
			// the ModelGenerator handle the cases as it pleases... It is its
			// responsibility to do whatever it takes!
			// this.processSelectedFile0(fileType, file);
			final String path =
				util.io.Files.normalizePath(file.getAbsolutePath())
						+ File.separatorChar;
			DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow()
				.addSourceFile(fileType, path);
			this.build(DesktopPane
				.getInstance()
				.getAbstractRepresentationWindow());
		}
	}
	//	private void processSelectedFile0(final String fileType, final File file) {
	//		if (file.isFile()) {
	//			final String path =
	//				util.io.Files.normalizePath(file.getAbsolutePath());
	//			DesktopPane
	//				.getInstance()
	//				.getModelWindow()
	//				.addSourceFile(fileType, path);
	//		}
	//		else if (file.isDirectory()) {
	//			final String[] files = file.list();
	//			for (int i = 0; i < files.length; i++) {
	//				final File newFile = new File(file, files[i]);
	//				this.processSelectedFile0(fileType, newFile);
	//			}
	//		}
	//	}
	private File selectFile(
		final String fileTypeDescription,
		final String fileExtensionName,
		final String fileTypeName,
		final boolean directory) {

		// Yann 2013/08/07: Model combination
		// Although PADL is made to be multi-language and addable,
		// right now each window will contain only one model.
		if (DesktopPane.getInstance().getAbstractRepresentationWindow() == null) {
			this.createHierarchicalProject();
			if (DesktopPane.getInstance().getAbstractRepresentationWindow() == null) {
				return null;
			}
		}

		final File file;
		if (directory) {
			file =
				Utils.loadDirectory(
					DesktopFrame.getInstance(),
					false,
					"Choose " + fileTypeDescription,
					fileExtensionName,
					fileTypeName);
		}
		else {
			file =
				Utils.loadFile(DesktopFrame.getInstance(), false, "Choose "
						+ fileTypeDescription, fileExtensionName, fileTypeName);
		}
		return file;
	}
}
