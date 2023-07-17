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
package padl.creator.aolfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.io.Writer;
import java.util.StringTokenizer;
import padl.creator.aolfile.parser.AOLCodeParser;
import padl.creator.aolfile.parser.AOLIdiomParser;
import padl.creator.aolfile.parser.AOLLexer;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/02/20
 */
public class AOLCreator implements ICodeLevelModelCreator {
	public static char[] AOL_PACKAGE_ID = "(AOL package)".toCharArray();
	//	private static final int AOL_CODE = 0;
	//	private static final int AOL_IDIOM = 1;

	private static String computeFilteredFileName(final String aFileName) {
		return aFileName.substring(0, aFileName.indexOf(".aol"))
				+ ".filtered.aol";
	}

	private final String fileName;
	public AOLCreator(final String[] fileNames) {
		// TODO: Must deal with multiple AOL files!
		this.fileName = fileNames[0];
	}
	public void create(final ICodeLevelModel aCodeLevelModel) {
		this.create(aCodeLevelModel, false);
	}
	public void create(
		final ICodeLevelModel aCodeLevelModel,
		final boolean forceFiltering) {

		this.create(aCodeLevelModel, forceFiltering, true);
	}
	public void create(
		final ICodeLevelModel aCodeLevelModel,
		final boolean forceFiltering,
		final boolean enableSemanticActions) {

		try {
			this.filter(forceFiltering);
			//	final ICodeLevelModel codeLevelModel = (ICodeLevelModel)
			this.parse(aCodeLevelModel, enableSemanticActions);

			//	System.out.println(idiomLevelModel);
			//	this.updateModel(aCodeLevelModel, codeLevelModel);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	public void create(
		final IIdiomLevelModel anIdiomLevelModel,
		final boolean forceFiltering,
		final boolean enableSemanticActions) {

		try {
			this.filter(forceFiltering);
			//	final IIdiomLevelModel designLevelModel = (IIdiomLevelModel)
			this.parse(anIdiomLevelModel, enableSemanticActions);

			//	System.out.println(idiomLevelModel);
			//	this.updateModel(anIdiomLevelModel, designLevelModel);
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
	private void filter(final boolean forceFiltering) {
		if (forceFiltering
				|| !new File(AOLCreator.computeFilteredFileName(this.fileName))
					.exists()) {

			ProxyConsole.getInstance().debugOutput().println("Filtering...");

			try {
				final LineNumberReader reader =
					new LineNumberReader(new InputStreamReader(
						new FileInputStream(this.fileName)));
				final StringBuffer buffer = new StringBuffer();
				String readLine;
				while ((readLine = reader.readLine()) != null) {
					buffer.append(readLine);
					buffer.append('\n');
				}
				reader.close();

				// See line 3689 of the original poly-class-code-concat_des_2010-01-29180648.aol.
				this.replace(buffer, "itemModel *,", "itemModel,");

				// See line 3816 of the original poly-class-code-concat_des_2010-01-29180648.aol.
				this
					.replace(
						buffer,
						"PUBLIC slotDeleteCustProperty():protected slots: public slots: void,",
						"PUBLIC slotDeleteCustProperty():void,");

				// See line 81 of the original Sodali's ADM.aol file.
				this.replace(buffer, "operator =", "operator=");

				// See line 175 of the original Sodali's ADM.aol file.
				this.replace(buffer, "consts", "const");

				// See line 187 of the original Sodali's ADM.aol file.
				this.replace(buffer, " ~ ", " ~");

				// See line 567 of the original Sodali's ADM.aol file.
				this.replace(buffer, " One", " ONE");

				// See line 584 of the original Sodali's ADM.aol file.
				this.replace(
					buffer,
					"CLASS ADM_ProfileDomain MULT ONE",
					"CLASS ADM_ProfileDomain MULT ONE;");

				// See line 584 of the original Sodali's ADM.aol file.
				this.replace(buffer, "NAMEe`_eseguito_da", "");

				// See line 584 of the original Sodali's ADM.aol file.
				this.replace(buffer, "NAMEesegue", "");

				// See line 698 of the original Sodali's ADM.aol file.
				this.replace(buffer, "e`attivatoda", "activation");

				// See line 698 of the original Sodali's ADM.aol file.
				this.replace(buffer, "appartengonoa", "aggregation");

				// See line 698 of the original Sodali's ADM.aol file.
				this.replace(buffer, "e`compostada", "composition");

				// See line 698 of the original Sodali's ADM.aol file.
				this
					.replace(buffer, "e`abilitata/disabilitatada", "activation");

				// See line 698 of the original Sodali's ADM.aol file.
				this.replace(
					buffer,
					"persistentdata(storing/retrieving)",
					"serialisation");

				// See line 698 of the original Sodali's ADM.aol file.
				this.replace(
					buffer,
					"persistentdata(storing/retriving)",
					"serialisation");

				// See line 788 of the original Sodali's ADM.aol file.
				this.replace(buffer, "ONE;,", "ONE,");

				// See line 16 of the original Sodali's DB.aol file.
				this.replace(buffer, "+$", "$+");

				// See line 97 of the original Sodali's DB.aol file.
				this.replace(buffer, " = \"\"", "");

				// See line 170 of the original Sodali's DB.aol file.
				this.replace(buffer, " & ", " &");

				// See line 258 of the original Sodali's DB.aol file.
				this.replace(buffer, "List <IMS_Id>", "List<IMS_Id>");

				// See line 473 of the original Sodali's DB.aol file.
				this.replace(
					buffer,
					"CLASS DB_ADM_FaultData\tMULT ONE",
					"CLASS DB_ADM_FaultData MULT ONE;");

				// See line 474 of the original Sodali's DB.aol file.
				this.replace(buffer, "NAMEn", "");

				// See line 478 of the original Sodali's DB.aol file.
				this.replace(buffer, "NAMEParent", "");

				// See line 478 of the original Sodali's DB.aol file.
				this.replace(buffer, "NAMEChildren", "");

				// See line 50 of the original Sodali's GUI.aol file.
				this.replace(buffer, "static init(", "init(");

				// See line 72 of the original Sodali's GUI.aol file.
				this.replace(buffer, " virtual ", " ");

				// See line 215 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"asKey : RWCString(): ,",
					"asKey():RWCString,");

				// See line 216 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"asText : RWCString(): ,",
					"asText():RWCString,");

				// See line 306 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"List <CmgOvwObject>",
					"List<CmgOvwObject>");

				// See line 306 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"&selections int argc,",
					"&selections, int argc,");

				// See line 484 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"AlbAttrVarEnum::type",
					"AlbAttrVarEnum_type");

				// See line 1018 of the original Sodali's GUI.aol file.
				this.replace(
					buffer,
					"CLASS MM_ObjectAckRec\tMULT ONE",
					"CLASS MM_ObjectAckRec MULT ONE;");

				// See line 1239 of the original Sodali's GUI.aol file.
				this.replace(buffer, "ONE;,", "ONE,");

				// See line 96 of the original Sodali's NMI.aol file.
				this.replace(
					buffer,
					"CORBA::SystemException",
					"CORBA_SystemException");

				// See line 359 of the original Sodali's TM.aol file.
				// For Chrome 15.0.838.0
				//				this.replace(buffer, "NAME child", "");

				// See line 361 of the original Sodali's TM.aol file.
				this.replace(buffer, "NAME parent", "");

				// See line 361 of the original Sodali's TM.aol file.
				this.replace(
					buffer,
					"CLASS TM_MsgVnmFIFO\tMULT ONE",
					"CLASS TM_MsgVnmFIFO MULT ONE;");

				// See line 295 of the original Sodali's TM.aol file.
				this.replace(buffer, "ONE;;", "ONE;");

				// See line 348 of the original Sodali's TM.aol file.
				this.replace(buffer, "ONE;,", "ONE,");

				// See line 348 of the original Sodali's TM.aol file.
				this.replace(buffer, "int; ", "int ");

				// See line 336 of the original Sodali's TM.aol file.
				this.replace(
					buffer,
					"MULT ONE\n RELATION",
					"MULT ONE;\n RELATION");

				// See line 359 of the original Sodali's TM.aol file.
				this.replace(buffer, "NAMEchild", "");

				// See line 360 of the original Sodali's TM.aol file.
				this.replace(buffer, "NAMEparent", "");

				// See line 1 of the original Sodali's TM.aol file.
				this.replace(buffer, " CLASS", "CLASS");

				// See line 48875 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this
					.replace(
						buffer,
						" struct {\n                int hits[GC_CACHE_SIZE];\n                int misses;\n                int reclaim;\n               } GCCacheStats;",
						"");

				// See line 97071 of the original mozilla-1.1-concat_des_2006-02-14082728.aol file.
				this
					.replace(
						buffer,
						" struct { \n                int hits[GC_CACHE_SIZE];\n                int misses;\n                int reclaim;\n               } GCCacheStats;",
						"");

				// See line 238085 of the original mozilla-1.1-concat_des_2006-02-14082728.aol file.
				this
					.replace(
						buffer,
						"CONST_nsstype\n  nsstype *&object;                                \\\npublic:                                            \\\n  nsstype\n    :object {  }                           \\\n  ~nsstype\n    if  {                                   \\\n      cleanfunc;                           \\\n      object",
						"");

				// See line 68161 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "%get()->", "");

				// See line 123869 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "n, n* or n%", "");

				// See line 24363 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, ":CLASSNAME~CLASSNAME", "BOOO");

				// See line 24353 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "CLASSNAME:BOOO", "BOOO");

				// See line 24358 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "CLASSNAME", "BOOO");

				// See line 107174 of the original mozilla-1.1-concat_des_2006-02-14082728.aol file.
				this.replace(buffer, "meant to be\n   * overridden", "");

				// See line 162533 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "for HTML or XML", "");

				// See line 171680 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "/*out* ", "");

				// See line 179417 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "__RPC_FAR *__RPC_FAR ", "");

				// See line 179417 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "__RPC_FAR ", "");

				// See line 26371 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "QueuedFileData *", "QueuedFileData*");

				// See line 190240 of the original mozilla-1.4-concat_des_2006-02-14072834.aol file.
				this.replace(buffer, ", class ns", ", ns");

				// See line 103613 of the original mozilla-1.4-concat_des_2006-02-14072834.aol file.
				this
					.replace(
						buffer,
						"\n                            GetStyleData(eStyleStruct_\n     }",
						"");

				// See line 42846 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this
					.replace(
						buffer,
						"        PRIVATE operator_=(CONST_nsstype\n  nsstype *&object;                                \\\npublic:                                            \\\n  nsstype\n    :object {  }                           \\\n  ~nsstype\n    if  {                                   \\\n      cleanfunc;                           \\\n      object):\\ \\ nsstype nsstype void\n",
						"");

				// See line 143468 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this
					.replace(
						buffer,
						"/ *0: not locked *-1: a native lockfile call is in progress *> 0: ",
						"");

				// See line 79623 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "_CONTAINER CLASS ", "_ CONTAINER CLASS ");

				// See line 80197 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(
					buffer,
					"PUBLIC _size_map[WORDS_TO_BYTES(MAXOBJSZ+1):unsigned,\n",
					"");

