package ffeatureextractor.actions;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;

public class DelegationRecord {

	/**
	 * mainType is the delegator type
	 */
	private IType mainType;
	
	/**
	 * the full domain interface of the delegator
	 */
	private IMethod[] mainTypeDomainInterface;
	
	/**
	 * the name of the delegate
	 */
	private String fieldName;
	
	/**
	 * the type of the delegate
	 */
	private IType fieldType;
	
	/**
	 * the domain interface of the delegate
	 */
	private IMethod[] fieldDomainInterface;
	
	/**
	 * the percentage of the coverage of the domain interface of the delegate
	 * by the 'delegator' (mainType)
	 */
	private float coveragePercentage;
	
	/**
	 * whether this is a perfect case of delegation. A perfect case is when the main type
	 * extends or implements the type of the field. This cannot be judged from the domain interfaces,
	 * but from the type definitions (mType and fieldType) 
	 */
	private boolean perfectDelegationCase;
	
	/**
	 * public constructor. The attributes coveragePercentage and perfectDelegationCase can be set
	 * separately.
	 * @param mType
	 * @param typeDomInterface
	 * @param fName
	 * @param fType
	 * @param fDomInterface
	 */
	public DelegationRecord(IType mType, IMethod[] typeDomInterface, String fName, IType fType, IMethod[] fDomInterface){
		mainType = mType;
		mainTypeDomainInterface = typeDomInterface;
		fieldName = fName;
		fieldType = fType;
		fieldDomainInterface = fDomInterface;
		
	}

	/**
	 * @return the coveragePercentage
	 */
	public float getCoveragePercentage() {
		return coveragePercentage;
	}

	/**
	 * @param coveragePercentage the coveragePercentage to set
	 */
	public void setCoveragePercentage(float coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}

	/**
	 * @return the perfectDelegationCase
	 */
	public boolean isPerfectDelegationCase() {
		return perfectDelegationCase;
	}

	/**
	 * @param perfectDelegationCase the perfectDelegationCase to set
	 */
	public void setPerfectDelegationCase(boolean perfectDelegationCase) {
		this.perfectDelegationCase = perfectDelegationCase;
	}

	/**
	 * @return the mainType
	 */
	public IType getMainType() {
		return mainType;
	}

	/**
	 * @return the mainTypeDomainInterface
	 */
	public IMethod[] getMainTypeDomainInterface() {
		return mainTypeDomainInterface;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return the fieldType
	 */
	public IType getFieldType() {
		return fieldType;
	}

	/**
	 * @return the fieldDomainInterface
	 */
	public IMethod[] getFieldDomainInterface() {
		return fieldDomainInterface;
	}
	
	public String toString() {
		String printable = "Delegation candidate with percentage coverage:" + coveragePercentage +"\n";
		printable = printable + "\tClass:" + mainType.getFullyQualifiedName() + "\n";
		printable = printable + "\tAttribute: "+ fieldName + "with type: " + fieldType.getFullyQualifiedName()+ "\n";
		printable = printable + "\t\t and domain interface:";
		for (int i = 0; i < fieldDomainInterface.length;i++){
			printable = printable + fieldDomainInterface[i] + ", ";
		}
		return printable;
	}
	
	
}
