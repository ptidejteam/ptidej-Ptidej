package modec.metamodel.example.pattern.visitor;

//[C] 2002 Sun Microsystems, Inc.---
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class RunVisitorPattern {
  public static void main(String[] arguments) {
    System.out.println("Example for the Visitor pattern");
    System.out.println();
    System.out
        .println("This sample will use a ProjectCostVisitor to calculate");
    System.out.println(" the total amount required to complete a Project.");
    System.out.println();

    System.out.println("Deserializing a test Project for Visitor pattern");
    System.out.println();
    if (!(new File("tmp/data.ser").exists())) {
      DataCreator.serialize("tmp/data.ser");
    }
    Project project = (Project) (DataRetriever.deserializeData("tmp/data.ser"));

    System.out
        .println("Creating a ProjectCostVisitor, to calculate the total cost of the project.");
    ProjectCostVisitor visitor = new ProjectCostVisitor();
    visitor.setHourlyRate(100);

    System.out
        .println("Moving throuhg the Project, calculating total cost");
    System.out
        .println(" by passing the Visitor to each of the ProjectItems.");
    visitProjectItems(project, visitor);
    System.out.println("The total cost for the project is: "
        + visitor.getTotalCost());
  }

  private static void visitProjectItems(ProjectItem item,
      ProjectVisitor visitor) {
    item.accept(visitor);
    if (item.getProjectItems() != null) {
      Iterator subElements = item.getProjectItems().iterator();
      while (subElements.hasNext()) {
        visitProjectItems((ProjectItem) subElements.next(), visitor);
      }
    }
  }
}

interface Contact extends Serializable {
  public static final String SPACE = " ";

  public String getFirstName();

  public String getLastName();

  public String getTitle();

  public String getOrganization();

  public void setFirstName(String newFirstName);

  public void setLastName(String newLastName);

  public void setTitle(String newTitle);

  public void setOrganization(String newOrganization);
}

class Task implements ProjectItem {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private String name;

  private ArrayList projectItems = new ArrayList();

  private Contact owner;

  private double timeRequired;

  public Task() {
  }

  public Task(String newName, Contact newOwner, double newTimeRequired) {
    this.name = newName;
    this.owner = newOwner;
    this.timeRequired = newTimeRequired;
  }

  public String getName() {
    return this.name;
  }

  public ArrayList getProjectItems() {
    return this.projectItems;
  }

  public Contact getOwner() {
    return this.owner;
  }

  public double getTimeRequired() {
    return this.timeRequired;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public void setOwner(Contact newOwner) {
    this.owner = newOwner;
  }

  public void setTimeRequired(double newTimeRequired) {
    this.timeRequired = newTimeRequired;
  }

  public void addProjectItem(ProjectItem element) {
    if (!this.projectItems.contains(element)) {
      this.projectItems.add(element);
    }
  }

  public void removeProjectItem(ProjectItem element) {
    this.projectItems.remove(element);
  }

  public void accept(ProjectVisitor v) {
    v.visitTask(this);
  }
}

class Deliverable implements ProjectItem {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private String name;

  private String description;

  private Contact owner;

  private double materialsCost;

  private double productionCost;

  public Deliverable() {
  }

