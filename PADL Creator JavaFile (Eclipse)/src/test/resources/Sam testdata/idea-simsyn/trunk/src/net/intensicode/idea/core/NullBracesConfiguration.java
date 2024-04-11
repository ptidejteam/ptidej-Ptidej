package net.intensicode.idea.core;

import net.intensicode.idea.config.BracesConfiguration;



/**
 * TODO: Describe this!
 */
public final class NullBracesConfiguration implements BracesConfiguration
{
    public static final NullBracesConfiguration INSTANCE = new NullBracesConfiguration();

    // From BracesConfiguration

    public final String[] getBracePairs()
    {
        return EMPTY_ARRAY;
    }

    public final String[] getStructuralPairs()
    {
        return EMPTY_ARRAY;
    }

    // Implementation

    private NullBracesConfiguration()
    {
    }

    private static final String[] EMPTY_ARRAY = new String[0];
}
