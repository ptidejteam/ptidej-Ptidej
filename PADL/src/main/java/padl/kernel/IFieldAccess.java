package padl.kernel;

public interface IFieldAccess extends IConstituentOfOperation {
	public int getCardinality();
	public IField getField();
	public IFirstClassEntity getFieldDeclaringEntity();
}
