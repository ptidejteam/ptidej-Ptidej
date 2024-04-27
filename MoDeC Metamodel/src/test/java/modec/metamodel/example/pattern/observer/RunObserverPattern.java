package modec.metamodel.example.pattern.observer;

//[C] 2002 Sun Microsystems, Inc.---
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RunObserverPattern {
  public static void main(String[] arguments) {
    System.out.println("Example for the Observer pattern");
    System.out.println("This demonstration uses a central observable");
    System.out.println(" object to send change notifications to several");
    System.out.println(" JPanels in a GUI. Each JPanel is an Observer,");
    System.out.println(" receiving notifcations when there has been some");
    System.out.println(" change in the shared Task that is being edited.");
    System.out.println();

    System.out.println("Creating the ObserverGui");
    ObserverGui application = new ObserverGui();
    application.createGui();
  }
}

class Task {
  private String name = "";

  private String notes = "";

  private double timeRequired;

  public Task() {
  }

  public Task(String newName, String newNotes, double newTimeRequired) {
    this.name = newName;
    this.notes = newNotes;
    this.timeRequired = newTimeRequired;
  }

  public String getName() {
    return this.name;
  }

  public String getNotes() {
    return this.notes;
  }

  public double getTimeRequired() {
    return this.timeRequired;
  }

  public void setName(String newName) {
    this.name = newName;
  }

  public void setTimeRequired(double newTimeRequired) {
    this.timeRequired = newTimeRequired;
  }

  public void setNotes(String newNotes) {
    this.notes = newNotes;
  }

  public String toString() {
    return this.name + " " + this.notes;
  }
}

class TaskChangeObservable {
  private ArrayList observers = new ArrayList();

  public void addTaskChangeObserver(TaskChangeObserver observer) {
    if (!this.observers.contains(observer)) {
      this.observers.add(observer);
    }
  }

  public void removeTaskChangeObserver(TaskChangeObserver observer) {
    this.observers.remove(observer);
  }

  public void selectTask(Task task) {
    Iterator elements = this.observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskSelected(task);
    }
  }

  public void addTask(Task task) {
    Iterator elements = this.observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskAdded(task);
    }
  }

  public void updateTask(Task task) {
    Iterator elements = this.observers.iterator();
    while (elements.hasNext()) {
      ((TaskChangeObserver) elements.next()).taskChanged(task);
    }
  }
}

interface TaskChangeObserver {
  public void taskAdded(Task task);

  public void taskChanged(Task task);

  public void taskSelected(Task task);
}

class TaskEditorPanel extends JPanel implements ActionListener,
    TaskChangeObserver {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private JPanel controlPanel, editPanel;

  private JButton add, update, exit;

  private JTextField taskName, taskNotes, taskTime;

  private TaskChangeObservable notifier;

  private Task editTask;

  public TaskEditorPanel(TaskChangeObservable newNotifier) {
    this.notifier = newNotifier;
    createGui();
  }

  public void createGui() {
    setLayout(new BorderLayout());
    this.editPanel = new JPanel();
    this.editPanel.setLayout(new GridLayout(3, 2));
    this.taskName = new JTextField(20);
    this.taskNotes = new JTextField(20);
    this.taskTime = new JTextField(20);
    this.editPanel.add(new JLabel("Task Name"));
    this.editPanel.add(this.taskName);
    this.editPanel.add(new JLabel("Task Notes"));
    this.editPanel.add(this.taskNotes);
    this.editPanel.add(new JLabel("Time Required"));
    this.editPanel.add(this.taskTime);

    this.controlPanel = new JPanel();
    this.add = new JButton("Add Task");
    this.update = new JButton("Update Task");
    this.exit = new JButton("Exit");
    this.controlPanel.add(this.add);
    this.controlPanel.add(this.update);
    this.controlPanel.add(this.exit);
    this.add.addActionListener(this);
    this.update.addActionListener(this);
    this.exit.addActionListener(this);
    add(this.controlPanel, BorderLayout.SOUTH);
    add(this.editPanel, BorderLayout.CENTER);
  }

  public void setTaskChangeObservable(TaskChangeObservable newNotifier) {
    this.notifier = newNotifier;
  }

  public void actionPerformed(ActionEvent event) {
    Object source = event.getSource();
    if (source == this.add) {
      double timeRequired = 0.0;
      try {
        timeRequired = Double.parseDouble(this.taskTime.getText());
      } catch (NumberFormatException exc) {
      }
      this.notifier.addTask(new Task(this.taskName.getText(), this.taskNotes.getText(),
          timeRequired));
    } else if (source == this.update) {
      this.editTask.setName(this.taskName.getText());
      this.editTask.setNotes(this.taskNotes.getText());
      try {
        this.editTask
            .setTimeRequired(Double.parseDouble(this.taskTime.getText()));
      } catch (NumberFormatException exc) {
      }
      this.notifier.updateTask(this.editTask);
    } else if (source == this.exit) {
      System.exit(0);
    }

  }

  public void taskAdded(Task task) {
  }

  public void taskChanged(Task task) {
  }

  public void taskSelected(Task task) {
    this.editTask = task;
    this.taskName.setText(task.getName());
    this.taskNotes.setText(task.getNotes());
    this.taskTime.setText("" + task.getTimeRequired());
  }
}

