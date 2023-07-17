package net.intensicode.idea.syntax;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.config.LanguageConfiguration;
import org.jetbrains.annotations.NotNull;



/**
 * TODO: Describe this!
 */
public final class SimpleSyntaxHighlighter implements SyntaxHighlighter
{
    public SimpleSyntaxHighlighter( final SystemContext aSystemContext, final LanguageConfiguration aConfiguration )
    {
        if ( aConfiguration.getRecognizedTokens().size() == 0 )
        {
            throw new IllegalArgumentException( "No recognized tokens" );
        }

        final TokenFinder tokenFinder = new TokenFinder( aSystemContext.getErrorHandler() );
        for ( final RecognizedToken token : aConfiguration.getRecognizedTokens() )
        {
            tokenFinder.register( token );
        }
        myConfiguration = aConfiguration;
        mySyntaxLexer = new SimpleSyntaxLexer( aConfiguration, tokenFinder );
    }

    // From SyntaxHighlighter

    @NotNull
    public final Lexer getHighlightingLexer()
    {
        return mySyntaxLexer;
    }

    @NotNull
    public final TextAttributesKey[] getTokenHighlights( final IElementType tokenType )
    {
        return myConfiguration.getTokenHighlights( tokenType );
    }



    private final SimpleSyntaxLexer mySyntaxLexer;

    private final LanguageConfiguration myConfiguration;
}
