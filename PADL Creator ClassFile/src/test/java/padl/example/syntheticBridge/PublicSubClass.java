package padl.example.syntheticBridge;

// Luca 2025/11/12 : When a public class inherits from a non-public class,
// each public method of the superclass appears in the classfile of the
// subclass, as 'bridge' and 'synthetic' methods.
public class PublicSubClass extends NonPublicSuperClass {

}
