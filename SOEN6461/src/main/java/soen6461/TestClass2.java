package soen6461;

public class TestClass2 implements ITestClass2 {

    private final String attribute;

    public TestClass2(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
