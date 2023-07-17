package net.intensicode.idea.config.loaded;

import net.intensicode.idea.config.ConfigurationProperties;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.system.OptionsFolder;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
final class LoadedFileTypeConfiguration implements FileTypeConfiguration
{
    LoadedFileTypeConfiguration( final ConfigurationProperties aProperties, final OptionsFolder aOptionsFolder )
    {
        myProperties = aProperties;
        myOptionsFolder = aOptionsFolder;
    }

    // From FileTypeConfiguration

    public final Icon getIcon()
    {
        if ( myProperties.isValidProperty( FILE_TYPE_ICON ) == false ) return null;
        return myOptionsFolder.loadIcon( myProperties.getProperty( FILE_TYPE_ICON ) );
    }

    public final String[] getExtensions()
    {
        if ( myProperties.isValidProperty( FILE_TYPE_EXTENSIONS ) )
        {
            final String extensionsList = myProperties.getProperty( FILE_TYPE_EXTENSIONS );
            return extensionsList.split( ",[ \t]*" );
        }
        return new String[0];
    }

    public final String getDefaultExtension()
    {
        if ( myProperties.isValidProperty( FILE_TYPE_DEFAULT_EXTENSION ) )
        {
            return myProperties.getProperty( FILE_TYPE_DEFAULT_EXTENSION );
        }
        if ( getExtensions().length == 0 ) return "";
        return getExtensions()[ 0 ];
    }



    private final OptionsFolder myOptionsFolder;

    private final ConfigurationProperties myProperties;

    private static final String FILE_TYPE_ICON = "FileType.Icon";

    private static final String FILE_TYPE_EXTENSIONS = "FileType.Extensions";

    private static final String FILE_TYPE_DEFAULT_EXTENSION = "FileType.DefaultExtension";
}
