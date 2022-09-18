package net.intensicode.idea.core;

import junit.framework.TestCase;
import net.intensicode.idea.FakeConfiguration;



public final class TestConfigurableLanguage extends TestCase
{
    public final void testCreate()
    {
        final FakeSystemContext context = new FakeSystemContext( this );
        final FakeConfiguration configuration = new FakeConfiguration();
        final ConfigurableLanguage language = ConfigurableLanguage.getOrCreate( context, configuration );
        assertEquals( "ConfigurableLanguageFake", language.getClass().getName() );
    }
}
