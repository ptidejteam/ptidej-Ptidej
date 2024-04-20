package net.intensicode.idea.core;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.config.BracesConfiguration;
import net.intensicode.idea.config.LanguageConfiguration;
import net.intensicode.idea.util.LoggerFactory;

import java.util.ArrayList;
import java.util.List;



/**
 * TODO: Describe this!
 */
final class ConfigurableBraceMatcher implements PairedBraceMatcher
{
    ConfigurableBraceMatcher( final LanguageConfiguration aLanguageConfiguration, final BracesConfiguration aConfiguration )
    {
        myConfiguration = aLanguageConfiguration;

        final List<BracePair> bracePairs = createBracePairs( aConfiguration.getBracePairs(), false );
        final List<BracePair> structuralPairs = createBracePairs( aConfiguration.getStructuralPairs(), true );
        bracePairs.addAll( structuralPairs );

        myPairs = new BracePair[ bracePairs.size() ];
        bracePairs.toArray( myPairs );
    }

    // From PairedBraceMatcher

    public final BracePair[] getPairs()
    {
        return myPairs;
    }

    // Implementation

    private final ArrayList<BracePair> createBracePairs( final String[] aBracePairs, final boolean aStructural )
    {
        final ArrayList<BracePair> result = new ArrayList<BracePair>();
        for ( final String pair : aBracePairs )
        {
            if ( pair.length() != 2 )
            {
                LOG.error( "Ignoring invalid brace pair spec: " + pair );
                continue;
            }
            final char left = pair.charAt( 0 );
            final char right = pair.charAt( 1 );
            final IElementType leftToken = myConfiguration.getToken( new String( new char[]{left} ) );
            final IElementType rightToken = myConfiguration.getToken( new String( new char[]{right} ) );
            result.add( new BracePair( left, leftToken, right, rightToken, aStructural ) );
        }
        return result;
    }



    private final BracePair[] myPairs;

    private final LanguageConfiguration myConfiguration;

    private static final Logger LOG = LoggerFactory.getLogger();
}
