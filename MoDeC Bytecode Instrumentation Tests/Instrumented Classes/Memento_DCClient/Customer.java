//package src.MEMENTO;
public class Customer {
  private String ID;
  private String firstName;
  private String lastName;
  private String cardNum;

  public Customer(String id, String fn, String ln,
                  String CCNum) {
    ID = id;
    firstName = fn;
    lastName = ln;
    cardNum = CCNum;
  }
  public String getSQL() {
    String str =
      "Insert into Customer(ID, fname, lname,ccnum)" +
      "values(" + ID + ",'" + firstName + "','" +
      lastName + "','" + cardNum + "');";
    return str;
  }
  public boolean isValid() {
    String validChars = "0123456789";
    boolean result = true;

    if (lastName.trim().length() == 0) {
      result = false;
    }
    for (int i = 0; i < cardNum.length(); i++) {
      if (validChars.indexOf(cardNum.substring(i, i + 1)) <
          0) {
        result = false;
        break;
      }
    }

    return result;
  }
}
