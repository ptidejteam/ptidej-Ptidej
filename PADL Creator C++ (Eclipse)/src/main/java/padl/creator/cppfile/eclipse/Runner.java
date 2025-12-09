package padl.creator.cppfile.eclipse;

import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.cppfile.eclipse.misc.EclipseCPPParserCaller;
import padl.kernel.ICodeLevelModel;

public final class Runner {
    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: Runner <path-to-cpp-sources>");
            System.exit(2);
        }

        final String cppDir = args[0];

        final ICodeLevelModel model =
                CPPFactoryEclipse.getInstance().createCodeLevelModel("C++ Model");

        EclipseCPPParserCaller.getInstance()
                .createCodeLevelModelUsingOSGiRemote(cppDir, model);

        System.out.println("C++ model created. Top-level entities: "
                + model.getNumberOfTopLevelEntities());
    }
}

