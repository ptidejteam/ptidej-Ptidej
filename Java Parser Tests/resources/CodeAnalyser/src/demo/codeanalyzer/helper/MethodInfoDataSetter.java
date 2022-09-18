package demo.codeanalyzer.helper;

import com.sun.source.tree.MethodTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor6;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.common.model.LocationInfo;
import demo.codeanalyzer.common.model.MethodInfo;
import demo.codeanalyzer.common.util.CodeAnalyzerUtil;
import static demo.codeanalyzer.common.util.CodeAnalyzerConstants.DEFAULT_CONSTRUCTOR_NAME;

/**
 * Helper class to set the properties of a method
 * to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class MethodInfoDataSetter {

    /**
     * Set the attributes of the currently visiting method 
     * to the java class model
     * @param clazzInfo The java class model
     * @param methodTree Curently visiting method tree
     * @param path tree path
     * @param trees trees
     */
    public static void populateMethodInfo(JavaClassInfo clazzInfo,
            MethodTree methodTree, TreePath path, Trees trees) {

        MethodInfo methodInfo = new MethodInfo();
        String methodName = methodTree.getName().toString();
        methodInfo.setOwningClass(clazzInfo);
        //Set modifier details
        Element e = trees.getElement(path);
        //Set the param type and return path
        visitExecutable(e, methodInfo);

        //set modifiers
        for (Modifier modifier : e.getModifiers()) {
            DataSetterUtil.setModifiers(modifier.toString(), methodInfo);
        }
        
        //Check if the method is a default constructor
        if (methodName.equals(DEFAULT_CONSTRUCTOR_NAME)) {
            methodInfo.setName(CodeAnalyzerUtil.getSimpleNameFromQualifiedName
                                                    (clazzInfo.getName()));
            clazzInfo.addConstructor(methodInfo);
        } else {
            clazzInfo.addMethod(methodInfo);
            methodInfo.setName(methodName);
        }

       LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, methodTree);
        methodInfo.setLocationInfo(locationInfo);       
    }

    /**
     * Visit the element passed to this method to extract the parameter types 
     * and return type of the method
     * @param e Element being visited
     * @param methodInfo Model which holds method-level attributes
     */
    private static void visitExecutable(Element e, 
                                        final MethodInfo methodInfo) {
        e.accept(new SimpleElementVisitor6<Object, MethodInfo>() {

            @Override
            public Object visitExecutable(ExecutableElement element, 
                                          MethodInfo mInfo) {

                for (VariableElement var : element.getParameters()) {
                    methodInfo.addParameters(var.asType().toString());
                }
                methodInfo.setReturnType(element.getReturnType().toString());
                return super.visitExecutable(element, methodInfo);
            }
        }, null);
    }
}
