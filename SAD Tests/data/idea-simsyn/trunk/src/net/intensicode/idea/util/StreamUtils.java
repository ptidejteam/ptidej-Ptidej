package net.intensicode.idea.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * TODO: Describe this!
 */
public final class StreamUtils
{
    public static final byte[] loadFully( final InputStream aStream ) throws IOException
    {
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        copy( aStream, bytes );
        return bytes.toByteArray();
    }

    public static final void copy( final InputStream aInput, final OutputStream aOutput ) throws IOException
    {
        final byte[] buffer = new byte[ 4096 ];
        while ( true )
        {
            final int newBytes = aInput.read( buffer );
            if ( newBytes == -1 ) break;
            aOutput.write( buffer, 0, newBytes );
        }
    }
}
