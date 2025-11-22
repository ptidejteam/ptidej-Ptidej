package soen6461;

public class TestClass implements ITestClass {

    private final String attribute;

    private final ITestClass2 testClass2;

    public TestClass(String attribute,
                     TestClass2 testClass2) {
        this.attribute = attribute;
        this.testClass2 = testClass2;
    }

    public String getAttribute() {
        return attribute;
    }

    public ITestClass2 getTestClass2() {
        return testClass2;
    }
}
