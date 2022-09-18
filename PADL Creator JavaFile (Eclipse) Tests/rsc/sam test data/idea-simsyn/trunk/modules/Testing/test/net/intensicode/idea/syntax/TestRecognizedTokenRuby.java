package net.intensicode.idea.syntax;

import junit.framework.TestCase;
import net.intensicode.idea.Utilities;
import org.jruby.IRuby;
import org.jruby.Ruby;
import org.jruby.RubyFixnum;
import org.jruby.RubyString;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.IOException;
import java.io.InputStreamReader;



public final class TestRecognizedTokenRuby extends TestCase
{
    public final void testCreate() throws IOException
    {
        final RecognizedTokenRuby token = createToken( "TestCode_Token.rb" );
        assertTrue( token.isFoundIn( "something", 0, 9 ) );
    }

    public final void testCreateReal() throws IOException
    {
        final String script = Utilities.readIntoString( this, "TestCode_TokenReal.rb" );
        final RecognizedTokenRuby token = RecognizedTokenRuby.newInstance( "TEST_CREATE_REAL", script );
        assertTrue( token.isFoundIn( "something %q! oops ! yes yes.. :)", 0, 9 ) );
        assertEquals( 10, token.getTokenStart() );
        assertEquals( 20, token.getTokenEnd() );
    }

    public final void testRubyDelimiter()
    {
        final IRubyObject result = callDelimiter( "something %q! oops ! yes yes.. :)" );
        assertNotNull( result );
        assertFalse( result.isNil() );
        assertEquals( "!", result.convertToString().getValue() );
    }

    public final void testRubyFindIn()
    {
        final IRubyObject result = callFindIn( "something %q! oops ! yes yes.. :)" );
        assertNotNull( result );
        assertFalse( result.isNil() );

        final IRuby ruby = loadRuby();
        final IRubyObject beginIndex = result.callMethod( "[]", ruby.newFixnum( 0 ) );
        final IRubyObject endIndex = result.callMethod( "[]", ruby.newFixnum( 1 ) );
        assertEquals( 10, beginIndex.convertToInteger().getLongValue() );
        assertEquals( 20, endIndex.convertToInteger().getLongValue() );
    }

    public final void testRubyFindIn2()
    {
        final IRubyObject result = callFindIn( "something %q! o\\!ops ! yes yes.. :)" );
        assertNotNull( result );
        assertFalse( result.isNil() );

        final IRuby ruby = loadRuby();
        final IRubyObject beginIndex = result.callMethod( "[]", ruby.newFixnum( 0 ) );
        final IRubyObject endIndex = result.callMethod( "[]", ruby.newFixnum( 1 ) );
        assertEquals( 10, beginIndex.convertToInteger().getLongValue() );
        assertEquals( 22, endIndex.convertToInteger().getLongValue() );
    }

    public final void testRubyFindIn3()
    {
        final IRubyObject result = callFindIn( "something %q!!! yes yes.. :)" );
        assertNotNull( result );
        assertFalse( result.isNil() );

        final IRuby ruby = loadRuby();
        final IRubyObject beginIndex = result.callMethod( "[]", ruby.newFixnum( 0 ) );
        final IRubyObject endIndex = result.callMethod( "[]", ruby.newFixnum( 1 ) );
        assertEquals( 10, beginIndex.convertToInteger().getLongValue() );
        assertEquals( 14, endIndex.convertToInteger().getLongValue() );
    }

