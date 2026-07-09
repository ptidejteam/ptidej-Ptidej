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
 * Resource.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/15 07:34:14 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * TODO: Resource Implementation <br>
 * 100% c++ public methods (some in super class)
 * 
 * @author Kai Klesatschke <kai.klesatschke@ogre4j.org>
 */
abstract public class Resource extends StringInterface {

    private static native void _notifyOrigin(int ptrSelf, String origin);

    private static native int getCreator(int ptrSelf);

    private static native String getGroup(int ptrSelf);

    private static native int getHandle(int ptrSelf);

    private static native String getName(int ptrSelf);

    private static native String getOrigin(int ptrSelf);

    private static native int getSize(int ptrSelf);

    private static native boolean isLoaded(int ptrSelf);

    private static native boolean isManuallyLoaded(int ptrSelf);

    private static native void load(int ptrSelf);

    private static native void reload(int ptrSelf);

    private static native void touch(int ptrSelf);

    private static native void unload(int ptrSelf);

    /**
     * @param pInstance
     */
    public Resource(InstancePointer pInstance) {
        super(pInstance);
    }

    public Resource(ResourceManager creator, String name, int handle,
            String group, boolean isManual, ManualResourceLoader loader) {
        super(null);
    }

    /**
     * Notify this resource of it's origin.
     * 
     * @param origin
     */
    public void _notifyOrigin(String origin) {
        _notifyOrigin(pInstance.getValue(), origin);
    }

    /**
     * Gets the manager which created this resource.
     * 
     * @return
     */
    public ResourceManager getCreator() {
        int ptr = getCreator(pInstance.getValue());
        return new ResourceManager(new InstancePointer(ptr));
    }

    /**
     * Gets the group which this resource is a member of.
     * 
     * @return
     */
    public String getGroup() {
        return getGroup(pInstance.getValue());
    }

    public int getHandle() {
        return getHandle(pInstance.getValue());
    }

    /**
     * Gets resource name.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    /**
     * Get the origin of this resource, e.g.
     * 
     * @return
     */
    public String getOrigin() {
        return getOrigin(pInstance.getValue());
    }

    /**
     * Retrieves info about the size of the resource.
     * 
     * @return
     */
    public int getSize() {
        return getSize(pInstance.getValue());
    }

    /**
     * Returns true if the Resource has been loaded, false otherwise.
     * 
     * @return
     */
    public boolean isLoaded() {
        return isLoaded(pInstance.getValue());
    }

    /**
     * Is this resource manually loaded?
     * 
     * @return
     */
    public boolean isManuallyLoaded() {
        return isManuallyLoaded(pInstance.getValue());
    }

    /**
     * Loads the resource, if it is not already.
     * 
     */
    public void load() {
        load(pInstance.getValue());
    }

    /**
     * Reloads the resource, if it is already loaded.
     * 
     */
    public void reload() {
        reload(pInstance.getValue());
    }

    /**
     * 'Touches' the resource to indicate it has been used.
     * 
     */
    public void touch() {
        touch(pInstance.getValue());
    }

    /**
     * Unloads the resource; this is not permanent, the resource can be reloaded
     * later if required.
     * 
     */
    public void unload() {
        unload(pInstance.getValue());
    }

}
