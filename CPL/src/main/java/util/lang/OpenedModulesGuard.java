package util.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.InaccessibleObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OpenedModulesGuard {
	private static class PackageInfo {
		private final String moduleName;
		private final String packageName;
		private final String className;

		private PackageInfo(final String aModuleName, final String aClassName) {
			this.moduleName = aModuleName;
			this.className = aClassName;

			this.packageName = this.className.substring(0,
					this.className.lastIndexOf('.'));
		}
	}

	private static OpenedModulesGuard UNIQUE_INSTANCE;

	public static OpenedModulesGuard getInstance() {
		if (OpenedModulesGuard.UNIQUE_INSTANCE == null) {
			OpenedModulesGuard.UNIQUE_INSTANCE = new OpenedModulesGuard();
		}
		return OpenedModulesGuard.UNIQUE_INSTANCE;
	}

	private List<PackageInfo> list = new ArrayList<>();

	private OpenedModulesGuard() {
	}

	public void addOpenedModuleCheck(final String aModuleName,
			final String aClassName) {

		this.list.add(new PackageInfo(aModuleName, aClassName));
	}

	public Optional<String> checkOpenedModules() {
		for (final PackageInfo packageInfo : this.list) {
			try {
				final Class<?> clazz = Class.forName(packageInfo.className);
				final Constructor<?>[] constructors = clazz
						.getDeclaredConstructors();
				final Constructor<?> constructor = constructors[0];
				constructor.setAccessible(true);
			}
			catch (final ClassNotFoundException
					| InaccessibleObjectException e) {

				return Optional.of("Module " + packageInfo.moduleName
						+ " does not opens " + packageInfo.packageName
						+ "\nDid you forget to add \"--add-opens="
						+ packageInfo.moduleName + '/' + packageInfo.packageName
						+ "=ALL-UNNAMED\" in Maven, run configuration, or command line?");
			}
		}

		return Optional.empty();
	}
}
