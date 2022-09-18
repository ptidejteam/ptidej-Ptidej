package net.intensicode.idea.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;



/**
 * TODO: Describe this!
 */
public final class IconFactory
{
    public static final Icon getIcon( final String aResourcePath )
    {
        try
        {
            final ImageIcon icon = new ImageIcon( aResourcePath );
            if ( icon != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0 ) return icon;
        }
        catch ( final Throwable t )
        {
        }
        try
        {
            final URL resource = IconFactory.class.getResource( aResourcePath );
            final ImageIcon icon = new ImageIcon( resource );
            if ( icon != null && icon.getIconWidth() > 0 && icon.getIconHeight() > 0 ) return icon;
        }
        catch ( final Throwable t )
        {
        }
        try
        {
            return IconLoader.getIcon( aResourcePath );
        }
        catch ( final Throwable t )
        {
        }
        LOG.info( "Failed loading icon from " + aResourcePath );
        return null;
    }

    private static final Logger LOG = LoggerFactory.getLogger();
}
