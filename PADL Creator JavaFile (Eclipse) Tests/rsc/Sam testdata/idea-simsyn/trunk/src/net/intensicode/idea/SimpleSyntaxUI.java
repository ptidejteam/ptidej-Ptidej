package net.intensicode.idea;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import net.intensicode.idea.util.LoggerFactory;
import net.intensicode.idea.system.SystemContext;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.Nullable;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListDataListener;



/**
 * TODO: Describe this!
 */
public final class SimpleSyntaxUI implements Configurable, ComboBoxModel
{
    public SimpleSyntaxUI( final SystemContext aSystemContext, final ArrayList<SimpleSyntaxInstance> aInstances )
    {
        mySystemContext = aSystemContext;
        myInstances = aInstances;
    }

    // From Configurable

    public final String getDisplayName()
    {
        return "SimpleSyntax";
    }

    @Nullable
    @NonNls
    public final String getHelpTopic()
    {
        return null;
    }

    public final Icon getIcon()
    {
//        return mySystemContext.loadIcon( "SimpleSyntax.png" );
        return null;
    }

    // From UnnamedConfigurable

    public final void apply() throws ConfigurationException
    {
        LOG.info( "apply" );
    }

    public final JComponent createComponent()
    {
        LOG.info( "createComponent" );

        myEditorPane = new JEditorPane();
        myEditorPane.setEnabled( false );
        myEditorPane.setPreferredSize( new Dimension( 640, 480 ) );

        final JComboBox chooser = new JComboBox( this );
        chooser.setEditable( false );

        final JPanel panel = new JPanel( new BorderLayout() );
        panel.add( chooser, BorderLayout.NORTH );
        panel.add( myEditorPane, BorderLayout.CENTER );

        return panel;
    }

    public final void disposeUIResources()
    {
        LOG.info( "disposeUIResources" );
    }

    public final boolean isModified()
    {
        return false;
    }

    public final void reset()
    {
        LOG.info( "reset" );
    }

    // From ComboBoxModel

    public final Object getSelectedItem()
    {
        LOG.info( "getSelectedItem " + mySelectedItem );
        return mySelectedItem;
    }

    public final void setSelectedItem( final Object aItem )
    {
        LOG.info( "setSelectedItem " + aItem );
        mySelectedItem = aItem;
    }

    // From ListModel

    public final void addListDataListener( final ListDataListener aListener )
    {
        LOG.info( "addListDataListener " + aListener );
        myListeners.add( aListener );
    }

    public final Object getElementAt( final int index )
    {
        LOG.info( "getElementAt " + index );
        return myInstances.get( index );
    }

    public final int getSize()
    {
        return myInstances.size();
    }

    public final void removeListDataListener( final ListDataListener aListener )
    {
        LOG.info( "removeListDataListener " + aListener );
        myListeners.remove( aListener );
    }



    private Object mySelectedItem;

    private JEditorPane myEditorPane;

    private final SystemContext mySystemContext;

    private final ArrayList<SimpleSyntaxInstance> myInstances;

    private final ArrayList<ListDataListener> myListeners = new ArrayList<ListDataListener>();

    private static final Logger LOG = LoggerFactory.getLogger();
}
