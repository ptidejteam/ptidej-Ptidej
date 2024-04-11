package net.intensicode.idea.syntax;

import com.intellij.psi.tree.IElementType;
import com.intellij.util.text.CharArrayCharSequence;
import junit.framework.TestCase;
import net.intensicode.idea.Utilities;

import java.io.IOException;



/**
 * TODO: Describe this!
 */
abstract class LexerTestCaseBase extends TestCase
{
    protected final RecognizedToken newRecognizedToken( final String aID, final String aRegEx )
    {
        return new RecognizedTokenRegEx( aID, aRegEx );
    }

    protected final IElementType newToken( final String aID )
    {
        return Utilities.newToken( aID );
    }

    protected final char[] loadTestCode() throws IOException
    {
        return Utilities.readIntoString( this, "TestCode_Ruby.txt" ).toCharArray();
    }

    protected final CharSequence loadTestCodeSequence() throws IOException
    {
        return new CharArrayCharSequence( loadTestCode() );
    }
}
