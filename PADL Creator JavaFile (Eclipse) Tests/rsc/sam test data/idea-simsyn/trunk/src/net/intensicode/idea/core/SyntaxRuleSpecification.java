package net.intensicode.idea.core;

import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.syntax.RecognizedToken;
import net.intensicode.idea.syntax.RecognizedTokenGroovy;
import net.intensicode.idea.syntax.RecognizedTokenRegEx;
import net.intensicode.idea.syntax.RecognizedTokenRuby;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.system.OptionsFolder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;



/**
 * TODO: Describe this!
 */
public final class SyntaxRuleSpecification
{
    public static final String REGEX = "regex";

    public static final String RUBY = "ruby";

    public static final String GROOVY = "groovy";

    public final String type;

    public final String id;

    public final String value;



    public static final SyntaxRuleSpecification create( final String aType, final String aId, final String aValue )
    {
        final String type = tryIdentify( aType );
        return new SyntaxRuleSpecification( type, aId, aValue );
    }

    public RecognizedToken createToken( final OptionsFolder aOptionsFolder ) throws IOException
    {
        LOG.info( "Creating token for " + id );

        if ( type == REGEX )
        {
            return new RecognizedTokenRegEx( id, value );
        }

        if ( type == RUBY )
        {
            final String script = aOptionsFolder.readFileIntoString( value );
            return RecognizedTokenRuby.newInstance( id, script );
        }

        if ( type == GROOVY )
        {
            final String script = aOptionsFolder.readFileIntoString( value );
            return RecognizedTokenGroovy.newInstance( id, script );
        }

        throw new IllegalArgumentException( "Unknown token type: " + type );
    }

    // From Object

    public final String toString()
    {
        return type + " " + id + " " + value;
    }

    // Implementation

    private static final String tryIdentify( final String aType )
    {
        for ( final Field field : SyntaxRuleSpecification.class.getDeclaredFields() )
        {
            final String typeString = getTypeString( field );
            if ( typeString == INVALID ) continue;
            if ( typeString.equalsIgnoreCase( aType ) == false ) continue;
            return typeString;
        }
        throw new IllegalArgumentException( "Unknown rule type: " + aType );
    }

    private static final String getTypeString( final Field aField )
    {
        if ( Modifier.isStatic( aField.getModifiers() ) == false ) return INVALID;
        try
        {
            return ( String ) aField.get( null );
        }
        catch ( IllegalAccessException e )
        {
            return INVALID;
        }
    }

    private SyntaxRuleSpecification( final String aType, final String aId, final String aValue )
    {
        type = aType;
        id = aId;
        value = aValue;
    }



    private static final String INVALID = new String();

    private static final Logger LOG = LoggerFactory.getLogger();
}
