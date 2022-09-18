package net.intensicode.idea.config;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.psi.tree.IElementType;
import net.intensicode.idea.syntax.RecognizedToken;

import java.util.List;



/**
 * TODO: Describe this!
 */
public interface SyntaxConfiguration
{
    List<RecognizedToken> getRecognizedTokens();

    List<AttributesDescriptor> getAttributesDescriptors();

    TextAttributesKey[] getTokenHighlights( IElementType tokenType );
}
