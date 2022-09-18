package org.ogre4j;

import java.util.Vector;

/**
 * 100% public C++
 * 
 * @author Stephen Tyler
 * 
 */
public class ParamDictionary extends NativeObject {
    protected ParamDictionary(InstancePointer pInstance) {
        super(pInstance);
    }

    public ParamDictionary() {
        // TODO
    }

    /**
     * Method for adding a parameter definition for this class.
     * 
     * @param paramDef
     * @param paramCmd
     */
    public void addParameter(ParameterDef paramDef, ParamCommand paramCmd) {

    }

    /**
     * Retrieves a list of parameters valid for this object.
     * 
     * @return
     */
    public Vector<ParameterDef> getParameters() {
        return null;
    }

}
