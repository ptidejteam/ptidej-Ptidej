package net.intensicode.idea.config;

/**
 * TODO: Describe this!
 */
final class FakeConfigurationProperties implements ConfigurationProperties
{
    public String getProperty( String aKey )
    {
        return null;
    }

    public boolean isValidProperty( String aKey )
    {
        return false;
    }
}
