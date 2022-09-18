package gr.uom.java.pattern;

public class PatternDescriptor extends MatrixContainer {
    private int numberOfHierarchies;
    //size must be equal to pattern roles
    private int[] divisorArray;
    private String methodRoleName;
    private String fieldRoleName;

    public PatternDescriptor() {
        super();
    }
    
    public void setNumberOfHierarchies(int n) {
		this.numberOfHierarchies = n;
	}

    public void setDivisorArray(int[] array) {
        divisorArray = array;
    }

    public void setMethodRoleName(String methodRoleName) {
		this.methodRoleName = methodRoleName;
	}

	public void setFieldRoleName(String fieldRoleName) {
		this.fieldRoleName = fieldRoleName;
	}

	public int getDivisor(int pos) {
        if(divisorArray != null)
            return divisorArray[pos];
        return 0;
    }

    public int getNumberOfHierarchies() {
		return this.numberOfHierarchies;
	}

	public String getMethodRoleName() {
		return methodRoleName;
	}

	public String getFieldRoleName() {
		return fieldRoleName;
	}
}
