package demo.codeanalyzer.helper;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import demo.codeanalyzer.common.model.AnnotationInfo;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.common.model.JavaSourceTreeInfo;
import demo.codeanalyzer.common.model.LocationInfo;
import static demo.codeanalyzer.common.util.CodeAnalyzerConstants.SERIALIZABLE_PKG;

/**
 * Helper class to set the properties of a java class
 * to the java class model
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class ClassInfoDataSetter {

    /**
     * Set the attributes of the currently visiting class 
     * to the java class model
     * @param clazzInfo The java class model
     * @param classTree Curently visiting class tree
     * @param path tree path
     * @param trees trees
     */
    public static void populateClassInfo(JavaClassInfo clazzInfo,
            ClassTree classTree, TreePath path, Trees trees) {

        TypeElement e = (TypeElement) trees.getElement(path);

        //Set qualified class name
        clazzInfo.setName(e.getQualifiedName().toString());

        //Set Nesting kind
        clazzInfo.setNestingKind(e.getNestingKind().toString());

        //Set modifier details
        for (Modifier modifier : e.getModifiers()) {
            DataSetterUtil.setModifiers(modifier.toString(), clazzInfo);
        }

        //Set extending class info
        clazzInfo.setNameOfSuperClass(e.getSuperclass().toString());

        //Set implementing interface details
        for (TypeMirror mirror : e.getInterfaces()) {
            clazzInfo.addNameOfInterface(mirror.toString());
        }
        //Set serializable property
        try {
            Class serializable = Class.forName(SERIALIZABLE_PKG);
            Class thisClass = Class.forName(e.getQualifiedName().toString());
            if (serializable.isAssignableFrom(thisClass)) {
                clazzInfo.setSerializable(true);
            } else {
                clazzInfo.setSerializable(false);
            }

        } catch (ClassNotFoundException ex) {
            clazzInfo.setSerializable(false);
        }
        
          List<? extends AnnotationMirror> annotations = e.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotations) {
            String qualifiedName = annotationMirror.toString().substring(1);
            AnnotationInfo annotationInfo = new AnnotationInfo();
            annotationInfo.setName(qualifiedName);
            clazzInfo.addAnnotation(annotationInfo);
        }

        LocationInfo locationInfo = DataSetterUtil.getLocationInfo(trees, path, classTree);
        clazzInfo.setLocationInfo(locationInfo);

        //setJavaTreeDetails
        JavaSourceTreeInfo treeInfo = new JavaSourceTreeInfo();
        TreePath tp = trees.getPath(e);
        treeInfo.setCompileTree(tp.getCompilationUnit());
        treeInfo.setSourcePos(trees.getSourcePositions());
        clazzInfo.setSourceTreeInfo(treeInfo);

    }
}
