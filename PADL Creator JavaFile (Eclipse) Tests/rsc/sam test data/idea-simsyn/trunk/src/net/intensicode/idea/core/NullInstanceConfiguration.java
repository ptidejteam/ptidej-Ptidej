package net.intensicode.idea.core;

import net.intensicode.idea.config.BracesConfiguration;
import net.intensicode.idea.config.CommentConfiguration;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.syntax.RecognizedToken;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
final class NullInstanceConfiguration implements InstanceConfiguration
{
    static final NullInstanceConfiguration INSTANCE = new NullInstanceConfiguration();

    public final BracesConfiguration getBracesConfiguration()
    {
        return NullBracesConfiguration.INSTANCE;
    }

    public final CommentConfiguration getCommentConfiguration()
    {
        return NullCommentConfiguration.INSTANCE;
    }

    public final String getDescription()
    {
        return NULL_STRING;
    }

    public final String getExampleCode()
    {
        return NULL_STRING;
    }

    public final FileTypeConfiguration getFileTypeConfiguration()
    {
        return NullFileTypeConfiguration.INSTANCE;
    }

    public final Icon getIcon()
    {
        return null;
    }

    public final String getName()
    {
        return NULL_STRING;
    }

    public final List<RecognizedToken> getRecognizedTokens()
    {
        return NO_RECOGNIZED_TOKENS;
    }

    public final String getTokenAttributes( final String aTokenID )
    {
        return EMPTY_STRING;
    }

    public final String getTokenDescription( final String aTokenID )
    {
        return EMPTY_STRING;
    }

    public final boolean isVisibleToken( final String aTokenId )
    {
        return false;
    }

    private NullInstanceConfiguration()
    {
    }

    private static final String EMPTY_STRING = "";

    private static final String NULL_STRING = "NULL";

    private static final ArrayList<RecognizedToken> NO_RECOGNIZED_TOKENS = new ArrayList<RecognizedToken>();
}
