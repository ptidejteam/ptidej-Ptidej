package net.intensicode.idea.core;

import net.intensicode.idea.config.CommentConfiguration;



/**
 * TODO: Describe this!
 */
public final class NullCommentConfiguration implements CommentConfiguration
{
    public static final NullCommentConfiguration INSTANCE = new NullCommentConfiguration();

    public final String getBlockCommentPrefix()
    {
        return null;
    }

    public final String getBlockCommentSuffix()
    {
        return null;
    }

    public final String getLineCommentPrefix()
    {
        return null;
    }

    private NullCommentConfiguration()
    {
    }
}
