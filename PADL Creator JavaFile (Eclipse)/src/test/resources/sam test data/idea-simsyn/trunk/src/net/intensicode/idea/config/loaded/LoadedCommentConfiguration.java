package net.intensicode.idea.config.loaded;

import net.intensicode.idea.config.CommentConfiguration;
import net.intensicode.idea.config.ConfigurationProperties;



/**
 * TODO: Describe this!
 */
final class LoadedCommentConfiguration implements CommentConfiguration
{
    LoadedCommentConfiguration( final ConfigurationProperties aProperties )
    {
        myProperties = aProperties;
    }

    // From CommentConfiguration

    public final String getLineCommentPrefix()
    {
        return myProperties.getProperty( COMMENT_LINE );
    }

    public final String getBlockCommentPrefix()
    {
        return myProperties.getProperty( COMMENT_BLOCK_PREFIX );
    }

    public final String getBlockCommentSuffix()
    {
        return myProperties.getProperty( COMMENT_BLOCK_SUFFIX );
    }



    private final ConfigurationProperties myProperties;

    private static final String COMMENT_LINE = "Comment.Line";

    private static final String COMMENT_BLOCK_PREFIX = "Comment.BlockPrefix";

    private static final String COMMENT_BLOCK_SUFFIX = "Comment.BlockSuffix";
}