class TaskHistoryPanel extends JPanel implements TaskChangeObserver {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private JTextArea displayRegion;

  public TaskHistoryPanel() {
    createGui();
  }

  public void createGui() {
    setLayout(new BorderLayout());
    this.displayRegion = new JTextArea(10, 40);
    this.displayRegion.setEditable(false);
    add(new JScrollPane(this.displayRegion));
  }

  public void taskAdded(Task task) {
    this.displayRegion.append("Created task " + task + "\n");
  }

  public void taskChanged(Task task) {
    this.displayRegion.append("Updated task " + task + "\n");
  }

  public void taskSelected(Task task) {
    this.displayRegion.append("Selected task " + task + "\n");
  }
}

class TaskSelectorPanel extends JPanel implements ActionListener,
    TaskChangeObserver {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private JComboBox selector = new JComboBox();

  private TaskChangeObservable notifier;

  public TaskSelectorPanel(TaskChangeObservable newNotifier) {
    this.notifier = newNotifier;
    createGui();
  }

  public void createGui() {
    this.selector = new JComboBox();
    this.selector.addActionListener(this);
    add(this.selector);
  }

  public void actionPerformed(ActionEvent evt) {
    this.notifier.selectTask((Task) this.selector.getSelectedItem());
  }

  public void setTaskChangeObservable(TaskChangeObservable newNotifier) {
    this.notifier = newNotifier;
  }

  public void taskAdded(Task task) {
    this.selector.addItem(task);
  }

  public void taskChanged(Task task) {
  }

  public void taskSelected(Task task) {
  }
}

class ObserverGui {
  public void createGui() {
    JFrame mainFrame = new JFrame("Observer Pattern Example");
    Container content = mainFrame.getContentPane();
    content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
    TaskChangeObservable observable = new TaskChangeObservable();
    TaskSelectorPanel select = new TaskSelectorPanel(observable);
    TaskHistoryPanel history = new TaskHistoryPanel();
    TaskEditorPanel edit = new TaskEditorPanel(observable);
    observable.addTaskChangeObserver(select);
    observable.addTaskChangeObserver(history);
    observable.addTaskChangeObserver(edit);
    observable.addTask(new Task());
    content.add(select);
    content.add(history);
    content.add(edit);
    mainFrame.addWindowListener(new WindowCloseManager());
    mainFrame.pack();
    mainFrame.setVisible(true);
  }

  private class WindowCloseManager extends WindowAdapter {
    public void windowClosing(WindowEvent evt) {
      System.exit(0);
    }
  }
}

