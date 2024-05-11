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
package padl.creator.aolfile.util;
import padl.creator.aolfile.AOLCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/21
 */
public class TestParser {
	public static void main(final String[] args) {
		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("");
		//	final AOLCreator aolCreator =
		//		new AOLCreator(new String[] { "rsc/Mozilla/moz-1.0.rel.n.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "rsc/Firefox/AddBookmark_subset1.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(new String[] { "rsc/Firefox/AddBookmark.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(new String[] { "rsc/mozilla-1.0-concat_des_2006-02-15114305.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/Admin_2.3.3/Admin_2.3.3.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/DB_2.1.2/DB_2.1.2.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/ED_2.1.0/ED_2.1.0.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/HDOgui_2.4.6/HDOgui_2.4.6.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/Meta_2.1.0/Meta_2.1.0.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/NMI_2.2.0/NMI_2.2.0.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/OSSI_2.0.2/OSSI_2.0.2.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/PM_2.0.3/PM_2.0.3.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/TM_2.2.0/TM_2.2.0.N.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/IMS/design/ADM/ADM.aol" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/REUSE/code/AlarmBrowser.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/REUSE/code/LOGLIB.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/DbConn.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/DistPublic.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/Error_Library.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIBrowser.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIconfig.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIevents.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/Trace_Lib.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VIPER_PROCESSING.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VMCS_Processing.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VSPA_Processing.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorAcquisition.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorDistribution.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorFormatting.aol.raw" });
		//	final AOLCreator aolCreator =
		//		new AOLCreator(
		//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/DispatcherDistribution.aol.raw" });
		final AOLCreator aolCreator =
			new AOLCreator(
				new String[] { "C:/Temp/Giulio's AOL/VSPS/code/VP_FM_Filter.aol.raw" });
		aolCreator.create(codeLevelModel, true);
		System.out.println();
	}
}
