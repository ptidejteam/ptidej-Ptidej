package net.intensicode.idea.syntax;

import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.RubyClass;
import org.jruby.RubyFixnum;
import org.jruby.ast.Node;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.Map;



/**
 * TODO: Describe this!
 */
public final class RecognizedTokenRuby implements RecognizedToken
{
    public static final RecognizedTokenRuby newInstance( final String aTokenID, final String aScript )
    {
        initialize();
        return new RecognizedTokenRuby( aTokenID, aScript );
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

    private RecognizedTokenRuby( final String aTokenID, final String aScript )
    {
        myTokenID = aTokenID;

        final String className = theRuby.newSymbol( aTokenID ).asSymbol();

        final StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder.append( "class " );
        scriptBuilder.append( className );
        scriptBuilder.append( '\n' );
        scriptBuilder.append( aScript );
        scriptBuilder.append( '\n' );
        scriptBuilder.append( "end\n" );

        final String fullScript = scriptBuilder.toString();
        final Node scriptNode = theRuby.parse( fullScript, aTokenID );

        final RubyClass clazz = theRuby.defineClass( className, theRuby.getObject() );
        clazz.eval( scriptNode );

        final Map methods = clazz.getMethods();
        myFindAtMethod = methods.containsKey( "find_at" );
        myFindInMethod = methods.containsKey( "find_in" );

        if ( !( myFindAtMethod || myFindInMethod ) )
        {
            throw new IllegalArgumentException( "Missing find_at or find_in implementation for " + aTokenID );
        }

        myInstance = clazz.callMethod( "new" );
    }

    private final boolean callFindAt( final CharSequence aCharSequence, final int aStartOffset )
    {
        final IRubyObject[] args = new IRubyObject[2];
        args[ 0 ] = theRuby.newString( aCharSequence.toString() );
        args[ 1 ] = theRuby.newFixnum( aStartOffset );

        final IRubyObject result = myInstance.callMethod( "find_at", args );
        if ( result == null || result.isNil() ) return false;
        if ( result.getType().getBaseName().equals( "FalseClass" ) ) return false;

        myTokenStart = ( int ) result.callMethod( "[]", theIndex0 ).convertToInteger().getLongValue();
        myTokenEnd = ( int ) result.callMethod( "[]", theIndex1 ).convertToInteger().getLongValue();
        return true;
    }

    private final boolean callFindIn( final CharSequence aCharSequence, final int aStartOffset, final int aEndOffset )
    {
        final IRubyObject[] args = new IRubyObject[3];
        args[ 0 ] = theRuby.newString( aCharSequence.toString() );
        args[ 1 ] = theRuby.newFixnum( aStartOffset );
        args[ 2 ] = theRuby.newFixnum( aEndOffset );

        final IRubyObject result = myInstance.callMethod( "find_in", args );
        if ( result == null || result.isNil() ) return false;
        if ( result.getType().getBaseName().equals( "FalseClass" ) ) return false;

        myTokenStart = ( int ) result.callMethod( "[]", theIndex0 ).convertToInteger().getLongValue();
        myTokenEnd = ( int ) result.callMethod( "[]", theIndex1 ).convertToInteger().getLongValue();
        return true;
    }

    private synchronized static final void initialize()
    {
        if ( theRuby != null ) return;

        theRuby = Ruby.getDefaultInstance();
        theIndex0 = theRuby.newFixnum( 0 );
        theIndex1 = theRuby.newFixnum( 1 );
    }



    private int myTokenEnd = 0;

    private int myTokenStart = 0;

    private final String myTokenID;

    private final IRubyObject myInstance;

    private final boolean myFindAtMethod;

    private final boolean myFindInMethod;


    private static IRuby theRuby;

    private static RubyFixnum theIndex0;

    private static RubyFixnum theIndex1;
}
