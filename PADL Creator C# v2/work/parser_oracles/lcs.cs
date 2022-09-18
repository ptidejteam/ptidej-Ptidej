using System;

public class Line : DrawingObject
{
    public override void Draw()
    {
        Console.WriteLine("I'm a Line.");
    }
}

public class Circle : DrawingObject
{
    public override void Draw()
    {
        Console.WriteLine("I'm a Circle.");
    }
}

public class Square : DrawingObject
{
    public override void Draw()
    {
        Console.WriteLine("I'm a Square.");
    }
}

