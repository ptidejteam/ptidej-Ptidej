package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;
import org.apache.log4j.Level;
import org.jetbrains.annotations.NonNls;



/**
 * TODO: Describe this!
 */
final class PluginLogger extends Logger
{
    public PluginLogger( final String aCategory )
    {
        myCategory = aCategory;
    }

    // From Logger

    public final boolean isDebugEnabled()
    {
        return true;
    }

    public final void setLevel( final Level level )
    {
    }

    public final void debug( @NonNls final String message )
    {
        if ( isDebugEnabled() == false ) return;
        System.out.println( "[DEBUG] [" + myCategory + "] " + message );
    }

    public final void debug( @NonNls String message, Throwable t )
    {
        if ( isDebugEnabled() == false ) return;
        debug( message );
        debug( t );
    }

    public final void debug( final Throwable t )
    {
        if ( isDebugEnabled() == false ) return;
        System.out.println( "[DEBUG] [" + myCategory + "] " + t.getMessage() );
        t.printStackTrace();
    }

    public final void info( final @NonNls String message )
    {
        System.out.println( "[INFO] [" + myCategory + "] " + message );
    }

    public final void info( final @NonNls String message, final Throwable t )
    {
        System.out.println( "[INFO] [" + myCategory + "] " + message );
        t.printStackTrace();
    }

    public final void warn( final @NonNls String message, final Throwable t )
    {
        System.out.println( "[WARN] [" + myCategory + "] " + message );
        t.printStackTrace();
    }

    public final void error( final @NonNls String message, final Throwable t, final @NonNls String... details )
    {
        System.out.println( "[ERROR] [" + myCategory + "] " + message );
        error( t );
    }

    public final void error( final Throwable t )
    {
        System.out.println( "[ERROR] [" + myCategory + "] " + t.getMessage() );
        t.printStackTrace();
    }



    private final String myCategory;
}
