package net.intensicode.idea.core;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.FileTypeManager;
import net.intensicode.idea.system.SystemErrorHandler;
import net.intensicode.idea.system.SystemContext;
import net.intensicode.idea.system.ResourceLoader;
import net.intensicode.idea.system.OptionsFolder;



/**
 * TODO: Describe this!
 */
public final class FakeSystemContext implements SystemContext
{
    public FakeSystemContext( final Object aReferenceObject )
    {
        myReferenceObject = aReferenceObject;
    }

    // From SystemContext

    public OptionsFolder getOptionsFolder()
    {
        return new FakeOptionsFolder( myReferenceObject );
    }

    public ResourceLoader getResourceLoader()
    {
        return new FakeResourceLoader( myReferenceObject );
    }

    public SystemErrorHandler getErrorHandler()
    {
        return new FakeSystemErrorHandler();
    }

    public TextAttributesKey createTextAttributesKey( String aTokenID, TextAttributes aAttributes )
    {
        return TextAttributesKey.find( aTokenID );
    }

    public FileTypeManager getFileTypeManager()
    {
        throw new RuntimeException( "NYI" );
    }

    private final Object myReferenceObject;
}
