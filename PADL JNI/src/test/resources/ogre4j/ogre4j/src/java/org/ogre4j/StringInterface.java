/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 * 
 *
 * StringInterface.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

/**
 * TODO StringInterface type/class description.<br>
 * status: 100%
 * 
 * @author Kai Klesatschke <kai.klesatschke@ogre4j.org>
 */
public class StringInterface extends NativeObject {
    /**
     * Cleans up the static 'msDictionary' required to reset Ogre, otherwise the
     * containers are left with invalid pointers, which will lead to a crash as
     * soon as one of the ResourceManager implementers (e.g.
     * 
     */
    public static void cleanupDictionary() {
        // TODO
    }

    private static native void copyParametersTo(int ptrSelf, int ptrDest);

    private static native int getParamDictionary(int ptrSelf);

    private static native String getParameter(int ptrSelf, String name);

    private static native ParameterDef[] getParameters(int ptrSelf);

    private static native boolean setParameter(int ptrSelf, String name,
            String value);

    private static native void setParameterList(int ptrSelf, Object[] keys,
            Object[] values);

    private StringInterface() {
        super(null);
    }

    protected StringInterface(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Method for copying this object's parameters to another object.
     * 
     * @param dest
     * @return
     */
    public void copyParametersTo(StringInterface dest) {
        copyParametersTo(pInstance.getValue(), dest.pInstance.getValue());
    }

    /**
     * Retrieves the parameter dictionary for this class.
     * 
     * @return
     */
    public ParamDictionary getParamDictionary() {
        int address = getParamDictionary(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new ParamDictionary(ptr);
    }

    /**
     * Generic parameter retrieval method.
     * 
     * @param name
     */
    public String getParameter(String name) {
        return getParameter(pInstance.getValue(), name);
    }

    /**
     * Retrieves a list of parameters valid for this object.
     * 
     * @return
     */
    public Vector<ParameterDef> getParameters() {
        return new Vector<ParameterDef>(Arrays.asList(getParameters(pInstance
                .getValue())));
    }

    /**
     * Generic parameter setting method.
     * 
     * @param name
     * @param value
     * @return
     */
    public boolean setParameter(String name, String value) {
        return setParameter(pInstance.getValue(), name, value);
    }

    /**
     * Generic multiple parameter setting method.
     * 
     * @param paramList
     */
    public void setParameterList(Map<String, String> paramList) {
        Object[] keys = paramList.keySet().toArray();
        Object[] values = paramList.values().toArray();
        setParameterList(pInstance.getValue(), keys, values);
    }
}
