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
package padl.creator.cppfile.eclipse;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import padl.cpp.kernel.event.CPPEventGenerator;
import padl.event.IModelListener;
import padl.kernel.ICodeLevelModel;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import com.thoughtworks.xstream.XStream;

public final class Common {
	public static final String ARGUMENT_DIRECTORY_PTIDEJ_WORKSPACE =
		"-directoryPtidej";
	public static final String ARGUMENT_DIRECTORY_TARGET_CPP_FILES =
		"-directoryCPPFiles";
	public static final String ARGUMENT_OSGi_RUNNING_IN_REMOTE_JVM =
		"-isRunningInRemoteJVM";
	public static final String EQUINOX_CONFIGURATION_CONFIG_FILE = "config.ini";
	public static final String EQUINOX_CONFIGURATION_DATA =
		"PADL Creator C++ (Eclipse) Helper/Configuration Area/";
	public static final String EQUINOX_CONFIGURATION_TEMPLATE_CONFIG_FILE =
		"Paths-placeholder config.ini";
	// The Launcher name is made of two part: the name of the plug-in
	// see in the Overview tab when opening the plugin.xml file and
	// the ID of the extension to "org.eclipse.core.runtime.applications"
	// see in the plugin.xml tab when opening the plugin.xml file.
	public static final String EQUINOX_LAUNCHER_NAME =
		"PADL_Creator_Cpp_Eclipse.Launcher";
	public static final String EQUINOX_RUNTIME_WORKSPACE =
		"PADL Creator C++ (Eclipse) Helper/Runtime Workspace/";
	public static final IProgressMonitor NULL_PROGRESS_MONITOR =
		new NullProgressMonitor();
	public static final String PLACEHOLDER_TAG = "@PLACEHOLDER@";
	public static final String SERIALISED_MODEL_FILENAME =
		"SerialisedCodeLevelModel.xml";

	public static void prepareForCodeLevelModel() {
		ProxyConsole.getInstance().debugOutput().print(Common.class.getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(" is erasing any former serialised model");
		final File file =
			new File(ProxyDisk
				.getInstance()
				.directoryTempFile()
				.getAbsoluteFile(), Common.SERIALISED_MODEL_FILENAME);
		file.delete();

		ProxyConsole.getInstance().normalOutput().print(Common.class.getName());
		ProxyConsole.getInstance().normalOutput().print(" will use \"");
		ProxyConsole.getInstance().normalOutput().print(file.getAbsolutePath());
		ProxyConsole.getInstance().normalOutput().println('"');
	}
	public static void readCodeLevelModel(final ICodeLevelModel aCodeLevelModel) {
		// Yann 2013/05/23: Listeners!
		// I save the desired listener before "erasing" the model...
		final List<IModelListener> listeners = new ArrayList<IModelListener>();
		final Iterator<?> iterator =
			aCodeLevelModel.getIteratorOnModelListeners();
		while (iterator.hasNext()) {
			final IModelListener listener = (IModelListener) iterator.next();
			listeners.add(listener);
		}

		final XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		xstream.fromXML(
			ProxyDisk.getInstance().fileTempInput(
				Common.SERIALISED_MODEL_FILENAME),
			aCodeLevelModel);

		// Yann 2013/05/23: Statistics!
		// Because I recreate the model from an XML file,
		// no event is generated during its "creation" so
		// any listener would not have the chance to hear
		// from it... Therefore, I visit each node and
		// pretend that it is being created ;-)
		aCodeLevelModel.addModelListeners(listeners);
		aCodeLevelModel.walk(CPPEventGenerator.getInstance());

		//	DB4OSerialiser.getInstance().deserialise(
		//		Utils.TEMPORARY_OUTPUT_FILE + ".db4o");
	}
	public static void writeCodeLevelModel(final ICodeLevelModel aCodeLevelModel) {
		ProxyConsole.getInstance().debugOutput().print(Common.class.getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(" is serialising the model");
		final XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		xstream.toXML(
			aCodeLevelModel,
			ProxyDisk.getInstance().fileTempOutput(
				Common.SERIALISED_MODEL_FILENAME));
		ProxyConsole.getInstance().debugOutput().print(Common.class.getName());
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(" serialised the mode in \"");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(
				ProxyDisk.getInstance().directoryTempFile().getAbsolutePath());
		ProxyConsole.getInstance().debugOutput().println('\"');

		//	DB4OSerialiser.getInstance().serialise(
		//		aCodeLevelModel,
		//		Common.SERIALISED_MODEL_FILENAME + ".db4o");
	}
	private Common() {
	}
}
