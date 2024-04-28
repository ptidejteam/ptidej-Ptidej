package util.lang;

public class MavenTestGuard {
	private static MavenTestGuard UNIQUE_INSTANCE;

	public static MavenTestGuard getInstance() {
		if (MavenTestGuard.UNIQUE_INSTANCE == null) {
			MavenTestGuard.UNIQUE_INSTANCE = new MavenTestGuard();
		}
		return MavenTestGuard.UNIQUE_INSTANCE;
	}

	private MavenTestGuard() {
	}

	public boolean isRunningInsideMavenTest() {
		return System.out.getClass().getName().contains("maven");
	}

	public boolean isRunningOutsideMavenTest() {
		return !this.isRunningInsideMavenTest();
	}
}
