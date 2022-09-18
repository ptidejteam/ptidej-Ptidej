package gr.uom.java.pattern;

import java.util.List;
import java.util.ArrayList;

public class PatternGenerator {

    public static PatternDescriptor getPattern(String patternName) {
        if(patternName.equals(PatternEnum.DECORATOR.toString())) {
            return getDecoratorPattern();
        }
        else if(patternName.equals(PatternEnum.COMPOSITE.toString())) {
            return getCompositePattern();
        }
        else if(patternName.equals(PatternEnum.STATE_STRATEGY.toString())) {
            return getStatePattern();
        }
        else if(patternName.equals(PatternEnum.OBSERVER.toString())) {
            return getObserverPattern();
        }
        else if(patternName.equals(PatternEnum.VISITOR.toString())) {
            return getVisitorPattern();
        }
        else if(patternName.equals(PatternEnum.PROTOTYPE.toString())) {
            return getPrototypePattern();
        }
        else if(patternName.equals(PatternEnum.ADAPTER_COMMAND.toString())) {
            return getAdapterPattern();
        }
        else if(patternName.equals(PatternEnum.SINGLETON.toString())) {
            return getSingletonPattern();
        }
        else if(patternName.equals(PatternEnum.TEMPLATE_METHOD.toString())) {
            return getTemplateMethodPattern();
        }
        else if(patternName.equals(PatternEnum.FACTORY_METHOD.toString())) {
            return getFactoryMethodPattern();
        }
        else if(patternName.equals(PatternEnum.PROXY.toString())) {
        	return getProxyPattern();
        }
        else if(patternName.equals(PatternEnum.PROXY2.toString())) {
        	return getProxy2Pattern();
        }
        /*else if(patternName.equals(PatternEnum.REDIRECT_IN_FAMILY.toString())) {
        	return getRedirectInFamily();
        }*/

        return null;
    }

    public static PatternDescriptor getSingletonPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Singleton");
        patternDescriptor.setClassNameList(rowNameList);