  public Deliverable(String newName, String newDescription, Contact newOwner,
      double newMaterialsCost, double newProductionCost) {
    this.name = newName;
    this.description = newDescription;
    this.owner = newOwner;
    this.materialsCost = newMaterialsCost;
    this.productionCost = newProductionCost;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public Contact getOwner() {
    return this.owner;
  }

  public double getMaterialsCost() {
    return this.materialsCost;
  }

  public double getProductionCost() {
    return this.productionCost;
  }

  public void setMaterialsCost(double newCost) {
    this.materialsCost = newCost;
  }

  public void setProductionCost(double newCost) {
    this.productionCost = newCost;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  public void setOwner(Contact newOwner) {
    this.owner = newOwner;
  }

  public void accept(ProjectVisitor v) {
    v.visitDeliverable(this);
  }

  public ArrayList getProjectItems() {
    return null;
  }
}

interface ProjectVisitor {
  public void visitDependentTask(DependentTask p);

  public void visitDeliverable(Deliverable p);

  public void visitTask(Task p);

  public void visitProject(Project p);
}

interface ProjectItem extends Serializable {
  public void accept(ProjectVisitor v);

  public ArrayList getProjectItems();
}

class ContactImpl implements Contact {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private String firstName;

  private String lastName;

  private String title;

  private String organization;

  public ContactImpl() {
  }

  public ContactImpl(String newFirstName, String newLastName,
      String newTitle, String newOrganization) {
    this.firstName = newFirstName;
    this.lastName = newLastName;
    this.title = newTitle;
    this.organization = newOrganization;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getTitle() {
    return this.title;
  }

  public String getOrganization() {
    return this.organization;
  }

  public void setFirstName(String newFirstName) {
    this.firstName = newFirstName;
  }

  public void setLastName(String newLastName) {
    this.lastName = newLastName;
  }

  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  public void setOrganization(String newOrganization) {
    this.organization = newOrganization;
  }

  public String toString() {
    return this.firstName + SPACE + this.lastName;
  }
}

class DataCreator {
  private static final String DEFAULT_FILE = "tmp/data.ser";

  public static void main(String[] args) {
    String fileName;
    if (args.length == 1) {
      fileName = args[0];
    } else {
      fileName = DEFAULT_FILE;
    }
    serialize(fileName);
  }

  public static void serialize(String fileName) {
    try {
      serializeToFile(createData(), fileName);
    } catch (IOException exc) {
      exc.printStackTrace();
    }
  }

  private static Serializable createData() {
    Contact contact = new ContactImpl("Test", "Subject", "Volunteer",
        "United Patterns Consortium");

    Project project = new Project("Project 1", "Test Project");

    Task task1 = new Task("Task 1", contact, 1);
    Task task2 = new Task("Task 2", contact, 1);

    project.addProjectItem(new Deliverable("Deliverable 1",
        "Layer 1 deliverable", contact, 50.0, 50.0));
    project.addProjectItem(task1);
    project.addProjectItem(task2);
    project.addProjectItem(new DependentTask("Dependent Task 1", contact,
        1, 1));

    Task task3 = new Task("Task 3", contact, 1);
    Task task4 = new Task("Task 4", contact, 1);
    Task task5 = new Task("Task 5", contact, 1);
    Task task6 = new Task("Task 6", contact, 1);

    DependentTask dtask2 = new DependentTask("Dependent Task 2", contact,
        1, 1);

    task1.addProjectItem(task3);
    task1.addProjectItem(task4);
    task1.addProjectItem(task5);
    task1.addProjectItem(dtask2);

    dtask2.addDependentTask(task5);
    dtask2.addDependentTask(task6);
    dtask2.addProjectItem(new Deliverable("Deliverable 2",
        "Layer 3 deliverable", contact, 50.0, 50.0));

    task3.addProjectItem(new Deliverable("Deliverable 3",
        "Layer 3 deliverable", contact, 50.0, 50.0));
    task4.addProjectItem(new Task("Task 7", contact, 1));
    task4.addProjectItem(new Deliverable("Deliverable 4",
        "Layer 3 deliverable", contact, 50.0, 50.0));
    return project;
  }

  private static void serializeToFile(Serializable content, String fileName)
      throws IOException {
    ObjectOutputStream serOut = new ObjectOutputStream(
        new FileOutputStream(fileName));
    serOut.writeObject(content);
    serOut.close();
  }
}

class DataRetriever {
  public static Object deserializeData(String fileName) {
    Object returnValue = null;
    try {
      File inputFile = new File(fileName);
      if (inputFile.exists() && inputFile.isFile()) {
        ObjectInputStream readIn = new ObjectInputStream(
            new FileInputStream(fileName));
        returnValue = readIn.readObject();
        readIn.close();
      } else {
        System.err.println("Unable to locate the file " + fileName);
      }
    } catch (ClassNotFoundException exc) {
      exc.printStackTrace();

    } catch (IOException exc) {
      exc.printStackTrace();

    }
    return returnValue;
  }
}

class DependentTask extends Task {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private ArrayList dependentTasks = new ArrayList();

  private double dependencyWeightingFactor;

  public DependentTask() {
  }

  public DependentTask(String newName, Contact newOwner,
      double newTimeRequired, double newWeightingFactor) {
    super(newName, newOwner, newTimeRequired);
    this.dependencyWeightingFactor = newWeightingFactor;
  }

  public ArrayList getDependentTasks() {
    return this.dependentTasks;
  }

  public double getDependencyWeightingFactor() {
    return this.dependencyWeightingFactor;
  }

  public void setDependencyWeightingFactor(double newFactor) {
    this.dependencyWeightingFactor = newFactor;
  }

  public void addDependentTask(Task element) {
    if (!this.dependentTasks.contains(element)) {
      this.dependentTasks.add(element);
    }
  }

  public void removeDependentTask(Task element) {
    this.dependentTasks.remove(element);
  }

  public void accept(ProjectVisitor v) {
    v.visitDependentTask(this);
  }
}

class Project implements ProjectItem {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private String name;

  private String description;

  private ArrayList projectItems = new ArrayList();

  public Project() {
  }

  public Project(String newName, String newDescription) {
    this.name = newName;
    this.description = newDescription;
  }

  public String getName() {
    return this.name;
  }

  public String getDescription() {
    return this.description;
  }

  public ArrayList getProjectItems() {
    return this.projectItems;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  public void addProjectItem(ProjectItem element) {
    if (!this.projectItems.contains(element)) {
      this.projectItems.add(element);
    }
  }

  public void removeProjectItem(ProjectItem element) {
    this.projectItems.remove(element);
  }

  public void accept(ProjectVisitor v) {
    v.visitProject(this);
  }
}

class ProjectCostVisitor implements ProjectVisitor {
  private double totalCost;

  private double hourlyRate;

  public double getHourlyRate() {
    return this.hourlyRate;
  }

  public double getTotalCost() {
    return this.totalCost;
  }

  public void setHourlyRate(double rate) {
    this.hourlyRate = rate;
  }

  public void resetTotalCost() {
    this.totalCost = 0.0;
  }

  public void visitDependentTask(DependentTask p) {
    double taskCost = p.getTimeRequired() * this.hourlyRate;
    taskCost *= p.getDependencyWeightingFactor();
    this.totalCost += taskCost;
  }

  public void visitDeliverable(Deliverable p) {
    this.totalCost += p.getMaterialsCost() + p.getProductionCost();
  }

  public void visitTask(Task p) {
    this.totalCost += p.getTimeRequired() * this.hourlyRate;
  }

  public void visitProject(Project p) {
  }
}
