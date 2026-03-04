package padl.creator.cppfile.eclipse.test.helper;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.creator.cppfile.eclipse.CPPCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import util.io.ProxyConsole;

public class ModelGenerator {
	public static ICodeLevelModel generateModelFromCppTestResources(
		final String aName,
		final String aResourceDirectory) {

		final String sourceDirectory = resolveTestResourceDirectory(
			aResourceDirectory);
		return generateModelFromCppFilesUsingEclipse(aName, sourceDirectory);
	}

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

	private static String resolveTestResourceDirectory(
		final String aResourceDirectory) {

		final URL resource = ModelGenerator.class.getClassLoader()
			.getResource(aResourceDirectory);
		if (resource != null && "file".equals(resource.getProtocol())) {
			try {
				final Path path = Paths.get(resource.toURI());
				return path.toFile().getAbsolutePath();
			}
			catch (final Exception e) {
				// Fall back to the value as-is when URI conversion fails.
			}
		}

		final String normalizedDirectory =
			aResourceDirectory.replace('\\', '/').replaceFirst("^/+", "");
		final Path relativeDirectory = Paths.get(normalizedDirectory);
		final Path[] candidateBases =
			new Path[] {
				Paths.get("src", "test", "resources"),
				Paths.get("PADL Creator C++ (Eclipse)", "src", "test", "resources"),
				Paths.get(System.getProperty("user.dir"), "src", "test", "resources"),
				Paths.get(
					System.getProperty("user.dir"),
					"PADL Creator C++ (Eclipse)",
					"src",
					"test",
					"resources") };
		for (final Path candidateBase : candidateBases) {
			final Path candidate = candidateBase.resolve(relativeDirectory)
				.normalize()
				.toAbsolutePath();
			if (Files.isDirectory(candidate)) {
				return candidate.toString();
			}
		}
		return aResourceDirectory;
	}
}
