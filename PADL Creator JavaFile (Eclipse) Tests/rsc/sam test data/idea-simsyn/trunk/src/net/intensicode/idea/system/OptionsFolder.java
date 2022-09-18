package net.intensicode.idea.system;

import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public interface OptionsFolder
{
    String[] findConfigurations();

    Icon loadIcon( String aRelativeFileName );

    boolean fileExists( String aRelativeFileName );

    String readFileIntoString( String aRelativeFileName ) throws IOException;

    void writeFileFromStream( String aRelativeFileName, InputStream aStream ) throws IOException;
}
