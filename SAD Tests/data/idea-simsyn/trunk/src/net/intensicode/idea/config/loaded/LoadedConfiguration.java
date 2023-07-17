package net.intensicode.idea.config.loaded;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.config.*;
import net.intensicode.idea.config.loaded.parser.AssignmentConsumer;
import net.intensicode.idea.config.loaded.parser.ConfigurationParser;
import net.intensicode.idea.config.loaded.parser.PropertyConsumer;
import net.intensicode.idea.config.loaded.parser.SyntaxRuleConsumer;
import net.intensicode.idea.core.SyntaxRuleSet;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.core.SyntaxRuleSpecification;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.util.ReaderUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public class LoadedConfiguration implements InstanceConfiguration, ConfigurationProperties
{
    public static final LoadedConfiguration tryLoading( final OptionsFolder aOptionsFolder, final String aFileName )
    {
        try
        {
            final String config = aOptionsFolder.readFileIntoString( aFileName );
            final Reader reader = new StringReader( config );
            return new LoadedConfiguration( aOptionsFolder, reader );
        }
        catch ( final Throwable t )
        {
            LOG.info( "Failed loading configuration: ", t );
            return null;
        }
    }

    public LoadedConfiguration( final OptionsFolder aOptionsFolder, final Reader aReader ) throws IOException
    {
        myOptionsFolder = aOptionsFolder;

        loadConfiguration( aReader );
        validateConfiguration();

        for ( final SyntaxRuleSpecification ruleSpecification : myRuleSet )
        {
            try
            {
                myRecognizedTokens.add( ruleSpecification.createToken( aOptionsFolder ) );
            }
            catch ( final Throwable t )
            {
                LOG.info( "Ignoring invalid syntax rule " + ruleSpecification );
                LOG.info( t );
                LOG.error( t );
            }
        }
    }

    // From InstanceConfiguration

    public final Icon getIcon()
    {
        if ( isValidProperty( ICON ) == false ) return null;
        return myOptionsFolder.loadIcon( getProperty( ICON ) );
    }

    public final String getName()
    {
        return getProperty( NAME );
    }

    public final String getDescription()
    {
        return getProperty( DESCRIPTION );
    }

    public final String getExampleCode()
    {
        if ( myExampleCode != null ) return myExampleCode;
        try
        {
            myExampleCode = readExampleCode();
        }
        catch ( final Throwable t )
        {
            LOG.info( t.toString() );
            LOG.error( t );
            return t.toString();
        }
        return myExampleCode;
    }

    public final boolean isVisibleToken( final String aTokenId )
    {
        if ( aTokenId.equals( "WHITESPACE" ) ) return false;
        if ( aTokenId.equals( "UNRECOGNIZED" ) ) return false;
        return true;
    }

    public final List<RecognizedToken> getRecognizedTokens()
    {
        return myRecognizedTokens;
    }

    public final String getTokenAttributes( final String aTokenID )
    {
        final String attributes = myAttributes.get( aTokenID );
        if ( attributes == null ) return NO_ATTRIBUTES;
        return attributes;
    }

    public final String getTokenDescription( final String aTokenID )
    {
        final String description = myDescriptions.get( aTokenID );
        if ( description == null ) return aTokenID;
        return description;
    }

    public final BracesConfiguration getBracesConfiguration()
    {
        return new LoadedBracesConfiguration( this );
    }

    public final CommentConfiguration getCommentConfiguration()
    {
        return new LoadedCommentConfiguration( this );
    }

    public final FileTypeConfiguration getFileTypeConfiguration()
    {
        return new LoadedFileTypeConfiguration( this, myOptionsFolder );
    }

    // From ConfigurationProperties

    public final String getProperty( final String aKey )
    {
        return myProperties.get( aKey );
    }

    public final boolean isValidProperty( final String aKey )
    {
        final String value = getProperty( aKey );
        return value != null && value.length() > 0;
    }

    // Implementation

    private final void loadConfiguration( final Reader aReader ) throws IOException
    {
        final List<String> lines = ReaderUtils.readLines( aReader );
        if ( lines.size() < 3 ) throw new IOException( "Short configuration file" );

        final String configHeader = lines.get( 0 );
        if ( isValidVersion( configHeader ) == false )
        {
            throw new IOException( "Invalid configuration version: " + configHeader );
        }

        final ConfigurationParser parser = new ConfigurationParser();
        parser.addConsumer( new PropertyConsumer( myProperties ) );
        parser.addConsumer( new SyntaxRuleConsumer( myRuleSet ) );
        parser.addConsumer( new AssignmentConsumer( myAttributes, "attributes" ) );
        parser.addConsumer( new AssignmentConsumer( myDescriptions, "descriptions" ) );
        parser.consume( lines );

        myRuleSet.add( SyntaxRuleSpecification.REGEX, "WHITESPACE", "[ \t\n\r]+" );
        myRuleSet.add( SyntaxRuleSpecification.REGEX, "UNRECOGNIZED", "." );
    }

    private final void validateConfiguration() throws IOException
    {
        for ( final String key : REQUIRED_ENTRIES )
        {
            if ( isValidProperty( key ) == false )
            {
                throw new IOException( "Missing '" + key + "' entry" );
            }
        }
    }

    private static final boolean isValidVersion( final String aConfigHeader )
    {
        return aConfigHeader.equals( "[SimpleSyntax:V1.0]" );
    }

    private final String readExampleCode() throws Throwable
    {
        if ( isValidProperty( EXAMPLE_CODE ) == false ) return "No example code!";

        final String exampleCodeFileName = getProperty( EXAMPLE_CODE );
        return myOptionsFolder.readFileIntoString( exampleCodeFileName );
    }



    private String myExampleCode;

    private final OptionsFolder myOptionsFolder;

    private final HashMap<String, String> myProperties = new HashMap<String, String>();

    private final HashMap<String, String> myAttributes = new HashMap<String, String>();

    private final HashMap<String, String> myDescriptions = new HashMap<String, String>();

    private final SyntaxRuleSet myRuleSet = new SyntaxRuleSet();

    private final ArrayList<RecognizedToken> myRecognizedTokens = new ArrayList<RecognizedToken>();


    private static final String NAME = "Name";

    private static final String ICON = "Icon";

    private static final String NO_ATTRIBUTES = "";

    private static final String DESCRIPTION = "Description";

    private static final String EXAMPLE_CODE = "ExampleCode";

    private static final String[] REQUIRED_ENTRIES = {NAME, DESCRIPTION};

    private static final Logger LOG = LoggerFactory.getLogger();
}
