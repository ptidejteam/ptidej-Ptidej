package net.intensicode.idea.core;

import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import net.intensicode.idea.config.InstanceConfiguration;
import net.intensicode.idea.util.DynamicClassHelper;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;



/**
 * Adapter between IDEA FileType objects and our InstanceConfiguration/FileTypeConfiguration.
 * Has to be used through the ConfigurableFileTypeBuilder to take care of unregistering previous
 * extensions/filetypes.
 */
public class ConfigurableFileType implements FileType
{
    public /*protected*/ static final ConfigurableFileType getOrCreate( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        final String name = aConfiguration.getName();
        final FileTypeManager manager = FileTypeManager.getInstance();
        for ( final FileType fileType : manager.getRegisteredFileTypes() )
        {
            if ( fileType.getName().equals( name ) == false ) continue;
            if ( fileType instanceof ConfigurableFileType == false ) continue;

            final ConfigurableFileType oldFileType = ( ConfigurableFileType ) fileType;
            oldFileType.reset( aConfiguration, aLanguage );
            return oldFileType;
        }

        // TODO: Should we have an own registry here? So far IDEA seems to be OK with the current solution..

        final String className = "ConfigurableFileType" + aConfiguration.getName();
        final Class clazz = ConfigurableFileType.class;
        return ( ConfigurableFileType ) DynamicClassHelper.newInstance( className, clazz, aConfiguration, aLanguage );
    }

    public final void reset( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        myConfiguration = aConfiguration != null ? aConfiguration : NullInstanceConfiguration.INSTANCE;
        myLanguage = aLanguage != null ? aLanguage : Language.ANY;
    }

    // From FileType

    @NotNull
    public final Language getLanguage()
    {
        return myLanguage;
    }

    @NotNull
    @NonNls
    public final String getName()
    {
        return myConfiguration.getName();
    }

    @Nullable
    public final Icon getIcon()
    {
        return myConfiguration.getIcon();
    }

    @NotNull
    public final String getDescription()
    {
        return myConfiguration.getDescription();
    }

    @NotNull
    @NonNls
    public final String getDefaultExtension()
    {
        return myConfiguration.getFileTypeConfiguration().getDefaultExtension();
    }

    @Nullable
    public final SyntaxHighlighter getHighlighter( final @Nullable Project aProject, final VirtualFile aVirtualFile )
    {
        return myLanguage.getSyntaxHighlighter( aProject, aVirtualFile );
    }

    @Nullable
    public final StructureViewBuilder getStructureViewBuilder( final @NotNull VirtualFile aFile, final @NotNull Project aProject )
    {
        final PsiFile psiFile = PsiManager.getInstance( aProject ).findFile( aFile );
        return psiFile == null ? null : myLanguage.getStructureViewBuilder( psiFile );
    }

    public final String getCharset( final VirtualFile file )
    {
        return null;
    }

    public final boolean isBinary()
    {
        return false;
    }

    public final boolean isReadOnly()
    {
        return false;
    }

    // Protected Interface

    public /*protected*/ ConfigurableFileType( final InstanceConfiguration aConfiguration, final Language aLanguage )
    {
        reset( aConfiguration, aLanguage );
    }



    private Language myLanguage;

    private InstanceConfiguration myConfiguration;
}
