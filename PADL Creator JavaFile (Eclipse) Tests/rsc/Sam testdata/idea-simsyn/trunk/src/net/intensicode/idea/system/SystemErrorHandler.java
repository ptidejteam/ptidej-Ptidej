package net.intensicode.idea.system;

import net.intensicode.idea.syntax.RecognizedToken;



/**
 * TODO: Describe this!
 */
public interface SystemErrorHandler
{
    void onTokenRecognizerFailed( RecognizedToken aToken, Throwable aThrowable );

    void onSimpleSyntaxInstallFailed( Throwable aThrowable );

    Confirmation onFileReplaceConfirmation( String aFileName );

    Confirmation onFileTypeReplaceConfirmation( String aFileType );

    Confirmation onFileTypeInUseConfirmation( String[] aExtensions );

    /**
     * Use this to make the error handler forget 'All' and 'None' answers to confirmation questions.
     */
    void forgetConfirmationAnswers();
}
