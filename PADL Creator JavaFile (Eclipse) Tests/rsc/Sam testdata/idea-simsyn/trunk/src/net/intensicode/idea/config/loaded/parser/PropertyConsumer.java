package net.intensicode.idea.config.loaded.parser;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.util.LoggerFactory;

import java.util.HashMap;



/**
 * TODO: Describe this!
 */
public final class PropertyConsumer implements LineConsumer
{
    public PropertyConsumer( final HashMap<String, String> aProperties )
    {
        myProperties = aProperties;
    }

    // From LineConsumer

    public final void consume( final int aLineType, final MatchedLine aMatchedLine )
    {
        if ( aLineType != ConfigurationParser.PROPERTY ) return;

        final String key = aMatchedLine.getValue( 1 );
        final String value = aMatchedLine.getValue( 2 );
        myProperties.put( key, value );

        LOG.info( "Loaded property " + key + " = " + value );
    }



    private final HashMap<String, String> myProperties;

    private static final Logger LOG = LoggerFactory.getLogger();
}
