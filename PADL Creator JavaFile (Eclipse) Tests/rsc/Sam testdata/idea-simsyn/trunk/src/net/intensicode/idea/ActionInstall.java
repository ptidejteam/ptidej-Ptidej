package net.intensicode.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.util.ReaderUtils;
import net.intensicode.idea.system.*;
import net.intensicode.idea.system.production.ProductionSystemContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * TODO: Describe this!
 */
public final class ActionInstall extends AnAction
{
    public ActionInstall()
    {
        final ProductionSystemContext context = new ProductionSystemContext();
        myErrorHandler = context.getErrorHandler();
        myResourceLoader = context.getResourceLoader();
        myOptionsFolder = context.getOptionsFolder();

        addMatcher( "Icon:\\s*(.+)" );
        addMatcher( "ExampleCode:\\s*(.+)" );
        addMatcher( "\\w+\\s+[A-Z_]+\\s*=>\\s*(.+)" );
    }

    // From AnAction

    public final void actionPerformed( final AnActionEvent aEvent )
    {
        try
        {
            copyResource( SIMPLESYNTAX_RUBY_CONFIG );

            final Reader config = myResourceLoader.read( SIMPLESYNTAX_RUBY_CONFIG );
            for ( final String line : ReaderUtils.readLines( config ) )
            {
                final String fileName = extractFileName( line );
                if ( fileName != null ) copyResource( fileName );
            }
        }
        catch ( final Throwable t )
        {
            myErrorHandler.onSimpleSyntaxInstallFailed( t );
        }

        myAllConfirmedFlag = false;
    }

    private final String extractFileName( final String aLine )
    {
        for ( final Matcher matcher : myMatchers )
        {
            if ( matcher.reset( aLine ).matches() == false ) continue;

            final String fileName = matcher.group( 1 );
            if ( myResourceLoader.isAvailable( fileName ) ) return fileName;
        }
        return null;
    }

    // Implementation

    private final void addMatcher( final String aPattern )
    {
        myMatchers.add( Pattern.compile( aPattern ).matcher( EMPTY_INPUT ) );
    }

    private final void copyResource( final String aResourceName ) throws IOException
    {
        LOG.info( "Installing " + aResourceName );
        final InputStream stream = myResourceLoader.stream( aResourceName );
        try
        {
            final boolean writeConfirmed = confirmWrite( aResourceName );
            if ( writeConfirmed ) myOptionsFolder.writeFileFromStream( aResourceName, stream );
        }
        finally
        {
            stream.close();
        }
    }

    private final boolean confirmWrite( final String aResourceName ) throws IOException
    {
        if ( myAllConfirmedFlag ) return true;
        if ( myOptionsFolder.fileExists( aResourceName ) == false ) return true;

        final Confirmation confirmation = myErrorHandler.onFileReplaceConfirmation( aResourceName );
        if ( confirmation == Confirmation.ALL ) return myAllConfirmedFlag = true;
        if ( confirmation == Confirmation.YES ) return true;
        if ( confirmation == Confirmation.NO ) return false;
        if ( confirmation == Confirmation.CANCEL ) throw new IOException( "Installation cancelled" );

        throw new RuntimeException( "NYI" );
    }



    private boolean myAllConfirmedFlag = false;

    private final OptionsFolder myOptionsFolder;

    private final ResourceLoader myResourceLoader;

    private final SystemErrorHandler myErrorHandler;

    private final ArrayList<Matcher> myMatchers = new ArrayList<Matcher>();


    private static final String EMPTY_INPUT = "";

    private static final String SIMPLESYNTAX_RUBY_CONFIG = "Ruby.config";


    private static final Logger LOG = LoggerFactory.getLogger();
}
