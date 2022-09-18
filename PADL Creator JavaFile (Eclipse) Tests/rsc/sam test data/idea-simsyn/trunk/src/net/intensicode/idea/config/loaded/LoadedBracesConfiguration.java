package net.intensicode.idea.config.loaded;

import net.intensicode.idea.config.BracesConfiguration;
import net.intensicode.idea.config.ConfigurationProperties;



/**
 * TODO: Describe this!
 */
final class LoadedBracesConfiguration implements BracesConfiguration
{
    LoadedBracesConfiguration( final ConfigurationProperties aProperties )
    {
        myProperties = aProperties;
    }

    // From BracesConfiguration

    public final String[] getBracePairs()
    {
        final String key = BRACES_PAIRS;
        if ( myProperties.isValidProperty( key ) == false ) return new String[0];
        return myProperties.getProperty( key ).split( "," );
    }

    public final String[] getStructuralPairs()
    {
        final String key = BRACES_STRUCTURAL;
        if ( myProperties.isValidProperty( key ) == false ) return new String[0];
        return myProperties.getProperty( key ).split( "," );
    }



    private final ConfigurationProperties myProperties;

    private static final String BRACES_PAIRS = "Braces.Pairs";

    private static final String BRACES_STRUCTURAL = "Braces.Structural";
}
