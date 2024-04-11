package net.intensicode.idea.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
public final class ReaderUtils
{
    public static final List<String> readLines( final Reader aReader ) throws IOException
    {
        return readLines( new BufferedReader( aReader ) );
    }

    public static List<String> readLines( final BufferedReader aReader ) throws IOException
    {
        final ArrayList<String> lines = new ArrayList<String>();
        while ( true )
        {
            final String line = aReader.readLine();
            if ( line == null ) break;
            lines.add( line );
        }
        return lines;
    }

    public static String readIntoString( final Reader aReader ) throws IOException
    {
        final StringBuilder result = new StringBuilder();
        for ( final String line : readLines( aReader ) )
        {
            result.append( line );
            result.append( '\n' );
        }
        return result.toString();
    }
}
