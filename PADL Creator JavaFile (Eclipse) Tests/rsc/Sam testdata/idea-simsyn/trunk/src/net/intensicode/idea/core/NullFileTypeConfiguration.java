package net.intensicode.idea.core;

import net.intensicode.idea.config.FileTypeConfiguration;

import javax.swing.Icon;



/**
 * TODO: Describe this!
 */
public final class NullFileTypeConfiguration implements FileTypeConfiguration
{
    public static final NullFileTypeConfiguration INSTANCE = new NullFileTypeConfiguration();

    public final String getDefaultExtension()
    {
        return NULL_STRING;
    }

    public final String[] getExtensions()
    {
        return NO_EXTENSIONS;
    }

    public final Icon getIcon()
    {
        return null;
    }

    private NullFileTypeConfiguration()
    {
    }

    private static final String NULL_STRING = "NULL";

    private static final String[] NO_EXTENSIONS = new String[0];
}
