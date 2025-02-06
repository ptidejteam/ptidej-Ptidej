package ca.uqam.latece.aspects.extractor.lattice.model.impl;

import java.util.Set;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;

import ca.uqam.latece.aspects.extractor.input.RelationBuilder;
import ca.uqam.latece.aspects.extractor.input.impl.ReverseInheritanceRelationBuilder;

public class ReverseInheritanceRelation extends RelationImpl {
	
	
	public ReverseInheritanceRelation(RelationBuilder aBuilder){
		super(aBuilder);
	}
	
	@Override
	public Set<Object> getAllImages() {
		return (Set<Object>)((ReverseInheritanceRelationBuilder)getRelationBuilder()).getAllMethods();
	}
	
	/**
	 * prints image objects. Subclasses can override this
	 * @param obj
	 * @return
	 */
	protected String printImageObject(Object obj) {
		IMethod method = (IMethod)obj;
		try {
			return Signature.toString(method.getSignature(),method.getElementName(),method.getParameterNames(),true,true);
		} catch (JavaModelException e) {
			e.printStackTrace();
			return "UNPRINTABLE METHOD SIGNATURE(Java Model Exception)";
		}
	}

	/**
	 * prints domain objects. Subclasses can override this
	 * @param key
	 * @return
	 */
	protected String printDomainObject(Object key) {
		try {
			return ((IType) key).getFullyQualifiedParameterizedName();
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "UNIDENTIFIED_TYPE(JavaModelException)";
		}
	}

}
