package net.intensicode.idea.config;

/**
 * TODO: Describe this!
 */
public interface ConfigurationProperties
{
    String getProperty( String aKey );

    boolean isValidProperty( String aKey );
}
