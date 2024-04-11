package net.intensicode.idea.config;

import junit.framework.TestCase;
import net.intensicode.idea.config.loaded.LoadedConfiguration;
import net.intensicode.idea.core.FakeOptionsFolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public final class TestLoadedConfiguration extends TestCase
{
    public final void testCreate() throws IOException
    {
        final FakeOptionsFolder optionsFolder = new FakeOptionsFolder( this );

        final InputStream input = getClass().getResourceAsStream( "TestConfig_Ruby.config" );
        final LoadedConfiguration configuration = new LoadedConfiguration( optionsFolder, new InputStreamReader( input ) );
        assertEquals( "Ruby", configuration.getName() );
        assertEquals( "Ruby Script File", configuration.getDescription() );
        assertEquals( 275, configuration.getExampleCode().length() );
        assertNotNull( configuration.getIcon() );
        assertEquals( 18, configuration.getIcon().getIconWidth() );
        assertEquals( 18, configuration.getIcon().getIconHeight() );

        final CommentConfiguration commentConfiguration = configuration.getCommentConfiguration();
        assertEquals( "#", commentConfiguration.getLineCommentPrefix() );
        assertEquals( "", commentConfiguration.getBlockCommentPrefix() );
        assertEquals( "", commentConfiguration.getBlockCommentSuffix() );

        final FileTypeConfiguration fileTypeConfiguration = configuration.getFileTypeConfiguration();
        assertEquals( 2, fileTypeConfiguration.getExtensions().length );
        assertEquals( ".rb", fileTypeConfiguration.getExtensions()[ 0 ] );
        assertEquals( ".ruby", fileTypeConfiguration.getExtensions()[ 1 ] );
        assertEquals( ".rb", fileTypeConfiguration.getDefaultExtension() );
        assertNotNull( fileTypeConfiguration.getIcon() );
        assertEquals( 9, fileTypeConfiguration.getIcon().getIconWidth() );
        assertEquals( 9, fileTypeConfiguration.getIcon().getIconHeight() );

        assertEquals( "Block comment", configuration.getTokenDescription( "BLOCK_COMMENT" ) );
    }
}
