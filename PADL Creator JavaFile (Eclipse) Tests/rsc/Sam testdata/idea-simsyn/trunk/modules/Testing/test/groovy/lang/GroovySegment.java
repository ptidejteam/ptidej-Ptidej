package groovy.lang;

/**
 * TODO: Describe this!
 */
public final class GroovySegment
{
    public static final GroovySegment NOT_FOUND = new GroovySegment();

    public int start;

    public int end;

    public GroovySegment( final int aStart, final int aEnd )
    {
        start = aStart;
        end = aEnd;
    }

    // From Object

    public final boolean equals( final Object aObject )
    {
        final GroovySegment that = ( GroovySegment ) aObject;
        return start == that.start && end == that.end;
    }

    public final String toString()
    {
        return start + ".." + end;
    }

    // Implementation

    private GroovySegment()
    {
    }
}
