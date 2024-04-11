package net.intensicode.idea.core;

import com.intellij.lang.Commenter;
import net.intensicode.idea.config.CommentConfiguration;



/**
 * TODO: Describe this!
 */
final class ConfigurableCommenter implements Commenter
{
    ConfigurableCommenter( final CommentConfiguration aConfiguration )
    {
        myConfiguration = aConfiguration;
    }

    // From Commenter

    public final String getLineCommentPrefix()
    {
        return myConfiguration.getLineCommentPrefix();
    }

    public final boolean isLineCommentPrefixOnZeroColumn()
    {
        return false;
    }

    public final String getBlockCommentPrefix()
    {
        return myConfiguration.getBlockCommentPrefix();
    }

    public final String getBlockCommentSuffix()
    {
        return myConfiguration.getBlockCommentSuffix();
    }



    private final CommentConfiguration myConfiguration;
}
