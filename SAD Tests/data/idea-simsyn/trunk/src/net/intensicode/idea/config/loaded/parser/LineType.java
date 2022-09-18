package net.intensicode.idea.config.loaded.parser;

import java.util.regex.Matcher;



/**
 * TODO: Describe this!
 */
final class LineType
{
    final int line_type;

    final Matcher matcher;



    LineType( final int aLineType, final Matcher aMatcher )
    {
        line_type = aLineType;
        matcher = aMatcher;
    }

    final boolean matches( final String aLine )
    {
        return matcher.reset( aLine ).matches();
    }
}
