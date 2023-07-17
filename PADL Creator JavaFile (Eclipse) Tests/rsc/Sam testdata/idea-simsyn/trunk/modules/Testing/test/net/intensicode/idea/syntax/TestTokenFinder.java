package net.intensicode.idea.syntax;

import java.io.IOException;



/**
 * TODO: Describe this!
 */
public final class TestTokenFinder extends LexerTestCaseBase
{
    public final void testWithoutTokens() throws IOException
    {
        final CharSequence charSequence = loadTestCodeSequence();

        final TokenFinder finder = new TokenFinder();
        assertNull( finder.findClosest( charSequence, 0, charSequence.length() ) );
    }

    public final void testWhiteSpace() throws IOException
    {
        final CharSequence charSequence = loadTestCodeSequence();

        final TokenFinder finder = new TokenFinder();
        finder.register( newRecognizedToken( ( "WHITE_SPACE" ), "[ \t\r\n]+" ) );

        final RecognizedToken closest = finder.findClosest( charSequence, 0, charSequence.length() );
        assertNotNull( closest );
        assertEquals( 0, closest.getTokenStart() );
        assertEquals( 2, closest.getTokenEnd() );

        final RecognizedToken second = finder.findClosest( charSequence, 2, charSequence.length() );
        assertNotNull( second );
        assertEquals( 3, second.getTokenStart() );
        assertEquals( 4, second.getTokenEnd() );
    }

    public final void testLineComments() throws IOException
    {
        final CharSequence charSequence = loadTestCodeSequence();

        final TokenFinder finder = new TokenFinder();
        finder.register( newRecognizedToken( ( "LINE_COMMENT" ), "#.*" ) );
        finder.register( newRecognizedToken( ( "WHITE_SPACE" ), "[ \t\r\n]+" ) );

        RecognizedToken token = finder.findClosest( charSequence, 0, charSequence.length() );
        assertNotNull( token );
        assertEquals( 0, token.getTokenStart() );
        assertEquals( 2, token.getTokenEnd() );
        assertEquals( "WHITE_SPACE", token.getTokenID() );

        token = finder.findClosest( charSequence, 2, charSequence.length() );
        assertNotNull( token );
        assertEquals( 2, token.getTokenStart() );
        assertEquals( 16, token.getTokenEnd() );
        assertEquals( "LINE_COMMENT", token.getTokenID() );

        token = finder.findClosest( charSequence, 16, charSequence.length() );
        assertNotNull( token );
        assertEquals( 16, token.getTokenStart() );
        assertEquals( 20, token.getTokenEnd() );
        assertEquals( "WHITE_SPACE", token.getTokenID() );

        token = finder.findClosest( charSequence, 20, charSequence.length() );
        assertNotNull( token );
        assertEquals( 25, token.getTokenStart() );
        assertEquals( 26, token.getTokenEnd() );
        assertEquals( "WHITE_SPACE", token.getTokenID() );
    }

    public final void testWithUnrecognizedChar() throws IOException
    {
        final CharSequence charSequence = loadTestCodeSequence();

        final TokenFinder finder = new TokenFinder();
        finder.register( newRecognizedToken( ( "LINE_COMMENT" ), "#.*" ) );
        finder.register( newRecognizedToken( ( "WHITE_SPACE" ), "[ \t\r\n]+" ) );
        finder.register( newRecognizedToken( ( "UNRECOGNIZED_CHAR" ), "." ) );

        RecognizedToken token = finder.findClosest( charSequence, 0, charSequence.length() );
        assertNotNull( token );
        assertEquals( 0, token.getTokenStart() );
        assertEquals( 2, token.getTokenEnd() );
        assertEquals( "WHITE_SPACE", token.getTokenID() );

        token = finder.findClosest( charSequence, 2, charSequence.length() );
        assertNotNull( token );
        assertEquals( 2, token.getTokenStart() );
        assertEquals( 16, token.getTokenEnd() );
        assertEquals( "LINE_COMMENT", token.getTokenID() );

        token = finder.findClosest( charSequence, 16, charSequence.length() );
        assertNotNull( token );
        assertEquals( 16, token.getTokenStart() );
        assertEquals( 20, token.getTokenEnd() );
        assertEquals( "WHITE_SPACE", token.getTokenID() );

        token = finder.findClosest( charSequence, 20, charSequence.length() );
        assertNotNull( token );
        assertEquals( 20, token.getTokenStart() );
        assertEquals( 21, token.getTokenEnd() );
        assertEquals( "UNRECOGNIZED_CHAR", token.getTokenID() );
    }
}
