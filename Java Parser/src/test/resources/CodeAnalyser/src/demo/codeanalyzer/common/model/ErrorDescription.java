package demo.codeanalyzer.common.model;

/**
 * Stores details of problems found in the code
 * @author Deepa Sobhana, Seema Richard
 */
public class ErrorDescription {
    
    private String errorMessages;
    private Location errorLocation;

    public Location getErrorLocation() {
        return errorLocation;
    }

    public void setErrorLocation(Location errorLocation) {
        this.errorLocation = errorLocation;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

}
