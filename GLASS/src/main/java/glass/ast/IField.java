package glass.ast;

public interface IField {

	public boolean hasUnresolvedSignature();
	public String getTypeSignature();
	public String getElementName();
	public boolean isClassType();
	
	/**
	 * Returns the place (i.e., the method) where the field was found
	 * @return name of the method where the field is defined
	 */
	public String getLocation();
}
