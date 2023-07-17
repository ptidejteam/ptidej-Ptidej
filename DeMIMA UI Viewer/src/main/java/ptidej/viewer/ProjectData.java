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

//import util.multilanguage.MultilanguageManager;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/08/15
 */
public final class ProjectData {

	static final String resourceBaseName =
		"Ptidej UI Viewer::ProjectData::";

	private static final String[][] COPYRIGHT_MESSAGE =
		new String[][] {
			{	/*
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C01", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C02", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C03", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C04", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C05", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C06", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C07", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C08", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R01_C09", resourceBaseName)
				*/
				"The Ptidej tool suite is a set of tools to evaluate and to enhance the quality of object-",
				"oriented programs, promoting the use of patterns, either at the language-, design-, or",
				"architectural-levels.",
				"The Ptidej tool suite (Pattern Trace Identification, Detection, and Enhancement in Java)",
				"by Yann-Gaël Guéhéneuc uses the PADL meta-model (Pattern and Abstract-level",
				"Description Language), extension of the PDL meta-model (Pattern Description Language)",
				"by Hervé Albin-Amiot.",
				"Get more information at www.yann-gael.gueheneuc.net/Work/Research/Ptidej/Demo/.",
				"Send comments and questions to yann-gael@gueheneuc.net."
			}, {/*
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C01", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C02", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C03", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C04", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C05", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C06", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C07", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C08", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C09", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C10", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C11", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C12", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C13", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C14", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C15", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C16", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C17", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C18", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C19", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C20", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C21", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C22", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C23", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C24", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C25", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C26", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C27", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C28", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C29", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C30", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C31", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R02_C32", resourceBaseName)
				*/
				"Main developpers:",
				"        - Yann-Gaël Guéhéneuc",
				"                Université de Montréal",
				"                École des Mines de Nantes",
				"                Object Technology International, Inc.",
				"        - Hervé Albin-Amiot.",
				"                École des Mines de Nantes",
				"                Softmaint S.A.",
				"With contributions by:",
				"        - Jean-Yves Guyomarc'h",
				"                On the Ptidej Solver in Java and metrics",
				"                Graduate, fall 2004",
				"                Université de Montréal.",
				"        - Lulzim Laloshi and Driton Salihu",
				"                On the Eclipse plug-in",
				"                Undergraduates, summer 2004",
				"                Université de Montréal.",
				"        - Ward Flores and Sébastien Robidoux",
				"                On the C++ creator and PADL",
				"                Undergraduates, summer 2004",
				"                Université de Montréal.",
				"        - Salime Bensemmane, Iyadh Sidhom, and Fayçal Skhiri",
				"                On the Ptidej Solver in Java",
				"                Undergraduates, summer 2004",
				"                Université de Montréal.",
				"        - Farouk Zaidi",
				"                On the Java classfile creator and metrics",
				"                Trainee, winter 2004",
				"                Université de technologie Belfort-Montbéliard",
				"                Centre de Recherche en Informatique de Montréal",
				"                Université de Montréal."
			}, {/*
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R03_C01", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R03_C02", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R03_C03", resourceBaseName)
				*/
				"The Ptidej tool suite, copyright (c) 2000-2008,",
				"Yann-Gaël Guéhéneuc.",
				"All right reserved."
			}, {/*
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R04_C01", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R04_C02", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R04_C03", resourceBaseName)
				*/
				"Use and copying of this software and preparation of derivative works based upon this",
				"software are permitted. Any copy of this software or of any derivative work must include",
				"the above copyright notice of the authors, this paragraph and the one after it."
			}, {/*
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C01", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C02", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C03", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C04", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C05", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C06", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C07", resourceBaseName),
				MultilanguageManager.getStrResource("COPYRIGHT_MESSAGE_R05_C08", resourceBaseName)
				*/
				"This software is made available AS IS, and THE AUTHORS DISCLAIM ALL WARRANTIES,",
				"EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE IMPLIED WARRANTIES",
				"OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND NOT",
				"WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN ANY LIABILITY FOR",
				"DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS EXPRESSLY ",
				"DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING NEGLIGENCE)",
				"OR STRICT, LIABILITY EVEN IF THE AUTHORS ARE ADVISED OF THE POSSIBILITY",
				"OF SUCH DAMAGES."
			}
	};
	private static final String[] PROJECTS =
		new String[] {
			/*
			MultilanguageManager.getStrResource("PROJECTS_R01", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R02", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R03", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R04", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R05", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R06", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R07", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R08", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R09", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R10", resourceBaseName),
			MultilanguageManager.getStrResource("PROJECTS_R11", resourceBaseName)
			*/
			"CPL v1.0.0",
			"PADL v1.0.0",
			"PADL AOL Creator v1.0.0",
			"PADL C++ Creator v1.0.0",
			"PADL ClassFile Creator v1.0.0",
			"POM v1.0.0",
			"Ptidej v1.0.0",
			"Ptidej Solver 3 v1.0.0",
			"Ptidej Solver 4 v1.0.0",
			"Ptidej UI v1.0.0",
			"Ptidej UI Viewer v1.0.0"
		};
	public static String[][] getCopyrightData() {
		return ProjectData.COPYRIGHT_MESSAGE;
	}
	//	private static String getNameAndVersion(final String projectName) {
	//		final File pluginFile = new File("../" + projectName + "/plugin.xml");
	//
	//		if (pluginFile.exists()) {
	//			try {
	//				final BufferedReader pluginReader =
	//					new BufferedReader(new FileReader(pluginFile));
	//				final StringBuffer pluginContent = new StringBuffer();
	//				String line;
	//				while ((line = pluginReader.readLine()) != null) {
	//					pluginContent.append(line);
	//				}
	//
	//				final StringBuffer nameAndVersion = new StringBuffer();
	//
	//				int index = pluginContent.indexOf("name");
	//				index = pluginContent.indexOf("\"", index) + 1;
	//				nameAndVersion.append(
	//					pluginContent.substring(
	//						index,
	//						pluginContent.indexOf("\"", index + 1)));
	//
	//				nameAndVersion.append(" v");
	//
	//				index = pluginContent.indexOf("version", index);
	//				index = pluginContent.indexOf("\"", index) + 1;
	//				nameAndVersion.append(
	//					pluginContent.substring(
	//						index,
	//						pluginContent.indexOf("\"", index + 1)));
	//
	//				//	nameAndVersion.append(", by ");
	//
	//				//	index = pluginContent.indexOf("provider-name");
	//				//	index = pluginContent.indexOf("\"", index) + 1;
	//				//	nameAndVersion.append(
	//				//		pluginContent.substring(
	//				//			index,
	//				//			pluginContent.indexOf("\"", index + 1)));
	//
	//				return nameAndVersion.toString();
	//			}
	//			catch (final FileNotFoundException fne) {
	//				fne.printStackTrace();
	//			}
	//			catch (final IOException ioe) {
	//				ioe.printStackTrace();
	//			}
	//		}
	//
	//		return "";
	//	}
	public static String[] getVersionData() {
		// Yann 2004/08/16: File access...
		// I cannot use this method to access the plugin.xml file becaue
		// (1) the name of the projects may change on disk, (2) it would
		// not work at all with the Applet version. So, I hardcode the
		// versions even if it duplicates the data...
		//	final String[] versions = new String[ProjectData.PROJECTS.length];
		//	for (int i = 0; i < ProjectData.PROJECTS.length; i++) {
		//		versions[i] =
		//			ProjectData.getNameAndVersion(ProjectData.PROJECTS[i]);
		//	}
		return ProjectData.PROJECTS;
	}
	private ProjectData() {
	}
}
