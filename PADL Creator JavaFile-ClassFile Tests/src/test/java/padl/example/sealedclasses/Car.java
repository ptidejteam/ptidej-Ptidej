package padl.example.sealedclasses;

public sealed class Car extends Vehicle permits ElectricCar, HumanPoweredCar {

}
