package demo.codeanalyzer.common.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores the attribute details of all java input files
 * @author Deepa Sobhana, Seema Richard
 */
public class ClassModelMap {

    private Map<String, ClassFile> classInfoMap = new HashMap<String, ClassFile>();
    private static ClassModelMap modelMap = new ClassModelMap();

    private ClassModelMap() {

    }

    public static ClassModelMap getInstance() {
        return modelMap;
    }

    public Map<String, ClassFile> getClassInfoMap() {
        return classInfoMap;
    }

    public ClassFile getClassInfo(String className) {
        return classInfoMap.get(className);
    }

    public void addClassInfo(String className, ClassFile classInfo) {
        classInfoMap.put(className, classInfo);
    }
}
