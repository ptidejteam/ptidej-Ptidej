package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;
import sun.reflect.Reflection;

import java.util.HashMap;



/**
 * TODO: Describe this!
 */
public final class LoggerFactory
{
    public static final Logger getLogger()
    {
        final Class callerClass = Reflection.getCallerClass( 2 );
        if ( theKnownLoggers.containsKey( callerClass ) == false )
        {
            final String category = callerClass.getSimpleName();
            theKnownLoggers.put( callerClass, new PluginLogger( category ) );
        }
        return theKnownLoggers.get( callerClass );
    }

    public static final Logger getLogger( final String aCategory )
    {
        if ( theKnownLoggers.containsKey( aCategory ) == false )
        {
            theKnownLoggers.put( aCategory, new PluginLogger( aCategory ) );
        }
        return theKnownLoggers.get( aCategory );
    }

    private static final HashMap<Object, Logger> theKnownLoggers = new HashMap<Object, Logger>();
}
