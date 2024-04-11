package net.intensicode.idea.core;

import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.system.SystemErrorHandler;
import net.intensicode.idea.system.Confirmation;



/**
 * TODO: Describe this!
 */
public final class FakeSystemErrorHandler implements SystemErrorHandler
{
    public void onTokenRecognizerFailed( RecognizedToken aToken, Throwable aThrowable )
    {
        throw new RuntimeException( aToken.getTokenID(), aThrowable );
    }

    public void onSimpleSyntaxInstallFailed( Throwable aThrowable )
    {
        throw new RuntimeException( aThrowable );
    }

    public Confirmation onFileReplaceConfirmation( String aFileName )
    {
        throw new RuntimeException( "NYI" );
    }

    public Confirmation onFileTypeReplaceConfirmation( String aFileType )
    {
        throw new RuntimeException( "NYI" );
    }

    public Confirmation onFileTypeInUseConfirmation( String[] aExtensions )
    {
        throw new RuntimeException( "NYI" );
    }

    public void forgetConfirmationAnswers()
    {
        throw new RuntimeException( "NYI" );
    }
}
