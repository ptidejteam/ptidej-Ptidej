package net.intensicode.idea.config.loaded.parser;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.core.SyntaxRuleSet;
import net.intensicode.idea.util.LoggerFactory;



/**
 * TODO: Describe this!
 */
public final class SyntaxRuleConsumer implements LineConsumer
{
    public SyntaxRuleConsumer( final SyntaxRuleSet aRuleSet )
    {
        myRuleSet = aRuleSet;
    }

    // From LineConsumer

    public final void consume( final int aLineType, final MatchedLine aMatchedLine )
    {
        if ( aLineType != ConfigurationParser.SYNTAX_RULE ) return;

        final String type = aMatchedLine.getValue( 1 );
        final String id = aMatchedLine.getValue( 2 );
        final String value = aMatchedLine.getValue( 3 );
        myRuleSet.add( type, id, value );

        LOG.info( "Loaded " + type + " rule: " + id + " = " + value );
    }



    private final SyntaxRuleSet myRuleSet;

    private static final Logger LOG = LoggerFactory.getLogger();
}
