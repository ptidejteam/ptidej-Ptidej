package net.intensicode.idea.config;

import junit.framework.TestCase;



public final class TestLoadedSyntaxConfiguration extends TestCase
{
    public final void testSomething()
    {
    }

//    public final void testCreate()
//    {
//        myDescriptions.clear();
//        myDescriptions.put( "BLOCK_COMMENT", "Block comment" );
//        myDescriptions.put( "LINE_COMMENT", "Line comment" );
//        myDescriptions.put( "DOC_COMMENT", "Documentation" );
//
//        myLoadedRuleSet.clear();
//        myLoadedRuleSet.add( "regex", "BLOCK_COMMENT", "(?m)^(?:\\s*#.*$){2,}" );
//        myLoadedRuleSet.add( "regex", "LINE_COMMENT", "(?m)#.*$" );
//        myLoadedRuleSet.add( "regex", "DOC_COMMENT", "(?ms)^=begin$.*^=end$" );
//
//        final LoadedSyntaxConfiguration configuration = new LoadedSyntaxConfiguration( this );
//        assertEquals( 3, configuration.getAttributesDescriptors().size() );
//        assertEquals( 5, configuration.getRecognizedTokens().size() );
//
//        assertEquals( "Block comment", configuration.getAttributesDescriptors().get( 0 ).getDisplayName() );
//        assertEquals( "Line comment", configuration.getAttributesDescriptors().get( 1 ).getDisplayName() );
//        assertEquals( "Documentation", configuration.getAttributesDescriptors().get( 2 ).getDisplayName() );
//
//        final LanguageConfiguration spec = getLanguageSpecification();
//        final IElementType BLOCK_COMMENT = spec.getToken( "BLOCK_COMMENT" );
//        final IElementType LINE_COMMENT = spec.getToken( "LINE_COMMENT" );
//        final IElementType DOC_COMMENT = spec.getToken( "DOC_COMMENT" );
//
//        assertEquals( BLOCK_COMMENT, configuration.getRecognizedTokens().get( 0 ).getTokenType() );
//        assertEquals( LINE_COMMENT, configuration.getRecognizedTokens().get( 1 ).getTokenType() );
//        assertEquals( DOC_COMMENT, configuration.getRecognizedTokens().get( 2 ).getTokenType() );
//
//        assertEquals( 1, configuration.getTokenHighlights( BLOCK_COMMENT ).length );
//        assertEquals( null, configuration.getTokenHighlights( BLOCK_COMMENT )[ 0 ] );
//    }
//
//    public final void testRubyRule()
//    {
//        myDescriptions.clear();
//
//        myLoadedRuleSet.clear();
//        myLoadedRuleSet.add( "ruby", "QUOTED_STRING", "TestCode_QuotedString.rb" );
//
//        final LoadedSyntaxConfiguration configuration = new LoadedSyntaxConfiguration( this );
//        assertEquals( 1, configuration.getRecognizedTokens().size() );
//
//        final LanguageConfiguration spec = getLanguageSpecification();
//        final IElementType QUOTED_STRING = spec.getToken( "QUOTED_STRING" );
//        assertEquals( QUOTED_STRING, configuration.getRecognizedTokens().get( 0 ).getTokenType() );
//    }
//
//    // From ConfigurationAPI
//
//    public ConfigurationProperties getConfigurationProperties()
//    {
//        return null;
//    }
//
//    public LanguageConfiguration getLanguageSpecification()
//    {
//        return LANGUAGE_SPECIFICATION;
//    }
//
//    public LoadedRuleSet getRuleSet()
//    {
//        return myLoadedRuleSet;
//    }
//
//    public SystemContext getSystemContext()
//    {
//        return new FakeSystemContext();
//    }
//
//    public Reader readFile( String aFileName )
//    {
//        return null;
//    }
//
//    // From InstanceConfiguration
//
//    public BracesConfiguration getBracesConfiguration()
//    {
//        return null;
//    }
//
//    public CommentConfiguration getCommentConfiguration()
//    {
//        return null;
//    }
//
//    public String getDescription()
//    {
//        return null;
//    }
//
//    public String getExampleCode()
//    {
//        return null;
//    }
//
//    public FileTypeConfiguration getFileTypeConfiguration()
//    {
//        return null;
//    }
//
//    public Icon getIcon()
//    {
//        return null;
//    }
//
//    public SyntaxConfiguration getSyntaxConfiguration()
//    {
//        return null;
//    }
//
//    public HashMap<String, String> getElementAttributes()
//    {
//        return myAttributes;
//    }
//
//    public HashMap<String, String> getElementDescriptions()
//    {
//        return myDescriptions;
//    }
//
//
//
//    private final LoadedRuleSet myLoadedRuleSet = new LoadedRuleSet();
//
//    private final HashMap<String, String> myAttributes = new HashMap<String, String>();
//
//    private final HashMap<String, String> myDescriptions = new HashMap<String, String>();
//
//    private static final LanguageConfiguration LANGUAGE_SPECIFICATION = new FakeConfigurableLanguage();
}
