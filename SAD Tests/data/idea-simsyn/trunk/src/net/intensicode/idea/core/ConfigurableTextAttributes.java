package net.intensicode.idea.core;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.markup.TextAttributes;
import net.intensicode.idea.util.LoggerFactory;

import java.awt.Color;
import java.awt.Font;
import java.util.regex.Pattern;



/**
 * TODO: Describe this!
 */
final class ConfigurableTextAttributes extends TextAttributes
{
    ConfigurableTextAttributes()
    {
    }

    public ConfigurableTextAttributes( final String aSpec )
    {
        setFrom( aSpec );
    }

    final void reset()
    {
        super.setFontType( 0 );
        super.setForegroundColor( null );
        super.setBackgroundColor( null );
        super.setEffectColor( null );
        super.setErrorStripeColor( null );
    }

    final void setFrom( final String aAttributesSpec )
    {
        final String[] parts = aAttributesSpec.split( "," );
        for ( final String part : parts )
        {
            if ( isColor( part ) ) setColor( part );
            else if ( isBold( part ) ) setFontType( Font.BOLD );
            else if ( isItalic( part ) ) setFontType( Font.ITALIC );
            else LOG.error( "Unknown attribute tag: " + part + " in " + aAttributesSpec );
        }
    }

    // From TextAttributes

    public final void setFontType( final int type )
    {
        super.setFontType( getFontType() | type );
    }

    // Implementation

    private final void setColor( final String aColorSpec )
    {
        final Color color = Color.decode( aColorSpec );
        if ( getForegroundColor() == null ) setForegroundColor( color );
        else if ( getBackgroundColor() == null ) setBackgroundColor( color );
        else if ( getEffectColor() == null ) setEffectColor( color );
        else if ( getErrorStripeColor() == null ) setErrorStripeColor( color );
    }

    private static final boolean isColor( final String aPart )
    {
        return Pattern.matches( COLOR_PATTERN, aPart );
    }

    private static final boolean isBold( final String aPart )
    {
        return aPart.equalsIgnoreCase( "BOLD" );
    }

    private static final boolean isItalic( final String aPart )
    {
        return aPart.equalsIgnoreCase( "ITALIC" );
    }



    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String COLOR_PATTERN = "#[0-9a-fA-F]{6}";
}
