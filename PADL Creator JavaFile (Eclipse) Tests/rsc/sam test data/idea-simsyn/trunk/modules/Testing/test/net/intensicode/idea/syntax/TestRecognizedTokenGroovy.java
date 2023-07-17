package net.intensicode.idea.syntax;

import junit.framework.TestCase;
import net.intensicode.idea.Utilities;

import java.io.IOException;



public final class TestRecognizedTokenGroovy extends TestCase
{
    public final void testFindAt() throws IOException
    {
        final String fileName = "TestCode_FindAt.groovy";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenGroovy token = RecognizedTokenGroovy.newInstance( "TEST_FIND_AT", script );
        assertTrue( token.isFindAtDefined() );
        assertFalse( token.isFindInDefined() );

        assertFalse( token.isFoundAt( "test def me", 0 ) );
        assertTrue( token.isFoundAt( "test def me", 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 8, token.getTokenEnd() );

        assertFalse( token.isFoundAt( "test def me", 6 ) );

        assertTrue( token.isFoundIn( "test def me", 0, 10 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 8, token.getTokenEnd() );
    }

    public final void testFindIn() throws IOException
    {
        final String fileName = "TestCode_FindIn.groovy";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenGroovy token = RecognizedTokenGroovy.newInstance( "TEST_FIND_IN", script );
        assertFalse( token.isFindAtDefined() );
        assertTrue( token.isFindInDefined() );

        assertFalse( token.isFoundAt( "test def me", 0 ) );
        assertTrue( token.isFoundAt( "test def me", 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 8, token.getTokenEnd() );

        assertFalse( token.isFoundAt( "test def me", 6 ) );

        assertTrue( token.isFoundIn( "test def me", 0, 10 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 8, token.getTokenEnd() );
    }
}
