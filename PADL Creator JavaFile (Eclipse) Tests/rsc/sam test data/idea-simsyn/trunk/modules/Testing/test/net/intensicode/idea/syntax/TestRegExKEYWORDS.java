package net.intensicode.idea.syntax;

import junit.framework.TestCase;



/**
 * TODO: Describe this!
 */
public final class TestRegExKEYWORDS extends TestCase
{
    public final void testKeywords()
    {
        final RecognizedTokenRegEx token = new RecognizedTokenRegEx( "KEYWORD", "\\b(?:begin|class|def|end|module|raise|rescue)\\b" );
        final String test = "the ending end defined like def to raiserescue me";

        assertFalse( token.isFoundAt( test, 0 ) );
        assertFalse( token.isFoundAt( test, 4 ) );

        assertTrue( token.isFoundAt( test, 11 ) );
        assertEquals( 11, token.getTokenStart() );
        assertEquals( 14, token.getTokenEnd() );

        assertFalse( token.isFoundAt( test, 15 ) );

        assertTrue( token.isFoundAt( test, 28 ) );
        assertEquals( 28, token.getTokenStart() );
        assertEquals( 31, token.getTokenEnd() );

        assertFalse( token.isFoundAt( test, 35 ) );

        // This is a border case: Because when we jump in at
        // this position, it is actually a word boundary!
//        assertFalse( token.isFoundAt( test, 40 ) );
        assertTrue( token.isFoundAt( test, 40 ) );
        assertEquals( 40, token.getTokenStart() );
        assertEquals( 46, token.getTokenEnd() );

        // Here's the 'proof':
        assertFalse( token.isFoundIn( test, 30, test.length() ) );

        // All together, this shouldnt be a problem because if the beginning of
        // word isnt matched as something 'special', then its matched as an
        // identifier. And then it will 'swallow' any contained keyword..
    }
}
