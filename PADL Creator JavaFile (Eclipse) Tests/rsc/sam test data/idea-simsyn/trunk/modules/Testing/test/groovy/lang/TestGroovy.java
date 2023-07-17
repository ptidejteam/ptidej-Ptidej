package groovy.lang;

import junit.framework.TestCase;
import org.codehaus.groovy.control.CompilationFailedException;



public final class TestGroovy extends TestCase
{
    public final void testCreate() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "" );
        assertNull( result );
    }

    public final void testValue() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "return \"toast\"" );
        assertEquals( "toast", result );
    }

    public final void testClosure() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        final Object result = shell.evaluate( "find_in = { return 17 }" );
        final Closure closure = ( Closure ) result;
        assertEquals( 17, closure.call() );
    }

    public final void testClass() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();
        assertNull( shell.evaluate( "class Queso { \n def toast() { \n return 17; \n } } \n return null" ) );
        assertEquals( 17, shell.evaluate( "new Queso().toast()" ) );
    }

    public final void testRecognizer() throws CompilationFailedException
    {
        final GroovyShell shell = new GroovyShell();

        final Object result = shell.evaluate( getClass().getResourceAsStream( "TestCode_RECOGNIZER.groovy" ) );
        assertNotNull( result );
        assertTrue( result instanceof GroovyObject );
        assertTrue( result instanceof GroovyRecognizer );

        final GroovyObject object = ( GroovyObject ) result;
        assertEquals( "queso", object.invokeMethod( "toast", null ) );
        assertEquals( "test x", object.invokeMethod( "test1", "x" ) );
        assertEquals( "test x y", object.invokeMethod( "test2", new Object[]{"x", "y"} ) );

        assertEquals( GroovySegment.NOT_FOUND, object.invokeMethod( "find_at", new Object[]{"toasted", 3} ) );

        final GroovyRecognizer recognizer = ( GroovyRecognizer ) result;
        assertEquals( GroovySegment.NOT_FOUND, recognizer.find_at( "toasted", 3 ) );
        assertEquals( new GroovySegment( 3, 6 ), recognizer.find_at( "to def me", 3 ) );
    }
}
