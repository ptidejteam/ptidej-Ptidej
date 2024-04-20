package net.intensicode.idea.syntax;

import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * TODO: Describe this!
 */
public final class TestRegEx extends TestCase
{
    public final void testDoubleQuotedString()
    {
        final Pattern pattern = Pattern.compile( "\"(?:[^\"]|\\\")*\"" );

        String test = "this is \"a test\" you know?";

        Matcher matcher = pattern.matcher( test );
        assertEquals( true, matcher.find() );
        assertEquals( 8, matcher.start() );
        assertEquals( 16, matcher.end() );

        matcher = pattern.matcher( test );
        assertEquals( false, matcher.find( 10 ) );

        test = "this is \"a \\\"test\" you know?";

        matcher = pattern.matcher( test );
        assertEquals( true, matcher.find() );
        assertEquals( 8, matcher.start() );
        assertEquals( 18, matcher.end() );

        matcher = pattern.matcher( test );
        assertEquals( true, matcher.find( 10 ) );
        assertEquals( 12, matcher.start() );
        assertEquals( 18, matcher.end() );
    }

    public final void testDocComment()
    {
        final Pattern pattern = Pattern.compile( "(?ms)^=begin$.*^=end$" );

        String test = "this is =begin something =end you know?";

        Matcher matcher = pattern.matcher( test );
        assertEquals( false, matcher.find() );

        test = "this is\n=begin\nsomething\n=end\nyou know?";

        matcher = pattern.matcher( test );
        assertEquals( true, matcher.find() );
    }

    public final void testBlockComment()
    {
        final Pattern pattern = Pattern.compile( "(?m)^(?:\\s*#.*$){2,}" );

        String test = "this is\n# not enough\nbut what about\n#this\n#and\n#that\ndude?";

        Matcher matcher = pattern.matcher( test );
        assertEquals( true, matcher.find() );
        assertEquals( 36, matcher.start() );
        assertEquals( 52, matcher.end() );
    }

    public final void testSpecialQuotedString()
    {
        final Pattern pattern = Pattern.compile( "%[qQ](.).*\\1" );

        String test = "This is %q$ a test $. But this %Q|is not? Which makes you %q/wonder/, doesn't it?";

        Matcher matcher = pattern.matcher( test );
        assertEquals( true, matcher.find() );
        assertEquals( 8, matcher.start() );
        assertEquals( 20, matcher.end() );

        assertEquals( true, matcher.find( 20 ) );
        assertEquals( 58, matcher.start() );
        assertEquals( 68, matcher.end() );
    }
}
