package wsmapper;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private List<Project> dependencies;

	public Project(final String aName) {
		this.name = aName;
		this.dependencies = new ArrayList<Project>();
	}
	public void addDependency(final Project aProject) {
		this.dependencies.add(aProject);
	}
	public String getName() {
		return this.name;
	}
	public List<Project> getDependencies() {
		return this.dependencies;
	}
}
