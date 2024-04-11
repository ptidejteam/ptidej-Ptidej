[3:24:40 PM] venera-poly: public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //
                // Create a new instance of our application and display it.
                //
                new UppercaseTextFieldDemo().setVisible(true);
            }
        });
    }
[3:30:15 PM] venera-poly: ---
[3:30:17 PM] venera-poly: --
[3:30:19 PM] venera-poly: public class Weird {
  // A static member interface used below
  public static interface IntHolder { public int getValue(); }

  public static void main(String[] args) {     
    IntHolder[] holders = new IntHolder[10];   // An array to hold 10 objects
    for(int i = 0; i < 10; i++) {              // Loop to fill the array up
      final int fi = i;                        // A final local variable
      class MyIntHolder implements IntHolder { // A local class
 public int getValue() { return fi; }  // It uses the final variable
      }
      holders[i] = new MyIntHolder();          // Instantiate the local class
    }

    // The local class is now out of scope, so we can't use it. But
    // we've got ten valid instances of that class in our array. The local
    // variable fi is not in our scope here, but it is still in scope for
    // the getValue() method of each of those ten objects. So call getValue()
    // for each object and print it out. This prints the digits 0 to 9. 
    for(int i = 0; i < 10; i++) System.out.println(holders[i].getValue());
  }
}