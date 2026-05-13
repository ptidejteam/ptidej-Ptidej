package padl.kernel;

public interface IFieldAccess extends IConstituentOfOperation, ICardinality {
	public IField getField();
	public IFirstClassEntity getFieldDeclaringEntity();
}