				// See line 80197 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(
					buffer,
					"PUBLIC _modws_valid_offsets[sizeof(word):char\n",
					"");

				// See line 80197 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "PUBLIC (()[BYTES_TO_WORDS]):BOOO,\n", "");

				// See line 146328 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this
					.replace(
						buffer,
						"(full text and pos vs. cutted text and col0, glphyTextLen vs. replaceBefore/-After):/** <em>Note< m>: I use different strategies to pass context between the,\n",
						"():void,\n");

				// See line 58991 of the original mozilla-1.7-concat_des_2006-02-16032602.aol file.
				this.replace(buffer, "PRIVATE !(defined && defined):!,", "");

				// See line 58679 of the original mozilla-1.7b-concat_des_2006-02-13183827.aol file.
				this
					.replace(
						buffer,
						"PRIVATE (!defined || __GNUC__ > 2 || __GNUC_MINOR__ > 95):BOOO,",
						"");

				// See line 238991 of the original mozilla-1.7-concat_des_2006-02-16032602.aol file.
				this.replace(
					buffer,
					"classnsUnicodeToUTF16:nsUnicodeToUTF16BE",
					"");

				// See line 505 of the original DB_2.1.2.N.aol file.
				this.replace(
					buffer,
					":_Error_, List<VNM_Id> & idVNMNWObjectList)",
					"");

				// See line 656 of the original DB_2.1.2.N.aol file.
				this
					.replace(
						buffer,
						"PUBLIC loadConnectedObjectIdList(void):_Error_, List<PointToPointItem> & connectedIdList):_Error_,",
						"PUBLIC loadConnectedObjectIdList(List<PointToPointItem> & connectedIdList):_Error_,");

				// See line 691 of the original NMI_2.2.0.N.aol file.
				this.replace(
					buffer,
					"PUBLIC info2(the default level):void,",
					"PUBLIC info2():void,");

				// See line 1936 of the original AlarmBrowser.aol.raw file.
				this.replace(buffer, "int atPosition = -1", "int atPosition");

				// See line 2086 of the original AlarmBrowser.aol.raw file.
				this.replace(buffer, "row = XTBL_ALL_TABLE", "row");

				// See line 3487 of the original AlarmBrowser.aol.raw file.
				this.replace(
					buffer,
					"RWBoolean waitOnLock = TRUE",
					"RWBoolean waitOnLock");

				// See line 237 of the original DbConn.aol.raw file.
				this.replace(
					buffer,
					"sqlBoolean_t bAutoMessagesHandler = SQL_TRUE",
					"sqlBoolean_t bAutoMessagesHandler");

				// See line 223 of the original VSPA_Processing.aol.raw file.
				this.replace(
					buffer,
					"int iCallerIsValidation = 1",
					"int iCallerIsValidation");

				// See line 81 of the original CollectorAcquisition.aol.raw file.
				this.replace(buffer, "int mode = 2", "int mode");

				// See line 17 of the original CollectorDistribution.aol.raw file.
				this.replace(
					buffer,
					"int entryType = DIRS | REG_FILES | LINKS",
					"int entryType");

				// See line 2735 of the original AlarmBrowser.aol.raw file.
				this.replace(
					buffer,
					"const AlbSortingCriteria",
					"AlbSortingCriteria");

				// See line 3760 of the original AlarmBrowser.aol.raw file.
				this.replace(buffer, "const AlbFilterBase", "AlbFilterBase");

				// See line 1937 of the original AlarmBrowser.aol.raw file.
				this.replace(buffer, "(T* * , T* *)", "a");

				// See line 235 of the original LOGLIB.aol.raw file.
				this.replace(
					buffer,
					"cfgFile class LogCfgFile : public Config",
					"Config");

				// See line 78 of the original Error_Library.aol.raw file.
				this.replace(buffer, "RC_class:const", "rcc:RC_class");

				// See line 146328 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "GENERALIZATIONS", "GENERALIZATION");

				// See line 89636 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "_MULT ", "_ MULT ");

				// See line 146328 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "unisgned", "unsigned");

				// See line 144343 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "long long ", "long ");

				// See line 73486 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, " long long,", " long,");

				// See line 73486 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, " long long\n", " long\n");

				// See line 800 of the original ddd.raw.aol file.
				this.replace(buffer, ":public:", ":");

				// See line 31063 of the original mozilla-1.5-concat_des_2006-02-13183653.aol file.
				this.replace(buffer, " public: ", " ");

				// See line 23120 of the original ddd.raw.aol file.
				this.replace(buffer, ":protected:", ":");

				// See line 59133 of the original mozilla-1.6-concat_des_2006-02-14215550.aol file.
				this.replace(buffer, " protected: ", " ");

				// See line 144343 of the original mozilla-1.1-concat_des_2006-02-14082728.aol file.
				this.replace(buffer, " = 0", "");

				// See line 1441 of the original ddd.raw.aol file.
				this.replace(buffer, ":,", ",");

				// See lines 904, 2697 of the original ddd.raw.aol file.
				this.replace(buffer, "* *", "*");
				this.replace(buffer, "& *", "*");

				// See line 2274 of the original ddd.raw.aol file.
				this.replace(buffer, "* operator", "*");

				// See line 4864 of the original ddd.raw.aol file.
				this.replace(buffer, ":inline", ":");

				// See line 712 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " & ", "& ");

				// See line 1208 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, ":friend ", ": ");

				// See line 1399 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " * ", "* ");

				// See line 4241 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " struct ", " ");

				// See line 95 of the original DistPublic.aol.raw file.
				this.replace(buffer, "struct tm ", " tm ");

				// See line 19783 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, ":struct ", ": ");

				// See line 18918 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " *& ", "* ");

				// See line 28646 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " ** ", "** ");

				// See line 1320 of the original Admin_2.3.3.N.aol file.
				this.replace(buffer, ":const ", ":");

				// See line 70 of the original Admin_2.3.3.N.aol file.
				this.replace(buffer, "char* const", "char*");

				// See line 647 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " const ,", ",");

				// See line 28412 of the original ddd.raw.aol file.
				this.replace(buffer, " const*& ", " ");

				// See line 31402 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, " const& ", " ");

				// See line 29247 of the original ddd.raw.aol file.
				this.replace(buffer, " const* ", " ");

				// See line 48356 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " const ", "");

				// See line 49728 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, " const\n", "");

				// See line 49168 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " const,", ",");

				// See line 31402 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "_XCONST_unsigned ", "unsigned ");

				// See line 44163 of the original mozilla-1.2-concat_des_2006-02-13180947.aol file.
				this.replace(buffer, "CONST_unsigned ", "unsigned ");

				// See line 88774 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, "CONST_struct ", "CONST_");

				// See line 148150 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, "CONST_typename ", "CONST_");

				// See line 31087 of the original ddd.raw.aol file.
				this.replace(buffer, ": inline ", ":");

				// See line 46198 of the original ddd.raw.aol file.
				this.replace(buffer, "(struct ", "(");

				// See line 91661 of the original ddd.raw.aol file.
				this.replace(buffer, ":private:", ":");

				// See line 181502 of the original ddd.raw.aol file.
				this.replace(buffer, "(class ", "(");

				// See line 192802 of the original ddd.raw.aol file.
				this.replace(buffer, "__inline__ ", "");

				// See line 237853 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, " enum ", "");

				// See line 237853 of the original moz-1.0.rel.n.aol file.
				this.replace(buffer, "enum ", "");

				// See line 41 of the original ConfigLib file.
				this.replace(buffer, "CONTAINERCLASS ", "CONTAINER CLASS ");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "CLASSCLASS_NAME", "CLASS CLASS_NAME");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"SUBCLASSESCLASS_NAME",
					"SUBCLASSES CLASS_NAME");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "tm tm", "tm_tm");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"RegElement\tMULT ONE",
					"RegElement MULT ONE;");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "void* () pFunc", "void* pFunc");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"IMS_Trap\tMULT ONE\n",
					"IMS_Trap MULT ONE;");

				// See line 141 of the original Error_Library file.
				this
					.replace(buffer, "_Error_\tMULT ONE\n", "_Error_ MULT ONE;");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "; : ", " : ");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "&cfgVER'", "&cfgVER,");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "unsigned int to", "unsigned int");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"CLASS Binding\tMULT ONE\n",
					"CLASS Binding MULT ONE;\n");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "sServerName char*", "sServerName, char*");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"CLASS Vbs_VmcsServerThread\tMULT ONE",
					"CLASS Vbs_VmcsServerThread MULT ONE;");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"GENERALIZATION cfgFileclassLogCfgFile:Config",
					"GENERALIZATION Config");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, " = 1", "");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "const unsigned int", "const int");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"*puiStartTime unsigned",
					"*puiStartTime, unsigned");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "Many", "MANY");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"Vbs_tblFileStus   MULT ONE\n",
					"Vbs_tblFileStus\tMULT ONE;\n");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "MULT 1", "MULT ONE");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "QUALIFIER uiPortNumber", "");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"VP_FM_TableElement\tMULT MANY",
					"VP_FM_TableElement MULT MANY;");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"        List<VNM_Id>& idVNMNWObjectList):_Error_,",
					"");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"       List<PointToPointItem> &connectedIdList):_Error_,",
					"");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "rcs9[64 -", "");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"ThreadObjectclassNotifyServerInterface:Interface",
					"ThreadObjectclassNotifyServerInterface");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"ThreadObjectclassRPCServerInterface:Interface",
					"ThreadObjectclassRPCServerInterface");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, " = FALSE", "");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, " = Open", "");

				// See line 141 of the original Error_Library file.
				this
					.replace(
						buffer,
						"AttributeState::AlarmState st, AttributeSeverity::AlarmSeverity sev,",
						"AlarmState st, AlarmSeverity sev,");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, " = true", "");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, " = TRUE", "");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"CLASS AttributeCriteriaBase\tMULT MANY\n",
					"CLASS AttributeCriteriaBase MULT MANY;\n");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "MULT 2", "MULT MANY");

				// See line 141 of the original Error_Library file.
				this.replace(buffer, "MULT ExactlyOne", "MULT ONE");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"CLASS Vbs_tblFileStus\tMULT ONE\n",
					"CLASS Vbs_tblFileStus MULT ONE;\n");

				// See line 141 of the original Error_Library file.
				this.replace(
					buffer,
					"AGGREGATION TypeList CONTAINER",
					"AGGREGATION NAME TypeList CONTAINER");

				// See line 177 of the original access_bkmkd_page.0.aol file.
				this.replace(buffer, ":,", ":void,");

				// See line 2539 of the original access_bkmkd_page.0.aol file.
				this.replace(buffer, ":\n", ":void\n");

				// See line 20342 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\,\n", ":void,\n");

				// See line 112958 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\\n", ":void\n");

				// See line 27748 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\ \\,\n", ":void,\n");

				// See line 48875 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ",)", ")");

				// See line 48875 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "():, ", "(");

				// See line 65579 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "FAR* ", "");

				// See line 50220 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "):, ", "");

				// See line 86706 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\ \\\n", ":void\n");

				// See line 86706 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, " ()():", " dummy():");

				// See line 104868 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "%* ", "%*");

				// See line 3088 of the original Chrome 15.0.837.0
				this.replace(buffer, "* >", "*>");

				// See line 3088 of the original Chrome 15.0.837.0
				this.replace(buffer, "%", "::");

				// See line 3175 of the original Chrome 15.0.837.0
				this.replace(buffer, ":: ", " ");

				// See line 12243 of the original Chrome 15.0.837.0
				this.replace(buffer, "\\constcmd::nameargs)", "constcmd");

				// See line 13427 of the original Chrome 15.0.837.0
				this.replace(buffer, " ::", " ");

				// See line 13429 of the original Chrome 15.0.837.0
				this.replace(buffer, "(::", "(");

				// See line 13480 of the original Chrome 15.0.837.0
				this.replace(
					buffer,
					"PRIVATE base::Callback<void(int32):base::Callback<void\n",
					"");

				// See line 13748 of the original Chrome 15.0.837.0
				this.replace(
					buffer,
					"GLES2DecoderTestBase testing::StrictMock",
					"GLES2DecoderTestBase::StrictMock");

				// See line 14425 of the original Chrome 15.0.837.0
				this.replace(buffer, "char unsigned", "unsigned char");

				// See line 15658 of the original Chrome 15.0.837.0
				this
					.replace(
						buffer,
						"CONST_StackVector<T_stack_capacity>& other):StackVector<T, stack_capacity>&,\n",
						"");

				// See line 16251 of the original Chrome 15.0.837.0
				this.replace(
					buffer,
					"PUBLIC <void(BaseMixin::*):template,\n",
					"");

				// See line 16251 of the original Chrome 15.0.837.0
				this
					.replace(
						buffer,
						"PUBLIC COMPILE_ASSERT(sizeof > sizeof, unexpected_type_width):COMPILE_ASSERT,\n",
						"");

				// See line 112955 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\ \\ \\,\n", ":void,\n");

				// See line 112959 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":\\\n", ":\\\n");

				// See line 146328 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, "'", "");

				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":/*,\n", ":void,\n");

				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":__published:", ":");
				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, ":template< class T1, class T2>", ":");
				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(
					buffer,
					":template< class T1, class T2, class T3>",
					":");
				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(
					buffer,
					":template< class T1, class T2, class T3, class T4>",
					":");
				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				//this.replace(buffer, "PRIVATE", "PUBLIC");

				// See line 49317 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				this.replace(buffer, " __fastcall", "");
				//this.replace(buffer, " __fastcall,", "void __fastcall,");

				//See line 2382 of the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "FAR", "");
				//See line  7Â 628 of the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "\"side\"/\"top\"", "");
				//See line  7Â 629 of the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "\"yes\"/\"no\"", "");
				//See line  33Â 905  the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "unsigned short int", "int");
				//See line  33Â 074  the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "unsigned long int", "int");
				//See line 34Â 398  the original mozilla_01-01-2007-concat_des_2008-04-21141302.aol file.
				this.replace(buffer, "int ()", "int x");

				// See line 50220 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				// See line 79929 of the original mozilla-1.0-concat_des_2006-02-15114305.aol file.
				int pos = 0;
				while ((pos = buffer.indexOf("/*", pos)) > 0) {
					final int endPos1 = buffer.indexOf("* /", pos);
					final int endPos2 = buffer.indexOf("\n        ;", pos);
					final int endPos3 = buffer.indexOf("\n", pos);

					if (endPos1 > 0 && (endPos1 < endPos2 || endPos2 == -1)
							&& (endPos1 < endPos3 || endPos3 == -1)) {

						buffer.replace(pos, endPos1 + 3, "");
					}
					else if (endPos2 > 0
							&& (endPos2 <= endPos3 || endPos3 == -1)) {

						buffer.replace(pos, endPos2, "void");
					}
					else if (endPos3 > 0) {
						buffer.replace(pos, endPos3, "void,");
					}
					else {
						ProxyConsole
							.getInstance()
							.errorOutput()
							.println("Cannot find end of comment!");
					}
					pos++;
				}

				final Writer writer =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						AOLCreator.computeFilteredFileName(this.fileName));
				writer.write(buffer.toString());
				writer.close();

				ProxyConsole
					.getInstance()
					.debugOutput()
					.println(
						MultilingualManager.getString(
							"FILTERING_DONE",
							AOLCreator.class));
			}
			catch (final FileNotFoundException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	private IAbstractLevelModel parse(
		final IAbstractLevelModel anAbstractLevelModel,
		final boolean enableSemanticActions) {

		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(MultilingualManager.getString("PARSING", AOLCreator.class));

		IAbstractLevelModel aLevelModel = null;
		//	if (aModelType == AOLCreator.AOL_CODE) {
		//		aLevelModel =
		//			Factory.getInstance().createCodeLevelModel(
		//				"Code-level model built using AOLCreator");
		//	}
		//	else if (aModelType == AOLCreator.AOL_IDIOM) {
		//		aLevelModel =
		//			Factory.getInstance().createIdiomLevelModel(
		//				"Idiom-level model built using AOLCreator".toCharArray());
		//	}

		try {
			final LineNumberReader reader =
				new LineNumberReader(new InputStreamReader(new FileInputStream(
					AOLCreator.computeFilteredFileName(this.fileName))));

			// Yann 2008/11/17: Package!
			// I now manage packages properly, so I create
			// the default package first.
			final IPackage enclosingPackage =
				Factory.getInstance().createPackage(AOLCreator.AOL_PACKAGE_ID);
			anAbstractLevelModel.addConstituent(enclosingPackage);

			// Yann 2007/02/01: Classes and ghosts
			// I first populate the model with classes
			// so that I can build ghosts when appropriate.
			final StringBuffer buffer = new StringBuffer();
			String readLine;
			int numberOfClasses = 0;
			while ((readLine = reader.readLine()) != null) {
				buffer.append(readLine);
				buffer.append('\n');
				if (readLine.indexOf("CLASS") == 0) {
					// Yann 2004/12/14: Giulio's comment!
					// Giulio pointed out to me that we should count only
					// class which definitions start at the very beginning
					// of the line, because the keyword "CLASS" may appear
					// elsewhere on the line due to the AOL file format.
					numberOfClasses++;

					final StringTokenizer tokenizer =
						new StringTokenizer(readLine);
					tokenizer.nextToken();
					final String className = tokenizer.nextToken();
					enclosingPackage.addConstituent(Factory
						.getInstance()
						.createClass(
							className.toCharArray(),
							className.toCharArray()));
				}
			}
			reader.close();
			ProxyConsole.getInstance().debugOutput().print(' ');
			ProxyConsole.getInstance().debugOutput().print(numberOfClasses);
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(
					MultilingualManager.getString("CLASSES", AOLCreator.class));

			if (anAbstractLevelModel instanceof ICodeLevelModel) {
				final AOLCodeParser parser =
					new AOLCodeParser(new AOLLexer(new StringReader(
						buffer.toString())));
				parser
					.setCodeLevelModel((ICodeLevelModel) anAbstractLevelModel);
				parser.enableSemanticActions(enableSemanticActions);
				// adding for test 

				aLevelModel = (ICodeLevelModel) parser.parse().value;
			}
			else if (anAbstractLevelModel instanceof IIdiomLevelModel) {
				final AOLIdiomParser parser =
					new AOLIdiomParser(new AOLLexer(new StringReader(
						buffer.toString())));
				parser
					.setIdiomLevelModel((IIdiomLevelModel) anAbstractLevelModel);
				parser.enableSemanticActions(enableSemanticActions);
				aLevelModel = (IIdiomLevelModel) parser.parse().value;
			}

			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(
					MultilingualManager.getString(
						"PARSING_DONE",
						AOLCreator.class));
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return aLevelModel;
	}

	private void replace(
		final StringBuffer buffer,
		final String anOldString,
		final String aNewString) {

		ProxyConsole.getInstance().debugOutput().print("Replacing \"");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(
				anOldString.replaceAll("\\n", "\\n").substring(
					0,
					Math.min(anOldString.length(), 35)));
		ProxyConsole.getInstance().debugOutput().print("\" with \"");
		ProxyConsole
			.getInstance()
			.debugOutput()
			.print(
				aNewString.replaceAll("\\n", "\\n").substring(
					0,
					Math.min(aNewString.length(), 35)));
		ProxyConsole.getInstance().debugOutput().print('\"');

		final int length = anOldString.length();
		int pos = 0;
		int changeCount = 0;
		while ((pos = buffer.indexOf(anOldString, pos)) > -1) {
			buffer.replace(pos, pos + length, aNewString);
			pos++;
			changeCount++;
		}
		if (changeCount == 0) {
			ProxyConsole.getInstance().debugOutput().println(" - Not found");
		}
		else {
			ProxyConsole.getInstance().debugOutput().print(" - ");
			ProxyConsole.getInstance().debugOutput().print(changeCount);
			ProxyConsole.getInstance().debugOutput().println(" found");
		}
		ProxyConsole.getInstance().debugOutput().flush();
	}
	//	private void updateModel(
	//		final IAbstractLevelModel originModel,
	//		final IAbstractLevelModel destination) {
	//
	//		// Yann 2004/12/14: Listeners... again!
	//		// Once the idiom-level model is built by the parser, I copy
	//		// all the entities in the given idiom-level model (from the
	//		// arguments). Thus, I count twice every entities! (Once at
	//		// creation/addition time, second at copy time.) To solve
	//		// this problem, I remove temporarily all listeners before
	//		// copying and then add them back.
	//
	//		final Iterator listeners = originModel.getIteratorOnModelListeners();
	//		final List formerListeners = new ArrayList();
	//		while (listeners.hasNext()) {
	//			final IModelListener listener = (IModelListener) listeners.next();
	//			formerListeners.add(listener);
	//		}
	//
	//		// Yann 2010/02/07: ConcurrentModification
	//		// I must remove the listeners in two steps to 
	//		// avoid any concurrent modification exception.
	//		final Iterator nonConcurrentListeners = formerListeners.iterator();
	//		while (nonConcurrentListeners.hasNext()) {
	//			final IModelListener listener =
	//				(IModelListener) nonConcurrentListeners.next();
	//			originModel.removeModelListener(listener);
	//		}
	//
	//		final Iterator iterator = destination.getIteratorOnConstituents();
	//		while (iterator.hasNext()) {
	//			try {
	//				final IConstituentOfModel constituent =
	//					(IConstituentOfModel) iterator.next();
	//				destination.removeConstituentFromID(constituent.getID());
	//				originModel.addConstituent(constituent);
	//			}
	//			catch (final ModelDeclarationException e) {
	//				e.printStackTrace(Output.getInstance().errorOutput());
	//			}
	//		}
	//
	//		originModel.addModelListeners(formerListeners);
	//	}
}
