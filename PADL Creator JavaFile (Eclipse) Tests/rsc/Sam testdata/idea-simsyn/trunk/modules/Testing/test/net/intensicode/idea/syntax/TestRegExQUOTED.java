package net.intensicode.idea.syntax;

import junit.framework.TestCase;



/**
 * TODO: Describe this!
 */
public final class TestRegExQUOTED extends TestCase
{
//    regex SPECIAL_QUOTED_STRINGS        => %[qQ]\{(?:[^\}]|\\\})*\}
//    regex SPECIAL_QUOTED_STRINGS        => %[qQ]\[(?:[^\]]|\\\])*\]
//    regex SPECIAL_QUOTED_STRINGS        => %[qQ]\((?:[^\)]|\\\))*\)
//    regex SPECIAL_QUOTED_STRINGS        => %[qQ](.).*\1

    public final void testQ1()
    {
        final RecognizedTokenRegEx token = new RecognizedTokenRegEx( "Q", "%[qQ]\\{(?:(?:\\\\\\})|(?:[^\\}]))*\\}" );

        final String test1 = "this %q{suxx}!";
        assertFalse( token.isFoundAt( test1, 0 ) );
        assertTrue( token.isFoundAt( test1, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );

        final String test2 = "this %q{su\\}xx}!";
        assertFalse( token.isFoundAt( test2, 0 ) );
        assertTrue( token.isFoundAt( test2, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 15, token.getTokenEnd() );

        final String test3 = "this %q{suxx}! {}";
        assertFalse( token.isFoundAt( test3, 0 ) );
        assertTrue( token.isFoundAt( test3, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );
    }

    public final void testQ2()
    {
        final RecognizedTokenRegEx token = new RecognizedTokenRegEx( "Q", "%[qQ]\\[(?:(?:\\\\\\])|(?:[^\\]]))*\\]" );

        final String test1 = "this %q[suxx]!";
        assertFalse( token.isFoundAt( test1, 0 ) );
        assertTrue( token.isFoundAt( test1, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );

        final String test2 = "this %q[su\\]xx]!";
        assertFalse( token.isFoundAt( test2, 0 ) );
        assertTrue( token.isFoundAt( test2, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 15, token.getTokenEnd() );

        final String test3 = "this %q[suxx]! []";
        assertFalse( token.isFoundAt( test3, 0 ) );
        assertTrue( token.isFoundAt( test3, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );
    }

    public final void testQ3()
    {
//        final RecognizedTokenRegEx token = new RecognizedTokenRegEx( "Q", "%[qQ](.)(?:(?:\\\\\1)|(?:[^\1]))*\1" );
        final RecognizedTokenRegEx token = new RecognizedTokenRegEx( "Q", "%[qQ](.).*?\\1" );

        final String test1 = "this %q/suxx/!";
        assertFalse( token.isFoundAt( test1, 0 ) );
        assertTrue( token.isFoundAt( test1, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );

//        final String test2 = "this %q/su\\/xx/!";
//        assertFalse( token.isFoundAt( test2, 0 ) );
//        assertTrue( token.isFoundAt( test2, 5 ) );
//        assertEquals( 5, token.getTokenStart() );
//        assertEquals( 15, token.getTokenEnd() );

        final String test3 = "this %q/suxx/! //";
        assertFalse( token.isFoundAt( test3, 0 ) );
        assertTrue( token.isFoundAt( test3, 5 ) );
        assertEquals( 5, token.getTokenStart() );
        assertEquals( 13, token.getTokenEnd() );
    }
}
