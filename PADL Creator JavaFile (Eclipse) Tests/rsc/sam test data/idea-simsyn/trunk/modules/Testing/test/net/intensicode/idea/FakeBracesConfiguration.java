package net.intensicode.idea;

import net.intensicode.idea.config.BracesConfiguration;



/**
 * TODO: Describe this!
 */
public class FakeBracesConfiguration implements BracesConfiguration
{
    public String[] getBracePairs()
    {
        return new String[0];
    }

    public String[] getStructuralPairs()
    {
        return new String[0];
    }
}
