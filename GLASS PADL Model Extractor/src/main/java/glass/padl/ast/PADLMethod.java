package glass.padl.ast;

import java.util.Iterator;

import glass.ast.IMethod;
import padl.kernel.IEntity;
import padl.kernel.IParameter;
import padl.kernel.exception.ModelDeclarationException;
import util.lang.Modifier;

public class PADLMethod implements IMethod{
	
	private padl.kernel.IMethod padlMethod;
	private String signature;
	private int nbParameters;
	
	
	public PADLMethod(padl.kernel.IMethod padlMethod) {
		this.padlMethod = padlMethod;
		this.signature = createSignature();
	}
	
	private String createSignature() {
		StringBuffer signature = new StringBuffer();
		signature.append(Modifier.toString(this.padlMethod.getVisibility()));
		if (this.padlMethod.getVisibility() != 0) {
			signature.append(' ');
		}
		signature.append(this.padlMethod.getReturnType());
		signature.append(' ');
		signature.append(this.padlMethod.getName());
		signature.append('(');
		final Iterator iterator = this.padlMethod.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			this.nbParameters++;
			final IParameter parameter = (IParameter) iterator.next();
			signature.append(this.getParameterStrings(parameter));
			//signature.append(parameter.getDisplayPath());
			if (iterator.hasNext()) {
				signature.append(", ");
			}
		}
		signature.append(')');
		
		return signature.toString();
	}
	
	private String getParameterStrings(padl.kernel.IParameter parameter) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(parameter.getTypeName());
		if (parameter.getCardinality() == 2) {
			try {
				int dimension = parameter.getDimension();
				for (int i = 1; i < dimension; i++) {
					buffer.append("[]");
				}
			} catch (ModelDeclarationException e) {
				System.out.println("Parameter has no dimension!");
			}
		}
		return buffer.toString();
	}

	@Override
	public String getSignature() {
		return this.getSimpleSignature();
	}
	
	public String getSimpleSignature() {
		StringBuffer signature = new StringBuffer();
		signature.append(this.padlMethod.getReturnType());
		signature.append(' ');
		signature.append(this.padlMethod.getName());
		signature.append('(');
		final Iterator iterator = this.padlMethod.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			this.nbParameters++;
			final IParameter parameter = (IParameter) iterator.next();
			signature.append(this.getParameterStrings(parameter));
			if (iterator.hasNext()) {
				signature.append(", ");
			}
		}
		signature.append(')');
		
		return signature.toString();
	}

	@Override
	public boolean isSimilar(IMethod comparedMethod) {
		return this.getSignature().equals(comparedMethod.getSignature());
	}

	@Override
	public String getElementName() {
		return padlMethod.getDisplayName();
	}

	@Override
	public String[] getParameterNames() {
		int i = 0;
		String[] parameters = new String[nbParameters];
		final Iterator iterator = this.padlMethod.getIteratorOnConstituents(IParameter.class);
		while (iterator.hasNext()) {
			IParameter parameter = (IParameter) iterator.next();
			parameters[i] = String.valueOf(parameter.getTypeName());
			i++;
		}
		return parameters;
	}

	@Override
	public String getReturnType() {
		return padlMethod.getDisplayReturnType();
	}

	@Override
	public boolean isConstructor() {
		return false;
	}
	
	@Override
	public String toString() {
		return this.getSignature();
	}

}
