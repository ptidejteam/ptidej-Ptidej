package net.intensicode.idea.syntax;

import com.intellij.lexer.Lexer;
import net.intensicode.idea.FakeConfigurableLanguage;
import net.intensicode.idea.config.LanguageConfiguration;

import java.io.IOException;



/**
 * TODO: Describe this!
 */
public final class TestSimpleSyntaxLexer extends LexerTestCaseBase
{
    public final void testWithoutTokens() throws IOException
    {
        final char[] rubyCodeBuffer = loadTestCode();

        final SimpleSyntaxLexer lexer = createTestLexer();
        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );

        lexer.start( rubyCodeBuffer );
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 304, lexer.getTokenEnd() );
        assertEquals( "DEFAULT", lexer.getTokenType().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 304, lexer.getTokenStart() );
        assertEquals( 304, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );
    }

    public final void testWhiteSpace() throws IOException
    {
        final char[] rubyCodeBuffer = loadTestCode();

        final TokenFinder tokenFinder = new TokenFinder();
        tokenFinder.register( newRecognizedToken( "WHITE_SPACE", "[ \t\r\n]+" ) );
        final SimpleSyntaxLexer lexer = createTestLexer( tokenFinder );
        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );

        lexer.start( rubyCodeBuffer );
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 2, lexer.getTokenEnd() );
        assertEquals( "WHITE_SPACE", lexer.getTokenType().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 3, lexer.getTokenStart() );
        assertEquals( 4, lexer.getTokenEnd() );
        assertEquals( "WHITE_SPACE", lexer.getTokenType().toString() );
    }

    public final void testLineComments() throws IOException
    {
        final char[] rubyCodeBuffer = loadTestCode();

        final TokenFinder tokenFinder = new TokenFinder();
        tokenFinder.register( newRecognizedToken( "LINE_COMMENT", "#.*" ) );
        final SimpleSyntaxLexer lexer = createTestLexer( tokenFinder );
        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );

        lexer.start( rubyCodeBuffer );
        assertEquals( 0, lexer.getState() );
        assertEquals( 2, lexer.getTokenStart() );
        assertEquals( 16, lexer.getTokenEnd() );
        assertEquals( "LINE_COMMENT", lexer.getTokenType().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 133, lexer.getTokenStart() );
        assertEquals( 143, lexer.getTokenEnd() );
        assertEquals( "LINE_COMMENT", lexer.getTokenType().toString() );
    }

    public final void testVariousTokens() throws IOException
    {
        final char[] rubyCodeBuffer = loadTestCode();

        final TokenFinder tokenFinder = new TokenFinder();
        tokenFinder.register( newRecognizedToken( "LINE_COMMENT", "#.*" ) );
        tokenFinder.register( newRecognizedToken( "DOC_COMMENT", "(?ms)^=begin$.*^=end$" ) );
        tokenFinder.register( newRecognizedToken( "DOUBLE_QUOTED_STRING", "\"(?:[^\"]|\\\")*\"" ) );
        tokenFinder.register( newRecognizedToken( "OPERATOR", "\\+|\\-" ) );
        tokenFinder.register( newRecognizedToken( "CLASS_NAME", "(?:\\p{javaUpperCase}\\p{javaLowerCase}+)+" ) );
        tokenFinder.register( newRecognizedToken( "KEYWORD", "(?:def|end|class|module)" ) );
        tokenFinder.register( newRecognizedToken( "IDENTIFIER", "[\\p{javaLowerCase}_]+" ) );

        final SimpleSyntaxLexer lexer = createTestLexer( tokenFinder );
        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );

        lexer.start( rubyCodeBuffer );
        assertEquals( 0, lexer.getState() );
        assertEquals( 2, lexer.getTokenStart() );
        assertEquals( 16, lexer.getTokenEnd() );
        assertEquals( "LINE_COMMENT", lexer.getTokenType().toString() );
        assertEquals( "# comment line", lexer.getTokenText().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 20, lexer.getTokenStart() );
        assertEquals( 25, lexer.getTokenEnd() );
        assertEquals( "KEYWORD", lexer.getTokenType().toString() );
        assertEquals( "class", lexer.getTokenText().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 26, lexer.getTokenStart() );
        assertEquals( 31, lexer.getTokenEnd() );
        assertEquals( "CLASS_NAME", lexer.getTokenType().toString() );
        assertEquals( "Toast", lexer.getTokenText().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 39, lexer.getTokenStart() );
        assertEquals( 42, lexer.getTokenEnd() );
        assertEquals( "KEYWORD", lexer.getTokenType().toString() );
        assertEquals( "def", lexer.getTokenText().toString() );
    }

    public final void testEmptyFile()
    {
        final char[] rubyCodeBuffer = new char[0];

        final Lexer lexer = createTestLexer();

        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( 0, lexer.getBufferEnd() );
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 0, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 0, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 0, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );
    }

    public final void testEmptyLines()
    {
        final char[] rubyCodeBuffer = "\n\n\n\n\n\n\n\n".toCharArray();

        final Lexer lexer = createTestLexer();

        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( 8, lexer.getBufferEnd() );
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 8, lexer.getTokenEnd() );
        assertEquals( "DEFAULT", lexer.getTokenType().toString() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 8, lexer.getTokenStart() );
        assertEquals( 8, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );

        lexer.advance();
        assertEquals( 0, lexer.getState() );
        assertEquals( 8, lexer.getTokenStart() );
        assertEquals( 8, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );
    }

    // Implementation

    private final SimpleSyntaxLexer createTestLexer()
    {
        return createTestLexer( new TokenFinder() );
    }

    private final SimpleSyntaxLexer createTestLexer( final TokenFinder aTokenFinder )
    {
        final LanguageConfiguration langSpec = new FakeConfigurableLanguage();
        return new SimpleSyntaxLexer( langSpec, aTokenFinder );
    }
}