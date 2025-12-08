package util.lang.test;

//This class will be to fully test the current capabilities of the convertor Henrique 4/22/2025
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.List;

public final class FullClassTest<T extends Number> extends SuperClass implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;
    private final int constantField = 42;
    public List<? super T> genericField;

    public record RecordExample(int x, String y) {}
    public sealed interface Sealed permits SealedImpl {}
    public non-sealed static class SealedImpl implements Sealed {}

    @Deprecated
    @Retention(RetentionPolicy.CLASS)
    @interface Marker {}

    @Marker
    public <U extends Comparable<U>> void method(T param, U another) throws Exception {
        var localVar = "test";
        System.out.println(param + " " + another);
    }
    public class LambdaTest {
        Runnable r = () -> System.out.println("test");
    }
    @Override
    public void run() {
        System.out.println("Running");
    }

    public void stackMapExample(int x) {
        if (x > 0) {
            System.out.println("Positive");
        } else {
            System.out.println("Non-positive");
        }
    }

    public void methodWithParams(String firstParam, int count) {}

    private class SyntheticHolder {
        private void callLambda() {
            Runnable r = () -> System.out.println("Synthetic");
        }
    }

    
    public <E extends Throwable> void genericThrows() throws E {}

    
    enum AdvancedEnum {
        A {
            @Override
            public String toString() {
                return "A!";
            }
        },
        B;
    }

    
    class BridgeExample extends GenericBase<String> {
        @Override
        public String getValue() { return "ok"; }
    }

    class GenericBase<T> {
        public T getValue() { return null; }
    }

    @Marker
    public void anotherMethod() throws IllegalArgumentException, IllegalStateException {
        throw new IllegalStateException("Just testing");
    }


    @Marker
    public void annotatedOnly() {
        // No-op
    }


    public void throwingOnly() throws Exception {
        throw new Exception("Also test this");
    }
}


class SuperClass {}
