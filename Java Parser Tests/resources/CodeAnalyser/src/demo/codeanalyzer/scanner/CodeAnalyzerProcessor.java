package demo.codeanalyzer.scanner;

import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import demo.codeanalyzer.common.CodeAnalyzer;
import demo.codeanalyzer.common.model.ClassModelMap;
import demo.codeanalyzer.common.model.JavaClassInfo;
import demo.codeanalyzer.helper.LocationInfoSetter;

/**
 * The annotation processor class which processes java annotaions in the
 * supplied source file(s). This processor supports v1.6 of java language and
 * can processes all annotation types.
 * 
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("*")
public class CodeAnalyzerProcessor extends AbstractProcessor {

    private Trees trees;

    @Override
    public void init(ProcessingEnvironment pe) {
        super.init(pe);
        trees = Trees.instance(pe);
    }

    /**
     * Processes the annotation types defined for this processor.
     * 
     * @param annotations
     *            the annotation types requested to be processed
     * @param roundEnvironment
     *            environment to get information about the current and prior
     *            round
     * @return whether or not the set of annotations are claimed by this
     *         processor
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnvironment) {

        // Scanner class to scan through various component elements
        CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor();

        for (Element e : roundEnvironment.getRootElements()) {
            TreePath tp = trees.getPath(e);
            // invoke the scanner
            visitor.scan(tp, trees);
            TypeElement typeElement = (TypeElement) e;
            String className = typeElement.getQualifiedName().toString();
            JavaClassInfo clazzInfo = visitor.getClassInfo();
            LocationInfoSetter.setLocationInfoForElements(clazzInfo);
            ClassModelMap.getInstance().addClassInfo(className, clazzInfo);
            CodeAnalyzer.getInstance().process(className);
        }

        return true;
    }

}
