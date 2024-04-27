package modec.metamodel.example.pattern.memento;

//[C] 2002 Sun Microsystems, Inc.---
import java.io.Serializable;
import java.util.ArrayList;

public class RunMementoPattern {
  public static void main(String[] arguments) {
    System.out.println("Example for the Memento pattern");
    System.out.println();
    System.out
        .println("This example will use the AddressBook to demonstrate");
    System.out
        .println(" how a Memento can be used to save and restore state.");
    System.out
        .println("The AddressBook has an inner class, AddressBookMemento,");
    System.out
        .println(" that is used to store the AddressBook state... in this");
    System.out.println(" case, its internal list of contacts.");
    System.out.println();

    System.out.println("Creating the AddressBook");
    AddressBook book = new AddressBook();

    System.out.println("Adding Contact entries for the AddressBook");
    book.addContact(new ContactImpl("Peter", "Taggart", "Commander",
        "NSEA Protector", new AddressImpl()));
    book.addContact(new ContactImpl("Tawny", "Madison", "Lieutenant",
        "NSEA Protector", new AddressImpl()));
    book.addContact(new ContactImpl("Dr.", "Lazarus", "Dr.",
        "NSEA Protector", new AddressImpl()));
    book.addContact(new ContactImpl("Tech Sargent", "Chen", "Tech Sargent",
        "NSEA Protector", new AddressImpl()));

    System.out.println("Contacts added. Current Contact list:");
    System.out.println(book);
    System.out.println();

    System.out.println("Creating a Memento for the address book");
    Object memento = book.getMemento();
    System.out
        .println("Now that a Memento exists, it can be used to restore");
    System.out
        .println(" the state of this AddressBook object, or to set the");
    System.out.println(" state of a new AddressBook.");
    System.out.println();

    System.out.println("Creating new entries for the AddressBook");
    book.removeAllContacts();
    book.addContact(new ContactImpl("Jason", "Nesmith", "",
        "Actor's Guild", new AddressImpl()));
    book.addContact(new ContactImpl("Gwen", "DeMarco", "", "Actor's Guild",
        new AddressImpl()));
    book.addContact(new ContactImpl("Alexander", "Dane", "",
        "Actor's Guild", new AddressImpl()));
    book.addContact(new ContactImpl("Fred", "Kwan", "", "Actor's Guild",
        new AddressImpl()));

    System.out.println("New Contacts added. Current Contact list:");
    System.out.println(book);
    System.out.println();
    System.out
        .println("Using the Memento object to restore the AddressBook");
    System.out.println(" to its original state.");
    book.setMemento(memento);
    System.out.println("AddressBook restored. Current Contact list:");
    System.out.println(book);

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

interface Address extends Serializable {
  public static final String EOL_STRING = System
      .getProperty("line.separator");

  public static final String SPACE = " ";

  public static final String COMMA = ",";

  public String getType();

  public String getDescription();

  public String getStreet();

  public String getCity();

  public String getState();

  public String getZipCode();

  public void setType(String newType);

  public void setDescription(String newDescription);

  public void setStreet(String newStreet);

  public void setCity(String newCity);

  public void setState(String newState);

  public void setZipCode(String newZip);
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

  private Address address;

  public ContactImpl() {
  }

  public ContactImpl(String newFirstName, String newLastName,
      String newTitle, String newOrganization, Address newAddress) {
    this.firstName = newFirstName;
    this.lastName = newLastName;
    this.title = newTitle;
    this.organization = newOrganization;
    this.address = newAddress;
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

  public Address getAddress() {
    return this.address;
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

  public void setAddress(Address newAddress) {
    this.address = newAddress;
  }

  public String toString() {
    return this.firstName + " " + this.lastName;
  }
}

class AddressImpl implements Address {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private String type;

  private String description;

  private String street;

  private String city;

  private String state;

  private String zipCode;

  public AddressImpl() {
  }

  public AddressImpl(String newDescription, String newStreet, String newCity,
      String newState, String newZipCode) {
    this.description = newDescription;
    this.street = newStreet;
    this.city = newCity;
    this.state = newState;
    this.zipCode = newZipCode;
  }

  public String getType() {
    return this.type;
  }

  public String getDescription() {
    return this.description;
  }

  public String getStreet() {
    return this.street;
  }

  public String getCity() {
    return this.city;
  }

  public String getState() {
    return this.state;
  }

  public String getZipCode() {
    return this.zipCode;
  }

  public void setType(String newType) {
    this.type = newType;
  }

  public void setDescription(String newDescription) {
    this.description = newDescription;
  }

  public void setStreet(String newStreet) {
    this.street = newStreet;
  }

  public void setCity(String newCity) {
    this.city = newCity;
  }

  public void setState(String newState) {
    this.state = newState;
  }

  public void setZipCode(String newZip) {
    this.zipCode = newZip;
  }

  public String toString() {
    return this.street + EOL_STRING + this.city + COMMA + SPACE + this.state + SPACE
        + this.zipCode + EOL_STRING;
  }
}

class AddressBook {
  private ArrayList contacts = new ArrayList();

  public Object getMemento() {
    return new AddressBookMemento(this.contacts);
  }

  public void setMemento(Object object) {
    if (object instanceof AddressBookMemento) {
      AddressBookMemento memento = (AddressBookMemento) object;
      this.contacts = memento.state;
    }
  }

  private class AddressBookMemento {
    private ArrayList state;

    private AddressBookMemento(ArrayList contacts) {
      this.state = contacts;
    }
  }

  public AddressBook() {
  }

  public AddressBook(ArrayList newContacts) {
    this.contacts = newContacts;
  }

  public void addContact(Contact contact) {
    if (!this.contacts.contains(contact)) {
      this.contacts.add(contact);
    }
  }

  public void removeContact(Contact contact) {
    this.contacts.remove(contact);
  }

  public void removeAllContacts() {
    this.contacts = new ArrayList();
  }

  public ArrayList getContacts() {
    return this.contacts;
  }

  public String toString() {
    return this.contacts.toString();
  }
}

