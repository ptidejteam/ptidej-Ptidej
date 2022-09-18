package net.intensicode.idea;

import net.intensicode.idea.config.CommentConfiguration;



/**
 * TODO: Describe this!
 */
public class FakeCommentConfiguration implements CommentConfiguration
{
    public String getBlockCommentPrefix()
    {
        return "BEGIN";
    }

    public String getBlockCommentSuffix()
    {
        return "END";
    }

    public String getLineCommentPrefix()
    {
        return "REM";
    }
}
