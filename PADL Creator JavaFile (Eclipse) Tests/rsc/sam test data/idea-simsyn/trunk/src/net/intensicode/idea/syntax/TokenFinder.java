package net.intensicode.idea.syntax;

import net.intensicode.idea.system.SystemErrorHandler;

import java.util.ArrayList;



/**
 * TODO: Describe this!
 */
class TokenFinder
{
    TokenFinder()
    {
        this( null );
    }

    TokenFinder( final SystemErrorHandler aErrorHandler )
    {
        myErrorHandler = aErrorHandler;
    }

    final void register( final RecognizedToken aRecognizedToken )
    {
        myRecognizedTokens.add( aRecognizedToken );
    }

    final RecognizedToken findClosest( final CharSequence aCharSequence, final int aStartOffset, final int aEndOffset )
    {
        int offset = aStartOffset;
        while ( offset < aEndOffset )
        {
            for ( final RecognizedToken token : myRecognizedTokens )
            {
                try
                {
                    if ( token.isFoundAt( aCharSequence, offset ) ) return token;
                }
                catch ( final Throwable t )
                {
                    myRecognizedTokens.remove( token );
                    if ( myErrorHandler != null )
                    {
                        myErrorHandler.onTokenRecognizerFailed( token, t );
                    }

                    return findClosest( aCharSequence, offset, aEndOffset );
                }
            }
            offset++;
        }
        return null;
    }



    private final SystemErrorHandler myErrorHandler;

    private final ArrayList<RecognizedToken> myRecognizedTokens = new ArrayList<RecognizedToken>();
}
