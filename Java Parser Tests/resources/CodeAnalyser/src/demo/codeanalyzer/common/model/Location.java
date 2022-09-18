package demo.codeanalyzer.common.model;

/**
 * Stores location information of main elements of java class
 * @author Deepa Sobhana, Seema Richard
 */
public interface Location {

    int getStartOffset();

    int getEndOffset();
    
    long getLineNumber();
}
