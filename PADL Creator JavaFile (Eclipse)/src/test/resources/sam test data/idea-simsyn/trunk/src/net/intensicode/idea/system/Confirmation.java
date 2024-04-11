package net.intensicode.idea.system;

/**
 * TODO: Describe this!
 */
public final class Confirmation
{
    public static final Confirmation ALL = new Confirmation( true );

    public static final Confirmation YES = new Confirmation( true );

    public static final Confirmation NO = new Confirmation( false );

    public static final Confirmation NONE = new Confirmation( false );

    public static final Confirmation CANCEL = new Confirmation( false );

    public final boolean isYes()
    {
        return myIsYes;
    }

    public final boolean isNo()
    {
        return !isYes();
    }

    // Implementation

    private Confirmation( final boolean aIsYes )
    {
        myIsYes = aIsYes;
    }

    private final boolean myIsYes;
}
