
package demo.codeanalyzer.common.model;

/**
 * Stores location information of main elements of java class
 * @author Deepa Sobhana, Seema Richard
 */
public class LocationInfo implements Location {
    
    private int startOffset;
    private int endOffset;
    private long lineNumber;

    public int getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(int endOffset) {
        this.endOffset = endOffset;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(int startOffset) {
        this.startOffset = startOffset;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
        this.lineNumber = lineNumber;
    }
    
}
