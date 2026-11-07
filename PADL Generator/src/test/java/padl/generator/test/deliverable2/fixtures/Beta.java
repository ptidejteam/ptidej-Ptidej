package padl.generator.test.deliverable2.fixtures;

public class Beta extends Alpha {
	private final Alpha collaborator = new Alpha();

	public Alpha getCollaborator() {
		return this.collaborator;
	}
}
