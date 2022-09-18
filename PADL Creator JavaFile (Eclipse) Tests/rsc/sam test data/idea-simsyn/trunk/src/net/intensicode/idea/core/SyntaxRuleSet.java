package net.intensicode.idea.core;

import java.util.ArrayList;
import java.util.Iterator;



/**
 * TODO: Describe this!
 */
public final class SyntaxRuleSet implements Iterable<SyntaxRuleSpecification>
{
    public final void clear()
    {
        myRules.clear();
    }

    public final void add( final String aType, final String aId, final String aValue )
    {
        myRules.add( SyntaxRuleSpecification.create( aType, aId, aValue ) );
    }

    // From Iterable

    public final Iterator<SyntaxRuleSpecification> iterator()
    {
        return myRules.iterator();
    }



    private final ArrayList<SyntaxRuleSpecification> myRules = new ArrayList<SyntaxRuleSpecification>();
}
