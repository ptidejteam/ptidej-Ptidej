package net.intensicode.idea.util;

import groovy.lang.GroovyShell;

import java.lang.reflect.Constructor;



/**
 * TODO: Describe this!
 */
public final class DynamicClassHelper
{
    public static final Object newInstance( final String aSubclassName, final Class aClass, final Object... aParameters )
    {
        final String className = aClass.getName();

        final StringBuilder args = new StringBuilder();
        for ( int idx = 0; idx < aParameters.length; idx++ )
        {
            if ( args.length() > 0 ) args.append( ',' );
            args.append( "arg" + idx );
        }

        final StringBuilder scriptBuilder = new StringBuilder();
        //scriptBuilder.append( "package net.intensicode.idea.core;\n" );
        //scriptBuilder.append( "\n" );
        scriptBuilder.append( "import net.intensicode.idea.core.ConfigurableLanguage;\n" );
        scriptBuilder.append( "\n" );
        scriptBuilder.append( "class " + aSubclassName + " extends " + className + " {\n" );
        scriptBuilder.append( "\n" );
        scriptBuilder.append( "  " + aSubclassName + "( " + args + " ) {\n" );
        scriptBuilder.append( "    super( " + args + " );\n" );
        scriptBuilder.append( "  }\n" );
        scriptBuilder.append( "\n" );
        scriptBuilder.append( "}\n" );
        scriptBuilder.append( "return " + aSubclassName + ".class;" );

        try
        {
            final ClassLoader loader = GroovyShell.class.getClassLoader();
            final String script = scriptBuilder.toString();
            final Class newClass = ( Class ) new GroovyShell( loader ).evaluate( script );
            final Constructor constructor = newClass.getConstructors()[ 0 ];
            return constructor.newInstance( aParameters );
        }
        catch ( final Throwable t )
        {
            throw new RuntimeException( t );
        }
    }
}
