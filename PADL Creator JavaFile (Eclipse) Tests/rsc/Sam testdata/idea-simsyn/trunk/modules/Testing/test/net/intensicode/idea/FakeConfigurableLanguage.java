package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.syntax.RecognizedToken;

import java.util.HashMap;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class FakeConfigurableLanguage implements LanguageConfiguration
{
    public IElementType getToken( String aTokenId )
    {
        if ( myElementTypes.containsKey( aTokenId ) == false )
        {
            myElementTypes.put( aTokenId, new FakeElementType( aTokenId, Language.ANY ) );
        }
        return myElementTypes.get( aTokenId );
    }

    public List<RecognizedToken> getRecognizedTokens()
    {
        return null;
    }

    public SyntaxHighlighter getSyntaxHighlighter()
    {
        return null;
    }

    public TextAttributesKey getTextAttributesKey( String aTokenId )
    {
        return null;
    }

    public TextAttributesKey[] getTokenHighlights( IElementType tokenType )
    {
        return new TextAttributesKey[0];
    }

    private final HashMap<String, IElementType> myElementTypes = new HashMap<String, IElementType>();
}
