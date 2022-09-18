package net.intensicode.idea.system.production;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.production.ProductionOptionsFolder;
import net.intensicode.idea.system.production.ProductionResourceLoader;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.OptionsFolder;
import net.intensicode.idea.system.ResourceLoader;
import net.intensicode.idea.system.SystemErrorHandler;



/**
 * TODO: Describe this!
 */
public final class ProductionSystemContext implements SystemContext
{
    public ProductionSystemContext()
    {
    }

    // From SystemContext

    public final OptionsFolder getOptionsFolder()
    {
        return new ProductionOptionsFolder();
    }

    public final ResourceLoader getResourceLoader()
    {
        return new ProductionResourceLoader();
    }

    public final SystemErrorHandler getErrorHandler()
    {
        return new ProductionSystemErrorHandler();
    }

    public final FileTypeManager getFileTypeManager()
    {
        return FileTypeManager.getInstance();
    }

    public final TextAttributesKey createTextAttributesKey( final String aTokenID, final TextAttributes aAttributes )
    {
        return TextAttributesKey.createTextAttributesKey( aTokenID, aAttributes );
    }
}
