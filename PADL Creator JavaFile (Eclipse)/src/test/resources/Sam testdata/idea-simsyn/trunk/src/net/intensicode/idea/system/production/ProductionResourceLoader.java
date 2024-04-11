package net.intensicode.idea.system.production;

import net.intensicode.idea.system.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;



/**
 * TODO: Describe this!
 */
final class ProductionResourceLoader implements ResourceLoader
{
    ProductionResourceLoader()
    {
    }

    // From ResourceLoader

    public boolean isAvailable( String aResourcePath )
    {
        final InputStream stream = stream( aResourcePath );
        try
        {
            stream.close();
            return true;
        }
        catch ( final Throwable t )
        {
            return false;
        }
    }

    public final Reader read( final String aResourcePath ) throws IOException
    {
        final InputStream stream = stream( aResourcePath );
        if ( stream == null ) throw new IOException( "Resource not found: " + aResourcePath );
        return new InputStreamReader( stream );
    }

    public final InputStream stream( final String aResourcePath )
    {
        final String resourcePath = makeResourcePath( aResourcePath );
        return getClass().getResourceAsStream( resourcePath );
    }

    // Implementation

    private final String makeResourcePath( final String aResourcePath )
    {
        return "/" + aResourcePath;
    }
}
