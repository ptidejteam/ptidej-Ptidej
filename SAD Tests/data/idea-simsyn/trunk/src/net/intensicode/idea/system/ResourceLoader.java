package net.intensicode.idea.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;



/**
 * TODO: Describe this!
 */
public interface ResourceLoader
{
    boolean isAvailable( String aResourcePath );

    Reader read( String aResourcePath ) throws IOException;

    InputStream stream( String aResourcePath );
}
