package modec.metamodel.example.pattern.builder;

//[C] 2002 Sun Microsystems, Inc.---
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RunBuilderPattern {
    private static Calendar dateCreator = Calendar.getInstance();
    
    public static void main(String [] arguments){
        Appointment appt = null;
        
        System.out.println("Example for the Builder pattern");
        System.out.println();
        System.out.println("This example demonstrates the use of the Builder");
        System.out.println("pattern to create Appointment objects for the PIM.");
        System.out.println();
        
        System.out.println("Creating a Scheduler for the example.");
        Scheduler pimScheduler = new Scheduler();
        
        System.out.println("Creating an AppointmentBuilder for the example.");
        System.out.println();
        AppointmentBuilder apptBuilder = new AppointmentBuilder();
        try{
            System.out.println("Creating a new Appointment with an AppointmentBuilder");
            appt = pimScheduler.createAppointment(
                apptBuilder, createDate(2066, 9, 22, 12, 30),
                null, "Trek convention", new LocationImpl("Fargo, ND"),
                createAttendees(4));
            System.out.println("Successfully created an Appointment.");
            System.out.println("Appointment information:");
            System.out.println(appt);
            System.out.println();
        }
        catch (InformationRequiredException exc){
            printExceptions(exc);
        }
        
        System.out.println("Creating a MeetingBuilder for the example.");
        MeetingBuilder mtgBuilder = new MeetingBuilder();
        try{
            System.out.println("Creating a new Appointment with a MeetingBuilder");
            System.out.println("(notice that the same create arguments will produce");
            System.out.println(" an exception, since the MeetingBuilder enforces a");
            System.out.println(" mandatory end date)");
            appt = pimScheduler.createAppointment(
                mtgBuilder, createDate(2066, 9, 22, 12, 30),
                null, "Trek convention", new LocationImpl("Fargo, ND"),
                createAttendees(4));
            System.out.println("Successfully created an Appointment.");
            System.out.println("Appointment information:");
            System.out.println(appt);
            System.out.println();
        }
        catch (InformationRequiredException exc){
            printExceptions(exc);
        }
        
        System.out.println("Creating a new Appointment with a MeetingBuilder");
        System.out.println("(This time, the MeetingBuilder will provide an end date)");
        try{
            appt = pimScheduler.createAppointment(
                mtgBuilder,
                createDate(2002, 4, 1, 10, 00),
                createDate(2002, 4, 1, 11, 30),
                "OOO Meeting",
                new LocationImpl("Butte, MT"),
                createAttendees(2));
            System.out.println("Successfully created an Appointment.");
            System.out.println("Appointment information:");
            System.out.println(appt);
            System.out.println();
        }
        catch (InformationRequiredException exc){
            printExceptions(exc);
        }
    }
    
    public static Date createDate(int year, int month, int day, int hour, int minute){
        dateCreator.set(year, month, day, hour, minute);
        return dateCreator.getTime();
    }
    
    public static ArrayList createAttendees(int numberToCreate){
        ArrayList group = new ArrayList();
        for (int i = 0; i < numberToCreate; i++){
            group.add(new ContactImpl("John", getLastName(i), "Employee (non-exempt)", "Yoyodyne Corporation"));
        }
        return group;
    }
    
    public static String getLastName(int index){
        String name = "";
        switch (index % 6){
            case 0: name = "Worfin";
                break;
            case 1: name = "Smallberries";
                break;
            case 2: name = "Bigbootee";
                break;
            case 3: name = "Haugland";
                break;
            case 4: name = "Maassen";
                break;
            case 5: name = "Sterling";
                break;
        }
        return name;
    }
    
    public static void printExceptions(InformationRequiredException exc){
        int statusCode = exc.getInformationRequired();
        
        System.out.println("Unable to create Appointment: additional information is required");
        if ((statusCode & InformationRequiredException.START_DATE_REQUIRED) > 0){
            System.out.println("  A start date is required for this appointment to be complete.");
        }
        if ((statusCode & InformationRequiredException.END_DATE_REQUIRED) > 0){
            System.out.println("  An end date is required for this appointment to be complete.");
        }
        if ((statusCode & InformationRequiredException.DESCRIPTION_REQUIRED) > 0){
            System.out.println("  A description is required for this appointment to be complete.");
        }
        if ((statusCode & InformationRequiredException.ATTENDEE_REQUIRED) > 0){
            System.out.println("  At least one attendee is required for this appointment to be complete.");
        }
        if ((statusCode & InformationRequiredException.LOCATION_REQUIRED) > 0){
            System.out.println("  A location is required for this appointment to be complete.");
        }
        System.out.println();
    }
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
        return this.firstName + SPACE + this.lastName;
    }
}
interface Location extends Serializable{
    public String getLocation();
    public void setLocation(String newLocation);
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

class Scheduler{
    public Appointment createAppointment(AppointmentBuilder builder,
        Date startDate, Date endDate, String description,
        Location location, ArrayList attendees) throws InformationRequiredException{
            if (builder == null){
                builder = new AppointmentBuilder();
            }
        builder.buildAppointment();
        builder.buildDates(startDate, endDate);
        builder.buildDescription(description);
        builder.buildAttendees(attendees);
        builder.buildLocation(location);
        return builder.getAppointment();
    }
}

class Appointment{
    private Date startDate;
    private Date endDate;
    private String description;
    private ArrayList attendees = new ArrayList();
    private Location location;
    public static final String EOL_STRING =
        System.getProperty("line.separator");
    
