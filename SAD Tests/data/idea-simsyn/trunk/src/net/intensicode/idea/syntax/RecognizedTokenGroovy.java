package net.intensicode.idea.syntax;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.MetaMethod;
import org.codehaus.groovy.control.CompilationFailedException;

import java.util.List;



/**
 * TODO: Describe this!
 */
public final class RecognizedTokenGroovy implements RecognizedToken
{
    public static final RecognizedTokenGroovy newInstance( final String aTokenID, final String aScript )
    {
        return new RecognizedTokenGroovy( aTokenID, aScript );
    }

    public final boolean isFindAtDefined()
    {
        return myFindAtMethod;
    }

    public final boolean isFindInDefined()
    {
        return myFindInMethod;
    }

    // RecognizedToken

    public final boolean isFoundAt( final CharSequence aCharSequence, final int aStartOffset )
    {
        if ( aCharSequence == null || aCharSequence.length() == 0 ) return false;
        if ( myFindAtMethod ) return callFindAt( aCharSequence, aStartOffset );

        if ( isFoundIn( aCharSequence, aStartOffset, aCharSequence.length() ) )
        {
            return getTokenStart() == aStartOffset;
        }
        return false;
    }

    public final boolean isFoundIn( final CharSequence aCharSequence, final int aStartOffset, final int aEndOffset )
    {
        if ( aCharSequence == null || aCharSequence.length() == 0 ) return false;
        if ( myFindInMethod ) return callFindIn( aCharSequence, aStartOffset, aEndOffset );

        for ( int idx = aStartOffset; idx < aEndOffset; idx++ )
        {
            if ( isFoundAt( aCharSequence, idx ) ) return true;
        }
        return false;
    }

    public final int getTokenStart()
    {
        return myTokenStart;
    }

    public final int getTokenEnd()
    {
        return myTokenEnd;
    }

    public final String getTokenID()
    {
        return myTokenID;
    }

    // Implementation

    private RecognizedTokenGroovy( final String aTokenID, final String aScript )
    {
        myTokenID = aTokenID;

        final StringBuilder script = new StringBuilder();
        script.append( "class " );
        script.append( aTokenID );
        script.append( " {\n" );
        script.append( aScript );
        script.append( "\n}\n" );
        script.append( "return new " );
        script.append( aTokenID );
        script.append( "()\n" );

        try
        {
            final ClassLoader groovyLoader = GroovyShell.class.getClassLoader();
            myInstance = ( GroovyObject ) new GroovyShell( groovyLoader ).evaluate( script.toString() );
        }
        catch ( final CompilationFailedException e )
        {
            throw new IllegalArgumentException( "Error in Groovy script", e );
        }

        myFindAtMethod = isMethodDefined( "find_at" );
        myFindInMethod = isMethodDefined( "find_in" );

        if ( !( myFindAtMethod || myFindInMethod ) )
        {
            throw new IllegalArgumentException( "Missing find_at or find_in implementation for " + aTokenID );
        }
    }

    private final boolean isMethodDefined( final String aMethodName )
    {
        for ( final Object object : myInstance.getMetaClass().getMethods() )
        {
            final MetaMethod method = ( MetaMethod ) object;
            if ( method.getName().equals( aMethodName ) ) return true;
        }
        return false;
    }

    private final boolean callFindAt( final CharSequence aCharSequence, final int aStartOffset )
    {
        final Object result = myInstance.invokeMethod( "find_at", new Object[]{aCharSequence, aStartOffset} );
        if ( result == null ) return false;
        if ( result instanceof List == false )
        {
            throw new IllegalArgumentException( "Groovy find_at method should return null or a list, but not " + result );
        }

        final List list = ( List ) result;
        myTokenStart = ( Integer ) list.get( 0 );
        myTokenEnd = ( Integer ) list.get( 1 );
        return true;
    }

    private final boolean callFindIn( final CharSequence aCharSequence, final int aStartOffset, final int aEndOffset )
    {
        final Object result = myInstance.invokeMethod( "find_in", new Object[]{aCharSequence, aStartOffset, aEndOffset} );
        if ( result == null ) return false;
        if ( result instanceof List == false )
        {
            throw new IllegalArgumentException( "Groovy find_in method should return null or a list, but not " + result );
        }

        final List list = ( List ) result;
        myTokenStart = ( Integer ) list.get( 0 );
        myTokenEnd = ( Integer ) list.get( 1 );
        return true;
    }



    private int myTokenEnd = 0;

    private int myTokenStart = 0;

    private final String myTokenID;

    private final GroovyObject myInstance;

    private final boolean myFindAtMethod;

    private final boolean myFindInMethod;
}
