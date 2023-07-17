/* (c) Copyright 2001 and following years, Yann-Gaël Guéhéneuc,
 * University of Montreal.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package ptidej.viewer;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JFileChooser;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.statement.creator.classfiles.LOCModelAnnotator;
import padl.util.ModelStatistics;
import ptidej.viewer.helper.RepositoriesAccessor;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.designsmell.detection.IDesignSmellDetection;

class Business {
	private IAbstractModel abstractModel;
	private final WindowMain mainWindow;

	Business(final WindowMain aMainWindow) {
		this.mainWindow = aMainWindow;
	}
	void about() {
		this.mainWindow.warnUser(Constants.PROGRAM_NAME + " v"
				+ Constants.PROGRAM_VERSION + ". © " + Constants.PROGRAM_AUTHOR
				+ ", 2009");
	}
	private String convertResultsToText(final Set someResults) {
		final StringBuffer buffer = new StringBuffer();
		final Iterator iterator = someResults.iterator();
		while (iterator.hasNext()) {
			final Object o = iterator.next();
			buffer.append(o.toString());
		}
		return buffer.toString();
	}
	void createModel(final int aModelType) {
		this.mainWindow.informUser("Creating model...");

		if (aModelType == Constants.MODEL_JAVA_BYTECODE) {
			this.createModelFromJavaBytecode();
		}
		else if (aModelType == Constants.MODEL_JAVA_JAR_FILE) {
			this.createModelFromJavaJARFile();
		}
		else if (aModelType == Constants.MODEL_JAVA_SOURCE_CODE) {
			this.createModelFromJavaSourceCode();
		}
		else {
			this.mainWindow
				.warnUser("Selected model is not supported in this limited version");
		}
	}
	private void createModelFromJavaBytecode() {
		try {
			final File file =
				this
					.loadFile(
						"D:/Software/Ptidej 4 Workspace 3/Ptidej Examples and Configurations/bin/ptidej/example/",
						JFileChooser.DIRECTORIES_ONLY,
						"Select Java root path",
						"Java Classfiles",
						".class");
			if (file != null) {
				final String classPath = file.getCanonicalPath() + '/';
				this.mainWindow.displayModelInformation(classPath);

				final ModelStatistics statistics = new ModelStatistics();
				this.abstractModel =
					Factory.getInstance().createCodeLevelModel("");
				this.abstractModel.addModelListener(statistics);
				((ICodeLevelModel) this.abstractModel)
					.create(new CompleteClassFileCreator(
						new String[] { classPath },
						true));
				final LOCModelAnnotator annotator =
					new LOCModelAnnotator(new String[] { classPath });
				this.abstractModel = annotator.invoke(this.abstractModel);
				this.abstractModel =
					new AACRelationshipsAnalysis().invoke(this.abstractModel);

				this.mainWindow.displayModelInformation(statistics.toString());
			}
		}
		catch (final Exception e) {
			this.mainWindow.displayException(e);
		}
	}
	private void createModelFromJavaJARFile() {
		try {
			final File file =
				this
					.loadFile(
						"D:/Software/Ptidej 4 Workspace 3/Ptidej Examples and Configurations/bin/ptidej/example/",
						JFileChooser.FILES_ONLY,
						"Select Java JAR file",
						"Java JAR file",
						".jar");
			if (file != null) {
				final String classPath = file.getCanonicalPath() + '/';
				this.mainWindow.displayModelInformation(classPath);

				final ModelStatistics statistics = new ModelStatistics();
				this.abstractModel =
					Factory.getInstance().createCodeLevelModel("");
				this.abstractModel.addModelListener(statistics);
				((ICodeLevelModel) this.abstractModel)
					.create(new CompleteClassFileCreator(
						new String[] { classPath },
						true));
				final LOCModelAnnotator annotator =
					new LOCModelAnnotator(new String[] { classPath });
				this.abstractModel = annotator.invoke(this.abstractModel);
				this.abstractModel =
					new AACRelationshipsAnalysis().invoke(this.abstractModel);

				this.mainWindow.displayModelInformation(statistics.toString());
			}
		}
		catch (final Exception e) {
			this.mainWindow.displayException(e);
		}
	}
	private void createModelFromJavaSourceCode() {
		try {
			final File file =
				this
					.loadFile(
						"D:/Software/Ptidej 4 Workspace 3/Ptidej Examples and Configurations/bin/ptidej/example/",
						JFileChooser.DIRECTORIES_ONLY,
						"Select Java root path",
						"Java source code files",
						".java");
			if (file != null) {
				final String classPath = file.getCanonicalPath() + '/';
				this.mainWindow.displayModelInformation(classPath);

				//	final File[] javaFiles = this.getFiles(file, ".java");

				final ModelStatistics statistics = new ModelStatistics();
				this.abstractModel =
					Factory.getInstance().createCodeLevelModel("");
				this.abstractModel.addModelListener(statistics);
				//	((ICodeLevelModel) this.abstractModel).create(new CreatorJava(
				//		new DiagnosticListener() {
				//			public void report(Diagnostic arg0) {
				//			}
				//		},
				//		new HashMap(),
				//		javaFiles));
				this.abstractModel =
					new AACRelationshipsAnalysis().invoke(this.abstractModel);

				this.mainWindow.displayModelInformation(statistics.toString());
			}
		}
		catch (final Exception e) {
			this.mainWindow.displayException(e);
		}
	}
	//	private File[] getFiles(File file, String string) {
	//		// TODO Auto-generated method stub
	//		return null;
	//	}
	void detectCodeSmell(final String aCodeSmellName) {
		try {
			if (this.abstractModel != null) {
				this.mainWindow.informUser("Detecting code smell \""
						+ aCodeSmellName + "\"...");

				final ICodeSmellDetection[] codeSmellDetections =
					RepositoriesAccessor.getInstance().getCodeSmellDetections();
				for (int i = 0; i < codeSmellDetections.length; i++) {
					final ICodeSmellDetection codeSmellDetection =
						codeSmellDetections[i];
					if (codeSmellDetection.getName().equals(aCodeSmellName)) {
						// TODO Necessary?
						//	codeSmellDetection
						//		.setMetricsFileRepository(RepositoriesAccessor
						//			.getInstance()
						//			.getPOMFileRepository());
						codeSmellDetection
							.detect((IAbstractLevelModel) this.abstractModel);
						final Set results = codeSmellDetection.getCodeSmells();

						this.mainWindow.displayDetectionResults(
							"Detected " + results.size() + " occurrence(s) of "
									+ aCodeSmellName,
							this.convertResultsToText(results));
						break;
					}
				}
			}
			else {
				this.mainWindow
					.warnUser("Cannot detect a code smell without a model");
			}
		}
		catch (final Exception e) {
			this.mainWindow.displayException(e);
		}
	}
	void detectDesignSmell(final String aDesignSmellName) {
		try {
			if (this.abstractModel != null) {
				this.mainWindow.informUser("Detecting design smell \""
						+ aDesignSmellName + "\"...");

				final IDesignSmellDetection[] designSmellDetections =
					RepositoriesAccessor
						.getInstance()
						.getDesignSmellDetections();
				for (int i = 0; i < designSmellDetections.length; i++) {
					final IDesignSmellDetection designSmellDetection =
						designSmellDetections[i];
					if (designSmellDetection.getName().equals(aDesignSmellName)) {
						// TODO Necessary?
						//	designSmellDetection
						//		.setMetricsFileRepository(RepositoriesAccessor
						//			.getInstance()
						//			.getPOMFileRepository());
						designSmellDetection
							.detect((IAbstractLevelModel) this.abstractModel);
						final Set results =
							designSmellDetection.getDesignSmells();

						this.mainWindow.displayDetectionResults(
							"Detected " + results.size() + " occurrence(s) of "
									+ aDesignSmellName,
							this.convertResultsToText(results));
						break;
					}
				}
			}
			else {
				this.mainWindow
					.warnUser("Cannot detect a code smell without a model");
			}
		}
		catch (final Exception e) {
			this.mainWindow.displayException(e);
		}
	}
	void help() {
		try {
			final StringBuffer docFullPath =
				new StringBuffer(System.getProperty("user.dir"));
			docFullPath.append(File.separatorChar);
			docFullPath.append("doc");
			docFullPath.append(File.separatorChar);
			docFullPath.append("Ptidej.html");

			final LineNumberReader lineNumberReader =
				new LineNumberReader(new FileReader(docFullPath.toString()));
			final StringBuffer docBuffer = new StringBuffer();
			String line;
			while ((line = lineNumberReader.readLine()) != null) {
				docBuffer.append(line);
				docBuffer.append('\n');
			}
			lineNumberReader.close();

			final WindowHelp helpWindow =
				new WindowHelp("Help", docBuffer.toString());
			helpWindow.setVisible(true);
		}
		catch (final Exception e) {
			this.mainWindow.warnUser("Cannot find Ptidej.html file!");
		}
	}
	private File loadFile(
		final String aDefaultPath,
		final int aSelectionMode,
		final String aDialogTitle,
		final String aFilterName,
		final String aFilterDesc) {

		final JFileChooser fileChooser = new JFileChooser(aDefaultPath);
		final File file;

		fileChooser.setDialogTitle(aDialogTitle);
		fileChooser.addChoosableFileFilter(new FileFilter(
			aFilterName,
			aFilterDesc));
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileSelectionMode(aSelectionMode);

		if (fileChooser.showOpenDialog(this.mainWindow) == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		else {
			file = null;
		}

		return file;
	}
	void quit() {
		System.exit(0);
	}
}