    public Date getStartDate(){ return this.startDate; }
    public Date getEndDate(){ return this.endDate; }
    public String getDescription(){ return this.description; }
    public ArrayList getAttendees(){ return this.attendees; }
    public Location getLocation(){ return this.location; }

    public void setDescription(String newDescription){ this.description = newDescription; }
    public void setLocation(Location newLocation){ this.location = newLocation; }
    public void setStartDate(Date newStartDate){ this.startDate = newStartDate; }
    public void setEndDate(Date newEndDate){ this.endDate = newEndDate; }
    public void setAttendees(ArrayList newAttendees){
        if (newAttendees != null){
            this.attendees = newAttendees;
        }
    }
    
    public void addAttendee(Contact attendee){
        if (!this.attendees.contains(attendee)){
            this.attendees.add(attendee);
        }
    }
    
    public void removeAttendee(Contact attendee){
        this.attendees.remove(attendee);
    }
    
    public String toString(){
        return "  Description: " + this.description + EOL_STRING +
            "  Start Date: " + this.startDate + EOL_STRING +
            "  End Date: " + this.endDate + EOL_STRING +
            "  Location: " + this.location + EOL_STRING +
            "  Attendees: " + this.attendees;
    }
}

class AppointmentBuilder{
    
    public static final int START_DATE_REQUIRED = 1;
    public static final int END_DATE_REQUIRED = 2;
    public static final int DESCRIPTION_REQUIRED = 4;
    public static final int ATTENDEE_REQUIRED = 8;
    public static final int LOCATION_REQUIRED = 16;
    
    protected Appointment appointment;
    
    protected int requiredElements;
    
    public void buildAppointment(){
        this.appointment = new Appointment();
    }
    
    public void buildDates(Date startDate, Date endDate){
        Date currentDate = new Date();
        if ((startDate != null) && (startDate.after(currentDate))){
            this.appointment.setStartDate(startDate);
        }
        if ((endDate != null) && (endDate.after(startDate))){
            this.appointment.setEndDate(endDate);
        }
    }
    
    public void buildDescription(String newDescription){
        this.appointment.setDescription(newDescription);
    }
    
    public void buildAttendees(ArrayList attendees){
        if ((attendees != null) && (!attendees.isEmpty())){
            this.appointment.setAttendees(attendees);
        }
    }
    
    public void buildLocation(Location newLocation){
        if (newLocation != null){
            this.appointment.setLocation(newLocation);
        }
    }
    
    public Appointment getAppointment() throws InformationRequiredException{
        this.requiredElements = 0;
        
        if (this.appointment.getStartDate() == null){
            this.requiredElements += START_DATE_REQUIRED;
        }
        
        if (this.appointment.getLocation() == null){
            this.requiredElements += LOCATION_REQUIRED;
        }
        
        if (this.appointment.getAttendees().isEmpty()){
            this.requiredElements += ATTENDEE_REQUIRED;
        }
        
        if (this.requiredElements > 0){
            throw new InformationRequiredException(this.requiredElements);
        }
        return this.appointment;
    }
    
    public int getRequiredElements(){ return this.requiredElements; }
}

class InformationRequiredException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String MESSAGE = "Appointment cannot be created because further information is required";
    public static final int START_DATE_REQUIRED = 1;
    public static final int END_DATE_REQUIRED = 2;
    public static final int DESCRIPTION_REQUIRED = 4;
    public static final int ATTENDEE_REQUIRED = 8;
    public static final int LOCATION_REQUIRED = 16;
    private int informationRequired;
    
    public InformationRequiredException(int itemsRequired){
        super(MESSAGE);
        this.informationRequired = itemsRequired;
    }
    
    public int getInformationRequired(){ return this.informationRequired; }
}

class MeetingBuilder extends AppointmentBuilder{
    public Appointment getAppointment() throws InformationRequiredException{
        try{
            super.getAppointment();
        }
        finally{
            if (this.appointment.getEndDate() == null){
                this.requiredElements += END_DATE_REQUIRED;
            }
            
            if (this.requiredElements > 0){
                throw new InformationRequiredException(this.requiredElements);
            }
        }
        return this.appointment;
    }
}

