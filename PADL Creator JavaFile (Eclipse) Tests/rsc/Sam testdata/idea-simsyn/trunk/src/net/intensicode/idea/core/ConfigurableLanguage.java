package net.intensicode.idea.core;

import com.intellij.lang.Commenter;
import com.intellij.lang.Language;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.syntax.SimpleSyntaxHighlighter;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.DynamicClassHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * Adapter between IDEA Language objects and our LanguageConfiguration. Takes care of the IDEA OpenAPI quirks:
 * <ul>
 * <li>Circumvent the global Language registry by creating unique subclasses for each LanguageConfiguration.</li>
 * <li>Create and manage unique IElementType/GenericElementType instances (defined tokens).</li>
 * <li>Manage ConfigurableTextAttributes to make TextAttributesKey instances appear dynamic (color settings).</li>
 * </ul>
 */
public class ConfigurableLanguage extends Language implements LanguageConfiguration
{
    public static final ConfigurableLanguage getOrCreate( final SystemContext aSystemContext, InstanceConfiguration aConfiguration )
    {
        final String name = aConfiguration.getName();
        for ( final Language language : Language.getRegisteredLanguages() )
        {
            if ( language.getID().equals( name ) == false ) continue;
            if ( language instanceof ConfigurableLanguage == false )
            {
                throw new RuntimeException( "Incompatible language registered for " + name );
            }

            final ConfigurableLanguage oldLanguage = ( ConfigurableLanguage ) language;
            oldLanguage.reset( aSystemContext, aConfiguration );
            return oldLanguage;
        }

        final String className = "ConfigurableLanguage" + name;
        final Class clazz = ConfigurableLanguage.class;
        return ( ConfigurableLanguage ) DynamicClassHelper.newInstance( className, clazz, aSystemContext, aConfiguration );
    }

    // From LanguageConfiguration

    public final IElementType getToken( final String aTokenId )
    {
        if ( myTokens.containsKey( aTokenId ) == false )
        {
            myTokens.put( aTokenId, new GenericElementType( aTokenId, this ) );
        }
        return myTokens.get( aTokenId );
    }

    public final SyntaxHighlighter getSyntaxHighlighter()
    {
        return mySyntaxHighlighter;
    }

    public final List<RecognizedToken> getRecognizedTokens()
    {
        return myRecognizedTokens;
    }

    public final TextAttributesKey getTextAttributesKey( final String aTokenID )
    {
        final String keyID = makeKeyID( aTokenID );
        if ( myTextAttributesKeys.containsKey( keyID ) == false )
        {
            final TextAttributes attributes = createTextAttributes( keyID, aTokenID );
            final TextAttributesKey attributesKey = mySystemContext.createTextAttributesKey( keyID, attributes );
            myTextAttributesKeys.put( keyID, attributesKey );
        }
        return myTextAttributesKeys.get( keyID );
    }

    public final TextAttributesKey[] getTokenHighlights( final IElementType tokenType )
    {
        final TextAttributesKey[] keys = myTokenHighlights.get( tokenType );
        if ( keys == null ) return NO_TEXT_ATTRIBUTES;
        return keys;
    }

    // From Language

    @NotNull
    public final SyntaxHighlighter getSyntaxHighlighter( final Project project )
    {
        return getSyntaxHighlighter();
    }

    @Nullable
    public final PairedBraceMatcher getPairedBraceMatcher()
    {
        return myConfigurableBraceMatcher;
    }

    @Nullable
    public final Commenter getCommenter()
    {
        return myCommenter;
    }

    // Protected Interface

    public /*protected*/ ConfigurableLanguage( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        super( aConfiguration.getName() );
        reset( aSystemContext, aConfiguration );
    }

    // Implementation

    private final void reset( final SystemContext aSystemContext, final InstanceConfiguration aConfiguration )
    {
        mySystemContext = aSystemContext;
        myConfiguration = aConfiguration;

        myRecognizedTokens.clear();
        myTokenHighlights.clear();
        myTextAttributesKeys.clear();
        for ( final RecognizedToken token : aConfiguration.getRecognizedTokens() )
        {
            addRecognizedToken( token );
        }

        myCommenter = new ConfigurableCommenter( aConfiguration.getCommentConfiguration() );
        mySyntaxHighlighter = new SimpleSyntaxHighlighter( mySystemContext, this );
        myConfigurableBraceMatcher = new ConfigurableBraceMatcher( this, aConfiguration.getBracesConfiguration() );
    }

    private final void addRecognizedToken( final RecognizedToken aToken )
    {
        final String id = aToken.getTokenID();
        final IElementType elementType = getToken( id );
        final TextAttributesKey attributesKey = getTextAttributesKey( id );
        myTokenHighlights.put( elementType, new TextAttributesKey[]{attributesKey} );

        myRecognizedTokens.add( aToken );
    }

    private final TextAttributes createTextAttributes( final String aKeyID, final String aTokenID )
    {
        // Note: IDEA does a lot of stupid private/internal registry handling.
        // In case of the TextAttributes this means that for a given TextAttributesKey
        // with a certain ID, the defaults can be set only once. Which in general is
        // ok, i guess, but complicates things for us here. We have to reuse the old
        // defaults object and change its settings instead. Because we cant set a new
        // defaults object into a TextAttrKey object and we cant create a new TextAttrKey
        // for an already used ID. But the latter is exactly what happens upon reloading
        // the SimpleSyntax configuration!

        if ( myTextAttributes.containsKey( aKeyID ) == false )
        {
            myTextAttributes.put( aKeyID, new ConfigurableTextAttributes() );
        }

        final ConfigurableTextAttributes attributes = myTextAttributes.get( aKeyID );
        attributes.reset();

        final String spec = myConfiguration.getTokenAttributes( aTokenID );
        if ( spec != null && spec.length() > 0 ) attributes.setFrom( spec );

        return attributes;
    }

    private final String makeKeyID( final String aTokenID )
    {
        final StringBuilder id = new StringBuilder();
        id.append( myConfiguration.getName().toUpperCase() );
        id.append( '_' );
        id.append( aTokenID );
        return id.toString().replaceAll( "[^\\w]", "_" );
    }



    private Commenter myCommenter;

    private SystemContext mySystemContext;

    private InstanceConfiguration myConfiguration;

    private SimpleSyntaxHighlighter mySyntaxHighlighter;

    private ConfigurableBraceMatcher myConfigurableBraceMatcher;

    private final HashMap<String, IElementType> myTokens = new HashMap<String, IElementType>();

    private final ArrayList<RecognizedToken> myRecognizedTokens = new ArrayList<RecognizedToken>();

    private final HashMap<String, TextAttributesKey> myTextAttributesKeys = new HashMap<String, TextAttributesKey>();

    private final HashMap<IElementType, TextAttributesKey[]> myTokenHighlights = new HashMap<IElementType, TextAttributesKey[]>();

    private final HashMap<String, ConfigurableTextAttributes> myTextAttributes = new HashMap<String, ConfigurableTextAttributes>();

    private static final TextAttributesKey[] NO_TEXT_ATTRIBUTES = new TextAttributesKey[0];
}
