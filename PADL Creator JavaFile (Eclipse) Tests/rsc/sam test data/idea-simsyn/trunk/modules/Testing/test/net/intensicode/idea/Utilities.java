package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * TODO: Describe this!
 */
public final class Utilities
{
    public static final String readIntoString( final Object aReference, final String aResourceName ) throws IOException
    {
        final InputStream resource = aReference.getClass().getResourceAsStream( aResourceName );
        final InputStreamReader reader = new InputStreamReader( resource );

        final StringBuilder builder = new StringBuilder();
        final char[] buffer = new char[ 4096 ];
        while ( true )
        {
            final int charsRead = reader.read( buffer );
            if ( charsRead == -1 ) break;
            builder.append( buffer, 0, charsRead );
        }
        reader.close();

        return builder.toString();
    }

    public static final BufferedReader getReader( final Object aReference, final String aResourceName )
    {
        final InputStream resource = aReference.getClass().getResourceAsStream( aResourceName );
        final InputStreamReader reader = new InputStreamReader( resource );
        return new BufferedReader( reader );
    }

    public static final IElementType newToken( final String aID )
    {
        return new FakeElementType( aID, Language.ANY );
    }
}
