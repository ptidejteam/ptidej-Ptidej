package net.intensicode.idea.config.loaded.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * TODO: Describe this!
 */
public final class ConfigurationParser implements MatchedLine
{
    public static final int IGNORE = 0;

    public static final int COMMENT = 1;

    public static final int PROPERTY = 2;

    public static final int SYNTAX_RULE = 3;

    public static final int ASSIGNMENT = 4;



    public ConfigurationParser()
    {
        addLineType( COMMENT, "(?m)^[ \t]*#.*$" );
        addLineType( PROPERTY, "[ \t]*([a-zA-Z_\\.]+)[ \t]*[:=][ \t]*(.*)" );
        addLineType( SYNTAX_RULE, "[ \t]*([a-z_]+)[ \t]+([A-Z_]*)[ \t]*=>[ \t]*(.+)" );
        addLineType( ASSIGNMENT, "[ \t]*([a-z_]+)\\[[ \t]*([A-Z_]*)[ \t]*\\][ \t]*=[ \t]*(.+)" );
    }

    public final void addConsumer( final LineConsumer aConsumer )
    {
        myConsumers.add( aConsumer );
    }

    public final void consume( final List<String> aLines )
    {
        for ( final String line : aLines )
        {
            final int lineType = consume( line );
            for ( final LineConsumer consumer : myConsumers )
            {
                consumer.consume( lineType, this );
            }
        }
    }

    public final int consume( final String aLine )
    {
        for ( final LineType lineType : myLineTypes )
        {
            if ( lineType.matches( aLine ) )
            {
                myLastMatch = lineType.matcher;
                return lineType.line_type;
            }
        }
        myLastMatch = null;
        return IGNORE;
    }

    // From MatchedLine

    public final String getValue( final int aValueIndex )
    {
        if ( myLastMatch == null ) throw new IllegalStateException( "No valid match available" );
        return myLastMatch.group( aValueIndex ).trim();
    }

    // Implementation

    private final void addLineType( final int aLineType, final String aRegEx )
    {
        final Matcher matcher = Pattern.compile( aRegEx ).matcher( EMPTY_STRING );
        myLineTypes.add( new LineType( aLineType, matcher ) );
    }



    private Matcher myLastMatch = null;

    private static final String EMPTY_STRING = "";

    private final ArrayList<LineType> myLineTypes = new ArrayList<LineType>();

    private final ArrayList<LineConsumer> myConsumers = new ArrayList<LineConsumer>();
}
