package net.intensicode.idea.syntax;

/**
 * TODO: Describe this!
 */
public interface RecognizedToken
{
    boolean isFoundAt( CharSequence aCharSequence, int aStartOffset );

    boolean isFoundIn( CharSequence aCharSequence, int aStartOffset, int aEndOffset );

    int getTokenStart();

    int getTokenEnd();

    String getTokenID();
}
