package groovy.lang;

/**
 * TODO: Describe this!
 */
public interface GroovyRecognizer
{
    GroovySegment find_at( CharSequence aInput, int aOffset );

    GroovySegment find_in( CharSequence aInput, int aStart, int aEnd );
}
