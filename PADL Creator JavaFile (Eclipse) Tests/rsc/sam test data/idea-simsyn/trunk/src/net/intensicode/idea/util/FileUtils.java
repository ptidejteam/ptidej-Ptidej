package net.intensicode.idea.util;

import java.io.*;



/**
 * TODO: Describe this!
 */
public final class FileUtils
{
    public static final String readIntoString( final String aFileName ) throws IOException
    {
        final BufferedReader reader = readFile( aFileName );
        try
        {
            return ReaderUtils.readIntoString( reader );
        }
        finally
        {
            reader.close();
        }
    }

    public static final byte[] loadFile( final String aFileName ) throws IOException
    {
        final InputStream stream = new FileInputStream( aFileName );
        try
        {
            return StreamUtils.loadFully( stream );
        }
        finally
        {
            stream.close();
        }
    }

    // Implementation

    private static final BufferedReader readFile( final String aFileName ) throws IOException
    {
        final InputStream stream = new FileInputStream( aFileName );
        return new BufferedReader( new InputStreamReader( stream ) );
    }
}
