package net.intensicode.idea.config.parser;

import junit.framework.TestCase;
import net.intensicode.idea.config.loaded.parser.ConfigurationParser;



public final class TestConfigurationParser extends TestCase
{
    public final void testGetValue() throws Exception
    {
        final ConfigurationParser parser = new ConfigurationParser();
        assertEquals( ConfigurationParser.IGNORE, parser.consume( "" ) );
        assertEquals( ConfigurationParser.COMMENT, parser.consume( "# comment" ) );
        assertEquals( ConfigurationParser.COMMENT, parser.consume( "# comment" ) );
        assertEquals( ConfigurationParser.COMMENT, parser.consume( "  # comment" ) );
        assertEquals( ConfigurationParser.COMMENT, parser.consume( "   # comment #  " ) );

        checkProperty( "x", "7", "x:7" );
        checkProperty( "x", "7", "x : 7" );
        checkProperty( "x", "7", "x : 7" );
        checkProperty( "x", "7", " x : 7 " );
        checkProperty( "x", "7", " x = 7 " );
        checkProperty( "x", "7", " x =7 " );
        checkProperty( "x", "7", " x=7 " );
        checkProperty( "x", "7", "x=7 " );
        checkProperty( "x", "7", "x=7" );

        checkRule( "regex", "BLOCK_COMMENT", "/RegEx/", "regex BLOCK_COMMENT => /RegEx/" );
        checkRule( "regex", "BLOCK_COMMENT", "/RegEx/", "   regex BLOCK_COMMENT=>/RegEx/   " );
    }

    private final void checkProperty( final String aKey, final String aValue, final String aLine )
    {
        final ConfigurationParser parser = new ConfigurationParser();
        assertEquals( ConfigurationParser.PROPERTY, parser.consume( aLine ) );
        assertEquals( aKey, parser.getValue( 1 ) );
        assertEquals( aValue, parser.getValue( 2 ) );
    }

    private final void checkRule( final String aType, final String aID, final String aValue, final String aLine )
    {
        final ConfigurationParser parser = new ConfigurationParser();
        assertEquals( ConfigurationParser.SYNTAX_RULE, parser.consume( aLine ) );
        assertEquals( aType, parser.getValue( 1 ) );
        assertEquals( aID, parser.getValue( 2 ) );
        assertEquals( aValue, parser.getValue( 3 ) );
    }
}
