package net.intensicode.idea.core;

import net.intensicode.idea.util.ReaderUtils;
import net.intensicode.idea.system.OptionsFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * TODO: Describe this!
 */
public class FakeOptionsFolder implements OptionsFolder
{
    public FakeOptionsFolder( final Object aReferenceObject )
    {
        myReferenceObject = aReferenceObject;
    }

    public String[] findConfigurations()
    {
        throw new RuntimeException( "NYI" );
    }

    public Icon loadIcon( String aRelativeFileName )
    {
        return new ImageIcon( myReferenceObject.getClass().getResource( aRelativeFileName ) );
    }

    public boolean fileExists( String aRelativeFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public String readFileIntoString( String aRelativeFileName ) throws IOException
    {
        final InputStream stream = myReferenceObject.getClass().getResourceAsStream( aRelativeFileName );
        return ReaderUtils.readIntoString( new InputStreamReader( stream ) );
    }

    public void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException
    {
        throw new RuntimeException( "NYI" );
    }

    private final Object myReferenceObject;
}
