package padl.creator.cppfile.eclipse.test.helper;

import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import util.io.ProxyConsole;

public class ModelGenerator {
	public static ICodeLevelModel generateModelFromCppFilesUsingEclipse(
			final String aName, final String aSourceDirectory) {

		ICodeLevelModel codeLevelModel = null;
		try {
			final ICodeLevelModelCreator creator = new CPPCreator(
					aSourceDirectory);
			codeLevelModel = CPPFactoryEclipse.getInstance()
					.createCodeLevelModel(aName);
			codeLevelModel.create(creator);
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return codeLevelModel;
	}
}
