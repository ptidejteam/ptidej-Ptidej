package net.intensicode.idea.core;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public final class ConfigurableColorSettingsPage implements ColorSettingsPage
{
    public ConfigurableColorSettingsPage( final InstanceConfiguration aConfiguration, final LanguageConfiguration aLanguageConfiguration )
    {
        myConfiguration = aConfiguration;
        myLanguageConfiguration = aLanguageConfiguration;
    }

    public final void reset( final InstanceConfiguration aConfiguration, final LanguageConfiguration aLanguageConfiguration )
    {
        LOG.info( "Resetting color settings page" );
        myConfiguration = aConfiguration;
        myLanguageConfiguration = aLanguageConfiguration;
        myAttributesDescriptors = null;
    }

    // From ColorSettingsPage

    public final Icon getIcon()
    {
        return myConfiguration.getIcon();
    }

    @NotNull
    public final String getDisplayName()
    {
        return myConfiguration.getName();
    }

    @NotNull
    public final AttributesDescriptor[] getAttributeDescriptors()
    {
        if ( myLanguageConfiguration.getRecognizedTokens().size() == 0 )
        {
            return NO_ATTRIBUTES_DESCRIPTORS;
        }

        validateDescriptors();
        if ( myAttributesDescriptors != null ) return myAttributesDescriptors;

        final ArrayList<AttributesDescriptor> descriptors = createDescriptors();
        return myAttributesDescriptors = descriptors.toArray( new AttributesDescriptor[ descriptors.size() ] );
    }

    @NotNull
    public final ColorDescriptor[] getColorDescriptors()
    {
        return NO_COLOR_DESCRIPTOR;
    }

    @NotNull
    public final SyntaxHighlighter getHighlighter()
    {
        return myLanguageConfiguration.getSyntaxHighlighter();
    }

    @NotNull
    public final String getDemoText()
    {
        return myConfiguration.getExampleCode();
    }

    public final Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap()
    {
        return null;
    }

    // Implementation

    private final void validateDescriptors()
    {
        if ( myAttributesDescriptors == null ) return;

        final int availableTokens = myLanguageConfiguration.getRecognizedTokens().size();
        final int availableDescriptors = myAttributesDescriptors.length;
        if ( availableTokens == availableDescriptors ) return;

        myAttributesDescriptors = null;
    }

    private final ArrayList<AttributesDescriptor> createDescriptors()
    {
        final ArrayList<String> handledIDs = new ArrayList<String>();

        final ArrayList<AttributesDescriptor> descriptors = new ArrayList<AttributesDescriptor>();
        for ( final RecognizedToken token : myLanguageConfiguration.getRecognizedTokens() )
        {
            final String id = token.getTokenID();
            if ( handledIDs.contains( id ) ) continue;
            if ( myConfiguration.isVisibleToken( id ) == false ) continue;

            final String description = myConfiguration.getTokenDescription( id );
            final TextAttributesKey attributesKey = myLanguageConfiguration.getTextAttributesKey( id );
            descriptors.add( new AttributesDescriptor( description, attributesKey ) );

            handledIDs.add( id );
        }
        return descriptors;
    }



    private AttributesDescriptor[] myAttributesDescriptors;

    private InstanceConfiguration myConfiguration;

    private LanguageConfiguration myLanguageConfiguration;

    private static final ColorDescriptor[] NO_COLOR_DESCRIPTOR = new ColorDescriptor[0];

    private static final AttributesDescriptor[] NO_ATTRIBUTES_DESCRIPTORS = new AttributesDescriptor[0];

    private static final Logger LOG = LoggerFactory.getLogger();
}
