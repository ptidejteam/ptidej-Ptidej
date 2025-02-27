package glass.ast;

public interface IMethod {
	
	public String getSignature();
	public boolean isSimilar(IMethod comparedMethod);
	public String getElementName();
	public String[] getParameterNames();
	public String getReturnType();
	public boolean isConstructor();
}
