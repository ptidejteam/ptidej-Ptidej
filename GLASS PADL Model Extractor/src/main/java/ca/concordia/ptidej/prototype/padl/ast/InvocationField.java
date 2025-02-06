package ca.concordia.ptidej.prototype.padl.ast;

import ca.concordia.ptidej.prototype.ast.IField;
import padl.kernel.IMethodInvocation;

public class InvocationField implements IField{
	
	private IMethodInvocation invocation;
	private String location;
	
	public InvocationField(IMethodInvocation invocation) {
		this.invocation = invocation;
	}

	@Override
	public boolean hasUnresolvedSignature() {
		return false;
	}

	@Override
	public String getTypeSignature() {
		return this.invocation.getTargetEntity().getDisplayName();
	}

	@Override
	public String getElementName() {
		return null;
	}

	@Override
	public boolean isClassType() {
		return true;
	}

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

}
