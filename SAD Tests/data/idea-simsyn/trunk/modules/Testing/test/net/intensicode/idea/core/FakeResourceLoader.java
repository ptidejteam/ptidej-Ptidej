package net.intensicode.idea.core;

import net.intensicode.idea.system.ResourceLoader;

import java.io.InputStream;
import java.io.Reader;



/**
 * TODO: Describe this!
 */
public class FakeResourceLoader implements ResourceLoader
{
    public FakeResourceLoader( final Object aReferenceObject )
    {
    }

    public boolean isAvailable( String aResourcePath )
    {
        throw new RuntimeException( "NYI" );
    }

    public Reader read( String aResourcePath )
    {
        throw new RuntimeException( "NYI" );
    }

    public InputStream stream( String aResourcePath )
    {
        throw new RuntimeException( "NYI" );
    }
}
