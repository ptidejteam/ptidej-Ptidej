package gr.uom.java.pattern;

import java.util.List;

public class MatrixContainer {
	protected double[][] generalizationMatrix;
	protected double[][] associationMatrix;
	protected double[][] associationWithInheritanceMatrix;
	protected BehavioralData associationWithInheritanceBehavioralData;
	protected double[][] abstractMatrix;
	protected double[][] abstractMethodInvocationMatrix;
	protected double[][] similarAbstractMethodInvocationMatrix;
	protected BehavioralData similarAbstractMethodInvocationBehavioralData;
	protected double[][] similarMethodInvocationFromSiblingSubclassMatrix;
	protected BehavioralData similarMethodInvocationFromSiblingSubclassBehavioralData;
	protected double[][] fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix;
	protected BehavioralData fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData;
	protected double[][] doubleDispatchMatrix;
	protected BehavioralData doubleDispatchBehavioralData;
	protected double[][] invokedMethodInInheritedMethodMatrix;
	protected BehavioralData invokedMethodInInheritedMethodBehavioralData;

	protected List<String> classNameList;
	protected double[][] methodInvocationsMatrix;
	protected double[][] iterativeNonSimilarAbstractMethodInvocationMatrix;
	protected BehavioralData iterativeNonSimilarAbstractMethodInvocationBehavioralData;
	protected double[][] iterativeSimilarAbstractMethodInvocationMatrix;
	protected BehavioralData iterativeSimilarAbstractMethodInvocationBehavioralData;
    protected double[][] cloneInvocationMatrix;
    protected BehavioralData cloneInvocationBehavioralData;
    protected double[][] singletonMatrix;
    protected BehavioralData singletonBehavioralData;
    protected double[][] templateMethodMatrix;
    protected BehavioralData templateMethodBehavioralData;
    protected double[][] factoryMethodMatrix;
    protected BehavioralData factoryMethodBehavioralData;

    public void setGeneralizationMatrix(double[][] m) {
		this.generalizationMatrix = m;
	}
	
	public void setAssociationMatrix(double[][] m) {
		this.associationMatrix = m;
	}
	
	public void setAssociationWithInheritanceMatrix(double[][] m) {
		this.associationWithInheritanceMatrix = m;
	}

	public void setAssociationWithInheritanceBehavioralData(BehavioralData behavioralData) {
		this.associationWithInheritanceBehavioralData = behavioralData;
	}

	public void setAbstractMatrix(double[][] m) {
		this.abstractMatrix = m;
	}
	
	public void setAbstractMethodInvocationMatrix(double[][] m) {
		this.abstractMethodInvocationMatrix = m;
	}

    public void setIterativeNonSimilarAbstractMethodInvocationMatrix(double[][] m) {
		this.iterativeNonSimilarAbstractMethodInvocationMatrix = m;
	}

    public void setIterativeNonSimilarAbstractMethodInvocationBehavioralData(BehavioralData behavioralData) {
		this.iterativeNonSimilarAbstractMethodInvocationBehavioralData = behavioralData;
	}

	public void setIterativeSimilarAbstractMethodInvocationMatrix(double[][] m) {
		this.iterativeSimilarAbstractMethodInvocationMatrix = m;
	}

	public void setIterativeSimilarAbstractMethodInvocationBehavioralData(BehavioralData behavioralData) {
		this.iterativeSimilarAbstractMethodInvocationBehavioralData = behavioralData;
	}

	public void setMethodInvocationsMatrix(double[][] m) {
		this.methodInvocationsMatrix = m;
	}
	
	public void setSimilarAbstractMethodInvocationMatrix(double[][] m) {
		this.similarAbstractMethodInvocationMatrix = m;
	}
	
	public void setSimilarAbstractMethodInvocationBehavioralData(BehavioralData behavioralData) {
		this.similarAbstractMethodInvocationBehavioralData = behavioralData;
	}

	public void setSimilarMethodInvocationFromSiblingSubclassMatrix(double[][] m) {
		this.similarMethodInvocationFromSiblingSubclassMatrix = m;
	}
	
	public void setSimilarMethodInvocationFromSiblingSubclassBehavioralData(BehavioralData behavioralData) {
		this.similarMethodInvocationFromSiblingSubclassBehavioralData = behavioralData;
	}

	public void setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix(double[][] m) {
		this.fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix = m;
	}

	public void setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData(BehavioralData behavioralData) {
		this.fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData = behavioralData;
	}

	public void setDoubleDispatchMatrix(double[][] m) {
		this.doubleDispatchMatrix = m;
	}
	
	public void setDoubleDispatchBehavioralData(BehavioralData behavioralData) {
		this.doubleDispatchBehavioralData = behavioralData;
	}

	public void setInvokedMethodInInheritedMethodMatrix(double[][] m) {
		this.invokedMethodInInheritedMethodMatrix = m;
	}
	
