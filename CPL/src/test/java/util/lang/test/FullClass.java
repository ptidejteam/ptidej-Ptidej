package util.lang.test;

//This class will be to fully test the current capabilities of the convertor Henrique 4/22/2025
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.List;

public final class FullClass<T extends Number> extends SuperClass implements Runnable, Serializable {

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

    @Override
    public void run() {
        System.out.println("Running");
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
