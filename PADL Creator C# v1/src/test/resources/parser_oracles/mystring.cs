// Namespace Declaration
using System;

// helper class
class OutputClass
{
    string myString;

    // Constructor
    public OutputClass(string inputString)
    {
        myString = inputString;
    }

    // Instance Method
    public void printString()
    {
        Console.WriteLine("{0}", myString);
    }

    // Destructor
    ~OutputClass()
    {
        // Some resource cleanup routines
    }
}