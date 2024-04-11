package net.intensicode.idea;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.ConfigurationException;
import net.intensicode.idea.config.FileTypeConfiguration;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.config.loaded.LoadedConfiguration;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.production.ProductionSystemContext;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.util.LoggerFactory;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;



/**
 * TODO: Describe this!
 */
public final class SimpleSyntax implements ApplicationComponent/*, Configurable*/
{
    public SimpleSyntax()
    {
    }

    // From ApplicationComponent

    public final void initComponent()
    {
        if ( initialize() == false ) return;

        ApplicationManager.getApplication().runWriteAction( new Runnable()
        {
            public final void run()
            {
                for ( final SimpleSyntaxInstance instance : myInstances )
                {
                    instance.init();
                }
            }
        } );
    }

    public final void disposeComponent()
    {
        if ( myInitStatus == false ) return;

        ApplicationManager.getApplication().runWriteAction( new Runnable()
        {
            public final void run()
            {
                if ( myInitStatus == false ) return;

                for ( final SimpleSyntaxInstance instance : myInstances )
                {
                    instance.dispose();
                }
                mySystemContext.getErrorHandler().forgetConfirmationAnswers();

                myInstances.clear();
                myInitStatus = false;
            }
        } );
    }

    public final String getComponentName()
    {
        return "net.intensicode.idea.SimpleSyntax";
    }

    // From Configurable

    public final String getDisplayName()
    {
        return myUI.getDisplayName();
    }

    @Nullable
    @NonNls
    public final String getHelpTopic()
    {
        return myUI.getHelpTopic();
    }

    public final Icon getIcon()
    {
        return myUI.getIcon();
    }

    // From UnnamedConfigurable

    public final void apply() throws ConfigurationException
    {
        myUI.apply();
    }

    public final JComponent createComponent()
    {
        return myUI.createComponent();
    }

    public final void disposeUIResources()
    {
        myUI.disposeUIResources();
    }

    public final boolean isModified()
    {
        return myUI.isModified();
    }

    public final void reset()
    {
        myUI.reset();
    }

    // Implementation

    private synchronized final boolean initialize()
    {
        if ( myInitStatus ) return true;
        try
        {
            final ArrayList<InstanceConfiguration> configurations = loadConfigurations();
            for ( final InstanceConfiguration config : configurations )
            {
                myInstances.add( new SimpleSyntaxInstance( mySystemContext, config ) );
            }
            return myInitStatus = true;
        }
        catch ( final Throwable t )
        {
            LOG.error( getComponentName(), t );
            return myInitStatus = false;
        }
    }

    private final ArrayList<InstanceConfiguration>  loadConfigurations()
    {
        final OptionsFolder optionsFolder = mySystemContext.getOptionsFolder();
        final String[] fileNames = optionsFolder.findConfigurations();

        final ArrayList<InstanceConfiguration> configurations = new ArrayList<InstanceConfiguration>();
        for ( final String fileName : fileNames )
        {
            LOG.info( "Reading configuration file " + fileName );

            final InstanceConfiguration config = LoadedConfiguration.tryLoading( optionsFolder, fileName );
            if ( config == null )
            {
                LOG.info( "Failed reading configuration from " + fileName );
                continue;
            }
            if ( isAlreadyDefined( config, configurations ) )
            {
                LOG.info( "Ignoring duplicate configuration for " + config.getName() );
                continue;
            }

            LOG.info( "Adding configuration for " + config.getName() );
            configurations.add( config );
        }
        return configurations;
    }

    private final boolean isAlreadyDefined( final InstanceConfiguration aNewConfig, final ArrayList<InstanceConfiguration> aResult )
    {
        for ( final InstanceConfiguration config : aResult )
        {
            if ( aNewConfig.getName().equals( config.getName() ) ) return true;

            final FileTypeConfiguration newFileType = aNewConfig.getFileTypeConfiguration();
            final FileTypeConfiguration oldFileType = config.getFileTypeConfiguration();
            if ( newFileType.getDefaultExtension().equals( oldFileType.getDefaultExtension() ) )
            {
                return true;
            }
        }
        return false;
    }



    private boolean myInitStatus = false;

    private final SystemContext mySystemContext = new ProductionSystemContext();

    private final ArrayList<SimpleSyntaxInstance> myInstances = new ArrayList<SimpleSyntaxInstance>();

    private final SimpleSyntaxUI myUI = new SimpleSyntaxUI( mySystemContext, myInstances );

    private static final Logger LOG = LoggerFactory.getLogger();
}
