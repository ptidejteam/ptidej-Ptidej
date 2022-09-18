package net.intensicode.idea.core;

import com.intellij.openapi.editor.markup.EffectType;
import junit.framework.TestCase;

import java.awt.Color;
import java.awt.Font;



public final class TestConfigurableTextAttributes extends TestCase
{
    public final void testDefaults()
    {
        final ConfigurableTextAttributes attributes = new ConfigurableTextAttributes();
        assertEquals( null, attributes.getBackgroundColor() );
        assertEquals( null, attributes.getEffectColor() );
        assertEquals( EffectType.BOXED, attributes.getEffectType() );
        assertEquals( null, attributes.getErrorStripeColor() );
        assertEquals( null, attributes.getForegroundColor() );
        assertEquals( 0, attributes.getFontType() );
    }

    public final void testSomething()
    {
        final ConfigurableTextAttributes attributes = new ConfigurableTextAttributes( "#FF0000,BOLD,ITALIC" );
        assertEquals( null, attributes.getBackgroundColor() );
        assertEquals( null, attributes.getEffectColor() );
        assertEquals( EffectType.BOXED, attributes.getEffectType() );
        assertEquals( null, attributes.getErrorStripeColor() );
        assertEquals( Color.RED, attributes.getForegroundColor() );
        assertEquals( Font.BOLD | Font.ITALIC, attributes.getFontType() );
    }

    public final void testColors()
    {
        final ConfigurableTextAttributes attributes = new ConfigurableTextAttributes( "#FF0000,#FFFFFF,BOLD,ITALIC" );
        assertEquals( Color.WHITE, attributes.getBackgroundColor() );
        assertEquals( null, attributes.getEffectColor() );
        assertEquals( EffectType.BOXED, attributes.getEffectType() );
        assertEquals( null, attributes.getErrorStripeColor() );
        assertEquals( Color.RED, attributes.getForegroundColor() );
        assertEquals( Font.BOLD | Font.ITALIC, attributes.getFontType() );
    }
}