    public final void testRubyFindAt() throws IOException
    {
        final String fileName = "TestCode_FindAt.rb";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenRuby tokenRuby = RecognizedTokenRuby.newInstance( "TEST_RUBY_FIND_AT", script );
        assertFalse( tokenRuby.isFoundAt( "test def me", 0 ) );

        assertTrue( tokenRuby.isFoundAt( "test def me", 5 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 8, tokenRuby.getTokenEnd() );

        assertFalse( tokenRuby.isFoundAt( "test def me", 6 ) );

        assertTrue( tokenRuby.isFoundIn( "test def me", 0, 10 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 8, tokenRuby.getTokenEnd() );
    }

    public final void testOPERATOR() throws IOException
    {
        final String fileName = "TestCode_OPERATORS.rb";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenRuby tokenRuby = RecognizedTokenRuby.newInstance( "TEST_OPERATOR", script );
        assertFalse( tokenRuby.isFoundAt( "test + me", 0 ) );

        assertTrue( tokenRuby.isFoundAt( "test + me", 5 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 6, tokenRuby.getTokenEnd() );

        assertFalse( tokenRuby.isFoundAt( "test + me", 6 ) );

        assertTrue( tokenRuby.isFoundIn( "test + me", 0, 10 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 6, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "test me >>>", 0, 10 ) );
        assertEquals( 8, tokenRuby.getTokenStart() );
        assertEquals( 11, tokenRuby.getTokenEnd() );
    }

    public final void testKEYWORDS() throws IOException
    {
        final String fileName = "TestCode_KEYWORDS.rb";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenRuby tokenRuby = RecognizedTokenRuby.newInstance( "TEST_KEYWORDS", script );
        assertFalse( tokenRuby.isFoundAt( "test def me", 0 ) );

        assertTrue( tokenRuby.isFoundAt( "test def me", 5 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 8, tokenRuby.getTokenEnd() );

        assertFalse( tokenRuby.isFoundAt( "test def me", 6 ) );

        assertTrue( tokenRuby.isFoundIn( "test def me", 0, 10 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 8, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "test me def", 0, 10 ) );
        assertEquals( 8, tokenRuby.getTokenStart() );
        assertEquals( 11, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "def me something", 0, 10 ) );
        assertEquals( 0, tokenRuby.getTokenStart() );
        assertEquals( 3, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "def_bad me def something", 0, 20 ) );
        assertEquals( 11, tokenRuby.getTokenStart() );
        assertEquals( 14, tokenRuby.getTokenEnd() );
    }

    public final void testSPECIAL_QUOTED_STRINGS() throws IOException
    {
        final String fileName = "TestCode_SPECIAL_QUOTED_STRINGS.rb";
        final String script = Utilities.readIntoString( this, fileName );

        final RecognizedTokenRuby tokenRuby = RecognizedTokenRuby.newInstance( "SPECIAL_QUOTED_STRINGS", script );

        assertTrue( tokenRuby.isFoundIn( "test %Q{toast} me", 0, 20 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 14, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "test %Q{t\\}t} me", 0, 20 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 13, tokenRuby.getTokenEnd() );

        assertTrue( tokenRuby.isFoundIn( "test %Q{t\\}t\\} me!", 0, 30 ) );
        assertEquals( 5, tokenRuby.getTokenStart() );
        assertEquals( 20, tokenRuby.getTokenEnd() );
    }

    // Implementation

    private final RecognizedTokenRuby createToken( final String aResourceName ) throws IOException
    {
        final String script = Utilities.readIntoString( this, aResourceName );
        return RecognizedTokenRuby.newInstance( "TOAST", script );
    }

    private final IRubyObject callDelimiter( final String aString )
    {
        return callMethod( "delimiter", aString );
    }

    private final IRubyObject callFindIn( final String aString )
    {
        return callMethod( "find_in", aString );
    }

    private final IRubyObject callMethod( final String aMethod, final String aString )
    {
        final IRuby ruby = loadRuby();
        final RubyString input = ruby.newString( aString );
        final RubyFixnum start_index = ruby.newFixnum( 0 );
        final RubyFixnum end_index = ruby.newFixnum( 30 );

        final IRubyObject topSelf = ruby.getTopSelf();
        return topSelf.callMethod( aMethod, new IRubyObject[]{input, start_index, end_index} );
    }

    private final IRuby loadRuby()
    {
        final InputStreamReader reader = new InputStreamReader( getClass().getResourceAsStream( "TestCode_TokenReal.rb" ) );
        final IRuby ruby = Ruby.getDefaultInstance();
        ruby.eval( ruby.parse( reader, "Toast" ) );
        return ruby;
    }
}
