package glass.ast;

import java.util.Collection;

public interface IProject {

	public Collection<IType> getDefinedTypes();
	/**
	 * Returns the first type (excluding secondary types) found following
	 * this project's classpath with the given fully qualified name
	 * or null if none is found.
	 * @param typeName
	 * @return First type found
	 */
	public IType findType(String typeName);
}
