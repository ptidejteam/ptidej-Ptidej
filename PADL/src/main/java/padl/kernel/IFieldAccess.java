package padl.kernel;

public interface IFieldAccess extends IConstituentOfOperation, ICardinalityAndDimension {
	public IField getField();
	public IFirstClassEntity getFieldDeclaringEntity();
}
