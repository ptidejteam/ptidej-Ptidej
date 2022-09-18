package net.intensicode.idea;

import net.intensicode.idea.config.BracesConfiguration;
import net.intensicode.idea.config.CommentConfiguration;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.syntax.RecognizedTokenRegEx;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public class FakeConfiguration implements InstanceConfiguration
{
    public final ArrayList<RecognizedToken> recognized_tokens = new ArrayList<RecognizedToken>();

    public FakeConfiguration()
    {
        recognized_tokens.add( new RecognizedTokenRegEx( "FAKE_TEST", "FAKE_TEST" ) );
    }

    public BracesConfiguration getBracesConfiguration()
    {
        return new FakeBracesConfiguration();
    }

    public CommentConfiguration getCommentConfiguration()
    {
        return new FakeCommentConfiguration();
    }

    public String getDescription()
    {
        return null;
    }

    public String getExampleCode()
    {
        return null;
    }

    public FileTypeConfiguration getFileTypeConfiguration()
    {
        return null;
    }

    public Icon getIcon()
    {
        return null;
    }

    public String getName()
    {
        return "Fake";
    }

    public List<RecognizedToken> getRecognizedTokens()
    {
        return recognized_tokens;
    }

    public String getTokenAttributes( String aTokenID )
    {
        return null;
    }

    public String getTokenDescription( String aTokenID )
    {
        return null;
    }

    public boolean isVisibleToken( final String aTokenId )
    {
        return false;
    }
}
