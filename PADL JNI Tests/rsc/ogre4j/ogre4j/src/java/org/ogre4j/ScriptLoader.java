package org.ogre4j;

import java.util.Arrays;
import java.util.Vector;

public class ScriptLoader extends NativeObject {

    private static native float getLoadingOrder(int ptrSelf);

    private static native String[] getScriptPatterns(int ptrSelf);

    private ScriptLoader() {
    }

    protected ScriptLoader(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Gets the relative loading order of scripts of this type.
     * 
     * @return
     */
    public float getLoadingOrder() {
        return getLoadingOrder(pInstance.getValue());
    }

    /**
     * Gets the file patterns which should be used to find scripts for this
     * class.
     * 
     * @return
     */
    public Vector<String> getScriptPatterns() {
        return new Vector<String>(Arrays.asList(getScriptPatterns(pInstance
                .getValue())));
    }
}
