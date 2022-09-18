package net.intensicode.idea.config;

import net.intensicode.idea.syntax.RecognizedToken;

import java.util.List;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public interface InstanceConfiguration
{
    Icon getIcon();

    String getName();

    String getDescription();

    String getExampleCode();


    boolean isVisibleToken( String aTokenId );

    List<RecognizedToken> getRecognizedTokens();

    String getTokenAttributes( String aTokenID );

    String getTokenDescription( String aTokenID );


    BracesConfiguration getBracesConfiguration();

    CommentConfiguration getCommentConfiguration();

    FileTypeConfiguration getFileTypeConfiguration();
}
