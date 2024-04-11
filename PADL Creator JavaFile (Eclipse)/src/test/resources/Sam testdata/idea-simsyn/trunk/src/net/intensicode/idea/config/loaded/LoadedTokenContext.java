package net.intensicode.idea.config.loaded;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.psi.tree.IElementType;

import java.io.Reader;
import java.io.FileNotFoundException;



/**
 * TODO: Describe this!
 */
interface LoadedTokenContext
{
    IElementType getToken( String aTokenID );

    String getDescription( String aTokenID );

    TextAttributesKey getTextAttributesKey( String aTokenID );

    boolean isVisible( String aTokenID );

    Reader readFile( String aFileName ) throws FileNotFoundException;
}
