package demo.codeanalyzer.helper;

import com.sun.source.tree.Tree;
import com.sun.source.util.SourcePositions;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import javax.lang.model.element.Modifier;

import demo.codeanalyzer.common.model.BaseJavaClassModelInfo;
import demo.codeanalyzer.common.model.LocationInfo;

/**
 * Util class to set modifier details
 * @author Seema Richard (Seema.Richard@ust-global.com)
 * @author Deepa Sobhana (Deepa.Sobhana@ust-global.com)
 */
public class DataSetterUtil {

    public static void setModifiers(String modifiers, BaseJavaClassModelInfo elementInfo) {
        if (modifiers.contains(Modifier.PUBLIC.toString())) {
            elementInfo.setPublicFlag(true);
        } else if (modifiers.contains(Modifier.PROTECTED.toString())) {
            elementInfo.setProtectedFlag(true);
        } else if (modifiers.contains(Modifier.PRIVATE.toString())) {
            elementInfo.setPrivateFlag(true);
        }

        if (modifiers.contains(Modifier.FINAL.toString())) {
            elementInfo.setFinalFlag(true);
        }

        if (modifiers.contains(Modifier.ABSTRACT.toString())) {
            elementInfo.setAbstractFlag(true);
        }

        if (modifiers.contains(Modifier.NATIVE.toString())) {
            elementInfo.setNativeFlag(true);
        }

        if (modifiers.contains(Modifier.STATIC.toString())) {
            elementInfo.setStaticFlag(true);
        }

    }

    public static LocationInfo getLocationInfo(Trees trees, TreePath path, Tree tree) {
        //Set Temp LocationInfo
        LocationInfo locationInfo = new LocationInfo();
        SourcePositions sourcePosition = trees.getSourcePositions();
        long startPosition = sourcePosition.getStartPosition(path.getCompilationUnit(), tree);
        locationInfo.setStartOffset((int) startPosition);
        return locationInfo;
    }
}