        patternDescriptor.setNumberOfHierarchies(0);
        patternDescriptor.setFieldRoleName("uniqueInstance");
        return patternDescriptor;
    }

    public static PatternDescriptor getTemplateMethodPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("AbstractClass");
        patternDescriptor.setClassNameList(rowNameList);
        patternDescriptor.setMethodRoleName("TemplateMethod()");

        patternDescriptor.setNumberOfHierarchies(0);
        return patternDescriptor;
    }

    public static PatternDescriptor getFactoryMethodPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Creator");
        patternDescriptor.setClassNameList(rowNameList);
        patternDescriptor.setMethodRoleName("FactoryMethod()");

        patternDescriptor.setNumberOfHierarchies(0);
        return patternDescriptor;
    }

    public static PatternDescriptor getDecoratorPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Component");
        rowNameList.add("Decorator");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] associationWithInheritanceMatrix = new double[2][2];
        associationWithInheritanceMatrix[1][0] = 1;
        patternDescriptor.setAssociationWithInheritanceMatrix(associationWithInheritanceMatrix);

        double[][] abMatrix = new double[2][2];
        abMatrix[0][0] = 1;
        abMatrix[1][1] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);

        double[][] simMatrix = new double[2][2];
        simMatrix[1][0] = 1;
        patternDescriptor.setSimilarAbstractMethodInvocationMatrix(simMatrix);

        patternDescriptor.setNumberOfHierarchies(1);

        int[] divisorArray = new int[2];
        divisorArray[0] = 3;
        divisorArray[1] = 3;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Operation()");
        patternDescriptor.setFieldRoleName("component");

        return patternDescriptor;
    }

    public static PatternDescriptor getCompositePattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Component");
        rowNameList.add("Composite");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] collectionsInvMatrix = new double[2][2];
        collectionsInvMatrix[1][0] = 1;
        patternDescriptor.setIterativeSimilarAbstractMethodInvocationMatrix(collectionsInvMatrix);

        double[][] abMatrix = new double[2][2];
        abMatrix[0][0] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);
        
        patternDescriptor.setNumberOfHierarchies(1);

        int[] divisorArray = new int[2];
        divisorArray[0] = 2;
        divisorArray[1] = 1;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Operation()");

        return patternDescriptor;
    }

    public static PatternDescriptor getObserverPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Subject");
        rowNameList.add("Observer");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] collectionsInvMatrix = new double[2][2];
        collectionsInvMatrix[0][1] = 1;
        patternDescriptor.setIterativeNonSimilarAbstractMethodInvocationMatrix(collectionsInvMatrix);

        double[][] abMatrix = new double[2][2];
        abMatrix[1][1] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);
        
        patternDescriptor.setNumberOfHierarchies(2);

        int[] divisorArray = new int[2];
        divisorArray[0] = 1;
        divisorArray[1] = 2;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Notify()");

        return patternDescriptor;
    }

    public static PatternDescriptor getStatePattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Context");
        rowNameList.add("State/Strategy");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] assocMatrix = new double[2][2];
        assocMatrix[0][1] = 1;
        patternDescriptor.setAssociationMatrix(assocMatrix);

        double[][] abMatrix = new double[2][2];
        abMatrix[1][1] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);

        double[][] abminvMatrix = new double[2][2];
        abminvMatrix[0][1] = 1;
        patternDescriptor.setAbstractMethodInvocationMatrix(abminvMatrix);

        patternDescriptor.setNumberOfHierarchies(2);

        int[] divisorArray = new int[2];
        divisorArray[0] = 2;
        divisorArray[1] = 3;
        patternDescriptor.setDivisorArray(divisorArray);

        return patternDescriptor;
    }

    public static PatternDescriptor getVisitorPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Visitor");
        rowNameList.add("ConcreteElement");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] abMatrix = new double[2][2];
        abMatrix[0][0] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);

        double[][] doubleDispatchMatrix = new double[2][2];
        doubleDispatchMatrix[0][1] = 1;
        patternDescriptor.setDoubleDispatchMatrix(doubleDispatchMatrix);

        patternDescriptor.setNumberOfHierarchies(2);

        int[] divisorArray = new int[2];
        divisorArray[0] = 2;
        divisorArray[1] = 1;

        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Accept()");

        return patternDescriptor;
    }

    public static PatternDescriptor getPrototypePattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Prototype");
        rowNameList.add("Client");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] abMatrix = new double[2][2];
        abMatrix[0][0] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);

        double[][] cloneMatrix = new double[2][2];
        cloneMatrix[1][0] = 1;
        patternDescriptor.setCloneInvocationMatrix(cloneMatrix);

        double[][] assocMatrix = new double[2][2];
        assocMatrix[1][0] = 1;
        patternDescriptor.setAssociationMatrix(assocMatrix);

        patternDescriptor.setNumberOfHierarchies(2);

        int[] divisorArray = new int[2];
        divisorArray[0] = 3;
        divisorArray[1] = 2;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Operation()");

        return patternDescriptor;
    }

    public static PatternDescriptor getAdapterPattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Adapter/ConcreteCommand");
        rowNameList.add("Adaptee/Receiver");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] assocMatrix = new double[2][2];
        assocMatrix[0][1] = 1;
        patternDescriptor.setAssociationMatrix(assocMatrix);

        double[][] adaptMatrix = new double[2][2];
        adaptMatrix[0][1] = 1;
        patternDescriptor.setInvokedMethodInInheritedMethodMatrix(adaptMatrix);

        patternDescriptor.setNumberOfHierarchies(2);

        int[] divisorArray = new int[2];
        divisorArray[0] = 2;
        divisorArray[1] = 2;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Request()/Execute()");

        return patternDescriptor;
    }
    
    public static PatternDescriptor getProxyPattern() {
    	PatternDescriptor patternDescriptor = new PatternDescriptor();
    	
    	List<String> rowNameList = new ArrayList<String>();
    	rowNameList.add("Proxy");
        rowNameList.add("RealSubject");
        patternDescriptor.setClassNameList(rowNameList);
        
        double[][] assocMatrix = new double[2][2];
        assocMatrix[0][1] = 1;
        patternDescriptor.setAssociationMatrix(assocMatrix);
        
        double[][] similarMethodInvocationFromSiblingSubclassMatrix = new double[2][2];
        similarMethodInvocationFromSiblingSubclassMatrix[0][1] = 1;
        patternDescriptor.setSimilarMethodInvocationFromSiblingSubclassMatrix(similarMethodInvocationFromSiblingSubclassMatrix);
        
        patternDescriptor.setNumberOfHierarchies(1);
        
        int[] divisorArray = new int[2];
        divisorArray[0] = 2;
        divisorArray[1] = 2;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Request()");

        return patternDescriptor;
    }
    
    public static PatternDescriptor getProxy2Pattern() {
        PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("Subject");
        rowNameList.add("Proxy");
        rowNameList.add("RealSubject");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] associationWithInheritanceMatrix = new double[3][3];
        associationWithInheritanceMatrix[1][0] = 1;
        patternDescriptor.setAssociationWithInheritanceMatrix(associationWithInheritanceMatrix);

        double[][] abMatrix = new double[3][3];
        abMatrix[0][0] = 1;
        patternDescriptor.setAbstractMatrix(abMatrix);

        double[][] simMatrix = new double[3][3];
        simMatrix[1][0] = 1;
        patternDescriptor.setSimilarAbstractMethodInvocationMatrix(simMatrix);

        double[][] fieldMatrix = new double[3][3];
        fieldMatrix[1][2] = 1;
        patternDescriptor.setFieldOfSuperClassTypeIsInitializedWithSiblingClassTypeMatrix(fieldMatrix);
        
        patternDescriptor.setNumberOfHierarchies(1);

        int[] divisorArray = new int[3];
        divisorArray[0] = 3;
        divisorArray[1] = 3;
        divisorArray[2] = 1;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Request()");
        patternDescriptor.setFieldRoleName("subject");

        return patternDescriptor;
    }
    
    public static PatternDescriptor getRedirectInFamily() {
    	PatternDescriptor patternDescriptor = new PatternDescriptor();

        List<String> rowNameList = new ArrayList<String>();
        rowNameList.add("FamilyHead");
        rowNameList.add("Redirecter");
        patternDescriptor.setClassNameList(rowNameList);

        double[][] assocMatrix = new double[2][2];
        assocMatrix[1][0] = 1;
        patternDescriptor.setAssociationMatrix(assocMatrix);

        double[][] generalizationMatrix = new double[2][2];
        generalizationMatrix[1][0] = 1;
        patternDescriptor.setGeneralizationMatrix(generalizationMatrix);

        double[][] simMatrix = new double[2][2];
        simMatrix[1][0] = 1;
        patternDescriptor.setSimilarAbstractMethodInvocationMatrix(simMatrix);

        patternDescriptor.setNumberOfHierarchies(1);

        int[] divisorArray = new int[2];
        divisorArray[0] = 3;
        divisorArray[1] = 3;
        patternDescriptor.setDivisorArray(divisorArray);
        patternDescriptor.setMethodRoleName("Operation()");

        return patternDescriptor;
    }
}