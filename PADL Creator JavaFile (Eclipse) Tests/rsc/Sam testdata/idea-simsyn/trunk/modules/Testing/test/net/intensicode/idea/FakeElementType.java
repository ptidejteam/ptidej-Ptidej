package net.intensicode.idea;

import com.intellij.lang.Language;
import com.intellij.psi.tree.IElementType;



/**
 * TODO: Describe this!
 */
public final class FakeElementType extends IElementType
{
    public FakeElementType( final String aID, final Language aLanguage )
    {
        super( aID, aLanguage, false );
    }
}