	public void setInvokedMethodInInheritedMethodBehavioralData(BehavioralData behavioralData) {
		this.invokedMethodInInheritedMethodBehavioralData = behavioralData;
	}

	public double[][] getGeneralizationMatrix() {
		return this.generalizationMatrix;
	}
	
	public double[][] getAssociationMatrix() {
		return this.associationMatrix;
	}
	
	public double[][] getAssociationWithInheritanceMatrix() {
		return associationWithInheritanceMatrix;
	}

	public BehavioralData getAssociationWithInheritanceBehavioralData() {
		return associationWithInheritanceBehavioralData;
	}

	public double[][] getAbstractMatrix() {
		return this.abstractMatrix;
	}
	
	public double[][] getAbstractMethodInvocationMatrix() {
		return this.abstractMethodInvocationMatrix;
	}

    public double[][] getIterativeNonSimilarAbstractMethodInvocationMatrix() {
		return this.iterativeNonSimilarAbstractMethodInvocationMatrix;
	}

    public BehavioralData getIterativeNonSimilarAbstractMethodInvocationBehavioralData() {
		return iterativeNonSimilarAbstractMethodInvocationBehavioralData;
	}

	public double[][] getIterativeSimilarAbstractMethodInvocationMatrix() {
		return this.iterativeSimilarAbstractMethodInvocationMatrix;
	}

	public BehavioralData getIterativeSimilarAbstractMethodInvocationBehavioralData() {
		return iterativeSimilarAbstractMethodInvocationBehavioralData;
	}

	public double[][] getMethodInvocationsMatrix() {
		return this.methodInvocationsMatrix;
	}
	
	public double[][] getSimilarAbstractMethodInvocationMatrix() {
		return this.similarAbstractMethodInvocationMatrix;
	}

	public BehavioralData getSimilarAbstractMethodInvocationBehavioralData() {
		return similarAbstractMethodInvocationBehavioralData;
	}

	public double[][] getSimilarMethodInvocationFromSiblingSubclassMatrix() {
		return this.similarMethodInvocationFromSiblingSubclassMatrix;
	}
	
	public BehavioralData getSimilarMethodInvocationFromSiblingSubclassBehavioralData() {
		return similarMethodInvocationFromSiblingSubclassBehavioralData;
	}

	public double[][] getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix() {
		return fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix;
	}

	public BehavioralData getFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData() {
		return fieldOfSuperClassTypeIsInitializedWithSiblingClassTypeBehavioralData;
	}

	public double[][] getDoubleDispatchMatrix() {
		return this.doubleDispatchMatrix;
	}
	
	public BehavioralData getDoubleDispatchBehavioralData() {
		return doubleDispatchBehavioralData;
	}

	public double[][] getInvokedMethodInInheritedMethodMatrix() {
		return this.invokedMethodInInheritedMethodMatrix;
	}

	public BehavioralData getInvokedMethodInInheritedMethodBehavioralData() {
		return invokedMethodInInheritedMethodBehavioralData;
	}

	public void setClassNameList(List<String> nameList) {
		this.classNameList = nameList;
	}
	
	public List<String> getClassNameList() {
		return this.classNameList;
	}

    public void setCloneInvocationMatrix(double[][] m) {
        this.cloneInvocationMatrix = m;
    }

    public void setCloneInvocationBehavioralData(BehavioralData behavioralData) {
		this.cloneInvocationBehavioralData = behavioralData;
	}

	public double[][] getCloneInvocationMatrix() {
        return this.cloneInvocationMatrix;
    }

    public BehavioralData getCloneInvocationBehavioralData() {
		return cloneInvocationBehavioralData;
	}

	public void setSingletonMatrix(double[][] m) {
        this.singletonMatrix = m;
    }

    public void setSingletonBehavioralData(BehavioralData behavioralData) {
		this.singletonBehavioralData = behavioralData;
	}

	public double[][] getSingletonMatrix() {
        return this.singletonMatrix;
    }

    public BehavioralData getSingletonBehavioralData() {
		return singletonBehavioralData;
	}

	public void setTemplateMethodMatrix(double[][] m) {
        this.templateMethodMatrix = m;
    }

    public void setTemplateMethodBehavioralData(BehavioralData behavioralData) {
		this.templateMethodBehavioralData = behavioralData;
	}

	public double[][] getTemplateMethodMatrix() {
        return this.templateMethodMatrix;
    }

    public BehavioralData getTemplateMethodBehavioralData() {
		return templateMethodBehavioralData;
	}

	public void setFactoryMethodMatrix(double[][] m) {
        this.factoryMethodMatrix = m;
    }

    public void setFactoryMethodBehavioralData(BehavioralData behavioralData) {
		this.factoryMethodBehavioralData = behavioralData;
	}

	public double[][] getFactoryMethodMatrix() {
        return this.factoryMethodMatrix;
    }

	public BehavioralData getFactoryMethodBehavioralData() {
		return factoryMethodBehavioralData;
	}
}