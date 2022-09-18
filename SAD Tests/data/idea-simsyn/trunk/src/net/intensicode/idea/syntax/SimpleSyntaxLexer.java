package net.intensicode.idea.syntax;

import com.intellij.lang.Language;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.LexerPosition;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayCharSequence;
import net.intensicode.idea.config.LanguageConfiguration;



/**
 * TODO: Describe this!
 */
final class SimpleSyntaxLexer implements Lexer
{
    SimpleSyntaxLexer( final LanguageConfiguration aLanguageConfiguration, final TokenFinder aTokenFinder )
    {
        myLanguageConfiguration = aLanguageConfiguration;
        myTokenFinder = aTokenFinder;
    }

    // From Lexer

    public final void advance()
    {
        updateTokenType( myTokenEnd );
    }

    public final char[] getBuffer()
    {
        return myBuffer;
    }

    public final int getBufferEnd()
    {
        return myEndOffset;
    }

    public final LexerPosition getCurrentPosition()
    {
        throw new RuntimeException( "NYI" );
    }

    public final int getState()
    {
        return 0;
    }

    public final int getTokenEnd()
    {
        return Math.min( myEndOffset, myTokenEnd );
    }

    public final int getTokenStart()
    {
        return Math.max( myStartOffset, myTokenStart );
    }

    public final IElementType getTokenType()
    {
        return myTokenType;
    }

    public final void restore( final LexerPosition position )
    {
        throw new RuntimeException( "NYI" );
    }

    public final void start( char[] buffer )
    {
        start( buffer, 0, buffer.length );
    }

    public final void start( char[] buffer, int startOffset, int endOffset )
    {
        start( buffer, startOffset, endOffset, 0 );
    }

    public final void start( char[] buffer, int startOffset, int endOffset, int initialState )
    {
        if ( buffer == null ) buffer = new char[0];
        if ( startOffset < 0 ) startOffset = 0;
        if ( startOffset > buffer.length ) startOffset = buffer.length;
        if ( endOffset < 0 ) endOffset = 0;
        if ( endOffset > buffer.length ) endOffset = buffer.length;
        if ( startOffset > endOffset ) endOffset = startOffset;

        myCharSequence = new CharArrayCharSequence( buffer, 0, buffer.length );

        myBuffer = buffer;
        myStartOffset = startOffset;
        myEndOffset = endOffset;

        myTokenType = null;
        myTokenStart = myTokenEnd = startOffset;

        advance();
    }

    // Test Interface

    final CharSequence getTokenText()
    {
        return myCharSequence.subSequence( myTokenStart, myTokenEnd );
    }

    // Implementation

    private final void updateTokenType( final int aStartOffset )
    {
        myTokenStart = aStartOffset;
        myTokenEnd = myEndOffset;
        myTokenType = myTokenStart < myTokenEnd ? DEFAULT_TOKEN : null;

        final RecognizedToken recognizedToken = myTokenFinder.findClosest( myCharSequence, aStartOffset, myEndOffset );
        if ( recognizedToken == null ) return;

        myTokenType = myLanguageConfiguration.getToken( recognizedToken.getTokenID() );
        myTokenStart = recognizedToken.getTokenStart();
        myTokenEnd = recognizedToken.getTokenEnd();
    }



    private char[] myBuffer;

    private int myStartOffset;

    private int myEndOffset;

    private int myTokenStart;

    private int myTokenEnd;

    private IElementType myTokenType;

    private final TokenFinder myTokenFinder;

    private CharArrayCharSequence myCharSequence;

    private final LanguageConfiguration myLanguageConfiguration;

    private static final IElementType DEFAULT_TOKEN = new IElementType( "DEFAULT", Language.ANY );
}
