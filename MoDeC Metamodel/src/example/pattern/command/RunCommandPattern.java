package example.pattern.command;

//[C] 2002 Sun Microsystems, Inc.---
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class RunCommandPattern {
    private static Calendar dateCreator = Calendar.getInstance();

    public static void main(String [] arguments){
        System.out.println("Example for the Command pattern");
        System.out.println();
        System.out.println("This sample will use a command class called");
        System.out.println(" ChangeLocationCommand to update the location");
        System.out.println(" of an Appointment object.");
        System.out.println("The ChangeLocationCommand has the additional");
        System.out.println(" ability to undo and redo commands, so it can");
        System.out.println(" set the locaition back to its original value,");
        System.out.println(" if desired.");
        System.out.println();

        System.out.println("Creating an Appointment for use in the demo");
        Contact [] people = { new ContactImpl(), new ContactImpl() };
        Appointment appointment = new Appointment("Java Twister Semi-Finals",
            people, new LocationImpl(""), createDate(2001, 10, 31, 14, 30),
            createDate(2001, 10, 31, 14, 31));

        System.out.println("Creating the ChangeLocationCommand");
        ChangeLocationCommand cmd = new ChangeLocationCommand();
        cmd.setAppointment(appointment);

        System.out.println("Creating the GUI");
        CommandGui application = new CommandGui(cmd);
        application.setAppointment(appointment);
        cmd.setLocationEditor(application);
        application.createGui();

    }
    public static Date createDate(int year, int month, int day, int hour, int minute){
        dateCreator.set(year, month, day, hour, minute);
        return dateCreator.getTime();
    }
}
class CommandGui implements ActionListener, LocationEditor{
    private JFrame mainFrame;
    private JTextArea display;
    private JTextField updatedLocation;
    private JButton update, undo, redo, exit;
    private JPanel controlPanel, displayPanel, editorPanel;
    private UndoableCommand command;
    private Appointment appointment;

    public CommandGui(UndoableCommand newCommand){
        this.command = newCommand;
    }

    public void setAppointment(Appointment newAppointment){
        this.appointment = newAppointment;
    }

    public void createGui(){
        this.mainFrame = new JFrame("Command Pattern Example");
        Container content = this.mainFrame.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        this.editorPanel = new JPanel();
        this.editorPanel.add(new JLabel("Location"));
        this.updatedLocation = new JTextField(20);
        this.editorPanel.add(this.updatedLocation);
        content.add(this.editorPanel);

        this.displayPanel = new JPanel();
        this.display = new JTextArea(10, 40);
        this.display.setEditable(false);
        this.displayPanel.add(this.display);
        content.add(this.displayPanel);

        this.controlPanel = new JPanel();
        this.update = new JButton("Update Location");
        this.undo = new JButton("Undo Location");
        this.redo = new JButton("Redo Location");
        this.exit = new JButton("Exit");
        this.controlPanel.add(this.update);
        this.controlPanel.add(this.undo);
        this.controlPanel.add(this.redo);
        this.controlPanel.add(this.exit);
        content.add(this.controlPanel);

        this.update.addActionListener(this);
        this.undo.addActionListener(this);
        this.redo.addActionListener(this);
        this.exit.addActionListener(this);

        refreshDisplay();
        this.mainFrame.addWindowListener(new WindowCloseManager());
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt){
        Object originator = evt.getSource();
        if (originator == this.update){
            executeCommand();
        }
        if (originator == this.undo){
            undoCommand();
        }
        if (originator == this.redo){
            redoCommand();
        }
        else if (originator == this.exit){
            exitApplication();
        }
    }

    private class WindowCloseManager extends WindowAdapter{
        public void windowClosing(WindowEvent evt){
            exitApplication();
        }
    }

    public Location getNewLocation(){
        return new LocationImpl(this.updatedLocation.getText());
    }

    private void executeCommand(){
        this.command.execute();
        refreshDisplay();
    }

    private void undoCommand(){
        this.command.undo();
        refreshDisplay();
    }

    private void redoCommand(){
        this.command.redo();
        refreshDisplay();
    }

    private void refreshDisplay(){
        this.display.setText(this.appointment.toString());
    }

    private void exitApplication(){
        System.exit(0);
    }
}
interface Command{
    public void execute();
}
interface Contact extends Serializable{
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

class ContactImpl implements Contact{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
    private String lastName;
    private String title;
    private String organization;
    public static final String EOL_STRING =
        System.getProperty("line.separator");

    public ContactImpl(){ }
    public ContactImpl(String newFirstName, String newLastName,
        String newTitle, String newOrganization){
            this.firstName = newFirstName;
            this.lastName = newLastName;
            this.title = newTitle;
            this.organization = newOrganization;
    }

    public String getFirstName(){ return this.firstName; }
    public String getLastName(){ return this.lastName; }
    public String getTitle(){ return this.title; }
    public String getOrganization(){ return this.organization; }

    public void setFirstName(String newFirstName){ this.firstName = newFirstName; }
    public void setLastName(String newLastName){ this.lastName = newLastName; }
    public void setTitle(String newTitle){ this.title = newTitle; }
    public void setOrganization(String newOrganization){ this.organization = newOrganization; }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }
}

class Appointment{
    private String reason;
    private Contact[] contacts;
    private Location location;
    private Date startDate;
    private Date endDate;

    public Appointment(String reason, Contact[] contacts, Location location, Date startDate, Date endDate){
        this.reason = reason;
        this.contacts = contacts;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getReason(){ return this.reason; }
    public Contact[] getContacts(){ return this.contacts; }
    public Location getLocation(){ return this.location; }
    public Date getStartDate(){ return this.startDate; }
    public Date getEndDate(){ return this.endDate; }

    public void setLocation(Location location){ this.location = location; }

    public String toString(){
        return "Appointment:" + "\n    Reason: " + this.reason +
    "\n    Location: " + this.location + "\n    Start: " +
            this.startDate + "\n    End: " + this.endDate + "\n";
    }
}

interface Location extends Serializable{
    public String getLocation();
    public void setLocation(String newLocation);
}

class ChangeLocationCommand implements UndoableCommand{
    private Appointment appointment;
    private Location oldLocation;
    private Location newLocation;
    private LocationEditor editor;

    public Appointment getAppointment(){ return this.appointment; }

    public void setAppointment(Appointment appointment){ this.appointment = appointment; }
    public void setLocationEditor(LocationEditor locationEditor){ this.editor = locationEditor; }

    public void execute(){
        this.oldLocation = this.appointment.getLocation();
        this.newLocation = this.editor.getNewLocation();
        this.appointment.setLocation(this.newLocation);
    }
    public void undo(){
        this.appointment.setLocation(this.oldLocation);
    }
    public void redo(){
        this.appointment.setLocation(this.newLocation);
    }
}

interface LocationEditor{
    public Location getNewLocation();
}

class LocationImpl implements Location{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String location;

    public LocationImpl(){ }
    public LocationImpl(String newLocation){
        this.location = newLocation;
    }

    public String getLocation(){ return this.location; }

    public void setLocation(String newLocation){ this.location = newLocation; }

    public String toString(){ return this.location; }
}

interface UndoableCommand extends Command{
    public void undo();
    public void redo();
}

