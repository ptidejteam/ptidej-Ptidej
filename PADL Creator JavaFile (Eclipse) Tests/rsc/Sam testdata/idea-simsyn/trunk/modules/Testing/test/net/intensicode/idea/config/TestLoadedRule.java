package net.intensicode.idea.config;

import junit.framework.TestCase;
import net.intensicode.idea.core.SyntaxRuleSpecification;



public final class TestLoadedRule extends TestCase
{
    public final void testCreate()
    {
        final SyntaxRuleSpecification rule = SyntaxRuleSpecification.create( "regex", "id", "value" );
        assertEquals( "regex", rule.type );
        assertEquals( "id", rule.id );
        assertEquals( "value", rule.value );
    }

//    public final void testTryIdentify()
//    {
//        assertEquals( "regex", SyntaxRuleSpecification.tryIdentify( "regex" ) );
//        assertSame( SyntaxRuleSpecification.REGEX, SyntaxRuleSpecification.tryIdentify( "regex" ) );
//    }
//
//    public final void testGetTypeString() throws NoSuchFieldException
//    {
//        final Field field = SyntaxRuleSpecification.class.getDeclaredField( "REGEX" );
//        assertEquals( "regex", SyntaxRuleSpecification.getTypeString( field ) );
//    }
}
