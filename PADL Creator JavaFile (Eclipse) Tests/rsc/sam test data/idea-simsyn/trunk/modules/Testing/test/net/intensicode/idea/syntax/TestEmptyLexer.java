package net.intensicode.idea.syntax;

import com.intellij.lexer.EmptyLexer;

import java.io.IOException;



/**
 * TODO: Describe this!
 */
public final class TestEmptyLexer extends LexerTestCaseBase
{
    public final void testUsage() throws IOException
    {
        final char[] rubyCodeBuffer = loadTestCode();

        final EmptyLexer lexer = new EmptyLexer();
        lexer.start( rubyCodeBuffer );
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );
        assertEquals( 0, lexer.getState() );
        assertEquals( 0, lexer.getTokenStart() );
        assertEquals( 304, lexer.getTokenEnd() );
        assertEquals( 0, lexer.getCurrentPosition().getOffset() );
        assertEquals( 0, lexer.getCurrentPosition().getState().intern() );

        // Because of the way IDEA handles types internally (fucking globals! die jetbrains, die!),
        // there are no gurantees about who created a type for id 0, which could then be returned
        // here..
//        assertEquals( null, lexer.getTokenType() );

        lexer.advance();
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );
        assertEquals( 0, lexer.getState() );
        assertEquals( 304, lexer.getTokenStart() );
        assertEquals( 304, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );
        assertEquals( 0, lexer.getCurrentPosition().getOffset() );
        assertEquals( 0, lexer.getCurrentPosition().getState().intern() );

        lexer.advance();
        assertEquals( rubyCodeBuffer, lexer.getBuffer() );
        assertEquals( rubyCodeBuffer.length, lexer.getBufferEnd() );
        assertEquals( 0, lexer.getState() );
        assertEquals( 304, lexer.getTokenStart() );
        assertEquals( 304, lexer.getTokenEnd() );
        assertEquals( null, lexer.getTokenType() );
        assertEquals( 0, lexer.getCurrentPosition().getOffset() );
        assertEquals( 0, lexer.getCurrentPosition().getState().intern() );
    }
}
